package hu.unideb.inf;

import hu.unideb.inf.controller.GameController;
import hu.unideb.inf.model.Board;
import hu.unideb.inf.model.Orientation;
import hu.unideb.inf.model.Position;
import hu.unideb.inf.model.Vehicle;
import hu.unideb.inf.persistence.GameResultRepository;
import hu.unideb.inf.view.ConsoleView;

public class Main {

    public static void main(String[] args) {
        String dbUrl = "jdbc:h2:./data/rushhour_db;AUTO_SERVER=TRUE";
        GameResultRepository repository = new GameResultRepository(dbUrl, "sa", "");

        Board board = new Board(6);

        // Céljármű (piros autó)
        board.addVehicle(new Vehicle("R", 2, Orientation.HORIZONTAL, new Position(0, 2)));

        // Akadályozó járművek a pályán
        board.addVehicle(new Vehicle("A", 2, Orientation.VERTICAL, new Position(2, 1)));
        board.addVehicle(new Vehicle("B", 3, Orientation.HORIZONTAL, new Position(0, 0)));
        board.addVehicle(new Vehicle("C", 3, Orientation.VERTICAL, new Position(3, 1)));

        ConsoleView view = new ConsoleView();

        GameController controller = new GameController(board, view, repository, "R");
        controller.play();
    }
}