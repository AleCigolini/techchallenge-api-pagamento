package br.com.fiap.techchallengeapipagamento.pagamento.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Pedido Domain Tests")
class PedidoTest {

    private Pedido pedido;

    @BeforeEach
    void setUp() {
        pedido = new Pedido();
    }

    @Test
    @DisplayName("Deve criar um pedido com valores válidos")
    void deveCriarPedidoComValoresValidos() {
        // Given
        String codigoPedido = "PED-123";
        String codigo = "COD-456";
        String codigoCliente = "CLI-789";
        BigDecimal preco = new BigDecimal("75.50");
        String codigoPagamento = "PAG-321";
        String observacao = "Pedido especial";

        // When
        pedido.setCodigoPedido(codigoPedido);
        pedido.setCodigo(codigo);
        pedido.setCodigoCliente(codigoCliente);
        pedido.setPreco(preco);
        pedido.setCodigoPagamento(codigoPagamento);
        pedido.setObservacao(observacao);

        // Then
        assertEquals(codigoPedido, pedido.getCodigoPedido());
        assertEquals(codigo, pedido.getCodigo());
        assertEquals(codigoCliente, pedido.getCodigoCliente());
        assertEquals(preco, pedido.getPreco());
        assertEquals(codigoPagamento, pedido.getCodigoPagamento());
        assertEquals(observacao, pedido.getObservacao());
    }

    @Test
    @DisplayName("Deve gerenciar lista de produtos")
    void deveGerenciarListaDeProdutos() {
        // Given
        Produto produto1 = new Produto();
        produto1.setCodigoProduto("PROD-1");
        produto1.setNome("Produto 1");
        produto1.setPreco(new BigDecimal("25.00"));

        Produto produto2 = new Produto();
        produto2.setCodigoProduto("PROD-2");
        produto2.setNome("Produto 2");
        produto2.setPreco(new BigDecimal("50.00"));

        List<Produto> produtos = Arrays.asList(produto1, produto2);

        // When
        pedido.setProdutos(produtos);

        // Then
        assertNotNull(pedido.getProdutos());
        assertEquals(2, pedido.getProdutos().size());
        assertEquals("PROD-1", pedido.getProdutos().get(0).getCodigoProduto());
        assertEquals("PROD-2", pedido.getProdutos().get(1).getCodigoProduto());
    }

    @Test
    @DisplayName("Deve gerenciar lista de pagamentos")
    void deveGerenciarListaDePagamentos() {
        // Given
        Pagamento pagamento1 = new Pagamento();
        pagamento1.setId("PAG-1");
        pagamento1.setStatus("PENDENTE");
        pagamento1.setPreco(new BigDecimal("30.00"));

        Pagamento pagamento2 = new Pagamento();
        pagamento2.setId("PAG-2");
        pagamento2.setStatus("APROVADO");
        pagamento2.setPreco(new BigDecimal("45.00"));

        List<Pagamento> pagamentos = Arrays.asList(pagamento1, pagamento2);

        // When
        pedido.setPagamentos(pagamentos);

        // Then
        assertNotNull(pedido.getPagamentos());
        assertEquals(2, pedido.getPagamentos().size());
        assertEquals("PAG-1", pedido.getPagamentos().get(0).getId());
        assertEquals("PAG-2", pedido.getPagamentos().get(1).getId());
    }

    @Test
    @DisplayName("Deve aceitar listas vazias")
    void deveAceitarListasVazias() {
        // Given
        List<Produto> produtosVazios = new ArrayList<>();
        List<Pagamento> pagamentosVazios = new ArrayList<>();

        // When
        pedido.setProdutos(produtosVazios);
        pedido.setPagamentos(pagamentosVazios);

        // Then
        assertNotNull(pedido.getProdutos());
        assertNotNull(pedido.getPagamentos());
        assertTrue(pedido.getProdutos().isEmpty());
        assertTrue(pedido.getPagamentos().isEmpty());
    }

    @Test
    @DisplayName("Deve aceitar listas nulas")
    void deveAceitarListasNulas() {
        // When
        pedido.setProdutos(null);
        pedido.setPagamentos(null);

        // Then
        assertNull(pedido.getProdutos());
        assertNull(pedido.getPagamentos());
    }

