package br.com.fiap.techchallengeapipagamento.pagamento.infrastructure.client.mercadopago.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MercadoPagoPosResponse {

    private final String id;
    private final MercadoPagoPosQrResponse qr;
    private final String status;
    @JsonProperty("date_created")
    private final String dateCreated;
    @JsonProperty("date_last_updated")
    private final String dateLastUpdated;
    private final String uuid;
    @JsonProperty("user_id")
    private final String userId;
    private final String name;
    @JsonProperty("fixed_amount")
    private final String fixedAmount;
    private final String category;
    @JsonProperty("store_id")
    private final String storeId;
    @JsonProperty("external_store_id")
    private final String externalStoreId;
    @JsonProperty("external_id")
    private final String externalId;
    private final String site;
    @JsonProperty("qr_code")
    private final String qrCode;
}