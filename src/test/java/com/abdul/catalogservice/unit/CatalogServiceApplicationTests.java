package com.abdul.catalogservice.unit;

import com.abdul.catalogservice.adapter.out.persistence.repository.TrackJpaRepository;
import com.abdul.catalogservice.adapter.out.persistence.repository.UserJpaRepository;
import com.abdul.catalogservice.adapter.out.persistence.repository.UserAliasJpaRepository;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

@SpringBootTest
@ActiveProfiles("test")
class CatalogServiceApplicationTests {

    @MockitoBean
    private UserJpaRepository userJpaRepository;

    @MockitoBean
    private UserAliasJpaRepository userAliasJpaRepository;

    @MockitoBean
    private TrackJpaRepository trackJpaRepository;

    @Test
    void contextLoads() {
    }

}
