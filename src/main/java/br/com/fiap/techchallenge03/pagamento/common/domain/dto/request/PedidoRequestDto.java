package br.com.fiap.techchallenge03.pagamento.common.domain.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class PedidoRequestDto {

    @NotBlank
    private String id;

    @NotNull
    private BigDecimal preco;

    @NotBlank
    private String observacao;

    @NotEmpty
    private List<ProdutoRequestDto> produtos;
}

