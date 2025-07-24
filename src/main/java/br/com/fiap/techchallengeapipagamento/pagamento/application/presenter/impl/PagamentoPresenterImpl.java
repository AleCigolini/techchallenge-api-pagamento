package br.com.fiap.techchallengeapipagamento.pagamento.application.presenter.impl;

import br.com.fiap.techchallengeapipagamento.pagamento.application.presenter.PagamentoPresenter;
import br.com.fiap.techchallengeapipagamento.pagamento.common.domain.dto.response.PagamentoResponseDto;
import br.com.fiap.techchallengeapipagamento.pagamento.domain.Pagamento;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@AllArgsConstructor
public class PagamentoPresenterImpl implements PagamentoPresenter {

    private final ModelMapper modelMapper;

    @Override
    public PagamentoResponseDto pagamentoParaPagamentoResponseDTO(Pagamento pagamento) {
        return modelMapper.map(pagamento, PagamentoResponseDto.class);
    }

    @Override
    public List<PagamentoResponseDto> pagamentosParaPagamentoResponseDTOs(List<Pagamento> pagamentos) {
        return pagamentos.stream().map(pedido -> modelMapper.map(pedido, PagamentoResponseDto.class)).toList();
    }
}