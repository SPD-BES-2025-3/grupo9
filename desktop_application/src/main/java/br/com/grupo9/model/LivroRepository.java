package br.com.grupo9.model;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import jakarta.xml.bind.*;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class LivroRepository extends Repository<Livro, Integer> {

    private Livro loadedLivro;
    private List<Livro> loadedLivros;
    private final Gson gson;
    private JAXBContext jaxbContext;
    private Marshaller jaxbMarshaller;
    private Unmarshaller jaxbUnmarshaller;

    public LivroRepository() {
        super(Livro.class);
        this.loadedLivros = new ArrayList<>();

        this.gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd")
                .setPrettyPrinting()
                .create();

        try {
            this.jaxbContext = JAXBContext.newInstance(Livro.class);
            this.jaxbMarshaller = jaxbContext.createMarshaller();
            this.jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            this.jaxbUnmarshaller = jaxbContext.createUnmarshaller();
        } catch (JAXBException e) {
            System.err.println("Erro ao inicializar JAXB para Livro: " + e.getMessage());
        }
    }

    @Override
    public Livro create(Livro livro) {
        Livro createdLivro = super.create(livro);
        if (createdLivro != null) {
            this.loadedLivro = createdLivro;
            if (!this.loadedLivros.contains(createdLivro)) {
                this.loadedLivros.add(createdLivro);
            }
        }
        return createdLivro;
    }

    @Override
    public List<Livro> loadAll() {
        this.loadedLivros = super.loadAll();
        if (this.loadedLivros != null && !this.loadedLivros.isEmpty()) {
            this.loadedLivro = this.loadedLivros.get(0);
        } else {
            this.loadedLivro = null;
        }
        System.out.println("Todos os livros carregados. Total: " + (this.loadedLivros != null ? this.loadedLivros.size() : 0));
        return this.loadedLivros;
    }

    @Override
    public void update(Livro livro) {
        super.update(livro);
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
        if (this.loadedLivro != null && this.loadedLivro.getId() == livro.getId()) {
            this.loadedLivro = null;
        }
    }
    //endregion

    private String toJson(Livro livro) {
        return gson.toJson(livro);
    }

    private Livro fromJson(String json) {
        return gson.fromJson(json, Livro.class);
    }

    private String toXml(Livro livro) throws JAXBException {
        if (jaxbMarshaller == null) throw new JAXBException("JAXB Marshaller não inicializado.");
        StringWriter sw = new StringWriter();
        jaxbMarshaller.marshal(livro, sw);
        return sw.toString();
    }

    private Livro fromXml(String xml) throws JAXBException {
        if (jaxbUnmarshaller == null) throw new JAXBException("JAXB Unmarshaller não inicializado.");
        StringReader sr = new StringReader(xml);
        return (Livro) jaxbUnmarshaller.unmarshal(sr);
    }

    public String dumpData(String formato) {
        List<Livro> todosLivros = super.loadAll();
        StringBuilder result = new StringBuilder();

        if (formato.equalsIgnoreCase("JSON")) {
            result.append(gson.toJson(todosLivros));
        } else if (formato.equalsIgnoreCase("XML")) {
            try {
                result.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<livros>\n");
                for (Livro livro : todosLivros) {
                    String xmlLivro = toXml(livro);
                    xmlLivro = xmlLivro.replaceFirst("<\\?xml.*?\\?>", "").trim();
                    result.append(xmlLivro).append("\n");
                }
                result.append("</livros>");
            } catch (JAXBException e) {
                System.err.println("Erro ao serializar livros para XML: " + e.getMessage());
                return null;
            }
        } else {
            System.err.println("Formato não suportado. Use 'JSON' ou 'XML'.");
            return null;
        }

        System.out.println("Dados exportados em formato " + formato + ". Total: " + todosLivros.size() + " livros.");
        return result.toString();
    }

    public boolean dumpFile(String formato, File arquivo) {
        try (FileWriter writer = new FileWriter(arquivo)) {
            String dados = dumpData(formato);
            if (dados != null) {
                writer.write(dados);
                System.out.println("Arquivo salvo com sucesso: " + arquivo.getAbsolutePath());
                return true;
            }
        } catch (IOException e) {
            System.err.println("Erro ao salvar arquivo: " + e.getMessage());
        }
        return false;
    }

    public Livro createFromJSON(String json) {
        try {
            Livro livro = fromJson(json);
            livro.setId(0);
            return create(livro);
        } catch (Exception e) {
            System.err.println("Erro ao criar livro a partir de JSON: " + e.getMessage());
            return null;
        }
    }

    public Livro createFromXML(String xml) {
        try {
            Livro livro = fromXml(xml);
            livro.setId(0);
            return create(livro);
        } catch (Exception e) {
            System.err.println("Erro ao criar livro a partir de XML: " + e.getMessage());
            return null;
        }
    }

    //endregion

    //region Getters para Cache Local

    public Livro getLoadedLivro() {
        return this.loadedLivro;
    }

    public List<Livro> getLoadedLivros() {
        return this.loadedLivros;
    }

    //endregion
}
