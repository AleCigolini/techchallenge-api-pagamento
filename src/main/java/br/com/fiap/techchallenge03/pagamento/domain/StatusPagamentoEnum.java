package br.com.fiap.techchallenge03.pagamento.domain;

import com.fasterxml.jackson.annotation.JsonValue;

public enum StatusPagamentoEnum {

    PENDENTE("Pendente"),
    APROVADO("Aprovado"),
    FALHA("Falha"),
    CANCELADO("Cancelado");

    private final String status;

    StatusPagamentoEnum(String status) {
        this.status = status;
    }

    @JsonValue
    public String getStatus() {
        return status;
    }
}