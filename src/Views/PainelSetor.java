package Views;

import DAOs.SetorDAO;
import Models.Setor;
import Models.Usuario;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.SQLException;
import java.util.List;

class PainelSetor extends JPanel {
    private JTable tabela;
    private DefaultListModel<Setor> modeloTabela;
    private JTextField nome, local, desc;
    private int setorSelecionadoId = -1;

    public PainelSetor(Usuario usuario) {
        setLayout(new BorderLayout());

        // Tabela de setores
        String[] colunas = {"ID", "Nome", "Localização", "Descrição"};
        String[][] dados = {};
        tabela = new JTable(new javax.swing.table.DefaultTableModel(dados, colunas));
        JScrollPane scrollPane = new JScrollPane(tabela);
        add(scrollPane, BorderLayout.CENTER);

        tabela.getSelectionModel().addListSelectionListener(e -> {
            int selectedRow = tabela.getSelectedRow();
            if (selectedRow >= 0) {
                setorSelecionadoId = Integer.parseInt(tabela.getValueAt(selectedRow, 0).toString());
                nome.setText(tabela.getValueAt(selectedRow, 1).toString());
                local.setText(tabela.getValueAt(selectedRow, 2).toString());
                desc.setText(tabela.getValueAt(selectedRow, 3).toString());
            }
        });

        // Formulário e botões
        JPanel formulario = new JPanel(new GridLayout(4, 2));
        nome = new JTextField();
        local = new JTextField();
        desc = new JTextField();

        formulario.add(new JLabel("Nome:")); formulario.add(nome);
        formulario.add(new JLabel("Localização:")); formulario.add(local);
        formulario.add(new JLabel("Descrição:")); formulario.add(desc);

        add(formulario, BorderLayout.NORTH);

        JPanel botoes = new JPanel();
        JButton btnSalvar = new JButton("Salvar");
        JButton btnEditar = new JButton("Editar");
        JButton btnDeletar = new JButton("Deletar");

        botoes.add(btnSalvar);
        botoes.add(btnEditar);
        botoes.add(btnDeletar);

        add(botoes, BorderLayout.SOUTH);

        btnSalvar.addActionListener((ActionEvent e) -> {

            String nomeTexto = nome.getText().trim();
            String localTexto = local.getText().trim();
            String descTexto = desc.getText().trim();

            if (nomeTexto.isEmpty() || localTexto.isEmpty() || descTexto.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Todos os campos devem ser preenchidos!", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }

            try {
                Setor setor = new Setor(0, nome.getText(), local.getText(), desc.getText(), usuario.getId());
                new SetorDAO().salvar(setor);
                limparCampos();
                atualizarTabelaSetores();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        });

        btnEditar.addActionListener((ActionEvent e) -> {
            try {
                if (setorSelecionadoId != -1) {
                    Setor setor = new Setor(setorSelecionadoId, nome.getText(), local.getText(), desc.getText(), usuario.getId());
                    new SetorDAO().atualizar(setor);
                    limparCampos();
                    atualizarTabelaSetores();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        });

        btnDeletar.addActionListener((ActionEvent e) -> {
            try {
                if (setorSelecionadoId != -1) {
                    new SetorDAO().deletar(setorSelecionadoId);
                    limparCampos();
                    atualizarTabelaSetores();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        });

        atualizarTabelaSetores();
    }

    private void atualizarTabelaSetores() {
        try {
            List<Setor> setores = new SetorDAO().buscarTodos();
            String[][] dados = new String[setores.size()][4];
            for (int i = 0; i < setores.size(); i++) {
                Setor s = setores.get(i);
                dados[i][0] = String.valueOf(s.getId());
                dados[i][1] = s.getNome();
                dados[i][2] = s.getLocalizacao();
                dados[i][3] = s.getDescricao();
            }
            String[] colunas = {"ID", "Nome", "Localização", "Descrição"};
            tabela.setModel(new javax.swing.table.DefaultTableModel(dados, colunas));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void limparCampos() {
        nome.setText("");
        local.setText("");
        desc.setText("");
        setorSelecionadoId = -1;
    }
}
