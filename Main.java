import java.util.Random;

public class Main {

    public static void main(String[] args) {
        int[][] grafo = grafoCompletoPonderado(14); // Substitua o número de vértices desejado aqui
        int[] melhorCaminho = encontrarMenorCaminho(grafo);
        System.out.println("Melhor caminho: " + imprimirCaminho(melhorCaminho));
    }

    static Random aleatorio = new Random(42);

    public static int[][] grafoCompletoPonderado(int vertices) {
        int[][] matriz = new int[vertices][vertices];
        int valor;
        for (int i = 0; i < matriz.length; i++) {
            matriz[i][i] = -1;
            for (int j = i + 1; j < matriz.length; j++) {
                valor = aleatorio.nextInt(25) + 1;
                matriz[i][j] = valor;
                matriz[j][i] = valor;
            }
        }
        return matriz;
    }

    public static int[] encontrarMenorCaminho(int[][] grafo) {
        int vertices = grafo.length;
        int[] caminhoAtual = new int[vertices + 1];
        int[] melhorCaminho = new int[vertices + 1];
        boolean[] visitado = new boolean[vertices];
        int custoAtual, menorCusto = Integer.MAX_VALUE;
        int cidadeAtual, proximaCidade;

        caminhoAtual[0] = 0; // Começa na cidade 0
        visitado[0] = true;

        permutacao(grafo, caminhoAtual, 1, visitado, 0, vertices, 0, melhorCaminho, menorCusto);

        return melhorCaminho;
    }

    public static void permutacao(int[][] grafo, int[] caminhoAtual, int posicaoAtual, boolean[] visitado,
                                  int custoAtual, int vertices, int nivel, int[] melhorCaminho, int menorCusto) {
        if (posicaoAtual == vertices) {
            if (grafo[caminhoAtual[posicaoAtual - 1]][caminhoAtual[0]] != -1) {
                custoAtual += grafo[caminhoAtual[posicaoAtual - 1]][caminhoAtual[0]];
                if (custoAtual < menorCusto) {
                    menorCusto = custoAtual;
                    System.arraycopy(caminhoAtual, 0, melhorCaminho, 0, vertices + 1);
                }
            }
            return;
        }

        for (int i = 1; i < vertices; i++) {
            if (!visitado[i] && grafo[caminhoAtual[posicaoAtual - 1]][i] != -1) {
                visitado[i] = true;
                caminhoAtual[posicaoAtual] = i;
                custoAtual += grafo[caminhoAtual[posicaoAtual - 1]][i];

                permutacao(grafo, caminhoAtual, posicaoAtual + 1, visitado, custoAtual, vertices, nivel + 1,
                        melhorCaminho, menorCusto);

                custoAtual -= grafo[caminhoAtual[posicaoAtual - 1]][i];
                visitado[i] = false;
            }
        }
    }

    public static String imprimirCaminho(int[] caminho) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < caminho.length - 1; i++) {
            sb.append(caminho[i]).append(" -> ");
        }
        sb.append(caminho[caminho.length - 1]);
        return sb.toString();
    }
}
