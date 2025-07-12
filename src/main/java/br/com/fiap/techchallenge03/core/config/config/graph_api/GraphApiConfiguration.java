package br.com.fiap.techchallenge03.core.config.config.graph_api;

import com.azure.identity.ClientSecretCredential;
import com.azure.identity.ClientSecretCredentialBuilder;
import com.microsoft.graph.serviceclient.GraphServiceClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GraphApiConfiguration {
    @Value("${azure.entra-id.tenant-id}")
    private String tenantId;

    @Value("${azure.entra-id.client-id}")
    private String clientId;

    @Value("${azure.entra-id.client-secret}")
    private String clientSecret;

    @Bean
    public ClientSecretCredential clientSecretCredential() {
        return new ClientSecretCredentialBuilder()
                .clientId(clientId)
                .clientSecret(clientSecret)
                .tenantId(tenantId)
                .build();
    }

    @Bean
    public GraphServiceClient graphServiceClient(ClientSecretCredential credential) {
        return new GraphServiceClient(credential, "https://graph.microsoft.com/.default");
    }
}
