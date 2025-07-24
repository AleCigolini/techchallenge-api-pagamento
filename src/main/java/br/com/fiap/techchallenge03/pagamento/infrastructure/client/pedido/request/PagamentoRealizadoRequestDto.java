package br.com.fiap.techchallenge03.pagamento.infrastructure.client.pedido.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class PagamentoRealizadoRequestDto {

    @NotBlank
    private String codigoPagamento;
}