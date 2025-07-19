package br.com.fiap.techchallenge03.core.config.health;

import org.springframework.boot.context.event.ApplicationFailedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.data.mongodb.UncategorizedMongoDbException;
import org.springframework.stereotype.Component;

@Component
public class MongoFailFastConfig implements ApplicationListener<ApplicationFailedEvent> {

    @Override
    public void onApplicationEvent(ApplicationFailedEvent event) {
        Throwable exception = event.getException();
        if (exception instanceof UncategorizedMongoDbException) {
            System.err.println("Falha crítica ao conectar com MongoDB. Encerrando a aplicação...");
            System.exit(1);
        }
    }
}
