package logica.psl;

/**
 *
 * @author Alan Yoset Garcia Cruz
 */
public class CancionSl {

  private int idCancion;
  private String titulo;
  private String ruta;
  private String album;
  private String artista;
  private float duracion; 

  public int getIdCancion() {
    return idCancion;
  }

  public void setIdCancion(int idCancion) {
    this.idCancion = idCancion;
  }

  public String getTitulo() {
    return titulo;
  }

  public void setTitulo(String titulo) {
    this.titulo = titulo;
  }

  public String getRuta() {
    return ruta;
  }

  public void setRuta(String ruta) {
    this.ruta = ruta;
  }

  public String getAlbum() {
    return album;
  }

  public void setAlbum(String album) {
    this.album = album;
  }

  public String getArtista() {
    return artista;
  }

  public void setArtista(String artista) {
    this.artista = artista;
  }

  public float getDuracion() {
    return duracion;
  }

  public void setDuracion(float duracion) {
    this.duracion = duracion;
  }
}
