package presentacion.controladores;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author alan
 */
public class IUInicioController implements Initializable {

    @FXML
    private AnchorPane contentPane;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cargarIniciarSesion();
    }

    public void cargarIniciarSesion() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/presentacion/vistas/modIniciarSesion.fxml"));
        ModIniciarSesionController controller = new ModIniciarSesionController();
        controller.setParent(this);
        loader.setController(controller);
        
        try {
            contentPane.getChildren().setAll((AnchorPane)loader.load());
        } catch (IOException ex) {
            Logger.getLogger(IUInicioController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void cargarRegistro() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/presentacion/vistas/modCrearCuenta.fxml"));
        ModCrearCuentaController controller = new ModCrearCuentaController();
        controller.setParent(this);
        loader.setController(controller);
        
        try {
            contentPane.getChildren().setAll((AnchorPane)loader.load());
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

}
