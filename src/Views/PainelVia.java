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

public class PainelVia extends JPanel {
    private JTextArea lista;

    public PainelVia(Usuario usuario) {
        setLayout(new BorderLayout());

        JPanel form = new JPanel(new GridLayout(7, 2));
        JTextField nome = new JTextField();
        JTextField tipo = new JTextField();
        JTextField grau = new JTextField();
        JTextField setorId = new JTextField();
        JButton btnSalvar = new JButton("Salvar Via");
        JTextField idDeletar = new JTextField();
        JButton btnDeletar = new JButton("Deletar por ID");

        form.add(new JLabel("Nome:")); form.add(nome);
        form.add(new JLabel("Tipo:")); form.add(tipo);
        form.add(new JLabel("Grau:")); form.add(grau);
        form.add(new JLabel("Setor ID:")); form.add(setorId);
        form.add(btnSalvar); form.add(new JLabel());
        form.add(new JLabel("ID para deletar:")); form.add(idDeletar);
        form.add(btnDeletar); form.add(new JLabel());


        add(form, BorderLayout.NORTH);

        lista = new JTextArea();
        lista.setEditable(false);
        add(new JScrollPane(lista), BorderLayout.CENTER);

        btnSalvar.addActionListener((ActionEvent e) -> {

            String nomeTexto = nome.getText().trim();
            String tipoTexto = nome.getText().trim();
            String grauTexto = nome.getText().trim();
            String setorIdTexto = nome.getText().trim();

            if (nomeTexto.isEmpty() || tipoTexto.isEmpty() || grauTexto.isEmpty() || setorIdTexto.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Todos os campos devem ser preenchidos!", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }

            try {
                Via via = new Via(0, usuario.getId(), nome.getText(), tipo.getText(), Integer.parseInt(setorId.getText()), grau.getText());
                new ViaDAO().salvar(via);
                lista.append("Via cadastrada: " + via.getNome() + "\n");
                nome.setText(""); tipo.setText(""); grau.setText(""); setorId.setText("");
            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
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
                new ViaDAO().deletar(id);
                idDeletar.setText("");
                atualizarListaVias();
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this,ex.getMessage(),"erro",JOptionPane.ERROR_MESSAGE);
            }
        });
        atualizarListaVias();
    }

    private void atualizarListaVias() {
        try {
            SetorDAO dao = new SetorDAO();
            List<Via> vias = new ViaDAO().buscarTodos();
            StringBuilder sb = new StringBuilder();
            for (Via v : vias) {
                sb.append("ID: ").append(v.getId())
                        .append(" | Nome: ").append(v.getNome())
                        .append(" | Tipo: ").append(v.getTipo())
                        .append(" | Grua: ").append(v.getGrau())
                        .append(" | Setor: ").append(dao.buscarPorId(v.getSetor_id()).getNome())
                        .append("\n");
            }
            lista.setText(sb.toString());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
