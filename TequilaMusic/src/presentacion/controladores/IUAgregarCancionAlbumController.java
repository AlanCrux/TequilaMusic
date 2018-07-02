/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package presentacion.controladores;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.apache.thrift.TException;
import servicios.AlbumSL;
import servicios.Cancion;
import servicios.Genero;
import servicios.Usuario;
import servicios.servicios;
import utilerias.Utilerias;

/**
 * FXML Controller class
 *
 * @author esmeralda
 */
public class IUAgregarCancionAlbumController implements Initializable {

    @FXML
    private TextField tfNombreCancion;

    @FXML
    private JFXComboBox<Genero> comboGenero;

    @FXML
    private TextField tfNombreArchivo;

    @FXML
    private JFXButton btnSelecArchico;

    @FXML
    private JFXButton btnSalir;

    @FXML
    private JFXButton btnGuardar;

    ModCancionesAlbumController parent;
    String pathArchivoOriginal;
    ResourceBundle rb;
    List<Cancion> cancionesTemp;
    Usuario usuario;
    AlbumSL albumSL;
    Utilerias utilerias = new Utilerias();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.rb = rb;
        llenarCombo();
    }

    @FXML
    private void onAbrirArchivo(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
        File selectedFile = fileChooser.showOpenDialog(btnSelecArchico.getScene().getWindow());
        if (selectedFile != null) {
            File file = new File(selectedFile.getPath());
            this.pathArchivoOriginal = file.getPath();
            System.out.println("touri " + file.getAbsolutePath());
            tfNombreArchivo.setText(file.getName());
        }
    }

    @FXML
    private void onCancelar(ActionEvent event) {
        Stage stage = (Stage) btnSalir.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void onGuardar(ActionEvent event) {
        Cancion cancion = new Cancion();
        String pathBD = separarTexto(usuario.getNombre()) + "/" + separarTexto(albumSL.getTitulo() + "/" + separarTexto(tfNombreCancion.getText()));
        cancion.setTitulo(tfNombreCancion.getText());
        cancion.setIdGenero(comboGenero.getSelectionModel().getSelectedItem().getIdGenero());
        cancion.setIdAlbum(albumSL.getIdAlbum());
        cancion.setRuta(pathBD);
        int pastranaPort = Integer.parseInt(rb.getString("transport"));
        String pastranaHost = rb.getString("transhost");
        utilerias.subirCancion(pathArchivoOriginal, pathBD,pastranaHost,pastranaPort);

        int port = Integer.parseInt(rb.getString("dataport"));
        String host = rb.getString("datahost");
        servicios.Client servicios;
        try {
            servicios = Utilerias.conectar(host, port);
            if (servicios.insertarCancion(cancion)) {
                limpiarCampos();
                Stage stage = (Stage) btnGuardar.getScene().getWindow();
                stage.close();
            }
            Utilerias.closeServer(servicios);
        } catch (TException ex) {
            Logger.getLogger(ModBuscarCancionesController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void limpiarCampos() {
        tfNombreArchivo.clear();
        tfNombreCancion.clear();
        comboGenero.getSelectionModel().clearSelection();
    }

    public String separarTexto(String caracter) {

        String completo = "";
        completo = caracter.replace(" ", "_");
        System.out.println("ruta creada: " + completo);
        return completo;
    }

    void llenarCombo() {
        List<Genero> generos = new ArrayList();
        int port = Integer.parseInt(rb.getString("dataport"));
        String host = rb.getString("datahost");
        servicios.Client servicios;
        try {
            servicios = Utilerias.conectar(host, port);
            generos = servicios.obtenerGeneros();
            Utilerias.closeServer(servicios);
        } catch (TException ex) {
            Logger.getLogger(ModBuscarCancionesController.class.getName()).log(Level.SEVERE, null, ex);
        }
        comboGenero.setItems(FXCollections.observableList(generos));
    }

    public ModCancionesAlbumController getParent() {
        return parent;
    }

    public void setParent(ModCancionesAlbumController parent) {
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
            Utilerias.fadeTransition(root, 300);
            primaryStage.showAndWait();
        } catch (IOException ex) {
            Logger.getLogger(IUInicioController.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public AlbumSL getAlbum() {
        return albumSL;
    }

    public void setAlbum(AlbumSL albumSL) {
        this.albumSL = albumSL;
    }

}
