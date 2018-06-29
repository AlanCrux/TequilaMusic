package presentacion.controladores;

import com.jfoenix.controls.JFXListView;
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
import javafx.scene.layout.AnchorPane;
import servicios.CancionSL;

/**
 * FXML Controller class
 *
 * @author Alan Yoset Garcia Cruz
 */
public class ModColaController implements Initializable {

    @FXML
    private JFXListView<AnchorPane> listCanciones = new JFXListView<>();
    
    private List<CancionSL> colafija = new ArrayList<>(); 
    private List<CancionSL> colaDinamica = new ArrayList<>(); 

    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        int size = colafija.size(); 
        NodeEncolaController controller; 
        for (int i = 0; i < size; i++) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/presentacion/vistas/nodeEncola.fxml"));
            controller = new NodeEncolaController(); 
            controller.setCancion(colafija.get(i));
            loader.setController(controller);
            try {
                AnchorPane nodo = (AnchorPane) loader.load();
                listCanciones.getItems().add(nodo); 
            } catch (IOException ex) {
                Logger.getLogger(ModColaController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }    
    
    public void recargarItems(){
        listCanciones.getItems().clear();
        int size = colafija.size(); 
        NodeEncolaController controller; 
        for (int i = 0; i < size; i++) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/presentacion/vistas/nodeEncola.fxml"));
            controller = new NodeEncolaController(); 
            controller.setCancion(colafija.get(i));
            loader.setController(controller);
            try {
                AnchorPane nodo = (AnchorPane) loader.load();
                listCanciones.getItems().add(nodo); 
            } catch (IOException ex) {
                Logger.getLogger(ModColaController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        imprimirCola();
    }

    public void setColaDinamica(List<CancionSL> colaDinamica) {
        this.colaDinamica = colaDinamica;
    }
    
    public void agregarFinal(CancionSL cancion){
        this.colafija.add(cancion);
        recargarItems();
    }
    
    public void agregarInicio(CancionSL cancion){
        List<CancionSL> nueva = new ArrayList<>(); 
        nueva.add(cancion);
        nueva.addAll(colafija);
        colafija = nueva; 
        recargarItems();
    }
    
    public int cancionesPendiente(){
        return colafija.size() + colaDinamica.size(); 
    }
    
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
    
    public void imprimirCola(){
        for (int i = 0; i < colafija.size(); i++) {
            System.out.println(colafija.get(i).getTitulo());
        }
        
        System.out.println(" ---------------------------- ");
    }
}
