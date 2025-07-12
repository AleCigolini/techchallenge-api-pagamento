package br.com.fiap.techchallenge03.pagamento.application.usecase.impl;

import br.com.fiap.techchallenge03.core.config.properties.MercadoPagoProperties;
import br.com.fiap.techchallenge03.pagamento.application.usecase.CriarPedidoMercadoPagoUseCase;
import br.com.fiap.techchallenge03.pagamento.domain.Pedido;
import br.com.fiap.techchallenge03.pagamento.infrastructure.client.mercadopago.MercadoPagoCodigoQRClient;
import br.com.fiap.techchallenge03.pagamento.infrastructure.client.mercadopago.mapper.MercadoPagoOrderRequestMapper;
import br.com.fiap.techchallenge03.pagamento.infrastructure.client.mercadopago.request.MercadoPagoOrderRequest;

public class CriarPedidoMercadoPagoUseCaseImpl implements CriarPedidoMercadoPagoUseCase {

    private final MercadoPagoOrderRequestMapper mercadoPagoOrderRequestMapper;
    private final MercadoPagoCodigoQRClient mercadoPagoCodigoQRClient;
    private final MercadoPagoProperties mercadoPagoProperties;

    public CriarPedidoMercadoPagoUseCaseImpl(
            MercadoPagoOrderRequestMapper mercadoPagoOrderRequestMapper,
            MercadoPagoCodigoQRClient mercadoPagoCodigoQRClient,
            MercadoPagoProperties mercadoPagoProperties
    ) {
        this.mercadoPagoOrderRequestMapper = mercadoPagoOrderRequestMapper;
        this.mercadoPagoCodigoQRClient = mercadoPagoCodigoQRClient;
        this.mercadoPagoProperties = mercadoPagoProperties;
    }


    @Override
    public boolean criarPedidoMercadoPago(Pedido pedido) {
        try {
            MercadoPagoOrderRequest mercadoPagoOrderRequest = mercadoPagoOrderRequestMapper.pedidoParaMercadoPagoOrderItemRequest(pedido);

            mercadoPagoCodigoQRClient.pedidosPresenciaisV2(
                    mercadoPagoProperties.getUserId(),
                    mercadoPagoProperties.getExternalStoreId(),
                    mercadoPagoProperties.getExternalPosId(),
                    mercadoPagoProperties.getAuthHeader(),
                    mercadoPagoOrderRequest);

            return true;
        } catch (Exception ex) {
            System.out.printf(
                    "MercadoPago Error. Status: %s, Content: %s",
                    ex.getCause(), ex.getMessage());
            return false;
        }
    }
}