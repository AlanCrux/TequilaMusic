package tequilamusic;

import java.util.List;
import org.apache.thrift.TException;
import org.apache.thrift.transport.TTransport;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import services.Cancion;
import services.servicioCanciones;

/**
 *
 * @author Alan
 */
public class Client {

    public static void main(String[] args) {
        try {
            TTransport transport;
            transport = new TSocket("192.168.0.100", 9090);
            transport.open();

            TProtocol protocol = new TBinaryProtocol(transport);
            servicioCanciones.Client client = new servicioCanciones.Client(protocol);

            perform(client);

            transport.close();
        } catch (TException x) {
            x.printStackTrace();
        }
    }

    private static void perform(servicioCanciones.Client client) throws TException {
        System.out.println("Método obtener canciones");
        List<Cancion> canciones = client.obtenerCanciones("algo");
        System.out.println(canciones.size());
    }
}
