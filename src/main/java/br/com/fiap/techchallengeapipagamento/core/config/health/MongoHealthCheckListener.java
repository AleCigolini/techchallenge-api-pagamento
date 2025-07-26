package br.com.fiap.techchallengeapipagamento.core.config.health;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;

@Component
public class MongoHealthCheckListener implements ApplicationListener<ApplicationReadyEvent> {

    private static final Logger logger = LoggerFactory.getLogger(MongoHealthCheckListener.class);
    private final MongoTemplate mongoTemplate;

    public MongoHealthCheckListener(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        try {
            mongoTemplate.getDb().runCommand(new org.bson.Document("ping", 1));
            logger.info("Conexão com o MongoDB estabelecida com sucesso");
        } catch (Exception e) {
            logger.error("Falha ao estabelecer conexão com o MongoDB. A aplicação será finalizada.", e);
            System.exit(1);
        }
    }
}
