import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

public class Servidor implements IServidor {
    private static List<Carro> listaCarros;
    private static String funcLogin = "admin";
    private static String funcSenha = "senhaadmin";
    private static String clienteLogin = "cliente";
    private static String clienteSenha = "senhacliente";
    public boolean lider = true;
    public static boolean health = true;

    public Servidor(List<Carro> inicial) {
        listaCarros = inicial;
    }

    @Override
    public boolean adicionarCarro(Carro novo, boolean senderIsLider) throws RemoteException {
        if (!lider && !senderIsLider) {
            System.out.println("Réplica não é o lider, não pode executar operações de escrita");
            return false;
        }
        for (Carro iterador : listaCarros) {
            if (iterador.getRenavan().equals(novo.getRenavan()))
                return false;
        }

        System.out.println("Aqui");
        if (lider) {
            boolean successReplica2 = true;
            if (App.replica2.checarHealth()) {
                successReplica2 = App.replica2.adicionarCarro(novo, lider);
            }
            boolean successReplica3 = true;
            if (App.replica3.checarHealth()) {
                successReplica3 = App.replica3.adicionarCarro(novo, lider);
            }
        }
        listaCarros.add(novo);
        return true;
    }

    @Override
    public boolean deletarCarro(Carro deletado, boolean senderIsLider) throws RemoteException {
        if (!lider && !senderIsLider) {
            System.out.println("Réplica não é o lider, não pode executar operações de escrita");
            return false;
        }
        for (Carro iterador : listaCarros) {
            if (iterador.getRenavan().equals(deletado.getRenavan())) {
                listaCarros.remove(iterador);
                if (lider) {
                    boolean successReplica2 = true;
                    if (App.replica2.checarHealth()) {
                        successReplica2 = App.replica2.deletarCarro(deletado, lider);
                    }
                    boolean successReplica3 = true;
                    if (App.replica3.checarHealth()) {
                        successReplica3 = App.replica3.deletarCarro(deletado, lider);
                    }
                }
                return true;
            }

        }

        return false;
    }

    @Override
    public List<Carro> listarCarros(String opcao) throws RemoteException {
        List<Carro> temp = new ArrayList<>();
        listaCarros.stream().sorted();
        if (opcao.equals("geral")) {
            listaCarros.sort((carro1, carro2) -> carro1.getNome().compareTo(carro2.getNome()));
            return listaCarros;
        }
        for (Carro iterador : listaCarros) {
            if (iterador.getCategoria().equals(opcao))
                temp.add(iterador);
        }

        temp.sort((carro1, carro2) -> carro1.getNome().compareTo(carro2.getNome()));

        return temp;
    }

    @Override
    public Carro pesquisarRenavan(String renavan) throws RemoteException {
        for (Carro iterador : listaCarros) {
            if (iterador.getRenavan().equals(renavan))
                return iterador;
        }

        return null;
    }

    @Override
    public List<Carro> pesquisarNome(String nome) throws RemoteException {
        List<Carro> temp = new ArrayList<>();
        for (Carro iterador : listaCarros) {
            if (iterador.getNome().equals(nome)) {
                temp.add(iterador);
            }

        }

        if (temp.size() == 0)
            return null;

        return temp;
    }

    @Override
    public boolean alterarDado(int opcao, String infPesquisa, String dadoString, Integer dadoInteger,
            boolean senderIsLider)
            throws RemoteException {
        if (!lider && !senderIsLider) {
            System.out.println("Réplica não é o lider, não pode executar operações de escrita");
            return false;
        }

        Integer temp = 0;
        Carro alterado = null;
        for (Carro iterador : listaCarros) {
            if (iterador.getRenavan().equals(infPesquisa)) {
                alterado = iterador;
                break;
            }
            temp++;
        }

        if (alterado == null) {
            return false;
        }

        switch (opcao) {
            case 1: {
                listaCarros.get(temp).setRenavan(dadoString);
                break;
            }
            case 2: {
                listaCarros.get(temp).setNome(dadoString);
                break;
            }
            case 3: {
                listaCarros.get(temp).setCategoria(dadoString);
                break;
            }
            case 4: {
                listaCarros.get(temp).setAno(dadoInteger);
                break;
            }
            case 5: {
                listaCarros.get(temp).setPreco(dadoInteger);
                break;
            }
        }
        if (lider) {
            boolean successReplica2 = true;
            if (App.replica2.checarHealth()) {
                successReplica2 = App.replica2.alterarDado(opcao, infPesquisa, dadoString, dadoInteger, lider);
            }
            boolean successReplica3 = true;
            if (App.replica3.checarHealth()) {
                successReplica3 = App.replica3.alterarDado(opcao, infPesquisa, dadoString, dadoInteger, lider);
            }
        }
        return true;
    }

    @Override
    public Integer quantidadeCarros() throws RemoteException {
        return listaCarros.size();
    }

    @Override
    public boolean comprarCarro(Carro comprado, boolean senderIsLider) throws RemoteException {
        if (!lider && !senderIsLider) {
            System.out.println("Réplica não é o lider, não pode executar operações de escrita");
            return false;
        }
        boolean retorno = listaCarros.remove(comprado);
        if (lider) {
            boolean successReplica2 = true;
            if (App.replica2.checarHealth()) {
                successReplica2 = App.replica2.comprarCarro(comprado, lider);
            }
            boolean successReplica3 = true;
            if (App.replica3.checarHealth()) {
                successReplica3 = App.replica3.comprarCarro(comprado, lider);
            }
        }
        return true;
    }

    @Override
    public String verificarIdentidade(String login, String senha) throws RemoteException {
        String retorno = "erro";
        if (funcLogin.equals(login) && funcSenha.equals(senha)) {
            retorno = "func";
        }

        if (clienteLogin.equals(login) && clienteSenha.equals(senha)) {
            retorno = "cliente";
        }
        return retorno;
    }

    @Override
    public void inverterLider() {
        lider = !lider;
    }

    @Override
    public boolean isLider() throws RemoteException {
        return lider;
    }

    @Override
    public void ajustarOperacoes() throws RemoteException {
        if (App.replica2.isLider()) {
            listaCarros = App.replica2.getLista();
        } else {
            listaCarros = App.replica2.getLista();
        }
    }

    public boolean checarHealth() throws RemoteException {
        return health;
    }

    public void mudarHealth() throws RemoteException {
        health = !health;
    }

    public List<Carro> getLista() throws RemoteException {
        return listaCarros;
    }

}
