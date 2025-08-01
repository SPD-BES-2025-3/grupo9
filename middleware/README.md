# Middleware de Sincronização de Dados de Biblioteca

Este é um serviço de **middleware** desenvolvido com **Spring Boot**, responsável por garantir a sincronização de dados em tempo real entre dois sistemas de gerenciamento de biblioteca: uma aplicação desktop com banco de dados **SQLite (ORM)** e uma API RESTful com banco de dados **MongoDB (ODM)**.

Utilizando um padrão **Pub/Sub com Redis**, o middleware escuta eventos de criação, atualização e exclusão (CRUD) de ambos os sistemas. Ao receber um evento, ele transforma os dados do modelo de origem (ORM ou ODM) para o modelo de destino e os persiste no banco de dados correspondente, mantendo a consistência entre as duas aplicações.

---

## Tecnologias Utilizadas

- **Spring Boot** – Framework principal da aplicação de background.
- **Redis (com Spring Data Redis)** – Utilizado como message broker para a comunicação assíncrona via Pub/Sub.
- **Spring Data JPA & Hibernate** – Para conexão e persistência de dados no banco **SQLite (ORM)**.
- **Spring Data MongoDB** – Para conexão e persistência de dados no banco **MongoDB (ODM)**.
- **Gson** – Biblioteca para serialização e desserialização das mensagens JSON trocadas via Redis.
- **Lombok** – Para reduzir código boilerplate nos modelos.

---

## Estrutura do Projeto

O projeto é organizado para separar claramente as responsabilidades de escuta, transformação e sincronização.

```plaintext
src/
└── main/
    └── java/
        └── br.com.grupo9.middleware/
            ├── config/          # Configuração do listener do Redis
            ├── listener/        # Classe que escuta os canais do Redis (RedisListener)
            ├── model/
            │   ├── odm/         # Entidades do MongoDB (Ex: Livro_ODM)
            │   └── orm/         # Entidades do SQLite (Ex: Livro_ORM)
            ├── repository/
            │   ├── odm/         # Interfaces MongoRepository
            │   └── orm/         # Interfaces JpaRepository
            ├── service/         # Lógica de negócio da sincronização (SyncService)
            ├── transformer/     # Classes que convertem modelos (Ex: LivroTransformer)
            └── MiddlewareApplication.java
```

## Arquitetura de Sincronização

O middleware é o coração da comunicação entre os dois sistemas, conforme o diagrama abaixo:

![](https://github.com/SPD-BES-2025-3/grupo9/blob/main/diagramas/diagrama-redis.png)

---

## Como Executar

O middleware depende das outras duas aplicações (`desktop_application` e `api_rest_full_application`) e de suas respectivas bases de dados. É crucial seguir a ordem de inicialização.

### 1. Pré-requisitos
- **Java Development Kit (JDK) 17 ou superior**.
- **IDE** com suporte a Maven (IntelliJ IDEA ou Eclipse são recomendados).
- **Redis** rodando localmente (ou via Docker).
- **MongoDB** rodando localmente (ou via Docker).
- O arquivo de banco de dados **`livraria.sqlite`** precisa existir (ele é criado pela `desktop_application`).

### 2. Instalação do Redis (via Docker)

Se você não tem o Redis instalado, a forma mais fácil é usando Docker:

```bash
docker pull redis:latest
docker run -d --name redis -p 6379:6379 redis
```

### 3. Clone o Repositório
Se ainda não o fez, clone o repositório completo:
```bash
git clone https://github.com/SPD-BES-2025-3/grupo9.git
```

### 4. Configure o Caminho do Banco de Dados

1.  Abra o projeto `middleware` na sua IDE.
2.  Navegue até o arquivo `src/main/resources/application.properties`.
3.  Localize a linha `spring.datasource.url` e **certifique-se de que o caminho para o arquivo `livraria.sqlite` está correto para o seu sistema operacional.**

    Exemplo para Windows:
    ```properties
    spring.datasource.url=jdbc:sqlite:C:/Users/SeuUsuario/caminho/para/grupo9/desktop_application/livraria.sqlite
    ```
    Exemplo para Linux/macOS:
    ```properties
    spring.datasource.url=jdbc:sqlite:/home/seu-usuario/caminho/para/grupo9/desktop_application/livraria.sqlite
    ```

### 5. Ordem de Execução

Para que a sincronização funcione corretamente, as aplicações devem ser iniciadas na seguinte ordem:

1.  **Execute a `desktop_application` primeiro.**
    - Isso garante que o banco de dados `livraria.sqlite` seja criado com o esquema correto.
2.  **Execute a `api_rest_full_application` (API RESTful).**
    - Ela se conectará ao MongoDB.
3.  **Por último, execute a `middleware` application.**
    - Na sua IDE, abra o projeto `middleware` e execute a classe `MiddlewareApplication.java`.
    - O console deverá mostrar que a aplicação iniciou e está rodando (com um servidor Tomcat embutido), pronta para escutar as mensagens do Redis.

Após a inicialização, qualquer operação de CRUD realizada na aplicação desktop ou na API será capturada pelo middleware e sincronizada com o outro sistema. Você pode acompanhar as operações através dos logs no console do middleware.
