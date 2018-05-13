package presentacion;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Slider;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author Alan Yoset Garcia Cruz
 */
public class IUReproductorController implements Initializable {

    @FXML
    private TextField tfBuscar;
    @FXML
    private ImageView btnPlay11111;
    @FXML
    private Slider sliderVolumen;
    @FXML
    private SplitPane splitPane;
    @FXML
    private AnchorPane paneContenido;
    @FXML
    private ImageView btnAleatorio;
    @FXML
    private ImageView btnAnterior;
    @FXML
    private ImageView btnPlay;
    @FXML
    private ImageView btnSiguiente;
    @FXML
    private ImageView btnRepetir;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void onActionBuscar(KeyEvent event) {
    }

    @FXML
    private void onActionVolume(MouseEvent event) {
    }

    @FXML
    private void onActionTimeline(MouseEvent event) {
    }

    @FXML
    private void onActionRandom(MouseEvent event) {
    }

    @FXML
    private void onActionBack(MouseEvent event) {
    }

    @FXML
    private void onActionPlay(MouseEvent event) {
    }

    @FXML
    private void onActionNext(MouseEvent event) {
    }

    @FXML
    private void onActionRepeat(MouseEvent event) {
    }
    
}
