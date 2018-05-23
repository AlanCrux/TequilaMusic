package logica.pojos;

import java.util.Date;

/**
 *
 * @author Alan Yoset Garcia Cruz
 */
public class Historial {
  private String correo;
  private int idCancion; 
  private Date fecha; 

  public String getCorreo() {
    return correo;
  }

  public void setCorreo(String correo) {
    this.correo = correo;
  }

  public int getIdCancion() {
    return idCancion;
  }

  public void setIdCancion(int idCancion) {
    this.idCancion = idCancion;
  }

  public Date getFecha() {
    return fecha;
  }

  public void setFecha(Date fecha) {
    this.fecha = fecha;
  }
  
  
}
