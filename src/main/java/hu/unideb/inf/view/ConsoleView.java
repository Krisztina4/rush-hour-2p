package hu.unideb.inf.view;

import hu.unideb.inf.model.Board;
import hu.unideb.inf.model.Position;
import hu.unideb.inf.model.Vehicle;
import hu.unideb.inf.persistence.GameResult;

import java.util.List;
import java.util.Scanner;

public class ConsoleView {

    private final Scanner scanner;

    public ConsoleView() {
        this.scanner = new Scanner(System.in);
    }

    public void printBoard(Board board) {
        int size = board.getSize();
        String[][] grid = new String[size][size];

        for (int y = 0; y < size; y++) {
            for (int x = 0; x < size; x++) {
                grid[y][x] = ".";
            }
        }

        for (Vehicle vehicle : board.getVehicles()) {
            String symbol = vehicle.getId().substring(0, 1);
            for (Position p : vehicle.getOccupiedPositions()) {
                grid[p.y()][p.x()] = symbol;
            }
        }

        System.out.println("Játéktér:");
        for (int y = 0; y < size; y++) {
            for (int x = 0; x < size; x++) {
                System.out.print(grid[y][x] + " ");
            }
            System.out.println();
        }
        System.out.println();
    }

    public void printMessage(String message) {
        System.out.println(message);
    }

    public String readString(String prompt) {
        System.out.print(prompt + ": ");
        return scanner.nextLine().trim();
    }

    public void printTopResults(List<GameResult> results) {
        System.out.println("--- Ranglista ---");
        for (int i = 0; i < results.size(); i++) {
            GameResult result = results.get(i);
            System.out.printf("%d. %s - %d lépés%n", i + 1, result.playerName(), result.steps());
        }
        System.out.println("-----------------");
    }
}