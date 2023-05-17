import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class App {
    public static Registry registro2 = null;
    public static IServidor replica2 = null;
    public static Registry registro3 = null;
    public static IServidor replica3 = null;

    public static void main(String[] args) throws Exception {
        List<Carro> listaCarros = new ArrayList<>();
        Carro temp = new Carro("86918466255", "Gol", "economico", 2020, 40000);
        listaCarros.add(temp);
        temp = new Carro("17525291949", "Palio", "economico", 2013, 45000);
        listaCarros.add(temp);
        temp = new Carro("98752090777", "Corsa", "economico", 2012, 42500);
        listaCarros.add(temp);
        temp = new Carro("61954819152", "Onix", "intermediario", 2005, 73000);
        listaCarros.add(temp);
        temp = new Carro("33728524818", "HB20", "intermediario", 2019, 82000);
        listaCarros.add(temp);
        temp = new Carro("71588013729", "Corolla", "intermediario", 2021, 91000);
        listaCarros.add(temp);
        temp = new Carro("53327237149", "Ferrari", "executivo", 2021, 200000);
        listaCarros.add(temp);
        temp = new Carro("85808894265", "Mustang", "executivo", 2016, 140000);
        listaCarros.add(temp);
        temp = new Carro("53791055251", "Camaro", "executivo", 2015, 180000);
        listaCarros.add(temp);
        try {
            // System.setProperty("java.rmi.server.hostname", "192.168.0.108");
            Servidor remoteObject = new Servidor(listaCarros);

            IServidor skeleton = (IServidor) UnicastRemoteObject.exportObject(remoteObject, 0);

            LocateRegistry.createRegistry(5002);

            Registry registro = LocateRegistry.getRegistry(5002);

            registro.bind("servidor2", skeleton);

            Scanner teclado = new Scanner(System.in);
            System.out.println("Esperando os outros servidores ficarem prontos!");
            teclado.nextLine();

            registro2 = LocateRegistry.getRegistry("localhost", 5001);

            replica2 = (IServidor) registro2.lookup("servidor");

            registro3 = LocateRegistry.getRegistry("localhost", 5003);

            replica3 = (IServidor) registro3.lookup("servidor3");

            System.out.println("Servidor Pronto!");
        } catch (Exception e) {
            System.err.println("Servidor: " + e.toString());
            e.printStackTrace();
        }

    }
}
