package br.com.fiap.techchallengeapipagamento.core.config.exception.exceptions;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("ValidacaoEntidadeException Tests")
class ValidacaoEntidadeExceptionTest {

    @Test
    @DisplayName("Deve criar exceção com mensagem")
    void deveCriarExcecaoComMensagem() {
        // Given
        String mensagem = "Erro de validação da entidade";

        // When
        ValidacaoEntidadeException exception = new ValidacaoEntidadeException(mensagem);

        // Then
        assertEquals(mensagem, exception.getMessage());
    }

    @Test
    @DisplayName("Deve herdar de NegocioException")
    void deveHerdarDeNegocioException() {
        // Given
        ValidacaoEntidadeException exception = new ValidacaoEntidadeException("Teste");

        // Then
        assertTrue(exception instanceof NegocioException);
    }

    @Test
    @DisplayName("Deve ter anotação ResponseStatus com BAD_REQUEST")
    void deveTerAnotacaoResponseStatusComBadRequest() {
        // Given
        Class<ValidacaoEntidadeException> clazz = ValidacaoEntidadeException.class;

        // When
        ResponseStatus responseStatus = clazz.getAnnotation(ResponseStatus.class);

        // Then
        assertNotNull(responseStatus);
        assertEquals(HttpStatus.BAD_REQUEST, responseStatus.value());
    }

    @Test
    @DisplayName("Deve permitir mensagem nula")
    void devePermitirMensagemNula() {
        // When
        ValidacaoEntidadeException exception = new ValidacaoEntidadeException(null);

        // Then
        assertNull(exception.getMessage());
    }

    @Test
    @DisplayName("Deve permitir mensagem vazia")
    void devePermitirMensagemVazia() {
        // When
        ValidacaoEntidadeException exception = new ValidacaoEntidadeException("");

        // Then
        assertEquals("", exception.getMessage());
    }

    @Test
    @DisplayName("Deve ser possível capturar como NegocioException")
    void deveSerPossivelCapturarComoNegocioException() {
        // Given
        String mensagem = "Campo obrigatório não informado";

        // When & Then
        assertThrows(NegocioException.class, () -> {
            throw new ValidacaoEntidadeException(mensagem);
        });
    }

    @Test
    @DisplayName("Deve preservar mensagem ao ser lançada e capturada")
    void devePreservarMensagemAoSerLancadaECapturada() {
        // Given
        String mensagem = "Preço deve ser maior que zero";

        // When & Then
        NegocioException exception = assertThrows(ValidacaoEntidadeException.class, () -> {
            throw new ValidacaoEntidadeException(mensagem);
        });

        assertEquals(mensagem, exception.getMessage());
    }

    @Test
    @DisplayName("Deve ser possível capturar como RuntimeException")
    void deveSerPossivelCapturarComoRuntimeException() {
        // Given
        String mensagem = "Dados da entidade inválidos";

        // When & Then
        assertThrows(RuntimeException.class, () -> {
            throw new ValidacaoEntidadeException(mensagem);
        });
    }
}
