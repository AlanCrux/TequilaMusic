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
import javafx.stage.Stage;

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

    private IUInicioController parent;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    private void cargarRegistro(ActionEvent event) {
        parent.cargarRegistro();
    }

    @FXML
    private void onActionIniciarSesion(ActionEvent event) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/presentacion/vistas/IUReproductor.fxml"));
        IUReproductorController controller = new IUReproductorController();
        loader.setController(controller);
        controller.mostrarVentana(loader);
        Stage stage = (Stage) btnIniciarSesion.getScene().getWindow();
        stage.close();
    }

    public void setParent(IUInicioController parent) {
        this.parent = parent;
    }

}
