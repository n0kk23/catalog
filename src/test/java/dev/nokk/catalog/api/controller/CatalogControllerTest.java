package dev.nokk.catalog.api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.nokk.catalog.catalog.api.controller.CatalogController;
import dev.nokk.catalog.catalog.api.dto.request.CreateCatalogRequestDto;
import dev.nokk.catalog.catalog.api.dto.response.CatalogResponseDto;
import dev.nokk.catalog.catalog.api.mapper.CatalogApiMapper;
import dev.nokk.catalog.catalog.api.mapper.RequestApiMapper;
import dev.nokk.catalog.catalog.app.service.CatalogService;
import dev.nokk.catalog.catalog.app.service.command.CreateCatalogCommand;
import dev.nokk.catalog.catalog.domain.model.Catalog;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CatalogController.class)
class CatalogControllerTest {
    private static final String TEST_CATALOG_NAME = "товар";
    private static final BigDecimal TEST_CATALOG_PRICE = BigDecimal.TEN;

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private CatalogService catalogService;

    @MockitoBean
    private CatalogApiMapper catalogMapper;

    @MockitoBean
    private RequestApiMapper requestMapper;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void createCatalog_shouldReturn201_whenRequestValid() throws Exception {

        // given
        UUID id = UUID.randomUUID();

        CreateCatalogRequestDto request =
                new CreateCatalogRequestDto(
                        TEST_CATALOG_NAME,
                        TEST_CATALOG_PRICE
                );

        CreateCatalogCommand command =
                new CreateCatalogCommand(
                        TEST_CATALOG_NAME,
                        TEST_CATALOG_PRICE
                );

        Catalog catalog = new Catalog(
                id,
                TEST_CATALOG_NAME,
                TEST_CATALOG_PRICE,
                true,
                null,
                null
        );

        Mockito.when(requestMapper.toCommand(any())).thenReturn(command);

        Mockito.when(catalogService.createCatalog(command)).thenReturn(catalog);

        Mockito.when(catalogMapper.toResponse(catalog)).thenReturn(
                        new CatalogResponseDto(
                                id,
                                TEST_CATALOG_NAME,
                                TEST_CATALOG_PRICE,
                                true
                        )
                );

        String json = objectMapper.writeValueAsString(request);

        // when / then
        mockMvc.perform(post("/api/v1/catalog")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpectAll(
                        status().isCreated(),
                        jsonPath("$.name").value(TEST_CATALOG_NAME)
                );
    }

}
