package br.com.fiap.techchallengeapipagamento.pagamento.application.usecase.impl;

import br.com.fiap.techchallengeapipagamento.pagamento.application.gateway.PagamentoGateway;
import br.com.fiap.techchallengeapipagamento.pagamento.domain.Pagamento;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("ConsultarPagamentoUseCaseImpl Tests")
class ConsultarPagamentoUseCaseImplTest {

    private PagamentoGateway pagamentoGateway;
    private ConsultarPagamentoUseCaseImpl useCase;

    private Pagamento pagamento1;
    private Pagamento pagamento2;

    @BeforeEach
    void setUp() {
        pagamentoGateway = Mockito.mock(PagamentoGateway.class);
        useCase = new ConsultarPagamentoUseCaseImpl(pagamentoGateway);

        pagamento1 = new Pagamento();
        pagamento1.setId("pag-001");
        pagamento1.setCodigoPedido("ped-001");
        pagamento1.setPreco(new BigDecimal("50.00"));
        pagamento1.setStatus("APROVADO");
        pagamento1.setDataCriacao(OffsetDateTime.now());

        pagamento2 = new Pagamento();
        pagamento2.setId("pag-002");
        pagamento2.setCodigoPedido("ped-001");
        pagamento2.setPreco(new BigDecimal("25.50"));
        pagamento2.setStatus("PENDENTE");
        pagamento2.setDataCriacao(OffsetDateTime.now().minusHours(1));
    }

    @AfterEach
    public void tearDown() {
        Mockito.reset(pagamentoGateway);
    }

    @Test
    @DisplayName("Deve buscar pagamentos por pedido ID com sucesso")
    void deveBuscarPagamentosPorPedidoIdComSucesso() {
        // Given
        String pedidoId = "ped-001";
        List<Pagamento> pagamentosEsperados = Arrays.asList(pagamento1, pagamento2);
        when(pagamentoGateway.buscarPorPedidoId(pedidoId)).thenReturn(pagamentosEsperados);

        // When
        List<Pagamento> resultado = useCase.buscarPorPedidoId(pedidoId);

        // Then
        assertNotNull(resultado);
        assertEquals(2, resultado.size());
        assertEquals(pagamentosEsperados, resultado);
        assertTrue(resultado.contains(pagamento1));
        assertTrue(resultado.contains(pagamento2));

        verify(pagamentoGateway, times(1)).buscarPorPedidoId(pedidoId);
    }

    @Test
    @DisplayName("Deve retornar lista vazia quando não encontrar pagamentos por pedido ID")
    void deveRetornarListaVaziaQuandoNaoEncontrarPagamentosPorPedidoId() {
        // Given
        String pedidoId = "ped-inexistente";
        when(pagamentoGateway.buscarPorPedidoId(pedidoId)).thenReturn(Collections.emptyList());

        // When
        List<Pagamento> resultado = useCase.buscarPorPedidoId(pedidoId);

        // Then
        assertNotNull(resultado);
        assertTrue(resultado.isEmpty());

        verify(pagamentoGateway, times(1)).buscarPorPedidoId(pedidoId);
    }

    @Test
    @DisplayName("Deve buscar pagamentos por status com sucesso")
    void deveBuscarPagamentosPorStatusComSucesso() {
        // Given
        String status = "APROVADO";
        List<Pagamento> pagamentosEsperados = List.of(pagamento1);
        when(pagamentoGateway.buscarPorStatus(status)).thenReturn(pagamentosEsperados);

        // When
        List<Pagamento> resultado = useCase.buscarPorStatus(status);

        // Then
        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals(pagamentosEsperados, resultado);
        assertEquals("APROVADO", resultado.get(0).getStatus());

        verify(pagamentoGateway, times(1)).buscarPorStatus(status);
    }

    @Test
    @DisplayName("Deve retornar lista vazia quando não encontrar pagamentos por status")
    void deveRetornarListaVaziaQuandoNaoEncontrarPagamentosPorStatus() {
        // Given
        String status = "CANCELADO";
        when(pagamentoGateway.buscarPorStatus(status)).thenReturn(Collections.emptyList());

        // When
        List<Pagamento> resultado = useCase.buscarPorStatus(status);

        // Then
        assertNotNull(resultado);
        assertTrue(resultado.isEmpty());

        verify(pagamentoGateway, times(1)).buscarPorStatus(status);
    }

    @Test
    @DisplayName("Deve buscar pagamentos pendentes")
    void deveBuscarPagamentosPendentes() {
        // Given
        String status = "PENDENTE";
        List<Pagamento> pagamentosEsperados = List.of(pagamento2);
        when(pagamentoGateway.buscarPorStatus(status)).thenReturn(pagamentosEsperados);

        // When
        List<Pagamento> resultado = useCase.buscarPorStatus(status);

        // Then
        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals("PENDENTE", resultado.get(0).getStatus());
        assertEquals("pag-002", resultado.get(0).getId());

        verify(pagamentoGateway, times(1)).buscarPorStatus(status);
    }

    @Test
    @DisplayName("Deve tratar pedido ID nulo")
    void deveTratarPedidoIdNulo() {
        // Given
        when(pagamentoGateway.buscarPorPedidoId(null)).thenReturn(Collections.emptyList());

        // When
        List<Pagamento> resultado = useCase.buscarPorPedidoId(null);

        // Then
        assertNotNull(resultado);
        assertTrue(resultado.isEmpty());

        verify(pagamentoGateway, times(1)).buscarPorPedidoId(null);
    }

    @Test
    @DisplayName("Deve tratar status nulo")
    void deveTratarStatusNulo() {
        // Given
        when(pagamentoGateway.buscarPorStatus(null)).thenReturn(Collections.emptyList());

        // When
        List<Pagamento> resultado = useCase.buscarPorStatus(null);

        // Then
        assertNotNull(resultado);
        assertTrue(resultado.isEmpty());

        verify(pagamentoGateway, times(1)).buscarPorStatus(null);
    }
}
