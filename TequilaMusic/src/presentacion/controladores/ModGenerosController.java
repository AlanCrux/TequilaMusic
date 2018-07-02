package presentacion.controladores;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
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
import javafx.scene.Cursor;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import org.apache.thrift.TException;
import org.apache.thrift.transport.TTransportException;
import servicios.CancionSL;
import servicios.Genero;
import servicios.servicios;
import utilerias.Utilerias;

/**
 * FXML Controller class
 *
 * @author alan
 */
public class ModGenerosController implements Initializable {

    @FXML
    private VBox listGeneros;
    @FXML
    private VBox listCanciones;

    ResourceBundle rb;
    private String correo;
    IUReproductorController parent;
    List<Genero> generos;

    private static final String ICON_GENERO = "src/recursos/iconos/puntos_tequila.png";

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.rb = rb;
        generos = new ArrayList<>();
        cargarGeneros();
    }

    public void cargarGeneros() {
        generos = obtenerGeneros();
        Label genero;
        int size = generos.size();
        for (int i = 0; i < size; i++) {
            final int index = i;
            genero = new Label(generos.get(i).getNombreGenero());
            genero.setFont(new Font("Avenir Book", 16));
            try {
                genero.setGraphic(new ImageView(new Image(new FileInputStream(ICON_GENERO))));
            } catch (FileNotFoundException ex) {
                Logger.getLogger(ModGenerosController.class.getName()).log(Level.SEVERE, null, ex);
            }
            genero.setCursor(Cursor.HAND);

            genero.setOnMouseClicked(e -> {
                cargarCanciones(index);
            });

            listGeneros.getChildren().add(genero);
            Separator sep = new Separator();
            listGeneros.getChildren().add(sep);
        }

    }

    public List<Genero> obtenerGeneros() {
        List<Genero> listaGeneros = new ArrayList<>();
        int port = Integer.parseInt(rb.getString("dataport"));
        String host = rb.getString("datahost");
        servicios.Client servicios;

        try {
            servicios = Utilerias.conectar(host, port);
            listaGeneros = servicios.obtenerGenerosUsuario(correo);
            Utilerias.closeServer(servicios);
        } catch (TTransportException ex) {
            Logger.getLogger(ModArtistasController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (TException ex) {
            Logger.getLogger(ModArtistasController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listaGeneros;
    }

    public void cargarCanciones(int index) {
        Label seleccionada = (Label) listGeneros.getChildren().get(index + index);
        System.out.println("REFERENCIA: " + seleccionada.getText());

        listCanciones.getChildren().clear();
        List<CancionSL> canciones = obtenerCanciones(generos.get(index).getIdGenero(), correo);
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

    public List<CancionSL> obtenerCanciones(int idGenero, String correo) {
        List<CancionSL> canciones = new ArrayList<>();
        int port = Integer.parseInt(rb.getString("dataport"));
        String host = rb.getString("datahost");
        servicios.Client servicios;
        try {
            servicios = Utilerias.conectar(host, port);
            canciones = servicios.obtenerCancionesGenero(idGenero, correo);
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
