package br.com.fiap.techchallengeapipagamento.pagamento.common.domain.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
@Schema(description = "DTO para criação de pagamento de pedido")
public class PedidoRequestDto {

    @NotBlank
    private String codigoPedido;

    @NotBlank(message = "O código do pedido é obrigatório")
    @Schema(description = "Código do pedido", example = "PED123", required = true)
    private String codigo;

    @NotBlank
    private String observacao;

    @NotNull(message = "O preço é obrigatório")
    @Positive(message = "O preço deve ser maior que zero")
    @Schema(description = "Valor do pagamento", example = "59.90", required = true)
    private BigDecimal preco;

    @NotBlank
    private String codigoCliente;

    @NotEmpty
    private List<ProdutoRequestDto> produtos;
}
