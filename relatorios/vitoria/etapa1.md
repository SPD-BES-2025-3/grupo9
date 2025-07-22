# 📚 Projeto Biblioteca - Relatório Inicial

Este repositório tem como objetivo o documentar as principais atividades realizadas nas primeira etapa desse projeto.

---

## 🚀 Features Iniciais

### 🔧 feature/estrutura

- Estruturação inicial do projeto com **Spring Boot**.
- Adição das dependências e extensões essenciais (MongoDB, Lombok, Spring Web, Spring Data MongoDB, etc).
- Criação da estrutura de diretórios conforme boas práticas (controller, service, repository, model, dto).
- Configuração do ambiente Docker:
  - Criado `docker-compose.yml` para subir o banco **MongoDB**.
  - Banco disponível na porta padrão (27017).
- Criação do arquivo `application.properties` com as configurações do MongoDB.

### 📦 feature/biblioteca-crud

- Início da implementação do CRUD de entidades principais (`Livro`, `Autor`, etc).
- Criação de repositórios com **Spring Data MongoDB**.
- Aplicação do **padrão Builder** com uso do `@Builder` da biblioteca **Lombok** em DTOs.
- Modelos iniciais criados com base no diagrama do projeto.

---
## Próximos Passos
- Finalizar os métodos CRUD no controller e service.
- Criar os testes unitários para os repositórios.
- Adicionar validações nas entidades e DTOs.
- Iniciar a documentação da API com Swagger/OpenAPI.
