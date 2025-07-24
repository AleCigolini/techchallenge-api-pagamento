package br.com.fiap.techchallengeapipagamento.pagamento.infrastructure.client.mercadopago.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class MercadoPagoOrderItemRequest {

    @JsonProperty("sku_number")
    private final String skuNumber;
    private final String category;
    private final String title;
    private final String description;
    @JsonProperty("unit_price")
    private final BigDecimal unitPrice;
    private final Long quantity;
    @JsonProperty("unit_measure")
    private final String unitMeasure;
    @JsonProperty("total_amount")
    private final BigDecimal totalAmount;
}