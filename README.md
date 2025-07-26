# Tech Challenge 03 - Autoatendimento de Fast Food
## Colaboradores
- Alessandro Cigolini
- Carlos Ferreira
- Josinaldo Fontes
- Sandro Nascimento

## Objetivo
Desenvolvida para otimizar a experiência do cliente e a gestão da lanchonete, esta aplicação backend atua como o coração do sistema de autoatendimento. 
Responsável por administrar de forma eficiente e segura todas as operações, desde a criação e gestão de produtos até o processamento de pedidos e a manutenção de um banco de dados completo sobre os clientes. 
Através da integração com o sistema de frente de loja, a aplicação garante a disponibilidade de informações precisas e atualizadas, proporcionando ao cliente autonomia na escolha e personalização dos pedidos, 
e à lanchonete, uma ferramenta poderosa para aumentar a eficiência operacional, reduzir custos e elevar a satisfação do consumidor.

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
![image](https://github.com/user-attachments/assets/0211d2e7-9e7f-486a-8005-b8a32682a7fc)
- Dentro do Resource Group techchallenge-rg, há uma zona de DNS configurada com o domínio techchallenge.app.br, tornando-o acessível externamente.
- Quando acessado, o tráfego é direcionado para o IP público do POD ingress-nginx-controller.
- O Ingress Controller então roteia as requisições para o serviço interno Java App, utilizando a comunicação via Cluster IP.
- O Java App, quando necessário, se comunica com o POD PostgreSQL, que contém a base de dados, também via Cluster IP.
### Diagrama de Componente
![image](https://github.com/user-attachments/assets/26923c74-d144-4001-88e0-a3d90be58a36)
O cluster techchallenge-k8s é configurado com dois namespaces principais, cada um com funções específicas:
- default: Namespace onde as aplicações principais são implantadas e gerenciadas, contendo os PODs:
  - java-app: aplicação principal que contém as APIs do sistema.
    - Ingress: Configurado para gerenciar o tráfego de entrada direcionado à aplicação Java.
    - Cluster IP: Endereço IP interno para comunicação dentro do cluster.
    - Deployment: Gerencia a implantação e a escalabilidade da aplicação Java.
    - Secret: Armazena dados sensíveis, como chaves de API ou credenciais usadas pela aplicação.
    - Horizontal Pod Autoscaler (HPA): Configurado para escalar automaticamente o número de réplicas do pod com base na utilização de CPU.
    - Configuração do HPA:
      - Mínimo de 1 e máximo de 3 réplicas.
      - Escala a partir da métrica de uso de CPU atingir 70%.
    - Role HPA: Define as permissões necessárias para que o HPA acesse métricas do cluster (como CPU e memória) para tomar decisões de escalabilidade.
  - postgress: hospeda o banco de dados PostgreeSQL usado para o armazenamento de dados.
    - Stateful.
    - Volume: utilizado para o armazenamento dos dados.
    - Cluster IP:  Endereço IP interno para comunicação com outros pods no cluster.
    - Secret: contém as credenciais do banco de dados.
- ingress-basic: é responsável por gerenciar o tráfego externo e rotear as requisições para os serviços no namespace default.
  - ingress-nginx-controller: Executa o controlador NGINX Ingress, que atua como ponto de entrada para requisições externas e roteia o tráfego para os serviços apropriados no namespace default.
    - Ingress: Define as regras de roteamento para requisições externas (por exemplo, rotear requisições para o serviço do java-app).
    - Service: Expõe o controlador NGINX internamente no cluster.
    - Endpoint: Mapeia os endpoints para os serviços internos.
    - Deployment: Gerencia a implantação do controlador NGINX.
    - ConfigMap: Armazena configurações do NGINX, como limites de requisições, timeouts e outras opções de personalização.
    - Secret: Armazena informações sensíveis, como certificados TLS para habilitar HTTPS.    
*Os arquivos de configuração do Kubernetes (em formato .yml) estão organizados no diretório kubernetes/, que contém os recursos descritos no diagrama.

## Execução em ambiente local
### Pré-requisitos
- Ter o Docker instalado e em execução. Para mais informações:
  - Windows: https://docs.docker.com/engine/install/ubuntu/
  - Linux: https://docs.docker.com/desktop/setup/install/windows-install/

### Execução
1. Clone o projeto para sua máquina:
```
git clone https://github.com/AleCigolini/fiap-tech-challenge-02.git
```
2. Acesse o diretório do projeto clonado e execute:
```
docker-compose up -d
```

Será inicializado 3 contêineres:
- Aplicação Java
- Flyway (Inicializar estrutura de banco de dados)
- PostgreSQL

Segue URL do banco de dados abaixo. Credenciais para acesso estão no arquivo .env do projeto.
```
jdbc:postgresql://localhost:5432/techchallenge
```

### Visualização do Swagger
Com a aplicação rodando, via navegador de sua preferência, acesse a URL:
```
http://localhost:8080
```

## Passo a passo integração Mercado Pago

### Pedido

1 - Crie o pedido - POST
```
http://localhost:8080/pedidos
```
- Corpo da requisição:
```
{
  "cliente": {
    "cpf": "12022425022",
    "email": "teste.usuario@email.com"
  },
  "observacao": "Tocar interfone",
  "produtos": [
    {
      "quantidade": 2,
      "idProduto": "e389406d-5531-4acf-a354-be5cc46a8ca1",
      "observacao": "Mal passado"
    },
    {
      "quantidade": 3,
      "idProduto": "e389406d-5531-4acf-a354-be5cc46a8ca2",
      "observacao": "Gelado"
    },
    {
      "quantidade": 1,
      "idProduto": "e389406d-5531-4acf-a354-be5cc46a8ca3"
    }
  ]
} 
```

2 - Listagem dos pedidos por situação - GET
<br/>
2.1 - Ao filtrar por "ABERTO", será possível verificar o pedido anteriormente criado.

```
http://localhost:8080/pedidos?status=ABERTO
```

#### Pagamento

3 - Pagamentos relacionados ao pedido - GET.
<br/>
3.1 - Existirá um pagamento como pendente visto que o pedido ainda não foi pago.
```
http://localhost:8080/pagamentos/pedidos/{id_do_pedido_criado}
```
<br/>

### Mercado Pago

4 - Acessar o aplicativo do mercado pago com as credenciais de teste:
```
Usuário: TESTUSER2078021759
Senha: KzOb3GTTz0
```

### Pagamento

5 - Gere o Qr-Code para pagamento - GET
```
http://localhost:8080/pagamentos/caixa/qr-code
```

6 - Com o aplicativo do mercado pago aberto, escaneie o Qr-Code e realize o pagamento.


### Webhook fake

7 - Acessar o webhook abaixo e copiar a mensagem de retorno da chamada POST contida nele.
<br/>
7.1 - A chamada conterá a situação do pedido como "closed".

```
https://webhook.site/#!/view/812e902e-50fb-4f4d-8369-d08924575236
```

### Webhook Pedido

8 - Após copiar a mensagem de retorno do mercado pago na passo anterior, insira ela na requisição do webhook da aplicação - POST
```
http://localhost:8080/pedidos/webhook-mercado-pago
```
- O corpo da requisição será algo como:
```
{
    "action": "create",
    "application_id": "6626499642890434",
    "data": {
        "currency_id": "",
        "marketplace": "NONE",
        "status": "closed"
    },
    "date_created": "2025-03-17T16:28:28.296-04:00",
    "id": "29622218613",
    "live_mode": false,
    "status": "closed",
    "type": "topic_merchant_order_wh",
    "user_id": 2307740945,
    "version": 0
}
```


### Listar pagamentos e pedidos

#### Pagamento

8 - Pagamentos relacionados ao pedido - GET
<br/>
8.1 - Terá um pagamento aprovado para o pedido.
```
http://localhost:8080/pagamentos/pedidos/{id_do_pedido_criado}
```

#### Pedidos
9 - Listagem dos pedidos por situação - GET
<br/>
9.1 - Ao filtrar por "RECEBIDO", será possível verificar o pedido anteriormente criado.

```
http://localhost:8080/pedidos?status=RECEBIDO
```

### **Assim, terá simulado a integração com o mercado pago para geração e pagamento de um pedido.**

---
## Lista de endpoints

### Pedido

- Retornar todos os pedidos - GET
```
http://localhost:8080/pedidos
```

### Produto

- Retornar todos os produtos - GET
```
http://localhost:8080/produtos
```

- Criar produto - POST
```
http://localhost:8080/produtos
```
- Corpo da requisição:
```
{
  "nome": "Exemplo produto",
  "descricao": "Exemplo descrição",
  "idCategoria": "4ce30a87-5654-486b-bed6-88c6f83f491a",
  "preco": 10
}
```

- Buscar produto por ID - GET
```
http://localhost:8080/produtos/d98620ab-094e-4702-a066-42c8f39caaaa
```

- Atualizar produto - PUT
```
http://localhost:8080/produtos/d98620ab-094e-4702-a066-42c8f39caaaa
```
- Corpo da requisição:
```
{
  "nome": "Nome produto atualizado",
  "descricao": "Descrição atualizada",
  "idCategoria": "2ae01e62-6805-4095-9bc3-9b9081517b87",
  "preco": 50
}
```

- Excluir produto - DELETE
```
http://localhost:8080/produtos/d98620ab-094e-4702-a066-42c8f39caaaa
```

- Buscar produtos por categoria - GET
```
http://localhost:8080/produtos/categoria/d5b5a378-3862-4436-bdcc-29d8c8a2ee65
```
<br/>

### Cliente
- Criar cliente - POST
```
http://localhost:8080/clientes
```
Corpo da requisição. Cliente com e-mail e CPF:
```
{
  "nome": "Nome cliente",
  "email": "teste@gmail.com",
  "cpf": "13742443097"
}
```

Corpo da requisição. Cliente com e-mail:
```
{
  "nome": "Nome cliente",
  "email": "teste@gmail.com",
  "cpf": ""
}
```

Corpo da requisição. Cliente com CPF:
```
{
  "nome": "Nome cliente",
  "email": "",
  "cpf": "13742443097"
}
```
- Buscar cliente pelo CPF - GET
```
http://localhost:8080/clientes/cpf
```
Corpo da requisição:
```
{
  "cpf": "12022425022"
}
```

- Buscar cliente pelo E-mail - GET
```
http://localhost:8080/clientes/email
```
Corpo da requisição:
```
{
  "email": "teste.usuario@email.com"
}
```

- Buscar cliente pelo ID - GET
```
http://localhost:8080/clientes/id
```
Corpo da requisição:
```
{
  "id": "e389406d-5531-4acf-a354-be5cc46a8cd4"
}
```


<br/>

### Categorias de Produto

- Buscar categorias de produto - GET
```
http://localhost:8080/categorias-produto
```

- Criar categoria de produto - POST
```
http://localhost:8080/categorias-produto
```
```
{
  "nome": "Nome do Acompanhamento"
}
```

- Buscar categoria de produto por ID - GET
```
http://localhost:8080/categorias-produto/e397f412-9c76-4fb5-b029-7c3a99b7e982
```

- Atualizar categoria de produto por ID - PUT
```
http://localhost:8080/categorias-produto/e397f412-9c76-4fb5-b029-7c3a99b7e982
```
```
{
  "nome": "Nome do Acompanhamento atualizado"
}
```

- Excluir categoria de produto por ID - DELETE
```
http://localhost:8080/categorias-produto/e397f412-9c76-4fb5-b029-7c3a99b7e982
```
