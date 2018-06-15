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
import logica.psl.CancionSl;

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
  private TableView<CancionSl> tbCanciones;
  @FXML
  private TableColumn<CancionSl, String> tbcTitulo;
  @FXML
  private TableColumn<CancionSl, String> tbcArtista;
  @FXML
  private TableColumn<CancionSl, String> tbcAlbum;
  @FXML
  private TableColumn<CancionSl, String> tbcDuracion;

  private boolean play = true;
  private static final String ICON_CANCIONES = "src/recursos/iconos/maracas.png";
  private static final String ICON_ARTISTAS = "src/recursos/iconos/mexicano.png";
  private static final String ICON_ALBUMES = "src/recursos/iconos/guitarra.png";
  private static final String ICON_PAUSE = "/recursos/iconos/icon_pausa.png";
  private static final String ICON_PLAY = "/recursos/iconos/icon_play.png";
  private static final String ICON_PLAYLIST = "src/recursos/iconos/icon_playlist.png";

  private static final int PUERTO = 4481;
  private Socket socket;
  
  /**
   * Inicializa los componentes de la ventana IUReproductor.
   */
  @Override
  public void initialize(URL url, ResourceBundle rb) {
    agregarOpcionesBiblioteca();
    agregarPlaylists();

    List<JFXButton> botones = new ArrayList<>();
    botones.add(btnConfigurar);
    botones.add(btnUsuario);
    botones.add(btnAjustes);

    tbcTitulo.setCellValueFactory(new PropertyValueFactory<>("titulo"));
    tbcArtista.setCellValueFactory(new PropertyValueFactory<>("artista"));
    tbcAlbum.setCellValueFactory(new PropertyValueFactory<>("album"));
    tbcDuracion.setCellValueFactory(new PropertyValueFactory<>("duracion"));
   
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
      tbCanciones.setItems(FXCollections.observableList(obtenerCanciones(tfBuscar.getText())));
    }
  }

  private List<CancionSl> obtenerCanciones(String criterio) {
    List<CancionSl> resultados = new ArrayList<>();

    return resultados;
  }

  @FXML
  void onActionNext(MouseEvent event) {

  }

  @FXML
  void onActionPlay(MouseEvent event) {
    if (play) {
      btnPlay.setImage(new Image(ICON_PAUSE));
      play = false;
    } else {
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
