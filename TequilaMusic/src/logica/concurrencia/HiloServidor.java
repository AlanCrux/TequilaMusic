package logica.concurrencia;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import presentacion.controladores.IUReproductorController;

/**
 *
 * @author Alan Yoset Garcia Cruz
 */
public class HiloServidor extends Thread{

  private Scanner entradaRed;
  private PrintWriter salidaRed;
  IUReproductorController controller;

  public HiloServidor(Socket servidor, IUReproductorController controller) {
    this.controller = controller;
    try {
      entradaRed = new Scanner(servidor.getInputStream());
      salidaRed = new PrintWriter(servidor.getOutputStream());
    } catch (IOException ex) {
      Logger.getLogger(HiloServidor.class.getName()).log(Level.SEVERE, null, ex);
    }
  }

  
  @Override
  public void run(){
    while (true) {
      String respuesta = entradaRed.nextLine();
      controller.obtenerRespuesta(respuesta);
    }
  }
}
