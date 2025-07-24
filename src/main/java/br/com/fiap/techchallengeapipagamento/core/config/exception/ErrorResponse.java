package br.com.fiap.techchallengeapipagamento.core.config.exception;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ErrorResponse {
    private String code;
    private String message;
}
