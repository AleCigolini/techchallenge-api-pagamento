package br.com.fiap.techchallengeapipagamento.pagamento.application.gateway.impl;

import br.com.fiap.techchallengeapipagamento.core.config.properties.MercadoPagoProperties;
import br.com.fiap.techchallengeapipagamento.pagamento.application.gateway.PagamentoGateway;
import br.com.fiap.techchallengeapipagamento.pagamento.common.interfaces.PagamentoDatabase;
import br.com.fiap.techchallengeapipagamento.pagamento.domain.Pagamento;
import br.com.fiap.techchallengeapipagamento.pagamento.infrastructure.client.mercadopago.MercadoPagoPosClient;
import br.com.fiap.techchallengeapipagamento.pagamento.infrastructure.client.mercadopago.response.MercadoPagoPosResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.net.URL;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class PagamentoGatewayImpl implements PagamentoGateway {

    private final PagamentoDatabase pagamentoDatabase;
    private final MercadoPagoPosClient mercadoPagoPosClient;
    private final MercadoPagoProperties mercadoPagoProperties;

    @Override
    public Pagamento salvar(Pagamento pagamento) {
        return pagamentoDatabase.salvar(pagamento);
    }

    @Override
    public List<Pagamento> buscarPorPedidoId(String codigoPedido) {
        return pagamentoDatabase.buscarPorCodigoPedido(codigoPedido);
    }

    @Override
    public Optional<Pagamento> buscarPorId(String id) {
        return pagamentoDatabase.buscarPorId(id);
    }

    @Override
    public List<Pagamento> buscarPorStatus(String status) {
        return pagamentoDatabase.buscarPorStatus(status);
    }

    @Override
    public Optional<Pagamento> buscarPagamentoPorPedidoEStatus(String codigoPedido, String status) {
        return pagamentoDatabase.buscarPagamentoPorPedidoEStatus(codigoPedido, status);
    }

    @Override
    public Optional<Pagamento> atualizarStatusPagamento(String id, String novoStatus) {
        return pagamentoDatabase.atualizarStatusPagamento(id, novoStatus);
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
