/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package presentacion.controladores;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import org.apache.thrift.TException;
import org.apache.thrift.transport.TTransportException;
import servicios.AlbumSL;
import servicios.CancionSL;
import servicios.Usuario;
import servicios.servicios;
import utilerias.Utilerias;

/**
 * FXML Controller class
 *
 * @author Esmeralda
 */
public class ModCancionesAlbumController implements Initializable {

    @FXML
    private TableView<CancionSL> tablaCanciones;

    @FXML
    private Label tfResumen;

    @FXML
    private TableColumn<CancionSL, String> tcTitulo;

    @FXML
    private TableColumn<CancionSL, String> tcAlbum;

    @FXML
    private TableColumn<CancionSL, String> tcGenero;

    @FXML
    private ImageView imageAlbum;

    @FXML
    private Label lbTitulo;

    @FXML
    private Label lbDiscografia;

    @FXML
    private Label lbAnio;

    @FXML
    private Hyperlink btnEditar;

    private AlbumSL album;
    private ResourceBundle rb;
    private NodeAlbumController parent;
    Usuario usuario;
    
    IUAgregarCancionAlbumController modAgregarCancion;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.rb = rb;
        tcAlbum.setCellValueFactory(new PropertyValueFactory<>("album"));
        tcGenero.setCellValueFactory(new PropertyValueFactory<>("genero"));
        tcTitulo.setCellValueFactory(new PropertyValueFactory<>("titulo"));
        tablaCanciones.setPlaceholder(new Label("No has agregado canciones a este álbum"));
        cargarDatosAlbum(obtenerCanciones(album.getIdAlbum()));
    }

    public void cargarDatosAlbum(List<CancionSL> canciones) {
        imageAlbum.setImage(Utilerias.byteToImage(album.getImagenAlbum()));
        lbTitulo.setText(album.getTitulo());
        lbDiscografia.setText(album.getCompaniaDiscografica());
        lbAnio.setText(album.getAnioLanzamiento());
        System.out.println("datos album" + album.getTitulo());
        System.out.println("tamaño " + canciones.size());
        if (canciones.size() > 1) {
            tfResumen.setText(canciones.size() + " canciones");
        } else {
            tfResumen.setText(canciones.size() + " canción");
        }
        tablaCanciones.setItems(FXCollections.observableList(canciones));
    }

    public List<CancionSL> obtenerCanciones(int idAlbum) {
        List<CancionSL> canciones = new ArrayList<>();
        int port = Integer.parseInt(rb.getString("dataport"));
        String host = rb.getString("datahost");
        try {
            servicios.Client servicios = Utilerias.conectar(host, port);
            canciones = servicios.obtenerCancionesAlbumArtista(idAlbum);
            System.out.println("Tamano en obtCa" + canciones.size());
            Utilerias.closeServer(servicios);
        } catch (TTransportException ex) {
            Logger.getLogger(ModPlaylistController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (TException ex) {
            Logger.getLogger(ModPlaylistController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return canciones;

    }

    public AlbumSL getAlbum() {
        return album;
    }

    public void setAlbum(AlbumSL album) {
        this.album = album;
    }

    public ResourceBundle getRb() {
        return rb;
    }

    public void setRb(ResourceBundle rb) {
        this.rb = rb;
    }

    public NodeAlbumController getParent() {
        return parent;
    }

    public void setParent(NodeAlbumController parent) {
        this.parent = parent;
    }

    @FXML
    void editar(ActionEvent event) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/presentacion/vistas/IUAgregarCancionAlbum.fxml"), rb);
        modAgregarCancion = new IUAgregarCancionAlbumController();
        modAgregarCancion.setParent(this);
        loader.setController(modAgregarCancion);
        modAgregarCancion.setUsuario(usuario);
        modAgregarCancion.setAlbum(album);
        modAgregarCancion.mostrarVentana(loader, (Stage) lbAnio.getScene().getWindow());

    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
    
   

}
