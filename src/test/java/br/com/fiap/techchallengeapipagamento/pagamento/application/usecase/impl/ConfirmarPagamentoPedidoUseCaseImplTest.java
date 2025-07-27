package br.com.fiap.techchallengeapipagamento.pagamento.application.usecase.impl;

import br.com.fiap.techchallengeapipagamento.pagamento.domain.Pagamento;
import br.com.fiap.techchallengeapipagamento.pagamento.infrastructure.client.pedido.PedidoClient;
import br.com.fiap.techchallengeapipagamento.pagamento.infrastructure.client.pedido.request.PagamentoRealizadoRequestDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("ConfirmarPagamentoPedidoUseCaseImpl Tests")
class ConfirmarPagamentoPedidoUseCaseImplTest {

    @Mock
    private PedidoClient pedidoClient;

    @InjectMocks
    private ConfirmarPagamentoPedidoUseCaseImpl useCase;

    private Pagamento pagamento;

    @BeforeEach
    void setUp() {
        pagamento = new Pagamento();
        pagamento.setId("pagamento-123");
        pagamento.setCodigoPedido("pedido-456");
        pagamento.setPreco(new BigDecimal("25.50"));
        pagamento.setStatus("APROVADO");
    }

    @Test
    @DisplayName("Deve confirmar pagamento do pedido com sucesso")
    void deveConfirmarPagamentoPedidoComSucesso() {
        // Given
        // When
        assertDoesNotThrow(() -> useCase.confirmarPagamentoPedido(pagamento));

        // Then
        ArgumentCaptor<String> codigoPedidoCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<PagamentoRealizadoRequestDto> requestCaptor = ArgumentCaptor.forClass(PagamentoRealizadoRequestDto.class);

        verify(pedidoClient, times(1)).confirmarPagamentoRecebido(
            codigoPedidoCaptor.capture(),
            requestCaptor.capture()
        );

        assertEquals("pedido-456", codigoPedidoCaptor.getValue());
        assertEquals("pagamento-123", requestCaptor.getValue().getCodigoPagamento());
    }

    @Test
    @DisplayName("Deve tratar exceção ao confirmar pagamento do pedido")
    void deveTratarExcecaoAoConfirmarPagamentoPedido() {
        // Given
        doThrow(new RuntimeException("Erro na comunicação")).when(pedidoClient)
            .confirmarPagamentoRecebido(any(), any());

        // When & Then
        assertDoesNotThrow(() -> useCase.confirmarPagamentoPedido(pagamento));

        verify(pedidoClient, times(1)).confirmarPagamentoRecebido(any(), any());
    }

    @Test
    @DisplayName("Deve confirmar pagamento com valores corretos")
    void deveConfirmarPagamentoComValoresCorretos() {
        // Given
        Pagamento pagamentoEspecifico = new Pagamento();
        pagamentoEspecifico.setId("pag-789");
        pagamentoEspecifico.setCodigoPedido("ped-321");

        // When
        useCase.confirmarPagamentoPedido(pagamentoEspecifico);

        // Then
        ArgumentCaptor<PagamentoRealizadoRequestDto> requestCaptor =
            ArgumentCaptor.forClass(PagamentoRealizadoRequestDto.class);

        verify(pedidoClient).confirmarPagamentoRecebido(
            eq("ped-321"),
            requestCaptor.capture()
        );

        assertEquals("pag-789", requestCaptor.getValue().getCodigoPagamento());
    }
}
