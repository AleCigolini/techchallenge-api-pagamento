package br.com.fiap.techchallengeapipagamento.pagamento.common.domain.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Data
public class PagamentoResponseDto {

    @Schema(description = "Identificador único do pagamento", example = "e389406d-5531-4acf-a354-be5cc46a8cb1")
    private String id;

    @Schema(description = "Código do pedido", example = "PED123")
    private String codigoPedido;

    @Schema(description = "Valor do pagamento", example = "59.90")
    private BigDecimal preco;

    @Schema(description = "Status do pagamento", example = "PENDENTE")
    private String status;
}