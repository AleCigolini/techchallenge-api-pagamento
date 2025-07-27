package br.com.fiap.techchallengeapipagamento.core.config.config.http;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.converter.BufferedImageHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;

import java.awt.image.BufferedImage;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("HttpResponseConfig Tests")
class HttpResponseConfigTest {

    private HttpResponseConfig httpResponseConfig;

    @BeforeEach
    void setUp() {
        httpResponseConfig = new HttpResponseConfig();
    }

    @Test
    @DisplayName("Deve criar HttpMessageConverter para BufferedImage")
    void deveCriarHttpMessageConverterParaBufferedImage() {
        // When
        HttpMessageConverter<BufferedImage> converter = httpResponseConfig.createImageHttpMessageConverter();

        // Then
        assertNotNull(converter);
        assertTrue(converter instanceof BufferedImageHttpMessageConverter);
    }

    @Test
    @DisplayName("Deve retornar sempre uma nova instância do converter")
    void deveRetornarSempreNovaInstanciaDoConverter() {
        // When
        HttpMessageConverter<BufferedImage> converter1 = httpResponseConfig.createImageHttpMessageConverter();
        HttpMessageConverter<BufferedImage> converter2 = httpResponseConfig.createImageHttpMessageConverter();

        // Then
        assertNotNull(converter1);
        assertNotNull(converter2);
        assertNotSame(converter1, converter2);
    }

    @Test
    @DisplayName("Deve configurar converter com tipos suportados")
    void deveConfigurarConverterComTiposSuportados() {
        // When
        HttpMessageConverter<BufferedImage> converter = httpResponseConfig.createImageHttpMessageConverter();

        // Then
        assertNotNull(converter);
        assertTrue(converter.canRead(BufferedImage.class, null));
        assertTrue(converter.canWrite(BufferedImage.class, null));
    }
}
