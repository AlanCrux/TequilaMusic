package presentacion.controladores;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXProgressBar;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
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
import org.apache.thrift.TException;
import org.apache.thrift.transport.TTransportException;
import servicios.Playlist;
import servicios.Usuario;
import servicios.servicios.Client;
import utilerias.Utilerias;

/**
 * FXML Controller class
 *
 * @author alanc
 */
public class IUReproductorController implements Initializable {

    @FXML
    private Slider sliderVolumen;
    @FXML
    private TextField tfBuscar;
    @FXML
    private ImageView imgPerfil;
    @FXML
    private JFXButton btnBiblioteca;
    @FXML
    private JFXButton btnRadio;
    @FXML
    private JFXButton btnExplorar;
    @FXML
    private SplitPane splitPane;
    @FXML
    private JFXListView<Label> listOpciones;
    @FXML
    private JFXListView<Label> listPlaylist;
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
    private JFXProgressBar pbCancion;
    @FXML
    private ImageView imgDisco;
    @FXML
    private Label lbNombreCancion;
    @FXML
    private Label lbArtistas;
    @FXML
    private AnchorPane contentPane;
    @FXML
    private ImageView imgFondo;
    @FXML
    private AnchorPane contentError;

    private Usuario usuario;

    private static final String ICON_CANCIONES = "src/recursos/iconos/maracas.png";
    private static final String ICON_ARTISTAS = "src/recursos/iconos/mexicano.png";
    private static final String ICON_ALBUMES = "src/recursos/iconos/guitarra.png";
    private static final String ICON_GENEROS = "src/recursos/iconos/mexican-hat.png";
    private static final String ICON_HISTORIAL = "src/recursos/iconos/corn.png";
    private static final String ICON_PLAYLIST = "src/recursos/iconos/icon_playlist.png";

    private ResourceBundle rb;

    // Las listas de reproducción del usuario
    List<Playlist> listas;

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.rb = rb;
        /*
         * 1. Cargar las opciones de la biblioteca
         * 2. Cargar las playlist del usuario 
         */

        agregarOpciones();
        listas = obtenerPlaylist(usuario.getCorreo());
        cargarPlaylist(listas);
    }

    @FXML
    private void onBuscar(KeyEvent event) {
    }

    @FXML
    private void ocultarFlechaPerfil(MouseEvent event) {
    }

    @FXML
    private void mostrarFlechaPerfil(MouseEvent event) {
    }

    @FXML
    private void onAjustes(MouseEvent event) {
    }

    @FXML
    private void onActionBiblioteca(ActionEvent event) {
    }

    @FXML
    private void onActionRadio(ActionEvent event) {
    }

    @FXML
    private void onActionExplorar(ActionEvent event) {
    }

    @FXML
    private void onBiblioteca(MouseEvent event) {
    }

    @FXML
    private void onPlayslist(MouseEvent event) {
        int index = listPlaylist.getSelectionModel().getSelectedIndex();
        Playlist seleccionada = listas.get(index);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/presentacion/vistas/modPlaylist.fxml"),rb);
        ModPlaylistController controller = new ModPlaylistController();
        controller.setPlaylist(seleccionada);
        loader.setController(controller);

        try {
            contentPrincipal.getChildren().setAll((AnchorPane) loader.load());
        } catch (IOException ex) {
            Logger.getLogger(IUReproductorController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void onNuevaLista(ActionEvent event) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/presentacion/vistas/IUAgregarPlaylist.fxml"),rb);
        IUAgregarPlaylistController controller = new IUAgregarPlaylistController();
        controller.setParent(this);
        loader.setController(controller);
        contentError.setVisible(true);
        controller.mostrarVentana(loader, (Stage) btnBiblioteca.getScene().getWindow());
    }

    @FXML
    private void onActionRandom(MouseEvent event) {
    }

    @FXML
    private void onActionBack(MouseEvent event) {
    }

    @FXML
    private void onActionPlay(MouseEvent event) {
    }

    @FXML
    private void onActionNext(MouseEvent event) {
    }

    @FXML
    private void onActionRepeat(MouseEvent event) {
    }

    @FXML
    private void onContentError(MouseEvent event) {
    }

    /**
     *
     */
    private void agregarOpciones() {
        Label lbCanciones = new Label("Canciones");
        Label lbArtistasBiblioteca = new Label("Artistas");
        Label lbAlbumes = new Label("Álbumes");
        Label lbGeneros = new Label("Géneros");
        Label lbHistorial = new Label("Recientes");

        try {
            lbCanciones.setGraphic(new ImageView(new Image(new FileInputStream(ICON_CANCIONES))));
            lbArtistasBiblioteca.setGraphic(new ImageView(new Image(new FileInputStream(ICON_ARTISTAS))));
            lbAlbumes.setGraphic(new ImageView(new Image(new FileInputStream(ICON_ALBUMES))));
            lbGeneros.setGraphic(new ImageView(new Image(new FileInputStream(ICON_GENEROS))));
            lbHistorial.setGraphic(new ImageView(new Image(new FileInputStream(ICON_HISTORIAL))));

            lbCanciones.setFont(new Font("Avenir Book", 14));
            lbArtistasBiblioteca.setFont(new Font("Avenir Book", 14));
            lbAlbumes.setFont(new Font("Avenir Book", 14));
            lbGeneros.setFont(new Font("Avenir Book", 14));
            lbHistorial.setFont(new Font("Avenir Book", 14));

            listOpciones.getItems().add(lbCanciones);
            listOpciones.getItems().add(lbArtistasBiblioteca);
            listOpciones.getItems().add(lbAlbumes);
            listOpciones.getItems().add(lbGeneros);
            listOpciones.getItems().add(lbHistorial);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(IUReproductorController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     *
     * @param playlist
     */
    private void cargarPlaylist(List<Playlist> playlist) {
        for (int i = 0; i < playlist.size(); i++) {
            Label lbPlay = new Label(playlist.get(i).getNombre());
            try {
                lbPlay.setGraphic(new ImageView(new Image(new FileInputStream(ICON_PLAYLIST))));
            } catch (FileNotFoundException ex) {
                Logger.getLogger(IUReproductorController.class.getName()).log(Level.SEVERE, null, ex);
            }
            lbPlay.setFont(new Font("Avenir Book", 14));
            listPlaylist.getItems().add(lbPlay);
        }
    }

    private List<Playlist> obtenerPlaylist(String correo) {
        List<Playlist> listaPlaylist = new ArrayList<>();
        try {
            int port = Integer.parseInt(rb.getString("dataport"));
            String host = rb.getString("datahost");
            Client servicios = Utilerias.conectar(host, port);
            listaPlaylist = servicios.obtenerPlaylists(correo);
            Utilerias.closeServer(servicios);
        } catch (TTransportException ex) {
            Logger.getLogger(IUReproductorController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (TException ex) {
            Logger.getLogger(IUReproductorController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listaPlaylist;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

}
