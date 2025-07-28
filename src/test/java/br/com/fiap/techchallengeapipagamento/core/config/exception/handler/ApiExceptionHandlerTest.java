package br.com.fiap.techchallengeapipagamento.core.config.exception.handler;

import br.com.fiap.techchallengeapipagamento.core.config.exception.domain.Problema;
import br.com.fiap.techchallengeapipagamento.core.config.exception.exceptions.EntidadeNaoEncontradaException;
import br.com.fiap.techchallengeapipagamento.core.config.exception.exceptions.NegocioException;
import br.com.fiap.techchallengeapipagamento.core.config.exception.exceptions.ValidacaoEntidadeException;
import com.fasterxml.jackson.databind.JsonMappingException.Reference;
import com.fasterxml.jackson.databind.exc.PropertyBindingException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.MessageSource;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.validation.method.ParameterValidationResult;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.HandlerMethodValidationException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ApiExceptionHandlerTest {

    private ApiExceptionHandler apiExceptionHandler;

    @Mock
    private MessageSource messageSource;

    @Mock
    private WebRequest webRequest;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        apiExceptionHandler = new ApiExceptionHandler();
        ReflectionTestUtils.setField(apiExceptionHandler, "messageSource", messageSource);

        when(webRequest.getDescription(false)).thenReturn("uri=/test");
    }

    @AfterEach
    public void tearDown() {
        Mockito.reset(messageSource, webRequest);
    }

    @Test
    public void deveHandleExceptionQuandoOcorrerErroGenerico() {
        // arrange
        Exception exception = new RuntimeException("Erro genérico");

        // when
        ResponseEntity<Object> response = apiExceptionHandler.handleUncaught(exception, webRequest);

        // then
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertNotNull(response.getBody());
        assertInstanceOf(Problema.class, response.getBody());

        Problema problema = (Problema) response.getBody();
        assertEquals(500, problema.getStatus());
        assertEquals(ApiExceptionHandler.MSG_ERRO_GENERICA_USUARIO_FINAL, problema.getMensagemUsuario());
    }

    @Test
    public void deveHandleEntidadeNaoEncontradaException() {
        // arrange
        EntidadeNaoEncontradaException exception = new EntidadeNaoEncontradaException("Entidade não encontrada");

        // when
        ResponseEntity<?> response = apiExceptionHandler.handleEntidadeNaoEncontrada(exception, webRequest);

        // then
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNotNull(response.getBody());
        assertInstanceOf(Problema.class, response.getBody());

        Problema problema = (Problema) response.getBody();
        assertEquals(404, problema.getStatus());
        assertEquals("Entidade não encontrada", problema.getMensagemUsuario());
    }

    @Test
    public void deveHandleNegocioException() {
        // arrange
        NegocioException exception = new NegocioException("Erro de negócio");

        // when
        ResponseEntity<?> response = apiExceptionHandler.handleNegocio(exception, webRequest);

        // then
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNotNull(response.getBody());
        assertInstanceOf(Problema.class, response.getBody());

        Problema problema = (Problema) response.getBody();
        assertEquals(400, problema.getStatus());
        assertEquals("Erro de negócio", problema.getMensagemUsuario());
    }

    @Test
    public void deveHandleValidacaoEntidadeException() {
        // arrange
        ValidacaoEntidadeException exception = new ValidacaoEntidadeException("Erro de validação");

        // when
        ResponseEntity<?> response = apiExceptionHandler.handleValidacaoEntidadeException(exception, webRequest);

        // then
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNotNull(response.getBody());
        assertInstanceOf(Problema.class, response.getBody());

        Problema problema = (Problema) response.getBody();
        assertEquals(400, problema.getStatus());
        assertEquals("Erro de validação", problema.getMensagemUsuario());
    }

    @Test
    public void deveHandleDataIntegrityViolation() {
        // arrange
        DataIntegrityViolationException exception = new DataIntegrityViolationException("Erro de integridade");

        // when
        ResponseEntity<Object> response = apiExceptionHandler.handleDataIntegrityViolation(exception, webRequest);

        // then
        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
        assertNotNull(response.getBody());
        assertInstanceOf(Problema.class, response.getBody());

        Problema problema = (Problema) response.getBody();
        assertEquals(409, problema.getStatus());
        assertEquals(ApiExceptionHandler.MSG_ERRO_GENERICA_USUARIO_FINAL, problema.getMensagemUsuario());
    }

    @Test
    public void deveHandleNoResourceFoundException() {
        // arrange
        NoResourceFoundException exception = new NoResourceFoundException(HttpMethod.GET, "/recurso-nao-encontrado");

        // when
        ResponseEntity<Object> response = apiExceptionHandler.handleNoResourceFoundException(exception, new HttpHeaders(), HttpStatus.NOT_FOUND, webRequest);

        // then
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNotNull(response.getBody());
        assertInstanceOf(Problema.class, response.getBody());

        Problema problema = (Problema) response.getBody();
        assertEquals(404, problema.getStatus());
        assertEquals(ApiExceptionHandler.MSG_ERRO_GENERICA_USUARIO_FINAL, problema.getMensagemUsuario());
    }

    @Test
    public void deveHandleMethodArgumentNotValid() {
        // arrange
        MethodArgumentNotValidException exception = mock(MethodArgumentNotValidException.class);
        BindingResult bindingResult = mock(BindingResult.class);

        FieldError fieldError = new FieldError("objeto", "campo", "mensagem de erro");
        List<FieldError> fieldErrors = new ArrayList<>();
        fieldErrors.add(fieldError);

        when(exception.getBindingResult()).thenReturn(bindingResult);
        when(bindingResult.getAllErrors()).thenReturn(List.of(fieldError));
        when(messageSource.getMessage(any(), any())).thenReturn("mensagem traduzida");

        // when
        ResponseEntity<Object> response = apiExceptionHandler.handleMethodArgumentNotValid(exception, new HttpHeaders(), HttpStatus.BAD_REQUEST, webRequest);

        // then
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNotNull(response.getBody());
        assertInstanceOf(Problema.class, response.getBody());

        Problema problema = (Problema) response.getBody();
        assertEquals(400, problema.getStatus());
        assertNotNull(problema.getListaErroAtributos());
        assertEquals(1, problema.getListaErroAtributos().size());
        assertEquals("campo", problema.getListaErroAtributos().getFirst().getNomeAtributo());
        assertEquals("mensagem traduzida", problema.getListaErroAtributos().getFirst().getMensagemErro());
    }

    @Test
    public void deveHandleHandlerMethodValidationException() {
        // arrange
        HandlerMethodValidationException exception = mock(HandlerMethodValidationException.class);
        ParameterValidationResult validationResult = mock(ParameterValidationResult.class);

        when(exception.getAllValidationResults()).thenReturn(List.of(validationResult));
        when(validationResult.getResolvableErrors()).thenReturn(List.of());

        // when
        ResponseEntity<Object> response = apiExceptionHandler.handleHandlerMethodValidationException(exception, new HttpHeaders(), HttpStatus.BAD_REQUEST, webRequest);

        // then
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNotNull(response.getBody());
        assertInstanceOf(Problema.class, response.getBody());

        Problema problema = (Problema) response.getBody();
        assertEquals(400, problema.getStatus());
        assertEquals("Erro de validação.", problema.getMensagemUsuario());
    }

    @Test
    public void deveHandleHttpRequestMethodNotSupported() {
        // arrange
        HttpRequestMethodNotSupportedException exception = new HttpRequestMethodNotSupportedException("POST", List.of("GET"));

        // when
        ResponseEntity<Object> response = apiExceptionHandler.handleHttpRequestMethodNotSupported(exception, new HttpHeaders(), HttpStatus.METHOD_NOT_ALLOWED, webRequest);

        // then
        assertEquals(HttpStatus.METHOD_NOT_ALLOWED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertInstanceOf(Problema.class, response.getBody());

        Problema problema = (Problema) response.getBody();
        assertEquals(405, problema.getStatus());
        assertTrue(problema.getDetalhe().contains("POST"));
        assertEquals(ApiExceptionHandler.MSG_ERRO_GENERICA_USUARIO_FINAL, problema.getMensagemUsuario());
    }

    @Test
    public void deveHandleHttpMessageNotReadable() {
        // arrange
        HttpMessageNotReadableException exception = mock(HttpMessageNotReadableException.class);

        // when
        ResponseEntity<Object> response = apiExceptionHandler.handleHttpMessageNotReadable(
                exception, new HttpHeaders(), HttpStatus.BAD_REQUEST, webRequest);

        // then
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNotNull(response.getBody());
        assertInstanceOf(Problema.class, response.getBody());

        Problema problema = (Problema) response.getBody();
        assertEquals(400, problema.getStatus());
        assertEquals("O corpo da requisição está inválido. Verifique erro de sintaxe.", problema.getDetalhe());
        assertEquals(ApiExceptionHandler.MSG_ERRO_GENERICA_USUARIO_FINAL, problema.getMensagemUsuario());
    }

    @Test
    public void deveHandleHttpMessageNotReadableComPropertyBindingException() {
        // arrange
        HttpMessageNotReadableException exception = mock(HttpMessageNotReadableException.class);
        PropertyBindingException rootCause = mock(PropertyBindingException.class);

        Reference reference = new Reference(null, "propriedadeInexistente");
        List<Reference> path = List.of(reference);

        when(rootCause.getPath()).thenReturn(path);

        RuntimeException wrappedException = new RuntimeException(rootCause);
        when(exception.getCause()).thenReturn(wrappedException);

        // when
        ResponseEntity<Object> response = apiExceptionHandler.handleHttpMessageNotReadable(
                exception, new HttpHeaders(), HttpStatus.BAD_REQUEST, webRequest);

        // then
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNotNull(response.getBody());
        assertInstanceOf(Problema.class, response.getBody());

        Problema problema = (Problema) response.getBody();
        assertEquals(400, problema.getStatus());
        assertTrue(problema.getDetalhe().contains("propriedadeInexistente"));
        assertTrue(problema.getDetalhe().contains("não existe"));
        assertEquals(ApiExceptionHandler.MSG_ERRO_GENERICA_USUARIO_FINAL, problema.getMensagemUsuario());
    }

    @Test
    public void deveUsarHandleValidationInternal() {
        // arrange
        Exception exception = new Exception("Teste");
        BindingResult bindingResult = mock(BindingResult.class);

        ObjectError objectError = new ObjectError("objeto", "mensagem");
        FieldError fieldError = new FieldError("objeto", "campo", "valor");

        when(bindingResult.getAllErrors()).thenReturn(List.of(objectError, fieldError));
        when(messageSource.getMessage(any(), any())).thenReturn("mensagem traduzida");

        // when
        ResponseEntity<Object> response = ReflectionTestUtils.invokeMethod(apiExceptionHandler, 
                "handleValidationInternal", exception, bindingResult, new HttpHeaders(), HttpStatus.BAD_REQUEST, webRequest);

        // then
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNotNull(response.getBody());
        assertInstanceOf(Problema.class, response.getBody());

        Problema problema = (Problema) response.getBody();
        assertEquals(400, problema.getStatus());
        assertEquals("Um ou mais campos estão inválidos. Faça o preenchimento correto e tente novamente.", problema.getDetalhe());
        assertNotNull(problema.getListaErroAtributos());
        assertEquals(2, problema.getListaErroAtributos().size());
    }

    @Test
    public void deveUsarCreateProblemBuilder() {
        // when
        Problema.ProblemaBuilder builder = ReflectionTestUtils.invokeMethod(apiExceptionHandler, 
                "createProblemBuilder", HttpStatus.BAD_REQUEST, 
                br.com.fiap.techchallengeapipagamento.core.config.exception.domain.ProblemaType.DADOS_INVALIDOS,
                "Detalhe teste");

        Problema problema = builder.mensagemUsuario("Mensagem teste").build();

        // then
        assertNotNull(problema);
        assertEquals(400, problema.getStatus());
        assertEquals("Detalhe teste", problema.getDetalhe());
        assertEquals("Mensagem teste", problema.getMensagemUsuario());
        assertNotNull(problema.getDataHora());
        assertTrue(problema.getDataHora().isBefore(OffsetDateTime.now().plusSeconds(1)));
    }

    @Test
    public void deveUsarJoinPath() {
        // arrange
        Reference ref1 = new Reference(null, "campo1");
        Reference ref2 = new Reference(null, "campo2");
        List<Reference> references = List.of(ref1, ref2);

        // when
        String result = ReflectionTestUtils.invokeMethod(apiExceptionHandler, "joinPath", references);

        // then
        assertEquals("campo1.campo2", result);
    }

    @Test
    public void deveRetornarStringVaziaQuandoReferencesVazio() {
        // when
        String result = ReflectionTestUtils.invokeMethod(apiExceptionHandler, "joinPath", Collections.emptyList());

        // then
        assertEquals("", result);
    }
}
