package br.com.fiap.techchallengeapipagamento.core.config.properties;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Getter
public class MercadoPagoProperties {

    @Value("#{new Long('${client.mercado-pago.user_id}')}")
    private Long userId;

    @Value("${client.mercado-pago.pos_id}")
    private String posId;

    @Value("${client.mercado-pago.external_store_id}")
    private String externalStoreId;

    @Value("${client.mercado-pago.external_pos_id}")
    private String externalPosId;

    @Value("${client.mercado-pago.auth_header}")
    private String authHeader;
}
