package presentacion.controladores;

import com.jfoenix.controls.JFXButton;
import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import org.apache.thrift.TException;
import servicios.CancionSL;
import servicios.Playlist;
import servicios.servicios;
import utilerias.Utilerias;

/**
 * FXML Controller class
 *
 * @author alanc
 */
public class ModPlaylistController implements Initializable {

    @FXML
    private ImageView imgPortada;
    @FXML
    private TextField tfNombre;
    @FXML
    private TextField tfResumen;
    @FXML
    private TextArea taDescripcion;
    @FXML
    private Hyperlink hpEditar;
    @FXML
    private JFXButton btnGuardar;
    @FXML
    private JFXButton btnCancelar;
    @FXML
    private TableView<CancionSL> tbCanciones;
    @FXML
    private TableColumn<CancionSL, String> tbcTitulo;
    @FXML
    private TableColumn<CancionSL, String> tbcArtista;
    @FXML
    private TableColumn<CancionSL, String> tbcAlbum;
    @FXML
    private TableColumn<CancionSL, ImageView> tbcEliminar;

    private servicios.Client servidor;
    private Playlist playlist;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        tbcTitulo.setCellValueFactory(new PropertyValueFactory<>("titulo"));
        tbcArtista.setCellValueFactory(new PropertyValueFactory<>("artista"));
        tbcAlbum.setCellValueFactory(new PropertyValueFactory<>("album"));
        tbcEliminar.setCellValueFactory(new PropertyValueFactory<>("eliminar"));
        tbCanciones.setPlaceholder(new Label("No has agregado canciones a esta playlist"));
    }

    public void cargarDatosPlaylist(List<CancionSL> canciones) {
        imgPortada.setImage(Utilerias.byteToImage(playlist.getImagen()));
        tfNombre.setText(playlist.getNombre());
        taDescripcion.setText(playlist.getDescripcion());
        
        tbCanciones.setItems(FXCollections.observableList(canciones));
    }

    @FXML
    private void onCambiarImagen(MouseEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
        File selectedFile = fileChooser.showOpenDialog(imgPortada.getScene().getWindow());
        if (selectedFile != null) {
            Image image = new Image(selectedFile.toURI().toString());
            imgPortada.setImage(image);
        }
    }

    @FXML
    private void onEditar(ActionEvent event) {
        btnGuardar.setVisible(true);
        btnCancelar.setVisible(true);
        tfNombre.setEditable(true);
        taDescripcion.setEditable(true);
        imgPortada.setDisable(false);
        tfNombre.requestFocus();
        hpEditar.setVisible(false);
    }

    @FXML
    private void onGuardar(ActionEvent event) {
        hpEditar.setVisible(true);
        actualizarPlaylist();
        btnGuardar.setVisible(false);
        btnCancelar.setVisible(false);
        tfNombre.setEditable(false);
        taDescripcion.setEditable(false);
        tfNombre.setStyle("-fx-background-color: transparent");
        taDescripcion.setStyle("-fx-background-color: transparent");
        imgPortada.setDisable(true);
    }

    private void actualizarPlaylist() {
        playlist.setNombre(tfNombre.getText());
        playlist.setDescripcion(taDescripcion.getText());
        playlist.setImagen(Utilerias.imageToByteArray(imgPortada.getImage()));
        try {
            servidor.actualizarPlaylist(playlist);
        } catch (TException ex) {
            Logger.getLogger(ModPlaylistController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void onCancelar(ActionEvent event) {
        btnGuardar.setVisible(false);
        btnCancelar.setVisible(false);
        tfNombre.setEditable(false);
        taDescripcion.setEditable(false);
        tfNombre.setStyle("-fx-background-color: transparent");
        taDescripcion.setStyle("-fx-background-color: transparent");
        imgPortada.setDisable(true);
    }

    

    public void eliminarCancionPlaylist(int idCancion) {
        try {
            servidor.elimnarCancionPlaylist(idCancion);
            tbCanciones.getItems().clear();
            //cargarDatosPlaylist();
        } catch (TException ex) {
            Logger.getLogger(ModPlaylistController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void setServidor(servicios.Client servidor) {
        this.servidor = servidor;
    }

    public void setPlaylist(Playlist playlist) {
        this.playlist = playlist;
    }

}
