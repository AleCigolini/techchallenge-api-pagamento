package br.com.fiap.techchallengeapipagamento.pagamento.application.presenter.impl;

import br.com.fiap.techchallengeapipagamento.pagamento.common.domain.dto.response.PagamentoResponseDto;
import br.com.fiap.techchallengeapipagamento.pagamento.domain.Pagamento;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("PagamentoPresenterImpl Tests")
class PagamentoPresenterImplTest {

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private PagamentoPresenterImpl presenter;

    private Pagamento pagamento;
    private PagamentoResponseDto pagamentoResponseDto;

    @BeforeEach
    void setUp() {
        pagamento = new Pagamento();
        pagamento.setId("pag-123");
        pagamento.setCodigoPedido("ped-456");
        pagamento.setPreco(new BigDecimal("50.00"));
        pagamento.setStatus("APROVADO");

        pagamentoResponseDto = new PagamentoResponseDto();
        pagamentoResponseDto.setId("pag-123");
        pagamentoResponseDto.setCodigoPedido("ped-456");
        pagamentoResponseDto.setPreco(new BigDecimal("50.00"));
        pagamentoResponseDto.setStatus("APROVADO");
    }

    @Test
    @DisplayName("Deve converter pagamento para PagamentoResponseDto com sucesso")
    void deveConverterPagamentoParaPagamentoResponseDtoComSucesso() {
        // Given
        when(modelMapper.map(pagamento, PagamentoResponseDto.class))
                .thenReturn(pagamentoResponseDto);

        // When
        PagamentoResponseDto resultado = presenter.pagamentoParaPagamentoResponseDTO(pagamento);

        // Then
        assertNotNull(resultado);
        assertEquals(pagamentoResponseDto.getId(), resultado.getId());
        assertEquals(pagamentoResponseDto.getCodigoPedido(), resultado.getCodigoPedido());
        assertEquals(pagamentoResponseDto.getPreco(), resultado.getPreco());
        assertEquals(pagamentoResponseDto.getStatus(), resultado.getStatus());

        verify(modelMapper, times(1)).map(pagamento, PagamentoResponseDto.class);
    }

    @Test
    @DisplayName("Deve converter lista de pagamentos para lista de PagamentoResponseDto com sucesso")
    void deveConverterListaPagamentosParaListaPagamentoResponseDtoComSucesso() {
        // Given
        Pagamento pagamento2 = new Pagamento();
        pagamento2.setId("pag-789");
        pagamento2.setCodigoPedido("ped-321");
        pagamento2.setPreco(new BigDecimal("75.50"));
        pagamento2.setStatus("PENDENTE");

        PagamentoResponseDto pagamentoResponseDto2 = new PagamentoResponseDto();
        pagamentoResponseDto2.setId("pag-789");
        pagamentoResponseDto2.setCodigoPedido("ped-321");
        pagamentoResponseDto2.setPreco(new BigDecimal("75.50"));
        pagamentoResponseDto2.setStatus("PENDENTE");

        List<Pagamento> pagamentos = Arrays.asList(pagamento, pagamento2);

        when(modelMapper.map(pagamento, PagamentoResponseDto.class))
                .thenReturn(pagamentoResponseDto);
        when(modelMapper.map(pagamento2, PagamentoResponseDto.class))
                .thenReturn(pagamentoResponseDto2);

        // When
        List<PagamentoResponseDto> resultado = presenter.pagamentosParaPagamentoResponseDTOs(pagamentos);

        // Then
        assertNotNull(resultado);
        assertEquals(2, resultado.size());

        assertEquals(pagamentoResponseDto.getId(), resultado.get(0).getId());
        assertEquals(pagamentoResponseDto.getCodigoPedido(), resultado.get(0).getCodigoPedido());
        assertEquals(pagamentoResponseDto.getPreco(), resultado.get(0).getPreco());

        assertEquals(pagamentoResponseDto2.getId(), resultado.get(1).getId());
        assertEquals(pagamentoResponseDto2.getCodigoPedido(), resultado.get(1).getCodigoPedido());
        assertEquals(pagamentoResponseDto2.getPreco(), resultado.get(1).getPreco());

        verify(modelMapper, times(1)).map(pagamento, PagamentoResponseDto.class);
        verify(modelMapper, times(1)).map(pagamento2, PagamentoResponseDto.class);
    }

    @Test
    @DisplayName("Deve converter lista vazia de pagamentos corretamente")
    void deveConverterListaVaziaDePagamentosCorretamente() {
        // Given
        List<Pagamento> pagamentosVazios = Collections.emptyList();

        // When
        List<PagamentoResponseDto> resultado = presenter.pagamentosParaPagamentoResponseDTOs(pagamentosVazios);

        // Then
        assertNotNull(resultado);
        assertTrue(resultado.isEmpty());

        verify(modelMapper, never()).map(any(), any());
    }

    @Test
    @DisplayName("Deve converter lista com um único pagamento")
    void deveConverterListaComUmUnicoPagamento() {
        // Given
        List<Pagamento> pagamentosSingleton = List.of(pagamento);
        when(modelMapper.map(pagamento, PagamentoResponseDto.class))
                .thenReturn(pagamentoResponseDto);

        // When
        List<PagamentoResponseDto> resultado = presenter.pagamentosParaPagamentoResponseDTOs(pagamentosSingleton);

        // Then
        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals(pagamentoResponseDto.getId(), resultado.get(0).getId());
        assertEquals(pagamentoResponseDto.getCodigoPedido(), resultado.get(0).getCodigoPedido());

        verify(modelMapper, times(1)).map(pagamento, PagamentoResponseDto.class);
    }

