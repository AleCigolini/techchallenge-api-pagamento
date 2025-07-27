package br.com.fiap.techchallengeapipagamento.pagamento.infrastructure.client.mercadopago.mapper.impl;

import br.com.fiap.techchallengeapipagamento.pagamento.domain.Pedido;
import br.com.fiap.techchallengeapipagamento.pagamento.domain.Produto;
import br.com.fiap.techchallengeapipagamento.pagamento.infrastructure.client.mercadopago.request.MercadoPagoOrderItemRequest;
import br.com.fiap.techchallengeapipagamento.pagamento.infrastructure.client.mercadopago.request.MercadoPagoOrderRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("MercadoPagoOrderRequestMapperImpl Tests")
class MercadoPagoOrderRequestMapperImplTest {

    private MercadoPagoOrderRequestMapperImpl mapper;
    private Pedido pedido;
    private Produto produto1;

    @BeforeEach
    void setUp() {
        mapper = new MercadoPagoOrderRequestMapperImpl();

        produto1 = new Produto();
        produto1.setCodigoProduto("PROD-001");
        produto1.setNome("Hambúrguer Clássico");
        produto1.setDescricao("Hambúrguer com carne, queijo e salada");
        produto1.setCategoria("Sanduíches");
        produto1.setPreco(new BigDecimal("25.50"));
        produto1.setQuantidade(2L);

        Produto produto2 = new Produto();
        produto2.setCodigoProduto("PROD-002");
        produto2.setNome("Refrigerante");
        produto2.setDescricao("Coca-Cola 350ml");
        produto2.setCategoria("Bebidas");
        produto2.setPreco(new BigDecimal("5.00"));
        produto2.setQuantidade(1L);

        pedido = new Pedido();
        pedido.setCodigo("PED-123");
        pedido.setObservacao("Pedido especial sem cebola");
        pedido.setPreco(new BigDecimal("56.00"));
        pedido.setProdutos(Arrays.asList(produto1, produto2));
    }

    @Test
    @DisplayName("Deve mapear pedido para MercadoPagoOrderRequest com sucesso")
    void deveMappearPedidoParaMercadoPagoOrderRequestComSucesso() {
        // When
        MercadoPagoOrderRequest resultado = mapper.pedidoParaMercadoPagoOrderItemRequest(pedido);

        // Then
        assertNotNull(resultado);
        assertEquals("PED-123", resultado.getExternalReference());
        assertEquals("PED-123", resultado.getTitle());
        assertEquals("Pedido especial sem cebola", resultado.getDescription());
        assertEquals(new BigDecimal("56.00"), resultado.getTotalAmount());
        assertNotNull(resultado.getItems());
        assertEquals(2, resultado.getItems().size());
    }

    @Test
    @DisplayName("Deve mapear itens do pedido corretamente")
    void deveMappearItensDoPedidoCorretamente() {
        // When
        MercadoPagoOrderRequest resultado = mapper.pedidoParaMercadoPagoOrderItemRequest(pedido);

        // Then
        List<MercadoPagoOrderItemRequest> items = resultado.getItems();

        // Primeiro item
        MercadoPagoOrderItemRequest item1 = items.getFirst();
        assertEquals("PROD-001", item1.getSkuNumber());
        assertEquals("Hambúrguer Clássico", item1.getTitle());
        assertEquals("Hambúrguer com carne, queijo e salada", item1.getDescription());
        assertEquals("Sanduíches", item1.getCategory());
        assertEquals(new BigDecimal("25.50"), item1.getUnitPrice());
        assertEquals(2L, item1.getQuantity());
        assertEquals("unit", item1.getUnitMeasure());
        assertEquals(new BigDecimal("51.00"), item1.getTotalAmount()); // 25.50 * 2

        // Segundo item
        MercadoPagoOrderItemRequest item2 = items.get(1);
        assertEquals("PROD-002", item2.getSkuNumber());
        assertEquals("Refrigerante", item2.getTitle());
        assertEquals("Coca-Cola 350ml", item2.getDescription());
        assertEquals("Bebidas", item2.getCategory());
        assertEquals(new BigDecimal("5.00"), item2.getUnitPrice());
        assertEquals(1L, item2.getQuantity());
        assertEquals("unit", item2.getUnitMeasure());
        assertEquals(new BigDecimal("5.00"), item2.getTotalAmount()); // 5.00 * 1
    }

