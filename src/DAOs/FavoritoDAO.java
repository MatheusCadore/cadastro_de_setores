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

    // 1. SALVAR
    public void salvar(Favorito favorito) throws SQLException {
        String sql = "INSERT INTO favoritos (usuario_id, via_id, data_favorito) VALUES (?, ?, ?)";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setInt(1, favorito.getUsuario_id());
        stmt.setInt(2, favorito.getVia_id());
        stmt.setDate(3, new Date(System.currentTimeMillis()));
        stmt.executeUpdate();
    }

    // 2. BUSCAR POR ID
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

    // 3. BUSCAR TODOS
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

    // 4. ATUALIZAR
    public void atualizar(Favorito favorito) throws SQLException {
        String sql = "UPDATE favoritos SET usuario_id = ?, via_id = ?, data_favorito = ? WHERE id = ?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setInt(1, favorito.getUsuario_id());
        stmt.setInt(2, favorito.getVia_id());
        stmt.setDate(3, favorito.getData_favorito());
        stmt.setInt(4, favorito.getId());
        stmt.executeUpdate();
    }

    // 5. DELETAR
    public void deletar(int id) throws SQLException {
        String sql = "DELETE FROM favoritos WHERE id = ?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setInt(1, id);
        stmt.executeUpdate();
    }


    public void deletarPorUsuarioEVia(int id, int id1) {
    }
}
