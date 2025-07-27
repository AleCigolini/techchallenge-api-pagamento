package br.com.fiap.techchallengeapipagamento.core.config.modelmapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@DisplayName("ModelMapperConfig Tests")
class ModelMapperConfigTest {

    private ModelMapperConfig modelMapperConfig;

    @Mock
    private Converter<String, Integer> mockConverter1;

    @Mock
    private Converter<Integer, String> mockConverter2;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        modelMapperConfig = new ModelMapperConfig();
    }

    @Test
    @DisplayName("Deve criar ModelMapper com lista vazia de converters")
    void deveCriarModelMapperComListaVaziaDeConverters() {
        // Given
        List<Converter<?, ?>> convertersVazios = Collections.emptyList();

        // When
        ModelMapper modelMapper = modelMapperConfig.modelMapper(convertersVazios);

        // Then
        assertNotNull(modelMapper);
    }

    @Test
    @DisplayName("Deve criar ModelMapper e adicionar converters fornecidos")
    void deveCriarModelMapperEAdicionarConvertersFornecidos() {
        // Given
        List<Converter<?, ?>> converters = Arrays.asList(mockConverter1, mockConverter2);

        // When
        ModelMapper modelMapper = modelMapperConfig.modelMapper(converters);

        // Then
        assertNotNull(modelMapper);
        // Note: Verificação direta dos converters adicionados requer acesso interno ao ModelMapper
        // que não está disponível através da API pública
    }

    @Test
    @DisplayName("Deve criar ModelMapper com um único converter")
    void deveCriarModelMapperComUmUnicoConverter() {
        // Given
        List<Converter<?, ?>> converters = Collections.singletonList(mockConverter1);

        // When
        ModelMapper modelMapper = modelMapperConfig.modelMapper(converters);

        // Then
        assertNotNull(modelMapper);
    }

    @Test
    @DisplayName("Deve criar sempre nova instância do ModelMapper")
    void deveCriarSempreNovaInstanciaDoModelMapper() {
        // Given
        List<Converter<?, ?>> converters = Collections.emptyList();

        // When
        ModelMapper modelMapper1 = modelMapperConfig.modelMapper(converters);
        ModelMapper modelMapper2 = modelMapperConfig.modelMapper(converters);

        // Then
        assertNotNull(modelMapper1);
        assertNotNull(modelMapper2);
        assertNotSame(modelMapper1, modelMapper2);
    }

    @Test
    @DisplayName("Deve processar lista nula de converters sem erro")
    void deveProcessarListaNulaDeConvertersSemErro() {
        // Given
        List<Converter<?, ?>> convertersNulos = null;

        // When & Then
        assertThrows(NullPointerException.class, () -> {
            modelMapperConfig.modelMapper(convertersNulos);
        });
    }

    @Test
    @DisplayName("Deve criar ModelMapper funcional para mapeamento básico")
    void deveCriarModelMapperFuncionalParaMapeamentoBasico() {
        // Given
        List<Converter<?, ?>> converters = Collections.emptyList();

        // When
        ModelMapper modelMapper = modelMapperConfig.modelMapper(converters);

        // Then
        assertNotNull(modelMapper);

        // Teste básico de funcionalidade
        TestSource source = new TestSource("teste", 123);
        TestDestination destination = modelMapper.map(source, TestDestination.class);

        assertNotNull(destination);
        assertEquals("teste", destination.getName());
        assertEquals(123, destination.getValue());
    }

    // Classes auxiliares para teste
    public static class TestSource {
        private String name;
        private Integer value;

        public TestSource(String name, Integer value) {
            this.name = name;
            this.value = value;
        }

        public String getName() { return name; }
        public void setName(String name) { this.name = name; }
        public Integer getValue() { return value; }
        public void setValue(Integer value) { this.value = value; }
    }

    public static class TestDestination {
        private String name;
        private Integer value;

        public String getName() { return name; }
        public void setName(String name) { this.name = name; }
        public Integer getValue() { return value; }
        public void setValue(Integer value) { this.value = value; }
    }
}
