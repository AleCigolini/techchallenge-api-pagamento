package br.com.fiap.techchallengeapipagamento.pagamento.application.mapper.model;

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
@DisplayName("DatabasePagamentoModelMapper Tests")
class DatabasePagamentoModelMapperTest {

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private DatabasePagamentoModelMapper mapper;

    private Pagamento pagamentoEntity;
    private Pagamento pagamentoDomain;

    @BeforeEach
    void setUp() {
        pagamentoEntity = new Pagamento();
        pagamentoEntity.setId("jpa-001");
        pagamentoEntity.setCodigoPedido("ped-001");
        pagamentoEntity.setPreco(new BigDecimal("100.00"));
        pagamentoEntity.setStatus("APROVADO");

        pagamentoDomain = new Pagamento();
        pagamentoDomain.setId("domain-001");
        pagamentoDomain.setCodigoPedido("ped-001");
        pagamentoDomain.setPreco(new BigDecimal("100.00"));
        pagamentoDomain.setStatus("APROVADO");
    }

    @Test
    @DisplayName("Deve converter lista de JPA entities para lista de domain objects")
    void deveConverterListaJpaEntitiesParaListaDomainObjects() {
        // Given
        Pagamento jpaPagamento2 = new Pagamento();
        jpaPagamento2.setId("jpa-002");
        jpaPagamento2.setCodigoPedido("ped-002");
        jpaPagamento2.setPreco(new BigDecimal("50.00"));
        jpaPagamento2.setStatus("PENDENTE");

        Pagamento domainPagamento2 = new Pagamento();
        domainPagamento2.setId("domain-002");
        domainPagamento2.setCodigoPedido("ped-002");
        domainPagamento2.setPreco(new BigDecimal("50.00"));
        domainPagamento2.setStatus("PENDENTE");

        List<Pagamento> jpaEntities = Arrays.asList(pagamentoEntity, jpaPagamento2);

        when(modelMapper.map(pagamentoEntity, Pagamento.class)).thenReturn(pagamentoDomain);
        when(modelMapper.map(jpaPagamento2, Pagamento.class)).thenReturn(domainPagamento2);

        // When
        List<Pagamento> resultado = mapper.jpaPagamentosEntityParaPagamentos(jpaEntities);

        // Then
        assertNotNull(resultado);
        assertEquals(2, resultado.size());
        assertEquals(pagamentoDomain.getId(), resultado.get(0).getId());
        assertEquals(domainPagamento2.getId(), resultado.get(1).getId());

        verify(modelMapper, times(1)).map(pagamentoEntity, Pagamento.class);
        verify(modelMapper, times(1)).map(jpaPagamento2, Pagamento.class);
    }

    @Test
    @DisplayName("Deve converter lista vazia de JPA entities corretamente")
    void deveConverterListaVaziaJpaEntitiesCorretamente() {
        // Given
        List<Pagamento> jpaEntitiesVazia = Collections.emptyList();

        // When
        List<Pagamento> resultado = mapper.jpaPagamentosEntityParaPagamentos(jpaEntitiesVazia);

        // Then
        assertNotNull(resultado);
        assertTrue(resultado.isEmpty());
        verify(modelMapper, never()).map(any(), any());
    }

    @Test
    @DisplayName("Deve converter pagamento domain para JPA entity")
    void deveConverterPagamentoDomainParaJpaEntity() {
        // Given
        when(modelMapper.map(pagamentoDomain, Pagamento.class)).thenReturn(pagamentoEntity);

        // When
        Pagamento resultado = mapper.pagamentoParaPagamentoEntity(pagamentoDomain);

        // Then
        assertNotNull(resultado);
        assertEquals(pagamentoEntity.getId(), resultado.getId());
        assertEquals(pagamentoEntity.getCodigoPedido(), resultado.getCodigoPedido());
        assertEquals(pagamentoEntity.getPreco(), resultado.getPreco());
        assertEquals(pagamentoEntity.getStatus(), resultado.getStatus());

        verify(modelMapper, times(1)).map(pagamentoDomain, Pagamento.class);
    }

    @Test
    @DisplayName("Deve converter JPA entity para pagamento domain")
    void deveConverterJpaEntityParaPagamentoDomain() {
        // Given
        when(modelMapper.map(pagamentoEntity, Pagamento.class)).thenReturn(pagamentoDomain);

        // When
        Pagamento resultado = mapper.pagamentoEntityParaPagamento(pagamentoEntity);

        // Then
        assertNotNull(resultado);
        assertEquals(pagamentoDomain.getId(), resultado.getId());
        assertEquals(pagamentoDomain.getCodigoPedido(), resultado.getCodigoPedido());
        assertEquals(pagamentoDomain.getPreco(), resultado.getPreco());
        assertEquals(pagamentoDomain.getStatus(), resultado.getStatus());

        verify(modelMapper, times(1)).map(pagamentoEntity, Pagamento.class);
    }

