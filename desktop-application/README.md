# Sistema de Gerenciamento de Biblioteca

Este é um sistema de gerenciamento de biblioteca desenvolvido em JavaFX, que permite realizar operações CRUD (Criar, Ler, Atualizar, Deletar) em cinco entidades principais: **Livros**, **Autores**, **Usuários**, **Editoras** e **Empréstimos**. O projeto utiliza **ORMLite** para persistência de dados em um banco de dados **SQLite** e oferece funcionalidades de importação/exportação de dados em formatos **JSON** e **XML** utilizando **Gson** e **JAXB**.

## Funcionalidades

- **Gerenciamento de Livros:**
  - Adicionar, atualizar e deletar livros.
  - Visualizar a lista de livros em uma tabela interativa.
  - Associar livros a autores existentes usando um `ComboBox`.
- **Gerenciamento de Autores:**
  - Adicionar, atualizar e deletar autores.
  - Visualizar a lista de autores em uma tabela.
  - Validação para impedir a exclusão de autores vinculados a livros.
- **Persistência de Dados:** Todos os dados são armazenados em um arquivo SQLite (`livraria.sqlite`), garantindo que as informações persistam entre as sessões.
- **Importação e Exportação:**
  - Exporte dados de livros ou autores para arquivos JSON ou XML.
  - Importe dados de livros ou autores de arquivos JSON ou XML.

## Tecnologias Utilizadas

- **JavaFX:** Framework para construção de interfaces gráficas.
- **ORMLite:** Biblioteca ORM (Object-Relational Mapping) para mapeamento objeto-relacional com SQLite.
- **SQLite:** Banco de dados relacional embutido (`.sqlite`).
- **Gson:** Biblioteca para serialização e desserialização de objetos Java para e de JSON.
- **JAXB (Java Architecture for XML Binding):** API para mapeamento entre objetos Java e XML (pode exigir dependências extras para JDKs mais recentes).

## Estrutura do Projeto

O projeto segue o padrão arquitetural **MVC (Model-View-Controller)**:

- `src/Model/`: Contém as classes de modelo e os repositórios que interagem com o banco de dados e a lógica de negócio.
- `src/View/`: Contém o arquivo FXML (`app.fxml`) que define a interface gráfica e as classes "View Model" que adaptam os dados para exibição na interface.
- `src/Controller/`: Contém o `AppController.java`, que gerencia a lógica de interação entre a View e o Model, respondendo a eventos da interface.

## Como Executar o Código-Fonte

Para executar este projeto, você precisará ter o **Java Development Kit (JDK) 11 ou superior** e o **JavaFX SDK** configurados.

### 1\. Obtenha o Código e Dependências

1. **Clone o Repositório:**

   ```bash
   git clone https://github.com/SPD-BES-2025-3/grupo9.git
   cd grupo9/desktop-application
   ```

