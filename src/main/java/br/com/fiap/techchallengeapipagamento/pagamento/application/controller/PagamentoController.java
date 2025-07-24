package br.com.fiap.techchallengeapipagamento.pagamento.application.controller;

import br.com.fiap.techchallengeapipagamento.pagamento.common.domain.dto.request.PedidoRequestDto;
import br.com.fiap.techchallengeapipagamento.pagamento.common.domain.dto.response.PagamentoResponseDto;

import java.awt.image.BufferedImage;
import java.util.List;

public interface PagamentoController {

    List<PagamentoResponseDto> buscarPorPedidoId(String pedidoId);

    BufferedImage gerarImagemCodigoQRCaixa();

    PagamentoResponseDto fazerPagamentoDoPedido(PedidoRequestDto pedidoRequestDTO);

    List<PagamentoResponseDto> buscarPorStatus(String status);

    PagamentoResponseDto atualizarStatusPagamento(String id, String novoStatus);
}