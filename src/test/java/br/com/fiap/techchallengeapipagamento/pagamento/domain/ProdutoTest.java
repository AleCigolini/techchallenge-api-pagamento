package br.com.fiap.techchallengeapipagamento.pagamento.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Produto Domain Tests")
class ProdutoTest {

    private Produto produto;

    @BeforeEach
    void setUp() {
        produto = new Produto();
    }

    @Test
    @DisplayName("Deve criar um produto com valores válidos")
    void deveCriarProdutoComValoresValidos() {
        // Given
        String codigoProduto = "PROD-123";
        String nome = "Hambúrguer Especial";
        String descricao = "Hambúrguer artesanal com ingredientes premium";
        String categoria = "Sanduíches";
        BigDecimal preco = new BigDecimal("25.90");
        Long quantidade = 2L;
        String observacao = "Sem cebola";

        // When
        produto.setCodigoProduto(codigoProduto);
        produto.setNome(nome);
        produto.setDescricao(descricao);
        produto.setCategoria(categoria);
        produto.setPreco(preco);
        produto.setQuantidade(quantidade);
        produto.setObservacao(observacao);

        // Then
        assertEquals(codigoProduto, produto.getCodigoProduto());
        assertEquals(nome, produto.getNome());
        assertEquals(descricao, produto.getDescricao());
        assertEquals(categoria, produto.getCategoria());
        assertEquals(preco, produto.getPreco());
        assertEquals(quantidade, produto.getQuantidade());
        assertEquals(observacao, produto.getObservacao());
    }

    @Test
    @DisplayName("Deve permitir valores nulos em todos os campos")
    void devePermitirValoresNulosEmTodosOsCampos() {
        // When
        produto.setCodigoProduto(null);
        produto.setNome(null);
        produto.setDescricao(null);
        produto.setCategoria(null);
        produto.setPreco(null);
        produto.setQuantidade(null);
        produto.setObservacao(null);

        // Then
        assertNull(produto.getCodigoProduto());
        assertNull(produto.getNome());
        assertNull(produto.getDescricao());
        assertNull(produto.getCategoria());
        assertNull(produto.getPreco());
        assertNull(produto.getQuantidade());
        assertNull(produto.getObservacao());
    }

    @Test
    @DisplayName("Deve aceitar preço zero")
    void deveAceitarPrecoZero() {
        // When
        produto.setPreco(BigDecimal.ZERO);

        // Then
        assertEquals(BigDecimal.ZERO, produto.getPreco());
    }

    @Test
    @DisplayName("Deve aceitar quantidade zero")
    void deveAceitarQuantidadeZero() {
        // When
        produto.setQuantidade(0L);

        // Then
        assertEquals(0L, produto.getQuantidade());
    }

    @Test
    @DisplayName("Deve aceitar valores decimais no preço")
    void deveAceitarValoresDecimaisNoPreco() {
        // Given
        BigDecimal precoDecimal = new BigDecimal("15.99");

        // When
        produto.setPreco(precoDecimal);

        // Then
        assertEquals(precoDecimal, produto.getPreco());
    }

    @Test
    @DisplayName("Deve aceitar quantidade negativa")
    void deveAceitarQuantidadeNegativa() {
        // When
        produto.setQuantidade(-1L);

        // Then
        assertEquals(-1L, produto.getQuantidade());
    }

    @Test
    @DisplayName("Deve testar equals e hashCode")
    void deveTestarEqualsEHashCode() {
        // Given
        Produto produto1 = new Produto();
        produto1.setCodigoProduto("PROD-123");
        produto1.setNome("Hambúrguer");
        produto1.setPreco(new BigDecimal("25.00"));

        Produto produto2 = new Produto();
        produto2.setCodigoProduto("PROD-123");
        produto2.setNome("Hambúrguer");
        produto2.setPreco(new BigDecimal("25.00"));

        Produto produto3 = new Produto();
        produto3.setCodigoProduto("PROD-456");
        produto3.setNome("Hambúrguer");
        produto3.setPreco(new BigDecimal("25.00"));

        // Then
        assertEquals(produto1, produto2);
        assertNotEquals(produto1, produto3);
        assertEquals(produto1.hashCode(), produto2.hashCode());
    }

