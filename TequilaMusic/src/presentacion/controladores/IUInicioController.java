package presentacion.controladores;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.ThreadLocalRandom;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Hyperlink;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import org.apache.thrift.transport.TTransportException;
import servicios.servicios.Client;
import utilerias.Utilerias;

/**
 * FXML Controller class
 *
 * @author alan
 */
public class IUInicioController implements Initializable {

    @FXML
    private AnchorPane contentPane;
    @FXML
    private ImageView imgFondo;
    @FXML
    private ImageView imgIcono;
    @FXML
    private Hyperlink hpDatosFondo;
    
    private Client servidor; 
    private static String HOST = "192.168.1.101";
    
    private static final int TIEMPO_CAMBIO = 15;
    private int TIEMPO_CRONOMETRO = 15;
    private int numFondoActual = 0; 
    private Timeline cronometro;

    private static final String BASE_FONDO = "file:///C:/Users/alanc/Documents/GitHub/TequilaMusic/TequilaMusic/src/recursos/fondos/TequilaPhoto";
    private static final String ICONO_NEGRO = "/recursos/iconos/TequilaMusicBlack.png";
    private static final String ICONO_BLANCO = "/recursos/iconos/TequilaMusicWhite.png";
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        servidor = conectar();
        cargarIniciarSesion();
        iniciarCronometro();
    }

    public void cargarIniciarSesion() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/presentacion/vistas/modIniciarSesion.fxml"));
        ModIniciarSesionController controller = new ModIniciarSesionController();
        controller.setParent(this);
        controller.setServidor(servidor);
        loader.setController(controller);

        try {
            Utilerias.fadeTransition(contentPane);
            contentPane.getChildren().setAll((AnchorPane) loader.load());
        } catch (IOException ex) {
            Logger.getLogger(IUInicioController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void cargarRegistro() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/presentacion/vistas/modCrearCuenta.fxml"));
        ModCrearCuentaController controller = new ModCrearCuentaController();
        controller.setParent(this);
        controller.setServidor(servidor);
        loader.setController(controller);

        try {
            Utilerias.fadeTransition(contentPane);
            contentPane.getChildren().setAll((AnchorPane) loader.load());
        } catch (IOException ex) {
            Logger.getLogger(IUInicioController.class.getName()).log(Level.SEVERE, null, ex);
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

    public Client conectar() {
        Client servidor = null;
        try {
            TTransport transport;
            transport = new TSocket(HOST, 9090);
            transport.open();
            TProtocol protocol = new TBinaryProtocol(transport);
            servidor = new Client(protocol);
        } catch (TTransportException ex) {
            Logger.getLogger(IUInicioController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return servidor;
    }
    
    public void iniciarCronometro() {
        cronometro = new Timeline(new KeyFrame(Duration.seconds(1), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (TIEMPO_CRONOMETRO == 0) {
                    cambiarFondo();
                    TIEMPO_CRONOMETRO = TIEMPO_CAMBIO + 1;
                }
                TIEMPO_CRONOMETRO--;
            }
        }));
        cronometro.setCycleCount(Timeline.INDEFINITE);
        cronometro.play();
    }
    
    public void cambiarFondo() {
        int numImagen = ThreadLocalRandom.current().nextInt(1, 7);
        while (numImagen == numFondoActual) {            
            numImagen = ThreadLocalRandom.current().nextInt(1, 7);
        }
        numFondoActual = numImagen;
        String datosFondo = "";
        String style = "";
        switch (numImagen) {
            case 1:
                datosFondo = "Zaret Sahar Jahzeel Roque\nParque Juárez, Xalapa";
                style = "-fx-text-fill: black;";
                imgIcono.setImage(new Image(ICONO_NEGRO));
                break;
            case 2:
                datosFondo = "Esmeralda Jimenez Ramos\nFacultad de Estadística e Informática";
                style = "-fx-text-fill: white;";
                imgIcono.setImage(new Image(ICONO_NEGRO));
                break;
            case 3:
                datosFondo = "Laura Giselle Vázquez Casas\nFacultad de Estadística e Informática";
                style = "-fx-text-fill: white;";
                imgIcono.setImage(new Image(ICONO_BLANCO));
                break;
            case 4:
                datosFondo = "Miguel Alejandro Cámara Árciga\nParque Juárez, Xalapa";
                style = "-fx-text-fill: black;";
                imgIcono.setImage(new Image(ICONO_NEGRO));
                break;
            case 5:
                datosFondo = "Alberto de Jesús Sánchez López\nAv. Miguel Alemán, Xalapa";
                style = "-fx-text-fill: white;";
                imgIcono.setImage(new Image(ICONO_BLANCO));
                break;
            case 6:
                datosFondo = "Valeria Berenice Reyes Armenta\nCentro Superior de Estudios Turisticos Xalapa";
                style = "-fx-text-fill: white;";
                imgIcono.setImage(new Image(ICONO_NEGRO));
                break;
            case 7:
                datosFondo = "Hernan Uriel Falconi Falconi\nC. Popocatépetl, Xalapa";
                style = "-fx-text-fill: white;";
                imgIcono.setImage(new Image(ICONO_NEGRO));
                break;
        }
        String ruta = BASE_FONDO + numImagen + ".jpg";
        Utilerias.fadeTransitionFast(imgFondo);
        imgFondo.setImage(new Image(ruta));
        hpDatosFondo.setText(datosFondo);
        hpDatosFondo.setStyle(style);
    }
}
