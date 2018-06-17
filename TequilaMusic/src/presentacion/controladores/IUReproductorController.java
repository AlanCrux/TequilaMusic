package presentacion.controladores;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXNodesList;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
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
  private JFXButton btnBiblioteca;
  @FXML
  private JFXButton btnRadio;
  @FXML
  private JFXButton btnExplorar;
  @FXML
  private JFXNodesList nodeListAjustes;
  @FXML
  private JFXButton btnAjustes;
  @FXML
  private JFXButton btnUsuario;
  @FXML
  private JFXButton btnConfigurar;
  @FXML
  private ImageView imgDisco;
  @FXML
  private Label lbNombreCancion;
  @FXML
  private Label lbArtistas;
  @FXML
  private ImageView imgDetallesDisco;
  @FXML
  private TableView<?> tbCanciones;
  @FXML
  private TableColumn<?, String> tbcTitulo;
  @FXML
  private TableColumn<?, String> tbcArtista;
  @FXML
  private TableColumn<?, String> tbcAlbum;
  @FXML
  private TableColumn<?, String> tbcDuracion;

  

  
  /**
   * Inicializa los componentes de la ventana IUReproductor.
   */
  @Override
  public void initialize(URL url, ResourceBundle rb) {
    

    
   
  }

  @FXML
  private void mostrarDetallesDisco(MouseEvent event) {
    imgDetallesDisco.setOpacity(0.8);
  }

  @FXML
  private void ocultarDetallesDisco(MouseEvent event) {
    imgDetallesDisco.setOpacity(0);
  }

  @FXML
  private void onActionBack(MouseEvent event) {

  }

  @FXML
  void onActionBiblioteca(ActionEvent event) {

  }

  @FXML
  private void onActionBuscar(KeyEvent event) {
    if (event.getCode() == KeyCode.ENTER) {
      //tbCanciones.setItems(FXCollections.observableList(obtenerCanciones(tfBuscar.getText())));
    }
  }

  private List<?> obtenerCanciones(String criterio) {
    List<?> resultados = new ArrayList<>();

    return resultados;
  }

  @FXML
  void onActionNext(MouseEvent event) {

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

  /**
   * Muestra la ventana.
   *
   * @param loader el loader con la ruta de la ventana que se quiere cargar.
   */
  public void mostrarVentana(FXMLLoader loader) {
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


  public void obtenerRespuesta(String respuesta) {
    tfBuscar.setText(respuesta);
  }
}
