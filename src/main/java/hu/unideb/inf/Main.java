package hu.unideb.inf;

import hu.unideb.inf.controller.GameController;
import hu.unideb.inf.model.Board;
import hu.unideb.inf.model.Orientation;
import hu.unideb.inf.model.Position;
import hu.unideb.inf.model.Vehicle;
import hu.unideb.inf.persistence.GameResultRepository;
import hu.unideb.inf.view.ConsoleView;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Main {

    public static void main(String[] args) {
        Properties properties = loadProperties();
        String dbUrl = properties.getProperty("db.url");
        String dbUser = properties.getProperty("db.user");
        String dbPassword = properties.getProperty("db.password");

        GameResultRepository repository = new GameResultRepository(dbUrl, dbUser, dbPassword);

        Board board = new Board(8);

        // 1. játékos céljárműve (R1) a (0,2) pozícióban
        board.addVehicle(new Vehicle("R1", 2, Orientation.HORIZONTAL, new Position(0, 2)));

        // 2. játékos céljárműve (R2) a (0,5) pozícióban
        board.addVehicle(new Vehicle("R2", 2, Orientation.HORIZONTAL, new Position(0, 5)));

        // Akadályozó járművek a 8x8-as pályán
        board.addVehicle(new Vehicle("A", 3, Orientation.VERTICAL, new Position(2, 1)));
        board.addVehicle(new Vehicle("B", 2, Orientation.HORIZONTAL, new Position(3, 0)));
        board.addVehicle(new Vehicle("C", 3, Orientation.VERTICAL, new Position(4, 3)));
        board.addVehicle(new Vehicle("D", 2, Orientation.VERTICAL, new Position(6, 4)));

        ConsoleView view = new ConsoleView();

        GameController controller = new GameController(board, view, repository, "R1", "R2");
        controller.play();
    }

    private static Properties loadProperties() {
        Properties properties = new Properties();
        try (InputStream input = Main.class.getClassLoader().getResourceAsStream("application.properties")) {
            if (input == null) {
                throw new RuntimeException("Nem található az application.properties konfigurációs fájl.");
            }
            properties.load(input);
        } catch (IOException e) {
            throw new RuntimeException("Hiba a konfigurációs fájl beolvasásakor: " + e.getMessage(), e);
        }
        return properties;
    }
}