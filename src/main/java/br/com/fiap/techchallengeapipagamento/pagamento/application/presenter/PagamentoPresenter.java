package br.com.fiap.techchallengeapipagamento.pagamento.application.presenter;

import br.com.fiap.techchallengeapipagamento.pagamento.common.domain.dto.response.PagamentoResponseDto;
import br.com.fiap.techchallengeapipagamento.pagamento.domain.Pagamento;

import java.util.List;

public interface PagamentoPresenter {

    PagamentoResponseDto pagamentoParaPagamentoResponseDTO(Pagamento pagamento);

    List<PagamentoResponseDto> pagamentosParaPagamentoResponseDTOs(List<Pagamento> pagamentos);
}