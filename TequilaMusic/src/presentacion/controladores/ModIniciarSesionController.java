package presentacion.controladores;

import com.jfoenix.controls.JFXButton;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.apache.thrift.TException;
import servicios.Usuario;
import servicios.servicios.Client;
import utilerias.Utilerias;

/**
 * FXML Controller class
 *
 * @author Alan Yoset Garcia Cruz
 */
public class ModIniciarSesionController implements Initializable {

    @FXML
    private TextField tfUsuario;
    @FXML
    private PasswordField tfClave;
    @FXML
    private Hyperlink hpRecuperarClave;
    @FXML
    private Hyperlink hpCrearCuenta;
    @FXML
    private JFXButton btnIniciarSesion;
    @FXML
    private AnchorPane contentError;

    private IUInicioController parent;
    private ResourceBundle rb;

    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.rb = rb;
        tfUsuario.setOnKeyReleased(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                iniciarSesion();
            }
        });

        tfClave.setOnKeyReleased(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                iniciarSesion();
            }
        });
    }

    @FXML
    private void cargarRegistro(ActionEvent event) {
        parent.cargarRegistro();
    }

    @FXML
    private void onActionIniciarSesion(MouseEvent event) {
        if (event.getButton().equals(MouseButton.PRIMARY)) {
            if (!(event.getClickCount() >= 2)) {
                iniciarSesion();
            }
        }
    }

    /**
     * Invoca un método del servidor para validar el inicio de sesión con los
     * datos proporcionados en la IU.
     */
    public void iniciarSesion() {
        if (validarCampos()) {
            Usuario usuario;
            String host = rb.getString("datahost");
            int puerto = Integer.parseInt(rb.getString("dataport"));
            try {
                Client servidor = Utilerias.conectar(host, puerto);
                usuario = servidor.obtenerUsuario(tfUsuario.getText());
                if (usuario.getCorreo() != null) {
                    if (tfClave.getText().equals(usuario.getClave())) {
                        if (usuario.getTipo().equals("consumidor")) {
                            abrirReproductor(usuario);
                        } else {
                            abrirMenuArtista(usuario);
                        }

                    } else {
                        Utilerias.doingTransition(tfClave);
                    }
                } else {
                    Utilerias.doingTransition(tfUsuario);
                }
                Utilerias.closeServer(servidor);
            } catch (TException ex) {
                Utilerias.mostrarErrorConexion(parent.getContentError());
            }
            
        }
    }

    
    public void abrirReproductor(Usuario usuario) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/presentacion/vistas/IUReproductor.fxml"), rb);
        IUReproductorController controller = new IUReproductorController();
        loader.setController(controller);
        controller.setUsuario(usuario);
        Utilerias.mostrarVentanaMax(loader);
        Stage stage = (Stage) btnIniciarSesion.getScene().getWindow();
        stage.close();
    }

    public void abrirMenuArtista(Usuario usuario) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/presentacion/vistas/IUArtista.fxml"), rb);
        IUArtistaController controller = new IUArtistaController();
        loader.setController(controller);
        controller.setUsuario(usuario);
        Utilerias.mostrarVentana(loader);
        Stage stage = (Stage) btnIniciarSesion.getScene().getWindow();
        stage.close();
    }

    public boolean validarCampos() {
        if (tfUsuario.getText().trim().isEmpty()) {
            Utilerias.doingTransition(tfUsuario);
            return false;
        }

        if (tfClave.getText().trim().isEmpty()) {
            Utilerias.doingTransition(tfClave);
            return false;
        }
        return true;
    }

    @FXML
    public void onActionReintentar(ActionEvent event) {

    }

    @FXML
    public void onActionSalir(ActionEvent event) {

    }

    public void setParent(IUInicioController parent) {
        this.parent = parent;
    }
}
