package br.com.fiap.techchallengeapipagamento.pagamento.application.mapper.model;

import br.com.fiap.techchallengeapipagamento.pagamento.application.mapper.DatabasePagamentoMapper;
import br.com.fiap.techchallengeapipagamento.pagamento.domain.Pagamento;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@AllArgsConstructor
public class DatabasePagamentoModelMapper implements DatabasePagamentoMapper {

    private ModelMapper modelMapper;

    @Override
    public List<Pagamento> jpaPagamentosEntityParaPagamentos(List<Pagamento> jpaPagamentoEntities) {
        return jpaPagamentoEntities.stream().map(jpaPagamentoEntity -> modelMapper.map(jpaPagamentoEntity, Pagamento.class)).toList();
    }

    @Override
    public Pagamento pagamentoParaJpaPagamentoEntity(Pagamento pagamento) {
        return modelMapper.map(pagamento, Pagamento.class);
    }

    @Override
    public Pagamento jpaPagamentoEntityParaPagamento(Pagamento jpaPagamentoEntity) {
        return modelMapper.map(jpaPagamentoEntity, Pagamento.class);
    }
}