package br.com.fiap.techchallengeapipagamento.pagamento.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("StatusPedidoEnum Domain Tests")
class StatusPedidoEnumTest {

    @Test
    @DisplayName("Deve conter todos os status de pedido esperados")
    void deveConterTodosOsStatusDePedidoEsperados() {
        // Given
        StatusPedidoEnum[] valoresEsperados = {
            StatusPedidoEnum.ABERTO,
            StatusPedidoEnum.RECEBIDO,
            StatusPedidoEnum.EM_PREPARACAO,
            StatusPedidoEnum.PRONTO,
            StatusPedidoEnum.FINALIZADO,
            StatusPedidoEnum.CANCELADO
        };

        // When
        StatusPedidoEnum[] valoresAtuais = StatusPedidoEnum.values();

        // Then
        assertEquals(6, valoresAtuais.length);
        assertArrayEquals(valoresEsperados, valoresAtuais);
    }

    @Test
    @DisplayName("Deve retornar status correto para cada enum")
    void deveRetornarStatusCorretoParaCadaEnum() {
        // Then
        assertEquals("Aberto", StatusPedidoEnum.ABERTO.getStatus());
        assertEquals("Recebido", StatusPedidoEnum.RECEBIDO.getStatus());
        assertEquals("Em preparação", StatusPedidoEnum.EM_PREPARACAO.getStatus());
        assertEquals("Pronto", StatusPedidoEnum.PRONTO.getStatus());
        assertEquals("Finalizado", StatusPedidoEnum.FINALIZADO.getStatus());
        assertEquals("Cancelado", StatusPedidoEnum.CANCELADO.getStatus());
    }

    @Test
    @DisplayName("Deve converter string status para enum com fromStringStatus")
    void deveConverterStringStatusParaEnumComFromStringStatus() {
        // When & Then
        assertEquals(StatusPedidoEnum.ABERTO, StatusPedidoEnum.fromStringStatus("Aberto"));
        assertEquals(StatusPedidoEnum.RECEBIDO, StatusPedidoEnum.fromStringStatus("Recebido"));
        assertEquals(StatusPedidoEnum.EM_PREPARACAO, StatusPedidoEnum.fromStringStatus("Em preparação"));
        assertEquals(StatusPedidoEnum.PRONTO, StatusPedidoEnum.fromStringStatus("Pronto"));
        assertEquals(StatusPedidoEnum.FINALIZADO, StatusPedidoEnum.fromStringStatus("Finalizado"));
        assertEquals(StatusPedidoEnum.CANCELADO, StatusPedidoEnum.fromStringStatus("Cancelado"));
    }

    @Test
    @DisplayName("Deve converter string nome para enum com fromStatus")
    void deveConverterStringNomeParaEnumComFromStatus() {
        // When & Then
        assertEquals(StatusPedidoEnum.ABERTO, StatusPedidoEnum.fromStatus("ABERTO"));
        assertEquals(StatusPedidoEnum.RECEBIDO, StatusPedidoEnum.fromStatus("RECEBIDO"));
        assertEquals(StatusPedidoEnum.EM_PREPARACAO, StatusPedidoEnum.fromStatus("EM_PREPARACAO"));
        assertEquals(StatusPedidoEnum.PRONTO, StatusPedidoEnum.fromStatus("PRONTO"));
        assertEquals(StatusPedidoEnum.FINALIZADO, StatusPedidoEnum.fromStatus("FINALIZADO"));
        assertEquals(StatusPedidoEnum.CANCELADO, StatusPedidoEnum.fromStatus("CANCELADO"));
    }

    @Test
    @DisplayName("Deve retornar null para status inválido em fromStringStatus")
    void deveRetornarNullParaStatusInvalidoEmFromStringStatus() {
        // When & Then
        assertNull(StatusPedidoEnum.fromStringStatus("Status Inexistente"));
        assertNull(StatusPedidoEnum.fromStringStatus("aberto")); // case sensitive
        assertNull(StatusPedidoEnum.fromStringStatus("ABERTO")); // case sensitive
        assertNull(StatusPedidoEnum.fromStringStatus(""));
        assertNull(StatusPedidoEnum.fromStringStatus(null));
    }

