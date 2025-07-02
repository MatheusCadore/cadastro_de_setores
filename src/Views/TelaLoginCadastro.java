package Views;

import DAOs.UsuarioDAO;
import Models.Usuario;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.Date;
import java.sql.SQLException;

public class TelaLoginCadastro extends JFrame {

    private JTabbedPane tabs = new JTabbedPane();

    public TelaLoginCadastro() {
        setTitle("Login / Cadastro");
        setSize(400, 300);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        tabs.add("Login", criarPainelLogin());
        tabs.add("Cadastro", criarPainelCadastro());

        add(tabs);
        setVisible(true);
    }

    private JPanel criarPainelLogin() {
        JPanel panel = new JPanel(new GridLayout(4, 1));
        JTextField email = new JTextField();
        JPasswordField senha = new JPasswordField();
        JButton btnLogin = new JButton("Entrar");

        panel.add(new JLabel("Email:"));
        panel.add(email);
        panel.add(new JLabel("Senha:"));
        panel.add(senha);
        panel.add(btnLogin);

        btnLogin.addActionListener((ActionEvent e) -> {
            try {
                UsuarioDAO dao = new UsuarioDAO();

                Usuario u = dao.buscarPorEmail(email.getText());
                if (u != null && u.getSenha().equals(new String(senha.getPassword()))) {
                    JOptionPane.showMessageDialog(this, "Bem-vindo, " + u.getNome() + "!");
                    dispose();
                    new TelaPrincipal(u); // passa o usuário logado
                } else {
                    JOptionPane.showMessageDialog(this, "Email ou senha inválidos.");
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Erro ao acessar o banco.");
            }
        });

        return panel;
    }

    private JPanel criarPainelCadastro() {
        JPanel panel = new JPanel(new GridLayout(6, 1));
        JTextField nome = new JTextField();
        JTextField email = new JTextField();
        JPasswordField senha = new JPasswordField();
        JButton btnCadastrar = new JButton("Cadastrar");

        panel.add(new JLabel("Nome:"));
        panel.add(nome);
        panel.add(new JLabel("Email:"));
        panel.add(email);
        panel.add(new JLabel("Senha:"));
        panel.add(senha);
        panel.add(btnCadastrar);

        btnCadastrar.addActionListener((ActionEvent e) -> {

            String nomeTexto = nome.getText().trim();
            String emailTexto = email.getText().trim();
            String senhaTexto = new String(senha.getPassword()).trim();

            if (nomeTexto.isEmpty() || emailTexto.isEmpty() || senhaTexto.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Todos os campos devem ser preenchidos!", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }

            try {
                Usuario novo = new Usuario();
                novo.setNome(nomeTexto);
                novo.setEmail(emailTexto);
                novo.setSenha(senhaTexto);
                novo.setDt_criacao(new Date(System.currentTimeMillis()));

                UsuarioDAO dao = new UsuarioDAO();
                dao.salvar(novo);

                JOptionPane.showMessageDialog(this, "Cadastro realizado com sucesso!");
                nome.setText("");
                email.setText("");
                senha.setText("");
            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Erro ao cadastrar.");
            }
        });

        return panel;

    }
}
