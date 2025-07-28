package br.com.fiap.techchallengeapipagamento.pagamento.domain;

import br.com.fiap.techchallengeapipagamento.core.config.exception.StatusPagamentoInvalidoException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("StatusPagamentoEnum Domain Tests")
class StatusPagamentoEnumTest {

    @Test
    @DisplayName("Deve conter todos os status de pagamento esperados")
    void deveConterTodosOsStatusDePagamentoEsperados() {
        // Given
        StatusPagamentoEnum[] valoresEsperados = {
            StatusPagamentoEnum.PENDENTE,
            StatusPagamentoEnum.APROVADO,
            StatusPagamentoEnum.REJEITADO,
            StatusPagamentoEnum.CANCELADO
        };

        // When
        StatusPagamentoEnum[] valoresAtuais = StatusPagamentoEnum.values();

        // Then
        assertEquals(4, valoresAtuais.length);
        assertArrayEquals(valoresEsperados, valoresAtuais);
    }

    @Test
    @DisplayName("Deve retornar status correto para cada enum")
    void deveRetornarStatusCorretoParaCadaEnum() {
        // Then
        assertEquals("Pendente", StatusPagamentoEnum.PENDENTE.getStatus());
        assertEquals("Aprovado", StatusPagamentoEnum.APROVADO.getStatus());
        assertEquals("Rejeitado", StatusPagamentoEnum.REJEITADO.getStatus());
        assertEquals("Cancelado", StatusPagamentoEnum.CANCELADO.getStatus());
    }

    @Test
    @DisplayName("Deve converter string para enum com sucesso")
    void deveConverterStringParaEnumComSucesso() {
        // When & Then
        assertEquals(StatusPagamentoEnum.PENDENTE, StatusPagamentoEnum.fromString("Pendente"));
        assertEquals(StatusPagamentoEnum.APROVADO, StatusPagamentoEnum.fromString("Aprovado"));
        assertEquals(StatusPagamentoEnum.REJEITADO, StatusPagamentoEnum.fromString("Rejeitado"));
        assertEquals(StatusPagamentoEnum.CANCELADO, StatusPagamentoEnum.fromString("Cancelado"));
    }

    @Test
    @DisplayName("Deve ser case-insensitive na conversão")
    void deveSerCaseInsensitiveNaConversao() {
        // When & Then
        assertEquals(StatusPagamentoEnum.PENDENTE, StatusPagamentoEnum.fromString("pendente"));
        assertEquals(StatusPagamentoEnum.APROVADO, StatusPagamentoEnum.fromString("APROVADO"));
        assertEquals(StatusPagamentoEnum.REJEITADO, StatusPagamentoEnum.fromString("rejeitado"));
        assertEquals(StatusPagamentoEnum.CANCELADO, StatusPagamentoEnum.fromString("CaNcElAdO"));
    }

    @Test
    @DisplayName("Deve lançar exceção para status inválido")
    void deveLancarExcecaoParaStatusInvalido() {
        // Given
        String statusInvalido = "STATUS_INEXISTENTE";

        // When & Then
        StatusPagamentoInvalidoException exception = assertThrows(
            StatusPagamentoInvalidoException.class,
            () -> StatusPagamentoEnum.fromString(statusInvalido)
        );

        assertTrue(exception.getMessage().contains("Status de pagamento inválido: STATUS_INEXISTENTE"));
        assertTrue(exception.getMessage().contains("Status válidos: PENDENTE, APROVADO, REJEITADO, CANCELADO"));
    }

    @Test
    @DisplayName("Deve lançar exceção para string nula")
    void deveLancarExcecaoParaStringNula() {
        // When & Then
        StatusPagamentoInvalidoException exception = assertThrows(
            StatusPagamentoInvalidoException.class,
            () -> StatusPagamentoEnum.fromString(null)
        );

        assertTrue(exception.getMessage().contains("Status de pagamento inválido: null"));
    }

    @Test
    @DisplayName("Deve lançar exceção para string vazia")
    void deveLancarExcecaoParaStringVazia() {
        // When & Then
        StatusPagamentoInvalidoException exception = assertThrows(
            StatusPagamentoInvalidoException.class,
            () -> StatusPagamentoEnum.fromString("")
        );

        assertTrue(exception.getMessage().contains("Status de pagamento inválido: "));
    }