    @Test
    @DisplayName("Deve tratar pagamento com valores nulos corretamente")
    void deveTratarPagamentoComValoresNulosCorretamente() {
        // Given
        Pagamento pagamentoComNulos = new Pagamento();
        pagamentoComNulos.setId("pag-nulo");
        pagamentoComNulos.setCodigoPedido(null);
        pagamentoComNulos.setPreco(null);
        pagamentoComNulos.setStatus(null);

        PagamentoResponseDto responseComNulos = new PagamentoResponseDto();
        responseComNulos.setId("pag-nulo");
        responseComNulos.setCodigoPedido(null);
        responseComNulos.setPreco(null);
        responseComNulos.setStatus(null);

        when(modelMapper.map(pagamentoComNulos, PagamentoResponseDto.class))
                .thenReturn(responseComNulos);

        // When
        PagamentoResponseDto resultado = presenter.pagamentoParaPagamentoResponseDTO(pagamentoComNulos);

        // Then
        assertNotNull(resultado);
        assertEquals("pag-nulo", resultado.getId());
        assertNull(resultado.getCodigoPedido());
        assertNull(resultado.getPreco());
        assertNull(resultado.getStatus());

        verify(modelMapper, times(1)).map(pagamentoComNulos, PagamentoResponseDto.class);
    }

    @Test
    @DisplayName("Deve converter pagamento com valor zero")
    void deveConverterPagamentoComValorZero() {
        // Given
        Pagamento pagamentoZero = new Pagamento();
        pagamentoZero.setId("pag-zero");
        pagamentoZero.setCodigoPedido("ped-zero");
        pagamentoZero.setPreco(BigDecimal.ZERO);
        pagamentoZero.setStatus("GRATUITO");

        PagamentoResponseDto responseZero = new PagamentoResponseDto();
        responseZero.setId("pag-zero");
        responseZero.setCodigoPedido("ped-zero");
        responseZero.setPreco(BigDecimal.ZERO);
        responseZero.setStatus("GRATUITO");

        when(modelMapper.map(pagamentoZero, PagamentoResponseDto.class))
                .thenReturn(responseZero);

        // When
        PagamentoResponseDto resultado = presenter.pagamentoParaPagamentoResponseDTO(pagamentoZero);

        // Then
        assertNotNull(resultado);
        assertEquals("pag-zero", resultado.getId());
        assertEquals(BigDecimal.ZERO, resultado.getPreco());
        assertEquals("GRATUITO", resultado.getStatus());

        verify(modelMapper, times(1)).map(pagamentoZero, PagamentoResponseDto.class);
    }

    @Test
    @DisplayName("Deve converter múltiplos pagamentos com diferentes status")
    void deveConverterMultiplosPagamentosComDiferentesStatus() {
        // Given
        Pagamento pagamentoPendente = new Pagamento();
        pagamentoPendente.setId("pag-pendente");
        pagamentoPendente.setStatus("PENDENTE");

        Pagamento pagamentoAprovado = new Pagamento();
        pagamentoAprovado.setId("pag-aprovado");
        pagamentoAprovado.setStatus("APROVADO");

        Pagamento pagamentoRejeitado = new Pagamento();
        pagamentoRejeitado.setId("pag-rejeitado");
        pagamentoRejeitado.setStatus("REJEITADO");

        PagamentoResponseDto responsePendente = new PagamentoResponseDto();
        responsePendente.setId("pag-pendente");
        responsePendente.setStatus("PENDENTE");

        PagamentoResponseDto responseAprovado = new PagamentoResponseDto();
        responseAprovado.setId("pag-aprovado");
        responseAprovado.setStatus("APROVADO");

        PagamentoResponseDto responseRejeitado = new PagamentoResponseDto();
        responseRejeitado.setId("pag-rejeitado");
        responseRejeitado.setStatus("REJEITADO");

        List<Pagamento> pagamentos = Arrays.asList(pagamentoPendente, pagamentoAprovado, pagamentoRejeitado);

        when(modelMapper.map(pagamentoPendente, PagamentoResponseDto.class)).thenReturn(responsePendente);
        when(modelMapper.map(pagamentoAprovado, PagamentoResponseDto.class)).thenReturn(responseAprovado);
        when(modelMapper.map(pagamentoRejeitado, PagamentoResponseDto.class)).thenReturn(responseRejeitado);

        // When
        List<PagamentoResponseDto> resultado = presenter.pagamentosParaPagamentoResponseDTOs(pagamentos);

        // Then
        assertNotNull(resultado);
        assertEquals(3, resultado.size());
        assertEquals("PENDENTE", resultado.get(0).getStatus());
        assertEquals("APROVADO", resultado.get(1).getStatus());
        assertEquals("REJEITADO", resultado.get(2).getStatus());

        verify(modelMapper, times(3)).map(any(Pagamento.class), eq(PagamentoResponseDto.class));
    }
}
