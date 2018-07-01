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
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import org.apache.thrift.TException;
import org.apache.thrift.transport.TTransportException;
import presentacion.controladores.IUReproductorController;
import presentacion.controladores.ModHistorialController;
import servicios.AlbumSL;
import servicios.servicios;
import utilerias.Utilerias;

/**
 * FXML Controller class
 *
 * @author alan
 */
public class ModAlbumBibliotecaController implements Initializable {

    @FXML
    private FlowPane fpContenido;
    
    ResourceBundle rb;
    private IUReproductorController parent;
    private String correo;


    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.rb = rb;
        cargarAlbumnes();
    }    

    public void setParent(IUReproductorController parent) {
        this.parent = parent;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }
    
    public void cargarAlbumnes(){
        List<AlbumSL> albumes = obtenerAlbumes();
        NodeAlbumBibliotecaController controller; 
        int size = albumes.size();
        for (int i = 0; i < size; i++) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/presentacion/vistas/nodeAlbumBiblioteca.fxml"));
            controller = new NodeAlbumBibliotecaController(); 
            controller.setAlbum(albumes.get(i));
            loader.setController(controller);
            try {
                AnchorPane nodo = (AnchorPane) loader.load();
                fpContenido.getChildren().add(nodo); 
            } catch (IOException ex) {
                Logger.getLogger(ModHistorialController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    public List<AlbumSL> obtenerAlbumes() {
        List<AlbumSL> albumes = new ArrayList<>();
        int port = Integer.parseInt(rb.getString("dataport"));
        String host = rb.getString("datahost");
        servicios.Client servicios;
        try {
            servicios = Utilerias.conectar(host, port);
            albumes = servicios.obtenerAlbumesUsuario(correo);
            Utilerias.closeServer(servicios);
        } catch (TTransportException ex) {
            Logger.getLogger(IUReproductorController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (TException ex) {
            Logger.getLogger(ModHistorialController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return albumes;
    }
    
}
