package br.com.fiap.techchallengeapipagamento.pagamento.application.usecase;


import br.com.fiap.techchallengeapipagamento.pagamento.domain.Pagamento;

public interface ConfirmarPagamentoPedidoUseCase {

    void confirmarPagamentoPedido(Pagamento pagamento);
}
