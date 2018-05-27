package logica.mappers;

import java.util.ArrayList;
import java.util.List;
import logica.psl.CancionSl;

/**
 *
 * @author Alan Yoset Garcia Cruz
 */
public class MapperCancion {
  public List<CancionSl> obtenerCanciones(){
    List<CancionSl> canciones = null;
    
    // AQUI DEBERIA OBTENER LAS CANCIONES DEL SERVIDOR
    canciones = new ArrayList<>();
    CancionSl cancion = new CancionSl();
    cancion.setTitulo("Ya no vives en mi");
    cancion.setAlbum("Primera fila");
    cancion.setArtista("Yuri, Carlos Rivera");
    cancion.setRuta("ruta");
    cancion.setDuracion((float) 4.21);
    canciones.add(cancion);
   
    CancionSl cancion2 = new CancionSl();
    cancion2.setTitulo("Ya no vives en mi");
    cancion2.setAlbum("Simplemente amor");
    cancion2.setArtista("Yuri");
    cancion2.setRuta("ruta");
    cancion2.setDuracion((float) 4.10);
    canciones.add(cancion2);
    // ------------------------------------------------
    
    return canciones; 
  }
}
