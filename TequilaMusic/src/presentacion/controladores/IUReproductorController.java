package presentacion.controladores;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXProgressBar;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.embed.swing.SwingFXUtils;
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
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import org.apache.thrift.TException;
import servicios.CancionSL;
import servicios.Contenido;
import servicios.Playlist;
import servicios.Usuario;
import servicios.servicios.Client;
import utilerias.LabelPlaylist;
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
    private JFXListView<LabelPlaylist> listPlaylist;
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
    private ImageView imgDetallesDisco;
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

    private boolean banderaModBusqueda;
    private boolean banderaModRadio;
    private boolean banderaModExplorar;
    private boolean banderaModPlaylist;

    private Usuario usuario;
    private Client servidor;
    private List<Playlist> playlist = new ArrayList<>();

    private static final String TAB_SELECCIONADA = "-fx-background-color: #1ABC9C; -fx-background-radius: 30; -fx-border-radius: 30;";
    private static final String TAB_BASE = "-fx-background-color: #2f7aff; -fx-background-radius: 30; -fx-border-radius: 30;";
    private static final String ICON_CANCIONES = "src/recursos/iconos/maracas.png";
    private static final String ICON_ARTISTAS = "src/recursos/iconos/mexicano.png";
    private static final String ICON_ALBUMES = "src/recursos/iconos/guitarra.png";
    private static final String ICON_GENEROS = "src/recursos/iconos/mexican-hat.png";
    private static final String ICON_HISTORIAL = "src/recursos/iconos/corn.png";
    private static final String ICON_PAUSE = "/recursos/iconos/icon_pausa.png";
    private static final String ICON_PLAY = "/recursos/iconos/icon_play.png";
    private static final String ICON_PLAYLIST = "src/recursos/iconos/icon_playlist.png";
    public static String DOWNLOADS_DIR = "C:\\Users\\alanc\\Downloads\\";

    //Controladores de los modulos
    ModBuscarCancionesController controllerBuscar;
    ModPlaylistController controllerPlaylist;

    //FXML Ventanas
    FXMLLoader loaderModPlaylist;
    FXMLLoader loaderModBuscar;

    //AnchorPane de las ventanas
    AnchorPane anchorModPlaylist;
    AnchorPane anchorModBusqueda;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        agregarOpcionesBiblioteca();
        cargarPlaylist(servidor, usuario);

        listOpciones.setOnMouseClicked(event -> {
            listPlaylist.getSelectionModel().clearSelection();
            cambiarContenido(listOpciones.getSelectionModel().getSelectedItem().getText());
        });

        listPlaylist.setOnMouseClicked(event -> {
            listOpciones.getSelectionModel().clearSelection();
            Playlist seleccionada = listPlaylist.getSelectionModel().getSelectedItem().getPlaylist();
            activarModuloPlaylist(seleccionada);
        });
        
        cargarModuloBusqueda();
        cargarModuloPlaylist();
    }

    @FXML
    public void onBuscar(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            contentPane.setVisible(false);
            if (!banderaModBusqueda) {
                activarModuloBusqueda(tfBuscar.getText());
            } else {

            }
        }
    }

    public void cambiarContenido(String opcion) {
        switch (opcion) {
            case "Biblioteca":
                break;
            case "Canciones":
                break;
            case "Artistas":
                break;
            case "Álbumes":
                break;
            case "Géneros":
                break;
            case "Recientes":
                break;
        }
    }

    public void cargarModuloPlaylist() {
        //Control del modulo de contenido de playlist
        loaderModPlaylist = new FXMLLoader(getClass().getResource("/presentacion/vistas/modPlaylist.fxml"));
        controllerPlaylist = new ModPlaylistController();
        controllerPlaylist.setServidor(servidor);
        loaderModPlaylist.setController(controllerPlaylist);
        try {
            anchorModPlaylist = loaderModPlaylist.load();
        } catch (IOException ex) {
            Logger.getLogger(IUReproductorController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void cargarModuloBusqueda() {
        loaderModBuscar = new FXMLLoader(getClass().getResource("/presentacion/vistas/modBuscarCanciones.fxml"));
        controllerBuscar = new ModBuscarCancionesController();
        controllerBuscar.setParent(this);
        loaderModBuscar.setController(controllerBuscar);
        try {
            anchorModBusqueda = loaderModBuscar.load();
        } catch (IOException ex) {
            Logger.getLogger(IUReproductorController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void activarModuloPlaylist(Playlist playlist) {
        controllerPlaylist.setPlaylist(playlist);
        //
        List<CancionSL> canciones = obtenerCanciones(playlist.getIdPlaylist(), controllerPlaylist);
//        if (canciones.size() > 1) {
//            tfResumen.setText(tbCanciones.getItems().size() + " canciones");
//        } else {
//            tfResumen.setText(tbCanciones.getItems().size() + " canción");
//        }
        controllerPlaylist.cargarDatosPlaylist(canciones);
        System.out.println("SE LOGRO PLAYLIST");
        banderaModBusqueda = false;
        banderaModExplorar = false;
        banderaModRadio = false;
        banderaModPlaylist = true;
        contentPrincipal.getChildren().setAll(anchorModPlaylist);
    }
    
    public List<CancionSL> obtenerCanciones(int idPlaylist, ModPlaylistController controller) {
        List<CancionSL> resultados = new ArrayList<>();
        try {
            servidor = Utilerias.conectar("192.168.0.7", 9090);
            resultados = servidor.obtenerCancionesPlaylist(idPlaylist);
        } catch (TException ex) {
            Logger.getLogger(ModPlaylistController.class.getName()).log(Level.SEVERE, null, ex);
        }
        for (int i = 0; i < resultados.size(); i++) {
            resultados.get(i).setPlaylistController(controller);
        }
        return resultados;
    }

    public void activarModuloBusqueda(String cancion) {
        List<CancionSL> resultador = buscar(cancion);
        System.out.println("SE LOGRO BUSQUEDA");
        controllerBuscar.mostrarResultados(resultador);
        System.out.println("SE LOGRO CARGAR DATOS");
        banderaModBusqueda = false;
        banderaModExplorar = true;
        banderaModRadio = false;
        contentPrincipal.getChildren().setAll(anchorModBusqueda);
    }
    
    public List<CancionSL> buscar(String criterio) {
        List<CancionSL> resultados = new ArrayList<>();
        
        try {
            servidor = Utilerias.conectar("192.168.0.7", 9090);
            resultados = servidor.obtenerCancionesFiltradas(criterio);
        } catch (TException ex) {
            Logger.getLogger(ModBuscarCancionesController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return resultados;
    }

    private void agregarOpcionesBiblioteca() {
        Label lbBiblioteca = new Label("Biblioteca");
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

            lbBiblioteca.setFont(new Font("Avenir Book", 16));
            lbCanciones.setFont(new Font("Avenir Book", 14));
            lbArtistasBiblioteca.setFont(new Font("Avenir Book", 14));
            lbAlbumes.setFont(new Font("Avenir Book", 14));
            lbGeneros.setFont(new Font("Avenir Book", 14));
            lbHistorial.setFont(new Font("Avenir Book", 14));

            listOpciones.getItems().add(lbBiblioteca);
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
     * Nota: En el futuro debe recibir una lista cargada con las playlist
     */
    private void cargarPlaylist(Client servidor, Usuario usuario) {
        try {
            playlist = servidor.obtenerPlaylists(usuario.getCorreo());
        } catch (TException ex) {
            Logger.getLogger(IUReproductorController.class.getName()).log(Level.SEVERE, null, ex);
        }

        for (int i = 0; i < playlist.size(); i++) {
            LabelPlaylist lbPlay = new LabelPlaylist(playlist.get(i).getNombre());
            try {
                lbPlay.setGraphic(new ImageView(new Image(new FileInputStream(ICON_PLAYLIST))));
            } catch (FileNotFoundException ex) {
                Logger.getLogger(IUReproductorController.class.getName()).log(Level.SEVERE, null, ex);
            }
            lbPlay.setFont(new Font("Avenir Book", 14));
            lbPlay.setPlaylist(playlist.get(i));
            listPlaylist.getItems().add(lbPlay);
        }
    }

    public void agregarPlaylist(String nombre, String descripcion, Image imagen) {
        Playlist lista = new Playlist();
        lista.setCorreo(usuario.getCorreo());
        lista.setNombre(nombre);
        lista.setDescripcion(descripcion);
        lista.setImagen(Utilerias.imageToByteArray(imagen));
        try {
            servidor.insertarPlaylist(lista);
            listOpciones.getItems().clear();
            agregarOpcionesBiblioteca();
            cargarPlaylist(servidor, usuario);
        } catch (TException ex) {
            Logger.getLogger(IUReproductorController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void agregarCancionPlaylist(int idPlaylist, int idCancion) {
        Contenido contenido = new Contenido(idCancion, idPlaylist);
        try {
            servidor.insertarCancionPlaylist(contenido);
        } catch (TException ex) {
            Logger.getLogger(IUReproductorController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    public void onNuevaLista(ActionEvent event) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/presentacion/vistas/IUAgregarPlaylist.fxml"));
        IUAgregarPlaylistController controller = new IUAgregarPlaylistController();
        controller.setParent(this);
        loader.setController(controller);
        contentError.setVisible(true);
        controller.mostrarVentana(loader, (Stage) btnBiblioteca.getScene().getWindow());
    }

    public void ocultarError() {
        contentError.setVisible(false);
    }

    @FXML
    private void ocultarFlechaPerfil(MouseEvent event) {
        imgPerfil.setOpacity(0);
        imgPerfil.setOpacity(0.7);
    }

    @FXML
    private void mostrarFlechaPerfil(MouseEvent event) {
        imgPerfil.setOpacity(0.7);
    }

    @FXML
    private void onActionBiblioteca(ActionEvent event) {
    }

    @FXML
    private void onActionRadio(ActionEvent event) {
        btnBiblioteca.setStyle(TAB_BASE);
        btnExplorar.setStyle(TAB_BASE);
        btnRadio.setStyle(TAB_SELECCIONADA);
        cargarRadio();
    }

    @FXML
    private void onActionExplorar(ActionEvent event) {
        btnBiblioteca.setStyle(TAB_BASE);
        btnExplorar.setStyle(TAB_SELECCIONADA);
        btnRadio.setStyle(TAB_BASE);
        cargarExplorar();
    }

    public void cargarExplorar() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/presentacion/vistas/modExplorar.fxml"));
        //ModBibliotecaController controller = new ModBibliotecaController();
        //controller.setParent(this);
        //loader.setController(controller);

        try {
            Utilerias.fadeTransition(contentPrincipal);
            contentPrincipal.getChildren().setAll((AnchorPane) loader.load());
        } catch (IOException ex) {
            Logger.getLogger(IUReproductorController.class.getName()).log(Level.SEVERE, null, ex);
        }
        banderaModBusqueda = false;
        banderaModExplorar = true;
        banderaModRadio = false;
    }

    public void cargarRadio() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/presentacion/vistas/modRadio.fxml"));
        //ModBibliotecaController controller = new ModBibliotecaController();
        //controller.setParent(this);
        //loader.setController(controller);

        try {
            Utilerias.fadeTransition(contentPrincipal);
            contentPrincipal.getChildren().setAll((AnchorPane) loader.load());
        } catch (IOException ex) {
            Logger.getLogger(IUReproductorController.class.getName()).log(Level.SEVERE, null, ex);
        }
        banderaModBusqueda = false;
        banderaModExplorar = false;
        banderaModRadio = true;
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
    private void ocultarDetallesDisco(MouseEvent event) {
    }

    @FXML
    private void mostrarDetallesDisco(MouseEvent event) {
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public void setServidor(Client servidor) {
        this.servidor = servidor;
    }

    void reproducir(CancionSL seleccionada) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public List<Playlist> getPlaylist() {
        return playlist;
    }

    public Client getServidor() {
        return servidor;
    }

}
