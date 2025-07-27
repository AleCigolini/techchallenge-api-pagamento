package br.com.fiap.techchallengeapipagamento.core.config.exception.exceptions;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("NegocioException Tests")
class NegocioExceptionTest {

    @Test
    @DisplayName("Deve criar exceção com mensagem")
    void deveCriarExcecaoComMensagem() {
        // Given
        String mensagem = "Erro de negócio";

        // When
        NegocioException exception = new NegocioException(mensagem);

        // Then
        assertEquals(mensagem, exception.getMessage());
        assertNull(exception.getCause());
    }

    @Test
    @DisplayName("Deve criar exceção com mensagem e causa")
    void deveCriarExcecaoComMensagemECausa() {
        // Given
        String mensagem = "Erro de negócio";
        Throwable causa = new IllegalArgumentException("Argumento inválido");

        // When
        NegocioException exception = new NegocioException(mensagem, causa);

        // Then
        assertEquals(mensagem, exception.getMessage());
        assertEquals(causa, exception.getCause());
    }

    @Test
    @DisplayName("Deve herdar de RuntimeException")
    void deveHerdarDeRuntimeException() {
        // Given
        NegocioException exception = new NegocioException("Teste");

        // Then
        assertTrue(exception instanceof RuntimeException);
    }

    @Test
    @DisplayName("Deve ter anotação ResponseStatus com BAD_REQUEST")
    void deveTerAnotacaoResponseStatusComBadRequest() {
        // Given
        Class<NegocioException> clazz = NegocioException.class;

        // When
        ResponseStatus responseStatus = clazz.getAnnotation(ResponseStatus.class);

        // Then
        assertNotNull(responseStatus);
        assertEquals(HttpStatus.BAD_REQUEST, responseStatus.value());
    }

    @Test
    @DisplayName("Deve permitir mensagem nula")
    void devePermitirMensagemNula() {
        // When
        NegocioException exception = new NegocioException(null);

        // Then
        assertNull(exception.getMessage());
    }

    @Test
    @DisplayName("Deve permitir mensagem e causa nulas")
    void devePermitirMensagemECausaNulas() {
        // When
        NegocioException exception = new NegocioException(null, null);

        // Then
        assertNull(exception.getMessage());
        assertNull(exception.getCause());
    }

    @Test
    @DisplayName("Deve ser possível capturar como RuntimeException")
    void deveSerPossivelCapturarComoRuntimeException() {
        // Given
        String mensagem = "Erro de validação de negócio";

        // When & Then
        assertThrows(RuntimeException.class, () -> {
            throw new NegocioException(mensagem);
        });
    }

    @Test
    @DisplayName("Deve preservar causa quando especificada")
    void devePreservarCausaQuandoEspecificada() {
        // Given
        String mensagem = "Erro de processamento";
        RuntimeException causa = new IllegalStateException("Estado inválido");

        // When
        NegocioException exception = new NegocioException(mensagem, causa);

        // Then
        assertEquals(causa, exception.getCause());
        assertTrue(exception.getCause() instanceof IllegalStateException);
    }
}
