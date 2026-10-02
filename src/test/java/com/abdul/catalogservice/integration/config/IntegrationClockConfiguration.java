package com.abdul.catalogservice.integration.config;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;

import java.time.Instant;

@TestConfiguration(proxyBeanMethods = false)
public class IntegrationClockConfiguration {
    @Bean
    @Primary
    public MutableClock integrationClock() {
        return new MutableClock(Instant.parse("2026-10-01T00:00:00Z"));
    }
}
