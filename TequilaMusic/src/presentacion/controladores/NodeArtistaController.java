package presentacion.controladores;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import servicios.Usuario;
import utilerias.Utilerias;

/**
 * FXML Controller class
 *
 * @author alan
 */
public class NodeArtistaController implements Initializable {

    @FXML
    private ImageView fotoArtista;
    @FXML
    private Label lbNombre;
    
    IUReproductorController grandparent; 
    ModArtistasController parent; 
    Usuario artista; 

    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        lbNombre.setText(artista.getNombre());
        fotoArtista.setImage(Utilerias.byteToImage(artista.getFoto()));
    }    

    public void setGrandparent(IUReproductorController grandparent) {
        this.grandparent = grandparent;
    }

    public void setParent(ModArtistasController parent) {
        this.parent = parent;
    }

    

    public void setArtista(Usuario artista) {
        this.artista = artista;
    }
    
    @FXML
    public void onArtista(MouseEvent event) {
        parent.cargarCanciones(artista);
    }
    
    
    
}
