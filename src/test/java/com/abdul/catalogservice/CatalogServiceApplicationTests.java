package com.abdul.catalogservice;

import com.abdul.catalogservice.adapter.out.persistence.repository.UserJpaRepository;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

@SpringBootTest
@ActiveProfiles("test")
class CatalogServiceApplicationTests {

    @MockitoBean
    private UserJpaRepository userJpaRepository;

    @Test
    void contextLoads() {
    }

}
