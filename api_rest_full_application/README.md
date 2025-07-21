# Sistema de Gerenciamento de Biblioteca (API RESTful)

Este é um sistema de gerenciamento de biblioteca desenvolvido com **Spring Boot**, que fornece uma **API RESTful** para operações **CRUD** em entidades como **Usuário**, **Livro**, **Autor**, **Editora** e **Empréstimo**. Ele utiliza **MongoDB** com **Spring Data (ODM)** para persistência, e suporta testes automatizados com **JUnit**. Futuramente, será integrado ao **Redis com Pub/Sub** para eventos em tempo real.

---

## Tecnologias Utilizadas

- **Spring Boot** – Framework principal da aplicação
- **MongoDB** – Banco de dados NoSQL
- **Spring Data MongoDB** – ODM
- **JUnit 5 + Mockito** – Testes automatizados
- **Redis + Lettuce (Etapa 2)** – Comunicação assíncrona


---

## Estrutura do Projeto

```plaintext
src/
├── main/
│   ├── java/
│   │   └── br.ufg.inf.grupo9.biblioteca/
│   │       ├── controller/        # Controladores REST
│   │       ├── model/             # Entidades: Livro, Usuario, etc.
│   │       ├── repository/        # Interfaces MongoRepository
│   │       ├── service/           # Regras de negócio
│   │       ├── config/            # Configurações Mongo, Redis
│   │       ├── pubsub/            # (Etapa 2) - Integração Redis
│   │       └── BibliotecaApplication.java
│   └── resources/
│       ├── application.properties
│       └── ...
└── test/
    └── java/
        └── br.ufg.inf.grupo9.biblioteca/
            ├── service/
            ├── repository/
            └── controller/
```

## Como Executar

1. Pré-requisitos
- Java 21 Temurin 
- Docker e Docker Compose
- MongoDB configurado via Docker

2. Clone o Repositório
```bash
   git clone https://github.com/SPD-BES-2025-3/grupo9.git
   cd api_rest_full_application
```

3. Configure as Variáveis de Ambiente: No application.properties, use as variáveis.

4. Subir o MongoDB com Docker
```bash
   docker-compose up -d
```

5. Testar a API
A aplicação rodará por padrão em:
 http://localhost:8081

---

## Instalação do MongoDB via Docker

Vamos usar Docker para evitar instalação manual no sistema operacional.

> **Docker** é uma plataforma para rodar aplicativos em containers, que são ambientes isolados e reproduzíveis.

---

### Passos

1. **Baixar a imagem oficial do MongoDB**

   Este comando baixa a última versão da Community Edition do MongoDB:

   ```bash
   docker pull mongodb/mongodb-community-server:latest
   ```

   🔍 **O que faz esse comando?**
    - Faz download da imagem do MongoDB que contém tudo pronto para execução.
    - Você só precisa rodar isso uma única vez.

2. **Criar e iniciar o container**

   O comando abaixo cria o container, define nome, portas e volume persistente:

   ```bash
   docker run -d \
      --name mongodb \
      -p 27017:27017 \
      -v mongodb_data:/data/db \
      mongodb/mongodb-community-server:latest
   ```

   🔍 **Explicando cada parâmetro:**
    - `-d`: roda o container em background.
    - `--name mongodb`: dá um nome amigável ao container.
    - `-p 27017:27017`: mapeia a porta padrão do MongoDB para o host.
    - `-v mongodb_data:/data/db`: cria um volume chamado `mongodb_data` que guarda os dados.
    - A última parte indica a imagem a ser usada.

3. **Verificar se o container está ativo**

   Use o comando:

   ```bash
   docker ps
   ```

   Você deve ver algo parecido com:

   ```
   CONTAINER ID   IMAGE                                        PORTS
   abcd1234efgh   mongodb/mongodb-community-server:latest      0.0.0.0:27017->27017/tcp
   ```

4. **Acessar o shell do MongoDB (opcional)**

   Para entrar no prompt interativo do MongoDB, execute:

   ```bash
   docker exec -it mongodb mongosh
   ```

   Exemplo de comandos que você pode usar dentro do shell:

   ```mongodb
   show dbs
   ```

   ```mongodb
   use test
   ```

   ```mongodb
   db.createCollection("exemplo")
   ```


5. **Acessando o MongoDB através do VS Code (opcional)**

Se preferir, você pode gerenciar seu banco MongoDB usando a extensão **MongoDB for VS Code**.

#### Passos

1. Abra o VS Code.
2. Vá no menu lateral esquerdo, clique no ícone de **Extensions** (ou use o atalho `Ctrl + Shift + X`).
3. Pesquise por **MongoDB for VS Code** e instale a extensão oficial da MongoDB Inc.
4. Após instalar, clique no ícone do **MongoDB** no menu lateral esquerdo.
5. Clique em **Connect**.
6. Na tela de conexão, informe a string de conexão:

   ```
   mongodb://localhost:27017
   ```

   > Como estamos rodando localmente no Docker, não é necessário usuário nem senha por padrão.
   
7. Clique em **Connect**.

Após conectar, você poderá:

- Visualizar bancos e coleções.
- Criar documentos.
- Executar queries com sintaxe MongoDB.
- Editar dados interativamente.

![](mongodb-vscode.png)

Essa é uma opção prática para explorar seus dados sem sair do VS Code.

