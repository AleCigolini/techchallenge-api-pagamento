package br.com.fiap.techchallengeapipagamento.pagamento.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Pagamento Domain Tests")
class PagamentoTest {

    private Pagamento pagamento;

    @BeforeEach
    void setUp() {
        pagamento = new Pagamento();
    }

    @Test
    @DisplayName("Deve criar um pagamento com valores válidos")
    void deveCriarPagamentoComValoresValidos() {
        // Given
        String id = "pag-123";
        String codigoPedido = "ped-456";
        BigDecimal preco = new BigDecimal("50.00");
        String status = "APROVADO";

        // When
        pagamento.setId(id);
        pagamento.setCodigoPedido(codigoPedido);
        pagamento.setPreco(preco);
        pagamento.setStatus(status);

        // Then
        assertEquals(id, pagamento.getId());
        assertEquals(codigoPedido, pagamento.getCodigoPedido());
        assertEquals(preco, pagamento.getPreco());
        assertEquals(status, pagamento.getStatus());
    }

    @Test
    @DisplayName("Deve permitir valores nulos")
    void devePermitirValoresNulos() {
        // When
        pagamento.setId(null);
        pagamento.setCodigoPedido(null);
        pagamento.setPreco(null);
        pagamento.setStatus(null);

        // Then
        assertNull(pagamento.getId());
        assertNull(pagamento.getCodigoPedido());
        assertNull(pagamento.getPreco());
        assertNull(pagamento.getStatus());
    }

    @Test
    @DisplayName("Deve aceitar valor zero no preço")
    void deveAceitarValorZeroNoPreco() {
        // Given
        BigDecimal precoZero = BigDecimal.ZERO;

        // When
        pagamento.setPreco(precoZero);

        // Then
        assertEquals(BigDecimal.ZERO, pagamento.getPreco());
    }

    @Test
    @DisplayName("Deve aceitar valores decimais no preço")
    void deveAceitarValoresDecimaisNoPreco() {
        // Given
        BigDecimal precoDecimal = new BigDecimal("99.99");

        // When
        pagamento.setPreco(precoDecimal);

        // Then
        assertEquals(precoDecimal, pagamento.getPreco());
    }

    @Test
    @DisplayName("Deve manter precisão de BigDecimal")
    void deveManterPrecisaoBigDecimal() {
        // Given
        BigDecimal precoComPrecisao = new BigDecimal("123.456789");

        // When
        pagamento.setPreco(precoComPrecisao);

        // Then
        assertEquals(precoComPrecisao, pagamento.getPreco());
        assertEquals("123.456789", pagamento.getPreco().toString());
    }

    @Test
    @DisplayName("Deve testar equals e hashCode com Lombok")
    void deveTestarEqualsEHashCodeComLombok() {
        // Given
        Pagamento pagamento1 = new Pagamento();
        pagamento1.setId("pag-123");
        pagamento1.setCodigoPedido("ped-456");
        pagamento1.setPreco(new BigDecimal("50.00"));
        pagamento1.setStatus("APROVADO");

        Pagamento pagamento2 = new Pagamento();
        pagamento2.setId("pag-123");
        pagamento2.setCodigoPedido("ped-456");
        pagamento2.setPreco(new BigDecimal("50.00"));
        pagamento2.setStatus("APROVADO");

        Pagamento pagamento3 = new Pagamento();
        pagamento3.setId("pag-789");
        pagamento3.setCodigoPedido("ped-456");
        pagamento3.setPreco(new BigDecimal("50.00"));
        pagamento3.setStatus("APROVADO");

        // Then
        assertEquals(pagamento1, pagamento2);
        assertNotEquals(pagamento1, pagamento3);
        assertEquals(pagamento1.hashCode(), pagamento2.hashCode());
        assertNotEquals(pagamento1.hashCode(), pagamento3.hashCode());
    }

    @Test
    @DisplayName("Deve testar toString com Lombok")
    void deveTestarToStringComLombok() {
        // Given
        pagamento.setId("pag-123");
        pagamento.setCodigoPedido("ped-456");
        pagamento.setStatus("APROVADO");

        // When
        String toStringResult = pagamento.toString();

        // Then
        assertNotNull(toStringResult);
        assertTrue(toStringResult.contains("pag-123"));
        assertTrue(toStringResult.contains("ped-456"));
        assertTrue(toStringResult.contains("APROVADO"));
        assertTrue(toStringResult.contains("Pagamento"));
    }

    @Test
    @DisplayName("Deve aceitar diferentes status")
    void deveAceitarDiferentesStatus() {
        // Given
        String[] statusList = {"PENDENTE", "APROVADO", "REJEITADO", "CANCELADO"};

        // When & Then
        for (String status : statusList) {
            pagamento.setStatus(status);
            assertEquals(status, pagamento.getStatus());
        }
    }

    @Test
    @DisplayName("Deve aceitar código de pedido com diferentes formatos")
    void deveAceitarCodigoPedidoComDiferentesFormatos() {
        // Given
        String[] codigosPedido = {"PED123", "pedido-456", "PEDIDO_789", "123456789"};

        // When & Then
        for (String codigo : codigosPedido) {
            pagamento.setCodigoPedido(codigo);
            assertEquals(codigo, pagamento.getCodigoPedido());
        }
    }

    @Test
    @DisplayName("Deve manter estado após múltiplas modificações")
    void deveManterEstadoAposMultiplasModificacoes() {
        // Given
        String idOriginal = "pag-original";
        String idNovo = "pag-novo";

        // When
        pagamento.setId(idOriginal);
        pagamento.setCodigoPedido("ped-123");
        pagamento.setPreco(new BigDecimal("100.00"));

        String estadoIntermediario = pagamento.getId();

        pagamento.setId(idNovo);
        pagamento.setStatus("APROVADO");

        // Then
        assertEquals(idOriginal, estadoIntermediario);
        assertEquals(idNovo, pagamento.getId());
        assertEquals("ped-123", pagamento.getCodigoPedido());
        assertEquals(new BigDecimal("100.00"), pagamento.getPreco());
        assertEquals("APROVADO", pagamento.getStatus());
    }
}
