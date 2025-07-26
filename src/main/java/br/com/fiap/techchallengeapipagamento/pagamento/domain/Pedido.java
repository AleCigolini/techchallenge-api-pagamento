package br.com.fiap.techchallengeapipagamento.pagamento.domain;

import br.com.fiap.techchallengeapipagamento.core.utils.domain.DominioBase;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class Pedido extends DominioBase {

    private String codigoPedido;
    private String codigo;
    private String codigoCliente;
    private BigDecimal preco;
    private String codigoPagamento;
    private String observacao;
    private List<Produto> produtos;
    private List<Pagamento> pagamentos;
}