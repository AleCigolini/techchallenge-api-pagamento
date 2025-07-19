package br.com.fiap.techchallenge03.pagamento.application.usecase.impl;

import br.com.fiap.techchallenge03.pagamento.application.gateway.PagamentoGateway;
import br.com.fiap.techchallenge03.pagamento.application.usecase.CriarPedidoMercadoPagoUseCase;
import br.com.fiap.techchallenge03.pagamento.application.usecase.SalvarPagamentoUseCase;
import br.com.fiap.techchallenge03.pagamento.domain.Pagamento;
import br.com.fiap.techchallenge03.pagamento.domain.Pedido;
import br.com.fiap.techchallenge03.pagamento.domain.StatusPagamentoEnum;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.transaction.annotation.Transactional;

public class SalvarPagamentoUseCaseImpl implements SalvarPagamentoUseCase {

    private final PagamentoGateway pagamentoGateway;
    private final CriarPedidoMercadoPagoUseCase criarPedidoMercadoPagoUseCase;

    @Value("${mercado-pago.ativo}")
    private boolean isMercadoPagoAtivo;

    public SalvarPagamentoUseCaseImpl(
            PagamentoGateway pagamentoGateway,
            CriarPedidoMercadoPagoUseCase criarPedidoMercadoPagoUseCase) {
        this.pagamentoGateway = pagamentoGateway;
        this.criarPedidoMercadoPagoUseCase = criarPedidoMercadoPagoUseCase;
    }

    @Override
    @Transactional
    public Pagamento fazerPagamentoDoPedido(Pedido pedido) {

        boolean criouPedidoMercadoPago = criarPedidoMercadoPagoUseCase.criarPedidoMercadoPago(pedido);

        if (isMercadoPagoAtivo) {
            Pagamento pagamento = new Pagamento();
            pagamento.setPreco(pedido.getPreco());
            pagamento.setCodigoPedido(pedido.getId());

            pagamento.setStatus(!criouPedidoMercadoPago ?
                    StatusPagamentoEnum.REJEITADO.toString() :
                    StatusPagamentoEnum.PENDENTE.toString());

            return pagamentoGateway.salvar(pagamento);
        }
        return null;
    }

    @Override
    public Pagamento atualizarStatusPagamento(String id, String novoStatus) {
        return pagamentoGateway.atualizarStatusPagamento(id, novoStatus)
                .orElse(null);
    }
}