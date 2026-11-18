package hu.unideb.inf.controller;

import hu.unideb.inf.model.Board;
import hu.unideb.inf.model.Direction;
import hu.unideb.inf.persistence.GameResult;
import hu.unideb.inf.persistence.GameResultRepository;
import hu.unideb.inf.view.ConsoleView;

public class GameController {

    private final Board board;
    private final ConsoleView view;
    private final GameResultRepository repository;
    private final String targetVehicleId1;
    private final String targetVehicleId2;

    public GameController(Board board, ConsoleView view, GameResultRepository repository,
                          String targetVehicleId1, String targetVehicleId2) {
        this.board = board;
        this.view = view;
        this.repository = repository;
        this.targetVehicleId1 = targetVehicleId1;
        this.targetVehicleId2 = targetVehicleId2;
    }

    public void play() {
        String player1Name = view.readString("Kérem az 1. játékos nevét");
        String player2Name = view.readString("Kérem a 2. játékos nevét");

        int player1Steps = 0;
        int player2Steps = 0;
        int currentPlayer = 1;

        while (true) {
            String activePlayerName = (currentPlayer == 1) ? player1Name : player2Name;
            String activeTargetId = (currentPlayer == 1) ? targetVehicleId1 : targetVehicleId2;

            view.printMessage("\n========================================");
            view.printMessage("Soron következik: " + activePlayerName + " (Céljárműved: " + activeTargetId + ")");
            view.printMessage("========================================");
            view.printBoard(board);

            String input = view.readString("Lépés (pl. '" + activeTargetId + " R' vagy '" + activeTargetId + " R 2') vagy 'exit'");

            if (input.equalsIgnoreCase("exit")) {
                view.printMessage("A játékot megszakították.");
                return;
            }

            String[] parts = input.split("\\s+");
            if (parts.length < 2 || parts.length > 3) {
                view.printMessage("Hibás formátum! Használat: <JárműID> <Irány> [Távolság]");
                continue;
            }

            String vehicleId = parts[0];
            Direction direction = parseDirection(parts[1]);
            int distance = 1;

            if (parts.length == 3) {
                try {
                    distance = Integer.parseInt(parts[2]);
                } catch (NumberFormatException e) {
                    view.printMessage("Hibás lépéstávolság! Pozitív egész számot adj meg.");
                    continue;
                }
            }

            if (direction == null) {
                view.printMessage("Hibás irány! Engedélyezett: U (fel), D (le), L (bal), R (jobb).");
                continue;
            }

            try {
                board.moveVehicle(vehicleId, direction, distance);

                if (currentPlayer == 1) {
                    player1Steps++;
                } else {
                    player2Steps++;
                }

                if (board.isSolved(activeTargetId)) {
                    int finalSteps = (currentPlayer == 1) ? player1Steps : player2Steps;
                    view.printBoard(board);
                    view.printMessage("GYŐZELEM! " + activePlayerName + " megnyerte a játékot " + finalSteps + " lépésből!");

                    repository.saveResult(new GameResult(activePlayerName, finalSteps));
                    view.printTopResults(repository.getTopResults(5));
                    break;
                }

                currentPlayer = (currentPlayer == 1) ? 2 : 1;

            } catch (IllegalArgumentException e) {
                view.printMessage("Hiba: " + e.getMessage() + " Újra te lépsz!");
            }
        }
    }

    private Direction parseDirection(String input) {
        return switch (input.toUpperCase()) {
            case "U" -> Direction.UP;
            case "D" -> Direction.DOWN;
            case "L" -> Direction.LEFT;
            case "R" -> Direction.RIGHT;
            default -> null;
        };
    }
}