package Game;

import Game.model.*;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static Game.model.Board.BOARD_TEMPLATE;
import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

public class Main {
    private final static Scanner in = new Scanner(System.in);
    private static Board board;
    private final static int BOARD_LIMIT = 9;

    public static void main(String[] args) {
        final var positions = Stream.of(args)
                .collect(Collectors.toMap(
                        k -> k.split(";")[0],
                        v -> v.split(";")[1]
                ));

        var option = -1;

        while (true) {
            try {
                System.out.println("Selecione uma das opções a seguir");
                System.out.println("1 - Iniciar um novo Jogo");
                System.out.println("2 - Colocar um novo número");
                System.out.println("3 - Remover um número");
                System.out.println("4 - Visualizar jogo atual");
                System.out.println("5 - Verificar status do jogo");
                System.out.println("6 - Limpar jogo");
                System.out.println("7 - Finalizar jogo");
                System.out.println("8 - Sair");

                option = in.nextInt();
                switch (option) {
                    case 1 -> startGame(positions);
                    case 2 -> inputNumber();
                    case 3 -> removeNumber();
                    case 4 -> showCurrentGame();
                    case 5 -> showGameStatus();
                    case 6 -> clearGame();
                    case 7 -> finishGame();
                    case 8 -> System.exit(0);
                    default -> System.out.println("Opção invalida!");
                }
            } catch (InputMismatchException e) {
                System.out.println("Insira um inteiro válido!");
                in.nextLine();
            }
        }


    }


    private static void startGame(Map<String, String> positions) {
        if (nonNull(board)) {
            System.out.println("O jogo ja foi iniciado!");
            return;
        }

        List<List<Space>> spaces = new ArrayList<>();

        for (int i = 0; i < BOARD_LIMIT; i++) {

            spaces.add(new ArrayList<>());
            for (int j = 0; j < BOARD_LIMIT; j++) {
                var positionConfig = positions.get("%s,%s".formatted(i, j));
                var expected = Integer.parseInt(positionConfig.split(",")[0]);
                var fixed = Boolean.parseBoolean(positionConfig.split(",")[1]);
                var currentSpace = new Space(expected, fixed);
                spaces.get(i).add(currentSpace);
            }
        }
        board = new Board(spaces);
        System.out.println("Jogo pronto para começar!");
        showCurrentGame();
    }

    private static void inputNumber() {
        if (isNull(board)) {
            System.out.println("Jogo ainda não foi iniciado!");
            return;
        }
        boolean passou = true;
        while (passou) {
            try {
                System.out.println("Informe a coluna que será inserido");
                var col = runUntilGetValidNumber(0, BOARD_LIMIT - 1);
                System.out.println("Informe a linha que será inserido");
                var row = runUntilGetValidNumber(0, BOARD_LIMIT - 1);
                System.out.printf("Informe o valor que será inserido na posição [%s,%s]\n", col, row);
                var value = runUntilGetValidNumber(1, BOARD_LIMIT);
                if (!board.changeValue(col, row, value)) {
                    System.out.printf("A posição [%s,%s] tem um valor fixo\n", col, row);
                }
                passou = false;
            } catch (InputMismatchException e) {
                System.out.println("Insira um inteiro válido!");
                in.nextLine();
            }
        }
    }

    private static void removeNumber() {
        if (isNull(board)) {
            System.out.println("Jogo ainda não foi iniciado!");
            return;
        }

        boolean passou = true;
        while (passou) {
            try {
                System.out.println("Informe a coluna que será inserida");
                var col = runUntilGetValidNumber(0, BOARD_LIMIT - 1);
                System.out.println("Informe a linha que será inserido");
                var row = runUntilGetValidNumber(0, BOARD_LIMIT - 1);
                if (!board.clearValue(col, row)) {
                    System.out.printf("A posição [%s,%s] tem um valor fixo\n", col, row);
                }
                passou = false;
            } catch (InputMismatchException e) {
                System.out.println("Insira um inteiro válido!");
                in.nextLine();
            }
        }
    }

    private static void showCurrentGame() {
        if (isNull(board)) {
            System.out.println("O jogo não foi inicializado!");
            return;
        }

        var args = new Object[81];
        var argPos = 0;

        for (int i = 0; i < BOARD_LIMIT; i++) {
            for (var col : board.getSpaces()) {
                args[argPos++] = " " + (isNull(col.get(i).getActual()) ? " " : col.get(i).getActual());
            }
        }
        System.out.printf(BOARD_TEMPLATE.formatted(args));
    }

    private static void showGameStatus() {
        if (isNull(board)) {
            System.out.println("O jogo ainda não foi iniciado!");
            return;
        }
        System.out.println("O jogo se encontra no status: " + board.gameStatus().getLabel());
        if (board.hasErrors()) {
            System.out.println("O jogo contém erros");
        } else {
            System.out.println("O jogo não contém erros");
        }
    }

    private static void clearGame() {
        if (isNull(board)) {
            System.out.println("O jogo não foi inicializado!");
            return;
        }
        String confirm = "";
        do {
            try {
                System.out.println("Tem certeza que deseja limpar seu jogo?");
                confirm = in.nextLine();

                System.out.println("Digite 'sim' ou 'não'");

                if (confirm.equalsIgnoreCase("sim"))
                    board.reset();
            } catch (InputMismatchException e) {
                System.out.println("Insira um inteiro válido!");
                in.nextLine();
            }

        } while (!confirm.equalsIgnoreCase("sim") && !confirm.equalsIgnoreCase("não"));

    }


    private static void finishGame() {
        if (isNull(board)) {
            System.out.println("O jogo ainda não foi inicializado!");
            return;
        }

        if (board.gameIsFinished()) {
            System.out.println("Parabéns, você concluiu o jogo!");
            showCurrentGame();
            board = null;
        } else if (board.hasErrors()) {
            System.out.println("Seu jogo contém erros!");
        } else {
            System.out.println("Há espaços vazios!");
        }
    }


    private static int runUntilGetValidNumber(int min, int max) {
        var current = in.nextInt();

        while (current < min || current > max) {
            try {
                System.out.printf("Informe um numero entre %s e %s\n", min, max);
                current = in.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("Insira um inteiro válido!");
                in.nextLine();
            }
        }
        return current;
    }
}




