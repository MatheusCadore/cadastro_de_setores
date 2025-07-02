package Views;

import DAOs.FavoritoDAO;
import DAOs.ViaDAO;
import Models.Favorito;
import Models.Usuario;
import Models.Via;

import javax.swing.*;
import java.awt.*;
import java.sql.Date;
import java.sql.SQLException;
import java.util.*;
import java.util.List;

public class PainelFavoritos extends JPanel {
    private JPanel listaPanel;

    public PainelFavoritos(Usuario usuario) {
        setLayout(new BorderLayout());
        listaPanel = new JPanel();
        listaPanel.setLayout(new BoxLayout(listaPanel, BoxLayout.Y_AXIS));
        add(new JScrollPane(listaPanel), BorderLayout.CENTER);

        carregarFavoritos(usuario);
    }

    private void carregarFavoritos(Usuario usuario) {
        listaPanel.removeAll();
        try {
            List<Via> todasVias = new ViaDAO().buscarTodos();
            List<Favorito> favoritos = new FavoritoDAO().buscarTodos();

            for (Via via : todasVias) {
                JCheckBox check = new JCheckBox(via.getNome());
                boolean jaEhFav = favoritos.stream()
                        .anyMatch(f -> f.getUsuario_id() == usuario.getId() && f.getVia_id() == via.getId());
                check.setSelected(jaEhFav);

                check.addActionListener(e -> {
                    try {
                        FavoritoDAO dao = new FavoritoDAO();
                        if (check.isSelected()) {
                            Favorito f = new Favorito(0, usuario.getId(), via.getId(), new Date(System.currentTimeMillis()));
                            dao.salvar(f);
                        } else {
                            dao.deletarPorUsuarioEVia(usuario.getId(), via.getId());
                        }
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }
                });

                listaPanel.add(check);
            }
            revalidate();
            repaint();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
