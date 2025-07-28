package br.com.fiap.techchallengeapipagamento.pagamento.application.usecase.impl;

import br.com.fiap.techchallengeapipagamento.core.config.properties.MercadoPagoProperties;
import br.com.fiap.techchallengeapipagamento.pagamento.domain.Pedido;
import br.com.fiap.techchallengeapipagamento.pagamento.infrastructure.client.mercadopago.MercadoPagoCodigoQRClient;
import br.com.fiap.techchallengeapipagamento.pagamento.infrastructure.client.mercadopago.mapper.MercadoPagoOrderRequestMapper;
import br.com.fiap.techchallengeapipagamento.pagamento.infrastructure.client.mercadopago.request.MercadoPagoOrderRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("CriarPedidoMercadoPagoUseCaseImpl Tests")
class CriarPedidoMercadoPagoUseCaseImplTest {

    @Mock
    private MercadoPagoOrderRequestMapper mercadoPagoOrderRequestMapper;

    @Mock
    private MercadoPagoCodigoQRClient mercadoPagoCodigoQRClient;

    @Mock
    private MercadoPagoProperties mercadoPagoProperties;

    @InjectMocks
    private CriarPedidoMercadoPagoUseCaseImpl useCase;

    private Pedido pedido;
    private MercadoPagoOrderRequest mercadoPagoOrderRequest;

    @BeforeEach
    void setUp() {
        pedido = new Pedido();
        pedido.setCodigoPedido("ped-123");
        pedido.setPreco(new BigDecimal("50.00"));
        pedido.setCodigo("codigo-123");
        pedido.setCodigoCliente("cliente-456");

        mercadoPagoOrderRequest = mock(MercadoPagoOrderRequest.class);
    }

    @Test
    @DisplayName("Deve criar pedido no MercadoPago com sucesso")
    void deveCriarPedidoMercadoPagoComSucesso() {
        // Given
        Long userId = 123L;
        String externalStoreId = "store-456";
        String externalPosId = "pos-789";
        String authHeader = "Bearer token123";

        when(mercadoPagoProperties.getUserId()).thenReturn(userId);
        when(mercadoPagoProperties.getExternalStoreId()).thenReturn(externalStoreId);
        when(mercadoPagoProperties.getExternalPosId()).thenReturn(externalPosId);
        when(mercadoPagoProperties.getAuthHeader()).thenReturn(authHeader);
        when(mercadoPagoOrderRequestMapper.pedidoParaMercadoPagoOrderItemRequest(pedido))
                .thenReturn(mercadoPagoOrderRequest);

        // When
        boolean resultado = useCase.criarPedidoMercadoPago(pedido);

        // Then
        assertTrue(resultado);

        verify(mercadoPagoOrderRequestMapper).pedidoParaMercadoPagoOrderItemRequest(pedido);
        verify(mercadoPagoCodigoQRClient).pedidosPresenciaisV2(
                eq(userId), eq(externalStoreId), eq(externalPosId), eq(authHeader), eq(mercadoPagoOrderRequest));
        verify(mercadoPagoProperties).getUserId();
        verify(mercadoPagoProperties).getExternalStoreId();
        verify(mercadoPagoProperties).getExternalPosId();
        verify(mercadoPagoProperties).getAuthHeader();
    }

    @Test
    @DisplayName("Deve retornar false quando ocorrer exceção no mapper")
    void deveRetornarFalseQuandoOcorrerExcecaoNoMapper() {
        // Given
        when(mercadoPagoOrderRequestMapper.pedidoParaMercadoPagoOrderItemRequest(pedido))
                .thenThrow(new RuntimeException("Erro no mapeamento"));

        // When
        boolean resultado = useCase.criarPedidoMercadoPago(pedido);

        // Then
        assertFalse(resultado);

        verify(mercadoPagoOrderRequestMapper).pedidoParaMercadoPagoOrderItemRequest(pedido);
        verify(mercadoPagoCodigoQRClient, never()).pedidosPresenciaisV2(
                anyLong(), anyString(), anyString(), anyString(), any());
    }

    @Test
    @DisplayName("Deve retornar false quando ocorrer exceção no client MercadoPago")
    void deveRetornarFalseQuandoOcorrerExcecaoNoClientMercadoPago() {
        // Given
        Long userId = 123L;
        String externalStoreId = "store-456";
        String externalPosId = "pos-789";
        String authHeader = "Bearer token123";

        when(mercadoPagoProperties.getUserId()).thenReturn(userId);
        when(mercadoPagoProperties.getExternalStoreId()).thenReturn(externalStoreId);
        when(mercadoPagoProperties.getExternalPosId()).thenReturn(externalPosId);
        when(mercadoPagoProperties.getAuthHeader()).thenReturn(authHeader);
        when(mercadoPagoOrderRequestMapper.pedidoParaMercadoPagoOrderItemRequest(pedido))
                .thenReturn(mercadoPagoOrderRequest);

        doThrow(new RuntimeException("Erro de comunicação com MercadoPago"))
                .when(mercadoPagoCodigoQRClient).pedidosPresenciaisV2(
                        anyLong(), anyString(), anyString(), anyString(), any(MercadoPagoOrderRequest.class));

        // When
        boolean resultado = useCase.criarPedidoMercadoPago(pedido);

        // Then
        assertFalse(resultado);

        verify(mercadoPagoOrderRequestMapper).pedidoParaMercadoPagoOrderItemRequest(pedido);
        verify(mercadoPagoCodigoQRClient).pedidosPresenciaisV2(
                eq(userId), eq(externalStoreId), eq(externalPosId), eq(authHeader), eq(mercadoPagoOrderRequest));
    }

