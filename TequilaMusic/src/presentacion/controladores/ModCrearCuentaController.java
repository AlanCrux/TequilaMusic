package presentacion.controladores;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXRadioButton;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import servicios.servicios;
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
    private servicios.Client servidor;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    private void cargarIniciarSesion(ActionEvent event) {
        parent.cargarIniciarSesion();
    }

    @FXML
    public void onActionRegistrarse(ActionEvent event) {
        if (validarCampos()) {
            
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
        return true;
    }
}
