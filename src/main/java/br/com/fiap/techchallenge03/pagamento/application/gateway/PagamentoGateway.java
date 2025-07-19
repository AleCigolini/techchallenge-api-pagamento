package br.com.fiap.techchallenge03.pagamento.application.gateway;

import br.com.fiap.techchallenge03.pagamento.domain.Pagamento;

import java.awt.image.BufferedImage;
import java.util.List;
import java.util.Optional;

public interface PagamentoGateway {

    Pagamento salvar(Pagamento pagamento);

    List<Pagamento> buscarPagamentosPorPedidoId(String codigoPedido);

    List<Pagamento> buscarPagamentosPorStatus(String status);

    Optional<Pagamento> buscarPorId(String id);

    List<Pagamento> buscarPorStatus(String status);

    BufferedImage gerarImagemCodigoQRCaixa();
}
