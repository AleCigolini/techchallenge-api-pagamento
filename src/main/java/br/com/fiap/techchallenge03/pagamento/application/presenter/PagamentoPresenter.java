package br.com.fiap.techchallenge03.pagamento.application.presenter;

import br.com.fiap.techchallenge03.pagamento.common.domain.dto.response.PagamentoResponseDto;
import br.com.fiap.techchallenge03.pagamento.domain.Pagamento;

import java.util.List;

public interface PagamentoPresenter {

    PagamentoResponseDto pagamentoParaPagamentoResponseDTO(Pagamento pagamento);

    List<PagamentoResponseDto> pagamentosParaPagamentoResponseDTOs(List<Pagamento> pagamentos);
}