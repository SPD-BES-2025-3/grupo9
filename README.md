**Disciplina:** Software para persistência de Dados 

**Alunas:**
* [Stephany Milhomem]
* [Thayliny Moura]
* [Vitoria Luz ]


# Sistema de Gerenciamento de Biblioteca

O sistema  de Gerencaimento de Biblioteca visa simplificar o gerenciamento de uma biblioteca, oferecendo uma interface intuitiva para a administração de seu acervo e usuários. A motivação principal é fornecer uma solução robusta e flexível para as operações diárias de uma biblioteca, garantindo a persistência e a integridade dos dados.

## Objetivo Geral 

O objetivo é desenvolver um sistema de gerenciamento de biblioteca desktop completo, com funcionalidades CRUD, persistência de dados em SQLite e recursos de serialização/desserialização para JSON e XML.

## Objetivos Específicos

* Implementar operações CRUD para as entidades Livro, Autor, Editora, Usuário e Empréstimo.
* Utilizar ORMLite para o mapeamento objeto-relacional com o banco de dados SQLite.
* Permitir a importação e exportação de dados (especificamente livros) nos formatos JSON (via Gson) e XML (via JAXB).
* Prover uma interface gráfica amigável e interativa utilizando JavaFX.

## Tecnologias e Ferramentas Utilizadas

* JavaFX: Framework para construção da interface gráfica da aplicação desktop.
* RMLite: Biblioteca ORM para mapeamento de objetos Java para tabelas de banco de dados e vice-versa.
* SQLite: Banco de dados relacional leve e embutido (livraria.sqlite) para persistência dos dados.
* Gson: Biblioteca Java para serialização e desserialização de objetos Java em JSON.
* JAXB (Java Architecture for XML Binding): API para mapeamento de objetos Java para XML e vice-versa.
* JDK 11 ou superior: Ambiente de desenvolvimento Java necessário.
* Testes unitários com JUnit.
* Integrador de Entidades: uso de Redis, Lettuce e Pub/Sub.

## Divisão de Tarefas API Rest

|Atividade|Data Início|Data Fim|Responsável|Situação|
|---|---|---|---|---
|Documentar o Projeto |18/07|21/07|Thayliny | Concluida |
|Produzir o código e a documentação inicial no repositório|18/07| 20/07| Stepahny |Concluida|
|Teste Unitario||||||
|Adicionar, atualizar e deletar livros||||||
|Visualizar a lista de livros em uma tabela interativa||||||
|Associar livros a autores existentes usando um ComboBox||||||
|Adicionar, atualizar e deletar autores||||||
|Visualizar a lista de autores em uma tabela||||||
|Validação para impedir a exclusão de autores vinculados a livros||||||
|Persistência de Dados: Todos os dados são armazenados em um arquivo SQLite (livraria.sqlite), garantindo que as informações persistam entre as sessões.||||||
|Exporte dados de livros ou autores para arquivos JSON ou XML||||||
|Importe dados de livros ou autores de arquivos JSON ou XML||||||
|Contrução de Teste Unitário||||||

## Divisão de Tarefas JavaFX

|Atividade|Data Início|Data Fim|Responsável|Situação|
|---|---|---|---|---
|Documentar o Projeto |18/07|21/07|Thayliny | Concluida |
|Produzir o código e a documentação inicial no repositório|18/07| 20/07| Stepahny |Concluida|
|Teste Unitario do Projeto JavaFX|21/07|25/07|Thayliny| Em andamento||
|Adicionar, atualizar e deletar livros||||||
|Visualizar a lista de livros em uma tabela interativa||||||
|Associar livros a autores existentes usando um ComboBox||||||
|Adicionar, atualizar e deletar autores||||||
|Visualizar a lista de autores em uma tabela||||||
|Validação para impedir a exclusão de autores vinculados a livros||||||
|Persistência de Dados: Todos os dados são armazenados em um arquivo SQLite (livraria.sqlite), garantindo que as informações persistam entre as sessões.||||||
|Exporte dados de livros ou autores para arquivos JSON ou XML||||||
|Importe dados de livros ou autores de arquivos JSON ou XML||||||
|Contrução de Teste Unitário||||||


## Modelagem Inicial

### Diagrama de Classes com Relações

![](https://github.com/SPD-BES-2025-3/grupo9/blob/thayliny/diagramas/diagrama-classe1.png)

### Arquitetura do Sistema (MVC)

![](https://github.com/SPD-BES-2025-3/grupo9/blob/thayliny/diagramas/diagrama-componentes.png)

### Diagrama de Sequência para Operações de Integração

![](https://github.com/SPD-BES-2025-3/grupo9/blob/thayliny/diagramas/diagrama-sequencia.png)

## Documentação de Código Inicial

### Como Instalar e Executar o Código Inicial

1. BlueJ: Inclui passos para abrir o projeto, adicionar as bibliotecas JAR (SQLite JDBC, ORMLite Core, ORMLite JDBC, Gson, e dependências JAXB para JDK 11+) e configurar as VM Options do JavaFX.

 2. Linha de Comando: Fornece comandos javac para compilação e java para execução, com as configurações --module-path e --add-modules para JavaFX, e --cp para o classpath das bibliotecas.

3. Outras IDEs (IntelliJ IDEA, Eclipse): Orientações gerais para importar o projeto, configurar o JDK, adicionar o JavaFX SDK como biblioteca modular e incluir os demais JARs como bibliotecas de projeto, além de definir as VM Options.

### Passo a passo de como execultar os Projetos 
* [Sistema de Gerenciamento de Biblioteca API RestFull](https://github.com/SPD-BES-2025-3/grupo9/blob/main/api_rest_full_application/README.md)
* [Sistema de Gerenciamento de Biblioteca JavaFX](https://github.com/SPD-BES-2025-3/grupo9/blob/main/api_rest_full_application/README.md)

### Documentação de Código
O projeto utiliza:

* Markdown: O arquivo README.md serve como a documentação principal do projeto, descrevendo suas funcionalidades, tecnologias e instruções de execução. 
* JavaDoc: Algumas classes, como Database.java e AppView.java, contêm comentários Javadoc para descrever a finalidade da classe, autor e versão.


## Testes Unitários
