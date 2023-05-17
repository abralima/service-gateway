import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface IServiceGateway extends Remote {
    boolean adicionarCarro(Carro novo) throws RemoteException, InterruptedException;

    boolean deletarCarro(Carro deletado) throws RemoteException, InterruptedException;

    List<Carro> listarCarros(String opcao) throws RemoteException, InterruptedException;

    Carro pesquisarRenavan(String renavan) throws RemoteException, InterruptedException;

    List<Carro> pesquisarNome(String nome) throws RemoteException, InterruptedException;

    boolean alterarDado(int opcao, String infPesquisa, String dadoString, Integer dadoInteger)
            throws RemoteException, InterruptedException;

    Integer quantidadeCarros() throws RemoteException, InterruptedException;

    boolean comprarCarro(Carro comprado) throws RemoteException, InterruptedException;

    String verificarIdentidade(String login, String senha) throws RemoteException, InterruptedException;

    void mudarLider() throws RemoteException;

    void desativarReplica(int numero) throws RemoteException;
}
