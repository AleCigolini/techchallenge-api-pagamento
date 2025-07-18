package br.com.fiap.techchallenge03.core.config.config;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoClientConfiguration;
import org.springframework.data.mongodb.core.MongoTemplate;

@Configuration
public class MongoConfig extends AbstractMongoClientConfiguration {

    @Value("${spring.data.mongodb.uri}")
    private String mongoUri;

    @Value("${spring.data.mongodb.database}")
    private String database;

    @Override
    protected String getDatabaseName() {
        return database;
    }

    @Override
    @Bean
    public MongoClient mongoClient() {
        MongoClientSettings settings = MongoClientSettings.builder()
                .applyConnectionString(new ConnectionString(mongoUri))
                .applyToSocketSettings(builder ->
                    builder.connectTimeout(3, java.util.concurrent.TimeUnit.SECONDS)
                          .readTimeout(5, java.util.concurrent.TimeUnit.SECONDS))
                .applyToClusterSettings(builder ->
                    builder.serverSelectionTimeout(5, java.util.concurrent.TimeUnit.SECONDS))
                .build();

        return MongoClients.create(settings);
    }

    @Bean(name = "mongoTemplate")
    public MongoTemplate mongoTemplate() {
        try {
            MongoTemplate mongoTemplate = new MongoTemplate(mongoClient(), getDatabaseName());
            // Testa a conexão
            mongoTemplate.executeCommand("{ ping: 1 }");
            return mongoTemplate;
        } catch (Exception e) {
            throw new RuntimeException("Falha ao conectar com MongoDB. A aplicação será encerrada.", e);
        }
    }
}
