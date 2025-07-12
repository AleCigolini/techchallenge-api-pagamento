package br.com.fiap.techchallenge03.pagamento.infrastructure.client.mercadopago.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class MercadoPagoMerchantOrderResponse {

    private Long id;
    private String status;
    @JsonProperty("external_reference")
    private String externalReference;
//    @JsonProperty("preference_id")
//    private String preferenceId;
    private List<MercadoPagoMerchantOrderPaymentResponse> payments = new ArrayList<>();
//    //    private List<MercadoPagoMerchantOrderShipmentResponse> shipments;
//    //    private List<MercadoPagoMerchantOrderPayoutResponse> payouts;
//    //    private MercadoPagoMerchantOrderCollectorResponse collector;
//    private String marketplace;
//    @JsonProperty("notification_url")
//    private String notificationUrl;
//    @JsonProperty("date_created")
//    private String dateCreated;
//    @JsonProperty("last_updated")
//    private String lastUpdated;
//    @JsonProperty("sponsor_id")
//    private String sponsorId;
//    @JsonProperty("shipping_cost")
//    private Long shippingCost;
//    @JsonProperty("total_amount")
//    private Long totalAmount;
//    @JsonProperty("site_id")
//    private String siteId;
//    @JsonProperty("paid_amount")
//    private Long paidAmount;
//    @JsonProperty("refunded_amount")
//    private Long refundedAmount;
//    //    private MercadoPagoMerchantOrderPayerResponse payer;
//    //    private List<MercadoPagoMerchantOrderItemsResponse> items;
//    private String cancelled;
//    @JsonProperty("additional_info")
//    private String additionalInfo;
//    @JsonProperty("application_id")
//    private String applicatioId;
//    @JsonProperty("is_test")
//    private String isTest;
//    @JsonProperty("order_status")
//    private String orderStatus;
//    @JsonProperty("client_id")
//    private String clientId;
}