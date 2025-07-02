package DAOs;

import Models.Setor;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SetorDAO {

    private Connection conn;

    public SetorDAO() throws SQLException {
        conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/seg_pronta", "root", "Mgk62701");
    }

    // 1. SALVAR
    public void salvar(Setor setor) throws SQLException {
        String sql = "INSERT INTO setores (nome, localizacao, descricao, criado_por) VALUES (?, ?, ?, ?)";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1, setor.getNome());
        stmt.setString(2, setor.getLocalizacao());
        stmt.setString(3, setor.getDescricao());
        stmt.setInt(4, setor.getCriado_por());
        stmt.executeUpdate();
    }

    // 2. BUSCAR POR ID
    public Setor buscarPorId(int id) throws SQLException {
        String sql = "SELECT * FROM setores WHERE id = ?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setInt(1, id);
        ResultSet rs = stmt.executeQuery();

        if (rs.next()) {
            return new Setor(
                    rs.getInt("id"),
                    rs.getString("nome"),
                    rs.getString("localizacao"),
                    rs.getString("descricao"),
                    rs.getInt("criado_por")
            );
        }
        return null;
    }

    // 3. BUSCAR TODOS
    public List<Setor> buscarTodos() throws SQLException {
        List<Setor> setores = new ArrayList<>();
        String sql = "SELECT * FROM setores";
        PreparedStatement stmt = conn.prepareStatement(sql);
        ResultSet rs = stmt.executeQuery();

        while (rs.next()) {
            Setor s = new Setor(
                    rs.getInt("id"),
                    rs.getString("nome"),
                    rs.getString("localizacao"),
                    rs.getString("descricao"),
                    rs.getInt("criado_por")
            );
            setores.add(s);
        }

        return setores;
    }

    // 4. ATUALIZAR
    public void atualizar(Setor setor) throws SQLException {
        String sql = "UPDATE setores SET nome = ?, localizacao = ?, descricao = ?, criado_por = ? WHERE id = ?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1, setor.getNome());
        stmt.setString(2, setor.getLocalizacao());
        stmt.setString(3, setor.getDescricao());
        stmt.setInt(4, setor.getCriado_por());
        stmt.setInt(5, setor.getId());
        stmt.executeUpdate();
    }

    // 5. DELETAR
    public void deletar(int id) throws SQLException {
        String sql = "DELETE FROM setores WHERE id = ?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setInt(1, id);
        stmt.executeUpdate();
    }
}


