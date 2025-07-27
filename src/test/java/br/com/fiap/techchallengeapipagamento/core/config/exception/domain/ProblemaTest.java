package br.com.fiap.techchallengeapipagamento.core.config.exception.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.OffsetDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Problema Tests")
class ProblemaTest {

    @Test
    @DisplayName("Deve criar Problema usando builder com todos os campos")
    void deveCriarProblemaUsandoBuilderComTodosCampos() {
        // Given
        Integer status = 400;
        String tipo = "https://techchallenge.com/dados-invalidos";
        String titulo = "Dados inválidos";
        String detalhe = "Um ou mais campos estão inválidos";
        String mensagemUsuario = "Corrija os campos e tente novamente";
        OffsetDateTime dataHora = OffsetDateTime.now();
        List<Problema.ErroAtributo> listaErros = Collections.singletonList(
            Problema.ErroAtributo.builder()
                .nomeAtributo("nome")
                .mensagemErro("Nome é obrigatório")
                .build()
        );

        // When
        Problema problema = Problema.builder()
            .status(status)
            .tipo(tipo)
            .titulo(titulo)
            .detalhe(detalhe)
            .mensagemUsuario(mensagemUsuario)
            .dataHora(dataHora)
            .listaErroAtributos(listaErros)
            .build();

        // Then
        assertEquals(status, problema.getStatus());
        assertEquals(tipo, problema.getTipo());
        assertEquals(titulo, problema.getTitulo());
        assertEquals(detalhe, problema.getDetalhe());
        assertEquals(mensagemUsuario, problema.getMensagemUsuario());
        assertEquals(dataHora, problema.getDataHora());
        assertEquals(listaErros, problema.getListaErroAtributos());
    }

    @Test
    @DisplayName("Deve criar Problema com campos nulos")
    void deveCriarProblemaComCamposNulos() {
        // When
        Problema problema = Problema.builder().build();

        // Then
        assertNull(problema.getStatus());
        assertNull(problema.getTipo());
        assertNull(problema.getTitulo());
        assertNull(problema.getDetalhe());
        assertNull(problema.getMensagemUsuario());
        assertNull(problema.getDataHora());
        assertNull(problema.getListaErroAtributos());
    }

    @Test
    @DisplayName("Deve criar Problema apenas com campos obrigatórios")
    void deveCriarProblemaApenasComCamposObrigatorios() {
        // Given
        Integer status = 404;
        String titulo = "Recurso não encontrado";

        // When
        Problema problema = Problema.builder()
            .status(status)
            .titulo(titulo)
            .build();

        // Then
        assertEquals(status, problema.getStatus());
        assertEquals(titulo, problema.getTitulo());
        assertNull(problema.getTipo());
        assertNull(problema.getDetalhe());
        assertNull(problema.getMensagemUsuario());
        assertNull(problema.getDataHora());
        assertNull(problema.getListaErroAtributos());
    }

    @Test
    @DisplayName("Deve ter anotação JsonInclude NON_NULL")
    void deveTerAnotacaoJsonIncludeNonNull() {
        // Given
        Class<Problema> clazz = Problema.class;

        // When
        JsonInclude jsonInclude = clazz.getAnnotation(JsonInclude.class);

        // Then
        assertNotNull(jsonInclude);
        assertEquals(JsonInclude.Include.NON_NULL, jsonInclude.value());
    }

    @Test
    @DisplayName("Deve ter anotação Schema")
    void deveTerAnotacaoSchema() {
        // Given
        Class<Problema> clazz = Problema.class;

        // When
        Schema schema = clazz.getAnnotation(Schema.class);

        // Then
        assertNotNull(schema);
        assertEquals("Problema", schema.name());
        assertEquals("Detalhes sobre o problema ocorrido", schema.description());
    }

    @Test
    @DisplayName("Deve criar ErroAtributo usando builder")
    void deveCriarErroAtributoUsandoBuilder() {
        // Given
        String nomeAtributo = "email";
        String mensagemErro = "Email deve ter formato válido";

        // When
        Problema.ErroAtributo erroAtributo = Problema.ErroAtributo.builder()
            .nomeAtributo(nomeAtributo)
            .mensagemErro(mensagemErro)
            .build();

        // Then
        assertEquals(nomeAtributo, erroAtributo.getNomeAtributo());
        assertEquals(mensagemErro, erroAtributo.getMensagemErro());
    }

    @Test
    @DisplayName("Deve criar ErroAtributo com campos nulos")
    void deveCriarErroAtributoComCamposNulos() {
        // When
        Problema.ErroAtributo erroAtributo = Problema.ErroAtributo.builder().build();

        // Then
        assertNull(erroAtributo.getNomeAtributo());
        assertNull(erroAtributo.getMensagemErro());
    }

    @Test
    @DisplayName("Deve ter anotação Schema na classe ErroAtributo")
    void deveTerAnotacaoSchemaNaClasseErroAtributo() {
        // Given
        Class<Problema.ErroAtributo> clazz = Problema.ErroAtributo.class;

        // When
        Schema schema = clazz.getAnnotation(Schema.class);

        // Then
        assertNotNull(schema);
        assertEquals("ErroAtributo", schema.name());
        assertEquals("Detalhes sobre os erros na validação de atributos", schema.description());
    }

    @Test
    @DisplayName("Deve criar Problema com lista de múltiplos erros")
    void deveCriarProblemaComListaDeMultiplosErros() {
        // Given
        List<Problema.ErroAtributo> listaErros = Arrays.asList(
            Problema.ErroAtributo.builder()
                .nomeAtributo("nome")
                .mensagemErro("Nome é obrigatório")
                .build(),
            Problema.ErroAtributo.builder()
                .nomeAtributo("email")
                .mensagemErro("Email deve ter formato válido")
                .build()
        );

        // When
        Problema problema = Problema.builder()
            .status(400)
            .titulo("Dados inválidos")
            .listaErroAtributos(listaErros)
            .build();

        // Then
        assertEquals(2, problema.getListaErroAtributos().size());
        assertEquals("nome", problema.getListaErroAtributos().get(0).getNomeAtributo());
        assertEquals("email", problema.getListaErroAtributos().get(1).getNomeAtributo());
    }

    @Test
    @DisplayName("Deve criar Problema com lista vazia de erros")
    void deveCriarProblemaComListaVaziaDeErros() {
        // When
        Problema problema = Problema.builder()
            .status(400)
            .titulo("Dados inválidos")
            .listaErroAtributos(Collections.emptyList())
            .build();

        // Then
        assertNotNull(problema.getListaErroAtributos());
        assertTrue(problema.getListaErroAtributos().isEmpty());
    }
}
