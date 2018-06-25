package tequilamusic;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import presentacion.controladores.IUInicioController;
import utilerias.Utilerias;

/**
 *
 * @author Alan Yoset Garcia Cruz
 */
public class TequilaMusic extends Application {

    @Override
    public void start(Stage primaryStage) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/presentacion/vistas/IUInicio.fxml"));
        IUInicioController controller = new IUInicioController();
        loader.setController(controller);
        Utilerias.mostrarVentana(loader);
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
