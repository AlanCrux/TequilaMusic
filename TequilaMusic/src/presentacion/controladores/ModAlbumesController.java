/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
public class ModAlbumesController implements Initializable {

    @FXML
    private FlowPane flowPane;
    ResourceBundle rb;
    String correo;
    Usuario usuario;
    IUArtistaController parent;

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.rb = rb;
        cargarAlbumes();
    }

    public void cargarAlbumes() {
        List<AlbumSL> albumes = obtenerAlbumes();
        NodeAlbumController controller;
        int size = albumes.size();
        System.out.println("tamaño " + size);
        for (int i = 0; i < size; i++) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/presentacion/vistas/nodeAlbum.fxml"));
            controller = new NodeAlbumController();
            controller.setAlbum(albumes.get(i));
            controller.setParent(parent);
            controller.setRb(rb);
            controller.setUsuario(usuario);
            loader.setController(controller);
            try {
                AnchorPane nodo = (AnchorPane) loader.load();
                flowPane.getChildren().add(nodo);
            } catch (IOException ex) {
                Logger.getLogger(ModAlbumesController.class.getName()).log(Level.SEVERE, null, ex);
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
            albumes = servicios.obtenerAlbumesArtista(correo);
            Utilerias.closeServer(servicios);
        } catch (TTransportException ex) {
            Logger.getLogger(IUReproductorController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (TException ex) {
            Logger.getLogger(ModHistorialController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return albumes;
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

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
    
    

}
