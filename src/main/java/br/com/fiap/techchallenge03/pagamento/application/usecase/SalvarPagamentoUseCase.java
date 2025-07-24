package br.com.fiap.techchallenge03.pagamento.application.usecase;

import br.com.fiap.techchallenge03.pagamento.domain.Pagamento;
import br.com.fiap.techchallenge03.pagamento.domain.Pedido;
import br.com.fiap.techchallenge03.pagamento.domain.StatusPagamentoEnum;

public interface SalvarPagamentoUseCase {

    Pagamento fazerPagamentoDoPedido(Pedido pedido);

    Pagamento atualizarStatusPagamento(String id, StatusPagamentoEnum novoStatus);
}
