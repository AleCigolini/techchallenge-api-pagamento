package br.com.fiap.techchallengeapipagamento.core.config.config;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("SwaggerRedirectController Tests")
class SwaggerRedirectControllerTest {

    private SwaggerRedirectController swaggerRedirectController;

    @BeforeEach
    void setUp() {
        swaggerRedirectController = new SwaggerRedirectController();
    }

    @Test
    @DisplayName("Deve redirecionar para o Swagger UI")
    void deveRedirecionarParaSwaggerUI() {
        // When
        String redirectUrl = swaggerRedirectController.redirectToSwagger();

        // Then
        assertNotNull(redirectUrl);
        assertEquals("redirect:/swagger-ui/index.html", redirectUrl);
    }

    @Test
    @DisplayName("Deve sempre retornar a mesma URL de redirecionamento")
    void deveSempreRetornarMesmaURLDeRedirecionamento() {
        // When
        String redirectUrl1 = swaggerRedirectController.redirectToSwagger();
        String redirectUrl2 = swaggerRedirectController.redirectToSwagger();

        // Then
        assertEquals(redirectUrl1, redirectUrl2);
        assertEquals("redirect:/swagger-ui/index.html", redirectUrl1);
        assertEquals("redirect:/swagger-ui/index.html", redirectUrl2);
    }

    @Test
    @DisplayName("Deve retornar string com prefixo redirect")
    void deveRetornarStringComPrefixoRedirect() {
        // When
        String redirectUrl = swaggerRedirectController.redirectToSwagger();

        // Then
        assertTrue(redirectUrl.startsWith("redirect:"));
    }

    @Test
    @DisplayName("Deve apontar para o caminho correto do Swagger UI")
    void deveApontarParaCaminhoCorretoDoSwaggerUI() {
        // When
        String redirectUrl = swaggerRedirectController.redirectToSwagger();

        // Then
        assertTrue(redirectUrl.contains("swagger-ui"));
        assertTrue(redirectUrl.endsWith("index.html"));
    }
}
