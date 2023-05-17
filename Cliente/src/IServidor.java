import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface IServidor extends Remote {

    boolean adicionarCarro(Carro novo) throws RemoteException;

    boolean deletarCarro(Carro deletado) throws RemoteException;

    List<Carro> listarCarros(String opcao) throws RemoteException;

    Carro pesquisarRenavan(String renavan) throws RemoteException;

    List<Carro> pesquisarNome(String nome) throws RemoteException;

    boolean alterarDado(int opcao, String infPesquisa, String dadoString, Integer dadoInteger) throws RemoteException;

    Integer quantidadeCarros() throws RemoteException;

    boolean comprarCarro(Carro comprado) throws RemoteException;

    String verificarIdentidade(String login, String senha) throws RemoteException;
}
