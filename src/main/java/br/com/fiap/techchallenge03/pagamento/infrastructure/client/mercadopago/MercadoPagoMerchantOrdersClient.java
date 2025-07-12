package br.com.fiap.techchallenge03.pagamento.infrastructure.client.mercadopago;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "MercadoPagoMerchantOrdersClient", url = "https://api.mercadopago.com/merchant_orders")
public interface MercadoPagoMerchantOrdersClient {

    @GetMapping("/{payment_id}")
    ResponseEntity<Object> obterPagamento(
            @PathVariable("payment_id") String paymentId,
            @RequestHeader("Authorization") String authHeader);
}