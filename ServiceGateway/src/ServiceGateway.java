import java.rmi.RemoteException;
import java.util.List;

public class ServiceGateway implements IServiceGateway {
    public static boolean on = true;
    public static boolean[] health = { true, true, true };
    private int lider = 0;
    private int contador = 0;
    private int tentativas = 3;

    public ServiceGateway() {
        Thread t = new Thread(new Checker());
        t.start();
    }

    @Override
    public boolean adicionarCarro(Carro novo) throws RemoteException, InterruptedException {
        if (health[lider] == false) {
            mudarLider();
        }
        System.out.println("Solicitação Adicionar Carro ao Servidor - " + lider);
        boolean result = Service.listaHealth[lider].adicionarCarro(novo, false);
        System.out.println("Resultado: " + result);

        if (!result) {
            for (int i = 0; i < tentativas; i++) {
                Thread.sleep(3000);
                result = Service.listaHealth[lider].adicionarCarro(novo, false);
                if (result)
                    break;
            }
        }

        return result;

    }

    @Override
    public boolean deletarCarro(Carro deletado) throws RemoteException, InterruptedException {
        if (health[lider] == false) {
            mudarLider();
        }
        System.out.println("Solicitação Remover Carro ao Servidor - " + lider);
        boolean result = Service.listaHealth[lider].deletarCarro(deletado, false);

        if (!result) {
            for (int i = 0; i < tentativas; i++) {
                Thread.sleep(3000);
                result = Service.listaHealth[lider].deletarCarro(deletado, false);
                if (result)
                    break;
            }
        }

        return result;
    }

    @Override
    public List<Carro> listarCarros(String opcao) throws RemoteException, InterruptedException {
        if (health[contador] == false) {
            mudarReplica();
        }
        System.out.println("Solicitação Listar Carros ao Servidor - " + contador);
        List<Carro> result = Service.listaHealth[contador].listarCarros(opcao);

        if (result.isEmpty()) {
            for (int i = 0; i < tentativas; i++) {
                Thread.sleep(3000);
                result = Service.listaHealth[contador].listarCarros(opcao);

            }
        }
        mudarReplica();
        return result;
    }

    @Override
    public Carro pesquisarRenavan(String renavan) throws RemoteException, InterruptedException {
        if (health[contador] == false) {
            mudarReplica();
        }
        System.out.println("Solicitação Pesquisar Renavan Carro ao Servidor - " + contador);
        Carro result = Service.listaHealth[contador].pesquisarRenavan(renavan);

        if (result == null) {
            for (int i = 0; i < tentativas; i++) {
                Thread.sleep(3000);
                result = Service.listaHealth[contador].pesquisarRenavan(renavan);

            }
        }
        mudarReplica();
        return result;
    }

    @Override
    public List<Carro> pesquisarNome(String nome) throws RemoteException, InterruptedException {
        if (health[contador] == false) {
            mudarReplica();
        }
        System.out.println("Solicitação Pesquisar Nome Carro ao Servidor - " + contador);
        List<Carro> result = Service.listaHealth[contador].pesquisarNome(nome);

        if (result.isEmpty()) {
            for (int i = 0; i < tentativas; i++) {
                Thread.sleep(3000);
                result = Service.listaHealth[contador].pesquisarNome(nome);
            }
        }
        mudarReplica();
        return result;
    }

    @Override
    public boolean alterarDado(int opcao, String infPesquisa, String dadoString, Integer dadoInteger)
            throws RemoteException, InterruptedException {
        if (health[lider] == false) {
            mudarLider();
        }
        System.out.println("Solicitação Alterar Dado ao Servidor - " + lider);
        boolean result = Service.listaHealth[lider].alterarDado(opcao, infPesquisa, dadoString, dadoInteger, false);

        if (!result) {
            for (int i = 0; i < tentativas; i++) {
                Thread.sleep(3000);
                result = Service.listaHealth[lider].alterarDado(opcao, infPesquisa, dadoString, dadoInteger, false);
                if (result)
                    break;
            }
        }

        return result;
    }

    @Override
    public Integer quantidadeCarros() throws RemoteException, InterruptedException {
        if (health[contador] == false) {
            mudarReplica();
        }
        System.out.println("Solicitação Quantidade Carros ao Servidor - " + contador);
        Integer result = Service.listaHealth[contador].quantidadeCarros();

        if (result == 0) {
            for (int i = 0; i < tentativas; i++) {
                Thread.sleep(3000);
                result = Service.listaHealth[contador].quantidadeCarros();
            }
        }
        mudarReplica();
        return result;
    }

    @Override
    public boolean comprarCarro(Carro comprado) throws RemoteException, InterruptedException {
        if (health[lider] == false) {
            mudarLider();
        }
        System.out.println("Solicitação Comprar Carro ao Servidor - " + lider);
        boolean result = Service.listaHealth[lider].comprarCarro(comprado, false);

        if (result == false) {
            for (int i = 0; i < tentativas; i++) {
                Thread.sleep(3000);
                result = Service.listaHealth[lider].comprarCarro(comprado, false);
            }
        }

        return result;
    }

    @Override
    public String verificarIdentidade(String login, String senha) throws RemoteException, InterruptedException {
        if (health[contador] == false) {
            mudarReplica();
        }
        String result = Service.listaHealth[contador].verificarIdentidade(login, senha);

        if (result.equals("erro")) {
            for (int i = 0; i < tentativas; i++) {
                Thread.sleep(3000);
                result = Service.listaHealth[contador].verificarIdentidade(login, senha);
            }
        }
        mudarReplica();
        return result;
    }

    public void mudarLider() throws RemoteException {
        if (lider == 2)
            lider = -1;
        lider++;
        if (!health[lider])
            lider++;

        Service.listaHealth[lider].inverterLider();
    }

    public void mudarReplica() {
        contador++;
        if (contador == 3)
            contador = 0;
    }

    public void desativarReplica(int numero) throws RemoteException {
        Service.listaHealth[numero].mudarHealth();
    }

}
