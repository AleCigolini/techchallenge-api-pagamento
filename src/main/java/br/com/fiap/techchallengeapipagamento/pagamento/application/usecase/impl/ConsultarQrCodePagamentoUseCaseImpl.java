package br.com.fiap.techchallengeapipagamento.pagamento.application.usecase.impl;

import br.com.fiap.techchallengeapipagamento.core.config.properties.MercadoPagoProperties;
import br.com.fiap.techchallengeapipagamento.pagamento.application.usecase.ConsultarQrCodePagamentoUseCase;
import br.com.fiap.techchallengeapipagamento.pagamento.infrastructure.client.mercadopago.MercadoPagoPosClient;
import br.com.fiap.techchallengeapipagamento.pagamento.infrastructure.client.mercadopago.response.MercadoPagoPosResponse;
import org.springframework.http.ResponseEntity;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.net.URL;

public class ConsultarQrCodePagamentoUseCaseImpl implements ConsultarQrCodePagamentoUseCase {

    private final MercadoPagoPosClient mercadoPagoPosClient;
    private final MercadoPagoProperties mercadoPagoProperties;

    public ConsultarQrCodePagamentoUseCaseImpl(
            MercadoPagoPosClient mercadoPagoPosClient,
            MercadoPagoProperties mercadoPagoProperties
    ) {
        this.mercadoPagoPosClient = mercadoPagoPosClient;
        this.mercadoPagoProperties = mercadoPagoProperties;
    }

    @Override
    public BufferedImage gerarImagemCodigoQRCaixa() {
        try {
            ResponseEntity<MercadoPagoPosResponse> response =
                    mercadoPagoPosClient.obterCaixa(
                            mercadoPagoProperties.getPosId(),
                            mercadoPagoProperties.getAuthHeader());

            if (response.getStatusCode().is2xxSuccessful()) {
                String enderecoImagem = response.getBody().getQr().getImage();
                URL imageURL = new URL(enderecoImagem);
                return ImageIO.read(imageURL);
            } else {
                return null;
            }

        } catch (Exception ex) {
            System.out.printf(
                    "MercadoPago Error. Status: %s, Content: %s",
                    ex.getCause(), ex.getMessage());
            return null;
        }
    }
}