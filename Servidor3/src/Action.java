import java.io.Serializable;

public class Action implements Serializable {
    private static final long serialVersionUID = 1L;
    public Carro holder = null;
    public int opcao;
    public int infNum = 0;
    public String infTex = "";
    public String infPes = "";
    public int tipo;
    public boolean executed;

    public Action(int tipo, Carro holder) {
        this.tipo = tipo;
        this.holder = holder;
    }

    public Action(int tipo, int opcao, String infPes, int infNum) {
        this.tipo = tipo;
        this.opcao = opcao;
        this.infNum = infNum;
    }

    public Action(int tipo, int opcao, String infPes, String infTex) {
        this.tipo = tipo;
        this.opcao = opcao;
        this.infTex = infTex;
    }

    @Override
    public String toString() {
        String retorno = "";
        switch (tipo) {
            case 0: {
                retorno = "ADICIONARCARRO";
            }
            case 1: {
                retorno = "DELETARCARRO";
            }
            case 2: {
                retorno = "ALTERARDADO";
            }
            case 3: {
                retorno = "COMPRARCARRO";
            }
        }
        return retorno;
    }

}
