package br.com.fiap.techchallengeapipagamento.pagamento.application.usecase.impl;

import br.com.fiap.techchallengeapipagamento.pagamento.application.gateway.PagamentoGateway;
import br.com.fiap.techchallengeapipagamento.pagamento.application.usecase.ConfirmarPagamentoPedidoUseCase;
import br.com.fiap.techchallengeapipagamento.pagamento.application.usecase.CriarPedidoMercadoPagoUseCase;
import br.com.fiap.techchallengeapipagamento.pagamento.domain.Pagamento;
import br.com.fiap.techchallengeapipagamento.pagamento.domain.Pedido;
import br.com.fiap.techchallengeapipagamento.pagamento.domain.StatusPagamentoEnum;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("SalvarPagamentoUseCaseImpl Tests")
class SalvarPagamentoUseCaseImplTest {

    @Mock
    private PagamentoGateway pagamentoGateway;

    @Mock
    private CriarPedidoMercadoPagoUseCase criarPedidoMercadoPagoUseCase;

    @Mock
    private ConfirmarPagamentoPedidoUseCase confirmarPagamentoPedidoUseCase;

    @InjectMocks
    private SalvarPagamentoUseCaseImpl useCase;

    private Pedido pedido;
    private Pagamento pagamento;

    @BeforeEach
    void setUp() {
        pedido = new Pedido();
        pedido.setCodigoPedido("ped-123");
        pedido.setPreco(new BigDecimal("50.00"));
        pedido.setCodigo("codigo-123");

        pagamento = new Pagamento();
        pagamento.setId("pag-123");
        pagamento.setCodigoPedido("ped-123");
        pagamento.setPreco(new BigDecimal("50.00"));
        pagamento.setStatus("PENDENTE");
    }

    @Test
    @DisplayName("Deve fazer pagamento do pedido com sucesso quando MercadoPago está ativo e pedido foi criado")
    void deveFazerPagamentoPedidoComSucessoQuandoMercadoPagoAtivoEPedidoCriado() {
        // Given
        ReflectionTestUtils.setField(useCase, "isMercadoPagoAtivo", true);
        when(criarPedidoMercadoPagoUseCase.criarPedidoMercadoPago(pedido)).thenReturn(true);
        when(pagamentoGateway.salvar(any(Pagamento.class))).thenReturn(pagamento);

        // When
        Pagamento resultado = useCase.fazerPagamentoDoPedido(pedido);

        // Then
        assertNotNull(resultado);
        assertEquals(pagamento, resultado);

        verify(criarPedidoMercadoPagoUseCase).criarPedidoMercadoPago(pedido);
        verify(pagamentoGateway).salvar(any(Pagamento.class));
    }

    @Test
    @DisplayName("Deve criar pagamento com status REJEITADO quando falha ao criar pedido no MercadoPago")
    void deveCriarPagamentoComStatusRejeitadoQuandoFalhaAoCriarPedidoMercadoPago() {
        // Given
        ReflectionTestUtils.setField(useCase, "isMercadoPagoAtivo", true);
        when(criarPedidoMercadoPagoUseCase.criarPedidoMercadoPago(pedido)).thenReturn(false);

        Pagamento pagamentoRejeitado = new Pagamento();
        pagamentoRejeitado.setStatus("REJEITADO");
        when(pagamentoGateway.salvar(any(Pagamento.class))).thenReturn(pagamentoRejeitado);

        // When
        Pagamento resultado = useCase.fazerPagamentoDoPedido(pedido);

        // Then
        assertNotNull(resultado);
        assertEquals("REJEITADO", resultado.getStatus());

        verify(criarPedidoMercadoPagoUseCase).criarPedidoMercadoPago(pedido);
        verify(pagamentoGateway).salvar(any(Pagamento.class));
    }

    @Test
    @DisplayName("Deve retornar null quando MercadoPago está inativo")
    void deveRetornarNullQuandoMercadoPagoInativo() {
        // Given
        ReflectionTestUtils.setField(useCase, "isMercadoPagoAtivo", false);
        when(criarPedidoMercadoPagoUseCase.criarPedidoMercadoPago(pedido)).thenReturn(true);

        // When
        Pagamento resultado = useCase.fazerPagamentoDoPedido(pedido);

        // Then
        assertNull(resultado);

        verify(criarPedidoMercadoPagoUseCase).criarPedidoMercadoPago(pedido);
        verify(pagamentoGateway, never()).salvar(any());
    }

