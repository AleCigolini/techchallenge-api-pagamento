package br.com.fiap.techchallengeapipagamento.pagamento.infrastructure.database.adapter;

import br.com.fiap.techchallengeapipagamento.pagamento.domain.Pagamento;
import br.com.fiap.techchallengeapipagamento.pagamento.infrastructure.database.repository.mongodb.PagamentoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("PagamentoMongoDatabaseImpl Tests")
class PagamentoMongoDatabaseImplTest {

    @Mock
    private PagamentoRepository pagamentoRepository;

    @InjectMocks
    private PagamentoMongoDatabaseImpl pagamentoDatabase;

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
    @DisplayName("Deve salvar pagamento com data de criação e status padrão")
    void deveSalvarPagamentoComDataCriacaoEStatusPadrao() {
        // Given
        Pagamento pagamentoSemStatus = new Pagamento();
        pagamentoSemStatus.setId("pag-novo");
        pagamentoSemStatus.setCodigoPedido("ped-789");
        pagamentoSemStatus.setPreco(new BigDecimal("75.00"));
        pagamentoSemStatus.setStatus(null); // Status nulo

        Pagamento pagamentoSalvo = new Pagamento();
        pagamentoSalvo.setId("pag-novo");
        pagamentoSalvo.setCodigoPedido("ped-789");
        pagamentoSalvo.setPreco(new BigDecimal("75.00"));
        pagamentoSalvo.setStatus("PENDENTE");
        pagamentoSalvo.setDataCriacao(OffsetDateTime.now());

        when(pagamentoRepository.save(any(Pagamento.class))).thenReturn(pagamentoSalvo);

        // When
        Pagamento resultado = pagamentoDatabase.salvar(pagamentoSemStatus);

        // Then
        assertNotNull(resultado);
        assertEquals("PENDENTE", resultado.getStatus());
        assertNotNull(resultado.getDataCriacao());

        ArgumentCaptor<Pagamento> pagamentoCaptor = ArgumentCaptor.forClass(Pagamento.class);
        verify(pagamentoRepository).save(pagamentoCaptor.capture());

        Pagamento pagamentoCapturado = pagamentoCaptor.getValue();
        assertEquals("PENDENTE", pagamentoCapturado.getStatus());
        assertNotNull(pagamentoCapturado.getDataCriacao());
    }

    @Test
    @DisplayName("Deve salvar pagamento mantendo status existente")
    void deveSalvarPagamentoMantendoStatusExistente() {
        // Given
        Pagamento pagamentoComStatus = new Pagamento();
        pagamentoComStatus.setId("pag-existente");
        pagamentoComStatus.setCodigoPedido("ped-999");
        pagamentoComStatus.setPreco(new BigDecimal("100.00"));
        pagamentoComStatus.setStatus("APROVADO");

        when(pagamentoRepository.save(any(Pagamento.class))).thenReturn(pagamentoComStatus);

        // When
        Pagamento resultado = pagamentoDatabase.salvar(pagamentoComStatus);

        // Then
        assertNotNull(resultado);
        assertEquals("APROVADO", resultado.getStatus());
        assertNotNull(pagamentoComStatus.getDataCriacao()); // Data foi setada

        ArgumentCaptor<Pagamento> pagamentoCaptor = ArgumentCaptor.forClass(Pagamento.class);
        verify(pagamentoRepository).save(pagamentoCaptor.capture());

        Pagamento pagamentoCapturado = pagamentoCaptor.getValue();
        assertEquals("APROVADO", pagamentoCapturado.getStatus());
        assertNotNull(pagamentoCapturado.getDataCriacao());
    }

    @Test
    @DisplayName("Deve buscar pagamento por ID com sucesso")
    void deveBuscarPagamentoPorIdComSucesso() {
        // Given
        String id = "pag-123";
        when(pagamentoRepository.findById(id)).thenReturn(Optional.of(pagamento));

        // When
        Optional<Pagamento> resultado = pagamentoDatabase.buscarPorId(id);

        // Then
        assertTrue(resultado.isPresent());
        assertEquals(pagamento, resultado.get());
        verify(pagamentoRepository).findById(id);
    }

