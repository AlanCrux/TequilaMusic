package utilerias;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.animation.FadeTransition;
import javafx.animation.Interpolator;
import javafx.animation.TranslateTransition;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import javax.imageio.ImageIO;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import org.apache.thrift.transport.TTransportException;
import org.controlsfx.control.Notifications;
import presentacion.controladores.IUInicioController;
import servicios.servicios;

/**
 *
 * @author alanc
 */
public class Utilerias {

    /**
     * Aplica una transición de degradado a un nodo
     *
     * @param e el nodo al que se le quiere aplicar la transición
     */
    public static void fadeTransition(Node e) {
        FadeTransition x = new FadeTransition(new Duration(1000), e);
        x.setFromValue(0);
        x.setToValue(100);
        x.setCycleCount(1);
        x.setInterpolator(Interpolator.LINEAR);
        x.play();
    }

    /**
     * Aplica una transición rápida de degradado a un nodo
     *
     * @param e el nodo al que se le quiere aplicar la transición
     */
    public static void fadeTransitionFast(Node e) {
        FadeTransition x = new FadeTransition(new Duration(250), e);
        x.setFromValue(0);
        x.setToValue(100);
        x.setCycleCount(1);
        x.setInterpolator(Interpolator.LINEAR);
        x.play();
    }

    /**
     * Aplica una transición de degradado a un nodo
     *
     * @param e el nodo al que se le quiere aplicar la transición
     * @param duration la duración de la transición
     */
    public static void fadeTransition(Node e, int duration) {
        FadeTransition x = new FadeTransition(new Duration(duration), e);
        x.setFromValue(0);
        x.setToValue(100);
        x.setCycleCount(1);
        x.setInterpolator(Interpolator.LINEAR);
        x.play();
    }

    /**
     * Aplica una movimiento horizontal de rebote a un nodo
     *
     * @param e el nodo al que se le quiere aplicar la transición
     */
    public static void doingTransition(Node e) {
        TranslateTransition transition = new TranslateTransition();
        transition.setDuration(Duration.seconds(0.1));
        transition.setNode(e);
        transition.setToX(15);
        transition.setAutoReverse(true);
        transition.setCycleCount(4);
        transition.play();
    }

    /**
     * Aplica una movimiento horizontal de rebote a un nodo
     *
     * @param e el nodo al que se le quiere aplicar la transición
     */
    public static void efectoPersiana(Node e) {
        TranslateTransition transition = new TranslateTransition();
        transition.setDuration(Duration.seconds(0.2));
        transition.setNode(e);
        transition.setToY(50);
        transition.setAutoReverse(false);
        transition.setCycleCount(1);
        transition.play();
    }

    /**
     * Verifica si la cadena recibida cumple con el formato de un E-mail.
     *
     * @param correo la cadena que se quiere analizar
     * @return true si coincide con el formato
     */
    public static boolean validateEmailFormat(String correo) {
        Pattern pattern = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(correo);
        return matcher.find();
    }

    /**
     *
     * @param host
     * @param puerto
     * @return
     * @throws org.apache.thrift.transport.TTransportException
     */
    public static servicios.Client conectar(String host, int puerto) throws TTransportException {
        servicios.Client server = null;
        TTransport transport;
        transport = new TSocket(host, puerto);
        transport.open();
        TProtocol protocol = new TBinaryProtocol(transport);
        server = new servicios.Client(protocol);
        return server;
    }

    public static Socket conectarStreaming(String host, int puerto) {
        Socket socket = null;
        try {
            socket = new Socket(host, puerto);
        } catch (IOException ex) {
            Logger.getLogger(Utilerias.class.getName()).log(Level.SEVERE, null, ex);
        }
        return socket;
    }

    public static boolean bajarCancion(String path, String archivoRecibido, Socket socket) {
        try {
            PrintWriter salida = new PrintWriter(socket.getOutputStream(), true);
            salida.println(path);
            byte[] datosRecibidos = new byte[1024];
            BufferedOutputStream bufferSalida = new BufferedOutputStream(new FileOutputStream(archivoRecibido));
            BufferedInputStream bufferEntrada = new BufferedInputStream(socket.getInputStream());
            int in;
            while ((in = bufferEntrada.read(datosRecibidos)) != -1) {
                bufferSalida.write(datosRecibidos, 0, in);
            }
            bufferEntrada.close();
            bufferSalida.close();
            salida.close();
            socket.close();
            return true;
        } catch (IOException ex) {
            return false;
        }

    }

    public static void mostrarErrorConexion(AnchorPane contentError) {
        Utilerias.fadeTransition(contentError, 1000);
        contentError.setVisible(true);
    }

