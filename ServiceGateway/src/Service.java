import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class Service {
    public static Registry registro1 = null;
    public static IServidor replica1 = null;
    public static Registry registro2 = null;
    public static IServidor replica2 = null;
    public static Registry registro3 = null;
    public static IServidor replica3 = null;
    public static IServidor[] listaHealth = null;

    public static void main(String[] args) throws Exception {
        try {
            listaHealth = new IServidor[3];
            // System.setProperty("java.rmi.server.hostname", "192.168.0.108");
            ServiceGateway remoteObject = new ServiceGateway();

            IServiceGateway skeleton = (IServiceGateway) UnicastRemoteObject.exportObject(remoteObject, 0);

            LocateRegistry.createRegistry(6000);

            Registry registro = LocateRegistry.getRegistry(6000);

            registro.bind("servicegateway", skeleton);

            registro1 = LocateRegistry.getRegistry("localhost", 5001);

            replica1 = (IServidor) registro1.lookup("servidor");

            listaHealth[0] = replica1;

            registro2 = LocateRegistry.getRegistry("localhost", 5002);

            replica2 = (IServidor) registro2.lookup("servidor2");

            listaHealth[1] = replica2;

            registro3 = LocateRegistry.getRegistry("localhost", 5003);

            replica3 = (IServidor) registro3.lookup("servidor3");

            listaHealth[2] = replica3;

            System.out.println("Servidor Pronto!");
        } catch (Exception e) {
            System.err.println("Servidor: " + e.toString());
            e.printStackTrace();
        }
    }
}
