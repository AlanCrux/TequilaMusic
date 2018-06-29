package presentacion.controladores;

import com.jfoenix.controls.JFXListView;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;
import servicios.Usuario;
import servicios.servicios;
import servicios.servicios.Client;
import utilerias.Utilerias;

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
    private JFXListView<Label> listOpciones;
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
    private ResourceBundle rb;

    private static final String ICON_CANCIONES = "src/recursos/iconos/maracas.png";
    private static final String ICON_ALBUMES = "src/recursos/iconos/guitarra.png";
    private static final String ICON_AGREGAR = "src/recursos/iconos/anadir.png";

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.rb = rb;
        lblArtista.setText(usuario.getNombre());
        imgPerfil.setImage(Utilerias.byteToImage(usuario.getFoto()));
        agregarOpciones();
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public void setServidor(servicios.Client servidor) {
        this.sevidor = servidor;
    }

    private void agregarOpciones() {
        Label lbCanciones = new Label("Canciones");
        Label lbAlbumes = new Label("Álbumes");
        Label lbAgregarAlbum = new Label("Agregar álbum");

        try {
            lbCanciones.setGraphic(new ImageView(new Image(new FileInputStream(ICON_CANCIONES))));
            lbAlbumes.setGraphic(new ImageView(new Image(new FileInputStream(ICON_ALBUMES))));
            lbAgregarAlbum.setGraphic(new ImageView(new Image(new FileInputStream(ICON_AGREGAR))));

            lbCanciones.setFont(new Font("Avenir Book", 14));
            lbAlbumes.setFont(new Font("Avenir Book", 14));
            lbAgregarAlbum.setFont(new Font("Avenir Book", 14));

            listOpciones.getItems().add(lbCanciones);
            listOpciones.getItems().add(lbAlbumes);
            listOpciones.getItems().add(lbAgregarAlbum);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(IUReproductorController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void onBiblioteca(MouseEvent event) {
        String seleccionado = listOpciones.getSelectionModel().getSelectedItem().getText();
        switch (seleccionado) {
            case "Canciones":
                //cargarModCanciones();
                break;
            case "Artistas":
                break;
            case "Álbumes":
                break;
            case "Agregar álbum":
                break;

        }
    }

//    public void cargarModCanciones() {
//        FXMLLoader loader = new FXMLLoader(getClass().getResource("/presentacion/vistas/modHistorial.fxml"), rb);
//        ModHistorialController controller = new ModHistorialController();
//        controller.setCorreo(usuario.getCorreo());
//        controller.setParent(this);
//        loader.setController(controller);
//        try {
//            Utilerias.fadeTransition(contentPrincipal, 300);
//            contentPrincipal.getChildren().setAll((AnchorPane) loader.load());
//        } catch (IOException ex) {
//            Logger.getLogger(IUReproductorController.class.getName()).log(Level.SEVERE, null, ex);
//        }
//
    }

