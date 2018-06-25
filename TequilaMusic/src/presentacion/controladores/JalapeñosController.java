package presentacion.controladores;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

/**
 * FXML Controller class
 *
 * @author alanc
 */
public class JalapeñosController implements Initializable {

    @FXML
    private ImageView A;
    @FXML
    private ImageView B;
    @FXML
    private ImageView C;
    @FXML
    private ImageView D;
    @FXML
    private ImageView E;
    private int contador = 0; 

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    public void efectoMouseSobre(MouseEvent event) {
        ImageView ficha = (ImageView) event.getSource();
        
        String letra = ficha.getId();
        switch(letra){
            case "A":
                A.setOpacity(1);
                B.setOpacity(0.5);
                C.setOpacity(0.5);
                D.setOpacity(0.5);
                E.setOpacity(0.5);
//                A.setImage(new Image("/recursos/iconos/puntuacion.png"));
//                B.setImage(new Image("/recursos/iconos/puntuaciongris.png"));
//                C.setImage(new Image("/recursos/iconos/puntuaciongris.png"));
//                D.setImage(new Image("/recursos/iconos/puntuaciongris.png"));
//                E.setImage(new Image("/recursos/iconos/puntuaciongris.png"));
                break;
            case "B":
                A.setOpacity(1);
                B.setOpacity(1);
                C.setOpacity(0.5);
                D.setOpacity(0.5);
                E.setOpacity(0.5);
//                A.setImage(new Image("/recursos/iconos/puntuacion.png"));
//                B.setImage(new Image("/recursos/iconos/puntuacion.png"));
//                C.setImage(new Image("/recursos/iconos/puntuaciongris.png"));
//                D.setImage(new Image("/recursos/iconos/puntuaciongris.png"));
//                E.setImage(new Image("/recursos/iconos/puntuaciongris.png"));
                break;
            case "C":
                A.setOpacity(1);
                B.setOpacity(1);
                C.setOpacity(1);
                D.setOpacity(0.5);
                E.setOpacity(0.5);
//                A.setImage(new Image("/recursos/iconos/puntuacion.png"));
//                B.setImage(new Image("/recursos/iconos/puntuacion.png"));
//                C.setImage(new Image("/recursos/iconos/puntuacion.png"));
//                D.setImage(new Image("/recursos/iconos/puntuaciongris.png"));
//                E.setImage(new Image("/recursos/iconos/puntuaciongris.png"));
                break; 
            case "D":
                A.setOpacity(1);
                B.setOpacity(1);
                C.setOpacity(1);
                D.setOpacity(1);
                E.setOpacity(0.5);
//                A.setImage(new Image("/recursos/iconos/puntuacion.png"));
//                B.setImage(new Image("/recursos/iconos/puntuacion.png"));
//                C.setImage(new Image("/recursos/iconos/puntuacion.png"));
//                D.setImage(new Image("/recursos/iconos/puntuacion.png")); 
//                E.setImage(new Image("/recursos/iconos/puntuaciongris.png"));
                break; 
            case "E":
                A.setOpacity(1);
                B.setOpacity(1);
                C.setOpacity(1);
                D.setOpacity(1);
                E.setOpacity(1);
//                A.setImage(new Image("/recursos/iconos/puntuacion.png"));
//                B.setImage(new Image("/recursos/iconos/puntuacion.png"));
//                C.setImage(new Image("/recursos/iconos/puntuacion.png"));
//                D.setImage(new Image("/recursos/iconos/puntuacion.png"));
//                E.setImage(new Image("/recursos/iconos/puntuacion.png"));
                break; 
        }
    }
}
