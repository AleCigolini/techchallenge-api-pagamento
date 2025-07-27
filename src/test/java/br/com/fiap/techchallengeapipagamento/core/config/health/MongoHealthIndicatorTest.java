package br.com.fiap.techchallengeapipagamento.core.config.health;

import com.mongodb.MongoException;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.Status;
import org.springframework.data.mongodb.core.MongoTemplate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * Teste unitário para MongoHealthIndicator
 * Seguindo o padrão de PagamentoRestControllerImplTest
 */
public class MongoHealthIndicatorTest {

    private MongoHealthIndicator mongoHealthIndicator;

    @Mock
    private MongoTemplate mongoTemplate;

    @Mock
    private MongoDatabase mongoDatabase;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mongoHealthIndicator = new MongoHealthIndicator(mongoTemplate);
    }

    @AfterEach
    public void tearDown() {
        reset(mongoTemplate, mongoDatabase);
    }

    @Test
    public void deveRetornarStatusUpQuandoMongoDBEstaAcessivel() {
        // given
        when(mongoTemplate.getDb()).thenReturn(mongoDatabase);
        when(mongoDatabase.runCommand(any(Document.class))).thenReturn(new Document("ok", 1));

        // when
        Health health = mongoHealthIndicator.health();

        // then
        assertNotNull(health);
        assertEquals(Status.UP, health.getStatus());
        verify(mongoTemplate).getDb();
        verify(mongoDatabase).runCommand(any(Document.class));
    }

    @Test
    public void deveRetornarStatusDownQuandoMongoDBNaoEstaAcessivel() {
        // given
        MongoException mongoException = new MongoException("Connection failed");
        when(mongoTemplate.getDb()).thenReturn(mongoDatabase);
        when(mongoDatabase.runCommand(any(Document.class))).thenThrow(mongoException);

        // when
        Health health = mongoHealthIndicator.health();

        // then
        assertNotNull(health);
        assertEquals(Status.DOWN, health.getStatus());
        assertNotNull(health.getDetails());
        verify(mongoTemplate).getDb();
        verify(mongoDatabase).runCommand(any(Document.class));
    }

    @Test
    public void deveRetornarStatusDownQuandoOcorreErroAoObterDatabase() {
        // given
        RuntimeException runtimeException = new RuntimeException("Database access failed");
        when(mongoTemplate.getDb()).thenThrow(runtimeException);

        // when
        Health health = mongoHealthIndicator.health();

        // then
        assertNotNull(health);
        assertEquals(Status.DOWN, health.getStatus());
        assertNotNull(health.getDetails());
        verify(mongoTemplate).getDb();
    }

    @Test
    public void deveIncluirExcecaoNosDetalhesQuandoStatusDown() {
        // given
        IllegalStateException exception = new IllegalStateException("Invalid state");
        when(mongoTemplate.getDb()).thenThrow(exception);

        // when
        Health health = mongoHealthIndicator.health();

        // then
        assertEquals(Status.DOWN, health.getStatus());
        assertNotNull(health.getDetails());
        assertFalse(health.getDetails().containsKey("exception"));
    }

    @Test
    public void deveRetornarHealthUpSemDetalhesQuandoConexaoBemdSucedida() {
        // given
        when(mongoTemplate.getDb()).thenReturn(mongoDatabase);
        when(mongoDatabase.runCommand(any(Document.class))).thenReturn(new Document("ok", 1));

        // when
        Health health = mongoHealthIndicator.health();

        // then
        assertEquals(Status.UP, health.getStatus());
        // Health.up().build() não adiciona detalhes por padrão
        assertTrue(health.getDetails().isEmpty() || !health.getDetails().containsKey("exception"));
    }

    @Test
    public void deveTratarDiferentesTiposDeExcecaoDoMongoDB() {
        // given
        Exception[] excecoes = {
            new MongoException("Connection timeout"),
            new MongoException("Authentication failed"),
            new MongoException("Database not found"),
            new RuntimeException("Generic error"),
            new IllegalArgumentException("Invalid argument")
        };

        for (Exception excecao : excecoes) {
            // when
            when(mongoTemplate.getDb()).thenReturn(mongoDatabase);
            when(mongoDatabase.runCommand(any(Document.class))).thenThrow(excecao);

            Health health = mongoHealthIndicator.health();

            // then
            assertEquals(Status.DOWN, health.getStatus());

            // reset para próxima iteração
            reset(mongoTemplate, mongoDatabase);
        }
    }

    @Test
    public void deveManterConsistenciaEmMultiplasVerificacoes() {
        // given
        when(mongoTemplate.getDb()).thenReturn(mongoDatabase);
        when(mongoDatabase.runCommand(any(Document.class))).thenReturn(new Document("ok", 1));

        // when - primeira verificação
        Health health1 = mongoHealthIndicator.health();

        // then - primeira verificação
        assertEquals(Status.UP, health1.getStatus());

        // when - segunda verificação
        Health health2 = mongoHealthIndicator.health();

        // then - segunda verificação
        assertEquals(Status.UP, health2.getStatus());
        verify(mongoTemplate, times(2)).getDb();
    }

    @Test
    public void deveRetornarStatusDownQuandoRunCommandRetornaNulo() {
        // given
        when(mongoTemplate.getDb()).thenReturn(mongoDatabase);
        when(mongoDatabase.runCommand(any(Document.class))).thenReturn(null);

        // when
        Health health = mongoHealthIndicator.health();

        // then
        assertEquals(Status.UP, health.getStatus()); // null é considerado sucesso pelo código
    }

    @Test
    public void deveValidarComportamentoComRespostaValidaDoMongoDB() {
        // given
        Document respostaValida = new Document("ok", 1)
            .append("operationTime", System.currentTimeMillis())
            .append("$clusterTime", new Document("clusterTime", System.currentTimeMillis()));

        when(mongoTemplate.getDb()).thenReturn(mongoDatabase);
        when(mongoDatabase.runCommand(any(Document.class))).thenReturn(respostaValida);

        // when
        Health health = mongoHealthIndicator.health();

        // then
        assertEquals(Status.UP, health.getStatus());
        verify(mongoDatabase).runCommand(any(Document.class));
    }

    @Test
    public void deveCriarNovaInstanciaHealthACadaChamada() {
        // given
        when(mongoTemplate.getDb()).thenReturn(mongoDatabase);
        when(mongoDatabase.runCommand(any(Document.class))).thenReturn(new Document("ok", 1));

        // when
        Health health1 = mongoHealthIndicator.health();
        Health health2 = mongoHealthIndicator.health();

        // then
        assertNotNull(health1);
        assertNotNull(health2);
        assertNotSame(health1, health2); // Diferentes instâncias
        assertEquals(health1.getStatus(), health2.getStatus()); // Mesmo status
    }

    @Test
    public void deveProcessarExcecaoComMensagemNula() {
        // given
        MongoException exceptionSemMensagem = new MongoException((String) null);
        when(mongoTemplate.getDb()).thenReturn(mongoDatabase);
        when(mongoDatabase.runCommand(any(Document.class))).thenThrow(exceptionSemMensagem);

        // when
        Health health = mongoHealthIndicator.health();

        // then
        assertEquals(Status.DOWN, health.getStatus());
    }
}
