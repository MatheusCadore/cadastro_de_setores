package Views;

import DAOs.FavoritoDAO;
import DAOs.ViaDAO;
import Models.Favorito;
import Models.Usuario;
import Models.Via;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.Date;
import java.sql.SQLException;
import java.util.*;
import java.util.List;

public class PainelFavoritos extends JPanel {
    private JTable tabelaFavoritos;
    private JComboBox<Via> comboVias;
    private JButton btnFavoritar, btnDeletar;
    private Usuario usuario;

    public PainelFavoritos(Usuario usuario) {
        this.usuario = usuario;
        setLayout(new BorderLayout());


        JPanel topo = new JPanel();
        comboVias = new JComboBox<>();
        btnFavoritar = new JButton("Favoritar");
        topo.add(comboVias);
        topo.add(btnFavoritar);
        add(topo, BorderLayout.NORTH);


        String[] colunas = {"ID", "Nome", "Tipo", "Grau", "Setor"};
        tabelaFavoritos = new JTable(new javax.swing.table.DefaultTableModel(new String[0][0], colunas));
        add(new JScrollPane(tabelaFavoritos), BorderLayout.CENTER);


        JPanel rodape = new JPanel();
        btnDeletar = new JButton("Deletar Favorito Selecionado");
        rodape.add(btnDeletar);
        add(rodape, BorderLayout.SOUTH);

        carregarComboVias();
        atualizarTabela();

        btnFavoritar.addActionListener((ActionEvent e) -> favoritarVia());
        btnDeletar.addActionListener((ActionEvent e) -> deletarFavoritoSelecionado());
    }

    public void carregarComboVias() {
        try {
            comboVias.removeAllItems();
            List<Via> todasVias = new ViaDAO().buscarTodos();
            for (Via via : todasVias) {
                comboVias.addItem(via);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void atualizarTabela() {
        try {
            List<Favorito> favoritos = new FavoritoDAO().buscarPorUsuario(usuario.getId());
            String[][] dados = new String[favoritos.size()][5];

            for (int i = 0; i < favoritos.size(); i++) {
                Via via = new ViaDAO().buscarPorId(favoritos.get(i).getVia_id());
                dados[i][0] = String.valueOf(via.getId());
                dados[i][1] = via.getNome();
                dados[i][2] = via.getTipo();
                dados[i][3] = via.getGrau();
                dados[i][4] = String.valueOf(via.getSetor_id());
            }

            tabelaFavoritos.setModel(new javax.swing.table.DefaultTableModel(dados, new String[]{"ID", "Nome", "Tipo", "Grau", "Setor"}));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void favoritarVia() {
        Via viaSelecionada = (Via) comboVias.getSelectedItem();
        if (viaSelecionada == null) return;

        try {
            List<Favorito> favoritos = new FavoritoDAO().buscarPorUsuario(usuario.getId());
            boolean jaFavoritado = favoritos.stream()
                    .anyMatch(f -> f.getVia_id() == viaSelecionada.getId());

            if (jaFavoritado) {
                JOptionPane.showMessageDialog(this, "Essa via já está nos seus favoritos.");
                return;
            }

            Favorito novo = new Favorito(
                    0,
                    usuario.getId(),
                    viaSelecionada.getId(),
                    new Date(System.currentTimeMillis())
            );

            new FavoritoDAO().salvar(novo);
            atualizarTabela();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void deletarFavoritoSelecionado() {
        int selectedRow = tabelaFavoritos.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Selecione uma via para remover dos favoritos.");
            return;
        }

        int viaId = Integer.parseInt(tabelaFavoritos.getValueAt(selectedRow, 0).toString());

        try {
            new FavoritoDAO().deletarPorUsuarioEVia(usuario.getId(), viaId);
            atualizarTabela();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}