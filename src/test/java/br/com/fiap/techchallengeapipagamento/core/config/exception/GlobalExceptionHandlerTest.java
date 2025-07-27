package br.com.fiap.techchallengeapipagamento.core.config.exception;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("GlobalExceptionHandler Tests")
class GlobalExceptionHandlerTest {

    private GlobalExceptionHandler globalExceptionHandler;

    @BeforeEach
    void setUp() {
        globalExceptionHandler = new GlobalExceptionHandler();
    }

    @Test
    @DisplayName("Deve tratar StatusPagamentoInvalidoException e retornar BadRequest")
    void deveTratarStatusPagamentoInvalidoExceptionERetornarBadRequest() {
        // Given
        String mensagem = "Status de pagamento inválido: INVALID_STATUS";
        StatusPagamentoInvalidoException exception = new StatusPagamentoInvalidoException(mensagem);

        // When
        ResponseEntity<ErrorResponse> response = globalExceptionHandler.handleStatusPagamentoInvalido(exception);

        // Then
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("STATUS_PAGAMENTO_INVALIDO", response.getBody().getCode());
        assertEquals(mensagem, response.getBody().getMessage());
    }

    @Test
    @DisplayName("Deve tratar StatusPagamentoInvalidoException com mensagem nula")
    void deveTratarStatusPagamentoInvalidoExceptionComMensagemNula() {
        // Given
        StatusPagamentoInvalidoException exception = new StatusPagamentoInvalidoException(null);

        // When
        ResponseEntity<ErrorResponse> response = globalExceptionHandler.handleStatusPagamentoInvalido(exception);

        // Then
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("STATUS_PAGAMENTO_INVALIDO", response.getBody().getCode());
        assertNull(response.getBody().getMessage());
    }

    @Test
    @DisplayName("Deve tratar StatusPagamentoInvalidoException com mensagem vazia")
    void deveTratarStatusPagamentoInvalidoExceptionComMensagemVazia() {
        // Given
        String mensagemVazia = "";
        StatusPagamentoInvalidoException exception = new StatusPagamentoInvalidoException(mensagemVazia);

        // When
        ResponseEntity<ErrorResponse> response = globalExceptionHandler.handleStatusPagamentoInvalido(exception);

        // Then
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("STATUS_PAGAMENTO_INVALIDO", response.getBody().getCode());
        assertEquals(mensagemVazia, response.getBody().getMessage());
    }

    @Test
    @DisplayName("Deve sempre retornar código de erro STATUS_PAGAMENTO_INVALIDO")
    void deveSempreRetornarCodigoDeErroStatusPagamentoInvalido() {
        // Given
        StatusPagamentoInvalidoException exception1 = new StatusPagamentoInvalidoException("Mensagem 1");
        StatusPagamentoInvalidoException exception2 = new StatusPagamentoInvalidoException("Mensagem 2");

        // When
        ResponseEntity<ErrorResponse> response1 = globalExceptionHandler.handleStatusPagamentoInvalido(exception1);
        ResponseEntity<ErrorResponse> response2 = globalExceptionHandler.handleStatusPagamentoInvalido(exception2);

        // Then
        assertEquals("STATUS_PAGAMENTO_INVALIDO", response1.getBody().getCode());
        assertEquals("STATUS_PAGAMENTO_INVALIDO", response2.getBody().getCode());
    }

    @Test
    @DisplayName("Deve preservar mensagem original da exceção")
    void devePreservarMensagemOriginalDaExcecao() {
        // Given
        String mensagemOriginal = "Status CANCELADO não é válido para esta operação";
        StatusPagamentoInvalidoException exception = new StatusPagamentoInvalidoException(mensagemOriginal);

        // When
        ResponseEntity<ErrorResponse> response = globalExceptionHandler.handleStatusPagamentoInvalido(exception);

        // Then
        assertEquals(mensagemOriginal, response.getBody().getMessage());
    }
}
