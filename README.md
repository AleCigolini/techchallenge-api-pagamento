# Tech Challenge - Sistema de Pagamentos

## Visão Geral
Este projeto é uma API REST desenvolvida utilizando tecnologias modernas do ecossistema Java/Spring para gerenciamento de pagamentos de pedidos. O sistema foi construído seguindo as melhores práticas de desenvolvimento e arquitetura de software.

## Tecnologias Utilizadas
- Java 21
- Spring Boot
- Spring Data JPA
- Spring MVC
- Lombok
- Docker
- Kubernetes

## Estrutura do Projeto
O projeto segue uma arquitetura moderna e organizada, contendo:
- `/src` - Código fonte da aplicação
- `/kubernetes` - Arquivos de configuração para deploy em Kubernetes
- `/assets` - Recursos estáticos do projeto
- `Dockerfile` e `docker-compose.yml` - Configurações para containerização
- `.env` - Arquivo para variáveis de ambiente

## Funcionalidades Principais
1. Gerenciamento de Pagamentos
2. Controle de pagamentos
3. APIs RESTful para integração (Mercado Pago e Pedido)

## Infraestrutura
- O projeto está preparado para containerização com Docker
- Suporte a orquestração com Kubernetes
- Configurações de CI/CD através do diretório `.github`

## Requisitos para Execução
- JDK 21
- Maven
- Docker (opcional)
- Kubernetes (opcional)

## Como Executar
1. Clone o repositório
2. Configure as variáveis de ambiente no arquivo `.env`
3. Execute utilizando Maven:
   ```bash
   ./mvnw spring-boot:run
   ```

   Ou via Docker:
   ```bash
   docker-compose up
   ```
4. Acesse `http://localhost:8080` e terá acesso ao Swagger da aplicação

## Cobertura Sonar
![sonar.png](src/main/resources/static/sonar.png)

## BDD - Behavior-Driven Development
Funcionalidade: Integração com serviço de pedido

Como sistema de pagamentos
<br>
Quero que, ao pagamento ser realizado com sucesso, o serviço de pagamento informe o serviço de pedidos 
<br>
Para a atualização do status do pedido

Cenário: Realização do pagamento com sucesso
<br>
Dado que o pagamento foi efetuado através do QR code do estabelecimento via Mercado Pago
<br>
Quando o serviço do Mercado Pago retorna sucesso ao realizar a compra
<br>
Então o status do pagamento é atualizado para aprovado
<br>
E o código do pagamento é enviado para o serviço de pedidos 

## Arquitetura
Clean Archtecture.

## Estruturação das pastas
![image](https://github.com/user-attachments/assets/c8996715-f174-4611-ab40-7c1d5ba35877)
Considerando o uso da clean archtecture foi pensada da seguinte maneira:
- As camadas presentation/infrasctructre equivalem a Framework & Drivers, sendo a presentation responsável por capturar a entrada do usuário e a infrastrucutre pela comunicação com camadas externas.
- A camada Application contempla as camadas Application Business Roles e Interface Adapters.
- A camda de Entities representa a camada Domain.

## Arquitetura Infraestrutura

### Diagrama de Fluxo
![Comunicao_Kubernetes](https://github.com/user-attachments/assets/ff35b655-1385-4738-b50d-7eb09826ff20)
- Dentro do Resource Group techchallenge-rg, há um IP Público que acessa o APIM (Azure API Management)
- Quando acessado e havendo configuração de suas políticas realiza a chamada para a function.
- O Ingress Controller então roteia as requisições para os diferentes serviços internos a depender da URI chamada, utilizando a comunicação via Cluster IP.
- As aplicações java se comunicam com seus respectivos databases utilizando a comunicação via Cluster IP.
Obs: Para saber mais sobre o recurso Standard_B2S: https://learn.microsoft.com/pt-br/azure/virtual-machines/sizes/general-purpose/bv1-series?tabs=sizebasic
### Diagrama de Componente
![Arquitetura_Kubernetes](https://github.com/user-attachments/assets/8c5c551b-f5d1-4f37-833c-bb082a6d6594)
O cluster k8s-fiap é configurado com dois namespaces principais, cada um com funções específicas:
- default: Namespace onde as aplicações principais são implantadas e gerenciadas, contendo os PODs:
  - java-app-*: microsserviço presente no cluster.
    - Ingress: Configurado para gerenciar o tráfego de entrada direcionado à aplicação Java.
    - Cluster IP: Endereço IP interno para comunicação dentro do cluster.
    - Deployment: Gerencia a implantação e a escalabilidade da aplicação Java.
    - Secret: Armazena dados sensíveis, como chaves de API ou credenciais usadas pela aplicação.
    - Horizontal Pod Autoscaler (HPA): Configurado para escalar automaticamente o número de réplicas do pod com base na utilização de CPU.
    - Configuração do HPA:
      - Mínimo de 1 e máximo de 3 réplicas.
      - Escala a partir da métrica de uso de CPU atingir 70%.
    - Role HPA: Define as permissões necessárias para que o HPA acesse métricas do cluster (como CPU e memória) para tomar decisões de escalabilidade.
- ingress-basic: é responsável por gerenciar o tráfego externo e rotear as requisições para os serviços no namespace default.
  - ingress-nginx-controller: Executa o controlador NGINX Ingress, que atua como ponto de entrada para requisições externas e roteia o tráfego para os serviços apropriados no namespace default.
    - Ingress: Define as regras de roteamento para requisições externas (por exemplo, rotear requisições para o serviço do java-app).
    - Service: Expõe o controlador NGINX internamente no cluster.
    - Endpoint: Mapeia os endpoints para os serviços internos.
    - Deployment: Gerencia a implantação do controlador NGINX.
    - ConfigMap: Armazena configurações do NGINX, como limites de requisições, timeouts e outras opções de personalização.
    - Secret: Armazena informações sensíveis, como certificados TLS para habilitar HTTPS.    
*Os arquivos de configuração do Kubernetes (em formato .yml) estão organizados no diretório kubernetes/, que contém os recursos descritos no diagrama.
