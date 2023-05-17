import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface IServidor extends Remote {
    public static int ADICIONARCARRO = 0;
    public static int DELETARCARRO = 1;
    public static int ALTERARDADO = 2;
    public static int COMPRARCARRO = 3;

    boolean adicionarCarro(Carro novo, boolean senderIsLider) throws RemoteException;

    boolean deletarCarro(Carro deletado, boolean senderIsLider) throws RemoteException;

    List<Carro> listarCarros(String opcao) throws RemoteException;

    Carro pesquisarRenavan(String renavan) throws RemoteException;

    List<Carro> pesquisarNome(String nome) throws RemoteException;

    boolean alterarDado(int opcao, String infPesquisa, String dadoString, Integer dadoInteger, boolean senderIsLider)
            throws RemoteException;

    Integer quantidadeCarros() throws RemoteException;

    boolean comprarCarro(Carro comprado, boolean senderIsLider) throws RemoteException;

    String verificarIdentidade(String login, String senha) throws RemoteException;

    void inverterLider() throws RemoteException;

    boolean isLider() throws RemoteException;

    void ajustarOperacoes() throws RemoteException;

    boolean checarHealth() throws RemoteException;

    void mudarHealth() throws RemoteException;

    List<Carro> getLista() throws RemoteException;
}