import java.util.Arrays;
import java.util.Random;

public class CaixeiroViajante {

    private int[][] grafo;
    private int numCidades;
    private boolean[] visitado;
    private int[] melhorCaminho;
    private int menorCusto;

    public CaixeiroViajante(int numCidades) {
        this.numCidades = numCidades;
        this.grafo = gerarGrafoCompletoPonderado(numCidades);
        this.visitado = new boolean[numCidades];
        this.melhorCaminho = new int[numCidades + 1];
        this.menorCusto = Integer.MAX_VALUE;
    }

    private int[][] gerarGrafoCompletoPonderado(int numCidades) {
        int[][] matriz = new int[numCidades][numCidades];
        Random aleatorio = new Random(42);
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

    public void resolverForcaBruta() {
        Arrays.fill(visitado, false);
        melhorCaminho[0] = 0;
        visitado[0] = true;
        permutacaoForcaBruta(1, 0);
    }

    private void permutacaoForcaBruta(int posicaoAtual, int custoAtual) {
        if (posicaoAtual == numCidades) {
            if (grafo[melhorCaminho[posicaoAtual - 1]][melhorCaminho[0]] != -1) {
                custoAtual += grafo[melhorCaminho[posicaoAtual - 1]][melhorCaminho[0]];
                if (custoAtual < menorCusto) {
                    menorCusto = custoAtual;
                    System.arraycopy(melhorCaminho, 0, melhorCaminho, 0, numCidades + 1);
                }
            }
            return;
        }

        for (int i = 1; i < numCidades; i++) {
            if (!visitado[i] && grafo[melhorCaminho[posicaoAtual - 1]][i] != -1) {
                visitado[i] = true;
                melhorCaminho[posicaoAtual] = i;
                custoAtual += grafo[melhorCaminho[posicaoAtual - 1]][i];

                permutacaoForcaBruta(posicaoAtual + 1, custoAtual);

                custoAtual -= grafo[melhorCaminho[posicaoAtual - 1]][i];
                visitado[i] = false;
            }
        }
    }

    public void resolverGuloso() {
        Arrays.fill(visitado, false);
        melhorCaminho[0] = 0;
        visitado[0] = true;

        for (int i = 1; i < numCidades; i++) {
            int proximaCidade = -1;
            int custoMinimo = Integer.MAX_VALUE;
            int cidadeAtual = melhorCaminho[i - 1];

            for (int j = 0; j < numCidades; j++) {
                if (!visitado[j] && grafo[cidadeAtual][j] != -1 && grafo[cidadeAtual][j] < custoMinimo) {
                    proximaCidade = j;
                    custoMinimo = grafo[cidadeAtual][j];
                }
            }

            melhorCaminho[i] = proximaCidade;
            visitado[proximaCidade] = true;
        }

        melhorCaminho[numCidades] = 0;
        menorCusto = calcularCustoCaminho(melhorCaminho);
    }

    private int calcularCustoCaminho(int[] caminho) {
        int custoTotal = 0;
        for (int i = 0; i < numCidades; i++) {
            int cidadeAtual = caminho[i];
            int proximaCidade = caminho[i + 1];
            custoTotal += grafo[cidadeAtual][proximaCidade];
        }
        return custoTotal;
    }

    public int[] getMelhorCaminho() {
        return melhorCaminho;
    }

    public int getMenorCusto() {
        return menorCusto;
    }

    public static void main(String[] args) {
        int numCidades = 5;
        CaixeiroViajante cv = new CaixeiroViajante(numCidades);

        // Resolução usando força bruta
        cv.resolverForcaBruta();
        int[] melhorCaminhoForcaBruta = cv.getMelhorCaminho();
        int menorCustoForcaBruta = cv.getMenorCusto();

        // Resolução usando algoritmo guloso
        cv.resolverGuloso();
        int[] melhorCaminhoGuloso = cv.getMelhorCaminho();
        int menorCustoGuloso = cv.getMenorCusto();

        System.out.println("Caminho de menor custo (Força Bruta):");
        for (int cidade : melhorCaminhoForcaBruta) {
            System.out.print(cidade + " ");
        }
        System.out.println("Custo total: " + menorCustoForcaBruta);

        System.out.println("Caminho de menor custo (Guloso):");
        for (int cidade : melhorCaminhoGuloso) {
            System.out.print(cidade + " ");
        }
        System.out.println("Custo total: " + menorCustoGuloso);
    }
}

class RelatorioCaixeiroViajante {
    private int numGrafos;
    private long tempoTotalForcaBruta;
    private long tempoTotalGuloso;
    private int solucoesOtimaGuloso;

    public RelatorioCaixeiroViajante() {
        this.numGrafos = 0;
        this.tempoTotalForcaBruta = 0;
        this.tempoTotalGuloso = 0;
        this.solucoesOtimaGuloso = 0;
    }

    public void adicionarResultado(long tempoForcaBruta, long tempoGuloso, boolean encontrouSolucaoOtima) {
        numGrafos++;
        tempoTotalForcaBruta += tempoForcaBruta;
        tempoTotalGuloso += tempoGuloso;
        if (encontrouSolucaoOtima) {
            solucoesOtimaGuloso++;
        }
    }

    public void gerarRelatorio() {
        System.out.println("Relatório do Caixeiro Viajante:");
        System.out.println("Número de grafos processados: " + numGrafos);
        System.out.println("Tempo médio (Força Bruta): " + (double) tempoTotalForcaBruta / numGrafos + " ms");
        System.out.println("Tempo médio (Guloso): " + (double) tempoTotalGuloso / numGrafos + " ms");
        System.out.println("Soluções ótimas encontradas pelo algoritmo guloso: " + solucoesOtimaGuloso);
    }
}

class TesteAutomatizado {
    public static void main(String[] args) {
        int numGrafos = 1000;
        int numCidades = 10;

        RelatorioCaixeiroViajante relatorio = new RelatorioCaixeiroViajante();

        for (int i = 0; i < numGrafos; i++) {
            long inicioForcaBruta = System.currentTimeMillis();
            CaixeiroViajante cv = new CaixeiroViajante(numCidades);
            cv.resolverForcaBruta();
            long tempoForcaBruta = System.currentTimeMillis() - inicioForcaBruta;

            long inicioGuloso = System.currentTimeMillis();
            cv.resolverGuloso();
            long tempoGuloso = System.currentTimeMillis() - inicioGuloso;

            boolean encontrouSolucaoOtima = Arrays.equals(cv.getMelhorCaminho(), cv.getMelhorCaminho());

            relatorio.adicionarResultado(tempoForcaBruta, tempoGuloso, encontrouSolucaoOtima);
        }

        relatorio.gerarRelatorio();
    }
}
