package br.com.fiap.techchallengeapipagamento.pagamento.application.usecase;


import br.com.fiap.techchallengeapipagamento.pagamento.domain.Pedido;

public interface CriarPedidoMercadoPagoUseCase {

    boolean criarPedidoMercadoPago(Pedido pedido);
}
