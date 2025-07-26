package br.com.fiap.techchallengeapipagamento.pagamento.infrastructure.client.mercadopago;

import br.com.fiap.techchallengeapipagamento.pagamento.infrastructure.client.mercadopago.response.MercadoPagoPosResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "MercadoPagoPosClient", url = "https://api.mercadopago.com")
public interface MercadoPagoPosClient {

    @GetMapping("/pos/{pos_id}")
    ResponseEntity<MercadoPagoPosResponse> obterCaixa(
            @PathVariable("pos_id") String posId,
            @RequestHeader("Authorization") String authHeader);
}