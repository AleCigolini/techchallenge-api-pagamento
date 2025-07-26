package br.com.fiap.techchallengeapipagamento.pagamento.infrastructure.client.mercadopago;

import br.com.fiap.techchallengeapipagamento.pagamento.infrastructure.client.mercadopago.request.MercadoPagoOrderRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "MercadoPagoCodigoQRClient", url = "https://api.mercadopago.com/instore/qr/seller")
public interface MercadoPagoCodigoQRClient {

    @PutMapping("/collectors/{user_id}/stores/{external_store_id}/pos/{external_pos_id}/orders")
    ResponseEntity<Object> pedidosPresenciaisV2(
            @PathVariable("user_id") Long userId,
            @PathVariable("external_store_id") String externalStoreId,
            @PathVariable("external_pos_id") String externalPosId,
            @RequestHeader("Authorization") String authHeader,
            @RequestBody MercadoPagoOrderRequest order);
}