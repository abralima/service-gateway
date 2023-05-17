import java.io.Serializable;

public class Carro implements Serializable {
    private static final long serialVersionUID = 1L;
    private String renavan;
    private String nome;
    private String categoria;
    private Integer ano;
    private Integer preco;

    public Carro() {
    }

    public Carro(String renavan, String nome, String categoria, int ano, int preco) {
        this.renavan = renavan;
        this.nome = nome;
        this.categoria = categoria;
        this.ano = ano;
        this.preco = preco;
    }

    public String getRenavan() {
        return this.renavan;
    }

    public void setRenavan(String renavan) {
        this.renavan = renavan;
    }

    public String getNome() {
        return this.nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCategoria() {
        return this.categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public Integer getAno() {
        return this.ano;
    }

    public void setAno(Integer ano) {
        this.ano = ano;
    }

    public Integer getPreco() {
        return this.preco;
    }

    public void setPreco(Integer preco) {
        this.preco = preco;
    }

    @Override
    public String toString() {
        return "{" +
                " renavan='" + getRenavan() + "'" +
                ", nome='" + getNome() + "'" +
                ", categoria='" + getCategoria() + "'" +
                ", ano='" + getAno() + "'" +
                ", preco='" + getPreco() + "'" +
                "}";
    }

}
