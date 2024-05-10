import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        String[][] tabuleiroPrint =
                {
                        {"A1", "B1", "C1", "D1", "E1", "F1", "G1", "H1"},
                        {"A2", "B2", "C2", "D2", "E2", "F2", "G2", "H2"},
                        {"A3", "B3", "C3", "D3", "E3", "F3", "G3", "H3"},
                        {"A4", "B4", "C4", "D4", "E4", "F4", "G4", "H4"},
                        {"A5", "B5", "C5", "D5", "E5", "F5", "G5", "H5"},
                        {"A6", "B6", "C6", "D6", "E6", "F6", "G6", "H6"},
                        {"A7", "B7", "C7", "D7", "E7", "F7", "G7", "H7"},
                        {"A8", "B8", "C8", "D8", "E8", "F8", "G8", "H8"}
                };

        int[] casaAnterior = new int[2];

        int[] casaAtual;

        //Casa para verificar casas futuras
        int[] casaTeste = new int[2];

        // Opcoes de movimento
        int[][] opcoesDeMovimento = new int[2][8];

        int numPassos = 0;

        // Tabuleiro
        boolean[][] tabuleiro = new boolean[8][8];

        System.out.println("Digite a casa inicial");

        // primeiro movimento
        Scanner scan = new Scanner(System.in);

        casaAtual = lerString(scan.next());

        //Casa atual vira true
        tabuleiro[casaAtual[0]][casaAtual[1]] = true;

        while (numPassos != 64) {
            //Array que decide o proximo movimento
            int[] menorMovimento = new int[2];
            //preenche os array com as opcoes de movimento
            preencherMovimentosValidos(casaAtual, tabuleiro, opcoesDeMovimento);
            // int para validar se a casa tem a menor quantidade de movimentos validos
            int menor = 9;

            for (int j = 0; j < 8; j++) {

                //Atribui a opcao a ser contada a quantidade de passos
                casaTeste[0] = opcoesDeMovimento[0][j];

                casaTeste[1] = opcoesDeMovimento[1][j];

                int movimentosContados = contarMovimentos(casaTeste, tabuleiro);

                // Salva o movimento eficiente
                if(movimentosContados < menor && opcoesDeMovimento[0][j] != -1){
                    menorMovimento[0] = casaTeste[0];
                    menorMovimento[1] = casaTeste[1];
                    menor = movimentosContados;
                }

            }

            casaAnterior[0] = casaAtual[0];
            casaAnterior[1] = casaAtual[1];

            casaAtual[0] = menorMovimento[0];
            casaAtual[1] = menorMovimento[1];

            tabuleiro[ casaAtual[0] ] [ casaAtual[1] ] = true;

            numPassos++;

            printarCasa(tabuleiroPrint, casaAtual, casaAnterior, numPassos);

            for (int j = 0; j < 8; j++) {
                System.out.print("----------------------------" + "\n" + "|");
                for (int k = 0; k < 8; k++) {
                    System.out.print(tabuleiroPrint[j][k] + " " + "|");
                }
                System.out.println("\n");
            }
            System.out.print("----------------------------" + "\n" + "\n" + "\n");

        }
    }
    // mais perto do centro, mais perto da ponta !=

    public static int[] popularOpcoes(int direcao){
        int[] opcao = new int[2];
        switch(direcao){
            //Direita para baixo ( 1 linha + 2 coluna )
            case 0:
                opcao[0] = 1;
                opcao[1] = 2;
                return  opcao;
            //direita para cima ( -1 linha +2 coluna)
            case 1:
                opcao[0] = -1;
                opcao[1] = 2;
                return  opcao;
            // Cima para direita ( -2 linha +1 coluna)
            case 2:
                opcao[0] = -2;
                opcao[1] = 1;
                return  opcao;
            //Cima para esquerda
            case 3:
                opcao[0] = -2;
                opcao[1] = -1;
                return  opcao;
            //Esquerda para cima
            case 4:
                opcao[0] = -1;
                opcao[1] = -2;
                return  opcao;
            // Esquerda para baixo
            case 5:
                opcao[0] = 1;
                opcao[1] = -2;
                return  opcao;
            //Baixo para esquerda -2
            case 6:
                opcao[0] = 2;
                opcao[1] = -1;
                return  opcao;
            //Baixo para direita
            case 7:
                opcao[0] = 2;
                opcao[1] = 1;
                return  opcao;
            default: return opcao;

        }
    }

    public static int[] lerString(String posicaoString){
        //Vetor com posicao
        int[] posicaoInt = new int[2];
        //Letra para transformar
        String cha = "" + posicaoString.charAt(0);
        //Numero a transformar
        String num = "" + posicaoString.charAt(1);
        //Int que armazena letra transformada

        int coluna = switch (cha) {
            case "A" -> 0;
            case "B" -> 1;
            case "C" -> 2;
            case "D" -> 3;
            case "E" -> 4;
            case "F" -> 5;
            case "G" -> 6;
            case "H" -> 7;
            default -> -1;
        };

        //le a Coluna
        posicaoInt[1] = coluna;

        //Le a linha
        posicaoInt[0] = Integer.parseInt(num) - 1;

        return posicaoInt;
    }

    //preenche os array com as opcoes de movimento
    public static void preencherMovimentosValidos(int[] casaAtual, boolean[][]tabuleiro, int[][]opcoesDeMovimento){
        int i = 0;
        do{
            int[] opcoes = popularOpcoes(i);
            if ((opcoes[0] + casaAtual[0]) <= 7 && ((opcoes[0] + casaAtual[0]) >= 0)
                    && (opcoes[1] + casaAtual[1]) <= 7 && ((opcoes[1] + casaAtual[1]) >= 0)) {
                if(!tabuleiro[opcoes[0] + casaAtual[0]][opcoes[1] + casaAtual[1]]){
                    opcoesDeMovimento[0][i] = opcoes[0] + casaAtual[0];
                    opcoesDeMovimento[1][i] = opcoes[1] + casaAtual[1];

                } else{
                    opcoesDeMovimento[0][i] = -1;
                    opcoesDeMovimento[1][i] = -1;
                }

            } else {
                opcoesDeMovimento[0][i] = -1;
                opcoesDeMovimento[1][i] = -1;
            }
            i++;

        } while (i != 8);

    }

    // Conta os movimentos validos das casas futuras e retorna a contagem
    public static int contarMovimentos(int[] casaFutura, boolean[][] tabuleiro){

        int i = 0, contador = 0;
        while (i != 8){
            int[] opcoes = popularOpcoes(i);
            if ((opcoes[0] + casaFutura[0]) <= 7 && ((opcoes[0] + casaFutura[0]) >= 0)
                    && (opcoes[1] + casaFutura[1]) <= 7 && ((opcoes[1] + casaFutura[1]) >= 0)) {

                if(!tabuleiro[opcoes[0] + casaFutura[0]][opcoes[1] + casaFutura[1]]){
                    contador++;

                }
            }
            i++;
        }
        return contador;
    }

    public static void printarCasa(String[][] tabuleiroVisivel, int[] casaAtual, int[] casaAnterior, int numPassos){
        tabuleiroVisivel[casaAtual[0]][casaAtual[1]] = "X";
        tabuleiroVisivel[casaAnterior[0]] [casaAnterior[1]] = String.valueOf(numPassos);
    }
}

/*
*                       {"A1", "B1", "C1", "D1", "E1", "F1", "G1", "H1"},
                        {"A2", "B2", "C2", "D2", "E2", "F2", "G2", "H2"},
                        {"A3", "B3", "C3", "D3", "E3", "F3", "G3", "H3"},
                        {"A4", "B4", "C4", "D4", "E4", "F4", "G4", "H4"},
                        {"A5", "B5", "C5", "D5", "E5", "F5", "G5", "H5"},
                        {"A6", "B6", "C6", "D6", "E6", "F6", "G6", "H6"},
                        {"A7", "B7", "C7", "D7", "E7", "F7", "G7", "H7"},
                        {"A8", "B8", "C8", "D8", "E8", "F8", "G8", "H8"}
* */