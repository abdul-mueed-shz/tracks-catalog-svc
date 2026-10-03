package com.abdul.catalogservice.unit.config;

import com.abdul.catalogservice.config.RedisCacheConfig;
import io.lettuce.core.ClientOptions;
import org.junit.jupiter.api.Test;
import org.springframework.cache.Cache;
import org.springframework.cache.interceptor.CacheErrorHandler;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.Duration;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class RedisCacheConfigTest {

    private final RedisCacheConfig redisCacheConfig = new RedisCacheConfig();

    @Test
    void defaultCacheConfigurationConfiguresTtlPrefixAndNullValues() {
        ReflectionTestUtils.setField(redisCacheConfig, "defaultTtl", Duration.ofHours(12));
        ReflectionTestUtils.setField(redisCacheConfig, "keyPrefix", "catalog-test:");
        ReflectionTestUtils.setField(redisCacheConfig, "cacheNullValues", false);

        RedisCacheConfiguration config = redisCacheConfig.defaultCacheConfiguration();

        assertThat(config.getTtlFunction().getTimeToLive(null, null)).isEqualTo(Duration.ofHours(12));
        assertThat(config.getKeyPrefixFor("testCache")).isEqualTo("catalog-test:testCache::");
        assertThat(config.getAllowCacheNullValues()).isFalse();
        assertThat(config.getKeySerializationPair()).isNotNull();
        assertThat(config.getValueSerializationPair()).isNotNull();
    }

    @Test
    void cacheManagerConfiguresArtistOfTheDaySpecificTtl() {
        ReflectionTestUtils.setField(redisCacheConfig, "defaultTtl", Duration.ofHours(1));
        ReflectionTestUtils.setField(redisCacheConfig, "keyPrefix", "catalog:");
        ReflectionTestUtils.setField(redisCacheConfig, "cacheNullValues", false);

        RedisConnectionFactory connectionFactory = mock(RedisConnectionFactory.class);
        RedisCacheManager cacheManager = redisCacheConfig.cacheManager(connectionFactory);
        cacheManager.afterPropertiesSet();

        assertThat(cacheManager).isNotNull();
        RedisCacheConfiguration artistConfig = cacheManager.getCacheConfigurations()
                .get(RedisCacheConfig.ARTIST_OF_THE_DAY_CACHE);
        assertThat(artistConfig).isNotNull();
        assertThat(artistConfig.getTtlFunction().getTimeToLive(null, null)).isEqualTo(Duration.ofHours(24));
    }

    @Test
    void lettuceClientOptionsConfiguresKeepAliveAndTimeout() {
        var customizer = redisCacheConfig.lettuceClientOptionsBuilderCustomizer(Duration.ofSeconds(3));
        ClientOptions.Builder builder = ClientOptions.builder();
        customizer.customize(builder);

        ClientOptions options = builder.build();
        assertThat(options.isAutoReconnect()).isTrue();
        assertThat(options.getSocketOptions().isKeepAlive()).isTrue();
        assertThat(options.getSocketOptions().getConnectTimeout()).isEqualTo(Duration.ofSeconds(3));
    }

    @Test
    void errorHandlerDoesNotThrowExceptionsOnFailure() {
        CacheErrorHandler errorHandler = redisCacheConfig.errorHandler();
        Cache mockCache = mock(Cache.class);
        when(mockCache.getName()).thenReturn("testCache");
        RuntimeException error = new RuntimeException("Redis connection timed out");

        assertThatCode(() -> errorHandler.handleCacheGetError(error, mockCache, "key1"))
                .doesNotThrowAnyException();
        assertThatCode(() -> errorHandler.handleCachePutError(error, mockCache, "key1", "val1"))
                .doesNotThrowAnyException();
        assertThatCode(() -> errorHandler.handleCacheEvictError(error, mockCache, "key1"))
                .doesNotThrowAnyException();
        assertThatCode(() -> errorHandler.handleCacheClearError(error, mockCache))
                .doesNotThrowAnyException();
    }
}
