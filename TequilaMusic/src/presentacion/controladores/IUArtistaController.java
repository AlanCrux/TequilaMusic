package presentacion.controladores;

import com.jfoenix.controls.JFXListView;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import servicios.Usuario;
import servicios.servicios;
import servicios.servicios.Client;

/**
 * FXML Controller class
 *
 * @author alanc
 */
public class IUArtistaController implements Initializable {
    @FXML
    private ImageView imgPerfil;
    @FXML
    private Label lblArtista;
    @FXML
    private SplitPane splitPane;
    @FXML
    private JFXListView<?> listOpciones;
    @FXML
    private AnchorPane contentPrincipal;
    @FXML
    private TableView<?> tbCanciones;
    @FXML
    private TableColumn<?, ?> tbcTitulo;
    @FXML
    private TableColumn<?, ?> tbcArtista;
    @FXML
    private TableColumn<?, ?> tbcAlbum;
    @FXML
    private TableColumn<?, ?> tbcDuracion;
    Client sevidor; 
    Usuario usuario; 

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        lblArtista.setText(usuario.getNombre());
    }    

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario; 
    }

    public void setServidor(servicios.Client servidor) {
        this.sevidor = servidor; 
    }
    
}
