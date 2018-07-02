package presentacion.controladores;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import org.apache.thrift.TException;
import servicios.CancionSL;
import servicios.servicios;
import utilerias.Utilerias;

/**
 * FXML Controller class
 *
 * @author Esmeralda
 */
public class ModCancionesController implements Initializable {

    @FXML
    private TableView<CancionSL> tablaCanciones;
    @FXML
    private TableColumn<CancionSL, String> tcTitulo;
    @FXML
    private TableColumn<CancionSL, Integer> tcAlbum;
    @FXML
    private TableColumn<CancionSL, Integer> tcGenero;

    private String correo;
    private IUArtistaController parent;

    private ResourceBundle rb;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        tcAlbum.setCellValueFactory(new PropertyValueFactory<>("album"));
        tcTitulo.setCellValueFactory(new PropertyValueFactory<>("titulo"));
        tcGenero.setCellValueFactory(new PropertyValueFactory<>("genero"));
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public IUArtistaController getParent() {
        return parent;
    }

    public void setParent(IUArtistaController parent) {
        this.parent = parent;
    }

    public ResourceBundle getRb() {
        return rb;
    }

    public void setRb(ResourceBundle rb) {
        this.rb = rb;
    }
    
    

    public void mostrarResultados(String criterio) {
        List<CancionSL> resultados = new ArrayList<>();
        int port = Integer.parseInt(rb.getString("dataport"));
        String host = rb.getString("datahost");
        servicios.Client servicios;
        try {
            servicios = Utilerias.conectar(host, port);
            resultados = servicios.obtenerCancionesDelArtista(criterio);
            Utilerias.closeServer(servicios);
        } catch (TException ex) {
            Logger.getLogger(ModBuscarCancionesController.class.getName()).log(Level.SEVERE, null, ex);
        }
        tablaCanciones.setItems(FXCollections.observableList(resultados));
    }

}
