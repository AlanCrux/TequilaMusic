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
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import org.apache.thrift.TException;
import org.apache.thrift.transport.TTransportException;
import servicios.CancionSL;
import servicios.Contenido;
import servicios.Playlist;
import servicios.servicios;
import utilerias.Utilerias;

/**
 * FXML Controller class
 *
 * @author alanc
 */
public class ModBuscarCancionesController implements Initializable {

    @FXML
    private TableView<CancionSL> tbCanciones;
    @FXML
    private TableColumn<CancionSL, String> tbcTitulo;
    @FXML
    private TableColumn<CancionSL, String> tbcArtista;
    @FXML
    private TableColumn<CancionSL, String> tbcAlbum;
    @FXML
    private TableColumn<CancionSL, String> tbcGenero;

    private IUReproductorController parent;

    private ResourceBundle rb;

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        tbcTitulo.setCellValueFactory(new PropertyValueFactory<>("titulo"));
        tbcArtista.setCellValueFactory(new PropertyValueFactory<>("artista"));
        tbcAlbum.setCellValueFactory(new PropertyValueFactory<>("album"));
        tbcGenero.setCellValueFactory(new PropertyValueFactory<>("genero"));

        tbCanciones.setPlaceholder(new Label("Sin resultados"));

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

    public void mostrarResultados(String criterio) {
        List<CancionSL> resultados = new ArrayList<>();
        int port = Integer.parseInt(rb.getString("dataport"));
        String host = rb.getString("datahost");
        servicios.Client servicios;
        try {
            servicios = Utilerias.conectar(host, port);
            resultados = servicios.obtenerCancionesFiltradas(criterio);
            Utilerias.closeServer(servicios);
        } catch (TException ex) {
            Logger.getLogger(ModBuscarCancionesController.class.getName()).log(Level.SEVERE, null, ex);
        }
        parent.setResultadosBusqueda(resultados);
        tbCanciones.setItems(FXCollections.observableList(resultados));
    }

    public void addContextMenu() {
        ContextMenu context = new ContextMenu();
        MenuItem menuBiblioteca = new MenuItem("Añadir a la biblioteca");
        MenuItem menuCola = new MenuItem("Añadir a la cola");
        MenuItem menuInicio = new MenuItem("Añadir al inicio");
        MenuItem menuRadio = new MenuItem("Generar radio");
        Menu menuPlaylist = new Menu("Añadir a playlist");
        MenuItem nueva = new MenuItem("Nueva playlist");
        nueva.setOnAction(event -> {
            parent.onNuevaLista(event);
        });
        menuPlaylist.getItems().add(nueva);
        SeparatorMenuItem separator = new SeparatorMenuItem();
        menuPlaylist.getItems().add(separator);

        List<Playlist> playlist = parent.getListas();
        for (int i = 0; i < playlist.size(); i++) {
            MenuItem nuevo = new MenuItem(playlist.get(i).getNombre());
            final int idPlaylist = playlist.get(i).getIdPlaylist();
            nuevo.setOnAction(e -> {
                addToPlaylist(idPlaylist);
            });
            menuPlaylist.getItems().add(nuevo);
        }

        menuBiblioteca.setOnAction(e -> {
            parent.agregarBiblioteca(tbCanciones.getSelectionModel().getSelectedItem().getIdCancion());
        });

        menuInicio.setOnAction(e -> {
            parent.agregarInicio(tbCanciones.getSelectionModel().getSelectedItem());
        });

        menuCola.setOnAction(e -> {
            parent.agregarFinal(tbCanciones.getSelectionModel().getSelectedItem());
        });

        context.getItems().addAll(menuBiblioteca, menuCola, menuInicio, menuRadio, menuPlaylist);

        tbCanciones.setContextMenu(context);
    }

    public void addToPlaylist(int idPlaylist) {
        int idCancion = tbCanciones.getSelectionModel().getSelectedItem().getIdCancion();
        Contenido contenido = new Contenido(idCancion, idPlaylist);
        int port = Integer.parseInt(rb.getString("dataport"));
        String host = rb.getString("datahost");
        servicios.Client servicios;
        try {
            servicios = Utilerias.conectar(host, port);
            servicios.insertarCancionPlaylist(contenido);
            Utilerias.closeServer(servicios);
        } catch (TTransportException ex) {
            Logger.getLogger(IUReproductorController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (TException ex) {
            Logger.getLogger(IUReproductorController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void setRb(ResourceBundle rb) {
        this.rb = rb;
    }

}
