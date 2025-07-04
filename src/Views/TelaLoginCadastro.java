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
        tabs.add("Cadastro", criarPainelCadastro(tabs));

        add(tabs);
        setVisible(true);
    }

    private JPanel criarPainelLogin() {
        JPanel painelPrincipal = new JPanel();
        painelPrincipal.setLayout(new BorderLayout());
        painelPrincipal.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));

        // Campos com GridLayout
        JPanel campos = new JPanel(new GridLayout(2, 2, 10, 10));
        JLabel lblEmail = new JLabel("Email:");
        JTextField campoEmail = new JTextField();
        JLabel lblSenha = new JLabel("Senha:");
        JPasswordField campoSenha = new JPasswordField();
        campos.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));


        campos.add(lblEmail);
        campos.add(campoEmail);
        campos.add(lblSenha);
        campos.add(campoSenha);


        JPanel botoes = new JPanel();
        botoes.setLayout(new BoxLayout(botoes, BoxLayout.Y_AXIS));
        JButton btnLogin = new JButton("Entrar");
        btnLogin.setAlignmentX(Component.CENTER_ALIGNMENT);
        botoes.add(Box.createVerticalStrut(20));
        botoes.add(btnLogin);

        painelPrincipal.add(campos, BorderLayout.CENTER);
        painelPrincipal.add(botoes, BorderLayout.SOUTH);


        btnLogin.addActionListener((ActionEvent e) -> {
            try {
                UsuarioDAO dao = new UsuarioDAO();
                Usuario u = dao.buscarPorEmail(campoEmail.getText());
                if (u != null && u.getSenha().equals(new String(campoSenha.getPassword()))) {
                    JOptionPane.showMessageDialog(this, "Bem-vindo, " + u.getNome() + "!");
                    dispose();
                    new TelaPrincipal(u);
                } else {
                    JOptionPane.showMessageDialog(this, "Email ou senha inválidos.");
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Erro ao acessar o banco.");
            }
        });

        return painelPrincipal;
    }


    private JPanel criarPainelCadastro(JTabbedPane abas) {
        JPanel painelPrincipal = new JPanel();
        painelPrincipal.setLayout(new BorderLayout());
        painelPrincipal.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));

        // Campos com GridLayout
        JPanel campos = new JPanel(new GridLayout(3, 2, 10, 10));
        JLabel lblNome = new JLabel("Nome:");
        JTextField campoNome = new JTextField();
        JLabel lblEmail = new JLabel("Email:");
        JTextField campoEmail = new JTextField();
        JLabel lblSenha = new JLabel("Senha:");
        JPasswordField campoSenha = new JPasswordField();

        campos.add(lblNome);
        campos.add(campoNome);
        campos.add(lblEmail);
        campos.add(campoEmail);
        campos.add(lblSenha);
        campos.add(campoSenha);


        JPanel botoes = new JPanel();
        botoes.setLayout(new BoxLayout(botoes, BoxLayout.Y_AXIS));
        JButton btnCadastrar = new JButton("Cadastrar");
        btnCadastrar.setAlignmentX(Component.CENTER_ALIGNMENT);
        botoes.add(Box.createVerticalStrut(20));
        botoes.add(btnCadastrar);

        painelPrincipal.add(campos, BorderLayout.CENTER);
        painelPrincipal.add(botoes, BorderLayout.SOUTH);


        btnCadastrar.addActionListener((ActionEvent e) -> {
            String nomeTexto = campoNome.getText().trim();
            String emailTexto = campoEmail.getText().trim();
            String senhaTexto = new String(campoSenha.getPassword()).trim();

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
                campoNome.setText("");
                campoEmail.setText("");
                campoSenha.setText("");
                abas.setSelectedIndex(0);
            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Erro ao cadastrar.");
            }
        });

        return painelPrincipal;
    }

}
