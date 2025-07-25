# 📚 Projeto Biblioteca - Relatório Individual

- [Stephany Milhomem](https://github.com/StephanyMil)
- Relatório individual da primeira etapa deste projeto.

---

## Features Iniciais

### branch: stephany

- **Commit:** [74f2a5f](https://github.com/SPD-BES-2025-3/grupo9/commit/74f2a5f3dcae4dedf2af47360e918ec54c0801c5)
- Estruturação inicial do projeto com **JavaFX** seguindo o padrão **MVC (Model-View-Controller)**.
- Implementação do CRUD (Criar, Ler, Atualizar, Deletar) para as entidades `Livro` e `Autor`.
- Configuração da persistência de dados com **ORMLite** e banco de dados **SQLite**.
- Criação das interfaces gráficas iniciais para listagem e cadastro de livros e autores.

- **Commits Relevantes:**

  - **Usuário:** [a90eccd](https://github.com/SPD-BES-2025-3/grupo9/commit/a90eccd2bee6902a4dba38a713a4e07171bb118d) - Adição da entidade `Usuario` com seu respectivo CRUD e interface.
  - **Editora:** [ad2f781](https://github.com/SPD-BES-2025-3/grupo9/commit/ad2f7817cf91b32a9a0f09e29e4ccf98d735cb7f) - Implementação da gestão completa de `Editoras`.
  - **Empréstimo:** [f916379](https://github.com/SPD-BES-2025-3/grupo9/commit/f91637931142c04a49f0ab1444e8ca8d7d9667da) - Desenvolvimento do módulo de `Empréstimos`, relacionando livros e usuários.

- **Commits Relevantes:**
  - **Atualização de Model e View:** [f61e4ba](https://github.com/SPD-BES-2025-3/grupo9/commit/f61e4ba271f2859750bdae249a6d1a4124591579) - Ajuste no `Model` e na `View` de Livro para comportar os relacionamentos com as novas entidades.
  - **Atualização do Controller:** [d8eb278](https://github.com/SPD-BES-2025-3/grupo9/commit/d8eb278395c4feeb54e0f6927eefde18070d4f37) - Refatoração do `AppController` para centralizar e gerenciar a lógica de todas as entidades.
  - **Interface Unificada:** [a154acf](https://github.com/SPD-BES-2025-3/grupo9/commit/a154acf1c622c0d016194aff8168793d571d11a2) - Atualização do arquivo `app.fxml` para integrar todas as funcionalidades em uma única interface com abas.

---

## Próximos Passos

- **Migrar a camada de persistência de ORMLite para JPA com Hibernate:**
  - Substituir as dependências do ORMLite pelas do **JPA** e **Hibernate**.
  - Configurar a unidade de persistência através do arquivo `persistence.xml`, definindo a conexão com o banco de dados e as propriedades do Hibernate.
  - Refatorar as classes de modelo (`Livro`, `Autor`, etc.), trocando as anotações do ORMLite pelas anotações padrão do JPA (ex: `@Entity`, `@Id`, `@ManyToOne`).
  - Reescrever as classes de repositório (DAO) para utilizar o `EntityManager` do JPA em vez da interface `Dao` do ORMLite.
  - Adaptar a camada de serviço e os controllers para a nova implementação dos repositórios.