2. **Baixe o JavaFX SDK:**
   - Acesse [OpenJFX](https://www.google.com/search?q=https://openjfx.io/openjfx-docs/%23install).
   - Baixe a versão **SDK** mais recente (compatível com seu JDK, geralmente JDK 11 ou superior).
   - Descompacte o SDK em um local de fácil acesso no seu sistema (ex: `C:\javafx-sdk-21` ou `/opt/javafx-sdk-21`).
3. **Baixe as Bibliotecas (JARs):**
   Crie uma pasta chamada `lib` na raiz do seu projeto (onde está o `src`). Baixe os seguintes arquivos JAR e coloque-os dentro dessa pasta `lib`:
   - **SQLite JDBC Driver:** [sqlite-jdbc-X.X.X.jar](https://github.com/xerial/sqlite-jdbc/releases) (escolha a versão mais recente).
   - **ORMLite Core:** [ormlite-core-X.X.jar](https://www.google.com/search?q=http://repo1.maven.org/maven2/com/j256/ormlite/ormlite-core/) (escolha a versão mais recente).
   - **ORMLite JDBC:** [ormlite-jdbc-X.X.jar](https://www.google.com/search?q=http://repo1.maven.org/maven2/com/j256/ormlite/ormlite-jdbc/) (escolha a versão mais recente, correspondente à versão core).
   - **Gson:** [gson-X.X.X.jar](https://repo1.maven.org/maven2/com/google/code/gson/gson/) (escolha a versão mais recente).
   - **Dependências JAXB (apenas para JDK 11+):** Se você estiver usando um JDK 11 ou superior, o JAXB não vem mais incluído. Baixe estes dois JARs:
     - [jakarta.xml.bind-api-X.X.X.jar](https://www.google.com/search?q=https://repo1.maven.org/maven2/jakarta/xml/bind/jakarta.xml.bind-api/)
     - [jaxb-runtime-X.X.X.jar](https://www.google.com/search?q=https://repo1.maven.org/maven2/org/glassfish/jaxb/jaxb-runtime/)

### 2\. Configuração e Execução

#### a) Usando BlueJ

1. **Abra o Projeto no BlueJ:**
   - No BlueJ, vá em `Project` -\> `Open Non BlueJ...` ou `Open Project...` e selecione a pasta `grupo9/desktop-application` que você clonou.
2. **Adicione as Bibliotecas JAR (para BlueJ):**
   - No menu do BlueJ, vá em `Tools` -\> `Preferences...` -\> `Libraries`.
   - Clique em `Add Jar...` e selecione todos os arquivos `.jar` que você colocou na pasta `lib` do seu projeto (`sqlite-jdbc`, `ormlite-core`, `ormlite-jdbc`, `gson`, e os dois JARs `jakarta.xml.bind-api` e `jaxb-runtime` se estiver usando JDK 11+).
3. **Configure o JavaFX (para BlueJ):**

   - Ainda em `Tools` -\> `Preferences...` -\> `Libraries`, clique em `Add Jar...` novamente.
   - Navegue até a pasta `lib` dentro do seu JavaFX SDK (ex: `C:\javafx-sdk-21\lib`) e selecione **todos** os arquivos `.jar` dentro dela.
   - **Opções de Máquina Virtual (VM Options):** Para que o JavaFX funcione, você precisa configurar as VM Options.

     - Vá em `Tools` -\> `Preferences...` -\> `VM Arguments`.
     - Adicione a seguinte linha (ajuste o caminho para o seu JavaFX SDK):

       ```
       --module-path "/caminho/para/javafx-sdk-X.X.X/lib" --add-modules javafx.controls,javafx.fxml,javafx.graphics
       ```

       _(Exemplo real: `--module-path "C:\javafx-sdk-21\lib" --add-modules javafx.controls,javafx.fxml,javafx.graphics`)_

4. **Execute a Aplicação:**
   - Na janela do projeto BlueJ, localize a classe `AppView.java` (dentro da pasta `view`).
   - Clique com o botão direito do mouse em `AppView.java` e selecione `void main(String[] args)`.
   - A interface gráfica do sistema deverá ser iniciada.

#### b) Usando Linha de Comando

1. **Compile o Código:**

   - Abra o terminal ou prompt de comando na raiz do seu projeto (`grupo9/desktop-application`).
   - Ajuste o caminho do `--module-path` para onde você descompactou o JavaFX SDK e o `lib` para sua pasta de dependências.
   <!-- end list -->

   ```bash
   javac --module-path "/caminho/para/javafx-sdk-X.X.X/lib" --add-modules javafx.controls,javafx.fxml,javafx.graphics -d out src/**/*.java lib/*.jar
   ```

   _(Substitua `/caminho/para/javafx-sdk-X.X.X/lib` pelo caminho real do seu JavaFX SDK.)_
   _(Se você tiver muitos JARs na pasta `lib`, considere usar um build tool como Maven/Gradle, ou liste-os individualmente se o wildcard `lib/_.jar` não funcionar em seu SO/shell.)\*

2. **Execute a Aplicação:**

   ```bash
   java --module-path "/caminho/para/javafx-sdk-X.X.X/lib" --add-modules javafx.controls,javafx.fxml,javafx.graphics -cp "out:lib/*" view.AppView
   ```

   _(No Windows, use `;` em vez de `:` para o classpath: `-cp "out;lib/_"`)\*

#### c) Usando Outra IDE (Ex: IntelliJ IDEA, Eclipse)

1. **Importe o Projeto:**
   - No IntelliJ IDEA ou Eclipse, importe a pasta `grupo9/desktop-application` como um projeto Java existente ou um projeto do tipo "Empty Project" e importe os módulos/pastas de código.
2. **Configure o JDK e JavaFX:**
   - Adicione o JDK 11+ ao projeto.
   - **Adicione o JavaFX SDK:** Configure o JavaFX SDK como uma biblioteca modular no seu projeto/módulo.
     - No IntelliJ, vá em `File` -\> `Project Structure` -\> `Libraries`, clique no `+` e adicione o diretório `lib` do seu JavaFX SDK.
     - Em `Run/Debug Configurations`, adicione as `VM Options` (argumentos da JVM) conforme o item 3 da seção BlueJ.
   - **Adicione as demais JARs (`lib/`):** Adicione todos os JARs da pasta `lib` (SQLite, ORMLite, Gson, JAXB) como bibliotecas ao seu projeto/módulo.
3. **Execute a Aplicação:**
   - Execute a classe `AppView.java` (geralmente clicando com o botão direito e selecionando "Run").
