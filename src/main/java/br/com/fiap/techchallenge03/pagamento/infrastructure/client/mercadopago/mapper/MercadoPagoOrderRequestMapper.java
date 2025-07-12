package br.com.fiap.techchallenge03.pagamento.infrastructure.client.mercadopago.mapper;

import br.com.fiap.techchallenge03.pagamento.domain.Pedido;
import br.com.fiap.techchallenge03.pagamento.infrastructure.client.mercadopago.request.MercadoPagoOrderRequest;

public interface MercadoPagoOrderRequestMapper {

    MercadoPagoOrderRequest pedidoParaMercadoPagoOrderItemRequest(Pedido pedido);
}