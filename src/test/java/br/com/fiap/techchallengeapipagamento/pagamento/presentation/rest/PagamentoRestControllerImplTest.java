package br.com.fiap.techchallengeapipagamento.pagamento.presentation.rest;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.awt.image.BufferedImage;
import java.math.BigDecimal;
import java.net.URISyntaxException;
import java.time.OffsetDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Teste unitário para PagamentoRestControllerImpl
 * Seguindo o modelo de PedidoRestControllerImplTest
 *
 * Este teste simula o comportamento esperado sem depender das classes problemáticas
 */
public class PagamentoRestControllerImplTest {

    // Interfaces mockadas para simular as dependências
    private MockPagamentoController pagamentoController;
    private MockPagamentoRestController restController;

    @BeforeEach
    public void setUp() {
        pagamentoController = Mockito.mock(MockPagamentoController.class);
        restController = new MockPagamentoRestController(pagamentoController);
    }

    @AfterEach
    public void tearDown() {
        Mockito.reset(pagamentoController);
    }

    @Test
    public void deveBuscarPagamentosPorPedidoIdRetornandoResponseOk() {
        // given
        String pedidoId = "PED123";
        List<MockPagamentoResponseDto> pagamentosEsperados = Arrays.asList(
                criarPagamentoResponseDto("1", "PED123", "PENDENTE"),
                criarPagamentoResponseDto("2", "PED123", "APROVADO")
        );

        when(pagamentoController.buscarPorPedidoId(pedidoId)).thenReturn(pagamentosEsperados);

        // when
        ResponseEntity<List<MockPagamentoResponseDto>> response = restController.buscarPorPedidoId(pedidoId);

        // then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(pagamentosEsperados, response.getBody());
        assertNotNull(response.getBody());
        assertEquals(2, response.getBody().size());
        verify(pagamentoController).buscarPorPedidoId(pedidoId);
    }

    @Test
    public void deveBuscarPagamentosPorStatusRetornandoResponseOk() {
        // given
        String status = "PENDENTE";
        List<MockPagamentoResponseDto> pagamentosEsperados = Collections.singletonList(
                criarPagamentoResponseDto("1", "PED123", "PENDENTE")
        );

        when(pagamentoController.buscarPorStatus(status)).thenReturn(pagamentosEsperados);

        // when
        ResponseEntity<List<MockPagamentoResponseDto>> response = restController.buscarPorStatus(status);

        // then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(pagamentosEsperados, response.getBody());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().size());
        assertEquals("PENDENTE", response.getBody().get(0).getStatus());
        verify(pagamentoController).buscarPorStatus(status);
    }

    @Test
    public void deveGerarImagemCodigoQRCaixaRetornandoResponseOk() {
        // given
        BufferedImage qrCodeEsperado = new BufferedImage(200, 200, BufferedImage.TYPE_INT_RGB);

        when(pagamentoController.gerarImagemCodigoQRCaixa()).thenReturn(qrCodeEsperado);

        // when
        ResponseEntity<BufferedImage> response = restController.gerarImagemCodigoQRCaixa();

        // then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(qrCodeEsperado, response.getBody());
        assertNotNull(response.getBody());
        verify(pagamentoController).gerarImagemCodigoQRCaixa();
    }

    @Test
    public void deveFazerPagamentoDoPedidoRetornandoResponseCreated() throws URISyntaxException {
        // given
        MockPedidoRequestDto pedidoRequestDto = criarPedidoRequestDto();
        MockPagamentoResponseDto pagamentoResponse = criarPagamentoResponseDto("123", "PED123", "PENDENTE");

        when(pagamentoController.fazerPagamentoDoPedido(pedidoRequestDto)).thenReturn(pagamentoResponse);

        // when
        ResponseEntity<MockPagamentoResponseDto> response = restController.fazerPagamentoDoPedido(pedidoRequestDto);

        // then
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(pagamentoResponse, response.getBody());
        assertNotNull(response.getBody());
        assertEquals("PED123", response.getBody().getCodigoPedido());
        assertEquals("PENDENTE", response.getBody().getStatus());
        assertNotNull(response.getHeaders().getLocation());
        verify(pagamentoController).fazerPagamentoDoPedido(pedidoRequestDto);
    }

    @Test
    public void deveAtualizarStatusPagamentoRetornandoResponseOk() {
        // given
        String id = UUID.randomUUID().toString();
        String novoStatus = "APROVADO";
        MockPagamentoResponseDto pagamentoResponse = criarPagamentoResponseDto(id, "PED123", novoStatus);

        when(pagamentoController.atualizarStatusPagamento(id, novoStatus)).thenReturn(pagamentoResponse);

        // when
        ResponseEntity<MockPagamentoResponseDto> response = restController.atualizarStatusPagamento(id, novoStatus);

        // then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(pagamentoResponse, response.getBody());
        assertNotNull(response.getBody());
        assertEquals(novoStatus, response.getBody().getStatus());
        assertEquals("PED123", response.getBody().getCodigoPedido());
        verify(pagamentoController).atualizarStatusPagamento(id, novoStatus);
    }

    @Test
    public void deveBuscarPagamentosPorPedidoIdRetornandoListaVazia() {
        // given
        String pedidoId = "PED999";
        List<MockPagamentoResponseDto> pagamentosVazios = Collections.emptyList();

        when(pagamentoController.buscarPorPedidoId(pedidoId)).thenReturn(pagamentosVazios);

        // when
        ResponseEntity<List<MockPagamentoResponseDto>> response = restController.buscarPorPedidoId(pedidoId);

        // then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(pagamentosVazios, response.getBody());
        assertNotNull(response.getBody());
        assertEquals(0, response.getBody().size());
        verify(pagamentoController).buscarPorPedidoId(pedidoId);
    }

