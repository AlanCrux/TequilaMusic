package presentacion.controladores;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import servicios.Album;
import servicios.AlbumSL;
import utilerias.Utilerias;

/**
 * FXML Controller class
 *
 * @author alan
 */
public class NodeAlbumBibliotecaController implements Initializable {

    @FXML
    private ImageView imgAlbum;
    @FXML
    private Pane paneSombra;
    @FXML
    private TextField tfTituloAlbum;
    @FXML
    private TextField tfAnio;
    @FXML
    private TextField tfDiscografica;
    
    AlbumSL album; 

    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       imgAlbum.setImage(Utilerias.byteToImage(album.getImagenAlbum()));
       tfTituloAlbum.setText(album.getTitulo());
       tfAnio.setText(album.getAnioLanzamiento());
       tfDiscografica.setText(album.getCompaniaDiscografica());
    }    

    @FXML
    private void on(MouseEvent event) {
    }

    @FXML
    private void onSombrear(MouseEvent event) {
    }

    @FXML
    private void mostrarCanciones(MouseEvent event) {
    }

    public void setAlbum(AlbumSL album) {
        this.album = album;
    }
    
    
    
}
