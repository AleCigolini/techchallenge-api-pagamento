package br.com.fiap.techchallenge03.pagamento.application.usecase;

import br.com.fiap.techchallenge03.pagamento.domain.Pagamento;

import java.util.List;

public interface ConsultarPagamentoUseCase {

    List<Pagamento> buscarPorPedidoId(String pedidoId);

    List<Pagamento> buscarPorStatus(String status);
}