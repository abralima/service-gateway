import java.rmi.RemoteException;

public class Checker implements Runnable {
    public static boolean[] eraLider = { false, false, false };
    public static boolean[] estavaOff = { false, false, false };

    @Override
    public void run() {
        try {
            while (ServiceGateway.on) {
                Thread.sleep(4000);
                System.out.println("Checando Health");
                for (int i = 0; i < 3; i++) {
                    if (!Service.listaHealth[i].checarHealth()) {
                        ServiceGateway.health[i] = false;
                        estavaOff[i] = true;
                        if (Service.listaHealth[i].isLider()) {
                            eraLider[i] = true;
                        }
                    } else {
                        if (eraLider[i]) {
                            Service.listaHealth[i].inverterLider();
                            eraLider[i] = false;
                        }
                        if (estavaOff[i]) {
                            Service.listaHealth[i].ajustarOperacoes();
                            estavaOff[i] = false;
                        }
                        ServiceGateway.health[i] = true;
                    }
                }
            }

        } catch (InterruptedException | RemoteException e) {
            e.printStackTrace();
        }
        throw new UnsupportedOperationException("Unimplemented method 'run'");
    }

}
