package br.com.fiap.techchallengeapipagamento.pagamento.application.usecase.impl;

import br.com.fiap.techchallengeapipagamento.pagamento.application.gateway.PagamentoGateway;
import br.com.fiap.techchallengeapipagamento.pagamento.application.usecase.ConfirmarPagamentoPedidoUseCase;
import br.com.fiap.techchallengeapipagamento.pagamento.application.usecase.CriarPedidoMercadoPagoUseCase;
import br.com.fiap.techchallengeapipagamento.pagamento.application.usecase.SalvarPagamentoUseCase;
import br.com.fiap.techchallengeapipagamento.pagamento.domain.Pagamento;
import br.com.fiap.techchallengeapipagamento.pagamento.domain.Pedido;
import br.com.fiap.techchallengeapipagamento.pagamento.domain.StatusPagamentoEnum;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.transaction.annotation.Transactional;

public class SalvarPagamentoUseCaseImpl implements SalvarPagamentoUseCase {

    private final PagamentoGateway pagamentoGateway;
    private final CriarPedidoMercadoPagoUseCase criarPedidoMercadoPagoUseCase;
    private final ConfirmarPagamentoPedidoUseCase confirmarPagamentoPedidoUseCase;

    @Value("${mercado-pago.ativo}")
    private boolean isMercadoPagoAtivo;

    public SalvarPagamentoUseCaseImpl(
            PagamentoGateway pagamentoGateway,
            CriarPedidoMercadoPagoUseCase criarPedidoMercadoPagoUseCase,
            ConfirmarPagamentoPedidoUseCase confirmarPagamentoPedidoUseCase) {
        this.pagamentoGateway = pagamentoGateway;
        this.criarPedidoMercadoPagoUseCase = criarPedidoMercadoPagoUseCase;
        this.confirmarPagamentoPedidoUseCase = confirmarPagamentoPedidoUseCase;
    }

    @Override
    @Transactional
    public Pagamento fazerPagamentoDoPedido(Pedido pedido) {

        boolean criouPedidoMercadoPago = criarPedidoMercadoPagoUseCase.criarPedidoMercadoPago(pedido);

        Pagamento pagamento = new Pagamento();
        pagamento.setPreco(pedido.getPreco());
        pagamento.setCodigoPedido(pedido.getCodigoPedido());

        pagamento.setStatus(!criouPedidoMercadoPago ?
                StatusPagamentoEnum.REJEITADO.toString() :
                StatusPagamentoEnum.PENDENTE.toString());

        return pagamentoGateway.salvar(pagamento);
    }

    @Override
    public Pagamento atualizarStatusPagamento(String id, StatusPagamentoEnum novoStatus) {
        Pagamento pagamento = pagamentoGateway.atualizarStatusPagamento(id, novoStatus.getStatus())
                .orElse(null);

        if (pagamento != null && novoStatus == StatusPagamentoEnum.APROVADO) {
            confirmarPagamentoPedidoUseCase.confirmarPagamentoPedido(pagamento);
        }
        return pagamento;
    }
}