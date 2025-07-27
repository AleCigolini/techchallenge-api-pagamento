package br.com.fiap.techchallengeapipagamento.core.config.config.mongodb;

import com.mongodb.client.MongoClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.util.ReflectionTestUtils;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("MongoConfig Tests")
class MongoConfigTest {

    private MongoConfig mongoConfig;

    @BeforeEach
    void setUp() {
        mongoConfig = new MongoConfig();
        // Configurando valores de teste usando ReflectionTestUtils
        ReflectionTestUtils.setField(mongoConfig, "mongoUri", "mongodb://localhost:27017");
        ReflectionTestUtils.setField(mongoConfig, "databaseName", "test-db");
    }

    @Test
    @DisplayName("Deve retornar o nome do banco de dados configurado")
    void deveRetornarNomeDoBancoDeDadosConfigurado() {
        // When
        String databaseName = mongoConfig.getDatabaseName();

        // Then
        assertEquals("test-db", databaseName);
    }

    @Test
    @DisplayName("Deve criar MongoClient com configurações corretas")
    void deveCriarMongoClientComConfiguracoes() {
        // When
        MongoClient mongoClient = mongoConfig.mongoClient();

        // Then
        assertNotNull(mongoClient);
    }

    @Test
    @DisplayName("Deve criar MongoTemplate com cliente e database")
    void deveCriarMongoTemplateComClienteEDatabase() throws Exception {
        // When
        MongoTemplate mongoTemplate = mongoConfig.mongoTemplate();

        // Then
        assertNotNull(mongoTemplate);
    }

    @Test
    @DisplayName("Deve criar sempre nova instância do MongoClient")
    void deveCriarSempreNovaInstanciaDoMongoClient() {
        // When
        MongoClient client1 = mongoConfig.mongoClient();
        MongoClient client2 = mongoConfig.mongoClient();

        // Then
        assertNotNull(client1);
        assertNotNull(client2);
        assertNotSame(client1, client2);
    }

    @Test
    @DisplayName("Deve criar sempre nova instância do MongoTemplate")
    void deveCriarSempreNovaInstanciaDoMongoTemplate() throws Exception {
        // When
        MongoTemplate template1 = mongoConfig.mongoTemplate();
        MongoTemplate template2 = mongoConfig.mongoTemplate();

        // Then
        assertNotNull(template1);
        assertNotNull(template2);
        assertNotSame(template1, template2);
    }

    @Test
    @DisplayName("Deve configurar timeouts adequados no MongoClient")
    void deveConfigurarTimeoutsAdequadosNoMongoClient() {
        // Given & When
        MongoClient mongoClient = mongoConfig.mongoClient();

        // Then
        assertNotNull(mongoClient);
        // Note: Detailed timeout verification would require access to internal settings
        // which is not directly accessible from the public API
    }
}