    public static void ocultarErrorConexion(AnchorPane contentError, AnchorPane contentPane) {
        Utilerias.fadeTransition(contentPane, 750);
        contentError.setVisible(false);
    }

    /**
     * Muestra la ventana.
     *
     * @param loader el loader con la ruta de la ventana que se quiere cargar.
     */
    public static void mostrarVentana(FXMLLoader loader) {
        try {
            Stage primaryStage = new Stage();
            Parent root = (Parent) loader.load();
            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
            primaryStage.setResizable(false);
            primaryStage.sizeToScene();
            primaryStage.show();

        } catch (IOException ex) {
            Logger.getLogger(IUInicioController.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Muestra la ventana.
     *
     * @param loader el loader con la ruta de la ventana que se quiere cargar.
     */
    public static void mostrarVentanaMax(FXMLLoader loader) {
        try {
            Stage primaryStage = new Stage();
            Parent root = (Parent) loader.load();
            Scene scene = new Scene(root);
            scene.getStylesheets().add("/recursos/estilos/contextMenu.css");
            primaryStage.setScene(scene);
            primaryStage.show();

        } catch (IOException ex) {
            Logger.getLogger(IUInicioController.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void byteArrayToFile(byte[] byteArray, String outFilePath) throws FileNotFoundException, IOException {
        FileOutputStream fos = new FileOutputStream(outFilePath);
        fos.write(byteArray);
        fos.close();
    }

    public static byte[] imageToByteArray(Image imagen) {
        BufferedImage bImage = SwingFXUtils.fromFXImage(imagen, null);
        ByteArrayOutputStream s = new ByteArrayOutputStream();
        try {
            ImageIO.write(bImage, "jpg", s);
        } catch (IOException ex) {
            Logger.getLogger(Utilerias.class.getName()).log(Level.SEVERE, null, ex);
        }
        byte[] res = s.toByteArray();
        try {
            s.close(); //especially if you are using a different output stream.
        } catch (IOException ex) {
            Logger.getLogger(Utilerias.class.getName()).log(Level.SEVERE, null, ex);
        }
        return res;
    }

    /**
     * Este metodo convierte la cadena binarios en una imagen
     *
     * @param byteImage
     * @return objeto de tipo Image
     */
    public static Image byteToImage(byte[] byteImage) {
        Image img = new Image(new ByteArrayInputStream(byteImage));
        return img;
    }

    public static void closeServer(servicios.Client servidor) {
        TProtocol protocol = servidor.getInputProtocol();
        TTransport transport = protocol.getTransport();
        transport.close();
    }

    public static void showNotification(String title, String text, Pos pos) {
        Notifications notification;
        notification = Notifications.create().title(title).text(text).hideAfter(Duration.seconds(2)).position(pos);
        notification.show();
    }

    public static void displayInformation(String title, String message) {
        Stage primaryStage = new Stage();
        StackPane stackPane = new StackPane();
        stackPane.setStyle("-fx-background-color: #E8F8F5;");
        JFXDialogLayout content = new JFXDialogLayout();
        content.setHeading(new Text(title));
        content.setBody(new Text(message));

        JFXDialog dialog = new JFXDialog(stackPane, content, JFXDialog.DialogTransition.CENTER);

        JFXButton button = new JFXButton("Aceptar");
        button.setOnAction((ActionEvent event) -> {
            dialog.close();
            primaryStage.close();
        });
        content.setActions(button);

        Scene scene = new Scene(stackPane);

        primaryStage.setScene(scene);
        primaryStage.initStyle(StageStyle.TRANSPARENT);
        dialog.show();
        primaryStage.show();
    }
    
    public void subirCancion(String directorioCancion, String rutaAGuardar, String host, int puerto){
        int in;
        try {
            final File archivoEnviar = new File(directorioCancion);
            Socket cliente = new Socket(host, puerto);
            BufferedInputStream bufferEntrada = new BufferedInputStream(new FileInputStream(archivoEnviar));
            BufferedOutputStream bufferSalida = new BufferedOutputStream(cliente.getOutputStream());
            PrintWriter out = new PrintWriter(cliente.getOutputStream(),true);

            //Enviamos el nombre del fichero
            out.println(rutaAGuardar);
            System.out.println(rutaAGuardar);
            
            //Enviamos el fichero
            byte[] byteArray = new byte[8192];
            while (( in = bufferEntrada.read(byteArray)) != -1) {
                System.out.println("Enviando cancion...");
                bufferSalida.write(byteArray, 0, in);
            }

            bufferEntrada.close();
            bufferSalida.close();
            System.out.println("Se envio la cancion");

        } catch (IOException e) {
        }

    }
}

