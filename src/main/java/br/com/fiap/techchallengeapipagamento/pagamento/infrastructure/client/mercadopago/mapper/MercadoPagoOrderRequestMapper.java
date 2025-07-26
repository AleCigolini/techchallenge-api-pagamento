package br.com.fiap.techchallengeapipagamento.pagamento.infrastructure.client.mercadopago.mapper;

import br.com.fiap.techchallengeapipagamento.pagamento.domain.Pedido;
import br.com.fiap.techchallengeapipagamento.pagamento.infrastructure.client.mercadopago.request.MercadoPagoOrderRequest;

public interface MercadoPagoOrderRequestMapper {

    MercadoPagoOrderRequest pedidoParaMercadoPagoOrderItemRequest(Pedido pedido);
}