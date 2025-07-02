package Models;

import java.sql.Date;

public class Favorito {
    private int id;
    private int usuario_id;
    private int via_id;
    Date data_favorito;

    public Favorito(){}

    public Favorito(int id, int usuario_id, int via_id, Date data_favorito) {
        this.id = id;
        this.usuario_id = usuario_id;
        this.via_id = via_id;
        this.data_favorito = data_favorito;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUsuario_id() {
        return usuario_id;
    }

    public void setUsuario_id(int usuario_id) {
        this.usuario_id = usuario_id;
    }

    public int getVia_id() {
        return via_id;
    }

    public void setVia_id(int via_id) {
        this.via_id = via_id;
    }

    public Date getData_favorito() {
        return data_favorito;
    }

    public void setData_favorito(Date data_favorito) {
        this.data_favorito = data_favorito;
    }
}

