/**
 * Árvore Binária de Busca (ABB).
 *
 * Operações implementadas:
 *   - inserir          : insere um Elemento respeitando as regras de ABB (RGM como chave)
 *   - remover          : remove um nó pelo RGM usando o SUCESSOR (menor nó da subárvore direita)
 *   - pesquisar        : busca um nó pelo RGM
 *   - esvaziar         : remove todos os nós (percurso pós-ordem)
 *   - exibirPreOrdem   : Raiz → Esquerdo → Direito
 *   - exibirInOrdem    : Esquerdo → Raiz → Direito  (resultado em ordem crescente de RGM)
 *   - exibirPosOrdem   : Esquerdo → Direito → Raiz
 *   - exibirGrafico    : representação visual da árvore no console (rotacionada 90°)
 *
 * Estrutura de Dados I - Professor: Walace Bonfim
 */
public class ABB {

    private No raiz;

    public ABB() {
        raiz = null;
    }

    public void inserir(Elemento e) {
        raiz = inserirRec(raiz, e);
    }

    private No inserirRec(No atual, Elemento e) {
        // Posição vazia → cria o nó
        if (atual == null) {
            System.out.println("Inserido: " + e);
            return new No(e);
        }

        if (e.getRgm() < atual.elemento.getRgm()) {
            atual.esquerdo = inserirRec(atual.esquerdo, e);
        } else if (e.getRgm() > atual.elemento.getRgm()) {
            atual.direito = inserirRec(atual.direito, e);
        } else {
            System.out.println("RGM " + e.getRgm() + " já existe na árvore. Inserção ignorada.");
        }

        return atual;
    }


    public void remover(int rgm) {
        No encontrado = pesquisarNo(raiz, rgm);

        if (encontrado == null) {
            System.out.println("RGM " + rgm + " não encontrado na árvore.");
        } else {
            System.out.println("Removendo: " + encontrado.elemento);
            raiz = removerRec(raiz, rgm);
        }
    }

    private No removerRec(No atual, int rgm) {
        if (atual == null) {
            return null;
        }

        if (rgm < atual.elemento.getRgm()) {
            atual.esquerdo = removerRec(atual.esquerdo, rgm);

        } else if (rgm > atual.elemento.getRgm()) {
            atual.direito = removerRec(atual.direito, rgm);

        } else {

            if (atual.esquerdo == null && atual.direito == null) {
                return null;
            }

            // Caso 2a: apenas filho direito
            if (atual.esquerdo == null) {
                return atual.direito;
            }

            // Caso 2b: apenas filho esquerdo
            if (atual.direito == null) {
                return atual.esquerdo;
            }

            // Caso 3: dois filhos → substitui pelo SUCESSOR (menor da subárvore direita)
            No sucessor = menorNo(atual.direito);
            System.out.println("  (Sucessor utilizado: " + sucessor.elemento + ")");
            atual.elemento = sucessor.elemento;                    // copia o dado do sucessor
            atual.direito  = removerRec(atual.direito, sucessor.elemento.getRgm()); // remove o sucessor
        }

        return atual;
    }

    private No menorNo(No no) {
        No atual = no;
        while (atual.esquerdo != null) {
            atual = atual.esquerdo;
        }
        return atual;
    }


    public void pesquisar(int rgm) {
        No resultado = pesquisarNo(raiz, rgm);
        if (resultado == null) {
            System.out.println("RGM " + rgm + " NÃO encontrado na árvore.");
        } else {
            System.out.println("Encontrado → " + resultado.elemento);
        }
    }

    private No pesquisarNo(No atual, int rgm) {
        if (atual == null) {
            return null;
        }
        if (rgm == atual.elemento.getRgm()) {
            return atual;
        }
        if (rgm < atual.elemento.getRgm()) {
            return pesquisarNo(atual.esquerdo, rgm);
        }
        return pesquisarNo(atual.direito, rgm);
    }


    public void esvaziar() {
        esvaziarRec(raiz);
        raiz = null;
        System.out.println("Árvore esvaziada com sucesso.");
    }

    private void esvaziarRec(No atual) {
        if (atual == null) return;
        esvaziarRec(atual.esquerdo);
        esvaziarRec(atual.direito);
        System.out.println("  Removendo nó: " + atual.elemento);
    }


    public void exibirPreOrdem() {
        System.out.println("\n=== PRÉ-ORDEM ===");
        if (raiz == null) {
            System.out.println("Árvore vazia.");
            return;
        }
        preOrdemRec(raiz);
        System.out.println();
    }

    private void preOrdemRec(No atual) {
        if (atual == null) return;
        System.out.println("  " + atual.elemento);
        preOrdemRec(atual.esquerdo);
        preOrdemRec(atual.direito);
    }

    public void exibirInOrdem() {
        System.out.println("\n=== IN-ORDEM (ordem crescente de RGM) ===");
        if (raiz == null) {
            System.out.println("Árvore vazia.");
            return;
        }
        inOrdemRec(raiz);
        System.out.println();
    }

    private void inOrdemRec(No atual) {
        if (atual == null) return;
        inOrdemRec(atual.esquerdo);
        System.out.println("  " + atual.elemento);
        inOrdemRec(atual.direito);
    }


    public void exibirPosOrdem() {
        System.out.println("\n=== PÓS-ORDEM ===");
        if (raiz == null) {
            System.out.println("Árvore vazia.");
            return;
        }
        posOrdemRec(raiz);
        System.out.println();
    }

    private void posOrdemRec(No atual) {
        if (atual == null) return;
        posOrdemRec(atual.esquerdo);
        posOrdemRec(atual.direito);
        System.out.println("  " + atual.elemento);
    }


    public void exibirGrafico() {
        System.out.println("\n=== EXIBIÇÃO GRÁFICA (90° - direita=topo) ===");
        if (raiz == null) {
            System.out.println("Árvore vazia.");
            return;
        }
        exibirGraficoRec(raiz, 0);
        System.out.println();
    }

    private void exibirGraficoRec(No atual, int nivel) {
        if (atual == null) return;

        // Visita primeiro o filho DIREITO (fica no topo na exibição rotacionada)
        exibirGraficoRec(atual.direito, nivel + 1);

        // Indentação proporcional ao nível
        String indent = "    ".repeat(nivel);
        System.out.println(indent + "[" + atual.elemento.getRgm() + "] " + atual.elemento.getNome());

        // Depois o filho ESQUERDO (fica na parte de baixo)
        exibirGraficoRec(atual.esquerdo, nivel + 1);
    }


    public boolean estaVazia() {
        return raiz == null;
    }
}
