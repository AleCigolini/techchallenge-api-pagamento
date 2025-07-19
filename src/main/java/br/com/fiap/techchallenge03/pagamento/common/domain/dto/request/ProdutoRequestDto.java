package br.com.fiap.techchallenge03.pagamento.common.domain.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProdutoRequestDto {

    @NotBlank
    private String id;

    @NotBlank
    private String nome;

    @NotBlank
    private String descricao;

    @NotBlank
    private String categoria;

    @NotNull
    private BigDecimal preco;

    @NotBlank
    private String observacao;

    @Min(1)
    private Long quantidade;
}
