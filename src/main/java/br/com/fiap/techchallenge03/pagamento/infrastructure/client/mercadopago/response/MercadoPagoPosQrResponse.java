package br.com.fiap.techchallenge03.pagamento.infrastructure.client.mercadopago.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MercadoPagoPosQrResponse {

    private final String image;
    @JsonProperty("template_document")
    private final String templateDocument;
    @JsonProperty("template_image")
    private final String templateImage;
}