    @Test
    @DisplayName("Deve atualizar status de pagamento com sucesso")
    void deveAtualizarStatusPagamentoComSucesso() {
        // Given
        String id = "pag-123";
        StatusPagamentoEnum novoStatus = StatusPagamentoEnum.APROVADO;
        when(pagamentoGateway.atualizarStatusPagamento(id, novoStatus.getStatus()))
                .thenReturn(Optional.of(pagamento));

        // When
        Pagamento resultado = useCase.atualizarStatusPagamento(id, novoStatus);

        // Then
        assertNotNull(resultado);
        assertEquals(pagamento, resultado);

        verify(pagamentoGateway).atualizarStatusPagamento(id, novoStatus.getStatus());
        verify(confirmarPagamentoPedidoUseCase).confirmarPagamentoPedido(pagamento);
    }

    @Test
    @DisplayName("Deve retornar null quando pagamento não é encontrado para atualização")
    void deveRetornarNullQuandoPagamentoNaoEncontradoParaAtualizacao() {
        // Given
        String id = "pag-inexistente";
        StatusPagamentoEnum novoStatus = StatusPagamentoEnum.APROVADO;
        when(pagamentoGateway.atualizarStatusPagamento(id, novoStatus.getStatus()))
                .thenReturn(Optional.empty());

        // When
        Pagamento resultado = useCase.atualizarStatusPagamento(id, novoStatus);

        // Then
        assertNull(resultado);

        verify(pagamentoGateway).atualizarStatusPagamento(id, novoStatus.getStatus());
        verify(confirmarPagamentoPedidoUseCase, never()).confirmarPagamentoPedido(any());
    }

    @Test
    @DisplayName("Deve atualizar status para REJEITADO sem confirmar pagamento")
    void deveAtualizarStatusParaRejeitadoSemConfirmarPagamento() {
        // Given
        String id = "pag-123";
        StatusPagamentoEnum novoStatus = StatusPagamentoEnum.REJEITADO;
        pagamento.setStatus("REJEITADO");
        when(pagamentoGateway.atualizarStatusPagamento(id, novoStatus.getStatus()))
                .thenReturn(Optional.of(pagamento));

        // When
        Pagamento resultado = useCase.atualizarStatusPagamento(id, novoStatus);

        // Then
        assertNotNull(resultado);
        assertEquals(pagamento, resultado);
        assertEquals("REJEITADO", resultado.getStatus());

        verify(pagamentoGateway).atualizarStatusPagamento(id, novoStatus.getStatus());
        verify(confirmarPagamentoPedidoUseCase, never()).confirmarPagamentoPedido(any());
    }

    @Test
    @DisplayName("Deve atualizar status para PENDENTE sem confirmar pagamento")
    void deveAtualizarStatusParaPendenteSemConfirmarPagamento() {
        // Given
        String id = "pag-123";
        StatusPagamentoEnum novoStatus = StatusPagamentoEnum.PENDENTE;
        when(pagamentoGateway.atualizarStatusPagamento(id, novoStatus.getStatus()))
                .thenReturn(Optional.of(pagamento));

        // When
        Pagamento resultado = useCase.atualizarStatusPagamento(id, novoStatus);

        // Then
        assertNotNull(resultado);
        assertEquals(pagamento, resultado);

        verify(pagamentoGateway).atualizarStatusPagamento(id, novoStatus.getStatus());
        verify(confirmarPagamentoPedidoUseCase, never()).confirmarPagamentoPedido(any());
    }

    @Test
    @DisplayName("Deve criar pagamento com dados corretos do pedido")
    void deveCriarPagamentoComDadosCorretosDoPedido() {
        // Given
        ReflectionTestUtils.setField(useCase, "isMercadoPagoAtivo", true);
        Pedido pedidoEspecifico = new Pedido();
        pedidoEspecifico.setCodigoPedido("ped-especifico");
        pedidoEspecifico.setPreco(new BigDecimal("75.99"));

        when(criarPedidoMercadoPagoUseCase.criarPedidoMercadoPago(pedidoEspecifico)).thenReturn(true);
        when(pagamentoGateway.salvar(any(Pagamento.class))).thenAnswer(invocation -> {
            Pagamento pagamentoSalvo = invocation.getArgument(0);
            pagamentoSalvo.setId("novo-pag-id");
            return pagamentoSalvo;
        });

        // When
        Pagamento resultado = useCase.fazerPagamentoDoPedido(pedidoEspecifico);

        // Then
        assertNotNull(resultado);
        assertEquals("ped-especifico", resultado.getCodigoPedido());
        assertEquals(new BigDecimal("75.99"), resultado.getPreco());
        assertEquals("PENDENTE", resultado.getStatus());

        verify(pagamentoGateway).salvar(any(Pagamento.class));
    }
}