    @Test
    @DisplayName("Deve aceitar diferentes categorias")
    void deveAceitarDiferentesCategorias() {
        // Given
        String[] categorias = {"Sanduíches", "Bebidas", "Sobremesas", "Acompanhamentos", "Saladas"};

        // When & Then
        for (String categoria : categorias) {
            produto.setCategoria(categoria);
            assertEquals(categoria, produto.getCategoria());
        }
    }

    @Test
    @DisplayName("Deve aceitar descrições longas")
    void deveAceitarDescricoesLongas() {
        // Given
        String descricaoLonga = "Este é um produto especial preparado com ingredientes selecionados, " +
                "incluindo carne 100% bovina, queijo artesanal, alface orgânica, tomate fresquinho, " +
                "cebola roxa, molho especial da casa, tudo servido em pão brioche tostado na hora.";

        // When
        produto.setDescricao(descricaoLonga);

        // Then
        assertEquals(descricaoLonga, produto.getDescricao());
    }

    @Test
    @DisplayName("Deve aceitar observações com caracteres especiais")
    void deveAceitarObservacoesComCaracteresEspeciais() {
        // Given
        String observacaoEspecial = "Sem cebola, extra queijo, molho à parte, bem passado! @#$%&*()";

        // When
        produto.setObservacao(observacaoEspecial);

        // Then
        assertEquals(observacaoEspecial, produto.getObservacao());
    }

    @Test
    @DisplayName("Deve aceitar códigos de produto com diferentes formatos")
    void deveAceitarCodigosProdutoComDiferentesFormatos() {
        // Given
        String[] formatosCodigos = {"PROD123", "prod-456", "P_789", "123456", "PROD.001.2023"};

        // When & Then
        for (String codigo : formatosCodigos) {
            produto.setCodigoProduto(codigo);
            assertEquals(codigo, produto.getCodigoProduto());
        }
    }

    @Test
    @DisplayName("Deve manter precisão de BigDecimal no preço")
    void deveManterPrecisaoBigDecimalNoPreco() {
        // Given
        BigDecimal precoComPrecisao = new BigDecimal("12.345678");

        // When
        produto.setPreco(precoComPrecisao);

        // Then
        assertEquals(precoComPrecisao, produto.getPreco());
        assertEquals("12.345678", produto.getPreco().toString());
    }

    @Test
    @DisplayName("Deve aceitar quantidade muito grande")
    void deveAceitarQuantidadeMuitoGrande() {
        // Given
        Long quantidadeGrande = Long.MAX_VALUE;

        // When
        produto.setQuantidade(quantidadeGrande);

        // Then
        assertEquals(quantidadeGrande, produto.getQuantidade());
    }

    @Test
    @DisplayName("Deve testar toString com Lombok")
    void deveTestarToStringComLombok() {
        // Given
        produto.setCodigoProduto("PROD-123");
        produto.setNome("Hambúrguer");
        produto.setCategoria("Sanduíches");

        // When
        String toStringResult = produto.toString();

        // Then
        assertNotNull(toStringResult);
        assertTrue(toStringResult.contains("PROD-123"));
        assertTrue(toStringResult.contains("Hambúrguer"));
        assertTrue(toStringResult.contains("Sanduíches"));
        assertTrue(toStringResult.contains("Produto"));
    }

    @Test
    @DisplayName("Deve manter estado após múltiplas modificações")
    void deveManterEstadoAposMultiplasModificacoes() {
        // Given
        String nomeOriginal = "Produto Original";
        String nomeNovo = "Produto Modificado";

        // When
        produto.setCodigoProduto("PROD-001");
        produto.setNome(nomeOriginal);
        produto.setPreco(new BigDecimal("10.00"));

        String estadoIntermediario = produto.getNome();

        produto.setNome(nomeNovo);
        produto.setQuantidade(5L);

        // Then
        assertEquals(nomeOriginal, estadoIntermediario);
        assertEquals(nomeNovo, produto.getNome());
        assertEquals("PROD-001", produto.getCodigoProduto());
        assertEquals(new BigDecimal("10.00"), produto.getPreco());
        assertEquals(5L, produto.getQuantidade());
    }
}
