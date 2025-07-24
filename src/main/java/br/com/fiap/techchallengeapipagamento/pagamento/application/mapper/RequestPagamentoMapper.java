package br.com.fiap.techchallengeapipagamento.pagamento.application.mapper;

import br.com.fiap.techchallengeapipagamento.pagamento.common.domain.dto.request.PedidoRequestDto;
import br.com.fiap.techchallengeapipagamento.pagamento.domain.Pedido;

public interface RequestPagamentoMapper {

    Pedido pedidoRequestDtoParaPedido(PedidoRequestDto pedidoRequestDto);
}