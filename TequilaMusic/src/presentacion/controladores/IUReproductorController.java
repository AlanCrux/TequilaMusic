package presentacion.controladores;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXSlider;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import servicios.Consumidor;
import utilerias.Utilerias;

/**
 * FXML Controller class
 *
 * @author Alan Yoset Garcia Cruz
 */
public class IUReproductorController implements Initializable {

    @FXML
    private JFXSlider sliderVolumen;
    @FXML
    private TextField tfBuscar;
    @FXML
    private JFXButton btnBiblioteca;
    @FXML
    private JFXButton btnRadio;
    @FXML
    private JFXButton btnExplorar;
    @FXML
    private AnchorPane contentPane;

    private static final String TAB_SELECCIONADA = "-fx-background-color: #1ABC9C; -fx-background-radius: 30; -fx-border-radius: 30;";
    private static final String TAB_BASE = "-fx-background-color: #2f7aff; -fx-background-radius: 30; -fx-border-radius: 30;";
    
    Consumidor consumidor; 

    /**
     * Inicializa los componentes de la ventana IUReproductor.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        btnBiblioteca.setStyle(TAB_SELECCIONADA);
        btnExplorar.setStyle(TAB_BASE);
        btnRadio.setStyle(TAB_BASE);
    }

    @FXML
    private void onActionBack(MouseEvent event) {

    }

    @FXML
    void onActionBiblioteca(ActionEvent event) {
        btnBiblioteca.setStyle(TAB_SELECCIONADA);
        btnExplorar.setStyle(TAB_BASE);
        btnRadio.setStyle(TAB_BASE);
        cargarBiblioteca();
    }

    @FXML
    private void onActionBuscar(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            //tbCanciones.setItems(FXCollections.observableList(obtenerCanciones(tfBuscar.getText())));
        }
    }

    @FXML
    void onActionNext(MouseEvent event) {

    }

    @FXML
    void onActionRadio(ActionEvent event) {
        btnBiblioteca.setStyle(TAB_BASE);
        btnExplorar.setStyle(TAB_BASE);
        btnRadio.setStyle(TAB_SELECCIONADA);
        cargarRadio();
    }

    @FXML
    void onActionExplorar(ActionEvent event) {
        btnBiblioteca.setStyle(TAB_BASE);
        btnExplorar.setStyle(TAB_SELECCIONADA);
        btnRadio.setStyle(TAB_BASE);
        cargarExplorar();
    }

    @FXML
    void onActionRandom(MouseEvent event) {

    }

    @FXML
    void onActionRepeat(MouseEvent event) {

    }

    @FXML
    void onActionTimeline(MouseEvent event) {

    }

    @FXML
    void onActionVolume(MouseEvent event) {

    }

    public void cargarBiblioteca() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/presentacion/vistas/modBiblioteca.fxml"));
        ModBibliotecaController controller = new ModBibliotecaController();
        controller.setParent(this);
        loader.setController(controller);

        try {
            Utilerias.fadeTransition(contentPane);
            contentPane.getChildren().setAll((AnchorPane) loader.load());
        } catch (IOException ex) {
            Logger.getLogger(IUInicioController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void cargarExplorar() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/presentacion/vistas/modExplorar.fxml"));
        //ModBibliotecaController controller = new ModBibliotecaController();
        //controller.setParent(this);
        //loader.setController(controller);

        try {
            Utilerias.fadeTransition(contentPane);
            contentPane.getChildren().setAll((AnchorPane) loader.load());
        } catch (IOException ex) {
            Logger.getLogger(IUInicioController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void cargarRadio() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/presentacion/vistas/modRadio.fxml"));
        //ModBibliotecaController controller = new ModBibliotecaController();
        //controller.setParent(this);
        //loader.setController(controller);

        try {
            Utilerias.fadeTransition(contentPane);
            contentPane.getChildren().setAll((AnchorPane) loader.load());
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

    public void setConsumidor(Consumidor consumidor) {
        this.consumidor = consumidor;
    }
    
    
}
