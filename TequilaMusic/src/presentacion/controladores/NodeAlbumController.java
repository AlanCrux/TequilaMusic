/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package presentacion.controladores;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import servicios.AlbumSL;
import servicios.CancionSL;
import servicios.Playlist;
import utilerias.Utilerias;

/**
 * FXML Controller class
 *
 * @author Esmeralda
 */
public class NodeAlbumController implements Initializable {

    @FXML
    private ImageView imgAlbum;
    @FXML
    private Pane paneSombra;
    @FXML
    private TextField tfTituloAlbum;
    @FXML
    private TextField tfAnio;

    AlbumSL album = new AlbumSL();
    IUArtistaController parent;
    
    private ResourceBundle rb;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        tfTituloAlbum.setText(album.getTitulo());
        tfAnio.setText(album.getAnioLanzamiento());
        imgAlbum.setImage(Utilerias.byteToImage(album.getImagenAlbum()));
    }

    @FXML
    void mostrarCanciones(MouseEvent event) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/presentacion/vistas/modCancionesAlbum.fxml"), rb);
        ModCancionesAlbumController controller = new ModCancionesAlbumController();
        controller.setAlbum(album);
        controller.setParent(this);
        controller.setRb(rb);
        loader.setController(controller);

        try {
            parent.getContentPrincipal().getChildren().setAll((AnchorPane) loader.load());
        } catch (IOException ex) {
            Logger.getLogger(IUReproductorController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public AlbumSL getAlbum() {
        return album;
    }

    public void setAlbum(AlbumSL album) {
        this.album = album;
    }

    public IUArtistaController getParent() {
        return parent;
    }

    public void setParent(IUArtistaController parent) {
        this.parent = parent;
    }

    public ResourceBundle getRb() {
        return rb;
    }

    public void setRb(ResourceBundle rb) {
        this.rb = rb;
    }
    
    
}
