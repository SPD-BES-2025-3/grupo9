**Disciplina:** Software para persistência de Dados

**Alunas:**

  - [Stephany Milhomem](https://github.com/StephanyMil) - [Relatório Individual](https://github.com/SPD-BES-2025-3/grupo9/tree/main/relatorios/stephany)
  - [Thayliny Moura](https://github.com/thaylinymoura) - [Relatório Individual](https://github.com/SPD-BES-2025-3/grupo9/tree/main/relatorios/thayliny)
  - [Vitoria Luz](https://github.com/Vitorialuz229) - [Relatório Individual](https://github.com/SPD-BES-2025-3/grupo9/tree/main/relatorios/vitoria)

# Sistema de Gerenciamento de Biblioteca

O sistema de Gerenciamento de Biblioteca visa simplificar o gerenciamento de uma biblioteca, oferecendo uma interface intuitiva para a administração de seu acervo e usuários. A motivação principal é fornecer uma solução robusta e flexível para as operações diárias de uma biblioteca, garantindo a persistência e a integridade dos dados.

## Objetivo Geral

O objetivo é desenvolver um sistema de gerenciamento de biblioteca distribuído, composto por uma aplicação desktop (JavaFX, persistência em SQLite, serialização JSON/XML) e uma API RESTful (Spring Boot, persistência em MongoDB), com funcionalidades CRUD completas. Ambos os componentes se integrarão por meio de um barramento de mensagens assíncrono utilizando Redis (Pub/Sub), garantindo a consistência e a reatividade dos dados entre as diferentes interfaces.

## Objetivos Específicos

  - **Implementar operações CRUD abrangentes** para as entidades Livro, Autor, Editora, Usuário e Empréstimo, tanto na aplicação desktop quanto na API RESTful.

- **Assegurar a persistência de dados** utilizando SQLite (via ORMLite) para a aplicação desktop e MongoDB (via Spring Data MongoDB) para a API RESTful.

- **Habilitar a comunicação e sincronização de dados** entre a aplicação desktop e a API RESTful por meio de um barramento de mensagens assíncrono utilizando Redis (Pub/Sub), com a biblioteca Lettuce.

- **Permitir a importação e exportação de dados** (especificamente livros) nos formatos JSON (via Gson) e XML (via JAXB) na aplicação desktop.

- **Prover uma interface gráfica** amigável e interativa utilizando JavaFX para a aplicação desktop.

- **Garantir a qualidade e robustez do sistema** através da implementação de **testes unitários** abrangentes para as camadas de lógica de negócio e controladores, utilizando JUnit (e Mockito para a API).

- **Desenvolver e documentar uma API RESTful robusta** com Spring Boot para expor os recursos do sistema, permitindo a integração com outras plataformas e serviços.

## Tecnologias e Ferramentas Utilizadas

### Apllication Descktop

  - **JavaFX:** Framework para construção da interface gráfica da aplicação desktop.
  - **ORMLite:** Biblioteca ORM para mapeamento de objetos Java para tabelas de banco de dados e vice-versa.
  - **SQLite:** Banco de dados relacional leve e embutido (livraria.sqlite) para persistência dos dados.
  - **Gson:** Biblioteca Java para serialização e desserialização de objetos Java em JSON.
  - **JAXB (Java Architecture for XML Binding):** API para mapeamento de objetos Java para XML e vice-versa.
  - **JDK 11 ou superior:** Ambiente de desenvolvimento Java necessário.
  - **Testes unitários com JUnit.**
  - **Integrador de Entidades:** uso de Redis, Lettuce e Pub/Sub.

### API RESTful

- **Spring Boot:**  Framework principal da aplicação.
- **MongoDB:** Banco de dados NoSQL.
- **Spring Data MongoDB:** ODM (Object-Document Mapping) para interagir com o MongoDB.
- **JUnit 5 + Mockito:** Para testes automatizados.
- **Redis + Lettuce:** Para comunicação assíncrona.

## Divisão de Tarefas JavaFX

| Atividade                                                                                                                                               | Data Início | Data Fim | Responsável | Situação  |
| ------------------------------------------------------------------------------------------------------------------------------------------------------- | ----------- | -------- | ----------- | --------- |
| Documentar o Projeto                                                                                                                                    | 18/07       | 21/07    | Thayliny    | Concluida |
| Produzir o código e a documentação inicial no repositório                                                                                               | 18/07       | 20/07    | Stepahny    | Concluida |    
| Adicionar, atualizar e deletar livros                                                                                                                   | 18/07       | 20/07    | Stepahny    | Concluida |            
| Visualizar a lista de livros em uma tabela interativa                                                                                                   | 18/07       | 20/07    | Stepahny    | Concluida |         
| Associar livros a autores existentes usando um ComboBox                                                                                                 | 18/07       | 20/07    | Stepahny    | Concluida |          
| Adicionar, atualizar e deletar autores                                                                                                                  | 18/07       | 20/07    | Stepahny    | Concluida |   
| Visualizar a lista de autores em uma tabela                                                                                                             | 18/07       | 20/07    | Stepahny    | Concluida |     
| Validação para impedir a exclusão de autores vinculados a livros                                                                                        | 18/07       | 20/07    | Stepahny    | Concluida |    
| Implemtação das  classes usuario, emprestimo, editora                                                                                                  | 18/07       | 20/07    | Stepahny    | Concluida | 
| Atualização da aplicação desktop                                                                                                                        | 18/07       | 20/07    | Stepahny    | Concluida | 
| Persistência de Dados: Todos os dados são armazenados em um arquivo SQLite (livraria.sqlite), garantindo que as informações persistam entre as sessões. | 18/07       | 20/07    | Stepahny    | Concluida |        
| Atualização do Projeto usando JPA                                                                                                                       | 21/07       | 23/07    | Stepahny    | Concluida |
| Contrução de Teste Unitário                                                                                                                            | 23/07       | 25/07    | Thayliny    | Concluída |     
| Teste Unitario JUnit                                                                                                                                    | 23/07       | 25/07    | Thayliny    | Concluída | 
    

## Divisão de Tarefas API Rest


| Atividade | Data Início | Data Fim | Responsável | Situação |
|---|---|---|---|---|
| Documentar o Projeto (relatório inicial e estrutura) | 18/07 | 21/07 | Thayliny | Concluída |
| Estruturação inicial do projeto com Spring Boot | 21/07 | 25/07 | Vitoria | Concluída |
| Adição de dependências e extensões essenciais (MongoDB, Lombok, Spring Web, Spring Data MongoDB, etc) | 21/07 | 25/07 | Vitoria | Concluída |
| Criação da estrutura de diretórios (controller, service, repository, model, dto) | 21/07 | 25/07 | Vitoria | Concluída |
| Configuração do ambiente Docker (docker-compose.yml para MongoDB) | 21/07 | 25/07 | Vitoria | Concluída |
| Criação do arquivo application.properties com configurações do MongoDB | 21/07 | 25/07 | Vitoria | Concluída |
| Início da implementação do CRUD de entidades principais (Livro, Autor) | 21/07 | 25/07 | Vitoria | Concluída |
| Criação de repositórios com Spring Data MongoDB | 21/07 | 25/07 | Vitoria | Concluída |
| Aplicação do padrão Builder com Lombok em DTOs | 21/07 | 25/07 | Vitoria | Concluído |
| Criação de modelos iniciais com base no diagrama do projeto | 21/07 | 25/07 | Vitoria | Concluído |
| Finalizar os métodos CRUD no controller e service | 22/07 | 26/07 | Vitoria | Concluído |
| Criar os testes unitários para os repositórios | 22/07 | 28/07 | Thayliny | Concluído |
| Adicionar validações nas entidades e DTOs | 22/07 | 28/07 | Vitoria | Concluído |
| Iniciar a documentação da API com Swagger/OpenAPI | 22/07 | 29/07 | Thayliny |Concluída

## Divisão de Tarefas  Redis

 Atividade | Data Início | Data Fim | Responsável | Situação |
|---|---|---|---|---|
|Configurar Redis no Projeto JavaFX| 29/07| 30/07 | Stephany|Concluída
|Implementar Pub/Sub no Projeto JavaFX para eventos de CRUD| 29/07| 30/07 | Stephany|Concluída
|Configurar Redis no Projeto API RESTful| 29/07| 30/07 | Vitoria|Concluída
|Implementar Pub/Sub no Projeto API RESTful para eventos de CRUD| 29/07| 30/07 | Stephany e Vitoria|Concluída
|Testes de Integração com Redis| 29/07| 29/07 | Stephany|Concluída

## Modelagem Inicial

### Diagrama de Classes com Relações

![](https://github.com/SPD-BES-2025-3/grupo9/blob/thayliny/diagramas/diagrama-classe1.png)

### Arquitetura do Sistema (MVC)

![](https://github.com/SPD-BES-2025-3/grupo9/blob/thayliny/diagramas/diagrama-componentes.png)

### Diagrama de Sequência para Operações de Integração

![](https://github.com/SPD-BES-2025-3/grupo9/blob/thayliny/diagramas/diagrama-sequencia.png)

## Documentação de Código Inicial

### Como Instalar e Executar o Código Inicial

1.  **BlueJ:** Inclui passos para abrir o projeto, adicionar as bibliotecas JAR (SQLite JDBC, ORMLite Core, ORMLite JDBC, Gson, e dependências JAXB para JDK 11+) e configurar as VM Options do JavaFX.
2.  **Linha de Comando:** Fornece comandos `javac` para compilação e `java` para execução, com as configurações `--module-path` e `--add-modules` para JavaFX, e `--cp` para o classpath das bibliotecas.
3.  **Outras IDEs (IntelliJ IDEA, Eclipse):** Orientações gerais para importar o projeto, configurar o JDK, adicionar o JavaFX SDK como biblioteca modular e incluir os demais JARs como bibliotecas de projeto, além de definir as VM Options.

### Passo a passo de como executar os Projetos

  - [Sistema de Gerenciamento de Biblioteca API RestFull](https://github.com/SPD-BES-2025-3/grupo9/blob/main/api_rest_full_application/README.md)
  - [Sistema de Gerenciamento de Biblioteca JavaFX](https://github.com/SPD-BES-2025-3/grupo9/blob/main/desktop_application/README.md)

### Documentação de Código

O projeto utiliza:

  - **Markdown:** O arquivo `README.md` serve como a documentação principal do projeto, descrevendo suas funcionalidades, tecnologias e instruções de execução.
  - **JavaDoc:** Algumas classes, como `Database.java` e `AppView.java`, contêm comentários Javadoc para descrever a finalidade da classe, autor e versão.


##  Documentação da API RESTful

O sistema de gerenciamento de biblioteca também oferece uma API RESTful desenvolvida com Spring Boot, utilizando MongoDB para persistência. Abaixo estão os detalhes sobre a sua estrutura e como executá-la.

## Acessando a Documentação da API via Swagger

Para acessar a documentação interativa da sua API, siga estes passos:

1. **Execute a aplicação**  
   Inicie sua API Spring Boot normalmente, por exemplo:  
   ```bash
   mvn spring-boot:run
   ```
ou execute a classe principal diretamente na sua IDE.

**Abra o navegador** 

Após a aplicação estar rodando em:

 ```
http://localhost:8080/v3/api-docs
http://localhost:8080/swagger-ui/index.html#/
 ```
Explore os endpoints

Na interface do Swagger UI você poderá:
**Ver todos os recursos e métodos (GET, POST, PUT, DELETE, etc.).**
  

### Como Executar (API RESTful)

Pré-requisitos

- **Java 21**
- **Docker e Docker Compose**
- **MongoDB configurado via Docker**


## Passos para Execução

### Clonar o Repositório:


```
git clone https://github.com/SPD-BES-2025-3/grupo9.git
cd api_rest_full_application
```
Configurar as Variáveis de Ambiente: No application.properties, configure as variáveis.

Exemplo de application.properties:

```Properties
spring.application.name=api_biblioteca
server.port=${SERVER_PORT:8081}
```

``` 
spring.data.mongodb.host=${MONGODB_HOST:localhost}
spring.data.mongodb.port=${MONGODB_PORT:27017}
spring.data.mongodb.database=${MONGODB_DATABASE:biblioteca}
``` 
### Subir o MongoDB com Docker:

Utilize o docker-compose.yml para iniciar o contêiner do MongoDB:

```
YAML

version: "3.8"

services:
  mongodb:
    image: mongo:6.0
    container_name: mongodb
    ports:
      - "27017:27017"
    volumes:
      - mongodb_data:/data/db

  app:
    build: .
    container_name: api_biblioteca
    ports:
      - "8081:8081"
    environment:
      - SPRING_DATA_MONGODB_HOST=mongodb
      - SPRING_DATA_MONGODB_PORT=27017
      - SPRING_DATA_MONGODB_DATABASE=biblioteca
    depends_on:
      - mongodb

volumes:
  mongodb_data:

 ```

Execute o comando na raiz do diretório **api_rest_full_application:**

``` 
docker-compose up -d
Testar a API:
A aplicação rodará por padrão em:
http://localhost:8081
```

**Instalação do MongoDB via Docker**


### Baixar a imagem oficial do MongoDB:


```
docker pull mongodb/mongodb-community-server:latest
Este comando faz o download da última versão da Community Edition do MongoDB, incluindo tudo o que é necessário para a execução.
```

### Criar e iniciar o container:


```
docker run -d \
   --name mongodb \
   -p 27017:27017 \
   -v mongodb_data:/data/db \
   mongodb/mongodb-community-server:latest
Este comando cria o container, define um nome amigável (mongodb), mapeia a porta padrão do MongoDB (27017) para a porta do host, e cria um volume chamado mongodb_data para persistir os dados.

```

### Verificar se o container está ativo:
```
docker ps
```
### A saída deve ser similar a:

```
CONTAINER ID   IMAGE                                        PORTS
abcd1234efgh   mongodb/mongodb-community-server:latest      0.0.0.0:27017->27017/tcp
Acessar o shell do MongoDB (opcional):
```

docker exec -it mongodb mongosh

### Exemplos de comandos dentro do shell:


```
show dbs
use test
db.createCollection("exemplo")
```

### Acessando o MongoDB através do VS Code (opcional):


```
Para gerenciar o MongoDB via VS Code, instale a extensão "MongoDB for VS Code" (Ctrl + Shift + X, procure por "MongoDB for VS Code"). Após a instalação, clique no ícone do MongoDB no menu lateral esquerdo, clique em Connect e informe a string de conexão mongodb://localhost:27017.
```

## Testes Unitários

O projeto inclui testes unitários para garantir a funcionalidade e a integridade das operações. Os testes são implementados utilizando JUnit 5 e Mockito.

### Desktop Application (JavaFX) - Exemplos de Testes

>AppControllerTest.java: Contém testes para a lógica da interface do usuário e interações. 
>

> deveDefinirSenhaQuandoInformada(): Testa se a senha é definida corretamente quando informada para um usuário existente.
>

> deveLancarExcecaoQuandoNovaSenhaNaoInformadaParaNovoUsuario(): Verifica se uma exceção é lançada quando a senha não é informada para um novo usuário.
>

> deveDefinirDataDevolucaoParaDuasSemanasAFrente(): Testa se a data de devolução é corretamente definida para duas semanas à frente.
>


>deveMostrarAlertaAoTentarExcluirLivroVinculado(): Simula a exibição de um alerta ao tentar excluir um livro vinculado a um empréstimo.
>

### API RESTful - Exemplos de Testes

> AutorServiceTest.java: Testa a camada de serviço para a entidade Autor.

> getAllAutor_deveRetornarListaDTO(): Verifica se o método retorna uma lista de DTOs de autores.

> getAutorById_quandoEncontrado_deveRetornarDTO(): Testa se o método retorna o DTO correto quando um autor é encontrado pelo ID.

>getAutorById_quandoNaoEncontrado_deveLancar404(): Verifica se uma ResponseStatusException com status 404 é lançada quando o autor não é encontrado.

>create_deveSalvarEConverter(): Testa a criação de um autor, verificando se ele é salvo e convertido corretamente para DTO.

>update_quandoExistir_deveAtualizarEConverter(): Verifica se a atualização de um autor existente funciona conforme o esperado.




