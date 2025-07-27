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