    @Test
    @DisplayName("Deve calcular total corretamente para item com quantidade múltipla")
    void deveCalcularTotalCorretamenteParaItemComQuantidadeMultipla() {
        // Given
        Produto produtoMultiplo = new Produto();
        produtoMultiplo.setCodigoProduto("PROD-MULTI");
        produtoMultiplo.setNome("Pizza");
        produtoMultiplo.setDescricao("Pizza Margherita");
        produtoMultiplo.setCategoria("Pizzas");
        produtoMultiplo.setPreco(new BigDecimal("15.75"));
        produtoMultiplo.setQuantidade(3L);

        pedido.setProdutos(List.of(produtoMultiplo));
        pedido.setPreco(new BigDecimal("47.25")); // 15.75 * 3

        // When
        MercadoPagoOrderRequest resultado = mapper.pedidoParaMercadoPagoOrderItemRequest(pedido);

        // Then
        MercadoPagoOrderItemRequest item = resultado.getItems().getFirst();
        assertEquals(new BigDecimal("47.25"), item.getTotalAmount());
        assertEquals(3L, item.getQuantity());
        assertEquals(new BigDecimal("15.75"), item.getUnitPrice());
    }

    @Test
    @DisplayName("Deve mapear pedido com um único produto")
    void deveMappearPedidoComUmUnicoProduto() {
        // Given
        pedido.setProdutos(List.of(produto1));
        pedido.setPreco(new BigDecimal("51.00"));

        // When
        MercadoPagoOrderRequest resultado = mapper.pedidoParaMercadoPagoOrderItemRequest(pedido);

        // Then
        assertNotNull(resultado);
        assertEquals(1, resultado.getItems().size());
        assertEquals("PROD-001", resultado.getItems().getFirst().getSkuNumber());
        assertEquals(new BigDecimal("51.00"), resultado.getTotalAmount());
    }

    @Test
    @DisplayName("Deve mapear produto com quantidade 1")
    void deveMappearProdutoComQuantidade1() {
        // Given
        Produto produtoUnico = new Produto();
        produtoUnico.setCodigoProduto("PROD-UNICO");
        produtoUnico.setNome("Produto Único");
        produtoUnico.setDescricao("Descrição única");
        produtoUnico.setCategoria("Categoria");
        produtoUnico.setPreco(new BigDecimal("10.00"));
        produtoUnico.setQuantidade(1L);

        pedido.setProdutos(List.of(produtoUnico));

        // When
        MercadoPagoOrderRequest resultado = mapper.pedidoParaMercadoPagoOrderItemRequest(pedido);

        // Then
        MercadoPagoOrderItemRequest item = resultado.getItems().getFirst();
        assertEquals(1L, item.getQuantity());
        assertEquals(new BigDecimal("10.00"), item.getTotalAmount());
        assertEquals(new BigDecimal("10.00"), item.getUnitPrice());
    }

    @Test
    @DisplayName("Deve mapear corretamente campos do pedido para o request")
    void deveMappearCorretamenteCamposDoPedidoParaORequest() {
        // Given
        pedido.setCodigo("CODIGO-ESPECIAL");
        pedido.setObservacao("Observação detalhada do pedido");
        pedido.setPreco(new BigDecimal("99.99"));

        // When
        MercadoPagoOrderRequest resultado = mapper.pedidoParaMercadoPagoOrderItemRequest(pedido);

        // Then
        assertEquals("CODIGO-ESPECIAL", resultado.getExternalReference());
        assertEquals("CODIGO-ESPECIAL", resultado.getTitle());
        assertEquals("Observação detalhada do pedido", resultado.getDescription());
        assertEquals(new BigDecimal("99.99"), resultado.getTotalAmount());
    }