    @Test
    @DisplayName("Deve permitir valores nulos nos campos")
    void devePermitirValoresNulosNosCampos() {
        // When
        pedido.setCodigoPedido(null);
        pedido.setCodigo(null);
        pedido.setCodigoCliente(null);
        pedido.setPreco(null);
        pedido.setCodigoPagamento(null);
        pedido.setObservacao(null);

        // Then
        assertNull(pedido.getCodigoPedido());
        assertNull(pedido.getCodigo());
        assertNull(pedido.getCodigoCliente());
        assertNull(pedido.getPreco());
        assertNull(pedido.getCodigoPagamento());
        assertNull(pedido.getObservacao());
    }

    @Test
    @DisplayName("Deve aceitar preço zero")
    void deveAceitarPrecoZero() {
        // When
        pedido.setPreco(BigDecimal.ZERO);

        // Then
        assertEquals(BigDecimal.ZERO, pedido.getPreco());
    }

    @Test
    @DisplayName("Deve testar equals e hashCode")
    void deveTestarEqualsEHashCode() {
        // Given
        Pedido pedido1 = new Pedido();
        pedido1.setCodigoPedido("PED-123");
        pedido1.setCodigo("COD-456");
        pedido1.setPreco(new BigDecimal("100.00"));

        Pedido pedido2 = new Pedido();
        pedido2.setCodigoPedido("PED-123");
        pedido2.setCodigo("COD-456");
        pedido2.setPreco(new BigDecimal("100.00"));

        Pedido pedido3 = new Pedido();
        pedido3.setCodigoPedido("PED-789");
        pedido3.setCodigo("COD-456");
        pedido3.setPreco(new BigDecimal("100.00"));

        // Then
        assertEquals(pedido1, pedido2);
        assertNotEquals(pedido1, pedido3);
        assertEquals(pedido1.hashCode(), pedido2.hashCode());
    }

    @Test
    @DisplayName("Deve aceitar observações longas")
    void deveAceitarObservacoesLongas() {
        // Given
        String observacaoLonga = "Esta é uma observação muito longa que contém muitos detalhes sobre o pedido, " +
                "incluindo instruções especiais de preparo, preferências do cliente, observações sobre alergia, " +
                "e outras informações importantes que devem ser consideradas durante o processamento do pedido.";

        // When
        pedido.setObservacao(observacaoLonga);

        // Then
        assertEquals(observacaoLonga, pedido.getObservacao());
    }

    @Test
    @DisplayName("Deve aceitar códigos com diferentes formatos")
    void deveAceitarCodigosComDiferentesFormatos() {
        // Given
        String[] formatosCodigos = {"PED123", "pedido-456", "PEDIDO_789", "123456789", "PED.001.2023"};

        // When & Then
        for (String codigo : formatosCodigos) {
            pedido.setCodigoPedido(codigo);
            pedido.setCodigo(codigo);
            pedido.setCodigoCliente(codigo);
            pedido.setCodigoPagamento(codigo);

            assertEquals(codigo, pedido.getCodigoPedido());
            assertEquals(codigo, pedido.getCodigo());
            assertEquals(codigo, pedido.getCodigoCliente());
            assertEquals(codigo, pedido.getCodigoPagamento());
        }
    }

    @Test
    @DisplayName("Deve manter integridade após modificações nas listas")
    void deveManterIntegridadeAposModificacoesNasListas() {
        // Given
        Produto produto = new Produto();
        produto.setCodigoProduto("PROD-1");

        Pagamento pagamento = new Pagamento();
        pagamento.setId("PAG-1");

        List<Produto> produtos = new ArrayList<>();
        produtos.add(produto);

        List<Pagamento> pagamentos = new ArrayList<>();
        pagamentos.add(pagamento);

        // When
        pedido.setProdutos(produtos);
        pedido.setPagamentos(pagamentos);

        // Modificar as listas após setar no pedido
        produtos.add(new Produto());
        pagamentos.add(new Pagamento());

        // Then
        assertEquals(2, pedido.getProdutos().size());
        assertEquals(2, pedido.getPagamentos().size());
    }
}
