package br.com.fiap.techchallenge03.pagamento.application.mapper;

import br.com.fiap.techchallenge03.pagamento.common.domain.dto.request.PedidoRequestDto;
import br.com.fiap.techchallenge03.pagamento.domain.Pedido;

public interface RequestPagamentoMapper {

    Pedido pedidoRequestDtoParaPedido(PedidoRequestDto pedidoRequestDto);
}