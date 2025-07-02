import DAOs.FavoritoDAO;
import DAOs.SetorDAO;
import DAOs.UsuarioDAO;
import DAOs.ViaDAO;
import Models.Favorito;
import Models.Setor;
import Models.Usuario;
import Models.Via;
import javax.swing.*;
import java.sql.Date;
import java.sql.SQLException;
import java.util.List;
import Views.TelaLoginCadastro;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(TelaLoginCadastro::new);
    }
}

