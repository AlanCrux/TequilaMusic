package presentacion.controladores;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;
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
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import org.apache.thrift.TException;
import org.apache.thrift.transport.TTransportException;
import servicios.CancionSL;
import servicios.Playlist;
import servicios.servicios.Client;
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
    private JFXTextArea taDescripcion;
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

    private Playlist playlist;
    private ResourceBundle rb;
    private IUReproductorController parent; 

    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.rb = rb;
        tbcTitulo.setCellValueFactory(new PropertyValueFactory<>("titulo"));
        tbcArtista.setCellValueFactory(new PropertyValueFactory<>("artista"));
        tbcAlbum.setCellValueFactory(new PropertyValueFactory<>("album"));
        tbCanciones.setPlaceholder(new Label("No has agregado canciones a esta playlist"));
        
        cargarDatosPlaylist(obtenerCanciones(playlist.getIdPlaylist()));
    }

    /**
     * Carga los datos de la playlist en la interfaz gráfica.
     * @param canciones 
     */
    public void cargarDatosPlaylist(List<CancionSL> canciones) {
        imgPortada.setImage(Utilerias.byteToImage(playlist.getImagen()));
        tfNombre.setText(playlist.getNombre());
        taDescripcion.setText(playlist.getDescripcion());
        if (canciones.size() > 1) {
            tfResumen.setText(canciones.size() + " canciones");
        } else {
            tfResumen.setText(canciones.size() + " canción");
        }
        tbCanciones.setItems(FXCollections.observableList(canciones));
    }
    
    /**
     * Invoca un servicio para recuperar de la base de datos las canciones asignadas a un playlist. 
     * @param idPlaylist
     * @return 
     */
    public List<CancionSL> obtenerCanciones(int idPlaylist){
        List<CancionSL> canciones = new ArrayList<>(); 
        int port = Integer.parseInt(rb.getString("dataport"));
            String host = rb.getString("datahost");
        try {
            Client servicios = Utilerias.conectar(host, port);
            canciones = servicios.obtenerCancionesPlaylist(idPlaylist);
            Utilerias.closeServer(servicios);
        } catch (TTransportException ex) {
            Logger.getLogger(ModPlaylistController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (TException ex) {
            Logger.getLogger(ModPlaylistController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return canciones; 
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
        actualizarPlaylist(playlist.getIdPlaylist(), playlist.getCorreo());
        btnGuardar.setVisible(false);
        btnCancelar.setVisible(false);
        tfNombre.setEditable(false);
        taDescripcion.setEditable(false);
        imgPortada.setDisable(true);
        hpEditar.setVisible(true);
    }
    
    @FXML
    private void onCancelar(ActionEvent event) {
        btnGuardar.setVisible(false);
        btnCancelar.setVisible(false);
        tfNombre.setEditable(false);
        taDescripcion.setEditable(false);
        imgPortada.setDisable(true);
    }

    private void actualizarPlaylist(int idPlaylist, String correo) {
        Playlist actualizar = new Playlist();
        actualizar.setIdPlaylist(idPlaylist);
        actualizar.setNombre(tfNombre.getText());
        actualizar.setDescripcion(taDescripcion.getText());
        actualizar.setImagen(Utilerias.imageToByteArray(imgPortada.getImage()));
        actualizar.setCorreo(correo);
        try {
            int port = Integer.parseInt(rb.getString("dataport"));
            String host = rb.getString("datahost");
            Client servicios = Utilerias.conectar(host, port);
            servicios.actualizarPlaylist(actualizar);
            Utilerias.closeServer(servicios);
            parent.actualizarListas();
        } catch (TException ex) {
            Logger.getLogger(ModPlaylistController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void eliminarCancionPlaylist(int idCancion) {
        int port = Integer.parseInt(rb.getString("dataport"));
        String host = rb.getString("datahost");
        try {
            Client servicios = Utilerias.conectar(host, port);
            servicios.eliminarCancionPlaylist(idCancion);
            Utilerias.closeServer(servicios);
            tbCanciones.getItems().clear();
            
            cargarDatosPlaylist(obtenerCanciones(playlist.getIdPlaylist()));
        } catch (TException ex) {
            Logger.getLogger(ModPlaylistController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void setPlaylist(Playlist playlist) {
        this.playlist = playlist;
    }

    public void setParent(IUReproductorController parent) {
        this.parent = parent;
    }

    
}