    @Test
    @DisplayName("Deve retornar null para status inválido em fromStatus")
    void deveRetornarNullParaStatusInvalidoEmFromStatus() {
        // When & Then
        assertNull(StatusPedidoEnum.fromStatus("Status Inexistente"));
        assertNull(StatusPedidoEnum.fromStatus("aberto")); // case sensitive
        assertNull(StatusPedidoEnum.fromStatus("Aberto")); // case sensitive
        assertNull(StatusPedidoEnum.fromStatus(""));
        assertNull(StatusPedidoEnum.fromStatus(null));
    }

    @Test
    @DisplayName("Deve ser case sensitive em fromStringStatus")
    void deveSerCaseSensitiveEmFromStringStatus() {
        // When & Then
        assertNull(StatusPedidoEnum.fromStringStatus("aberto"));
        assertNull(StatusPedidoEnum.fromStringStatus("ABERTO"));
        assertNull(StatusPedidoEnum.fromStringStatus("AbErTo"));

        // Apenas o formato exato deve funcionar
        assertEquals(StatusPedidoEnum.ABERTO, StatusPedidoEnum.fromStringStatus("Aberto"));
    }

    @Test
    @DisplayName("Deve ser case sensitive em fromStatus")
    void deveSerCaseSensitiveEmFromStatus() {
        // When & Then
        assertNull(StatusPedidoEnum.fromStatus("aberto"));
        assertNull(StatusPedidoEnum.fromStatus("Aberto"));
        assertNull(StatusPedidoEnum.fromStatus("AbErTo"));

        // Apenas o formato exato deve funcionar
        assertEquals(StatusPedidoEnum.ABERTO, StatusPedidoEnum.fromStatus("ABERTO"));
    }

    @Test
    @DisplayName("Deve testar JsonValue annotation")
    void deveTestarJsonValueAnnotation() {
        // O metodo getStatus() deve retornar o valor usado na serialização JSON
        // When & Then
        assertEquals("Aberto", StatusPedidoEnum.ABERTO.getStatus());
        assertEquals("Em preparação", StatusPedidoEnum.EM_PREPARACAO.getStatus());
    }

    @Test
    @DisplayName("Deve testar valueOf padrão do enum")
    void deveTestarValueOfPadraoDoEnum() {
        // When & Then
        assertEquals(StatusPedidoEnum.ABERTO, StatusPedidoEnum.valueOf("ABERTO"));
        assertEquals(StatusPedidoEnum.RECEBIDO, StatusPedidoEnum.valueOf("RECEBIDO"));
        assertEquals(StatusPedidoEnum.EM_PREPARACAO, StatusPedidoEnum.valueOf("EM_PREPARACAO"));
        assertEquals(StatusPedidoEnum.PRONTO, StatusPedidoEnum.valueOf("PRONTO"));
        assertEquals(StatusPedidoEnum.FINALIZADO, StatusPedidoEnum.valueOf("FINALIZADO"));
        assertEquals(StatusPedidoEnum.CANCELADO, StatusPedidoEnum.valueOf("CANCELADO"));
    }

    @Test
    @DisplayName("Deve testar ordinal dos enums")
    void deveTestarOrdinalDosEnums() {
        // When & Then
        assertEquals(0, StatusPedidoEnum.ABERTO.ordinal());
        assertEquals(1, StatusPedidoEnum.RECEBIDO.ordinal());
        assertEquals(2, StatusPedidoEnum.EM_PREPARACAO.ordinal());
        assertEquals(3, StatusPedidoEnum.PRONTO.ordinal());
        assertEquals(4, StatusPedidoEnum.FINALIZADO.ordinal());
        assertEquals(5, StatusPedidoEnum.CANCELADO.ordinal());
    }

