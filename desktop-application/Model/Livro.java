package Model;

import java.util.Date;
import java.text.SimpleDateFormat;
import com.j256.ormlite.table.DatabaseTable;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.DataType;
import javax.xml.bind.annotation.*;

@DatabaseTable(tableName = "livro")
@XmlRootElement(name = "livro")
@XmlAccessorType(XmlAccessType.FIELD)
public class Livro {

    @DatabaseField(generatedId = true)
    @XmlElement
    private int id;

    @DatabaseField(canBeNull = false)
    @XmlElement
    private String titulo;

    @DatabaseField(foreign = true, foreignAutoRefresh = true, canBeNull = false, columnName = "autor_id")
    @XmlElement
    private Autor autor;
    
    @DatabaseField(foreign = true, foreignAutoRefresh = true, canBeNull = false, columnName = "editora_id")
    private Editora editora;

    @DatabaseField
    @XmlElement
    private int anoPublicacao;

    @DatabaseField
    @XmlElement
    private double preco;

    @DatabaseField(dataType = DataType.DATE)
    @XmlElement
    private Date dataCadastro;

    public Livro() {}

    public String printDataCadastro() {
        if (dataCadastro == null) return "N/A";
        SimpleDateFormat dateFor = new SimpleDateFormat("dd/MM/yyyy");
        return dateFor.format(dataCadastro);
    }

    // --- Getters e Setters ---
    public int getId() { return this.id; }
    public void setId(int id) { this.id = id; }
    public String getTitulo() { return this.titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }
    public Autor getAutor() { return this.autor; }
    public void setAutor(Autor autor) { this.autor = autor; }
    public Editora getEditora() { return editora; }
    public void setEditora(Editora editora) { this.editora = editora; }
    public int getAnoPublicacao() { return this.anoPublicacao; }
    public void setAnoPublicacao(int anoPublicacao) { this.anoPublicacao = anoPublicacao; }
    public double getPreco() { return this.preco; }
    public void setPreco(double preco) { this.preco = preco; }
    public Date getDataCadastro() { return this.dataCadastro; }
    public void setDataCadastro(Date dataCadastro) { this.dataCadastro = dataCadastro; }
    
    @Override
    public String toString() {
        return this.titulo;
    }
}