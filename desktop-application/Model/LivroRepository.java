package Model;

import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.table.TableUtils;
import java.sql.SQLException;
import java.util.List;
import java.util.ArrayList;
import java.io.File;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

// Imports para JSON (Gson)
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

// Imports para XML (JAXB)
import javax.xml.bind.*;
import java.io.StringWriter;
import java.io.StringReader;


public class LivroRepository extends Repository<Livro, Integer> {
    
    private Livro loadedLivro; // Removido 'static'
    private List<Livro> loadedLivros; // Removido 'static'

    // Atributos para serialização JSON
    private Gson gson;

    // Atributos para serialização XML
    private JAXBContext jaxbContext;
    private Marshaller jaxbMarshaller;
    private Unmarshaller jaxbUnmarshaller;

    public LivroRepository(Database database) {
        super(database, Livro.class); // Chama o construtor da classe pai
        loadedLivros = new ArrayList<>(); // Inicializa a lista

        // Inicializa Gson
        gson = new GsonBuilder()
            .setDateFormat("yyyy-MM-dd")
            .setPrettyPrinting()
            .create();

        // Inicializa JAXB
        try {
            jaxbContext = JAXBContext.newInstance(Livro.class);
            jaxbMarshaller = jaxbContext.createMarshaller();
            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            jaxbUnmarshaller = jaxbContext.createUnmarshaller();
        } catch (JAXBException e) {
            System.out.println("Erro ao inicializar JAXB para Livro: " + e.getMessage());
        }
    }

    // Sobrescrita dos métodos CRUD para incluir a lógica de 'loadedLivros' se necessário
    // Ou remova 'loadedLivros' e 'loadedLivro' se eles forem apenas caches temporários
    // e o foco for sempre buscar do banco de dados via 'dao'.

    @Override
    public Livro create(Livro livro) {
        Livro createdLivro = super.create(livro);
        if (createdLivro != null) {
            this.loadedLivro = createdLivro;
            if (!loadedLivros.contains(createdLivro)) {
                loadedLivros.add(createdLivro);
            }
        }
        return createdLivro;
    }

    public Livro loadFromId(int id) { // Mantido com nome original para consistência
        this.loadedLivro = super.loadById(id);
        if (this.loadedLivro != null && !loadedLivros.contains(this.loadedLivro)) {
            this.loadedLivros.add(this.loadedLivro);
        }
        System.out.println("Livro com ID " + id + " " + (this.loadedLivro != null ? "encontrado." : "não encontrado."));
        return this.loadedLivro;
    }

    @Override
    public List<Livro> loadAll() {
        this.loadedLivros = super.loadAll();
        if (!this.loadedLivros.isEmpty()) {
            this.loadedLivro = this.loadedLivros.get(0); // Atualiza o loadedLivro para o primeiro da lista
        }
        System.out.println("Todos os livros carregados. Total: " + this.loadedLivros.size());
        return this.loadedLivros;
    }

    @Override
    public void update(Livro livro) {
        super.update(livro);
        // Atualiza a lista 'loadedLivros' se o objeto estiver nela
        for (int i = 0; i < loadedLivros.size(); i++) {
            if (loadedLivros.get(i).getId() == livro.getId()) {
                loadedLivros.set(i, livro);
                break;
            }
        }
    }

    @Override
    public void delete(Livro livro) {
        super.delete(livro);
        loadedLivros.remove(livro);
        if (this.loadedLivro == livro) {
            this.loadedLivro = null;
        }
    }

    // --- MÉTODOS DE SERIALIZAÇÃO INTEGRADOS (Permanecem aqui) ---

    /**
     * Serializa um objeto Livro para JSON.
     */
    private String toJson(Livro livro) {
        return gson.toJson(livro);
    }

    /**
     * Desserializa uma string JSON para um objeto Livro.
     */
    private Livro fromJson(String json) {
        return gson.fromJson(json, Livro.class);
    }

    /**
     * Serializa um objeto Livro para XML.
     */
    private String toXml(Livro livro) throws JAXBException {
        if (jaxbMarshaller == null) {
            throw new JAXBException("JAXB Marshaller não inicializado.");
        }
        StringWriter sw = new StringWriter();
        jaxbMarshaller.marshal(livro, sw);
        return sw.toString();
    }

    /**
     * Desserializa uma string XML para um objeto Livro.
     */
    private Livro fromXml(String xml) throws JAXBException {
        if (jaxbUnmarshaller == null) {
            throw new JAXBException("JAXB Unmarshaller não inicializado.");
        }
        StringReader sr = new StringReader(xml);
        return (Livro) jaxbUnmarshaller.unmarshal(sr);
    }

    /**
     * Fornece uma string em formato JSON ou XML de todos objetos salvos no banco
     * @param formato "JSON" ou "XML"
     * @return String com dados serializados
     */
    public String dumpData(String formato) {
        List<Livro> todosLivros = loadAll(); // Usa o loadAll da classe pai/sobrescrito
        StringBuilder result = new StringBuilder();
        
        if (formato.equalsIgnoreCase("JSON")) {
            result.append("[\n");
            for (int i = 0; i < todosLivros.size(); i++) {
                result.append(toJson(todosLivros.get(i)));
                if (i < todosLivros.size() - 1) {
                    result.append(",\n");
                }
            }
            result.append("\n]");
        } else if (formato.equalsIgnoreCase("XML")) {
            result.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
            result.append("<livros>\n");
            for (Livro livro : todosLivros) {
                try {
                    String xmlLivro = toXml(livro);
                    xmlLivro = xmlLivro.replaceFirst("\\<\\?xml.*?\\?\\>", "").trim();
                    result.append(xmlLivro).append("\n");
                } catch (JAXBException e) {
                    System.out.println("Erro ao serializar livro para XML: " + e);
                }
            }
            result.append("</livros>");
        } else {
            System.out.println("Formato não suportado. Use 'JSON' ou 'XML'.");
            return null;
        }
        
        System.out.println("Dados exportados em formato " + formato + ". Total: " + todosLivros.size() + " livros.");
        return result.toString();
    }

