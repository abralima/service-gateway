import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class App {
    private static List<Carro> listaCarros = new ArrayList<>();

    public static void main(String[] args) throws Exception {
        try {
            Scanner teclado = new Scanner(System.in);

            Registry registro = LocateRegistry.getRegistry("localhost", 6000);

            IServiceGateway stub = (IServiceGateway) registro.lookup("servicegateway");

            String usuario;
            String temps1 = "";
            String temps2 = "";
            Integer tempi = 0;
            Integer tempi2 = 0;
            boolean tempb;

            while (true) {
                System.out.println("Digite seu login");
                temps1 = teclado.nextLine();
                System.out.println("Digite sua senha:");
                temps2 = teclado.nextLine();

                usuario = stub.verificarIdentidade(temps1, temps2);

                if (usuario.equals("func")) {
                    System.out.println("Funcionario");
                    break;
                } else if (usuario.equals("cliente")) {
                    System.out.println("Cliente");
                    break;
                } else {
                    System.err.println("Credenciais invalidas, tente novamente!");
                }

            }

            while (true) {
                if (usuario.equals("func")) {
                    System.out.println("Selecione qual opção deseja.");
                    System.out.println(
                            "1 - Listar Carros \n 2 - Pesquisar Carro \n 3 - Exibir Quantidade de Carros \n 4 - Comprar Carro \n 5 - Adicionar Carro \n 6 - Deletar Carro \n 7 - Alterar Dado de um Carro \n 8 - Sair \n 9 - Desativar Replica");

                    tempi = teclado.nextInt();
                    teclado.nextLine();
                    if (tempi == 8)
                        break;

                } else if (usuario.equals("cliente")) {
                    System.out.println("Selecione qual opção deseja.");
                    System.out.println(
                            "1 - Listar Carros \n 2 - Pesquisar Carro \n 3 - Exibir Quantidade de Carros \n 4 - Comprar Carro \n 5 - Sair");

                    tempi = teclado.nextInt();
                    teclado.nextLine();
                    if (tempi == 5)
                        break;

                    if (tempi < 1 || tempi > 5) {
                        System.out.println("Opção Inválida!");
                        continue;
                    }
                }
                switch (tempi) {
                    case 1: {
                        System.out.println("Quais carros deseja listar?");
                        System.out.println("1 - Todos \n 2 - Economicos \n 3 - Intermediarios \n 4 - Executivos");
                        tempi = teclado.nextInt();
                        if (tempi == 1) {
                            listaCarros = stub.listarCarros("geral");
                        } else if (tempi == 2) {
                            listaCarros = stub.listarCarros("economico");
                        } else if (tempi == 3) {
                            listaCarros = stub.listarCarros("intermediario");
                        } else {
                            listaCarros = stub.listarCarros("executivo");
                        }
                        listaCarros.sort((carro1, carro2) -> carro1.getNome().compareTo(carro2.getNome()));
                        for (Carro iterador : listaCarros) {
                            System.out.println(iterador);
                        }
                        continue;
                    }
                    case 2: {
                        System.out.println("Deseja pesquisar pelo \n1 - Nome\n2 - Renavan");
                        tempi = teclado.nextInt();
                        teclado.nextLine();
                        if (tempi == 1) {
                            System.out.println("Digite o nome:");
                            temps2 = teclado.nextLine().trim();
                            listaCarros = stub.pesquisarNome(temps2);
                            if (listaCarros == null) {
                                System.out.println("Nenhum carro encontrado com esse nome.");
                                continue;
                            }
                            System.out.println("Carros encontrados:");
                            for (Carro iterador : listaCarros) {
                                System.out.println(iterador);
                            }
                        } else {
                            System.out.println("Digite o renavan:");
                            temps2 = teclado.nextLine();
                            Carro pesquisado = stub.pesquisarRenavan(temps2);
                            if (pesquisado == null) {
                                System.out.println("Nenhum carro encontrado com esse Renavan");
                                continue;
                            }
                            System.out.println("Carro encontrado:");
                            System.out.println(pesquisado);
                        }
                        continue;
                    }
                    case 3: {
                        listaCarros = stub.listarCarros("geral");
                        System.out.println("Total de Carros: " + listaCarros.size());
                        continue;
                    }
                    case 4: {
                        listaCarros = stub.listarCarros("geral");
                        tempi = 0;
                        for (Carro iterador : listaCarros) {
                            System.out.println(tempi++ + " - " + iterador);
                        }
                        System.out.println("Digite a opção do carro que deseja comprar: ");
                        tempi = teclado.nextInt();
                        teclado.nextLine();
                        Carro deletado = listaCarros.get(tempi);
                        tempb = stub.deletarCarro(deletado);
                        if (tempb) {
                            System.out.println("Carro comprado com sucesso.");
                        } else {
                            System.out.println("Carro não encontrado, tente outro carro.");
                        }
                        continue;
                    }
                    case 5: {
                        Carro novo = new Carro();
                        System.out.println("Digite o Renavan do Carro:");
                        novo.setRenavan(teclado.nextLine());
                        System.out.println("Digite o Nome do Carro:");
                        novo.setNome(teclado.nextLine());
                        System.out.println("Digite a Categoria do Carro:");
                        novo.setCategoria(teclado.nextLine());
                        System.out.println("Digite o Ano do Carro:");
                        novo.setAno(teclado.nextInt());
                        System.out.println("Digite o Preço do Carro:");
                        novo.setPreco(teclado.nextInt());
                        tempb = stub.adicionarCarro(novo);
                        if (tempb) {
                            System.out.println("Carro Inserido com Sucesso!");
                        } else {
                            System.out.println("Não foi possível inserir o carro, o renavan já está em uso!");
                        }
                        teclado.nextLine();
                        continue;
                    }
                    case 6: {
                        listaCarros = stub.listarCarros("geral");
                        System.out.println("Digite o nome do carro que deseja procurar:");
                        temps1 = teclado.nextLine();
                        listaCarros = stub.pesquisarNome(temps1);
                        System.out.println(
                                "Estes são os carros com o respectivo nome");
                        tempi = 0;
                        for (Carro iterador : listaCarros) {
                            System.out.println(tempi++ + " - " + iterador);
                        }
                        System.out.println("Digite a opção do carro a ser deletado.");
                        tempi = teclado.nextInt();
                        Carro deletado = listaCarros.get(tempi);
                        tempb = stub.deletarCarro(deletado);
                        if (tempb) {
                            System.out.println("Carro Deletado com Sucesso!");
                        } else {
                            System.out.println("Carro não foi deletado, pois não existia!");
                        }
                        teclado.nextLine();
                        continue;
                    }
                    case 7: {
                        listaCarros = stub.listarCarros("geral");
                        tempi = 0;
                        for (Carro iterador : listaCarros) {
                            System.out.println(tempi++ + " - " + iterador);
                        }
                        System.out.println("Digite a opção do carro que deseja alterar o dado: ");
                        tempi = teclado.nextInt();
                        teclado.nextLine();
                        Carro deletado = listaCarros.get(tempi);
                        System.out.println(
                                "Qual dado deseja alterar \n1 - Renavan \n2 - Nome\n3 - Categoria\n4 - Ano\n5 - Preço");
                        tempi = teclado.nextInt();
                        teclado.nextLine();
                        System.out.println("Digite o valor do novo dado:");
                        if (tempi == 4 || tempi == 5) {
                            tempi2 = teclado.nextInt();
                            teclado.nextLine();
                        } else {
                            temps1 = teclado.nextLine();
                        }
                        tempb = stub.alterarDado(tempi, deletado.getRenavan(), temps1, tempi2);
                        if (tempb) {
                            System.out.println("Dado Alterado com Sucesso.");
                        } else {
                            System.out.println("Nao foi possível alterar o dado.");
                        }
                        continue;
                    }
                    case 9: {
                        System.out.println("Qual réplica deseja desativar:");
                        tempi = teclado.nextInt();
                        teclado.nextLine();
                        stub.desativarReplica(tempi);
                    }

                    default: {
                        System.out.println("Opção Inválida!");
                        continue;
                    }

                }
            }

        } catch (Exception e) {
            System.err.println("Cliente: " + e.toString());
            e.printStackTrace();
        }

    }
}
