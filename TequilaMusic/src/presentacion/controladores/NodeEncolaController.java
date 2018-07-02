package presentacion.controladores;


import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import servicios.CancionSL;
import utilerias.Utilerias;

/**
 * FXML Controller class
 *
 * @author alan
 */
public class NodeEncolaController implements Initializable {

    @FXML
    private ImageView imgPortada;
    @FXML
    private TextField lbTitulo;
    @FXML
    private TextField lbAlbum;
    
    private CancionSL cancion; 

    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        lbTitulo.setText(cancion.getTitulo());
        lbAlbum.setText(cancion.getAlbum());
        imgPortada.setImage(Utilerias.byteToImage(cancion.getImagenAlbum()));
    }

    public void setCancion(CancionSL cancion) {
        this.cancion = cancion;
    }  
    
}