    @Test
    @DisplayName("Deve usar todas as propriedades corretas do MercadoPago")
    void deveUsarTodasAsPropriedadesCorretasDoMercadoPago() {
        // Given
        Long userIdEsperado = 999L;
        String storeIdEsperado = "loja-especifica";
        String posIdEsperado = "pos-especifico";
        String authHeaderEsperado = "Bearer token-especifico";

        when(mercadoPagoProperties.getUserId()).thenReturn(userIdEsperado);
        when(mercadoPagoProperties.getExternalStoreId()).thenReturn(storeIdEsperado);
        when(mercadoPagoProperties.getExternalPosId()).thenReturn(posIdEsperado);
        when(mercadoPagoProperties.getAuthHeader()).thenReturn(authHeaderEsperado);
        when(mercadoPagoOrderRequestMapper.pedidoParaMercadoPagoOrderItemRequest(pedido))
                .thenReturn(mercadoPagoOrderRequest);

        // When
        useCase.criarPedidoMercadoPago(pedido);

        // Then
        verify(mercadoPagoCodigoQRClient).pedidosPresenciaisV2(
                eq(userIdEsperado),
                eq(storeIdEsperado),
                eq(posIdEsperado),
                eq(authHeaderEsperado),
                eq(mercadoPagoOrderRequest)
        );
    }

    @Test
    @DisplayName("Deve criar pedido com diferentes dados de entrada")
    void deveCriarPedidoComDiferentesDadosDeEntrada() {
        // Given
        Pedido pedidoDiferente = new Pedido();
        pedidoDiferente.setCodigoPedido("ped-diferente");
        pedidoDiferente.setPreco(new BigDecimal("100.00"));
        pedidoDiferente.setCodigo("codigo-diferente");

        MercadoPagoOrderRequest requestDiferente = mock(MercadoPagoOrderRequest.class);

        when(mercadoPagoProperties.getUserId()).thenReturn(123L);
        when(mercadoPagoProperties.getExternalStoreId()).thenReturn("store-456");
        when(mercadoPagoProperties.getExternalPosId()).thenReturn("pos-789");
        when(mercadoPagoProperties.getAuthHeader()).thenReturn("Bearer token123");
        when(mercadoPagoOrderRequestMapper.pedidoParaMercadoPagoOrderItemRequest(pedidoDiferente))
                .thenReturn(requestDiferente);

        // When
        boolean resultado = useCase.criarPedidoMercadoPago(pedidoDiferente);

        // Then
        assertTrue(resultado);

        verify(mercadoPagoOrderRequestMapper).pedidoParaMercadoPagoOrderItemRequest(pedidoDiferente);
        verify(mercadoPagoCodigoQRClient).pedidosPresenciaisV2(
                anyLong(), anyString(), anyString(), anyString(), eq(requestDiferente));
    }

    @Test
    @DisplayName("Deve tratar exceção com causa nula")
    void deveTratarExcecaoComCausaNula() {
        // Given
        RuntimeException excecaoSemCausa = new RuntimeException("Erro sem causa");
        when(mercadoPagoOrderRequestMapper.pedidoParaMercadoPagoOrderItemRequest(pedido))
                .thenThrow(excecaoSemCausa);

        // When
        boolean resultado = useCase.criarPedidoMercadoPago(pedido);

        // Then
        assertFalse(resultado);

        verify(mercadoPagoOrderRequestMapper).pedidoParaMercadoPagoOrderItemRequest(pedido);
    }

    @Test
    @DisplayName("Deve processar pedido com valor zero")
    void deveProcessarPedidoComValorZero() {
        // Given
        Pedido pedidoValorZero = new Pedido();
        pedidoValorZero.setCodigoPedido("ped-zero");
        pedidoValorZero.setPreco(BigDecimal.ZERO);

        when(mercadoPagoProperties.getUserId()).thenReturn(123L);
        when(mercadoPagoProperties.getExternalStoreId()).thenReturn("store-456");
        when(mercadoPagoProperties.getExternalPosId()).thenReturn("pos-789");
        when(mercadoPagoProperties.getAuthHeader()).thenReturn("Bearer token123");
        when(mercadoPagoOrderRequestMapper.pedidoParaMercadoPagoOrderItemRequest(pedidoValorZero))
                .thenReturn(mercadoPagoOrderRequest);

        // When
        boolean resultado = useCase.criarPedidoMercadoPago(pedidoValorZero);

        // Then
        assertTrue(resultado);

        verify(mercadoPagoOrderRequestMapper).pedidoParaMercadoPagoOrderItemRequest(pedidoValorZero);
    }
}