    @Test
    @DisplayName("Deve definir unitMeasure como 'unit' para todos os itens")
    void deveDefinirUnitMeasureComoUnitParaTodosOsItens() {
        // When
        MercadoPagoOrderRequest resultado = mapper.pedidoParaMercadoPagoOrderItemRequest(pedido);

        // Then
        for (MercadoPagoOrderItemRequest item : resultado.getItems()) {
            assertEquals("unit", item.getUnitMeasure());
        }
    }

    @Test
    @DisplayName("Deve mapear produto com valores decimais precisos")
    void deveMappearProdutoComValoresDecimaisPrecisos() {
        // Given
        Produto produtoDecimal = new Produto();
        produtoDecimal.setCodigoProduto("PROD-DECIMAL");
        produtoDecimal.setNome("Produto Decimal");
        produtoDecimal.setDescricao("Produto com preço decimal");
        produtoDecimal.setCategoria("Teste");
        produtoDecimal.setPreco(new BigDecimal("12.345"));
        produtoDecimal.setQuantidade(3L);

        pedido.setProdutos(List.of(produtoDecimal));

        // When
        MercadoPagoOrderRequest resultado = mapper.pedidoParaMercadoPagoOrderItemRequest(pedido);

        // Then
        MercadoPagoOrderItemRequest item = resultado.getItems().getFirst();
        assertEquals(new BigDecimal("12.345"), item.getUnitPrice());
        assertEquals(new BigDecimal("37.035"), item.getTotalAmount()); // 12.345 * 3
    }

    @Test
    @DisplayName("Deve mapear produto com preço zero")
    void deveMappearProdutoComPrecoZero() {
        // Given
        Produto produtoGratuito = new Produto();
        produtoGratuito.setCodigoProduto("PROD-GRATIS");
        produtoGratuito.setNome("Produto Gratuito");
        produtoGratuito.setDescricao("Produto gratuito");
        produtoGratuito.setCategoria("Promoção");
        produtoGratuito.setPreco(BigDecimal.ZERO);
        produtoGratuito.setQuantidade(1L);

        pedido.setProdutos(List.of(produtoGratuito));

        // When
        MercadoPagoOrderRequest resultado = mapper.pedidoParaMercadoPagoOrderItemRequest(pedido);

        // Then
        MercadoPagoOrderItemRequest item = resultado.getItems().getFirst();
        assertEquals(BigDecimal.ZERO, item.getUnitPrice());
        assertEquals(BigDecimal.ZERO, item.getTotalAmount());
    }

    @Test
    @DisplayName("Deve preservar todos os campos do produto no mapeamento")
    void devePreservarTodosOsCamposDoProdutoNoMapeamento() {
        // Given
        Produto produtoCompleto = new Produto();
        produtoCompleto.setCodigoProduto("CODIGO-COMPLETO");
        produtoCompleto.setNome("Nome Completo");
        produtoCompleto.setDescricao("Descrição Completa e Detalhada");
        produtoCompleto.setCategoria("Categoria Específica");
        produtoCompleto.setPreco(new BigDecimal("123.45"));
        produtoCompleto.setQuantidade(5L);

        pedido.setProdutos(List.of(produtoCompleto));

        // When
        MercadoPagoOrderRequest resultado = mapper.pedidoParaMercadoPagoOrderItemRequest(pedido);

        // Then
        MercadoPagoOrderItemRequest item = resultado.getItems().getFirst();
        assertEquals("CODIGO-COMPLETO", item.getSkuNumber());
        assertEquals("Nome Completo", item.getTitle());
        assertEquals("Descrição Completa e Detalhada", item.getDescription());
        assertEquals("Categoria Específica", item.getCategory());
        assertEquals(new BigDecimal("123.45"), item.getUnitPrice());
        assertEquals(5L, item.getQuantity());
        assertEquals(new BigDecimal("617.25"), item.getTotalAmount()); // 123.45 * 5
        assertEquals("unit", item.getUnitMeasure());
    }
}
