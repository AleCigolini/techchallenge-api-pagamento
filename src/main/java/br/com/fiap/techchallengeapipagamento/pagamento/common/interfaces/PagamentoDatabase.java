package br.com.fiap.techchallengeapipagamento.pagamento.common.interfaces;

import br.com.fiap.techchallengeapipagamento.pagamento.domain.Pagamento;

import java.util.List;
import java.util.Optional;

public interface PagamentoDatabase {

    Pagamento salvar(Pagamento pagamento);

    Optional<Pagamento> buscarPorId(String id);

    List<Pagamento> buscarPorCodigoPedido(String codigoPedido);

    List<Pagamento> buscarPorStatus(String status);

    Optional<Pagamento> buscarPagamentoPorPedidoEStatus(String codigoPedido, String status);

    Optional<Pagamento> atualizarStatusPagamento(String id, String novoStatus);
}
