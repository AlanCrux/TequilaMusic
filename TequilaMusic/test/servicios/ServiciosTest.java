package servicios;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.thrift.TException;
import org.apache.thrift.transport.TTransportException;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;
import servicios.servicios.Client;
import utilerias.Utilerias;

/**
 * Contiene pruebas unitarias para los servicios consumidos por la aplicación.
 *
 * @author Alan Yoset Garcia Cruz
 */
public class ServiciosTest {

    int puerto;
    String host;

    @Before
    public void serverInfo() {
        ResourceBundle rb = ResourceBundle.getBundle("recursos.serverproperties");
        puerto = Integer.parseInt(rb.getString("dataport"));
        host = rb.getString("datahost");
    }

    @Test
    public void testObtenerCancionesFiltradas() {
        System.out.println("Test: Obtener todas las canciones");
        List<CancionSL> canciones = new ArrayList<>();
        try {
            Client servicios = Utilerias.conectar(host, puerto);
            canciones = servicios.obtenerCancionesFiltradas("");
            Utilerias.closeServer(servicios);
        } catch (TTransportException ex) {
            Logger.getLogger(ServiciosTest.class.getName()).log(Level.SEVERE, null, ex);
        } catch (TException ex) {
            Logger.getLogger(ServiciosTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        int esperado = 4;
        int obtenido = canciones.size();
        assertEquals(esperado, obtenido);
    }

    @Test
    public void testObtenerUsuario() {
        System.out.println("Test: Obtener usuario");
        Usuario local = new Usuario();
        local.setCorreo("alancrux_@hotmail.com");
        local.setClave("alansuper");

        Usuario base = null;

        try {
            Client servicios = Utilerias.conectar(host, puerto);
            base = servicios.obtenerUsuario(local.getCorreo());
            Utilerias.closeServer(servicios);
        } catch (TTransportException ex) {
            Logger.getLogger(ServiciosTest.class.getName()).log(Level.SEVERE, null, ex);
        } catch (TException ex) {
            Logger.getLogger(ServiciosTest.class.getName()).log(Level.SEVERE, null, ex);
        }

        assertEquals(local.getClave(), base.getClave());
    }

    @Test
    public void insertarUsuarioExistente() {
        Usuario local = new Usuario();
        local.setCorreo("alancrux_@hotmail.com");
        local.setNombre("Alan");
        local.setClave("test");
        local.setTipo("consumidor");

        boolean esperado = false;
        boolean resultado = true;

        try {
            Client servicios = Utilerias.conectar(host, puerto);
            resultado = servicios.insertarUsuario(local);
            Utilerias.closeServer(servicios);
        } catch (TTransportException ex) {
            Logger.getLogger(ServiciosTest.class.getName()).log(Level.SEVERE, null, ex);
        } catch (TException ex) {
            Logger.getLogger(ServiciosTest.class.getName()).log(Level.SEVERE, null, ex);
        }

        assertEquals(esperado, resultado);
    }

    @Test
    public void insertarUsuario() {
        Usuario local = new Usuario();
        local.setCorreo("test@hotmail.com");
        local.setNombre("Test");
        local.setClave("test");
        local.setTipo("artista");

        boolean esperado = true;
        boolean resultado = false;

        try {
            Client servicios = Utilerias.conectar(host, puerto);
            resultado = servicios.insertarUsuario(local);
            Utilerias.closeServer(servicios);
        } catch (TTransportException ex) {
            Logger.getLogger(ServiciosTest.class.getName()).log(Level.SEVERE, null, ex);
        } catch (TException ex) {
            Logger.getLogger(ServiciosTest.class.getName()).log(Level.SEVERE, null, ex);
        }

        assertEquals(esperado, resultado);
    }
    
    @Test
    public void obtenerCancionesBiblioteca(){
        String correo = "alancrux_@hotmail.com";
        int esperado = 1; 
        int resultado = 0; 
        
        try {
            Client servicios = Utilerias.conectar(host, puerto);
            List<CancionSL> canciones = servicios.obtenerCancionesBiblioteca(correo);
            resultado = canciones.size(); 
            Utilerias.closeServer(servicios);
        } catch (TTransportException ex) {
            Logger.getLogger(ServiciosTest.class.getName()).log(Level.SEVERE, null, ex);
        } catch (TException ex) {
            Logger.getLogger(ServiciosTest.class.getName()).log(Level.SEVERE, null, ex);
        }

        assertEquals(esperado, resultado);
    }
    
    @Test 
    public void obtenerPlayList(){
        String correo = "alancrux_@hotmail.com";
        List<Playlist> listas = new ArrayList<>();
        int esperado = 1; 
        int resultado = 0; 
        try {
            Client servicios = Utilerias.conectar(host, puerto);
            listas = servicios.obtenerPlaylists(correo);
            resultado = listas.size(); 
            Utilerias.closeServer(servicios);
        } catch (TTransportException ex) {
            Logger.getLogger(ServiciosTest.class.getName()).log(Level.SEVERE, null, ex);
        } catch (TException ex) {
            Logger.getLogger(ServiciosTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        assertEquals(esperado, resultado);
    }

}
