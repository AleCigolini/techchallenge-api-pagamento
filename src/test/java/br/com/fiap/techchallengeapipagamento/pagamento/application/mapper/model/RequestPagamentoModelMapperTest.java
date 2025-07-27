package br.com.fiap.techchallengeapipagamento.pagamento.application.mapper.model;

import br.com.fiap.techchallengeapipagamento.core.config.exception.exceptions.ValidacaoEntidadeException;
import br.com.fiap.techchallengeapipagamento.pagamento.common.domain.dto.request.PedidoRequestDto;
import br.com.fiap.techchallengeapipagamento.pagamento.domain.Pedido;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.MappingException;
import org.modelmapper.ModelMapper;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("RequestPagamentoModelMapper Tests")
class RequestPagamentoModelMapperTest {

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private RequestPagamentoModelMapper mapper;

    private PedidoRequestDto pedidoRequestDto;
    private Pedido pedidoDomain;

    @BeforeEach
    void setUp() {
        pedidoRequestDto = new PedidoRequestDto();
        pedidoRequestDto.setCodigoPedido("ped-123");
        pedidoRequestDto.setCodigo("codigo-123");
        pedidoRequestDto.setObservacao("Observação do pedido");
        pedidoRequestDto.setPreco(new BigDecimal("75.50"));

        pedidoDomain = new Pedido();
        pedidoDomain.setCodigoPedido("ped-123");
        pedidoDomain.setCodigo("codigo-123");
        pedidoDomain.setObservacao("Observação do pedido");
        pedidoDomain.setPreco(new BigDecimal("75.50"));
    }

    @Test
    @DisplayName("Deve converter PedidoRequestDto para Pedido com sucesso")
    void deveConverterPedidoRequestDtoParaPedidoComSucesso() {
        // Given
        when(modelMapper.map(pedidoRequestDto, Pedido.class)).thenReturn(pedidoDomain);

        // When
        Pedido resultado = mapper.pedidoRequestDtoParaPedido(pedidoRequestDto);

        // Then
        assertNotNull(resultado);
        assertEquals(pedidoDomain.getCodigoPedido(), resultado.getCodigoPedido());
        assertEquals(pedidoDomain.getCodigo(), resultado.getCodigo());
        assertEquals(pedidoDomain.getObservacao(), resultado.getObservacao());
        assertEquals(pedidoDomain.getPreco(), resultado.getPreco());

        verify(modelMapper, times(1)).map(pedidoRequestDto, Pedido.class);
    }

    @Test
    @DisplayName("Deve lançar ValidacaoEntidadeException quando ModelMapper lança MappingException com causa ValidacaoEntidadeException")
    void deveLancarValidacaoEntidadeExceptionQuandoModelMapperLancaMappingExceptionComCausaValidacaoEntidadeException() {
        // Given
        ValidacaoEntidadeException validacaoException = new ValidacaoEntidadeException("Erro de validação");
        MappingException mappingException = mock(MappingException.class);
        when(mappingException.getCause()).thenReturn(validacaoException);

        when(modelMapper.map(pedidoRequestDto, Pedido.class)).thenThrow(mappingException);

        // When & Then
        ValidacaoEntidadeException exception = assertThrows(ValidacaoEntidadeException.class,
            () -> mapper.pedidoRequestDtoParaPedido(pedidoRequestDto));

        assertEquals("Erro de validação", exception.getMessage());
        verify(modelMapper, times(1)).map(pedidoRequestDto, Pedido.class);
    }

    @Test
    @DisplayName("Deve relançar MappingException quando não é ValidacaoEntidadeException")
    void deveRelancarMappingExceptionQuandoNaoEValidacaoEntidadeException() {
        // Given
        RuntimeException outraException = new RuntimeException("Outro tipo de erro");
        MappingException mappingException = mock(MappingException.class);
        when(mappingException.getCause()).thenReturn(outraException);

        when(modelMapper.map(pedidoRequestDto, Pedido.class)).thenThrow(mappingException);

        // When & Then
        MappingException exception = assertThrows(MappingException.class,
            () -> mapper.pedidoRequestDtoParaPedido(pedidoRequestDto));

        assertEquals(mappingException, exception);
        verify(modelMapper, times(1)).map(pedidoRequestDto, Pedido.class);
    }

    @Test
    @DisplayName("Deve relançar MappingException quando não tem causa")
    void deveRelancarMappingExceptionQuandoNaoTemCausa() {
        // Given
        MappingException mappingException = mock(MappingException.class);
        when(mappingException.getCause()).thenReturn(null);

        when(modelMapper.map(pedidoRequestDto, Pedido.class)).thenThrow(mappingException);

        // When & Then
        MappingException exception = assertThrows(MappingException.class,
            () -> mapper.pedidoRequestDtoParaPedido(pedidoRequestDto));

        assertEquals(mappingException, exception);
        verify(modelMapper, times(1)).map(pedidoRequestDto, Pedido.class);
    }

