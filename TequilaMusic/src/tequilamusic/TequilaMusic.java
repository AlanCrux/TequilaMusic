package tequilamusic;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import presentacion.IUReproductorController;

/**
 *
 * @author Alan Yoset Garcia Cruz
 */
public class TequilaMusic extends Application {

    @Override
    public void start(Stage primaryStage) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/presentacion/IUReproductor.fxml"));
        IUReproductorController controller = new IUReproductorController();
        loader.setController(controller);
        controller.mostrarVentana(loader);
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}