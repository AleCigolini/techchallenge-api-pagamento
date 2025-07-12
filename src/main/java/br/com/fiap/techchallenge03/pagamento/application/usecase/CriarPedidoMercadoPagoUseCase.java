package br.com.fiap.techchallenge03.pagamento.application.usecase;


import br.com.fiap.techchallenge03.pagamento.domain.Pedido;

public interface CriarPedidoMercadoPagoUseCase {

    boolean criarPedidoMercadoPago(Pedido pedido);
}
