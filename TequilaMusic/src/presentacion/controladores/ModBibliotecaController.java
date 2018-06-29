package presentacion.controladores;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import org.apache.thrift.transport.TTransportException;
import servicios.CancionSL;
import servicios.servicios;
import utilerias.Utilerias;

/**
 * FXML Controller class
 *
 * @author Alan Yoset Garcia Cruz
 */
public class ModBibliotecaController implements Initializable {

    @FXML
    private TableView<CancionSL> tbCanciones;
    @FXML
    private TableColumn<CancionSL, String> tbcTitulo;
    @FXML
    private TableColumn<CancionSL, String> tbcAlbum;
    @FXML
    private TableColumn<CancionSL, String> tbcArtista;
    @FXML
    private TableColumn<CancionSL, String> tbcGenero;
    @FXML
    private TableColumn<CancionSL, AnchorPane> tbcPuntuacion;

    private ResourceBundle rb;
    private IUReproductorController parent;

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.rb = rb;
        tbcTitulo.setCellValueFactory(new PropertyValueFactory<>("titulo"));
        tbcArtista.setCellValueFactory(new PropertyValueFactory<>("artista"));
        tbcAlbum.setCellValueFactory(new PropertyValueFactory<>("album"));
        tbcGenero.setCellValueFactory(new PropertyValueFactory<>("genero"));
        tbcPuntuacion.setCellValueFactory(new PropertyValueFactory<>("album"));
        tbCanciones.setPlaceholder(new Label("No has agregado canciones a esta playlist"));
    }

    public void setParent(IUReproductorController parent) {
        this.parent = parent;
    }

    public void obtenerCanciones() {
        int port = Integer.parseInt(rb.getString("dataport"));
        String host = rb.getString("datahost");
        try {
            servicios.Client servicios = Utilerias.conectar(host, port);
        } catch (TTransportException ex) {
            Logger.getLogger(ModBibliotecaController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
