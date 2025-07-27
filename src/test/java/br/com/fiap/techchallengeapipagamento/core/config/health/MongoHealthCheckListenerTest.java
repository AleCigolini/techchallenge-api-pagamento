package br.com.fiap.techchallengeapipagamento.core.config.health;

import com.mongodb.MongoException;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.data.mongodb.core.MongoTemplate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * Teste unitário para MongoHealthCheckListener
 * Seguindo o padrão de PagamentoRestControllerImplTest
 */
public class MongoHealthCheckListenerTest {

    private MongoHealthCheckListener mongoHealthCheckListener;

    @Mock
    private MongoTemplate mongoTemplate;

    @Mock
    private MongoDatabase mongoDatabase;

    @Mock
    private ConfigurableApplicationContext applicationContext;

    @Mock
    private ApplicationReadyEvent applicationReadyEvent;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mongoHealthCheckListener = new MongoHealthCheckListener(mongoTemplate);
    }

    @AfterEach
    public void tearDown() {
        reset(mongoTemplate, mongoDatabase, applicationContext, applicationReadyEvent);
    }

    @Test
    public void deveExecutarPingComSucessoQuandoMongoDBEstaDisponivel() {
        // given
        when(mongoTemplate.getDb()).thenReturn(mongoDatabase);
        when(mongoDatabase.runCommand(any(Document.class))).thenReturn(new Document("ok", 1));

        // when
        mongoHealthCheckListener.onApplicationEvent(applicationReadyEvent);

        // then
        verify(mongoTemplate).getDb();
        verify(mongoDatabase).runCommand(argThat(doc ->
            doc.toBsonDocument().containsKey("ping")));
    }

    @Test
    public void deveExecutarComandoPingComParametrosCorretos() {
        // given
        when(mongoTemplate.getDb()).thenReturn(mongoDatabase);
        when(mongoDatabase.runCommand(any(Document.class))).thenReturn(new Document("ok", 1));

        // when
        mongoHealthCheckListener.onApplicationEvent(applicationReadyEvent);

        // then
        verify(mongoDatabase).runCommand(argThat(doc -> {
            assertNotNull(doc);
            assertTrue(doc.toBsonDocument().containsKey("ping"));
            return true;
        }));
    }

    @Test
    public void deveProcessarEventoApplicationReadyEvent() {
        // given
        when(mongoTemplate.getDb()).thenReturn(mongoDatabase);
        when(mongoDatabase.runCommand(any(Document.class))).thenReturn(new Document("ok", 1));

        // when
        assertDoesNotThrow(() -> mongoHealthCheckListener.onApplicationEvent(applicationReadyEvent));

        // then
        verify(mongoTemplate).getDb();
    }

    @Test
    public void deveManterConsistenciaNoFluxoDeExecucao() {
        // given
        when(mongoTemplate.getDb()).thenReturn(mongoDatabase);
        when(mongoDatabase.runCommand(any(Document.class))).thenReturn(new Document("ok", 1));

        // when - primeira execução
        mongoHealthCheckListener.onApplicationEvent(applicationReadyEvent);

        // then - verifica primeira execução
        verify(mongoTemplate, times(1)).getDb();

        // when - segunda execução
        mongoHealthCheckListener.onApplicationEvent(applicationReadyEvent);

        // then - verifica segunda execução
        verify(mongoTemplate, times(2)).getDb();
    }
}
