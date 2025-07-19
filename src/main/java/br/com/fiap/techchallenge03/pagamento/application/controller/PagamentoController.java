package br.com.fiap.techchallenge03.pagamento.application.controller;

import br.com.fiap.techchallenge03.pagamento.common.domain.dto.request.PedidoRequestDto;
import br.com.fiap.techchallenge03.pagamento.common.domain.dto.response.PagamentoResponseDto;

import java.awt.image.BufferedImage;
import java.util.List;

public interface PagamentoController {

    List<PagamentoResponseDto> buscarPagamentosPorPedidoId(String pedidoId);

    BufferedImage gerarImagemCodigoQRCaixa();

    PagamentoResponseDto fazerPagamentoDoPedido(PedidoRequestDto pedidoRequestDTO);

    List<PagamentoResponseDto> buscarPagamentosPorStatus(String status);
}