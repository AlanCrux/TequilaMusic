/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import servicios.Album;
import servicios.Playlist;
import servicios.Usuario;
import utilerias.Utilerias;

/**
 * FXML Controller class
 *
 * @author Esmeralda
 */
public class IUCrearAlbumController implements Initializable {

    @FXML
    private TextField tfNombreAlbum;

    @FXML
    private TextField tfCompania;

    @FXML
    private TextField tfAnio;

    @FXML
    private ImageView imgPortada;

    @FXML
    private JFXButton btnCrear;

    @FXML
    private JFXButton btnCancelar;

    IUArtistaController parent;
    Usuario usuario;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        imgPortada.setPreserveRatio(true);
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
    private void onCrear(ActionEvent event) {
        Album album = new Album();
        album.setTitulo(tfNombreAlbum.getText());
        album.setAnioLanzamiento(tfAnio.getText());
        album.setCompaniaDiscografica(tfCompania.getText());
        album.setImagenAlbum(Utilerias.imageToByteArray(imgPortada.getImage()));
        parent.agregarAlbum(album);
        parent.ocultarError();
        Stage stage = (Stage) btnCancelar.getScene().getWindow();
        stage.close();
    }

    @FXML
     void onCancelar(ActionEvent event) {
        Stage stage = (Stage) btnCancelar.getScene().getWindow();
        parent.ocultarError();
        stage.close();
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

    public IUArtistaController getParent() {
        return parent;
    }

    public void setParent(IUArtistaController parent) {
        this.parent = parent;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
    
}
