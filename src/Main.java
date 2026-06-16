import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

/**
 * ============================================================
 *  EDITOR DE ÁRVORE BINÁRIA DE BUSCA (ABB)
 *  Estrutura de Dados I
 *  Professor: Walace Bonfim
 * ============================================================
 *
 *  Como executar:
 *    1. Compile todos os .java:   javac src/*.java -d out/
 *    2. Execute:                  java -cp out Main
 *
 *  O arquivo de dados deve estar em:  dados/alunos.txt
 *  Formato de cada linha:             <RGM> <Nome Completo>
 *  Exemplo:
 *       20231001 Ana Paula Souza
 *       20231002 Bruno Henrique Lima
 */
public class Main {

    // -----------------------------------------------------------------
    // Constante: caminho do arquivo de dados iniciais
    // -----------------------------------------------------------------
    private static final String ARQUIVO_DADOS = "dados/alunos.txt";

    // -----------------------------------------------------------------
    // Dados da equipe — EDITE com os nomes reais do grupo!
    // -----------------------------------------------------------------
    private static final String[] NOMES_EQUIPE = {
            "43428827 João Pedro Medeiros Barbosa",
            "NOME COMPLETO DO ALUNO 2",
            "NOME COMPLETO DO ALUNO 3",
            "NOME COMPLETO DO ALUNO 4",
            "NOME COMPLETO DO ALUNO 5"
            // adicione o 6º se necessário
    };

    // -----------------------------------------------------------------
    // main
    // -----------------------------------------------------------------
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ABB    arvore  = new ABB();

        // Carrega a árvore a partir do arquivo de texto
        carregarArquivo(arvore, ARQUIVO_DADOS);

        int opcao;

        do {
            exibirMenu();
            System.out.print("DIGITE SUA OPÇÃO: ");

            // Trata entrada inválida (não-numérica)
            while (!scanner.hasNextInt()) {
                System.out.println("Entrada inválida. Digite um número.");
                scanner.next();
                System.out.print("DIGITE SUA OPÇÃO: ");
            }
            opcao = scanner.nextInt();
            scanner.nextLine(); // consome o '\n' restante

            System.out.println(); // linha em branco para melhor legibilidade

            switch (opcao) {

                // ---- 1. INSERIR ----
                case 1:
                    System.out.print("RGM   : ");
                    int rgmIns = lerInteiro(scanner);
                    scanner.nextLine();
                    System.out.print("Nome  : ");
                    String nomeIns = scanner.nextLine().trim();
                    if (nomeIns.isEmpty()) {
                        System.out.println("Nome não pode ser vazio.");
                    } else {
                        arvore.inserir(new Elemento(rgmIns, nomeIns));
                    }
                    break;

                // ---- 2. REMOVER ----
                case 2:
                    System.out.print("RGM a remover: ");
                    int rgmRem = lerInteiro(scanner);
                    scanner.nextLine();
                    arvore.remover(rgmRem);
                    break;

                // ---- 3. PESQUISAR ----
                case 3:
                    System.out.print("RGM a pesquisar: ");
                    int rgmPes = lerInteiro(scanner);
                    scanner.nextLine();
                    arvore.pesquisar(rgmPes);
                    break;

                // ---- 4. ESVAZIAR ----
                case 4:
                    System.out.print("Confirma esvaziamento? (S/N): ");
                    String confirm = scanner.nextLine().trim().toUpperCase();
                    if (confirm.equals("S")) {
                        arvore.esvaziar();
                    } else {
                        System.out.println("Operação cancelada.");
                    }
                    break;

                // ---- 5. EXIBIR ----
                case 5:
                    exibirSubMenu();
                    System.out.print("Escolha o tipo de exibição: ");
                    String subOpcao = scanner.nextLine().trim().toUpperCase();
                    switch (subOpcao) {
                        case "1": case "PRE": case "PRÉ":
                            arvore.exibirPreOrdem();
                            break;
                        case "2": case "IN":
                            arvore.exibirInOrdem();
                            break;
                        case "3": case "POS": case "PÓS":
                            arvore.exibirPosOrdem();
                            break;
                        case "4": case "G": case "GRAFICO": case "GRÁFICO":
                            arvore.exibirGrafico();
                            break;
                        case "5": case "TODOS":
                            arvore.exibirPreOrdem();
                            arvore.exibirInOrdem();
                            arvore.exibirPosOrdem();
                            arvore.exibirGrafico();
                            break;
                        default:
                            System.out.println("Opção de exibição inválida.");
                    }
                    break;

                // ---- 0. SAIR ----
                case 0:
                    System.out.println("Encerrando o programa. Até mais!");
                    break;

                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }

            System.out.println(); // separador visual entre operações

        } while (opcao != 0);

