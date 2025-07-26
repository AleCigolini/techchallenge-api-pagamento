package br.com.fiap.techchallengeapipagamento.pagamento.application.usecase.impl;

import br.com.fiap.techchallengeapipagamento.pagamento.application.usecase.ConfirmarPagamentoPedidoUseCase;
import br.com.fiap.techchallengeapipagamento.pagamento.domain.Pagamento;
import br.com.fiap.techchallengeapipagamento.pagamento.infrastructure.client.pedido.PedidoClient;
import br.com.fiap.techchallengeapipagamento.pagamento.infrastructure.client.pedido.request.PagamentoRealizadoRequestDto;

public class ConfirmarPagamentoPedidoUseCaseImpl implements ConfirmarPagamentoPedidoUseCase {

    private final PedidoClient pedidoClient;

    public ConfirmarPagamentoPedidoUseCaseImpl(
            PedidoClient pedidoClient
    ) {
        this.pedidoClient = pedidoClient;
    }

    @Override
    public void confirmarPagamentoPedido(Pagamento pagamento) {

        try {
            PagamentoRealizadoRequestDto request = new PagamentoRealizadoRequestDto();
            request.setCodigoPagamento(pagamento.getId());

            this.pedidoClient.confirmarPagamentoRecebido(
                    pagamento.getCodigoPedido(),
                    request
            );

        } catch (Exception ex) {
            System.out.printf(
                    "PedidoClient Error. Status: %s, Content: %s",
                    ex.getCause(), ex.getMessage());
        }
    }
}