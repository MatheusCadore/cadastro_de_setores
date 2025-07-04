package DAOs;

import Models.Favorito;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FavoritoDAO {

    private Connection conn;

    public FavoritoDAO() throws SQLException {
        conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/seg_pronta", "root", "Mgk62701");
    }



    // SALVAR
    public void salvar(Favorito favorito) throws SQLException {
        String sql = "INSERT INTO favoritos (usuario_id, via_id, data_favorito) VALUES (?, ?, ?)";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setInt(1, favorito.getUsuario_id());
        stmt.setInt(2, favorito.getVia_id());
        stmt.setDate(3, new Date(System.currentTimeMillis()));
        stmt.executeUpdate();
    }

    // BUSCAR POR ID
    public Favorito buscarPorId(int id) throws SQLException {
        String sql = "SELECT * FROM favoritos WHERE id = ?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setInt(1, id);
        ResultSet rs = stmt.executeQuery();

        if (rs.next()) {
            return new Favorito(
                    rs.getInt("id"),
                    rs.getInt("usuario_id"),
                    rs.getInt("via_id"),
                    rs.getDate("data_favorito")
            );
        }
        return null;
    }

    // BUSCAR TODOS
    public List<Favorito> buscarTodos() throws SQLException {
        List<Favorito> favoritos = new ArrayList<>();
        String sql = "SELECT * FROM favoritos";
        PreparedStatement stmt = conn.prepareStatement(sql);
        ResultSet rs = stmt.executeQuery();

        while (rs.next()) {
            Favorito f = new Favorito(
                    rs.getInt("id"),
                    rs.getInt("usuario_id"),
                    rs.getInt("via_id"),
                    rs.getDate("data_favorito")
            );
            favoritos.add(f);
        }

        return favoritos;
    }

    // ATUALIZAR
    public void atualizar(Favorito favorito) throws SQLException {
        String sql = "UPDATE favoritos SET usuario_id = ?, via_id = ?, data_favorito = ? WHERE id = ?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setInt(1, favorito.getUsuario_id());
        stmt.setInt(2, favorito.getVia_id());
        stmt.setDate(3, favorito.getData_favorito());
        stmt.setInt(4, favorito.getId());
        stmt.executeUpdate();
    }

    // DELETAR
    public void deletar(int id) throws SQLException {
        String sql = "DELETE FROM favoritos WHERE id = ?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setInt(1, id);
        stmt.executeUpdate();
    }


    public void deletarPorUsuarioEVia(int usario_id, int via_id) throws SQLException {
        String sql = "DELETE FROM favoritos WHERE usuario_id = ? AND via_id = ?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setInt(1, usario_id);
        stmt.setInt(2, via_id);
        stmt.executeUpdate();
    }

    // BUSCA PELO ID DO USUARIO LOGADO
    public List<Favorito> buscarPorUsuario(int usuarioId) throws SQLException {
        List<Favorito> favoritos = new ArrayList<>();
        String sql = "SELECT * FROM favoritos WHERE usuario_id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, usuarioId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                favoritos.add(new Favorito(
                        rs.getInt("id"),
                        rs.getInt("usuario_id"),
                        rs.getInt("via_id"),
                        rs.getDate("data_favorito")
                ));
            }
        }
        return favoritos;
    }
}
