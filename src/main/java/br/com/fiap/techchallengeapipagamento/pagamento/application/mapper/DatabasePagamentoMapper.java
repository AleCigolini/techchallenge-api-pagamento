package br.com.fiap.techchallengeapipagamento.pagamento.application.mapper;

import br.com.fiap.techchallengeapipagamento.pagamento.domain.Pagamento;

import java.util.List;

public interface DatabasePagamentoMapper {

    List<Pagamento> jpaPagamentosEntityParaPagamentos(List<Pagamento> jpaPagamentoEntities);

    Pagamento pagamentoParaJpaPagamentoEntity(Pagamento pagamento);

    Pagamento jpaPagamentoEntityParaPagamento(Pagamento jpaPagamentoEntity);
}