    @Test
    @DisplayName("Deve converter lista com um único elemento")
    void deveConverterListaComUmUnicoElemento() {
        // Given
        List<Pagamento> jpaEntitiesSingleton = List.of(pagamentoEntity);
        when(modelMapper.map(pagamentoEntity, Pagamento.class)).thenReturn(pagamentoDomain);

        // When
        List<Pagamento> resultado = mapper.jpaPagamentosEntityParaPagamentos(jpaEntitiesSingleton);

        // Then
        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals(pagamentoDomain.getId(), resultado.get(0).getId());

        verify(modelMapper, times(1)).map(pagamentoEntity, Pagamento.class);
    }

    @Test
    @DisplayName("Deve tratar conversão de pagamento com valores nulos")
    void deveTratarConversaoPagamentoComValoresNulos() {
        // Given
        Pagamento pagamentoComNulos = new Pagamento();
        pagamentoComNulos.setId("id-nulo");
        pagamentoComNulos.setCodigoPedido(null);
        pagamentoComNulos.setPreco(null);
        pagamentoComNulos.setStatus(null);

        Pagamento resultadoEsperado = new Pagamento();
        resultadoEsperado.setId("id-nulo");
        resultadoEsperado.setCodigoPedido(null);
        resultadoEsperado.setPreco(null);
        resultadoEsperado.setStatus(null);

        when(modelMapper.map(pagamentoComNulos, Pagamento.class)).thenReturn(resultadoEsperado);

        // When
        Pagamento resultado = mapper.pagamentoParaPagamentoEntity(pagamentoComNulos);

        // Then
        assertNotNull(resultado);
        assertEquals("id-nulo", resultado.getId());
        assertNull(resultado.getCodigoPedido());
        assertNull(resultado.getPreco());
        assertNull(resultado.getStatus());

        verify(modelMapper, times(1)).map(pagamentoComNulos, Pagamento.class);
    }

    @Test
    @DisplayName("Deve converter múltiplos pagamentos com diferentes status")
    void deveConverterMultiplosPagamentosComDiferentesStatus() {
        // Given
        Pagamento jpaPendente = new Pagamento();
        jpaPendente.setId("jpa-pendente");
        jpaPendente.setStatus("PENDENTE");

        Pagamento jpaAprovado = new Pagamento();
        jpaAprovado.setId("jpa-aprovado");
        jpaAprovado.setStatus("APROVADO");

        Pagamento jpaRejeitado = new Pagamento();
        jpaRejeitado.setId("jpa-rejeitado");
        jpaRejeitado.setStatus("REJEITADO");

        Pagamento domainPendente = new Pagamento();
        domainPendente.setId("domain-pendente");
        domainPendente.setStatus("PENDENTE");

        Pagamento domainAprovado = new Pagamento();
        domainAprovado.setId("domain-aprovado");
        domainAprovado.setStatus("APROVADO");

        Pagamento domainRejeitado = new Pagamento();
        domainRejeitado.setId("domain-rejeitado");
        domainRejeitado.setStatus("REJEITADO");

        List<Pagamento> jpaEntities = Arrays.asList(jpaPendente, jpaAprovado, jpaRejeitado);

        when(modelMapper.map(jpaPendente, Pagamento.class)).thenReturn(domainPendente);
        when(modelMapper.map(jpaAprovado, Pagamento.class)).thenReturn(domainAprovado);
        when(modelMapper.map(jpaRejeitado, Pagamento.class)).thenReturn(domainRejeitado);

        // When
        List<Pagamento> resultado = mapper.jpaPagamentosEntityParaPagamentos(jpaEntities);

        // Then
        assertNotNull(resultado);
        assertEquals(3, resultado.size());
        assertEquals("PENDENTE", resultado.get(0).getStatus());
        assertEquals("APROVADO", resultado.get(1).getStatus());
        assertEquals("REJEITADO", resultado.get(2).getStatus());

        verify(modelMapper, times(3)).map(any(Pagamento.class), eq(Pagamento.class));
    }

    @Test
    @DisplayName("Deve converter pagamento com valor zero")
    void deveConverterPagamentoComValorZero() {
        // Given
        Pagamento pagamentoZero = new Pagamento();
        pagamentoZero.setId("pag-zero");
        pagamentoZero.setPreco(BigDecimal.ZERO);
        pagamentoZero.setStatus("GRATUITO");

        Pagamento resultadoZero = new Pagamento();
        resultadoZero.setId("pag-zero");
        resultadoZero.setPreco(BigDecimal.ZERO);
        resultadoZero.setStatus("GRATUITO");

        when(modelMapper.map(pagamentoZero, Pagamento.class)).thenReturn(resultadoZero);

        // When
        Pagamento resultado = mapper.pagamentoEntityParaPagamento(pagamentoZero);

        // Then
        assertNotNull(resultado);
        assertEquals("pag-zero", resultado.getId());
        assertEquals(BigDecimal.ZERO, resultado.getPreco());
        assertEquals("GRATUITO", resultado.getStatus());

        verify(modelMapper, times(1)).map(pagamentoZero, Pagamento.class);
    }
}
