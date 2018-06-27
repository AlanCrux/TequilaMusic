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
import javafx.scene.control.Hyperlink;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import org.apache.thrift.TException;
import servicios.Usuario;
import servicios.servicios;
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
    private JFXRadioButton rdbtnMusico;
    @FXML
    private ToggleGroup tipo;
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
    @FXML
    private Hyperlink hpIniciarSesion;

    private IUInicioController parent;
    private Client servidor;
    private ResourceBundle rb;

    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.rb = rb; 
    }

    @FXML
    private void cargarIniciarSesion(ActionEvent event) {
        parent.cargarIniciarSesion();
    }

    @FXML
    public void onActionRegistrarse(MouseEvent event) {
        if (event.getButton().equals(MouseButton.PRIMARY)) {
            if (!(event.getClickCount() >= 2)) {
                crearCuenta();
            }
        }
    }

    public void crearCuenta() {
        if (validarCampos()) {
            Usuario usuario = obtenerDatosUsuario();
            try {
                if (servidor.insertarUsuario(usuario)) {
                    if (usuario.getTipo().equals("consumidor")) {
                        abrirReproductor(usuario, servidor);
                    } else{
                        abrirMenuArtista(usuario, servidor);
                    }
                } else {
                    System.out.println("YA EXISTE OTRO");
                }
            } catch (TException ex) {
                Logger.getLogger(ModCrearCuentaController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void setParent(IUInicioController parent) {
        this.parent = parent;
    }

    public void setServidor(servicios.Client servidor) {
        this.servidor = servidor;
    }

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

    @FXML
    public void quitarColor() {
        tfNombre.setStyle("-fx-border-color: #FFFFFF");
        tfClave.setStyle("-fx-border-color: #FFFFFF");
        tfClaveConfirmar.setStyle("-fx-border-color: #FFFFFF");
        tfUsuario.setStyle("-fx-border-color: #FFFFFF");
    }

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

    public void abrirReproductor(Usuario usuario, Client servidor) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/presentacion/vistas/IUReproductor.fxml"),rb);
        IUReproductorController controller = new IUReproductorController();
        loader.setController(controller);
        controller.setUsuario(usuario);
        
        Utilerias.mostrarVentana(loader);
        Stage stage = (Stage) btnCrearCuenta.getScene().getWindow();
        stage.close();
    }

    public void abrirMenuArtista(Usuario usuario, Client servidor) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/presentacion/vistas/IUArtista.fxml"),rb);
        IUArtistaController controller = new IUArtistaController();
        loader.setController(controller);
        controller.setUsuario(usuario);
        controller.setServidor(servidor);
        Utilerias.mostrarVentana(loader);
        Stage stage = (Stage) btnCrearCuenta.getScene().getWindow();
        stage.close();
    }
}
