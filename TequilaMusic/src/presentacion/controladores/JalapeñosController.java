package presentacion.controladores;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import org.apache.thrift.TException;
import org.apache.thrift.transport.TTransportException;
import servicios.Biblioteca;
import servicios.CancionSL;
import servicios.servicios;
import utilerias.Utilerias;

/**
 * FXML Controller class
 *
 * @author Alan Yoset Garcia Cruz
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

    private CancionSL parent;
    private ResourceBundle rb;
    private String correo;

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.rb = ResourceBundle.getBundle("recursos.serverproperties");
        int numero = parent.getPuntuacion();
        colorearChiles(numero);
    }

    @FXML
    public void efectoMouseSobre(MouseEvent event) {
        ImageView ficha = (ImageView) event.getSource();
        String letra = ficha.getId();
        int numero = 0;

        switch (letra) {
            case "A":
                numero = 1;
                break;
            case "B":
                numero = 2;
                break;
            case "C":
                numero = 3;
                break;
            case "D":
                numero = 4;
                break;
            case "E":
                numero = 5;
                break;
        }

        colorearChiles(numero);
        actualizarPuntuacion(numero);
    }

    public void colorearChiles(int numero) {
        parent.setPuntuacion(numero);
        switch (numero) {
            case 1:
                A.setOpacity(1);
                B.setOpacity(0.5);
                C.setOpacity(0.5);
                D.setOpacity(0.5);
                E.setOpacity(0.5);
                break;
            case 2:
                A.setOpacity(1);
                B.setOpacity(1);
                C.setOpacity(0.5);
                D.setOpacity(0.5);
                E.setOpacity(0.5);
                break;
            case 3:
                A.setOpacity(1);
                B.setOpacity(1);
                C.setOpacity(1);
                D.setOpacity(0.5);
                E.setOpacity(0.5);
                break;
            case 4:
                A.setOpacity(1);
                B.setOpacity(1);
                C.setOpacity(1);
                D.setOpacity(1);
                E.setOpacity(0.5);
                break;
            case 5:
                A.setOpacity(1);
                B.setOpacity(1);
                C.setOpacity(1);
                D.setOpacity(1);
                E.setOpacity(1);
                break;
        }
    }

    public void setParent(CancionSL parent) {
        this.parent = parent;
    }

    public void actualizarPuntuacion(int numero) {
        int port = Integer.parseInt(rb.getString("dataport"));
        String host = rb.getString("datahost");
        servicios.Client servicios;
        Biblioteca b = new Biblioteca(); 
        b.setCorreo(correo);
        b.setIdCancion(parent.getIdCancion());
        b.setPuntuacion(numero);
        try {
            servicios = Utilerias.conectar(host, port);
            servicios.actualizarPuntuacion(b);
            Utilerias.closeServer(servicios);
        } catch (TTransportException ex) {
            Logger.getLogger(JalapeñosController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (TException ex) {
            Logger.getLogger(JalapeñosController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

}
