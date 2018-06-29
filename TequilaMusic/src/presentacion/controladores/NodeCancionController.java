package presentacion.controladores;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import servicios.CancionSL;
import utilerias.Utilerias;

/**
 * FXML Controller class
 *
 * @author Alan Yoset Garcia Cruz
 */
public class NodeCancionController implements Initializable {

    @FXML
    private ImageView imgAlbum;
    @FXML
    private Pane paneSombra;
    @FXML
    private ImageView btnPlay;
    @FXML
    private TextField tfTitulo;
    @FXML
    private TextField tfArtista;
    
    private CancionSL cancion;
    private IUReproductorController parent; 

    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        tfTitulo.setText(cancion.getTitulo());
        tfArtista.setText(cancion.getArtista());
        imgAlbum.setImage(Utilerias.byteToImage(cancion.getImagenAlbum()));
    }    

    @FXML
    private void ocultarPlay(MouseEvent event) {
        paneSombra.setOpacity(0);
        btnPlay.setOpacity(0);
    }

    @FXML
    private void mostrarPlay(MouseEvent event) {
        paneSombra.setOpacity(0.5);
        btnPlay.setOpacity(0.6);
    }
    
    @FXML
    public void reproducir(MouseEvent event) {
        parent.cargarDatosCancion(cancion);
    }

    public void setCancion(CancionSL cancion) {
        this.cancion = cancion;
        
    }

    public CancionSL getCancion() {
        return cancion;
    }

    public void setParent(IUReproductorController parent) {
        this.parent = parent;
    }
    
    @FXML
    public void disposePlay(MouseEvent event) {
        btnPlay.setOpacity(0.6);
    }
    
    @FXML
    public void showPlay(MouseEvent event) {
        paneSombra.setOpacity(0.5);
        btnPlay.setOpacity(1);
    }
    
    
    
}
