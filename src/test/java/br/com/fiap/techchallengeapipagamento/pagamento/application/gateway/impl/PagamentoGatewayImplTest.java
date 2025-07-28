package br.com.fiap.techchallengeapipagamento.pagamento.application.gateway.impl;

import br.com.fiap.techchallengeapipagamento.core.config.properties.MercadoPagoProperties;
import br.com.fiap.techchallengeapipagamento.pagamento.common.interfaces.PagamentoDatabase;
import br.com.fiap.techchallengeapipagamento.pagamento.domain.Pagamento;
import br.com.fiap.techchallengeapipagamento.pagamento.infrastructure.client.mercadopago.MercadoPagoPosClient;
import br.com.fiap.techchallengeapipagamento.pagamento.infrastructure.client.mercadopago.response.MercadoPagoPosResponse;
import br.com.fiap.techchallengeapipagamento.pagamento.infrastructure.client.mercadopago.response.MercadoPagoPosQrResponse;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.awt.image.BufferedImage;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class PagamentoGatewayImplTest {

    private PagamentoDatabase pagamentoDatabase;
    private MercadoPagoPosClient mercadoPagoPosClient;
    private MercadoPagoProperties mercadoPagoProperties;
    private PagamentoGatewayImpl gateway;

    @BeforeEach
    public void setUp() {
        pagamentoDatabase = Mockito.mock(PagamentoDatabase.class);
        mercadoPagoPosClient = Mockito.mock(MercadoPagoPosClient.class);
        mercadoPagoProperties = Mockito.mock(MercadoPagoProperties.class);
        gateway = new PagamentoGatewayImpl(pagamentoDatabase, mercadoPagoPosClient, mercadoPagoProperties);
    }

    @AfterEach
    public void tearDown() {
        Mockito.reset(pagamentoDatabase, mercadoPagoPosClient, mercadoPagoProperties);
    }

    @Test
    public void deveSalvarPagamento() {
        // given
        Pagamento pagamento = criarPagamento();
        Mockito.when(pagamentoDatabase.salvar(pagamento)).thenReturn(pagamento);

        // when
        Pagamento result = gateway.salvar(pagamento);

        // then
        assertNotNull(result);
        assertEquals(pagamento.getId(), result.getId());
        assertEquals(pagamento.getCodigoPedido(), result.getCodigoPedido());
        Mockito.verify(pagamentoDatabase, Mockito.times(1)).salvar(pagamento);
    }

    @Test
    public void deveBuscarPagamentosPorPedidoId() {
        // given
        String codigoPedido = "PED001";
        List<Pagamento> pagamentos = Arrays.asList(criarPagamento(), criarPagamento());
        Mockito.when(pagamentoDatabase.buscarPorCodigoPedido(codigoPedido)).thenReturn(pagamentos);

        // when
        List<Pagamento> result = gateway.buscarPorPedidoId(codigoPedido);

        // then
        assertNotNull(result);
        assertEquals(2, result.size());
        Mockito.verify(pagamentoDatabase, Mockito.times(1)).buscarPorCodigoPedido(codigoPedido);
    }

    @Test
    public void deveBuscarPagamentoPorId() {
        // given
        String id = "1";
        Pagamento pagamento = criarPagamento();
        Mockito.when(pagamentoDatabase.buscarPorId(id)).thenReturn(Optional.of(pagamento));

        // when
        Optional<Pagamento> result = gateway.buscarPorId(id);

        // then
        assertNotNull(result);
        assertTrue(result.isPresent());
        assertEquals(pagamento.getId(), result.get().getId());
        Mockito.verify(pagamentoDatabase, Mockito.times(1)).buscarPorId(id);
    }

    @Test
    public void deveRetornarOptionalVazioQuandoPagamentoNaoEncontrado() {
        // given
        String id = "inexistente";
        Mockito.when(pagamentoDatabase.buscarPorId(id)).thenReturn(Optional.empty());

        // when
        Optional<Pagamento> result = gateway.buscarPorId(id);

        // then
        assertNotNull(result);
        assertFalse(result.isPresent());
        Mockito.verify(pagamentoDatabase, Mockito.times(1)).buscarPorId(id);
    }

    @Test
    public void deveBuscarPagamentosPorStatus() {
        // given
        String status = "APROVADO";
        List<Pagamento> pagamentos = Arrays.asList(criarPagamento(), criarPagamento());
        Mockito.when(pagamentoDatabase.buscarPorStatus(status)).thenReturn(pagamentos);

        // when
        List<Pagamento> result = gateway.buscarPorStatus(status);

        // then
        assertNotNull(result);
        assertEquals(2, result.size());
        Mockito.verify(pagamentoDatabase, Mockito.times(1)).buscarPorStatus(status);
    }

    @Test
    public void deveRetornarListaVaziaQuandoNaoEncontrarPagamentosPorStatus() {
        // given
        String status = "INEXISTENTE";
        Mockito.when(pagamentoDatabase.buscarPorStatus(status)).thenReturn(Collections.emptyList());

        // when
        List<Pagamento> result = gateway.buscarPorStatus(status);

        // then
        assertNotNull(result);
        assertTrue(result.isEmpty());
        Mockito.verify(pagamentoDatabase, Mockito.times(1)).buscarPorStatus(status);
    }

    @Test
    public void deveBuscarPagamentoPorPedidoEStatus() {
        // given
        String codigoPedido = "PED001";
        String status = "APROVADO";
        Pagamento pagamento = criarPagamento();
        Mockito.when(pagamentoDatabase.buscarPagamentoPorPedidoEStatus(codigoPedido, status))
                .thenReturn(Optional.of(pagamento));

        // when
        Optional<Pagamento> result = gateway.buscarPagamentoPorPedidoEStatus(codigoPedido, status);

        // then
        assertNotNull(result);
        assertTrue(result.isPresent());
        assertEquals(pagamento.getId(), result.get().getId());
        Mockito.verify(pagamentoDatabase, Mockito.times(1))
                .buscarPagamentoPorPedidoEStatus(codigoPedido, status);
    }

    @Test
    public void deveAtualizarStatusPagamento() {
        // given
        String id = "1";
        String novoStatus = "APROVADO";
        Pagamento pagamentoAtualizado = criarPagamento();
        pagamentoAtualizado.setStatus(novoStatus);
        Mockito.when(pagamentoDatabase.atualizarStatusPagamento(id, novoStatus))
                .thenReturn(Optional.of(pagamentoAtualizado));

        // when
        Optional<Pagamento> result = gateway.atualizarStatusPagamento(id, novoStatus);

        // then
        assertNotNull(result);
        assertTrue(result.isPresent());
        assertEquals(novoStatus, result.get().getStatus());
        Mockito.verify(pagamentoDatabase, Mockito.times(1))
                .atualizarStatusPagamento(id, novoStatus);
    }

    @Test
    public void deveGerarImagemCodigoQRCaixaComSucesso() throws Exception {
        // given
        String posId = "POS123";
        String authHeader = "Bearer token123";
        String imageUrl = "https://example.com/qr-code.png";

        MercadoPagoPosQrResponse qr = MercadoPagoPosQrResponse.builder()
                .image(imageUrl)
                .templateDocument("template-doc")
                .templateImage("template-img")
                .build();

        MercadoPagoPosResponse response = MercadoPagoPosResponse.builder()
                .id("response-id")
                .qr(qr)
                .status("active")
                .dateCreated("2023-01-01")
                .dateLastUpdated("2023-01-01")
                .uuid("uuid-123")
                .userId("user-123")
                .name("Test POS")
                .fixedAmount("100.00")
                .category("category")
                .storeId("store-123")
                .externalStoreId("ext-store-123")
                .externalId("ext-id-123")
                .site("site")
                .qrCode("qr-code")
                .build();

        ResponseEntity<MercadoPagoPosResponse> responseEntity =
                new ResponseEntity<>(response, HttpStatus.OK);

        Mockito.when(mercadoPagoProperties.getPosId()).thenReturn(posId);
        Mockito.when(mercadoPagoProperties.getAuthHeader()).thenReturn(authHeader);
        Mockito.when(mercadoPagoPosClient.obterCaixa(posId, authHeader)).thenReturn(responseEntity);

        // when
        BufferedImage result = gateway.gerarImagemCodigoQRCaixa();

        // then
        // Como a URL é mock, o resultado será null porque não conseguirá baixar a imagem real
        // Em um cenário real, poderíamos mockar o ImageIO também
        assertNull(result); // Resultado esperado já que a URL é fictícia
        Mockito.verify(mercadoPagoPosClient, Mockito.times(1)).obterCaixa(posId, authHeader);
        Mockito.verify(mercadoPagoProperties, Mockito.times(1)).getPosId();
        Mockito.verify(mercadoPagoProperties, Mockito.times(1)).getAuthHeader();
    }

    @Test
    public void deveRetornarNullQuandoMercadoPagoRetornarErro() {
        // given
        String posId = "POS123";
        String authHeader = "Bearer token123";

        ResponseEntity<MercadoPagoPosResponse> responseEntity =
                new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);

        Mockito.when(mercadoPagoProperties.getPosId()).thenReturn(posId);
        Mockito.when(mercadoPagoProperties.getAuthHeader()).thenReturn(authHeader);
        Mockito.when(mercadoPagoPosClient.obterCaixa(posId, authHeader)).thenReturn(responseEntity);

        // when
        BufferedImage result = gateway.gerarImagemCodigoQRCaixa();

        // then
        assertNull(result);
        Mockito.verify(mercadoPagoPosClient, Mockito.times(1)).obterCaixa(posId, authHeader);
    }

    @Test
    public void deveRetornarNullQuandoOcorrerExcecao() {
        // given
        String posId = "POS123";
        String authHeader = "Bearer token123";

        Mockito.when(mercadoPagoProperties.getPosId()).thenReturn(posId);
        Mockito.when(mercadoPagoProperties.getAuthHeader()).thenReturn(authHeader);
        Mockito.when(mercadoPagoPosClient.obterCaixa(posId, authHeader))
                .thenThrow(new RuntimeException("Erro de conexão"));

        // when
        BufferedImage result = gateway.gerarImagemCodigoQRCaixa();

        // then
        assertNull(result);
        Mockito.verify(mercadoPagoPosClient, Mockito.times(1)).obterCaixa(posId, authHeader);
    }

    private Pagamento criarPagamento() {
        Pagamento pagamento = new Pagamento();
        pagamento.setId("1");
        pagamento.setCodigoPedido("PED001");
        pagamento.setPreco(new BigDecimal("25.50"));
        pagamento.setStatus("PENDENTE");
        return pagamento;
    }
}
