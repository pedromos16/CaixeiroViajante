import java.util.Random;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello World!");
    }

    /**
     * Aleatório "fixo" para geração de testes repetitíveis
     */
    static Random aleatorio = new Random(42);

    /**
     * Retorna uma matriz quadrada de "vertices" x "vertices" com números inteiros,
     * representando um grafo completo. A diagonal principal está preenchida com
     * valor -1, indicando que não há aresta.
     * @param vertices A quantidade de vértices do grafo.
     * @return Matriz quadrada com custos de movimentação entre os vértices.
     */
    public static int[][] grafoCompletoPonderado(int vertices){
        int[][] matriz = new int[vertices][vertices];
        int valor;
        for (int i = 0; i < matriz.length; i++) {
            matriz[i][i]=-1;
            for (int j = i+1; j < matriz.length; j++) {
                valor = aleatorio.nextInt(25)+1;
                matriz[i][j] = valor;
                matriz[j][i] = valor;
            }
        }
        return matriz;
    }
}
