import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Main {

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

    public static int calcularCustoViagem(int[][] matriz, int[] permutacao) {
        int custo = 0;
        for (int i = 0; i < permutacao.length - 1; i++) {
            int origem = permutacao[i];
            int destino = permutacao[i + 1];
            custo += matriz[origem][destino];
        }
        return custo;
    }

    public static int[] encontrarMenorCusto(int[][] matriz, List<int[]> permutacoes) {
        int menorCusto = Integer.MAX_VALUE;
        int[] melhorPermutacao = null;

        for (int[] permutacao : permutacoes) {
            int custo = calcularCustoViagem(matriz, permutacao);
            if (custo < menorCusto) {
                menorCusto = custo;
                melhorPermutacao = permutacao;
            }
        }

        return melhorPermutacao;
    }

    public static List<int[]> gerarPermutacoes(int vertices) {
        List<int[]> permutacoes = new ArrayList<>();
        int[] permutacaoInicial = new int[vertices];
        for (int i = 0; i < vertices; i++) {
            permutacaoInicial[i] = i;
        }
        permutar(permutacoes, permutacaoInicial, 0);
        return permutacoes;
    }

    private static void permutar(List<int[]> permutacoes, int[] permutacao, int indice) {
        if (indice == permutacao.length - 1) {
            permutacoes.add(permutacao.clone());
        } else {
            for (int i = indice; i < permutacao.length; i++) {
                trocar(permutacao, indice, i);
                permutar(permutacoes, permutacao, indice + 1);
                trocar(permutacao, indice, i); // Reverter a troca para restaurar a ordem original
            }
        }
    }

    private static void trocar(int[] array, int i, int j) {
        int temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }


    public static void main(String[] args) {
        int vertices = 11;
        int[][] matriz = grafoCompletoPonderado(vertices);

        List<int[]> permutacoes = gerarPermutacoes(vertices);
        int[] melhorPermutacao = encontrarMenorCusto(matriz, permutacoes);

        System.out.println("Melhor permutação: " + Arrays.toString(melhorPermutacao));
        System.out.println("Custo total: " + calcularCustoViagem(matriz, melhorPermutacao));
    }
}
