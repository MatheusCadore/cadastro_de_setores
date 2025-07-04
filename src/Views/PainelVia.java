package Views;

import DAOs.SetorDAO;
import DAOs.ViaDAO;
import Models.Setor;
import Models.Usuario;
import Models.Via;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;

class PainelVia extends JPanel {
    private JTable tabela;
    private JTextField nome;
    private JComboBox<String> tipoCombo, grauCombo;
    private JComboBox<Setor> setorCombo;
    private int viaSelecionadaId = -1;

    private static final String[] TIPOS = {"Boulder", "Esportiva", "Trad", "Mista"};
    private static final String[] GRAUS = {
            "4", "5", "5sup", "6a", "6b", "6c", "7a", "7b", "7c",
            "8a", "8b", "8c", "9a", "9b", "9c",
            "10a", "10b", "10c", "11a", "11b", "11c",
            "12a", "12b", "12c"
    };

    public PainelVia(Usuario usuario) {
        setLayout(new BorderLayout());

        String[] colunas = {"ID", "Nome", "Tipo", "Grau", "Setor ID"};
        tabela = new JTable(new javax.swing.table.DefaultTableModel(new String[0][0], colunas));
        JScrollPane scrollPane = new JScrollPane(tabela);
        add(scrollPane, BorderLayout.CENTER);

        tabela.getSelectionModel().addListSelectionListener(e -> {
            int selectedRow = tabela.getSelectedRow();
            if (selectedRow >= 0) {
                viaSelecionadaId = Integer.parseInt(tabela.getValueAt(selectedRow, 0).toString());
                nome.setText(tabela.getValueAt(selectedRow, 1).toString());
                tipoCombo.setSelectedItem(tabela.getValueAt(selectedRow, 2).toString());
                grauCombo.setSelectedItem(tabela.getValueAt(selectedRow, 3).toString());

                int setorIdSelecionado = Integer.parseInt(tabela.getValueAt(selectedRow, 4).toString());
                for (int i = 0; i < setorCombo.getItemCount(); i++) {
                    if (setorCombo.getItemAt(i).getId() == setorIdSelecionado) {
                        setorCombo.setSelectedIndex(i);
                        break;
                    }
                }
            }
        });

        JPanel form = new JPanel(new GridLayout(4, 2));
        nome = new JTextField();
        tipoCombo = new JComboBox<>(TIPOS);
        grauCombo = new JComboBox<>(GRAUS);
        setorCombo = new JComboBox<>();
        carregarSetores();

        form.add(new JLabel("Nome:")); form.add(nome);
        form.add(new JLabel("Tipo:")); form.add(tipoCombo);
        form.add(new JLabel("Grau:")); form.add(grauCombo);
        form.add(new JLabel("Setor:")); form.add(setorCombo);
        add(form, BorderLayout.NORTH);

        JPanel botoes = new JPanel();
        JButton btnSalvar = new JButton("Salvar");
        JButton btnEditar = new JButton("Editar");
        JButton btnDeletar = new JButton("Deletar");
        botoes.add(btnSalvar);
        botoes.add(btnEditar);
        botoes.add(btnDeletar);
        add(botoes, BorderLayout.SOUTH);

        btnSalvar.addActionListener((ActionEvent e) -> {
            try {
                Setor setorSelecionado = (Setor) setorCombo.getSelectedItem();
                if (setorSelecionado == null) return;

                Via via = new Via(
                        0, usuario.getId(),
                        nome.getText(),
                        tipoCombo.getSelectedItem().toString(),
                        setorSelecionado.getId(),
                        grauCombo.getSelectedItem().toString()
                );
                new ViaDAO().salvar(via);
                limpar();
                atualizarTabela();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        });

        btnEditar.addActionListener((ActionEvent e) -> {
            try {
                if (viaSelecionadaId != -1) {
                    Setor setorSelecionado = (Setor) setorCombo.getSelectedItem();
                    if (setorSelecionado == null) return;

                    Via via = new Via(
                            viaSelecionadaId, usuario.getId(),
                            nome.getText(),
                            tipoCombo.getSelectedItem().toString(),
                            setorSelecionado.getId(),
                            grauCombo.getSelectedItem().toString()
                    );
                    new ViaDAO().atualizar(via);
                    limpar();
                    atualizarTabela();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        });

        btnDeletar.addActionListener((ActionEvent e) -> {
            try {
                if (viaSelecionadaId != -1) {
                    new ViaDAO().deletar(viaSelecionadaId);
                    limpar();
                    atualizarTabela();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        });

        atualizarTabela();
    }

    public void atualizarTabela() {
        try {
            List<Via> vias = new ViaDAO().buscarTodos();
            String[][] dados = new String[vias.size()][5];
            for (int i = 0; i < vias.size(); i++) {
                Via v = vias.get(i);
                dados[i][0] = String.valueOf(v.getId());
                dados[i][1] = v.getNome();
                dados[i][2] = v.getTipo();
                dados[i][3] = v.getGrau();
                dados[i][4] = String.valueOf(v.getSetor_id());
            }
            tabela.setModel(new javax.swing.table.DefaultTableModel(dados, new String[]{"ID", "Nome", "Tipo", "Grau", "Setor ID"}));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void carregarSetores() {
        try {
            List<Setor> setores = new SetorDAO().buscarTodos();
            setorCombo.removeAllItems();
            for (Setor s : setores) {
                setorCombo.addItem(s);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void limpar() {
        nome.setText("");
        tipoCombo.setSelectedIndex(0);
        grauCombo.setSelectedIndex(0);
        setorCombo.setSelectedIndex(0);
        viaSelecionadaId = -1;
    }
}
