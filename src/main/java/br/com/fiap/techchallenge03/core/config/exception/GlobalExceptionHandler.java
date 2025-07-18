package br.com.fiap.techchallenge03.core.config.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(StatusPagamentoInvalidoException.class)
    public ResponseEntity<ErrorResponse> handleStatusPagamentoInvalido(StatusPagamentoInvalidoException ex) {
        ErrorResponse error = new ErrorResponse(
            "STATUS_PAGAMENTO_INVALIDO",
            ex.getMessage()
        );
        return ResponseEntity.badRequest().body(error);
    }
}
