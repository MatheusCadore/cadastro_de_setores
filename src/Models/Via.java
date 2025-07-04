package Models;

public class Via {
    private int id;
    private String nome;
    private String grau;
    private String tipo;
    private int setor_id;
    private int criado_por;

    public Via(){}

    public Via(int id, int criado_por, String nome, String tipo, int setor_id, String grau) {
        this.id = id;
        this.criado_por = criado_por;
        this.nome = nome;
        this.tipo = tipo;
        this.setor_id = setor_id;
        this.grau = grau;
    }

    @Override
    public String toString() {
        return nome;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCriado_por() {
        return criado_por;
    }

    public void setCriado_por(int criado_por) {
        this.criado_por = criado_por;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getGrau() {
        return grau;
    }

    public void setGrau(String grau) {
        this.grau = grau;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getSetor_id() {
        return setor_id;
    }

    public void setSetor_id(int setor_id) {
        this.setor_id = setor_id;
    }
}
