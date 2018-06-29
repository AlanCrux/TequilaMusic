package presentacion.controladores;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import servicios.CancionSL;
import utilerias.Utilerias;

/**
 * FXML Controller class
 *
 * @author alan
 */
public class NodeCancionListaController implements Initializable {

    @FXML
    private ImageView imgPortada;
    @FXML
    private Label lbTitulo;
    @FXML
    private Label lbAlbum;
    @FXML
    private ImageView imgPortada1;
    
    private CancionSL cancion;
    private IUReproductorController parent; 

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
        imgPortada1.setOnMouseClicked(e->{
            parent.cargarDatosCancion(cancion);
        });
    }    

    public void setCancion(CancionSL cancion) {
        this.cancion = cancion;
    }

    public void setParent(IUReproductorController parent) {
        this.parent = parent;
    }
    
    
    
}
