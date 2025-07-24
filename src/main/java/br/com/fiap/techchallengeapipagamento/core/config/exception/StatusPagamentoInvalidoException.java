package br.com.fiap.techchallengeapipagamento.core.config.exception;

public class StatusPagamentoInvalidoException extends RuntimeException {
    public StatusPagamentoInvalidoException(String message) {
        super(message);
    }
}
