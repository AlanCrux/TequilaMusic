package presentacion.controladores;

import com.jfoenix.controls.JFXButton;
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
import javafx.stage.Stage;
import org.apache.thrift.TException;
import servicios.Consumidor;
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
        
    }

    @FXML
    private void cargarRegistro(ActionEvent event) {
        parent.cargarRegistro();
    }

    @FXML
    private void onActionIniciarSesion(ActionEvent event) {
        if (validarCampos()) {
            Consumidor consumidor = new Consumidor();
            try {
                consumidor = servidor.obtenerConsumidor(tfUsuario.getText());
            } catch (TException ex) {
                Logger.getLogger(ModIniciarSesionController.class.getName()).log(Level.SEVERE, null, ex);
            }
            if (consumidor.getCorreo() != null) {
                if (tfClave.getText().equals(consumidor.getClave())) {
                    abrirReproductor(consumidor);
                } else {
                    Utilerias.doingTransition(tfClave);
                }
            } else {
                Utilerias.doingTransition(tfUsuario);
            }
        }
    }

    public void setParent(IUInicioController parent) {
        this.parent = parent;
    }

    public void setServidor(Client servidor) {
        this.servidor = servidor;
    }

    public void abrirReproductor(Consumidor consumidor) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/presentacion/vistas/IUReproductor.fxml"));
        IUReproductorController controller = new IUReproductorController();
        loader.setController(controller);
        controller.setConsumidor(consumidor);
        controller.mostrarVentana(loader);
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
