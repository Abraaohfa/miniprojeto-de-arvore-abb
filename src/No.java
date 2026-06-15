/**
 * Nó da Árvore Binária de Busca (ABB).
 * Cada nó armazena um Elemento e referências para os filhos esquerdo e direito.
 *
 * Estrutura de Dados I - Professor: Walace Bonfim
 */
public class No {

    Elemento elemento;
    No       esquerdo;
    No       direito;

    public No(Elemento elemento) {
        this.elemento  = elemento;
        this.esquerdo  = null;
        this.direito   = null;
    }
}