        scanner.close();
    }

    // -----------------------------------------------------------------
    // Exibe o menu principal no formato exigido pelo enunciado
    // -----------------------------------------------------------------
    private static void exibirMenu() {
        System.out.println("============================================");
        // Exibe todos os integrantes da equipe
        for (String nome : NOMES_EQUIPE) {
            System.out.println("ALUNO: " + nome);
        }
        System.out.println("DISCIPLINA: ESTRUTURA DE DADOS I");
        System.out.println("PROFESSOR : WALACE BONFIM");
        System.out.println("--------------------------------------------");
        System.out.println("          EDITOR DE ÁRVORE ABB              ");
        System.out.println("--------------------------------------------");
        System.out.println(" 1 – INSERIR         (fornecer RGM e Nome)  ");
        System.out.println(" 2 – REMOVER UM NÓ   (fornecer RGM)        ");
        System.out.println(" 3 – PESQUISAR       (fornecer RGM)        ");
        System.out.println(" 4 – ESVAZIAR A ÁRVORE                     ");
        System.out.println(" 5 – EXIBIR A ÁRVORE                       ");
        System.out.println(" 0 – SAIR                                  ");
        System.out.println("============================================");
    }

    // -----------------------------------------------------------------
    // Sub-menu de exibição
    // -----------------------------------------------------------------
    private static void exibirSubMenu() {
        System.out.println("  1 – PRÉ-ORDEM");
        System.out.println("  2 – IN-ORDEM");
        System.out.println("  3 – PÓS-ORDEM");
        System.out.println("  4 – GRÁFICO");
        System.out.println("  5 – TODOS");
    }

    // -----------------------------------------------------------------
    // Carrega os dados do arquivo texto para a ABB
    //
    // Formato esperado de cada linha:
    //   <RGM_inteiro> <Nome do Aluno com espaços>
    //   Ex.:  20231001 Ana Paula Souza
    // -----------------------------------------------------------------
    private static void carregarArquivo(ABB arvore, String caminho) {
        System.out.println("Carregando dados de: " + caminho);

        try (BufferedReader br = new BufferedReader(new FileReader(caminho))) {
            String linha;
            int count = 0;

            while ((linha = br.readLine()) != null) {
                linha = linha.trim();

                // Ignora linhas vazias ou comentários (iniciados com #)
                if (linha.isEmpty() || linha.startsWith("#")) continue;

                // Divide apenas na PRIMEIRA ocorrência de espaço/tab
                // Isso preserva nomes compostos como "Ana Paula Souza"
                String[] partes = linha.split("\\s+", 2);

                if (partes.length < 2) {
                    System.out.println("  Linha ignorada (formato inválido): " + linha);
                    continue;
                }

                try {
                    int    rgm  = Integer.parseInt(partes[0]);
                    String nome = partes[1].trim();
                    arvore.inserir(new Elemento(rgm, nome));
                    count++;
                } catch (NumberFormatException ex) {
                    System.out.println("  RGM inválido na linha: " + linha);
                }
            }

            System.out.println("Total de registros carregados: " + count + "\n");

        } catch (IOException e) {
            System.out.println("Arquivo '" + caminho + "' não encontrado.");
            System.out.println("A árvore será iniciada vazia.\n");
        }
    }

    // -----------------------------------------------------------------
    // Lê um inteiro com tratamento de entrada inválida
    // -----------------------------------------------------------------
    private static int lerInteiro(Scanner scanner) {
        while (!scanner.hasNextInt()) {
            System.out.println("Valor inválido. Digite um número inteiro.");
            scanner.next();
        }
        return scanner.nextInt();
    }
}
