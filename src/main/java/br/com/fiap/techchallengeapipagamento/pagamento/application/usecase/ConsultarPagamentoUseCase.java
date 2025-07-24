package br.com.fiap.techchallengeapipagamento.pagamento.application.usecase;

import br.com.fiap.techchallengeapipagamento.pagamento.domain.Pagamento;

import java.util.List;

public interface ConsultarPagamentoUseCase {

    List<Pagamento> buscarPorPedidoId(String pedidoId);

    List<Pagamento> buscarPorStatus(String status);
}