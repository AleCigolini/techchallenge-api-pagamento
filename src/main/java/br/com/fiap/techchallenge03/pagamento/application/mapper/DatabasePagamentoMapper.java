package br.com.fiap.techchallenge03.pagamento.application.mapper;

import br.com.fiap.techchallenge03.pagamento.common.domain.entity.JpaPagamentoEntity;
import br.com.fiap.techchallenge03.pagamento.domain.Pagamento;

import java.util.List;

public interface DatabasePagamentoMapper {

    List<Pagamento> jpaPagamentosEntityParaPagamentos(List<JpaPagamentoEntity> jpaPagamentoEntities);

    JpaPagamentoEntity pagamentoParaJpaPagamentoEntity(Pagamento pagamento);

    Pagamento jpaPagamentoEntityParaPagamento(JpaPagamentoEntity jpaPagamentoEntity);
}