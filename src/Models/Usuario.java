package Models;

import java.sql.Date;

public class Usuario {
    private int id;
    private String nome;
    private String email;
    private String senha;
    private Date dt_criacao;

    public Usuario() {
    }

    public Usuario(int id, String nome, String email, String senha, Date dt_criacao) {
        this.id = id;
        this.nome = nome;
        this.senha = senha;
        this.dt_criacao = dt_criacao;
        this.email = email;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public Date getDt_criacao() {
        return dt_criacao;
    }

    public void setDt_criacao(Date dt_criacao) {
        this.dt_criacao = dt_criacao;
    }
}
