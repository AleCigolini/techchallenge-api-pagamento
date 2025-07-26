package br.com.fiap.techchallengeapipagamento.pagamento.infrastructure.client.mercadopago.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
public class MercadoPagoOrderRequest {

    @JsonProperty("external_reference")
    private final String externalReference;
    private final String title;
    private final String description;
    @JsonProperty("notification_url")
    private final String notificationUrl;
    @JsonProperty("total_amount")
    private final BigDecimal totalAmount;
    private final List<MercadoPagoOrderItemRequest> items;
}