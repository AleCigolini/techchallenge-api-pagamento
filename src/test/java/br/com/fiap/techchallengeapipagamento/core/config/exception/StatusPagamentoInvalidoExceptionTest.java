package br.com.fiap.techchallengeapipagamento.core.config.exception;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("StatusPagamentoInvalidoException Tests")
class StatusPagamentoInvalidoExceptionTest {

    @Test
    @DisplayName("Deve criar exceção com mensagem")
    void deveCriarExcecaoComMensagem() {
        // Given
        String mensagem = "Status de pagamento inválido";

        // When
        StatusPagamentoInvalidoException exception = new StatusPagamentoInvalidoException(mensagem);

        // Then
        assertEquals(mensagem, exception.getMessage());
    }

    @Test
    @DisplayName("Deve herdar de RuntimeException")
    void deveHerdarDeRuntimeException() {
        // Given
        StatusPagamentoInvalidoException exception = new StatusPagamentoInvalidoException("Teste");

        // Then
        assertTrue(exception instanceof RuntimeException);
    }

    @Test
    @DisplayName("Deve permitir mensagem nula")
    void devePermitirMensagemNula() {
        // When
        StatusPagamentoInvalidoException exception = new StatusPagamentoInvalidoException(null);

        // Then
        assertNull(exception.getMessage());
    }

    @Test
    @DisplayName("Deve permitir mensagem vazia")
    void devePermitirMensagemVazia() {
        // When
        StatusPagamentoInvalidoException exception = new StatusPagamentoInvalidoException("");

        // Then
        assertEquals("", exception.getMessage());
    }

    @Test
    @DisplayName("Deve ser possível capturar como RuntimeException")
    void deveSerPossivelCapturarComoRuntimeException() {
        // Given
        String mensagem = "Erro de status";

        // When & Then
        assertThrows(RuntimeException.class, () -> {
            throw new StatusPagamentoInvalidoException(mensagem);
        });
    }

    @Test
    @DisplayName("Deve preservar mensagem ao ser lançada e capturada")
    void devePreservarMensagemAoSerLancadaECapturada() {
        // Given
        String mensagem = "Status INVALID não é permitido";

        // When & Then
        RuntimeException exception = assertThrows(StatusPagamentoInvalidoException.class, () -> {
            throw new StatusPagamentoInvalidoException(mensagem);
        });

        assertEquals(mensagem, exception.getMessage());
    }
}
