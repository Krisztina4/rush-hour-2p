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
    private final String targetVehicleId;

    public GameController(Board board, ConsoleView view, GameResultRepository repository, String targetVehicleId) {
        this.board = board;
        this.view = view;
        this.repository = repository;
        this.targetVehicleId = targetVehicleId;
    }

    public void play() {
        String playerName = view.readString("Kérem a játékos nevét");
        int steps = 0;

        while (!board.isSolved(targetVehicleId)) {
            view.printBoard(board);
            String input = view.readString("Lépés (pl. 'R R' -> R jármű jobbra, irányok: U, D, L, R) vagy 'exit'");

            if (input.equalsIgnoreCase("exit")) {
                view.printMessage("Játék megszakítva.");
                return;
            }

            String[] parts = input.split(" ");
            if (parts.length != 2) {
                view.printMessage("Hibás formátum! Használat: <JárműID> <Irány>");
                continue;
            }

            String vehicleId = parts[0];
            Direction direction = parseDirection(parts[1]);

            if (direction == null) {
                view.printMessage("Hibás irány! Engedélyezett: U (fel), D (le), L (bal), R (jobb).");
                continue;
            }

            try {
                board.moveVehicle(vehicleId, direction);
                steps++;
            } catch (IllegalArgumentException e) {
                view.printMessage("Hiba: " + e.getMessage());
            }
        }

        view.printBoard(board);
        view.printMessage("Győzelem! " + playerName + " megoldotta " + steps + " lépésből.");

        repository.saveResult(new GameResult(playerName, steps));
        view.printTopResults(repository.getTopResults(5));
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