package br.com.fiap.techchallenge03.pagamento.common.domain.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Data
public class PagamentoResponseDto {

    @Schema(description = "Identificador único do pagamento", example = "e389406d-5531-4acf-a354-be5cc46a8cb1")
    private String id;

    @Schema(description = "Código do pedido")
    private String codigoPedido;

    @Schema(description = "Valor do pagamento")
    private BigDecimal preco;

    @Schema(description = "Status do pagamento")
    private String status;

    @Schema(description = "Data de criação do pagamento", example = "2023-01-01T10:00:00Z")
    private OffsetDateTime dataCriacao;
}