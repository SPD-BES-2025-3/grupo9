# 📚 Projeto Biblioteca - Relatório Individual (Etapa 2)

- [Stephany Milhomem](https://github.com/StephanyMil)
- Relatório individual da segunda etapa deste projeto.

---

## Evolução da Arquitetura e Novas Funcionalidades

### branch: 17-add-middleware (e main)

A segunda etapa do projeto focou em robustecer a arquitetura de persistência e implementar o mecanismo de sincronização de dados em tempo real entre a aplicação desktop e a API RESTful. As principais entregas foram a migração da camada de persistência para JPA/Hibernate e a construção do sistema de comunicação assíncrona com Redis e um middleware dedicado.

---

### 1. Migração de ORMLite para JPA/Hibernate

- **Commit Principal:** [0e1d4a7](https://github.com/SPD-BES-2025-3/grupo9/commit/0e1d4a718aceaddd33365a98a70b2684e9b369eb)

A camada de persistência da **`desktop_application`** foi completamente refatorada para adotar o padrão **JPA (Jakarta Persistence API)** com **Hibernate**, substituindo o ORMLite. Esta mudança trouxe maior padronização, portabilidade e acesso a recursos avançados de ORM.

- **Principais Alterações:**
  - **Dependências:** O `pom.xml` foi atualizado para incluir as dependências do `jakarta.persistence-api` e `hibernate-core`.
  - **Configuração:** A configuração da conexão com o SQLite foi movida para o arquivo `META-INF/persistence.xml`, definindo a unidade de persistência.
  - **Modelos de Entidade:** As anotações do ORMLite (`@DatabaseTable`, `@DatabaseField`) foram substituídas pelas anotações padrão do JPA (`@Entity`, `@Table`, `@Id`, `@Column`, `@ManyToOne`, etc.).
  - **Repositórios:** As classes de repositório foram reescritas para utilizar o `EntityManager` do JPA, padronizando as operações de CRUD (Criar, Ler, Atualizar, Deletar) em uma classe base `Repository<T, ID>`.

---

### 2. Implementação da Sincronização Assíncrona com Redis

Para garantir que os dados permaneçam consistentes entre a aplicação desktop e a API, foi implementada uma arquitetura de **Pub/Sub (Publish/Subscribe)** utilizando Redis como _message broker_.

#### **2.1. Publicação de Eventos (Publisher) na Aplicação Desktop**
- **Commit:** [e9b48c2](https://github.com/SPD-BES-2025-3/grupo9/commit/e9b48c2d4305ef97bc5c8dcbcd3df2bdafd1174d)

A `desktop_application` agora atua como um publicador de eventos. Após cada operação de CRUD bem-sucedida (create, update, delete), a aplicação formata uma mensagem JSON e a envia para um canal Redis.

- **Implementações:**
  - **`RedisUtil.java`:** Classe responsável por gerenciar a conexão com o servidor Redis usando a biblioteca **Lettuce**.
  - **`RedisPublisher.java`:** Componente que constrói o "envelope" da mensagem (contendo a fonte `ORM_Desktop`, a operação e os dados da entidade em `payload`) e a publica no canal `crud-channel`.
  - **Integração nos Repositórios:** A classe base `Repository` foi modificada para invocar o `RedisPublisher` após cada transação ser comitada no banco de dados.

#### **2.2. Publicação de Eventos (Publisher) na API RESTful**
- **Commit:** [0dbad9f](https://github.com/SPD-BES-2025-3/grupo9/commit/0dbad9ff9bb242214e317db6b4e392ec8582c19e)

De forma análoga à aplicação desktop, a `api_rest_full_application` também foi equipada para publicar eventos de CRUD no mesmo canal Redis.

- **Implementações:**
  - **`RedisPublisher.java`:** Um `Service` do Spring que utiliza o `RedisTemplate` para enviar mensagens JSON. O formato do "envelope" é padronizado, identificando a fonte como `ODM_API`.
  - **Integração nos Services:** As classes de serviço (ex: `AutorService`, `LivroService`) foram atualizadas para chamar o `RedisPublisher` após salvar ou deletar uma entidade no MongoDB.

#### **2.3. Implementação do Middleware de Sincronização**
- **Commits:** [d19054e](https://github.com/SPD-BES-2025-3/grupo9/commit/d19054e790e3439a3065d359546a6161073dd8e6) e [87eaa63](https://github.com/SPD-BES-2025-3/grupo9/commit/87eaa635aa08a009c073e0fe90baeef28ebb4ec1)

Um novo projeto Spring Boot, o **`middleware`**, foi criado para atuar como o orquestrador da sincronização. Ele escuta as mensagens publicadas no Redis, transforma os dados e os persiste no banco de dados de destino.

- **Componentes Chave:**
  - **`RedisListener.java`:** Atua como _subscriber_ do canal `crud-channel`. Ao receber uma mensagem, ele a desserializa e identifica a sua origem (`ORM_Desktop` ou `ODM_API`).
  - **`SyncService.java`:** Contém a lógica de negócio principal. Com base na origem da mensagem, ele coordena a transformação e a persistência dos dados.
  - **`Transformer Classes`:** Um conjunto de classes (ex: `LivroTransformer`, `EmprestimoTransformer`) responsáveis por converter os modelos de dados entre ORM (SQLite) e ODM (MongoDB) e vice-versa.
  - **Repositórios Duplos:** O middleware possui acesso a ambos os conjuntos de repositórios (JPA para SQLite e Spring Data para MongoDB) para poder ler e escrever em ambos os bancos de dados.

---

## Desafios e Aprendizados da Etapa

- **Incompatibilidade de Nomenclatura:** Um dos maiores desafios foi lidar com a diferença entre as estratégias de nomenclatura de colunas do Hibernate padrão (usado no desktop) e do Spring Boot JPA (que converte para `snake_case`). A solução foi padronizar a estratégia no middleware para garantir que ele procurasse os nomes de coluna corretos (`camelCase`).
- **Formato de Datas:** A serialização de datas entre os sistemas se mostrou um ponto crítico. O `Gson` na aplicação desktop formatava as datas de uma maneira (localizada), enquanto o `Gson` no middleware esperava um formato padrão. A padronização para o formato **ISO 8601** (`yyyy-MM-dd'T'HH:mm:ss.SSS'Z'`) em ambos os lados resolveu o problema de desserialização.
- **Gerenciamento de Dependências:** Garantir que as entidades relacionadas (ex: um `Livro` depende de `Autor` e `Editora`) já existissem no banco de dados de destino antes de sincronizar a entidade principal foi um ponto de atenção, resolvido com a lógica de busca e verificação nos `Transformers`.

Esta etapa foi fundamental para transformar o projeto em um sistema verdadeiramente distribuído e reativo, solidificando conceitos de ORM, comunicação assíncrona e arquitetura de microsserviços.