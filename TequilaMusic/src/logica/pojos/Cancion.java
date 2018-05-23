package logica.pojos;

/**
 *
 * @author Alan Yoset Garcia Cruz
 */
public class Cancion {

  private int idCancion;
  private String titulo;
  private String ruta;
  private int idAlbum;

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

  public int getIdAlbum() {
    return idAlbum;
  }

  public void setIdAlbum(int idAlbum) {
    this.idAlbum = idAlbum;
  }

}
