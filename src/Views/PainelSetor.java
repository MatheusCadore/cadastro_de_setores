package Views;

import DAOs.SetorDAO;
import Models.Setor;
import Models.Usuario;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.SQLException;
import java.util.List;

public class PainelSetor extends JPanel {
    private JTextArea lista;
    private JTextField idDeletar;

    public PainelSetor(Usuario usuario) {
        setLayout(new BorderLayout());

        JPanel form = new JPanel(new GridLayout(6, 2));
        JTextField nome = new JTextField();
        JTextField local = new JTextField();
        JTextField desc = new JTextField();
        JButton btnSalvar = new JButton("Salvar Setor");
        idDeletar = new JTextField();
        JButton btnDeletar = new JButton("Deletar por ID");

        form.add(new JLabel("Nome:")); form.add(nome);
        form.add(new JLabel("Localização:")); form.add(local);
        form.add(new JLabel("Descrição:")); form.add(desc);

        form.add(btnSalvar); form.add(new JLabel());
        form.add(new JLabel("ID para deletar:")); form.add(idDeletar);
        form.add(btnDeletar); form.add(new JLabel());


        add(form, BorderLayout.NORTH);


        lista = new JTextArea();
        lista.setEditable(false);
        add(new JScrollPane(lista), BorderLayout.CENTER);

        btnSalvar.addActionListener((ActionEvent e) -> {

            String nomeTexto = nome.getText().trim();
            String locTexto = local.getText().trim();
            String descTexto = desc.getText().trim();

            if (nomeTexto.isEmpty() || locTexto.isEmpty() || descTexto.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Todos os campos devem ser preenchidos!", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }

            try {
                Setor setor = new Setor(0, nome.getText(), local.getText(), desc.getText(), usuario.getId());
                new SetorDAO().salvar(setor);
                nome.setText("");
                local.setText("");
                desc.setText("");
                atualizarListaSetores();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        });

        btnDeletar.addActionListener((ActionEvent e) -> {

            String idTexto = idDeletar.getText().trim();

            if (idTexto.isEmpty()){
                JOptionPane.showMessageDialog(this,"Id do setor deve ser preenchido!","erro",JOptionPane.ERROR_MESSAGE);
                return;
            }

            try {
                int id = Integer.parseInt(idDeletar.getText());
                new SetorDAO().deletar(id);
                idDeletar.setText("");
                atualizarListaSetores();
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this,ex.getMessage(),"erro",JOptionPane.ERROR_MESSAGE);
            }
        });

        atualizarListaSetores();
    }

    private void atualizarListaSetores() {
        try {
            List<Setor> setores = new SetorDAO().buscarTodos();
            StringBuilder sb = new StringBuilder();
            for (Setor s : setores) {
                sb.append("ID: ").append(s.getId())
                        .append(" | Nome: ").append(s.getNome())
                        .append(" | Local: ").append(s.getLocalizacao())
                        .append(" | Descricao: ").append(s.getDescricao())
                        .append("\n");
            }
            lista.setText(sb.toString());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
