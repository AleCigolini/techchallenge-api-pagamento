package br.com.fiap.techchallengeapipagamento.pagamento.infrastructure.service;

import br.com.fiap.techchallengeapipagamento.pagamento.domain.Pagamento;
import br.com.fiap.techchallengeapipagamento.pagamento.infrastructure.database.repository.mongodb.PagamentoMongoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("PagamentoMongoService Tests")
class PagamentoMongoServiceTest {

    @Mock
    private PagamentoMongoRepository pagamentoRepository;

    @InjectMocks
    private PagamentoMongoService pagamentoService;

    private Pagamento pagamento;

    @BeforeEach
    void setUp() {
        pagamento = new Pagamento();
        pagamento.setId("pag-123");
        pagamento.setCodigoPedido("ped-456");
        pagamento.setPreco(new BigDecimal("50.00"));
        pagamento.setStatus("APROVADO");
    }

    @Test
    @DisplayName("Deve buscar pagamentos por pedido ID com sucesso")
    void deveBuscarPagamentosPorPedidoIdComSucesso() {
        // Given
        String pedidoId = "ped-456";
        Pagamento pagamento2 = new Pagamento();
        pagamento2.setId("pag-789");
        pagamento2.setCodigoPedido("ped-456");

        List<Pagamento> pagamentosEsperados = Arrays.asList(pagamento, pagamento2);
        when(pagamentoRepository.findByCodigoPedido(pedidoId)).thenReturn(pagamentosEsperados);

        // When
        List<Pagamento> resultado = pagamentoService.buscarPorPedidoId(pedidoId);

        // Then
        assertNotNull(resultado);
        assertEquals(2, resultado.size());
        assertEquals(pagamentosEsperados, resultado);

        verify(pagamentoRepository, times(1)).findByCodigoPedido(pedidoId);
    }

    @Test
    @DisplayName("Deve retornar lista vazia quando não encontrar pagamentos")
    void deveRetornarListaVaziaQuandoNaoEncontrarPagamentos() {
        // Given
        String pedidoId = "ped-inexistente";
        when(pagamentoRepository.findByCodigoPedido(pedidoId)).thenReturn(Collections.emptyList());

        // When
        List<Pagamento> resultado = pagamentoService.buscarPorPedidoId(pedidoId);

        // Then
        assertNotNull(resultado);
        assertTrue(resultado.isEmpty());

        verify(pagamentoRepository, times(1)).findByCodigoPedido(pedidoId);
    }

    @Test
    @DisplayName("Deve salvar pagamento com todos os campos preenchidos")
    void deveSalvarPagamentoComTodosOsCamposPreenchidos() {
        // Given
        Pagamento pagamentoCompleto = new Pagamento();
        pagamentoCompleto.setId("pag-completo");
        pagamentoCompleto.setCodigoPedido("ped-completo");
        pagamentoCompleto.setPreco(new BigDecimal("99.99"));
        pagamentoCompleto.setStatus("PENDENTE");

        when(pagamentoRepository.save(any(Pagamento.class))).thenReturn(pagamentoCompleto);

        // When
        Pagamento resultado = pagamentoService.salvar(pagamentoCompleto);

        // Then
        assertNotNull(resultado);
        assertEquals("pag-completo", resultado.getId());
        assertEquals("ped-completo", resultado.getCodigoPedido());
        assertEquals(new BigDecimal("99.99"), resultado.getPreco());
        assertEquals("PENDENTE", resultado.getStatus());

        verify(pagamentoRepository, times(1)).save(pagamentoCompleto);
    }

    @Test
    @DisplayName("Deve buscar pagamentos para diferentes pedidos")
    void deveBuscarPagamentosParaDiferentesPedidos() {
        // Given
        String pedidoId1 = "ped-001";
        String pedidoId2 = "ped-002";

        List<Pagamento> pagamentosPedido1 = List.of(pagamento);
        List<Pagamento> pagamentosPedido2 = Collections.emptyList();

        when(pagamentoRepository.findByCodigoPedido(pedidoId1)).thenReturn(pagamentosPedido1);
        when(pagamentoRepository.findByCodigoPedido(pedidoId2)).thenReturn(pagamentosPedido2);

        // When
        List<Pagamento> resultado1 = pagamentoService.buscarPorPedidoId(pedidoId1);
        List<Pagamento> resultado2 = pagamentoService.buscarPorPedidoId(pedidoId2);

        // Then
        assertEquals(1, resultado1.size());
        assertEquals(0, resultado2.size());

        verify(pagamentoRepository, times(1)).findByCodigoPedido(pedidoId1);
        verify(pagamentoRepository, times(1)).findByCodigoPedido(pedidoId2);
    }

    @Test
    @DisplayName("Deve buscar pagamentos com códigos de pedido específicos")
    void deveBuscarPagamentosComCodigosDePedidoEspecificos() {
        // Given
        String codigoPedidoEspecifico = "PEDIDO-ESPECIAL-2024-001";

        Pagamento pagamentoEspecifico = new Pagamento();
        pagamentoEspecifico.setId("pag-especial");
        pagamentoEspecifico.setCodigoPedido(codigoPedidoEspecifico);
        pagamentoEspecifico.setPreco(new BigDecimal("150.75"));
        pagamentoEspecifico.setStatus("APROVADO");

        when(pagamentoRepository.findByCodigoPedido(codigoPedidoEspecifico))
                .thenReturn(List.of(pagamentoEspecifico));

        // When
        List<Pagamento> resultado = pagamentoService.buscarPorPedidoId(codigoPedidoEspecifico);

        // Then
        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals(codigoPedidoEspecifico, resultado.getFirst().getCodigoPedido());
        assertEquals(new BigDecimal("150.75"), resultado.getFirst().getPreco());

        verify(pagamentoRepository, times(1)).findByCodigoPedido(codigoPedidoEspecifico);
    }

    @Test
    @DisplayName("Deve manter integridade dos dados ao salvar")
    void deveManterIntegridadeDosDadosAoSalvar() {
        // Given
        Pagamento pagamentoOriginal = new Pagamento();
        pagamentoOriginal.setId("pag-integridade");
        pagamentoOriginal.setCodigoPedido("ped-integridade");
        pagamentoOriginal.setPreco(new BigDecimal("33.33"));
        pagamentoOriginal.setStatus("REJEITADO");

        when(pagamentoRepository.save(any(Pagamento.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // When
        Pagamento resultado = pagamentoService.salvar(pagamentoOriginal);

        // Then
        assertEquals("pag-integridade", resultado.getId());
        assertEquals("ped-integridade", resultado.getCodigoPedido());
        assertEquals(new BigDecimal("33.33"), resultado.getPreco());
        assertEquals("REJEITADO", resultado.getStatus());
    }
}
