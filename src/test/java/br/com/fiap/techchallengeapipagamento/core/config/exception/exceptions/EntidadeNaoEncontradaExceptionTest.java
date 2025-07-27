package br.com.fiap.techchallengeapipagamento.core.config.exception.exceptions;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("EntidadeNaoEncontradaException Tests")
class EntidadeNaoEncontradaExceptionTest {

    @Test
    @DisplayName("Deve criar exceção com mensagem")
    void deveCriarExcecaoComMensagem() {
        // Given
        String mensagem = "Entidade não encontrada";

        // When
        EntidadeNaoEncontradaException exception = new EntidadeNaoEncontradaException(mensagem);

        // Then
        assertEquals(mensagem, exception.getMessage());
    }

    @Test
    @DisplayName("Deve herdar de NegocioException")
    void deveHerdarDeNegocioException() {
        // Given
        EntidadeNaoEncontradaException exception = new EntidadeNaoEncontradaException("Teste");

        // Then
        assertTrue(exception instanceof NegocioException);
    }

    @Test
    @DisplayName("Deve ter anotação ResponseStatus com NOT_FOUND")
    void deveTerAnotacaoResponseStatusComNotFound() {
        // Given
        Class<EntidadeNaoEncontradaException> clazz = EntidadeNaoEncontradaException.class;

        // When
        ResponseStatus responseStatus = clazz.getAnnotation(ResponseStatus.class);

        // Then
        assertNotNull(responseStatus);
        assertEquals(HttpStatus.NOT_FOUND, responseStatus.value());
    }

    @Test
    @DisplayName("Deve permitir mensagem nula")
    void devePermitirMensagemNula() {
        // When
        EntidadeNaoEncontradaException exception = new EntidadeNaoEncontradaException(null);

        // Then
        assertNull(exception.getMessage());
    }

    @Test
    @DisplayName("Deve permitir mensagem vazia")
    void devePermitirMensagemVazia() {
        // When
        EntidadeNaoEncontradaException exception = new EntidadeNaoEncontradaException("");

        // Then
        assertEquals("", exception.getMessage());
    }

    @Test
    @DisplayName("Deve ser possível capturar como NegocioException")
    void deveSerPossivelCapturarComoNegocioException() {
        // Given
        String mensagem = "Pagamento não encontrado";

        // When & Then
        assertThrows(NegocioException.class, () -> {
            throw new EntidadeNaoEncontradaException(mensagem);
        });
    }

    @Test
    @DisplayName("Deve preservar mensagem ao ser lançada e capturada")
    void devePreservarMensagemAoSerLancadaECapturada() {
        // Given
        String mensagem = "Pedido com ID 123 não encontrado";

        // When & Then
        NegocioException exception = assertThrows(EntidadeNaoEncontradaException.class, () -> {
            throw new EntidadeNaoEncontradaException(mensagem);
        });

        assertEquals(mensagem, exception.getMessage());
    }
}
