package br.com.fiap.techchallengeapipagamento.core.config.exception.handler;

import br.com.fiap.techchallengeapipagamento.core.config.exception.domain.Problema;
import br.com.fiap.techchallengeapipagamento.core.config.exception.domain.ProblemaType;
import br.com.fiap.techchallengeapipagamento.core.config.exception.exceptions.EntidadeNaoEncontradaException;
import br.com.fiap.techchallengeapipagamento.core.config.exception.exceptions.NegocioException;
import br.com.fiap.techchallengeapipagamento.core.config.exception.exceptions.ValidacaoEntidadeException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.context.MessageSource;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@DisplayName("ApiExceptionHandler Tests")
class ApiExceptionHandlerTest {

    @InjectMocks
    private ApiExceptionHandler apiExceptionHandler;

    @Mock
    private MessageSource messageSource;

    @Mock
    private WebRequest webRequest;

    @Mock
    private BindingResult bindingResult;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Deve tratar Exception genérica e retornar erro interno do servidor")
    void deveTratarExceptionGenericaERetornarErroInternoDoServidor() {
        // Given
        Exception exception = new RuntimeException("Erro inesperado");

        // When
        ResponseEntity<Object> response = apiExceptionHandler.handleUncaught(exception, webRequest);

        // Then
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertNotNull(response.getBody());
        assertTrue(response.getBody() instanceof Problema);

        Problema problema = (Problema) response.getBody();
        assertEquals(500, problema.getStatus());
        assertEquals(ProblemaType.ERRO_SISTEMA.getUri(), problema.getTipo());
        assertEquals(ProblemaType.ERRO_SISTEMA.getTitulo(), problema.getTitulo());
    }

    @Test
    @DisplayName("Deve tratar EntidadeNaoEncontradaException e retornar NOT_FOUND")
    void deveTratarEntidadeNaoEncontradaExceptionERetornarNotFound() {
        // Given
        String mensagem = "Pagamento não encontrado";
        EntidadeNaoEncontradaException exception = new EntidadeNaoEncontradaException(mensagem);

        // When
        ResponseEntity<?> response = apiExceptionHandler.handleEntidadeNaoEncontrada(exception, webRequest);

        // Then
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNotNull(response.getBody());
        assertTrue(response.getBody() instanceof Problema);

        Problema problema = (Problema) response.getBody();
        assertEquals(404, problema.getStatus());
        assertEquals(ProblemaType.RECURSO_NAO_ENCONTRADO.getUri(), problema.getTipo());
        assertEquals(mensagem, problema.getDetalhe());
        assertEquals(mensagem, problema.getMensagemUsuario());
    }

    @Test
    @DisplayName("Deve tratar NegocioException e retornar BAD_REQUEST")
    void deveTratarNegocioExceptionERetornarBadRequest() {
        // Given
        String mensagem = "Regra de negócio violada";
        NegocioException exception = new NegocioException(mensagem);

        // When
        ResponseEntity<?> response = apiExceptionHandler.handleNegocio(exception, webRequest);

        // Then
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNotNull(response.getBody());
        assertTrue(response.getBody() instanceof Problema);

        Problema problema = (Problema) response.getBody();
        assertEquals(400, problema.getStatus());
        assertEquals(ProblemaType.VIOLACAO_REGRAS_NEGOCIO.getUri(), problema.getTipo());
        assertEquals(mensagem, problema.getDetalhe());
    }

    @Test
    @DisplayName("Deve tratar ValidacaoEntidadeException e retornar BAD_REQUEST")
    void deveTratarValidacaoEntidadeExceptionERetornarBadRequest() {
        // Given
        String mensagem = "Dados de validação inválidos";
        ValidacaoEntidadeException exception = new ValidacaoEntidadeException(mensagem);

        // When
        ResponseEntity<?> response = apiExceptionHandler.handleValidacaoEntidadeException(exception, webRequest);

        // Then
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNotNull(response.getBody());
        assertTrue(response.getBody() instanceof Problema);

        Problema problema = (Problema) response.getBody();
        assertEquals(400, problema.getStatus());
        assertEquals(ProblemaType.PARAMETRO_INVALIDO.getUri(), problema.getTipo());
        assertEquals(mensagem, problema.getDetalhe());
    }

