package Models;

public class Setor {
    private int id;
    private String nome;
    private String localizacao;
    private String descricao;
    private int criado_por;

    public Setor(){}

    public Setor(int id,String nome, String localizacoa, String descricao, int criado_por) {
        this.criado_por = criado_por;
        this.descricao = descricao;
        this.localizacao = localizacoa;
        this.nome = nome;

        this.id = id;
    }

    @Override
    public String toString() {
        return nome; // Exibe o nome no combo
    }

    public int getCriado_por() {
        return criado_por;
    }

    public void setCriado_por(int criado_por) {
        this.criado_por = criado_por;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getLocalizacao() {
        return localizacao;
    }

    public void setLocalizacoa(String localizacoa) {
        this.localizacao = localizacoa;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
