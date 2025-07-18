package br.com.fiap.techchallenge03.core.config.health;

import com.mongodb.client.MongoClient;
import org.bson.Document;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;

@Component
public class MongoHealthIndicator implements HealthIndicator {

    private final MongoTemplate mongoTemplate;
    private final MongoClient mongoClient;

    public MongoHealthIndicator(MongoTemplate mongoTemplate, MongoClient mongoClient) {
        this.mongoTemplate = mongoTemplate;
        this.mongoClient = mongoClient;
    }

    @Override
    public Health health() {
        try {
            Document pingResult = mongoTemplate.executeCommand("{ ping: 1 }");
            Document serverStatus = mongoTemplate.executeCommand("{ serverStatus: 1 }");

            if (pingResult.getDouble("ok") == 1.0) {
                return Health.up()
                        .withDetail("database", mongoTemplate.getDb().getName())
                        .withDetail("server", mongoClient.getClusterDescription().getClusterSettings().getHosts())
                        .withDetail("version", serverStatus.get("version"))
                        .withDetail("connections", serverStatus.get("connections"))
                        .build();
            }
            return Health.down()
                    .withDetail("error", "MongoDB ping command failed")
                    .withDetail("database", mongoTemplate.getDb().getName())
                    .build();
        } catch (Exception e) {
            return Health.down()
                    .withDetail("error", e.getMessage())
                    .withDetail("exception", e.getClass().getName())
                    .build();
        }
    }
}
