package dev.nokk.catalog.api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.nokk.catalog.catalog.api.dto.request.CreateCatalogRequestDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.elasticsearch.ElasticsearchContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.math.BigDecimal;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Testcontainers
class CatalogControllerIT {
    private final static String TEST_CATALOG_NAME = "товар";
    private final static BigDecimal TEST_CATALOG_PRICE = BigDecimal.TEN;

    @Container
    @ServiceConnection
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:18");

    @Container
    @ServiceConnection
    static ElasticsearchContainer elastic = new ElasticsearchContainer(
            "docker.elastic.co/elasticsearch/elasticsearch:9.2.3"
    )
            .withEnv("discovery.type", "single-node")
            .withEnv("xpack.security.enabled", "false")
            .withEnv("ES_JAVA_OPTS", "-Xms512m -Xmx512m");;


    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    void createCatalog_shouldPersistAndReturn201() throws Exception {

        // given
        CreateCatalogRequestDto request = new CreateCatalogRequestDto(
                        TEST_CATALOG_NAME,
                        TEST_CATALOG_PRICE
                );

        String json = objectMapper.writeValueAsString(request);

        // when / then
        mockMvc.perform(post("/api/v1/catalog")
                        .contentType(APPLICATION_JSON)
                        .content(json))
                .andDo(print())
                .andExpectAll(
                        status().isCreated(),
                        jsonPath("$.id").isNotEmpty(),
                        jsonPath("$.name").value("товар"),
                        jsonPath("$.price").value(10)
                );
    }
}
