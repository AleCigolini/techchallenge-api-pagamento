package br.com.fiap.techchallenge03.pagamento.application.gateway.impl;

import br.com.fiap.techchallenge03.pagamento.application.gateway.PagamentoGateway;
import br.com.fiap.techchallenge03.pagamento.application.mapper.DatabasePagamentoMapper;
import br.com.fiap.techchallenge03.pagamento.common.domain.entity.JpaPagamentoEntity;
import br.com.fiap.techchallenge03.pagamento.common.interfaces.PagamentoDatabase;
import br.com.fiap.techchallenge03.pagamento.domain.Pagamento;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@AllArgsConstructor
public class PagamentoGatewayImpl implements PagamentoGateway {

    private PagamentoDatabase pagamentoDatabase;
    private DatabasePagamentoMapper mapper;

    @Override
    public List<Pagamento> buscarPagamentosPorPedidoId(String pedidoId) {
        return mapper.jpaPagamentosEntityParaPagamentos(pagamentoDatabase.buscarPagamentosPorPedidoId(pedidoId));
    }

    @Override
    public Pagamento salvarPagamento(Pagamento pagamento) {
        final JpaPagamentoEntity jpaPagamentoEntity = mapper.pagamentoParaJpaPagamentoEntity(pagamento);
        return mapper.jpaPagamentoEntityParaPagamento(pagamentoDatabase.salvarPagamento(jpaPagamentoEntity));
    }
}