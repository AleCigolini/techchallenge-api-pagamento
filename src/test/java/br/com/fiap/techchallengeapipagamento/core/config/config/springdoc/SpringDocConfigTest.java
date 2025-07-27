package br.com.fiap.techchallengeapipagamento.core.config.config.springdoc;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.PathItem;
import io.swagger.v3.oas.models.Paths;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.Operation;
import io.swagger.v3.oas.models.responses.ApiResponse;
import io.swagger.v3.oas.models.responses.ApiResponses;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springdoc.core.customizers.OpenApiCustomizer;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("SpringDocConfig Tests")
class SpringDocConfigTest {

    private SpringDocConfig springDocConfig;

    @BeforeEach
    void setUp() {
        springDocConfig = new SpringDocConfig();
    }

    @Test
    @DisplayName("Deve criar OpenAPI com configurações básicas")
    void deveCriarOpenAPIComConfiguracoes() {
        // When
        OpenAPI openAPI = springDocConfig.openAPI();

        // Then
        assertNotNull(openAPI);
        assertNotNull(openAPI.getInfo());
        assertEquals("Fiap Tech Challenge", openAPI.getInfo().getTitle());
        assertEquals("v1", openAPI.getInfo().getVersion());
        assertEquals("REST API da Fiap Tech Challenge", openAPI.getInfo().getDescription());
    }

    @Test
    @DisplayName("Deve configurar license corretamente")
    void deveConfigurarLicenseCorretamente() {
        // When
        OpenAPI openAPI = springDocConfig.openAPI();

        // Then
        License license = openAPI.getInfo().getLicense();
        assertNotNull(license);
        assertEquals("Apache 2.0", license.getName());
        assertEquals("http://springdoc.com", license.getUrl());
    }

    @Test
    @DisplayName("Deve configurar documentação externa")
    void deveConfigurarDocumentacaoExterna() {
        // When
        OpenAPI openAPI = springDocConfig.openAPI();

        // Then
        assertNotNull(openAPI.getExternalDocs());
        assertEquals("Fiap Tech Challenge", openAPI.getExternalDocs().getDescription());
        assertEquals("https://techchallenge.com", openAPI.getExternalDocs().getUrl());
    }

    @Test
    @DisplayName("Deve configurar components com schemas e responses")
    void deveConfigurarComponentsComSchemasEResponses() {
        // When
        OpenAPI openAPI = springDocConfig.openAPI();

        // Then
        assertNotNull(openAPI.getComponents());
        assertNotNull(openAPI.getComponents().getSchemas());
        assertNotNull(openAPI.getComponents().getResponses());
    }

    @Test
    @DisplayName("Deve criar OpenApiCustomizer")
    void deveCriarOpenApiCustomizer() {
        // When
        OpenApiCustomizer customizer = springDocConfig.openApiCustomizer();

        // Then
        assertNotNull(customizer);
    }

    @Test
    @DisplayName("Deve aplicar responses customizadas para operações GET")
    void deveAplicarResponsesCustomizadasParaOperacoesGET() {
        // Given
        OpenApiCustomizer customizer = springDocConfig.openApiCustomizer();
        OpenAPI openAPI = criarOpenAPIComOperacao(PathItem.HttpMethod.GET);

        // When
        customizer.customise(openAPI);

        // Then
        Operation operation = openAPI.getPaths().get("/test").getGet();
        ApiResponses responses = operation.getResponses();
        assertTrue(responses.containsKey("406"));
        assertTrue(responses.containsKey("500"));
    }

    @Test
    @DisplayName("Deve aplicar responses customizadas para operações POST")
    void deveAplicarResponsesCustomizadasParaOperacoesPOST() {
        // Given
        OpenApiCustomizer customizer = springDocConfig.openApiCustomizer();
        OpenAPI openAPI = criarOpenAPIComOperacao(PathItem.HttpMethod.POST);

        // When
        customizer.customise(openAPI);

        // Then
        Operation operation = openAPI.getPaths().get("/test").getPost();
        ApiResponses responses = operation.getResponses();
        assertTrue(responses.containsKey("400"));
        assertTrue(responses.containsKey("500"));
    }

    @Test
    @DisplayName("Deve aplicar responses customizadas para operações PUT")
    void deveAplicarResponsesCustomizadasParaOperacoesPUT() {
        // Given
        OpenApiCustomizer customizer = springDocConfig.openApiCustomizer();
        OpenAPI openAPI = criarOpenAPIComOperacao(PathItem.HttpMethod.PUT);

        // When
        customizer.customise(openAPI);

        // Then
        Operation operation = openAPI.getPaths().get("/test").getPut();
        ApiResponses responses = operation.getResponses();
        assertTrue(responses.containsKey("400"));
        assertTrue(responses.containsKey("500"));
    }

    @Test
    @DisplayName("Deve aplicar responses customizadas para operações DELETE")
    void deveAplicarResponsesCustomizadasParaOperacoesDELETE() {
        // Given
        OpenApiCustomizer customizer = springDocConfig.openApiCustomizer();
        OpenAPI openAPI = criarOpenAPIComOperacao(PathItem.HttpMethod.DELETE);

        // When
        customizer.customise(openAPI);

        // Then
        Operation operation = openAPI.getPaths().get("/test").getDelete();
        ApiResponses responses = operation.getResponses();
        assertTrue(responses.containsKey("500"));
    }

    @Test
    @DisplayName("Deve criar sempre nova instância do OpenAPI")
    void deveCriarSempreNovaInstanciaDoOpenAPI() {
        // When
        OpenAPI openAPI1 = springDocConfig.openAPI();
        OpenAPI openAPI2 = springDocConfig.openAPI();

        // Then
        assertNotNull(openAPI1);
        assertNotNull(openAPI2);
        assertNotSame(openAPI1, openAPI2);
    }

    private OpenAPI criarOpenAPIComOperacao(PathItem.HttpMethod httpMethod) {
        OpenAPI openAPI = new OpenAPI();
        Paths paths = new Paths();
        PathItem pathItem = new PathItem();
        Operation operation = new Operation();
        operation.setResponses(new ApiResponses());

        switch (httpMethod) {
            case GET:
                pathItem.setGet(operation);
                break;
            case POST:
                pathItem.setPost(operation);
                break;
            case PUT:
                pathItem.setPut(operation);
                break;
            case DELETE:
                pathItem.setDelete(operation);
                break;
        }

        paths.addPathItem("/test", pathItem);
        openAPI.setPaths(paths);
        return openAPI;
    }
}
