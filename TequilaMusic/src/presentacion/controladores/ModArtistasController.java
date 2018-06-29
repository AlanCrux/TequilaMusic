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
import org.apache.thrift.TException;
import org.apache.thrift.transport.TTransportException;
import servicios.CancionSL;
import servicios.Usuario;
import servicios.servicios;
import servicios.servicios.Client;
import utilerias.Utilerias;

/**
 * FXML Controller class
 *
 * @author Alan Yoset Garcia Cruz
 */
public class ModArtistasController implements Initializable {

    @FXML
    private VBox listArtistas;
    @FXML
    private VBox listCanciones;

    ResourceBundle rb;
    private String correo; 
    IUReproductorController parent; 

    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.rb = rb;
        cargarArtistas();
    }
    
    public void cargarArtistas(){
        List<Usuario> artistas = obtenerArtistas();
        NodeArtistaController controller; 
        int size = artistas.size(); 
        for (int i = 0; i < size; i++) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/presentacion/vistas/nodeArtista.fxml"));
            controller = new NodeArtistaController(); 
            controller.setParent(this);
            controller.setGrandparent(parent);
            controller.setArtista(artistas.get(i));
            loader.setController(controller);
            
            try {
                AnchorPane nodo = (AnchorPane) loader.load();
                listArtistas.getChildren().add(nodo);
                Separator sep = new Separator();
                listArtistas.getChildren().add(sep);
            } catch (IOException ex) {
                Logger.getLogger(ModArtistasController.class.getName()).log(Level.SEVERE, null, ex);
            } 
        }
        
    }

    public List<Usuario> obtenerArtistas() {
        List<Usuario> artistas = new ArrayList<>();
        int port = Integer.parseInt(rb.getString("dataport"));
        String host = rb.getString("datahost");
        Client servicios;
        
        try {
            servicios = Utilerias.conectar(host, port);
            artistas = servicios.obtenerArtistasUsuario(correo);
            Utilerias.closeServer(servicios);
        } catch (TTransportException ex) {
            Logger.getLogger(ModArtistasController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (TException ex) {
            Logger.getLogger(ModArtistasController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return artistas;

    }

    /**
     * Nota: se ingresa a el a través de NodeArtistaController. 
     * @param artista 
     */
    public void cargarCanciones(Usuario artista) {
        listCanciones.getChildren().clear();
        List<CancionSL> canciones = obtenerCanciones(artista);
        NodeCancionListaController controller; 
        int size = canciones.size(); 
        for (int i = 0; i < size; i++) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/presentacion/vistas/nodeCancionLista.fxml"));
            controller = new NodeCancionListaController(); 
            controller.setParent(parent);
            controller.setCancion(canciones.get(i));
            loader.setController(controller);
            
            try {
                AnchorPane nodo = (AnchorPane) loader.load();
                listCanciones.getChildren().add(nodo);
                Separator sep = new Separator();
                listCanciones.getChildren().add(sep);
            } catch (IOException ex) {
                Logger.getLogger(ModArtistasController.class.getName()).log(Level.SEVERE, null, ex);
            } 
        }
    }
    
    public List<CancionSL> obtenerCanciones(Usuario artista) {
        List<CancionSL> canciones = new ArrayList<>();
        int port = Integer.parseInt(rb.getString("dataport"));
        String host = rb.getString("datahost");
        servicios.Client servicios;
        try {
            servicios = Utilerias.conectar(host, port);

            canciones = servicios.obtenerCancionesArtista(correo,artista.getCorreo());

            Utilerias.closeServer(servicios);
        } catch (TTransportException ex) {
            Logger.getLogger(IUReproductorController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (TException ex) {
            Logger.getLogger(ModHistorialController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return canciones;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public void setParent(IUReproductorController parent) {
        this.parent = parent;
    }

    
    
}
