**Disciplina:** Software para persistência de Dados

**Alunas:**

  - [Stephany Milhomem](https://github.com/StephanyMil) - [Relatório Individual](https://github.com/SPD-BES-2025-3/grupo9/tree/main/relatorios/stephany)
  - [Thayliny Moura](https://github.com/thaylinymoura) - [Relatório Individual](https://github.com/SPD-BES-2025-3/grupo9/tree/main/relatorios/thayliny)
  - [Vitoria Luz](https://github.com/Vitorialuz229) - [Relatório Individual](https://github.com/SPD-BES-2025-3/grupo9/tree/main/relatorios/vitoria)

# Sistema de Gerenciamento de Biblioteca

O sistema de Gerenciamento de Biblioteca visa simplificar o gerenciamento de uma biblioteca, oferecendo uma interface intuitiva para a administração de seu acervo e usuários. A motivação principal é fornecer uma solução robusta e flexível para as operações diárias de uma biblioteca, garantindo a persistência e a integridade dos dados.

## Objetivo Geral

O objetivo é desenvolver um sistema de gerenciamento de biblioteca desktop completo, com funcionalidades CRUD, persistência de dados em SQLite e recursos de serialização/desserialização para JSON e XML.

## Objetivos Específicos

  - Implementar operações CRUD para as entidades Livro, Autor, Editora, Usuário e Empréstimo.
  - Utilizar ORMLite para o mapeamento objeto-relacional com o banco de dados SQLite.
  - Permitir a importação e exportação de dados (especificamente livros) nos formatos JSON (via Gson) e XML (via JAXB).
  - Prover uma interface gráfica amigável e interativa utilizando JavaFX.

## Tecnologias e Ferramentas Utilizadas

  - **JavaFX:** Framework para construção da interface gráfica da aplicação desktop.
  - **ORMLite:** Biblioteca ORM para mapeamento de objetos Java para tabelas de banco de dados e vice-versa.
  - **SQLite:** Banco de dados relacional leve e embutido (livraria.sqlite) para persistência dos dados.
  - **Gson:** Biblioteca Java para serialização e desserialização de objetos Java em JSON.
  - **JAXB (Java Architecture for XML Binding):** API para mapeamento de objetos Java para XML e vice-versa.
  - **JDK 11 ou superior:** Ambiente de desenvolvimento Java necessário.
  - **Testes unitários com JUnit.**
  - **Integrador de Entidades:** uso de Redis, Lettuce e Pub/Sub.

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
| Contrução de Teste Unitário                                                                                                                            | 23/07       | 25/07    | Thayliny    | Em andamento |     
| Teste Unitario JUnit                                                                                                                                    | 23/07       | 25/07    | Thayliny    | Em andamento | 
    

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
| Aplicação do padrão Builder com Lombok em DTOs | 21/07 | 25/07 | Vitoria | Em andamento |
| Criação de modelos iniciais com base no diagrama do projeto | 21/07 | 25/07 | Vitoria | Em andamento |
| Finalizar os métodos CRUD no controller e service | 22/07 | 26/07 | Vitoria | A Fazer |
| Criar os testes unitários para os repositórios | 22/07 | 28/07 | Vitoria | A Fazer |
| Adicionar validações nas entidades e DTOs | 22/07 | 28/07 | Vitoria | A Fazer |
| Iniciar a documentação da API com Swagger/OpenAPI | 22/07 | 29/07 | Vitoria | A Fazer |

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

## Testes Unitários
