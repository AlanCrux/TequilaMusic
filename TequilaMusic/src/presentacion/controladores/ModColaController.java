package presentacion.controladores;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Separator;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import servicios.CancionSL;

/**
 * FXML Controller class
 *
 * @author Alan Yoset Garcia Cruz
 */
public class ModColaController implements Initializable {

    @FXML
    private VBox listCanciones;
    
    private List<CancionSL> colafija = new ArrayList<>(); 
    private List<CancionSL> colaDinamica = new ArrayList<>(); 

    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }    
    
    /**
     * Recarga la IU de la cola. 
     */
    public void recargarItems(){
        listCanciones.getChildren().clear();
        int size = colafija.size();
        NodeEncolaController controller; 
        for (int i = 0; i < size; i++) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/presentacion/vistas/nodeEncola.fxml"));
            controller = new NodeEncolaController(); 
            controller.setCancion(colafija.get(i));
            loader.setController(controller);
            try {
                AnchorPane nodo = (AnchorPane) loader.load();
                listCanciones.getChildren().add(nodo); 
                Separator sep = new Separator();
                listCanciones.getChildren().add(sep);
            } catch (IOException ex) {
                Logger.getLogger(ModColaController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    /**
     * Remplaza el contenido de la cola dinamica por el de la lista recibida. 
     * @param colaDinamica nueva lista dinamica. 
     */
    public void setColaDinamica(List<CancionSL> colaDinamica) {
        this.colaDinamica = colaDinamica;
    }
    
    /**
     * Agrega una canción al final de la cola. 
     * @param cancion 
     */
    public void agregarFinal(CancionSL cancion){
        this.colafija.add(cancion);
        recargarItems();
    }
    
    /**
     * Agrega una canción al inicio de la cola. 
     * @param cancion 
     */
    public void agregarInicio(CancionSL cancion){
        List<CancionSL> nueva = new ArrayList<>(); 
        nueva.add(cancion);
        nueva.addAll(colafija);
        colafija = nueva; 
        recargarItems();
    }
    
    /**
     * Devuleve la suma del número de canciones pendientes en las colas. 
     * @return 
     */
    public int cancionesPendiente(){
        return colafija.size() + colaDinamica.size(); 
    }
    
    /**
     * Devuelve la siguiente canción en la cola. 
     * @return CancionSL, siguiente en la cola. 
     */
    public CancionSL obtenerSiguiente(){
        CancionSL cancion;
        if (colafija.size() > 0) {
            cancion = colafija.get(0);
            colafija.remove(0); 
            recargarItems();
            return cancion;
        } else {
            cancion = colaDinamica.get(0);
            colaDinamica.remove(0); 
            recargarItems();
            return cancion; 
        }
    }
}
