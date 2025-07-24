package br.com.fiap.techchallenge03.pagamento.application.usecase;


import br.com.fiap.techchallenge03.pagamento.domain.Pagamento;

public interface ConfirmarPagamentoPedidoUseCase {

    void confirmarPagamentoPedido(Pagamento pagamento);
}
