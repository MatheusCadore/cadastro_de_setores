package Views;

import Models.Usuario;
import javax.swing.*;
import java.awt.*;

public class TelaPrincipal extends JFrame {
    private Usuario usuarioLogado;

    public TelaPrincipal(Usuario usuarioLogado) {
        this.usuarioLogado = usuarioLogado;

        setTitle("Sistema de Escalada - Bem-vindo " + usuarioLogado.getNome());
        setSize(600, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JTabbedPane abas = new JTabbedPane();
        abas.add("Setores", new PainelSetor(usuarioLogado));
        abas.add("Vias", new PainelVia(usuarioLogado));
        abas.add("Favoritos", new PainelFavoritos(usuarioLogado));

        add(abas);
        setVisible(true);
    }

}
