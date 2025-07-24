package br.com.fiap.techchallenge03.pagamento.infrastructure.client.mercadopago.mapper.impl;

import br.com.fiap.techchallenge03.pagamento.domain.Pedido;
import br.com.fiap.techchallenge03.pagamento.infrastructure.client.mercadopago.mapper.MercadoPagoOrderRequestMapper;
import br.com.fiap.techchallenge03.pagamento.infrastructure.client.mercadopago.request.MercadoPagoOrderItemRequest;
import br.com.fiap.techchallenge03.pagamento.infrastructure.client.mercadopago.request.MercadoPagoOrderRequest;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Component
public class MercadoPagoOrderRequestMapperImpl implements MercadoPagoOrderRequestMapper {

    @Override
    public MercadoPagoOrderRequest pedidoParaMercadoPagoOrderItemRequest(Pedido pedido) {

        List<MercadoPagoOrderItemRequest> mercadoPagoOrderItemRequests = new ArrayList<>();

        pedido.getProdutos().forEach(produto -> {
            BigDecimal totalPrecoItem = produto.getPreco().multiply(BigDecimal.valueOf(produto.getQuantidade()));
            MercadoPagoOrderItemRequest mercadoPagoOrderItemRequest = MercadoPagoOrderItemRequest.builder()
                    .skuNumber(produto.getCodigoProduto())
                    .category(produto.getCategoria())
                    .title(produto.getNome())
                    .description(produto.getDescricao())
                    .quantity(produto.getQuantidade())
                    .unitPrice(produto.getPreco())
                    .unitMeasure("unit")
                    .totalAmount(totalPrecoItem)
                    .build();
            mercadoPagoOrderItemRequests.add(mercadoPagoOrderItemRequest);
        });

        return MercadoPagoOrderRequest.builder()
                .externalReference(pedido.getCodigo())
                .title(pedido.getCodigo())
                .description(pedido.getObservacao())
                .totalAmount(pedido.getPreco())
                .items(mercadoPagoOrderItemRequests)
                .build();
    }
}