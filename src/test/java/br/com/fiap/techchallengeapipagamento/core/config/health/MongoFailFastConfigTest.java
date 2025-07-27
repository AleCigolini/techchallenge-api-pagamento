package br.com.fiap.techchallengeapipagamento.core.config.health;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.context.event.ApplicationFailedEvent;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.data.mongodb.UncategorizedMongoDbException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Teste unitário para MongoFailFastConfig
 * Seguindo o padrão de PagamentoRestControllerImplTest
 */
public class MongoFailFastConfigTest {

    private MongoFailFastConfig mongoFailFastConfig;

    @Mock
    private ConfigurableApplicationContext applicationContext;

    @Mock
    private ApplicationFailedEvent applicationFailedEvent;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mongoFailFastConfig = new MongoFailFastConfig();
    }

    @AfterEach
    public void tearDown() {
        reset(applicationContext, applicationFailedEvent);
    }

    @Test
    public void deveProcessarEventoApplicationFailedEventSemErro() {
        // given
        IllegalArgumentException excecaoNaoMongo = new IllegalArgumentException("Argumento inválido");
        when(applicationFailedEvent.getException()).thenReturn(excecaoNaoMongo);

        // when & then
        assertDoesNotThrow(() -> mongoFailFastConfig.onApplicationEvent(applicationFailedEvent));
    }

}