    @Test
    @DisplayName("Deve tratar DataIntegrityViolationException e retornar CONFLICT")
    void deveTratarDataIntegrityViolationExceptionERetornarConflict() {
        // Given
        DataIntegrityViolationException exception = new DataIntegrityViolationException("Violação de integridade");

        // When
        ResponseEntity<Object> response = apiExceptionHandler.handleDataIntegrityViolation(exception, webRequest);

        // Then
        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
        assertNotNull(response.getBody());
        assertTrue(response.getBody() instanceof Problema);

        Problema problema = (Problema) response.getBody();
        assertEquals(409, problema.getStatus());
        assertEquals(ProblemaType.DADOS_INVALIDOS.getUri(), problema.getTipo());
    }

    @Test
    @DisplayName("Deve tratar NoResourceFoundException e retornar NOT_FOUND")
    void deveTratarNoResourceFoundExceptionERetornarNotFound() {
        // Given
        NoResourceFoundException exception = new NoResourceFoundException(null, "/api/test");
        when(webRequest.getDescription(false)).thenReturn("uri=/api/test");

        // When
        ResponseEntity<Object> response = apiExceptionHandler.handleNoResourceFoundException(
            exception, new HttpHeaders(), HttpStatus.NOT_FOUND, webRequest);

        // Then
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNotNull(response.getBody());
        assertTrue(response.getBody() instanceof Problema);

        Problema problema = (Problema) response.getBody();
        assertEquals(404, problema.getStatus());
        assertEquals(ProblemaType.RECURSO_NAO_ENCONTRADO.getUri(), problema.getTipo());
    }

    @Test
    @DisplayName("Deve tratar HttpRequestMethodNotSupportedException")
    void deveTratarHttpRequestMethodNotSupportedException() {
        // Given
        HttpRequestMethodNotSupportedException exception = new HttpRequestMethodNotSupportedException("POST");
        when(webRequest.getDescription(false)).thenReturn("uri=/api/pagamentos");

        // When
        ResponseEntity<Object> response = apiExceptionHandler.handleHttpRequestMethodNotSupported(
            exception, new HttpHeaders(), HttpStatus.METHOD_NOT_ALLOWED, webRequest);

        // Then
        assertEquals(HttpStatus.METHOD_NOT_ALLOWED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertTrue(response.getBody() instanceof Problema);

        Problema problema = (Problema) response.getBody();
        assertEquals(405, problema.getStatus());
        assertEquals(ProblemaType.METODO_NAO_PERMITIDO.getUri(), problema.getTipo());
    }

    @Test
    @DisplayName("Deve tratar HttpMessageNotReadableException")
    void deveTratarHttpMessageNotReadableException() {
        // Given
        HttpMessageNotReadableException exception = new HttpMessageNotReadableException("Mensagem não legível", (Throwable) null);

        // When
        ResponseEntity<Object> response = apiExceptionHandler.handleHttpMessageNotReadable(
            exception, new HttpHeaders(), HttpStatus.BAD_REQUEST, webRequest);

        // Then
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNotNull(response.getBody());
        assertTrue(response.getBody() instanceof Problema);

        Problema problema = (Problema) response.getBody();
        assertEquals(400, problema.getStatus());
        assertEquals(ProblemaType.MENSAGEM_INCOMPREENSIVEL.getUri(), problema.getTipo());
    }

    @Test
    @DisplayName("Deve criar problema com data e hora atual")
    void deveCriarProblemaComDataEHoraAtual() {
        // Given
        NegocioException exception = new NegocioException("Teste");

        // When
        ResponseEntity<?> response = apiExceptionHandler.handleNegocio(exception, webRequest);

        // Then
        Problema problema = (Problema) response.getBody();
        assertNotNull(problema.getDataHora());
    }

    @Test
    @DisplayName("Deve preservar mensagem de erro genérica para usuário final")
    void devePreservarMensagemDeErroGenericaParaUsuarioFinal() {
        // Given
        Exception exception = new RuntimeException("Erro interno");

        // When
        ResponseEntity<Object> response = apiExceptionHandler.handleUncaught(exception, webRequest);

        // Then
        Problema problema = (Problema) response.getBody();
        assertEquals(ApiExceptionHandler.MSG_ERRO_GENERICA_USUARIO_FINAL, problema.getMensagemUsuario());
    }
}
