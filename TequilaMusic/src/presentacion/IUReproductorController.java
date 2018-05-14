package presentacion;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Alan Yoset Garcia Cruz
 */
public class IUReproductorController implements Initializable {

    @FXML
    private TextField tfBuscar;
    @FXML
    private Slider sliderVolumen;
    @FXML
    private SplitPane splitPane;
    @FXML
    private AnchorPane paneContenido;
    @FXML
    private ImageView btnAleatorio;
    @FXML
    private ImageView btnAnterior;
    @FXML
    private ImageView btnPlay;
    @FXML
    private ImageView btnSiguiente;
    @FXML
    private ImageView btnRepetir;
    @FXML
    private JFXListView<Label> listOpciones;
    @FXML
    private JFXListView<Label> lstPlaylists;
    @FXML
    private JFXButton btnBiblioteca;
    @FXML
    private JFXButton btnRadio;
    @FXML
    private JFXButton btnExplorar;

    private boolean play = true; 
    private static final String ICON_CANCIONES = "src/recursos/iconos/maracas.png";
    private static final String ICON_ARTISTAS = "src/recursos/iconos/mexicano.png";
    private static final String ICON_ALBUMES = "src/recursos/iconos/guitarra.png";
    private static final String ICON_PAUSE = "/recursos/iconos/icon_pausa.png";
    private static final String ICON_PLAY = "/recursos/iconos/icon_play.png";
    private static final String ICON_PLAYLIST = "src/recursos/iconos/icon_playlist.png";

    /**
     * Inicializa los componentes de la ventana IUReproductor.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        agregarOpcionesBiblioteca();
        agregarPlaylists();
        btnBiblioteca.requestFocus();
    }

    @FXML
    void onActionBack(MouseEvent event) {

    }

    @FXML
    void onActionBiblioteca(ActionEvent event) {

    }

    @FXML
    void onActionBuscar(KeyEvent event) {

    }

    @FXML
    void onActionNext(MouseEvent event) {

    }

    @FXML
    void onActionPlay(MouseEvent event) {
        if (play) {
            btnPlay.setImage(new Image(ICON_PAUSE));
            play = false; 
        }else{
            btnPlay.setImage(new Image(ICON_PLAY));
            play = true;
        }  
    }

    @FXML
    void onActionRadio(ActionEvent event) {

    }

    @FXML
    void onActionRandom(MouseEvent event) {

    }

    @FXML
    void onActionRepeat(MouseEvent event) {

    }

    @FXML
    void onActionTimeline(MouseEvent event) {

    }

    @FXML
    void onActionVolume(MouseEvent event) {

    }

    private void agregarOpcionesBiblioteca() {
        Label lbCanciones = new Label("Canciones");
        Label lbArtistas = new Label("Artistas");
        Label lbAlbumes = new Label("Álbumes");
        try {
            lbCanciones.setGraphic(new ImageView(new Image(new FileInputStream(ICON_CANCIONES))));
            lbArtistas.setGraphic(new ImageView(new Image(new FileInputStream(ICON_ARTISTAS))));
            lbAlbumes.setGraphic(new ImageView(new Image(new FileInputStream(ICON_ALBUMES))));

            lbCanciones.setFont(new Font("Avenir Book", 14));
            lbArtistas.setFont(new Font("Avenir Book", 14));
            lbAlbumes.setFont(new Font("Avenir Book", 14));

            listOpciones.getItems().add(lbCanciones);
            listOpciones.getItems().add(lbArtistas);
            listOpciones.getItems().add(lbAlbumes);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(IUReproductorController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void agregarPlaylists() {
        Label lbCanciones = new Label("Carne asada");
        Label lbArtistas = new Label("Estudiar");
        Label lbAlbumes = new Label("Baño de burbujas");
        try {
            lbCanciones.setGraphic(new ImageView(new Image(new FileInputStream(ICON_PLAYLIST))));
            lbArtistas.setGraphic(new ImageView(new Image(new FileInputStream(ICON_PLAYLIST))));
            lbAlbumes.setGraphic(new ImageView(new Image(new FileInputStream(ICON_PLAYLIST))));

            lbCanciones.setFont(new Font("Avenir Book", 14));
            lbArtistas.setFont(new Font("Avenir Book", 14));
            lbAlbumes.setFont(new Font("Avenir Book", 14));

            lstPlaylists.getItems().add(lbCanciones);
            lstPlaylists.getItems().add(lbArtistas);
            lstPlaylists.getItems().add(lbAlbumes);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(IUReproductorController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Muestra la ventana.
     *
     * @param loader el loader con la ruta de la ventana que se quiere cargar.
     */
    public static void mostrarVentana(FXMLLoader loader) {
        try {
            Stage stagePrincipal = new Stage();
            Parent root = (Parent) loader.load();
            Scene scene = new Scene(root);
            stagePrincipal.setScene(scene);
            stagePrincipal.show();
        } catch (IOException ex) {
            Logger.getLogger(IUReproductorController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
