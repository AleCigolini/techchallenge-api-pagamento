package br.com.fiap.techchallengeapipagamento.core.config.exception.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("ProblemaType Tests")
class ProblemaTypeTest {

    @Test
    @DisplayName("Deve ter todos os tipos de problema definidos")
    void deveTerTodosTiposDeProblemaDefinidos() {
        // When
        ProblemaType[] tipos = ProblemaType.values();

        // Then
        assertEquals(8, tipos.length);
        assertNotNull(ProblemaType.DADOS_INVALIDOS);
        assertNotNull(ProblemaType.ERRO_SISTEMA);
        assertNotNull(ProblemaType.PARAMETRO_INVALIDO);
        assertNotNull(ProblemaType.MENSAGEM_INCOMPREENSIVEL);
        assertNotNull(ProblemaType.RECURSO_NAO_ENCONTRADO);
        assertNotNull(ProblemaType.ENTIDADE_EM_USO);
        assertNotNull(ProblemaType.VIOLACAO_REGRAS_NEGOCIO);
        assertNotNull(ProblemaType.METODO_NAO_PERMITIDO);
    }

    @Test
    @DisplayName("Deve ter URI e título corretos para DADOS_INVALIDOS")
    void deveTerUriETituloCorretosParaDadosInvalidos() {
        // When
        ProblemaType tipo = ProblemaType.DADOS_INVALIDOS;

        // Then
        assertEquals("dados-invalidos", tipo.getUri());
        assertEquals("Os dados fornecidos são inválidos.", tipo.getTitulo());
    }

    @Test
    @DisplayName("Deve ter URI e título corretos para ERRO_SISTEMA")
    void deveTerUriETituloCorretosParaErroSistema() {
        // When
        ProblemaType tipo = ProblemaType.ERRO_SISTEMA;

        // Then
        assertEquals("erro-sistema", tipo.getUri());
        assertEquals("Ocorreu um erro interno no sistema.", tipo.getTitulo());
    }

    @Test
    @DisplayName("Deve ter URI e título corretos para PARAMETRO_INVALIDO")
    void deveTerUriETituloCorretosParaParametroInvalido() {
        // When
        ProblemaType tipo = ProblemaType.PARAMETRO_INVALIDO;

        // Then
        assertEquals("parametro-invalido", tipo.getUri());
        assertEquals("Um ou mais parâmetros fornecidos são inválidos.", tipo.getTitulo());
    }

    @Test
    @DisplayName("Deve ter URI e título corretos para MENSAGEM_INCOMPREENSIVEL")
    void deveTerUriETituloCorretosParaMensagemIncompreensivel() {
        // When
        ProblemaType tipo = ProblemaType.MENSAGEM_INCOMPREENSIVEL;

        // Then
        assertEquals("mensagem-incompreensivel", tipo.getUri());
        assertEquals("A mensagem enviada não pôde ser processada.", tipo.getTitulo());
    }

    @Test
    @DisplayName("Deve ter URI e título corretos para RECURSO_NAO_ENCONTRADO")
    void deveTerUriETituloCorretosParaRecursoNaoEncontrado() {
        // When
        ProblemaType tipo = ProblemaType.RECURSO_NAO_ENCONTRADO;

        // Then
        assertEquals("recurso-nao-encontrado", tipo.getUri());
        assertEquals("O recurso solicitado não foi encontrado.", tipo.getTitulo());
    }

    @Test
    @DisplayName("Deve ter URI e título corretos para ENTIDADE_EM_USO")
    void deveTerUriETituloCorretosParaEntidadeEmUso() {
        // When
        ProblemaType tipo = ProblemaType.ENTIDADE_EM_USO;

        // Then
        assertEquals("entidade-em-uso", tipo.getUri());
        assertEquals("Não é possível concluir a operação, pois a entidade está em uso.", tipo.getTitulo());
    }

    @Test
    @DisplayName("Deve ter URI e título corretos para VIOLACAO_REGRAS_NEGOCIO")
    void deveTerUriETituloCorretosParaViolacaoRegrasNegocio() {
        // When
        ProblemaType tipo = ProblemaType.VIOLACAO_REGRAS_NEGOCIO;

        // Then
        assertEquals("violacao-regras-negocio", tipo.getUri());
        assertEquals("Uma regra de negócio foi violada.", tipo.getTitulo());
    }

    @Test
    @DisplayName("Deve ter URI e título corretos para METODO_NAO_PERMITIDO")
    void deveTerUriETituloCorretosParaMetodoNaoPermitido() {
        // When
        ProblemaType tipo = ProblemaType.METODO_NAO_PERMITIDO;

        // Then
        assertEquals("metodo-nao-permitido", tipo.getUri());
        assertEquals("O método HTTP utilizado não é permitido para este recurso.", tipo.getTitulo());
    }

    @Test
    @DisplayName("Deve ser possível obter enum pelo valueOf")
    void deveSerPossivelObterEnumPeloValueOf() {
        // When
        ProblemaType tipo = ProblemaType.valueOf("DADOS_INVALIDOS");

        // Then
        assertEquals(ProblemaType.DADOS_INVALIDOS, tipo);
    }

    @Test
    @DisplayName("Deve lançar exceção para valor inválido no valueOf")
    void deveLancarExcecaoParaValorInvalidoNoValueOf() {
        // When & Then
        assertThrows(IllegalArgumentException.class, () -> {
            ProblemaType.valueOf("TIPO_INEXISTENTE");
        });
    }

    @Test
    @DisplayName("Deve ter getters funcionais")
    void deveTerGettersFuncionais() {
        // Given
        ProblemaType tipo = ProblemaType.DADOS_INVALIDOS;

        // When & Then
        assertNotNull(tipo.getUri());
        assertNotNull(tipo.getTitulo());
        assertFalse(tipo.getUri().isEmpty());
        assertFalse(tipo.getTitulo().isEmpty());
    }
}
