package DAOs;

import Models.Via;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ViaDAO {
    private Connection conn;

    public ViaDAO() throws SQLException {
        conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/seg_pronta", "root", "Mgk62701");
    }

    // 1. SALVAR
    public void salvar(Via via) throws SQLException {
        String sql = "INSERT INTO vias (criado_por, nome, tipo, setor_id, grau) VALUES (?, ?, ?, ?, ?)";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setInt(1, via.getCriado_por());
        stmt.setString(2, via.getNome());
        stmt.setString(3, via.getTipo());
        stmt.setInt(4, via.getSetor_id());
        stmt.setString(5, via.getGrau());
        stmt.executeUpdate();
    }

    // 2. BUSCAR POR ID
    public Via buscarPorId(int id) throws SQLException {
        String sql = "SELECT * FROM vias WHERE id = ?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setInt(1, id);
        ResultSet rs = stmt.executeQuery();

        if (rs.next()) {
            return new Via(
                    rs.getInt("id"),
                    rs.getInt("criado_por"),
                    rs.getString("nome"),
                    rs.getString("tipo"),
                    rs.getInt("setor_id"),
                    rs.getString("grau")
            );
        }

        return null;
    }

    // 3. BUSCAR TODOS
    public List<Via> buscarTodos() throws SQLException {
        List<Via> vias = new ArrayList<>();
        String sql = "SELECT * FROM vias";
        PreparedStatement stmt = conn.prepareStatement(sql);
        ResultSet rs = stmt.executeQuery();

        while (rs.next()) {
            Via v = new Via(
                    rs.getInt("id"),
                    rs.getInt("criado_por"),
                    rs.getString("nome"),
                    rs.getString("tipo"),
                    rs.getInt("setor_id"),
                    rs.getString("grau")
            );
            vias.add(v);
        }

        return vias;
    }

    // 4. ATUALIZAR
    public void atualizar(Via via) throws SQLException {
        String sql = "UPDATE vias SET criado_por = ?, nome = ?, tipo = ?, setor_id = ?, grau = ? WHERE id = ?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setInt(1, via.getCriado_por());
        stmt.setString(2, via.getNome());
        stmt.setString(3, via.getTipo());
        stmt.setInt(4, via.getSetor_id());
        stmt.setString(5, via.getGrau());
        stmt.setInt(6, via.getId());
        stmt.executeUpdate();
    }

    // 5. DELETAR
    public void deletar(int id) throws SQLException {
        String sql = "DELETE FROM vias WHERE id = ?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setInt(1, id);
        stmt.executeUpdate();
    }
}
