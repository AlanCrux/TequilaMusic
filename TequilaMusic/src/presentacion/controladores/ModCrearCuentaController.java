package presentacion.controladores;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXRadioButton;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import org.apache.thrift.TException;
import org.apache.thrift.transport.TTransportException;
import servicios.Usuario;
import servicios.servicios.Client;
import utilerias.Utilerias;

/**
 * FXML Controller class
 *
 * @author Alan Yoset Garcia Cruz
 */
public class ModCrearCuentaController implements Initializable {

    @FXML
    private TextField tfUsuario;
    @FXML
    private JFXRadioButton rdbtnCliente;
    @FXML
    private TextField tfNombre;
    @FXML
    private PasswordField tfClave;
    @FXML
    private PasswordField tfClaveConfirmar;
    @FXML
    private JFXButton btnCrearCuenta;

    private IUInicioController parent;
    private ResourceBundle rb;

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.rb = rb;
    }

    /**
     * Invoca un método del nodo padre para cambiar el módulo a iniciar sesión.
     *
     * @param event
     */
    @FXML
    private void cargarIniciarSesion(ActionEvent event) {
        parent.cargarIniciarSesion();
    }

    /**
     * Describe el funcionamiento del sistema al hacer clic en el botón
     * registrar.
     *
     * @param event
     */
    @FXML
    public void onActionRegistrarse(MouseEvent event) {
        if (event.getButton().equals(MouseButton.PRIMARY)) {
            if (!(event.getClickCount() >= 2)) {
                crearCuenta();
            }
        }
    }

    /**
     * Invoca un método del servidor para crear una cuenta con los datos
     * insertados en la IU.
     */
    public void crearCuenta() {
        if (validarCampos()) {
            Usuario usuario = obtenerDatosUsuario();
            String host = rb.getString("datahost");
            int puerto = Integer.parseInt(rb.getString("dataport"));
            try {
                Client servidor = Utilerias.conectar(host, puerto);
                if (servidor.insertarUsuario(usuario)) {
                    if (usuario.getTipo().equals("consumidor")) {
                        abrirReproductor(usuario);
                    } else {
                        abrirMenuArtista(usuario);
                    }
                } else {
                    Utilerias.displayInformation("Error!", "Ya existe otro usuario con ese correo");
                }
                Utilerias.closeServer(servidor);
            } catch (TTransportException ex) {
                Logger.getLogger(ModCrearCuentaController.class.getName()).log(Level.SEVERE, null, ex);
            } catch (TException ex) {
                Logger.getLogger(ModCrearCuentaController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    /**
     * Valida si los cuadros de texto de la IU contienen información.
     *
     * @return
     */
    public boolean validarCampos() {
        if (tfUsuario.getText().trim().isEmpty()) {
            Utilerias.doingTransition(tfUsuario);
            return false;
        }

        if (tfNombre.getText().trim().isEmpty()) {
            Utilerias.doingTransition(tfNombre);
            return false;
        }

        if (tfClave.getText().trim().isEmpty()) {
            Utilerias.doingTransition(tfClave);
            return false;
        }

        if (tfClaveConfirmar.getText().trim().isEmpty()) {
            Utilerias.doingTransition(tfClaveConfirmar);
            return false;
        }

        if (!tfClave.getText().equals(tfClaveConfirmar.getText())) {
            tfClaveConfirmar.setStyle("-fx-border-color: #FF0000");
            Utilerias.doingTransition(tfClaveConfirmar);
            return false;
        }
        return true;
    }

    /**
     * Pone en un color especial los colores de borde de los cuadros de texto de
     * la IU.
     */
    @FXML
    public void quitarColor() {
        tfNombre.setStyle("-fx-border-color: #FFFFFF");
        tfClave.setStyle("-fx-border-color: #FFFFFF");
        tfClaveConfirmar.setStyle("-fx-border-color: #FFFFFF");
        tfUsuario.setStyle("-fx-border-color: #FFFFFF");
    }

    /**
     * Crea un objeto de tipo Usuario a partir de los datos insertados en los
     * cuadros de texto de la IU.
     *
     * @return Objeto de tipo usuario con los datos cargados.
     */
    public Usuario obtenerDatosUsuario() {
        Usuario usuario = new Usuario();
        usuario.setClave(tfClave.getText());
        usuario.setCorreo(tfUsuario.getText());
        usuario.setNombre(tfNombre.getText());
        if (rdbtnCliente.isSelected()) {
            usuario.setTipo("consumidor");
        } else {
            usuario.setTipo("artista");
        }
        return usuario;
    }

    /**
     * Abre la IU dedicada al cliente consumidor.
     *
     * @param usuario cliente que ingresa al sistema.
     */
    public void abrirReproductor(Usuario usuario) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/presentacion/vistas/IUReproductor.fxml"), rb);
        IUReproductorController controller = new IUReproductorController();
        loader.setController(controller);
        controller.setUsuario(usuario);

        Utilerias.mostrarVentana(loader);
        Stage stage = (Stage) btnCrearCuenta.getScene().getWindow();
        stage.close();
    }

    /**
     * Abre la IU dedicada al artista.
     *
     * @param usuario artista que ingresa al sistema.
     */
    public void abrirMenuArtista(Usuario usuario) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/presentacion/vistas/IUArtista.fxml"), rb);
        IUArtistaController controller = new IUArtistaController();
        loader.setController(controller);
        controller.setUsuario(usuario);
        Utilerias.mostrarVentanaMax(loader);
        Stage stage = (Stage) btnCrearCuenta.getScene().getWindow();
        stage.close();
    }

    public void setParent(IUInicioController parent) {
        this.parent = parent;
    }
}
