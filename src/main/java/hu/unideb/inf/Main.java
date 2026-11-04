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
        Board board = new Board(8);

        board.addVehicle(new Vehicle("X", 2, Orientation.HORIZONTAL, new Position(0, 2)));
        board.addVehicle(new Vehicle("Y", 2, Orientation.HORIZONTAL, new Position(0, 5)));

        // Akadályozó járművek a 8x8-as pályán
        board.addVehicle(new Vehicle("A", 3, Orientation.VERTICAL, new Position(2, 1)));
        board.addVehicle(new Vehicle("B", 2, Orientation.HORIZONTAL, new Position(3, 0)));
        board.addVehicle(new Vehicle("C", 3, Orientation.VERTICAL, new Position(4, 3)));
        board.addVehicle(new Vehicle("D", 2, Orientation.VERTICAL, new Position(6, 4)));

        ConsoleView view = new ConsoleView();

        // Átadjuk a 8x8-as táblát és a két céljármű azonosítóját ("R1", "R2")
        GameController controller = new GameController(board, view, repository, "X", "Y");
        controller.play();
    }
}