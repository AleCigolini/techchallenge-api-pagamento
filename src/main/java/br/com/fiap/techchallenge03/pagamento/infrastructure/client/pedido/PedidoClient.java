package br.com.fiap.techchallenge03.pagamento.infrastructure.client.pedido;

import br.com.fiap.techchallenge03.pagamento.infrastructure.client.pedido.request.PagamentoRealizadoRequestDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "PedidoClient", url = "${client.pedido.base-url}")
public interface PedidoClient {

    @PatchMapping("/recebido/{codigoPedido}")
    ResponseEntity<Object> confirmarPagamentoRecebido(
            @PathVariable("codigoPedido") String codigoPedido,
            @RequestBody PagamentoRealizadoRequestDto request);
}