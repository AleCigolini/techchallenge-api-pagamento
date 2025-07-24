package br.com.fiap.techchallengeapipagamento.pagamento.application.controller.impl;

import br.com.fiap.techchallengeapipagamento.core.config.properties.MercadoPagoProperties;
import br.com.fiap.techchallengeapipagamento.pagamento.application.controller.PagamentoController;
import br.com.fiap.techchallengeapipagamento.pagamento.application.gateway.PagamentoGateway;
import br.com.fiap.techchallengeapipagamento.pagamento.application.gateway.impl.PagamentoGatewayImpl;
import br.com.fiap.techchallengeapipagamento.pagamento.application.mapper.RequestPagamentoMapper;
import br.com.fiap.techchallengeapipagamento.pagamento.application.presenter.PagamentoPresenter;
import br.com.fiap.techchallengeapipagamento.pagamento.application.usecase.*;
import br.com.fiap.techchallengeapipagamento.pagamento.application.usecase.impl.*;
import br.com.fiap.techchallengeapipagamento.pagamento.common.domain.dto.request.PedidoRequestDto;
import br.com.fiap.techchallengeapipagamento.pagamento.common.domain.dto.response.PagamentoResponseDto;
import br.com.fiap.techchallengeapipagamento.pagamento.common.interfaces.PagamentoDatabase;
import br.com.fiap.techchallengeapipagamento.pagamento.domain.StatusPagamentoEnum;
import br.com.fiap.techchallengeapipagamento.pagamento.infrastructure.client.mercadopago.MercadoPagoCodigoQRClient;
import br.com.fiap.techchallengeapipagamento.pagamento.infrastructure.client.mercadopago.MercadoPagoPosClient;
import br.com.fiap.techchallengeapipagamento.pagamento.infrastructure.client.mercadopago.mapper.MercadoPagoOrderRequestMapper;
import br.com.fiap.techchallengeapipagamento.pagamento.infrastructure.client.pedido.PedidoClient;
import org.springframework.stereotype.Component;

import java.awt.image.BufferedImage;
import java.util.List;

@Component
public class PagamentoControllerImpl implements PagamentoController {

    private final SalvarPagamentoUseCase salvarPagamentoUseCase;
    private final ConsultarPagamentoUseCase consultarPagamentoUseCase;
    private final ConsultarQrCodePagamentoUseCase consultarQrCodePagamentoUseCase;
    private final RequestPagamentoMapper requestPagamentoMapper;
    private final PagamentoPresenter pagamentoPresenter;

    public PagamentoControllerImpl(
            PagamentoPresenter pagamentoPresenter,
            RequestPagamentoMapper requestPagamentoMapper,
            PagamentoDatabase pagamentoDatabase,
            PedidoClient pedidoClient,
            MercadoPagoOrderRequestMapper mercadoPagoOrderRequestMapper,
            MercadoPagoCodigoQRClient mercadoPagoCodigoQRClient,
            MercadoPagoPosClient mercadoPagoPosClient,
            MercadoPagoProperties mercadoPagoProperties
    ) {
        final CriarPedidoMercadoPagoUseCase criarPedidoMercadoPagoUseCase = new CriarPedidoMercadoPagoUseCaseImpl(mercadoPagoOrderRequestMapper, mercadoPagoCodigoQRClient, mercadoPagoProperties);
        final ConfirmarPagamentoPedidoUseCase confirmarPagamentoPedidoUseCase = new ConfirmarPagamentoPedidoUseCaseImpl(pedidoClient);
        final PagamentoGateway pagamentoGateway = new PagamentoGatewayImpl(pagamentoDatabase, mercadoPagoPosClient, mercadoPagoProperties);

        this.salvarPagamentoUseCase = new SalvarPagamentoUseCaseImpl(pagamentoGateway, criarPedidoMercadoPagoUseCase, confirmarPagamentoPedidoUseCase);
        this.consultarPagamentoUseCase = new ConsultarPagamentoUseCaseImpl(pagamentoGateway);
        this.consultarQrCodePagamentoUseCase = new ConsultarQrCodePagamentoUseCaseImpl(mercadoPagoPosClient, mercadoPagoProperties);
        this.pagamentoPresenter = pagamentoPresenter;
        this.requestPagamentoMapper = requestPagamentoMapper;
    }

    @Override
    public List<PagamentoResponseDto> buscarPorPedidoId(String pedidoId) {
        return pagamentoPresenter.pagamentosParaPagamentoResponseDTOs(
                consultarPagamentoUseCase.buscarPorPedidoId(pedidoId));
    }

    @Override
    public BufferedImage gerarImagemCodigoQRCaixa() {
        return consultarQrCodePagamentoUseCase.gerarImagemCodigoQRCaixa();
    }

    @Override
    public PagamentoResponseDto fazerPagamentoDoPedido(PedidoRequestDto pedidoRequestDTO) {
        return pagamentoPresenter.pagamentoParaPagamentoResponseDTO(
                salvarPagamentoUseCase.fazerPagamentoDoPedido(
                        requestPagamentoMapper.pedidoRequestDtoParaPedido(pedidoRequestDTO)));
    }

    @Override
    public List<PagamentoResponseDto> buscarPorStatus(String status) {
        return pagamentoPresenter.pagamentosParaPagamentoResponseDTOs(
                consultarPagamentoUseCase.buscarPorStatus(status));
    }

    @Override
    public PagamentoResponseDto atualizarStatusPagamento(String id, String novoStatus) {
        return pagamentoPresenter.pagamentoParaPagamentoResponseDTO(
                salvarPagamentoUseCase.atualizarStatusPagamento(id, StatusPagamentoEnum.fromString(novoStatus)));
    }
}