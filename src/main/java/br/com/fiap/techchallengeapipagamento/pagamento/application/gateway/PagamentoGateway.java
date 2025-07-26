package br.com.fiap.techchallengeapipagamento.pagamento.application.gateway;

import br.com.fiap.techchallengeapipagamento.pagamento.domain.Pagamento;

import java.awt.image.BufferedImage;
import java.util.List;
import java.util.Optional;

public interface PagamentoGateway {

    Pagamento salvar(Pagamento pagamento);

    List<Pagamento> buscarPorPedidoId(String codigoPedido);

    List<Pagamento> buscarPorStatus(String status);

    Optional<Pagamento> buscarPorId(String id);

    BufferedImage gerarImagemCodigoQRCaixa();

    Optional<Pagamento> buscarPagamentoPorPedidoEStatus(String codigoPedido, String status);

    Optional<Pagamento> atualizarStatusPagamento(String id, String novoStatus);
}