    @Test
    @DisplayName("Deve testar name dos enums")
    void deveTestarNameDosEnums() {
        // When & Then
        assertEquals("ABERTO", StatusPedidoEnum.ABERTO.name());
        assertEquals("RECEBIDO", StatusPedidoEnum.RECEBIDO.name());
        assertEquals("EM_PREPARACAO", StatusPedidoEnum.EM_PREPARACAO.name());
        assertEquals("PRONTO", StatusPedidoEnum.PRONTO.name());
        assertEquals("FINALIZADO", StatusPedidoEnum.FINALIZADO.name());
        assertEquals("CANCELADO", StatusPedidoEnum.CANCELADO.name());
    }

    @Test
    @DisplayName("Deve testar toString dos enums")
    void deveTestarToStringDosEnums() {
        // When & Then
        assertEquals("ABERTO", StatusPedidoEnum.ABERTO.toString());
        assertEquals("RECEBIDO", StatusPedidoEnum.RECEBIDO.toString());
        assertEquals("EM_PREPARACAO", StatusPedidoEnum.EM_PREPARACAO.toString());
        assertEquals("PRONTO", StatusPedidoEnum.PRONTO.toString());
        assertEquals("FINALIZADO", StatusPedidoEnum.FINALIZADO.toString());
        assertEquals("CANCELADO", StatusPedidoEnum.CANCELADO.toString());
    }

    @Test
    @DisplayName("Deve testar consistência entre métodos de conversão")
    void deveTestarConsistenciaEntreMetodosDeConversao() {
        // Para cada enum, teste se os métodos são consistentes
        for (StatusPedidoEnum status : StatusPedidoEnum.values()) {
            // fromStringStatus deve funcionar com getStatus()
            assertEquals(status, StatusPedidoEnum.fromStringStatus(status.getStatus()));

            // fromStatus deve funcionar com toString()
            assertEquals(status, StatusPedidoEnum.fromStatus(status.toString()));
        }
    }

    @Test
    @DisplayName("Deve tratar strings com espaços extras")
    void deveTratarStringsComEspacosExtras() {
        // fromStringStatus e fromStatus são exatos, não removem espaços
        // When & Then
        assertNull(StatusPedidoEnum.fromStringStatus(" Aberto "));
        assertNull(StatusPedidoEnum.fromStringStatus("Aberto "));
        assertNull(StatusPedidoEnum.fromStringStatus(" Aberto"));

        assertNull(StatusPedidoEnum.fromStatus(" ABERTO "));
        assertNull(StatusPedidoEnum.fromStatus("ABERTO "));
        assertNull(StatusPedidoEnum.fromStatus(" ABERTO"));
    }

    @Test
    @DisplayName("Deve testar fluxo completo de status de pedido")
    void deveTestarFluxoCompletoDeStatusDePedido() {
        // Teste representando um fluxo típico de status de pedido
        StatusPedidoEnum[] fluxoEsperado = {
            StatusPedidoEnum.ABERTO,
            StatusPedidoEnum.RECEBIDO,
            StatusPedidoEnum.EM_PREPARACAO,
            StatusPedidoEnum.PRONTO,
            StatusPedidoEnum.FINALIZADO
        };

        // When & Then
        for (int i = 0; i < fluxoEsperado.length - 1; i++) {
            StatusPedidoEnum statusAtual = fluxoEsperado[i];
            StatusPedidoEnum proximoStatus = fluxoEsperado[i + 1];

            // Verifica se o ordinal está em sequência
            assertEquals(statusAtual.ordinal() + 1, proximoStatus.ordinal());
        }
    }

    @Test
    @DisplayName("Deve verificar diferença entre name e status")
    void deveVerificarDiferencaEntreNameEStatus() {
        // name() retorna o nome da constante, getStatus() retorna o valor legível
        // When & Then
        assertNotEquals(StatusPedidoEnum.EM_PREPARACAO.name(), StatusPedidoEnum.EM_PREPARACAO.getStatus());
        assertEquals("EM_PREPARACAO", StatusPedidoEnum.EM_PREPARACAO.name());
        assertEquals("Em preparação", StatusPedidoEnum.EM_PREPARACAO.getStatus());
    }
}
