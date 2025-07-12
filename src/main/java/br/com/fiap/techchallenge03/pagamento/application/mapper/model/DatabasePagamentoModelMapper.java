package br.com.fiap.techchallenge03.pagamento.application.mapper.model;

import br.com.fiap.techchallenge03.pagamento.application.mapper.DatabasePagamentoMapper;
import br.com.fiap.techchallenge03.pagamento.common.domain.entity.JpaPagamentoEntity;
import br.com.fiap.techchallenge03.pagamento.domain.Pagamento;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@AllArgsConstructor
public class DatabasePagamentoModelMapper implements DatabasePagamentoMapper {

    private ModelMapper modelMapper;

    @Override
    public List<Pagamento> jpaPagamentosEntityParaPagamentos(List<JpaPagamentoEntity> jpaPagamentoEntities) {
        return jpaPagamentoEntities.stream().map(jpaPagamentoEntity -> modelMapper.map(jpaPagamentoEntity, Pagamento.class)).toList();
    }

    @Override
    public JpaPagamentoEntity pagamentoParaJpaPagamentoEntity(Pagamento pagamento) {
        return modelMapper.map(pagamento, JpaPagamentoEntity.class);
    }

    @Override
    public Pagamento jpaPagamentoEntityParaPagamento(JpaPagamentoEntity jpaPagamentoEntity) {
        return modelMapper.map(jpaPagamentoEntity, Pagamento.class);
    }
}