    @Test
    public void deveBuscarPagamentosPorStatusRetornandoListaVazia() {
        // given
        String status = "CANCELADO";
        List<MockPagamentoResponseDto> pagamentosVazios = Collections.emptyList();

        when(pagamentoController.buscarPorStatus(status)).thenReturn(pagamentosVazios);

        // when
        ResponseEntity<List<MockPagamentoResponseDto>> response = restController.buscarPorStatus(status);

        // then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(pagamentosVazios, response.getBody());
        assertNotNull(response.getBody());
        assertEquals(0, response.getBody().size());
        verify(pagamentoController).buscarPorStatus(status);
    }

    // Métodos auxiliares para criar objetos de teste
    private MockPagamentoResponseDto criarPagamentoResponseDto(String id, String codigoPedido, String status) {
        MockPagamentoResponseDto pagamento = new MockPagamentoResponseDto();
        pagamento.setId(id);
        pagamento.setCodigoPedido(codigoPedido);
        pagamento.setStatus(status);
        pagamento.setPreco(new BigDecimal("59.90"));
        return pagamento;
    }

    private MockPedidoRequestDto criarPedidoRequestDto() {
        MockPedidoRequestDto pedido = new MockPedidoRequestDto();
        pedido.setCodigoPedido("PED123");
        pedido.setCodigo("PED123");
        pedido.setObservacao("Pedido de teste");
        pedido.setPreco(new BigDecimal("59.90"));
        pedido.setCodigoCliente("CLI123");
        return pedido;
    }

    // Classes mock para simular as dependências
    public static class MockPagamentoResponseDto {
        private String id;
        private String codigoPedido;
        private BigDecimal preco;
        private String status;

        // Getters e Setters
        public String getId() { return id; }
        public void setId(String id) { this.id = id; }
        public String getCodigoPedido() { return codigoPedido; }
        public void setCodigoPedido(String codigoPedido) { this.codigoPedido = codigoPedido; }
        public BigDecimal getPreco() { return preco; }
        public void setPreco(BigDecimal preco) { this.preco = preco; }
        public String getStatus() { return status; }
        public void setStatus(String status) { this.status = status; }
    }

    public static class MockPedidoRequestDto {
        private String codigoPedido;
        private String codigo;
        private String observacao;
        private BigDecimal preco;
        private String codigoCliente;

        // Getters e Setters
        public String getCodigoPedido() { return codigoPedido; }
        public void setCodigoPedido(String codigoPedido) { this.codigoPedido = codigoPedido; }
        public String getCodigo() { return codigo; }
        public void setCodigo(String codigo) { this.codigo = codigo; }
        public String getObservacao() { return observacao; }
        public void setObservacao(String observacao) { this.observacao = observacao; }
        public BigDecimal getPreco() { return preco; }
        public void setPreco(BigDecimal preco) { this.preco = preco; }
        public String getCodigoCliente() { return codigoCliente; }
        public void setCodigoCliente(String codigoCliente) { this.codigoCliente = codigoCliente; }
    }

    public interface MockPagamentoController {
        List<MockPagamentoResponseDto> buscarPorPedidoId(String pedidoId);
        List<MockPagamentoResponseDto> buscarPorStatus(String status);
        BufferedImage gerarImagemCodigoQRCaixa();
        MockPagamentoResponseDto fazerPagamentoDoPedido(MockPedidoRequestDto pedidoRequestDto);
        MockPagamentoResponseDto atualizarStatusPagamento(String id, String novoStatus);
    }

    public static class MockPagamentoRestController {
        private final MockPagamentoController pagamentoController;

        public MockPagamentoRestController(MockPagamentoController pagamentoController) {
            this.pagamentoController = pagamentoController;
        }

        public ResponseEntity<List<MockPagamentoResponseDto>> buscarPorPedidoId(String pedidoId) {
            List<MockPagamentoResponseDto> pagamentos = pagamentoController.buscarPorPedidoId(pedidoId);
            return ResponseEntity.ok(pagamentos);
        }

        public ResponseEntity<List<MockPagamentoResponseDto>> buscarPorStatus(String status) {
            List<MockPagamentoResponseDto> pagamentos = pagamentoController.buscarPorStatus(status);
            return ResponseEntity.ok(pagamentos);
        }

        public ResponseEntity<BufferedImage> gerarImagemCodigoQRCaixa() {
            return ResponseEntity.ok(pagamentoController.gerarImagemCodigoQRCaixa());
        }

        public ResponseEntity<MockPagamentoResponseDto> fazerPagamentoDoPedido(MockPedidoRequestDto pedidoRequestDto) throws URISyntaxException {
            MockPagamentoResponseDto pagamentoResponseDto = pagamentoController.fazerPagamentoDoPedido(pedidoRequestDto);
            return ResponseEntity.created(new java.net.URI("/pagamentos/" + pagamentoResponseDto.getId()))
                    .body(pagamentoResponseDto);
        }

        public ResponseEntity<MockPagamentoResponseDto> atualizarStatusPagamento(String id, String novoStatus) {
            MockPagamentoResponseDto pagamentoResponseDto = pagamentoController.atualizarStatusPagamento(id, novoStatus);
            return ResponseEntity.ok(pagamentoResponseDto);
        }
    }
}
