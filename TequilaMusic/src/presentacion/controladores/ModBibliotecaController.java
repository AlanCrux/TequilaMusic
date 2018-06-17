package presentacion.controladores;

import com.jfoenix.controls.JFXListView;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;

/**
 * FXML Controller class
 *
 * @author Alan Yoset Garcia Cruz
 */
public class ModBibliotecaController implements Initializable {

    @FXML
    private SplitPane splitPane;
    @FXML
    private JFXListView<?> listOpciones;
    @FXML
    private AnchorPane contentPrincipal;
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
    private ImageView imgDisco;
    @FXML
    private Label lbNombreCancion;
    @FXML
    private Label lbArtistas;
    @FXML
    private ImageView imgDetallesDisco;

    private boolean play = true;
    private static final String ICON_CANCIONES = "src/recursos/iconos/maracas.png";
    private static final String ICON_ARTISTAS = "src/recursos/iconos/mexicano.png";
    private static final String ICON_ALBUMES = "src/recursos/iconos/guitarra.png";
    private static final String ICON_PAUSE = "/recursos/iconos/icon_pausa.png";
    private static final String ICON_PLAY = "/recursos/iconos/icon_play.png";
    private static final String ICON_PLAYLIST = "src/recursos/iconos/icon_playlist.png";

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        agregarOpcionesBiblioteca();
        agregarPlaylists();

        tbcTitulo.setCellValueFactory(new PropertyValueFactory<>("titulo"));
        tbcArtista.setCellValueFactory(new PropertyValueFactory<>("artista"));
        tbcAlbum.setCellValueFactory(new PropertyValueFactory<>("album"));
        tbcDuracion.setCellValueFactory(new PropertyValueFactory<>("duracion"));
    }

    @FXML
    private void onActionRandom(MouseEvent event) {
    }

    @FXML
    private void onActionBack(MouseEvent event) {
    }

    @FXML
    private void onActionPlay(MouseEvent event) {
        if (play) {
            btnPlay.setImage(new Image(ICON_PAUSE));
            play = false;
        } else {
            btnPlay.setImage(new Image(ICON_PLAY));
            play = true;
        }
    }

    @FXML
    private void onActionNext(MouseEvent event) {
    }

    @FXML
    private void onActionRepeat(MouseEvent event) {
    }

    @FXML
    private void ocultarDetallesDisco(MouseEvent event) {
    }

    @FXML
    private void mostrarDetallesDisco(MouseEvent event) {
    }

    private void agregarOpcionesBiblioteca() {
        Label lbBiblioteca = new Label("Biblioteca");
        Label lbCanciones = new Label("Canciones");
        Label lbArtistas = new Label("Artistas");
        Label lbAlbumes = new Label("Álbumes");
        try {
            lbCanciones.setGraphic(new ImageView(new Image(new FileInputStream(ICON_CANCIONES))));
            lbArtistas.setGraphic(new ImageView(new Image(new FileInputStream(ICON_ARTISTAS))));
            lbAlbumes.setGraphic(new ImageView(new Image(new FileInputStream(ICON_ALBUMES))));

            lbBiblioteca.setFont(new Font("Avenir Book", 16));
            lbCanciones.setFont(new Font("Avenir Book", 14));
            lbArtistas.setFont(new Font("Avenir Book", 14));
            lbAlbumes.setFont(new Font("Avenir Book", 14));

            listOpciones.getItems().add(lbBiblioteca);
            listOpciones.getItems().add(lbCanciones);
            listOpciones.getItems().add(lbArtistas);
            listOpciones.getItems().add(lbAlbumes);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(IUReproductorController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Nota: En el futuro debe recibir una lista cargada con las playlist
     */
    private void agregarPlaylists() {
        Label lbPlaylist = new Label("Playlist");
        lbPlaylist.setFont(new Font("Avenir Book", 16));
        listOpciones.getItems().add(lbPlaylist);

        //AQUI SE DEBERÍAN AGREGAR LAS PLAYLIST DEL USUARIO
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

            listOpciones.getItems().add(lbCanciones);
            listOpciones.getItems().add(lbArtistas);
            listOpciones.getItems().add(lbAlbumes);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(IUReproductorController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