    @Test
    @DisplayName("Deve lançar exceção para string com espaços")
    void deveLancarExcecaoParaStringComEspacos() {
        // When & Then
        StatusPagamentoInvalidoException exception = assertThrows(
            StatusPagamentoInvalidoException.class,
            () -> StatusPagamentoEnum.fromString("   ")
        );

        assertTrue(exception.getMessage().contains("Status de pagamento inválido:    "));
    }

    @Test
    @DisplayName("Deve aceitar strings com espaços extras mas conteúdo válido")
    void deveAceitarStringsComEspacosExtrasConteudoValido() {
        // When & Then
        assertEquals(StatusPagamentoEnum.PENDENTE, StatusPagamentoEnum.fromString("Pendente"));
        assertEquals(StatusPagamentoEnum.APROVADO, StatusPagamentoEnum.fromString("Aprovado"));
    }

    @Test
    @DisplayName("Deve testar valueOf padrão do enum")
    void deveTestarValueOfPadraoDoEnum() {
        // When & Then
        assertEquals(StatusPagamentoEnum.PENDENTE, StatusPagamentoEnum.valueOf("PENDENTE"));
        assertEquals(StatusPagamentoEnum.APROVADO, StatusPagamentoEnum.valueOf("APROVADO"));
        assertEquals(StatusPagamentoEnum.REJEITADO, StatusPagamentoEnum.valueOf("REJEITADO"));
        assertEquals(StatusPagamentoEnum.CANCELADO, StatusPagamentoEnum.valueOf("CANCELADO"));
    }

    @Test
    @DisplayName("Deve manter consistência entre nome do enum e status")
    void deveManterConsistenciaEntreNomeDoEnumEStatus() {
        // When & Then
        for (StatusPagamentoEnum status : StatusPagamentoEnum.values()) {
            // Verifica se é possível converter o status de volta para o enum
            assertEquals(status, StatusPagamentoEnum.fromString(status.getStatus()));
        }
    }

    @Test
    @DisplayName("Deve testar ordinal dos enums")
    void deveTestarOrdinalDosEnums() {
        // When & Then
        assertEquals(0, StatusPagamentoEnum.PENDENTE.ordinal());
        assertEquals(1, StatusPagamentoEnum.APROVADO.ordinal());
        assertEquals(2, StatusPagamentoEnum.REJEITADO.ordinal());
        assertEquals(3, StatusPagamentoEnum.CANCELADO.ordinal());
    }

    @Test
    @DisplayName("Deve testar name dos enums")
    void deveTestarNameDosEnums() {
        // When & Then
        assertEquals("PENDENTE", StatusPagamentoEnum.PENDENTE.name());
        assertEquals("APROVADO", StatusPagamentoEnum.APROVADO.name());
        assertEquals("REJEITADO", StatusPagamentoEnum.REJEITADO.name());
        assertEquals("CANCELADO", StatusPagamentoEnum.CANCELADO.name());
    }

    @Test
    @DisplayName("Deve testar toString dos enums")
    void deveTestarToStringDosEnums() {
        // When & Then
        assertEquals("PENDENTE", StatusPagamentoEnum.PENDENTE.toString());
        assertEquals("APROVADO", StatusPagamentoEnum.APROVADO.toString());
        assertEquals("REJEITADO", StatusPagamentoEnum.REJEITADO.toString());
        assertEquals("CANCELADO", StatusPagamentoEnum.CANCELADO.toString());
    }

    @Test
    @DisplayName("Deve aceitar caracteres especiais similares")
    void deveAceitarCaracteresEspeciaisSimilares() {
        // Given - Testa alguns caracteres que podem ser confundidos
        String[] statusInvalidos = {"Pendénte", "Aprovàdo", "Rejèitado", "Canceládo"};

        // When & Then
        for (String statusInvalido : statusInvalidos) {
            assertThrows(StatusPagamentoInvalidoException.class,
                () -> StatusPagamentoEnum.fromString(statusInvalido)
            );
        }
    }
}
