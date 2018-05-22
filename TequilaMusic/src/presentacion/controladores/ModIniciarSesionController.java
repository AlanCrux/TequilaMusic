package presentacion.controladores;

import com.jfoenix.controls.JFXButton;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

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
    private Hyperlink hpDatosFondo;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
