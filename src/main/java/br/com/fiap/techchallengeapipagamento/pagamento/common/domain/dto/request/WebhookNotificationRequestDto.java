package br.com.fiap.techchallengeapipagamento.pagamento.common.domain.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class WebhookNotificationRequestDto {

    private String action;
    @JsonProperty("application_id")
    private String applicationId;
    @JsonProperty("date_created")
    private String dateCreated;
    private String id;
    @JsonProperty("live_mode")
    private String liveMode;
    private String status;
    private String type;
    @JsonProperty("user_id")
    private Long userId;
    private String version;
    private WebhookDataRequestDto data;
}
