/**
 * Elemento armazenado em cada nó da Árvore Binária de Busca (ABB).
 * Contém o RGM (chave de busca) e o Nome do aluno.
 *
 * Estrutura de Dados I - Professor: Walace Bonfim
 */
public class Elemento {

    private int rgm;
    private String nome;

    public Elemento(int rgm, String nome) {
        this.rgm  = rgm;
        this.nome = nome;
    }

    public int getRgm() {
        return rgm;
    }

    public void setRgm(int rgm) {
        this.rgm = rgm;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    @Override
    public String toString() {
        return "RGM: " + rgm + " | Nome: " + nome;
    }
}
