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
