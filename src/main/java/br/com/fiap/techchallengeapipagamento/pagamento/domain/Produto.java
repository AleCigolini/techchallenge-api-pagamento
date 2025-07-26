package br.com.fiap.techchallengeapipagamento.pagamento.domain;

import br.com.fiap.techchallengeapipagamento.core.utils.domain.DominioBase;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class Produto extends DominioBase {

    private String codigoProduto;
    private String nome;
    private String descricao;
    private String categoria;
    private BigDecimal preco;
    private Long quantidade;
    private String observacao;
}