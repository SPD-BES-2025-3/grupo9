package Model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import javax.xml.bind.annotation.*;

@DatabaseTable(tableName = "autor")
@XmlRootElement(name = "autor")
@XmlAccessorType(XmlAccessType.FIELD)
public class Autor {

    @DatabaseField(generatedId = true)
    @XmlElement
    private int id;

    @DatabaseField(canBeNull = false, unique = true)
    @XmlElement
    private String nome;

    @DatabaseField
    @XmlElement
    private String nacionalidade;

    public Autor() {}

    public Autor(String nome, String nacionalidade) {
        this.nome = nome;
        this.nacionalidade = nacionalidade;
    }

    @Override
    public String toString() {
        return this.nome;
    }

    // --- Getters e Setters ---
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public String getNacionalidade() { return nacionalidade; }
    public void setNacionalidade(String nacionalidade) { this.nacionalidade = nacionalidade; }
}
