package br.com.fiap.techchallengeapipagamento.pagamento.domain;

import br.com.fiap.techchallengeapipagamento.core.config.exception.StatusPagamentoInvalidoException;

public enum StatusPagamentoEnum {
    PENDENTE("Pendente"),
    APROVADO("Aprovado"),
    REJEITADO("Rejeitado"),
    CANCELADO("Cancelado");

    private final String status;

    StatusPagamentoEnum(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public static StatusPagamentoEnum fromString(String text) {
        for (StatusPagamentoEnum status : StatusPagamentoEnum.values()) {
            if (status.status.equalsIgnoreCase(text)) {
                return status;
            }
        }
        throw new StatusPagamentoInvalidoException("Status de pagamento inválido: " + text + ". Status válidos: PENDENTE, APROVADO, REJEITADO, CANCELADO");
    }
}
