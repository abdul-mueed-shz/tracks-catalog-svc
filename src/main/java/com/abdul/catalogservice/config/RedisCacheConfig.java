package com.abdul.catalogservice.config;

import io.lettuce.core.SocketOptions;
import lombok.extern.slf4j.Slf4j;
import org.jspecify.annotations.NonNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.data.redis.autoconfigure.LettuceClientOptionsBuilderCustomizer;
import org.springframework.cache.Cache;
import org.springframework.cache.annotation.CachingConfigurer;
import org.springframework.cache.interceptor.CacheErrorHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.RedisSerializer;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Configuration
@ConditionalOnProperty(name = "spring.cache.type", havingValue = "redis", matchIfMissing = true)
public class RedisCacheConfig implements CachingConfigurer {

    public static final String ARTIST_OF_THE_DAY_CACHE = "artistOfTheDay";

    @Value("${spring.cache.redis.time-to-live:1d}")
    private Duration defaultTtl;

    @Value("${spring.cache.redis.key-prefix:catalog:}")
    private String keyPrefix;

    @Value("${spring.cache.redis.cache-null-values:false}")
    private boolean cacheNullValues;

    @Bean
    public RedisCacheConfiguration defaultCacheConfiguration() {
        RedisCacheConfiguration config = RedisCacheConfiguration.defaultCacheConfig()
                .entryTtl(defaultTtl)
                .prefixCacheNameWith(keyPrefix)
                .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(RedisSerializer.string()))
                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(RedisSerializer.json()));

        if (!cacheNullValues) {
            config = config.disableCachingNullValues();
        }

        return config;
    }

    @Bean
    public RedisCacheManager cacheManager(RedisConnectionFactory connectionFactory) {
        RedisCacheConfiguration defaultConfiguration = defaultCacheConfiguration();

        Map<String, RedisCacheConfiguration> initialCacheConfigurations = new HashMap<>();
        // Configure specific cache TTLs: Artist of the Day is cached for 24 hours
        initialCacheConfigurations.put(ARTIST_OF_THE_DAY_CACHE, defaultConfiguration.entryTtl(Duration.ofHours(24)));

        return RedisCacheManager.builder(connectionFactory)
                .cacheDefaults(defaultConfiguration)
                .withInitialCacheConfigurations(initialCacheConfigurations)
                .build();
    }

    @Bean
    public LettuceClientOptionsBuilderCustomizer lettuceClientOptionsBuilderCustomizer(
            @Value("${spring.data.redis.connect-timeout:2000ms}") Duration connectTimeout
    ) {
        return builder -> {
            SocketOptions socketOptions = SocketOptions.builder()
                    .connectTimeout(connectTimeout)
                    .keepAlive(true)
                    .build();

            builder.socketOptions(socketOptions)
                    .autoReconnect(true);
        };
    }

    @Override
    public CacheErrorHandler errorHandler() {
        return new CacheErrorHandler() {
            @Override
            public void handleCacheGetError(@NonNull RuntimeException exception,
                                            @NonNull Cache cache,
                                            @NonNull Object key) {
                log.warn("Redis cache GET error for cache '{}' and key '{}'. Falling back to database.",
                        cache.getName(), key, exception);
            }

            @Override
            public void handleCachePutError(@NonNull RuntimeException exception,
                                            @NonNull Cache cache,
                                            @NonNull Object key,
                                            Object value) {
                log.warn("Redis cache PUT error for cache '{}' and key '{}'.",
                        cache.getName(), key, exception);
            }

            @Override
            public void handleCacheEvictError(@NonNull RuntimeException exception,
                                              @NonNull Cache cache,
                                              @NonNull Object key) {
                log.warn("Redis cache EVICT error for cache '{}' and key '{}'.",
                        cache.getName(), key, exception);
            }

            @Override
            public void handleCacheClearError(@NonNull RuntimeException exception,
                                              @NonNull Cache cache) {
                log.warn("Redis cache CLEAR error for cache '{}'.",
                        cache.getName(), exception);
            }
        };
    }
}
