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
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import org.apache.thrift.TException;
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
    private String correo;

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
        tbcPuntuacion.setCellValueFactory(new PropertyValueFactory<>("puntuacionGrafica"));
        tbCanciones.setPlaceholder(new Label("No has agregado canciones a tu biblioteca"));
        tbCanciones.setItems(FXCollections.observableList(obtenerCanciones()));
        tbCanciones.setOnMouseClicked(event -> {
            CancionSL seleccionada = tbCanciones.getSelectionModel().getSelectedItem();
            if (event.getClickCount() == 2) {
                parent.cargarDatosCancion(seleccionada);
            }
        });
        addContextMenu();
    }

    public void setParent(IUReproductorController parent) {
        this.parent = parent;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public List<CancionSL> obtenerCanciones() {
        int port = Integer.parseInt(rb.getString("dataport"));
        String host = rb.getString("datahost");
        List<CancionSL> listaCanciones = new ArrayList<>();
        try {
            servicios.Client servicios = Utilerias.conectar(host, port);
            listaCanciones = servicios.obtenerCancionesBiblioteca(correo);
            Utilerias.closeServer(servicios);
        } catch (TTransportException ex) {
            Logger.getLogger(ModBibliotecaController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (TException ex) {
            Logger.getLogger(ModBibliotecaController.class.getName()).log(Level.SEVERE, null, ex);
        }

        int size = listaCanciones.size();

        for (int i = 0; i < size; i++) {
            listaCanciones.get(i).setCorreoUsuario(correo);
        }
        return listaCanciones;
    }

    public void addContextMenu() {
        ContextMenu context = new ContextMenu();
        MenuItem descargar = new MenuItem("Descargar");

        descargar.setOnAction(e -> {
            parent.descargarCancion(tbCanciones.getSelectionModel().getSelectedItem());
        });
        context.getItems().addAll(descargar);

        tbCanciones.setContextMenu(context);
    }

}
