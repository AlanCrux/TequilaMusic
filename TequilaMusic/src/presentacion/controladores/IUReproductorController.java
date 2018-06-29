package presentacion.controladores;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXProgressBar;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.Socket;
import java.net.URL;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Slider;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.apache.thrift.TException;
import org.apache.thrift.transport.TTransportException;
import servicios.Biblioteca;
import servicios.CancionSL;
import servicios.Historial;
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
    private List<Playlist> listas;

    // Banderas que indican la ventana activa
    private boolean isNuevaLista;
    private boolean isBuscarCanciones;
    private boolean isHistorial;
    private boolean isModCanciones;
    private boolean isModArtistas;
    private boolean isModGeneros;

    // Controladores de algunas ventanas
    IUAgregarPlaylistController controllerNuevaLista;
    ModBuscarCancionesController controllerBuscarCanciones;
    ModColaController controllerCola;

    // Colas de reproducción 
    private List<CancionSL> resultadosBusqueda;
    private List<CancionSL> colaFija;
    private List<CancionSL> colaDinamica;

    // Objetos para reproducción de audio
    private Media hit;
    private MediaPlayer mediaPlayer;
    private static final String DIR_TEMP = System.getProperty("user.home") + "/Downloads/TequilaMusic/temporal/";
    private static final String DIR_DESC = System.getProperty("user.home") + "/Downloads/TequilaMusic/descargas/";

    // Variables para control del progreso
    private static final int LIMITE_CANCION = 100;
    private double currentTime = 0;
    private Task progressTask;
    private Thread reproductionThread;
    private boolean play;

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
         * 3. Agregar menu contextual a la lista de listas de reproducción
         */

        agregarOpciones();
        listas = obtenerPlaylist(usuario.getCorreo());
        cargarPlaylist(listas);
        addContextMenu(listPlaylist);
        controllerCola = new ModColaController();
    }

    public void actualizarListas() {
        int actual = listPlaylist.getSelectionModel().getSelectedIndex();
        listPlaylist.getItems().clear();
        listas = obtenerPlaylist(usuario.getCorreo());
        cargarPlaylist(listas);
        listPlaylist.getSelectionModel().select(actual);
    }

    @FXML
    private void onBuscar(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            cargarModBuscarCanciones(tfBuscar.getText());
        }
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
        listPlaylist.getSelectionModel().clearSelection();
        String seleccionado = listOpciones.getSelectionModel().getSelectedItem().getText();
        switch (seleccionado) {
            case "Canciones":
                cargarModCanciones();
                break;
            case "Artistas":
                cargarModArtistas();
                break;
            case "Álbumes":
                break;
            case "Géneros":
                cargarModGeneros();
                break;
            case "Recientes":
                cargarModRecientes();
                break;

        }
    }

    public void cargarModBuscarCanciones(String criterio) {
        if (!isBuscarCanciones) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/presentacion/vistas/modBuscarCanciones.fxml"));
            controllerBuscarCanciones = new ModBuscarCancionesController();
            controllerBuscarCanciones.setParent(this);
            controllerBuscarCanciones.setRb(rb);

            loader.setController(controllerBuscarCanciones);
            try {
                Utilerias.fadeTransition(contentPrincipal, 300);
                contentPrincipal.getChildren().setAll((AnchorPane) loader.load());
                controllerBuscarCanciones.mostrarResultados(criterio);
            } catch (IOException ex) {
                Logger.getLogger(IUReproductorController.class.getName()).log(Level.SEVERE, null, ex);
            }
            isBuscarCanciones = true;
            isHistorial = false;
            isModCanciones = false;
            isModArtistas = false;
            isModGeneros = false;
        } else {
            controllerBuscarCanciones.mostrarResultados(criterio);
        }

    }

    public void cargarModCanciones() {
        if (!isModCanciones) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/presentacion/vistas/modBiblioteca.fxml"), rb);
            ModBibliotecaController controller = new ModBibliotecaController();
            controller.setParent(this);
            controller.setCorreo(usuario.getCorreo());
            loader.setController(controller);

            try {
                Utilerias.fadeTransition(contentPrincipal, 750);
                contentPrincipal.getChildren().setAll((AnchorPane) loader.load());
            } catch (IOException ex) {
                Logger.getLogger(IUReproductorController.class.getName()).log(Level.SEVERE, null, ex);
            }
            isBuscarCanciones = false;
            isHistorial = false;
            isModCanciones = true;
            isModArtistas = false;
            isModGeneros = false;
        }
    }

    public void cargarModArtistas() {
        if (!isModArtistas) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/presentacion/vistas/modArtistas.fxml"), rb);
            ModArtistasController controller = new ModArtistasController();
            controller.setParent(this);
            controller.setCorreo(usuario.getCorreo());
            loader.setController(controller);

            try {
                Utilerias.fadeTransition(contentPrincipal, 500);
                contentPrincipal.getChildren().setAll((AnchorPane) loader.load());
            } catch (IOException ex) {
                Logger.getLogger(IUReproductorController.class.getName()).log(Level.SEVERE, null, ex);
            }
            isBuscarCanciones = false;
            isHistorial = false;
            isModCanciones = false;
            isModArtistas = true;
            isModGeneros = false;
        }
    }

    public void cargarModAlbumes() {

    }

    public void cargarModGeneros() {
        if (!isModGeneros) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/presentacion/vistas/modGeneros.fxml"), rb);
            ModGenerosController controller = new ModGenerosController();
            controller.setParent(this);
            controller.setCorreo(usuario.getCorreo());
            loader.setController(controller);

            try {
                Utilerias.fadeTransition(contentPrincipal, 500);
                contentPrincipal.getChildren().setAll((AnchorPane) loader.load());
            } catch (IOException ex) {
                Logger.getLogger(IUReproductorController.class.getName()).log(Level.SEVERE, null, ex);
            }
            isBuscarCanciones = false;
            isHistorial = false;
            isModCanciones = false;
            isModArtistas = false;
            isModGeneros = true;
        }
    }

    public void cargarModRecientes() {
        if (!isHistorial) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/presentacion/vistas/modHistorial.fxml"), rb);
            ModHistorialController controller = new ModHistorialController();
            controller.setCorreo(usuario.getCorreo());
            controller.setParent(this);
            loader.setController(controller);
            try {
                Utilerias.fadeTransition(contentPrincipal, 500);
                contentPrincipal.getChildren().setAll((AnchorPane) loader.load());
            } catch (IOException ex) {
                Logger.getLogger(IUReproductorController.class.getName()).log(Level.SEVERE, null, ex);
            }
            isHistorial = true;
            isBuscarCanciones = false;
            isModCanciones = false;
            isModArtistas = false;
        }
    }

    public void cargarExplorar() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/presentacion/vistas/modExplorar.fxml"), rb);
        //ModBibliotecaController controller = new ModBibliotecaController();
        //controller.setParent(this);
        //loader.setController(controller);

        try {
            Utilerias.fadeTransition(contentPrincipal);
            contentPrincipal.getChildren().setAll((AnchorPane) loader.load());
        } catch (IOException ex) {
            Logger.getLogger(IUReproductorController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void cargarRadio() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/presentacion/vistas/modRadio.fxml"), rb);
        //ModBibliotecaController controller = new ModBibliotecaController();
        //controller.setParent(this);
        //loader.setController(controller);

        try {
            Utilerias.fadeTransition(contentPrincipal);
            contentPrincipal.getChildren().setAll((AnchorPane) loader.load());
        } catch (IOException ex) {
            Logger.getLogger(IUReproductorController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @FXML
    private void onPlayslist(MouseEvent event) {
        int index = listPlaylist.getSelectionModel().getSelectedIndex();
        Playlist seleccionada = listas.get(index);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/presentacion/vistas/modPlaylist.fxml"), rb);
        ModPlaylistController controller = new ModPlaylistController();
        controller.setPlaylist(seleccionada);
        controller.setParent(this);
        loader.setController(controller);

        try {
            contentPrincipal.getChildren().setAll((AnchorPane) loader.load());
        } catch (IOException ex) {
            Logger.getLogger(IUReproductorController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    public void onNuevaLista(ActionEvent event) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/presentacion/vistas/IUAgregarPlaylist.fxml"), rb);
        controllerNuevaLista = new IUAgregarPlaylistController();
        controllerNuevaLista.setParent(this);
        loader.setController(controllerNuevaLista);
        isNuevaLista = true;
        contentError.setVisible(true);
        controllerNuevaLista.mostrarVentana(loader, (Stage) btnBiblioteca.getScene().getWindow());
    }

    public void agregarPlaylist(Playlist playlist) {
        int port = Integer.parseInt(rb.getString("dataport"));
        String host = rb.getString("datahost");
        Client servicios;
        playlist.setCorreo(usuario.getCorreo());
        try {
            servicios = Utilerias.conectar(host, port);
            if (!servicios.insertarPlaylist(playlist)) {
                System.out.println("MENSAJE ERROR AL CREAR");
            }

            Utilerias.closeServer(servicios);
        } catch (TTransportException ex) {
            Logger.getLogger(IUReproductorController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (TException ex) {
            Logger.getLogger(IUReproductorController.class.getName()).log(Level.SEVERE, null, ex);
        }
        listPlaylist.getItems().clear();
        listas = obtenerPlaylist(usuario.getCorreo());
        cargarPlaylist(listas);
    }

    @FXML
    private void onActionRandom(MouseEvent event) {
    }

    @FXML
    private void onActionBack(MouseEvent event) {
    }

    @FXML
    private void onActionPlay(MouseEvent event) {
        if (play) {
            mediaPlayer.pause();
            play = false;
        } else {
            mediaPlayer.play();
            play = true;
        }

    }

    @FXML
    private void onActionNext(MouseEvent event) {
    }

    @FXML
    private void onActionRepeat(MouseEvent event) {
    }

    @FXML
    private void onContentError(MouseEvent event) {
        if (isNuevaLista) {
            controllerNuevaLista.onCancelar(new ActionEvent());
            isNuevaLista = false;
        }
    }

    @FXML
    public void onCola(MouseEvent event) {

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

    /**
     * Agrega un menú contextual a la lista de playlist de la IU.
     *
     * @param lista
     */
    public void addContextMenu(JFXListView<?> lista) {
        ContextMenu context = new ContextMenu();
        MenuItem menuAgregar = new MenuItem("Agregar playlist");
        MenuItem menuEliminar = new MenuItem("Eliminar");

        menuAgregar.setOnAction(e -> {
            onNuevaLista(e);
        });

        menuEliminar.setOnAction(e -> {
            int index = lista.getSelectionModel().getSelectedIndex();
            eliminarPlaylist(index);
        });
        context.getItems().addAll(menuAgregar, menuEliminar);
        lista.setContextMenu(context);
    }

    public void cargarDatosCancion(CancionSL cancion) {
        Platform.runLater(() -> {
            lbNombreCancion.setText(cancion.getTitulo());
            lbArtistas.setText(cancion.getArtista());
            imgDisco.setImage(Utilerias.byteToImage(cancion.getImagenAlbum()));
            Utilerias.showNotification(cancion.getTitulo(), cancion.getArtista(), Pos.TOP_RIGHT);
        });

        agregarHistorial(cancion);
        reproducir(cancion);
    }

    public void reproducir(CancionSL cancion) {
        if (play) {
            reproductionThread.interrupt();
            mediaPlayer.stop();
        }
        currentTime = 0;
        
        // La ruta de la canción en la BD
        String ruta = cancion.getRuta();
        // La ruta temporal en el cliente
        String home = DIR_TEMP;
        home += cancion.getIdCancion() + ".mp3";

        // Verificamos si la canción ya fue descargada por el usuario
        String rutaDescarga = DIR_DESC + cancion.getIdCancion() + ".mp3";
        File fichero = new File(rutaDescarga);
        
        if (!fichero.exists()) {
            // Verificamos si ya existe la canción en temporal
            fichero = new File(home);
            if (!fichero.exists()) {
                System.out.println("SE DESCARGO EN TEMPORAL");
                Socket streaming = Utilerias.conectarStreaming("localhost", 1234);
                Utilerias.bajarCancion(ruta, home, streaming);
            }
        } else {
            System.out.println("YA FUE DESCARGADA, CAMBIANDO A DESC");
            // Si ya esta descargada entonces cambiamos la ruta
            home = rutaDescarga; 
        }

        hit = new Media(new File(home).toURI().toString());
        enlazarBarra();
        mediaPlayer = new MediaPlayer(hit);
        mediaPlayer.play();

        btnPlay.setDisable(false);
        btnAnterior.setDisable(false);
        btnSiguiente.setDisable(false);
        btnAleatorio.setDisable(false);
        btnRepetir.setDisable(false);
        play = true;

    }

    /**
     * Invoca un método del servidor para eliminar una playlist.
     *
     * @param index el indice de la playlist que se quiere eliminar.
     */
    public void eliminarPlaylist(int index) {
        int idPlaylist = listas.get(index).getIdPlaylist();
        int port = Integer.parseInt(rb.getString("dataport"));
        String host = rb.getString("datahost");
        Client servicios;
        try {
            servicios = Utilerias.conectar(host, port);
            servicios.elimnarPlaylist(idPlaylist);
            Utilerias.closeServer(servicios);

        } catch (TTransportException ex) {
            Logger.getLogger(IUReproductorController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (TException ex) {
            Logger.getLogger(IUReproductorController.class.getName()).log(Level.SEVERE, null, ex);
        }
        listPlaylist.getItems().clear();
        listas = obtenerPlaylist(usuario.getCorreo());
        cargarPlaylist(listas);
    }

    /**
     * Oculta el elemento de interfaz gráfica donde se muestran los errores.
     */
    public void ocultarError() {
        contentError.setVisible(false);
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public List<Playlist> getListas() {
        return listas;
    }

    public void setIsNuevaLista(boolean isNuevaLista) {
        this.isNuevaLista = isNuevaLista;
    }

    public void agregarHistorial(CancionSL cancion) {
        int port = Integer.parseInt(rb.getString("dataport"));
        String host = rb.getString("datahost");
        Client servicios;
        Historial historial = new Historial();
        historial.setIdCancion(cancion.getIdCancion());
        historial.setCorreo(usuario.getCorreo());

        Date date = new Date();
        Format formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String s = formatter.format(date);

        historial.setFecha(s);
        try {
            servicios = Utilerias.conectar(host, port);
            if (servicios.eliminarCancionHistorial(cancion.getIdCancion())) {
                servicios.insertarCancionHistorial(historial);
            }

            Utilerias.closeServer(servicios);

        } catch (TTransportException ex) {
            Logger.getLogger(IUReproductorController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (TException ex) {
            Logger.getLogger(IUReproductorController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void setResultadosBusqueda(List<CancionSL> resultadosBusqueda) {
        this.resultadosBusqueda = resultadosBusqueda;
    }

    /**
     * Enlaza la ProgressBar de la IU a una tarea en especifico.
     */
    public void enlazarBarra() {
        progressTask = reproductionTask();
        pbCancion.progressProperty().unbind();
        Platform.runLater(() -> {
            pbCancion.progressProperty().bind(progressTask.progressProperty());
        });

        reproductionThread = new Thread(progressTask);
        reproductionThread.start();
    }

    /**
     * Genera un task para la reproducción de medios, actualizando la variable
     * progreso cada medio segundo.
     *
     * @return el task generado.
     */
    public Task reproductionTask() {
        return new Task() {
            @Override
            protected Object call() {
                Duration current;
                Duration divide;
                String progress;
                do {
                    try {
                        Thread.sleep(400);
                        current = mediaPlayer.getCurrentTime().multiply(100);
                        divide = current.divide(hit.getDuration());
                        progress = divide.toString().substring(0, 3);
                        currentTime = Double.parseDouble(progress);
                        updateProgress(currentTime, LIMITE_CANCION);
                    } catch (InterruptedException | NumberFormatException ex) {

                    }
                } while (currentTime < LIMITE_CANCION);
                CancionSL siguiente = controllerCola.obtenerSiguiente();
                cargarDatosCancion(siguiente);
                return null;
            }
        };
    }

    public void agregarFinal(CancionSL cancion) {
        controllerCola.agregarFinal(cancion);
    }

    public void agregarInicio(CancionSL cancion) {
        controllerCola.agregarInicio(cancion);
    }

    public void agregarBiblioteca(int idCancion) {
        int port = Integer.parseInt(rb.getString("dataport"));
        String host = rb.getString("datahost");

        Biblioteca biblioteca = new Biblioteca();
        biblioteca.setCorreo(usuario.getCorreo());
        biblioteca.setIdCancion(idCancion);
        try {
            Client servicios = Utilerias.conectar(host, port);
            if (!servicios.insertarCancionBiblioteca(biblioteca)) {
                System.out.println("YA EXISTE EN TU BIBLIOTECA");
            }
        } catch (TException ex) {
            Logger.getLogger(IUReproductorController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void descargarCancion(CancionSL cancion) {
        if (cancion.getDescargada().equals("true")) {
            Utilerias.displayInformation("Upps", "Ya has descargado esta canción");
        } else {
            Socket streaming = Utilerias.conectarStreaming("localhost", 1234);
            // La ruta de la canción en la BD
            final String ruta = cancion.getRuta();
            // La ruta temporal en el cliente
            final String home = DIR_DESC + cancion.getIdCancion() + ".mp3";

            Runnable bajar = new Runnable() {
                @Override
                public void run() {
                    Utilerias.bajarCancion(ruta, home, streaming);
                    Platform.runLater(() -> {
                        Utilerias.showNotification("Terminado", cancion.getTitulo(), Pos.TOP_RIGHT);
                    });
                }
            };
            Thread hilo = new Thread(bajar);
            Utilerias.showNotification("Descargando", cancion.getTitulo(), Pos.TOP_RIGHT);
            hilo.start();
        }
    }

}
