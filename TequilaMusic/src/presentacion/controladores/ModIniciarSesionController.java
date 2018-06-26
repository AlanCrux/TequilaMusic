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

    private IUInicioController parent;
    private Client servidor;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
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

    public void iniciarSesion() {
        if (validarCampos()) {
            Usuario usuario = new Usuario();
            try {
                usuario = servidor.obtenerUsuario(tfUsuario.getText());
                if (usuario.getCorreo() != null) {
                    if (tfClave.getText().equals(usuario.getClave())) {
                        if (usuario.getTipo().equals("consumidor")) {
                            abrirReproductor(usuario, servidor);
                        }else{
                            abrirMenuArtista(usuario, servidor);
                        }
                        
                    } else {
                        Utilerias.doingTransition(tfClave);
                    }
                } else {
                    //AQUI DEBE HABER ALGO MEJOR QUE LE INDIQUE QUE NO EXISTE
                    Utilerias.doingTransition(tfUsuario);
                }
            } catch (TException ex) {
                Utilerias.mostrarErrorConexion(parent.getContentError());
            }
        }
    }

    public void setParent(IUInicioController parent) {
        this.parent = parent;
    }

    public void setServidor(Client servidor) {
        this.servidor = servidor;
    }

    public void abrirReproductor(Usuario usuario, Client servidor) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/presentacion/vistas/IUReproductor.fxml"));
        IUReproductorController controller = new IUReproductorController();
        loader.setController(controller);
        controller.setUsuario(usuario);
        Utilerias.closeServer(servidor);
        Utilerias.mostrarVentanaMax(loader);
        Stage stage = (Stage) btnIniciarSesion.getScene().getWindow();
        stage.close();
    }
    
    public void abrirMenuArtista(Usuario usuario, Client servidor) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/presentacion/vistas/IUArtista.fxml"));
        IUArtistaController controller = new IUArtistaController();
        loader.setController(controller);
        controller.setUsuario(usuario);
        controller.setServidor(servidor);
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
}
