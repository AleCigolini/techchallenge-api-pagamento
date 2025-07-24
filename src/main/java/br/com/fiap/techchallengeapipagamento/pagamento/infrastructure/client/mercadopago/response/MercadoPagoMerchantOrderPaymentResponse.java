package br.com.fiap.techchallengeapipagamento.pagamento.infrastructure.client.mercadopago.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MercadoPagoMerchantOrderPaymentResponse {

    private Long id;
    @JsonProperty("transaction_amount")
    private Long transactionAmount;
    @JsonProperty("total_paid_amount")
    private Long totalPaidAmount;
    @JsonProperty("shipping_cost")
    private String shippingCost;
    @JsonProperty("currency_id")
    private String currencyId;
    private String status;
    @JsonProperty("status_detail")
    private String statusDetail;
    @JsonProperty("operation_type")
    private String operationType;
    @JsonProperty("date_approved")
    private String dateApproved;
    @JsonProperty("date_created")
    private String dateCreated;
    @JsonProperty("last_modified")
    private String lastModified;
    @JsonProperty("amount_refunded")
    private String amountRefunded;
}
