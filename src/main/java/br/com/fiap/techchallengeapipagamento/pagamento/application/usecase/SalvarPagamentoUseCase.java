package br.com.fiap.techchallengeapipagamento.pagamento.application.usecase;

import br.com.fiap.techchallengeapipagamento.pagamento.domain.Pagamento;
import br.com.fiap.techchallengeapipagamento.pagamento.domain.Pedido;
import br.com.fiap.techchallengeapipagamento.pagamento.domain.StatusPagamentoEnum;

public interface SalvarPagamentoUseCase {

    Pagamento fazerPagamentoDoPedido(Pedido pedido);

    Pagamento atualizarStatusPagamento(String id, StatusPagamentoEnum novoStatus);
}
