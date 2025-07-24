# Sistema de Gerenciamento de Biblioteca

Este é um sistema de gerenciamento de biblioteca desenvolvido em JavaFX. Ele permite realizar operações CRUD (Criar, Ler, Atualizar, Deletar) em cinco entidades principais: **Livros**, **Autores**, **Usuários**, **Editoras** e **Empréstimos**.

O projeto utiliza o padrão **JPA (Java Persistence API)** com **Hibernate** para persistência de dados em um banco de dados **SQLite**, garantindo que as informações sejam salvas entre as sessões. Além disso, oferece funcionalidades de importação e exportação de dados nos formatos **JSON** (com Gson) e **XML** (com JAXB).

## Funcionalidades

- **Gerenciamento de Empréstimos:** Registro de novos empréstimos, devoluções e consulta do histórico.
- **Gerenciamento de Livros:** Cadastro, atualização e remoção de livros, associando-os a autores e editoras.
- **Gerenciamento de Autores, Editoras e Usuários:** Operações CRUD completas para todas as entidades de apoio.
- **Interface Intuitiva:** Uma interface gráfica de múltiplas abas, construída com JavaFX e FXML, para facilitar a navegação.
- **Persistência de Dados com JPA/Hibernate:** Os dados são mapeados de objetos Java para um banco de dados relacional SQLite, de forma robusta e padronizada.

## Tecnologias Utilizadas

- **JavaFX:** Framework para a construção da interface gráfica.
- **Maven:** Ferramenta para automação de build e gerenciamento de dependências.
- **JPA (Jakarta Persistence API):** Especificação padrão do Java para mapeamento objeto-relacional (ORM).
- **Hibernate:** Implementação mais popular do JPA, utilizada para gerenciar a persistência dos dados.
- **SQLite:** Banco de dados relacional embutido, que armazena os dados em um único arquivo (`livraria.sqlite`).
- **Gson:** Biblioteca para serialização e desserialização de objetos Java para e de JSON.
- **JAXB:** API padrão para mapeamento entre objetos Java e XML.

## Estrutura do Projeto (Padrão Maven)

O projeto segue a arquitetura **MVC (Model-View-Controller)** e a estrutura de diretórios padrão do Maven:

- **`src/main/java`**: Contém todo o código-fonte Java.
  - `br.com.grupo9.model/`: Classes de entidade (Ex: `Livro`, `Autor`), repositórios e a classe `JPAUtil`.
  - `br.com.grupo9.view/`: A classe principal da aplicação (`AppView.java`) e classes de "View Model".
  - `br.com.grupo9.controller/`: O `AppController.java`, que gerencia a lógica da interface.
- **`src/main/resources`**: Contém arquivos de recursos e configuração.
  - `view/app.fxml`: O arquivo FXML que define a interface gráfica.
  - `META-INF/persistence.xml`: Arquivo de configuração do JPA, que define a conexão com o banco de dados e as entidades.
- **`pom.xml`**: Arquivo de configuração do Maven, onde todas as dependências do projeto são declaradas.

## Como Executar o Código-Fonte

A forma recomendada para executar este projeto é utilizando uma IDE moderna como o **IntelliJ IDEA** (a versão Community é gratuita) ou **Eclipse**, que possuem integração nativa com o **Maven**.

### Pré-requisitos

- **Java Development Kit (JDK) 17 ou superior**.
- **IntelliJ IDEA** ou outra IDE com suporte a Maven.

### Configuração e Execução com IntelliJ IDEA

1. **Clone o Repositório:**

    ```bash
    git clone https://github.com/SPD-BES-2025-3/grupo9.git
    ```

2. **Abra o Projeto no IntelliJ IDEA:**

    - Na tela inicial do IntelliJ, clique em **Open**.
    - Navegue até a pasta `grupo9/desktop_application` que você acabou de clonar e selecione-a.
    - O IntelliJ detectará automaticamente o arquivo `pom.xml` e tratará o projeto como um projeto Maven.

3. **Carregue as Dependências do Maven:**

    - Após abrir o projeto, o IntelliJ pode pedir para "Load Maven Project". Se não o fizer, você pode clicar com o botão direito no arquivo `pom.xml` na árvore de projetos e selecionar `Maven` > `Reload Project`.
    - O Maven irá baixar e configurar automaticamente todas as dependências necessárias (JavaFX, Hibernate, SQLite, etc.). Este processo pode levar um minuto na primeira vez.

4. **Execute a Aplicação:**
    - Na árvore de projetos, navegue até `src/main/java/br/com/grupo9/view/AppView.java`.
    - Clique com o botão direito do mouse no arquivo `AppView.java` e selecione **Run 'AppView.main()'**.
    - A interface gráfica do sistema de gerenciamento da biblioteca deverá ser iniciada.

### Execução via Linha de Comando (com Maven)

Se você tiver o Maven instalado em seu sistema, também pode compilar e executar o projeto diretamente pelo terminal.

1. **Navegue até a pasta do projeto:**

    ```bash
    cd grupo9/desktop_application
    ```

2. **Compile o projeto:**

    ```bash
    mvn clean install
    ```

    Este comando irá baixar as dependências, compilar o código e criar um arquivo `.jar` na pasta `target`.

3. **Execute a aplicação (usando o plugin do JavaFX):**
    Adicione o plugin de execução do JavaFX no seu `pom.xml` dentro da tag `<build><plugins>`:

    ```xml
    <plugin>
        <groupId>org.openjfx</groupId>
        <artifactId>javafx-maven-plugin</artifactId>
        <version>0.0.8</version>
        <configuration>
            <mainClass>br.com.grupo9.view.AppView</mainClass>
        </configuration>
    </plugin>
    ```

    Em seguida, execute o comando:

    ```bash
    mvn javafx:run
    ```
