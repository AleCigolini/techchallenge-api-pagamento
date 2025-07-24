package br.com.fiap.techchallengeapipagamento.pagamento.domain;

import com.fasterxml.jackson.annotation.JsonValue;

public enum StatusPedidoEnum {

    ABERTO("Aberto"),
    RECEBIDO("Recebido"),
    EM_PREPARACAO("Em preparação"),
    PRONTO("Pronto"),
    FINALIZADO("Finalizado"),
    CANCELADO("Cancelado");

    private final String status;

    StatusPedidoEnum(String status) {
        this.status = status;
    }

    @JsonValue
    public String getStatus() {
        return status;
    }

    public static StatusPedidoEnum fromStringStatus(String status) {
        for (StatusPedidoEnum valoresStatusPedidoEnum : StatusPedidoEnum.values()) {
            if (valoresStatusPedidoEnum.getStatus().equals(status)) {
                return valoresStatusPedidoEnum;
            }
        }
        return null;
    }

    public static StatusPedidoEnum fromStatus(String status) {
        for (StatusPedidoEnum valoresStatusPedidoEnum : StatusPedidoEnum.values()) {
            if (valoresStatusPedidoEnum.toString().equals(status)) {
                return valoresStatusPedidoEnum;
            }
        }
        return null;
    }
}