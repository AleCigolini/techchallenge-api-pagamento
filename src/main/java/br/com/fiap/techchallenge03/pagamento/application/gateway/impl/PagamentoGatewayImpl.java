package br.com.fiap.techchallenge03.pagamento.application.gateway.impl;

import br.com.fiap.techchallenge03.pagamento.application.gateway.PagamentoGateway;
import br.com.fiap.techchallenge03.pagamento.common.interfaces.PagamentoDatabase;
import br.com.fiap.techchallenge03.pagamento.domain.Pagamento;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class PagamentoGatewayImpl implements PagamentoGateway {

    private final PagamentoDatabase pagamentoDatabase;

    @Override
    public Pagamento salvar(Pagamento pagamento) {
        return pagamentoDatabase.salvar(pagamento);
    }

    @Override
    public List<Pagamento> buscarPagamentosPorPedidoId(String codigoPedido) {
        return pagamentoDatabase.buscarPorCodigoPedido(codigoPedido);
    }

    @Override
    public Optional<Pagamento> buscarPorId(String id) {
        return pagamentoDatabase.buscarPorId(id);
    }

    @Override
    public List<Pagamento> buscarPorStatus(String status) {
        return pagamentoDatabase.buscarPorStatus(status);
    }
}
