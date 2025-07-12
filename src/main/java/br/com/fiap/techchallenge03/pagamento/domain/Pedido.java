package br.com.fiap.techchallenge03.pagamento.domain;

import br.com.fiap.techchallenge03.core.utils.domain.DominioBase;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class Pedido extends DominioBase {

    private String id;
    private String codigo;
    private String status;
    private BigDecimal preco;
    private String codigoPagamento;
    private String observacao;
    private List<Pagamento> pagamentos;
}