package br.com.fiap.techchallengeapipagamento.pagamento.application.usecase.impl;

import br.com.fiap.techchallengeapipagamento.pagamento.application.gateway.PagamentoGateway;
import br.com.fiap.techchallengeapipagamento.pagamento.application.usecase.ConsultarPagamentoUseCase;
import br.com.fiap.techchallengeapipagamento.pagamento.domain.Pagamento;

import java.util.List;

public class ConsultarPagamentoUseCaseImpl implements ConsultarPagamentoUseCase {

    private final PagamentoGateway pagamentoGateway;

    public ConsultarPagamentoUseCaseImpl(PagamentoGateway pagamentoGateway) {
        this.pagamentoGateway = pagamentoGateway;
    }

    @Override
    public List<Pagamento> buscarPorPedidoId(String pedidoId) {
        return pagamentoGateway.buscarPorPedidoId(pedidoId);
    }

    @Override
    public List<Pagamento> buscarPorStatus(String status) {
        return pagamentoGateway.buscarPorStatus(status);
    }
}