package br.com.fiap.techchallengeapipagamento.core.config.exception;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("ErrorResponse Tests")
class ErrorResponseTest {

    @Test
    @DisplayName("Deve criar ErrorResponse com código e mensagem")
    void deveCriarErrorResponseComCodigoEMensagem() {
        // Given
        String code = "ERRO_001";
        String message = "Erro de validação";

        // When
        ErrorResponse errorResponse = new ErrorResponse(code, message);

        // Then
        assertEquals(code, errorResponse.getCode());
        assertEquals(message, errorResponse.getMessage());
    }

    @Test
    @DisplayName("Deve permitir valores nulos")
    void devePermitirValoresNulos() {
        // When
        ErrorResponse errorResponse = new ErrorResponse(null, null);

        // Then
        assertNull(errorResponse.getCode());
        assertNull(errorResponse.getMessage());
    }

    @Test
    @DisplayName("Deve permitir código nulo e mensagem válida")
    void devePermitirCodigoNuloEMensagemValida() {
        // Given
        String message = "Mensagem de erro";

        // When
        ErrorResponse errorResponse = new ErrorResponse(null, message);

        // Then
        assertNull(errorResponse.getCode());
        assertEquals(message, errorResponse.getMessage());
    }

    @Test
    @DisplayName("Deve permitir código válido e mensagem nula")
    void devePermitirCodigoValidoEMensagemNula() {
        // Given
        String code = "ERR_001";

        // When
        ErrorResponse errorResponse = new ErrorResponse(code, null);

        // Then
        assertEquals(code, errorResponse.getCode());
        assertNull(errorResponse.getMessage());
    }

    @Test
    @DisplayName("Deve permitir strings vazias")
    void devePermitirStringsVazias() {
        // When
        ErrorResponse errorResponse = new ErrorResponse("", "");

        // Then
        assertEquals("", errorResponse.getCode());
        assertEquals("", errorResponse.getMessage());
    }

    @Test
    @DisplayName("Deve ser possível alterar valores usando setters")
    void deveSerPossivelAlterarValoresUsandoSetters() {
        // Given
        ErrorResponse errorResponse = new ErrorResponse("CODE_INICIAL", "MENSAGEM_INICIAL");

        // When
        errorResponse.setCode("NOVO_CODE");
        errorResponse.setMessage("NOVA_MENSAGEM");

        // Then
        assertEquals("NOVO_CODE", errorResponse.getCode());
        assertEquals("NOVA_MENSAGEM", errorResponse.getMessage());
    }

    @Test
    @DisplayName("Deve implementar equals e hashCode corretamente")
    void deveImplementarEqualsEHashCodeCorretamente() {
        // Given
        ErrorResponse response1 = new ErrorResponse("CODE", "MESSAGE");
        ErrorResponse response2 = new ErrorResponse("CODE", "MESSAGE");
        ErrorResponse response3 = new ErrorResponse("OTHER", "MESSAGE");

        // Then
        assertEquals(response1, response2);
        assertEquals(response1.hashCode(), response2.hashCode());
        assertNotEquals(response1, response3);
    }

    @Test
    @DisplayName("Deve implementar toString corretamente")
    void deveImplementarToStringCorretamente() {
        // Given
        ErrorResponse errorResponse = new ErrorResponse("TEST_CODE", "Test message");

        // When
        String toString = errorResponse.toString();

        // Then
        assertNotNull(toString);
        assertTrue(toString.contains("TEST_CODE"));
        assertTrue(toString.contains("Test message"));
    }
}