    @Test
    @DisplayName("Deve converter PedidoRequestDto com valores mínimos")
    void deveConverterPedidoRequestDtoComValoresMinimos() {
        // Given
        PedidoRequestDto requestMinimo = new PedidoRequestDto();
        requestMinimo.setCodigoPedido("ped-min");
        requestMinimo.setCodigo("cod-min");
        requestMinimo.setObservacao("Obs mínima");
        requestMinimo.setPreco(new BigDecimal("0.01"));

        Pedido domainMinimo = new Pedido();
        domainMinimo.setCodigoPedido("ped-min");
        domainMinimo.setCodigo("cod-min");
        domainMinimo.setObservacao("Obs mínima");
        domainMinimo.setPreco(new BigDecimal("0.01"));

        when(modelMapper.map(requestMinimo, Pedido.class)).thenReturn(domainMinimo);

        // When
        Pedido resultado = mapper.pedidoRequestDtoParaPedido(requestMinimo);

        // Then
        assertNotNull(resultado);
        assertEquals("ped-min", resultado.getCodigoPedido());
        assertEquals("cod-min", resultado.getCodigo());
        assertEquals("Obs mínima", resultado.getObservacao());
        assertEquals(new BigDecimal("0.01"), resultado.getPreco());

        verify(modelMapper, times(1)).map(requestMinimo, Pedido.class);
    }

    @Test
    @DisplayName("Deve converter PedidoRequestDto com valores grandes")
    void deveConverterPedidoRequestDtoComValoresGrandes() {
        // Given
        PedidoRequestDto requestGrande = new PedidoRequestDto();
        requestGrande.setCodigoPedido("pedido-com-codigo-muito-longo-para-teste");
        requestGrande.setCodigo("codigo-tambem-muito-longo");
        requestGrande.setObservacao("Observação muito longa com muitos detalhes sobre o pedido");
        requestGrande.setPreco(new BigDecimal("9999.99"));

        Pedido domainGrande = new Pedido();
        domainGrande.setCodigoPedido("pedido-com-codigo-muito-longo-para-teste");
        domainGrande.setCodigo("codigo-tambem-muito-longo");
        domainGrande.setObservacao("Observação muito longa com muitos detalhes sobre o pedido");
        domainGrande.setPreco(new BigDecimal("9999.99"));

        when(modelMapper.map(requestGrande, Pedido.class)).thenReturn(domainGrande);

        // When
        Pedido resultado = mapper.pedidoRequestDtoParaPedido(requestGrande);

        // Then
        assertNotNull(resultado);
        assertEquals("pedido-com-codigo-muito-longo-para-teste", resultado.getCodigoPedido());
        assertEquals("codigo-tambem-muito-longo", resultado.getCodigo());
        assertEquals("Observação muito longa com muitos detalhes sobre o pedido", resultado.getObservacao());
        assertEquals(new BigDecimal("9999.99"), resultado.getPreco());

        verify(modelMapper, times(1)).map(requestGrande, Pedido.class);
    }

    @Test
    @DisplayName("Deve converter PedidoRequestDto com valor zero")
    void deveConverterPedidoRequestDtoComValorZero() {
        // Given
        PedidoRequestDto requestZero = new PedidoRequestDto();
        requestZero.setCodigoPedido("ped-zero");
        requestZero.setCodigo("cod-zero");
        requestZero.setObservacao("Pedido gratuito");
        requestZero.setPreco(BigDecimal.ZERO);

        Pedido domainZero = new Pedido();
        domainZero.setCodigoPedido("ped-zero");
        domainZero.setCodigo("cod-zero");
        domainZero.setObservacao("Pedido gratuito");
        domainZero.setPreco(BigDecimal.ZERO);

        when(modelMapper.map(requestZero, Pedido.class)).thenReturn(domainZero);

        // When
        Pedido resultado = mapper.pedidoRequestDtoParaPedido(requestZero);

        // Then
        assertNotNull(resultado);
        assertEquals("ped-zero", resultado.getCodigoPedido());
        assertEquals(BigDecimal.ZERO, resultado.getPreco());
        assertEquals("Pedido gratuito", resultado.getObservacao());

        verify(modelMapper, times(1)).map(requestZero, Pedido.class);
    }

    @Test
    @DisplayName("Deve verificar que ModelMapper é chamado com parâmetros corretos")
    void deveVerificarQueModelMapperEChamadoComParametrosCorretos() {
        // Given
        when(modelMapper.map(any(PedidoRequestDto.class), eq(Pedido.class))).thenReturn(pedidoDomain);

        // When
        mapper.pedidoRequestDtoParaPedido(pedidoRequestDto);

        // Then
        verify(modelMapper, times(1)).map(eq(pedidoRequestDto), eq(Pedido.class));
    }

    @Test
    @DisplayName("Deve tratar ValidacaoEntidadeException encadeada em múltiplos níveis")
    void deveTratarValidacaoEntidadeExceptionEncadeadaEmMultiplosNiveis() {
        // Given
        ValidacaoEntidadeException validacaoOriginal = new ValidacaoEntidadeException("Erro original");
        RuntimeException excecaoIntermediaria = new RuntimeException("Exceção intermediária", validacaoOriginal);
        MappingException mappingException = mock(MappingException.class);
        when(mappingException.getCause()).thenReturn(excecaoIntermediaria);

        when(modelMapper.map(pedidoRequestDto, Pedido.class)).thenThrow(mappingException);

        // When & Then
        MappingException exception = assertThrows(MappingException.class,
            () -> mapper.pedidoRequestDtoParaPedido(pedidoRequestDto));

        assertEquals(mappingException, exception);
        verify(modelMapper, times(1)).map(pedidoRequestDto, Pedido.class);
    }
}
