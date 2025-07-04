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

        PainelSetor painelSetor = new PainelSetor(usuarioLogado);
        PainelVia painelVia = new PainelVia(usuarioLogado);
        PainelFavoritos painelFavoritos = new PainelFavoritos(usuarioLogado);

        JTabbedPane abas = new JTabbedPane();
        abas.add("Setores", painelSetor);
        abas.add("Vias", painelVia);
        abas.add("Favoritos", painelFavoritos);

        abas.addChangeListener(e ->{
            int abaSelecionada = abas.getSelectedIndex();
            String titulo = abas.getTitleAt(abaSelecionada);
            if (titulo.equals("Favoritos")) {
                painelFavoritos.atualizarTabela();
                painelFavoritos.carregarComboVias();
            }
            if (titulo.equals("Vias")) {
                painelVia.atualizarTabela();
                painelVia.carregarSetores();
            }
        });

        add(abas);
        setVisible(true);
    }

}