    /**
     * Serializa os objetos em dado formato em um arquivo de saída
     * @param formato "JSON" ou "XML"
     * @param arquivo Arquivo de destino
     * @return true se bem sucedido, false caso contrário
     */
    public boolean dumpFile(String formato, File arquivo) {
        try {
            String dados = dumpData(formato);
            if (dados != null) {
                FileWriter writer = new FileWriter(arquivo);
                writer.write(dados);
                writer.close();
                System.out.println("Arquivo salvo com sucesso: " + arquivo.getAbsolutePath());
                return true;
            }
        } catch (IOException e) {
            System.out.println("Erro ao salvar arquivo: " + e);
        }
        return false;
    }

    /**
     * Recebe uma string em formato JSON para ser salva no banco
     * @param json String JSON do livro
     * @return Livro criado no banco de dados
     */
    public Livro createFromJSON(String json) {
        try {
            Livro livro = fromJson(json);
            livro.setId(0); // Garante que o ID será gerado pelo banco
            return create(livro); // Usa o create da classe pai/sobrescrito
        } catch (Exception e) {
            System.out.println("Erro ao criar livro a partir de JSON: " + e);
            return null;
        }
    }

    /**
     * Recebe uma string em formato XML para ser salva no banco
     * @param xml String XML do livro
     * @return Livro criado no banco de dados
     */
    public Livro createFromXML(String xml) {
        try {
            Livro livro = fromXml(xml);
            livro.setId(0); // Garante que o ID será gerado pelo banco
            return create(livro); // Usa o create da classe pai/sobrescrito
        } catch (Exception e) {
            System.out.println("Erro ao criar livro a partir de XML: " + e);
            return null;
        }
    }

    /**
     * Recebe uma string em formato JSON ou XML e importa para o banco
     * @param formato "JSON" ou "XML"
     * @param dados String com dados a serem importados
     * @return Quantidade de objetos importados
     */
    public int importData(String formato, String dados) {
        int count = 0;
        
        if (formato.equalsIgnoreCase("JSON")) {
            try {
                dados = dados.trim();
                if (dados.startsWith("[") && dados.endsWith("]")) {
                    dados = dados.substring(1, dados.length() - 1);
                }
                
                // Melhorar o parsing de JSON para múltiplos objetos.
                // Uma maneira mais robusta seria usar um Array de Livros ou um TypeToken.
                // Para simplificar, vou manter a sua lógica de split, mas tenha em mente a limitação.
                String[] livrosJson = dados.split("\\},\\s*\\{");
                
                for (int i = 0; i < livrosJson.length; i++) {
                    String livroJson = livrosJson[i].trim();
                    if (!livroJson.startsWith("{")) {
                        livroJson = "{" + livroJson;
                    }
                    if (!livroJson.endsWith("}")) {
                        livroJson = livroJson + "}";
                    }
                    
                    Livro livro = createFromJSON(livroJson);
                    if (livro != null) {
                        count++;
                    }
                }
            } catch (Exception e) {
                System.out.println("Erro ao importar dados JSON: " + e);
            }
        } else if (formato.equalsIgnoreCase("XML")) {
            try {
                // Parsing de XML para múltiplos objetos também precisa ser mais robusto.
                // O JAXB pode desserializar listas de objetos se a raiz XML for uma coleção.
                String[] livrosXml = dados.split("</livro>");
                
                for (int i = 0; i < livrosXml.length - 1; i++) {
                    String livroXml = livrosXml[i].trim();
                    
                    int startIndex = livroXml.lastIndexOf("<livro>");
                    if (startIndex != -1) {
                        livroXml = livroXml.substring(startIndex) + "</livro>";
                        
                        if (!livroXml.contains("<?xml")) {
                            livroXml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" + livroXml;
                        }
                        
                        Livro livro = createFromXML(livroXml);
                        if (livro != null) {
                            count++;
                        }
                    }
                }
            } catch (Exception e) {
                System.out.println("Erro ao importar dados XML: " + e);
            }
        } else {
            System.out.println("Formato não suportado. Use 'JSON' ou 'XML'.");
        }
        
        System.out.println("Importação concluída. " + count + " livros importados.");
        return count;
    }

    /**
     * Importa objetos serializados de um arquivo para o banco
     * @param formato "JSON" ou "XML"
     * @param arquivo Arquivo a ser importado
     * @return Quantidade de objetos importados
     */
    public int importFile(String formato, File arquivo) {
        try {
            Scanner scanner = new Scanner(arquivo);
            StringBuilder dados = new StringBuilder();
            
            while (scanner.hasNextLine()) {
                dados.append(scanner.nextLine()).append("\n");
            }
            scanner.close();
            
            System.out.println("Arquivo lido com sucesso: " + arquivo.getAbsolutePath());
            return importData(formato, dados.toString());
            
        } catch (Exception e) {
            System.out.println("Erro ao ler arquivo: " + e);
            return 0;
        }
    }

    // --- Getters para os atributos carregados ---

    public Livro getLoadedLivro() {
        return this.loadedLivro;
    }

    public List<Livro> getLoadedLivros() {
        return this.loadedLivros;
    }
}