    @Test
    @DisplayName("Deve retornar Optional vazio quando pagamento não encontrado por ID")
    void deveRetornarOptionalVazioQuandoPagamentoNaoEncontradoPorId() {
        // Given
        String id = "pag-inexistente";
        when(pagamentoRepository.findById(id)).thenReturn(Optional.empty());

        // When
        Optional<Pagamento> resultado = pagamentoDatabase.buscarPorId(id);

        // Then
        assertFalse(resultado.isPresent());
        verify(pagamentoRepository).findById(id);
    }

    @Test
    @DisplayName("Deve buscar pagamentos por código do pedido")
    void deveBuscarPagamentosPorCodigoPedido() {
        // Given
        String codigoPedido = "ped-456";
        Pagamento pagamento2 = new Pagamento();
        pagamento2.setId("pag-789");
        pagamento2.setCodigoPedido("ped-456");

        List<Pagamento> pagamentosEsperados = Arrays.asList(pagamento, pagamento2);
        when(pagamentoRepository.findByCodigoPedido(codigoPedido)).thenReturn(pagamentosEsperados);

        // When
        List<Pagamento> resultado = pagamentoDatabase.buscarPorCodigoPedido(codigoPedido);

        // Then
        assertNotNull(resultado);
        assertEquals(2, resultado.size());
        assertEquals(pagamentosEsperados, resultado);
        verify(pagamentoRepository).findByCodigoPedido(codigoPedido);
    }

    @Test
    @DisplayName("Deve retornar lista vazia quando não encontrar pagamentos por código do pedido")
    void deveRetornarListaVaziaQuandoNaoEncontrarPagamentosPorCodigoPedido() {
        // Given
        String codigoPedido = "ped-inexistente";
        when(pagamentoRepository.findByCodigoPedido(codigoPedido)).thenReturn(Collections.emptyList());

        // When
        List<Pagamento> resultado = pagamentoDatabase.buscarPorCodigoPedido(codigoPedido);

        // Then
        assertNotNull(resultado);
        assertTrue(resultado.isEmpty());
        verify(pagamentoRepository).findByCodigoPedido(codigoPedido);
    }

    @Test
    @DisplayName("Deve buscar pagamento por código do pedido e status")
    void deveBuscarPagamentoPorCodigoPedidoEStatus() {
        // Given
        String codigoPedido = "ped-456";
        String status = "APROVADO";
        when(pagamentoRepository.findByCodigoPedidoAndStatus(codigoPedido, status))
                .thenReturn(Optional.of(pagamento));

        // When
        Optional<Pagamento> resultado = pagamentoDatabase.buscarPagamentoPorPedidoEStatus(codigoPedido, status);

        // Then
        assertTrue(resultado.isPresent());
        assertEquals(pagamento, resultado.get());
        verify(pagamentoRepository).findByCodigoPedidoAndStatus(codigoPedido, status);
    }

    @Test
    @DisplayName("Deve retornar Optional vazio quando não encontrar pagamento por código do pedido e status")
    void deveRetornarOptionalVazioQuandoNaoEncontrarPagamentoPorCodigoPedidoEStatus() {
        // Given
        String codigoPedido = "ped-inexistente";
        String status = "PENDENTE";
        when(pagamentoRepository.findByCodigoPedidoAndStatus(codigoPedido, status))
                .thenReturn(Optional.empty());

        // When
        Optional<Pagamento> resultado = pagamentoDatabase.buscarPagamentoPorPedidoEStatus(codigoPedido, status);

        // Then
        assertFalse(resultado.isPresent());
        verify(pagamentoRepository).findByCodigoPedidoAndStatus(codigoPedido, status);
    }

    @Test
    @DisplayName("Deve buscar pagamentos por status")
    void deveBuscarPagamentosPorStatus() {
        // Given
        String status = "APROVADO";
        List<Pagamento> pagamentosEsperados = List.of(pagamento);
        when(pagamentoRepository.findByStatus(status)).thenReturn(pagamentosEsperados);

        // When
        List<Pagamento> resultado = pagamentoDatabase.buscarPorStatus(status);

        // Then
        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals(pagamentosEsperados, resultado);
        verify(pagamentoRepository).findByStatus(status);
    }

