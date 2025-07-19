package br.com.fiap.techchallenge03.pagamento.domain;

import br.com.fiap.techchallenge03.core.utils.domain.DominioBase;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class Produto extends DominioBase {

    private String id;
    private String nome;
    private String descricao;
    private String categoria;
    private BigDecimal preco;
    private Long quantidade;
    private String observacao;
}