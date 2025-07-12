package br.com.fiap.techchallenge03.pagamento.application.usecase;

import br.com.fiap.techchallenge03.pagamento.domain.Pagamento;
import br.com.fiap.techchallenge03.pagamento.domain.Pedido;

public interface SalvarPagamentoUseCase {

    Pagamento fazerPagamentoDoPedido(Pedido pedido);
}
