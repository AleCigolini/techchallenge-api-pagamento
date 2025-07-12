package br.com.fiap.techchallenge03.pagamento.application.controller.impl;

import br.com.fiap.techchallenge03.core.config.properties.MercadoPagoProperties;
import br.com.fiap.techchallenge03.pagamento.application.controller.PagamentoController;
import br.com.fiap.techchallenge03.pagamento.application.gateway.PagamentoGateway;
import br.com.fiap.techchallenge03.pagamento.application.gateway.impl.PagamentoGatewayImpl;
import br.com.fiap.techchallenge03.pagamento.application.mapper.DatabasePagamentoMapper;
import br.com.fiap.techchallenge03.pagamento.application.mapper.RequestPagamentoMapper;
import br.com.fiap.techchallenge03.pagamento.application.presenter.PagamentoPresenter;
import br.com.fiap.techchallenge03.pagamento.application.usecase.ConsultarPagamentoUseCase;
import br.com.fiap.techchallenge03.pagamento.application.usecase.ConsultarQrCodePagamentoUseCase;
import br.com.fiap.techchallenge03.pagamento.application.usecase.CriarPedidoMercadoPagoUseCase;
import br.com.fiap.techchallenge03.pagamento.application.usecase.SalvarPagamentoUseCase;
import br.com.fiap.techchallenge03.pagamento.application.usecase.impl.ConsultarPagamentoUseCaseImpl;
import br.com.fiap.techchallenge03.pagamento.application.usecase.impl.ConsultarQrCodePagamentoUseCaseImpl;
import br.com.fiap.techchallenge03.pagamento.application.usecase.impl.CriarPedidoMercadoPagoUseCaseImpl;
import br.com.fiap.techchallenge03.pagamento.application.usecase.impl.SalvarPagamentoUseCaseImpl;
import br.com.fiap.techchallenge03.pagamento.common.domain.dto.request.PedidoRequestDto;
import br.com.fiap.techchallenge03.pagamento.common.domain.dto.response.PagamentoResponseDto;
import br.com.fiap.techchallenge03.pagamento.common.interfaces.PagamentoDatabase;
import br.com.fiap.techchallenge03.pagamento.infrastructure.client.mercadopago.MercadoPagoCodigoQRClient;
import br.com.fiap.techchallenge03.pagamento.infrastructure.client.mercadopago.MercadoPagoPosClient;
import br.com.fiap.techchallenge03.pagamento.infrastructure.client.mercadopago.mapper.MercadoPagoOrderRequestMapper;
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
            DatabasePagamentoMapper databasePagamentoMapper,
            MercadoPagoOrderRequestMapper mercadoPagoOrderRequestMapper,
            MercadoPagoCodigoQRClient mercadoPagoCodigoQRClient,
            MercadoPagoPosClient mercadoPagoPosClient,
            MercadoPagoProperties mercadoPagoProperties
    ) {
        final CriarPedidoMercadoPagoUseCase criarPedidoMercadoPagoUseCase = new CriarPedidoMercadoPagoUseCaseImpl(mercadoPagoOrderRequestMapper, mercadoPagoCodigoQRClient, mercadoPagoProperties);
        final PagamentoGateway pagamentoGateway = new PagamentoGatewayImpl(pagamentoDatabase, databasePagamentoMapper);

        this.salvarPagamentoUseCase = new SalvarPagamentoUseCaseImpl(pagamentoGateway, criarPedidoMercadoPagoUseCase);
        this.consultarPagamentoUseCase = new ConsultarPagamentoUseCaseImpl(pagamentoGateway);
        this.consultarQrCodePagamentoUseCase = new ConsultarQrCodePagamentoUseCaseImpl(mercadoPagoPosClient, mercadoPagoProperties);
        this.pagamentoPresenter = pagamentoPresenter;
        this.requestPagamentoMapper = requestPagamentoMapper;
    }

    @Override
    public List<PagamentoResponseDto> buscarPagamentosPorPedidoId(String pedidoId) {
        return pagamentoPresenter.pagamentosParaPagamentoResponseDTOs(
                consultarPagamentoUseCase.buscarPagamentosPorPedidoId(pedidoId));
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
}