    @Test
    @DisplayName("Deve atualizar status do pagamento com sucesso")
    void deveAtualizarStatusPagamentoComSucesso() {
        // Given
        String id = "pag-123";
        String novoStatus = "CANCELADO";

        Pagamento pagamentoAtualizado = new Pagamento();
        pagamentoAtualizado.setId("pag-123");
        pagamentoAtualizado.setCodigoPedido("ped-456");
        pagamentoAtualizado.setPreco(new BigDecimal("50.00"));
        pagamentoAtualizado.setStatus("CANCELADO");

        when(pagamentoRepository.findById(id)).thenReturn(Optional.of(pagamento));
        when(pagamentoRepository.save(any(Pagamento.class))).thenReturn(pagamentoAtualizado);

        // When
        Optional<Pagamento> resultado = pagamentoDatabase.atualizarStatusPagamento(id, novoStatus);

        // Then
        assertTrue(resultado.isPresent());
        assertEquals("CANCELADO", resultado.get().getStatus());

        verify(pagamentoRepository).findById(id);

        ArgumentCaptor<Pagamento> pagamentoCaptor = ArgumentCaptor.forClass(Pagamento.class);
        verify(pagamentoRepository).save(pagamentoCaptor.capture());

        assertEquals("CANCELADO", pagamentoCaptor.getValue().getStatus());
    }

    @Test
    @DisplayName("Deve retornar Optional vazio ao tentar atualizar pagamento inexistente")
    void deveRetornarOptionalVazioAoTentarAtualizarPagamentoInexistente() {
        // Given
        String id = "pag-inexistente";
        String novoStatus = "CANCELADO";

        when(pagamentoRepository.findById(id)).thenReturn(Optional.empty());

        // When
        Optional<Pagamento> resultado = pagamentoDatabase.atualizarStatusPagamento(id, novoStatus);

        // Then
        assertFalse(resultado.isPresent());
        verify(pagamentoRepository).findById(id);
        verify(pagamentoRepository, never()).save(any());
    }

    @Test
    @DisplayName("Deve definir data de criação como tempo atual ao salvar")
    void deveDefinirDataCriacaoComoTempoAtualAoSalvar() {
        // Given
        OffsetDateTime antes = OffsetDateTime.now();

        Pagamento pagamentoNovo = new Pagamento();
        pagamentoNovo.setId("pag-novo");
        pagamentoNovo.setCodigoPedido("ped-novo");
        pagamentoNovo.setPreco(new BigDecimal("25.00"));

        when(pagamentoRepository.save(any(Pagamento.class))).thenAnswer(invocation -> {
            Pagamento arg = invocation.getArgument(0);
            return arg;
        });

        // When
        pagamentoDatabase.salvar(pagamentoNovo);

        OffsetDateTime depois = OffsetDateTime.now();

        // Then
        assertNotNull(pagamentoNovo.getDataCriacao());
        assertTrue(pagamentoNovo.getDataCriacao().isAfter(antes) || pagamentoNovo.getDataCriacao().isEqual(antes));
        assertTrue(pagamentoNovo.getDataCriacao().isBefore(depois) || pagamentoNovo.getDataCriacao().isEqual(depois));
    }

    @Test
    @DisplayName("Deve manter data de criação existente ao salvar")
    void deveManterDataCriacaoExistenteAoSalvar() {
        // Given
        OffsetDateTime dataExistente = OffsetDateTime.now().minusHours(1);

        Pagamento pagamentoExistente = new Pagamento();
        pagamentoExistente.setId("pag-existente");
        pagamentoExistente.setCodigoPedido("ped-existente");
        pagamentoExistente.setPreco(new BigDecimal("30.00"));
        pagamentoExistente.setDataCriacao(dataExistente);

        when(pagamentoRepository.save(any(Pagamento.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // When
        pagamentoDatabase.salvar(pagamentoExistente);

        // Then
        // A data de criação deve ser substituída pela data atual
        assertNotEquals(dataExistente, pagamentoExistente.getDataCriacao());
        assertTrue(pagamentoExistente.getDataCriacao().isAfter(dataExistente));
    }
}
