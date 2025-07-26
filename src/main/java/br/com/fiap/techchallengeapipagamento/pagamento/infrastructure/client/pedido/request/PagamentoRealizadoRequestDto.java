package br.com.fiap.techchallengeapipagamento.pagamento.infrastructure.client.pedido.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class PagamentoRealizadoRequestDto {

    @NotBlank
    private String codigoPagamento;
}