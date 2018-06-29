package presentacion.controladores;

import com.jfoenix.controls.JFXButton;
import java.io.File;
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
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import servicios.Playlist;
import utilerias.Utilerias;

/**
 * FXML Controller class
 *
 * @author alanc
 */
public class IUAgregarPlaylistController implements Initializable {

    @FXML
    private ImageView imgPortada;
    @FXML
    private JFXButton btnCrear;
    @FXML
    private JFXButton btnCancelar;
    @FXML
    private TextField tfNombre;
    @FXML
    private TextArea taDescripcion;
    
    IUReproductorController parent;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        imgPortada.setPreserveRatio(true);
    }

    @FXML
    private void onCrear(ActionEvent event) {
        Playlist playlist = new Playlist();
        playlist.setNombre(tfNombre.getText());
        playlist.setDescripcion(taDescripcion.getText()); 
        playlist.setImagen(Utilerias.imageToByteArray(imgPortada.getImage()));
        parent.agregarPlaylist(playlist);
        parent.ocultarError();
        Stage stage = (Stage) btnCancelar.getScene().getWindow();
        stage.close();
    }

    @FXML
    public void onCancelar(ActionEvent event) {
        parent.setIsNuevaLista(false);
        parent.ocultarError();
        Stage stage = (Stage) btnCancelar.getScene().getWindow();
        stage.close();
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

    public void setParent(IUReproductorController parent) {
        this.parent = parent;
    }

    /**
     * Muestra la ventana.
     *
     * @param loader el loader con la ruta de la ventana que se quiere cargar.
     * @param parent
     */
    public void mostrarVentana(FXMLLoader loader, Stage parent) {
        try {
            Stage primaryStage = new Stage();
            Parent root = (Parent) loader.load();
            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
            primaryStage.setResizable(false);
            primaryStage.initOwner(parent);
            primaryStage.initModality(Modality.WINDOW_MODAL);
            primaryStage.initStyle(StageStyle.UNDECORATED);
            primaryStage.sizeToScene();
            utilerias.Utilerias.fadeTransition(root, 300);
            primaryStage.showAndWait();
        } catch (IOException ex) {
            Logger.getLogger(IUInicioController.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
    }

}
