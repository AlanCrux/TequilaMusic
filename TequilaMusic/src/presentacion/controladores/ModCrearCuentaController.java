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
    
    public void setParent(IUInicioController parent) {
        this.parent = parent;
    }
}
