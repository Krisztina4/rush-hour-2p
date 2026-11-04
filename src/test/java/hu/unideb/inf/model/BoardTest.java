package hu.unideb.inf.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class BoardTest {

    @Test
    void testAddValidVehicle() {
        Board board = new Board(6);
        Vehicle vehicle = new Vehicle("A", 2, Orientation.HORIZONTAL, new Position(0, 0));
        board.addVehicle(vehicle);

        assertEquals(1, board.getVehicles().size());
        assertEquals("A", board.getVehicles().getFirst().getId());
    }

    @Test
    void testAddVehicleOutOfBoundsThrowsException() {
        Board board = new Board(6);
        Vehicle vehicle = new Vehicle("A", 3, Orientation.HORIZONTAL, new Position(4, 0));

        assertThrows(IllegalArgumentException.class, () -> {
            board.addVehicle(vehicle);
        });
    }

    @Test
    void testAddOverlappingVehicleThrowsException() {
        Board board = new Board(6);
        Vehicle vehicle1 = new Vehicle("A", 2, Orientation.HORIZONTAL, new Position(0, 0));
        Vehicle vehicle2 = new Vehicle("B", 2, Orientation.VERTICAL, new Position(1, 0));

        board.addVehicle(vehicle1);

        assertThrows(IllegalArgumentException.class, () -> {
            board.addVehicle(vehicle2);
        });
    }

    @Test
    void testMoveVehicleSuccessfully() {
        Board board = new Board(6);
        Vehicle vehicle = new Vehicle("A", 2, Orientation.HORIZONTAL, new Position(0, 0));
        board.addVehicle(vehicle);

        board.moveVehicle("A", Direction.RIGHT);

        assertEquals(new Position(1, 0), board.getVehicle("A").getPosition());
    }

    @Test
    void testMoveVehicleOutOfBoundsRollsBack() {
        Board board = new Board(6);
        Vehicle vehicle = new Vehicle("A", 2, Orientation.HORIZONTAL, new Position(4, 0));
        board.addVehicle(vehicle);

        assertThrows(IllegalArgumentException.class, () -> {
            board.moveVehicle("A", Direction.RIGHT);
        });

        assertEquals(new Position(4, 0), board.getVehicle("A").getPosition());
    }

    @Test
    void testMoveVehicleCollisionRollsBack() {
        Board board = new Board(6);
        Vehicle vehicle1 = new Vehicle("A", 2, Orientation.HORIZONTAL, new Position(0, 0));
        Vehicle vehicle2 = new Vehicle("B", 2, Orientation.VERTICAL, new Position(2, 0));
        board.addVehicle(vehicle1);
        board.addVehicle(vehicle2);

        assertThrows(IllegalArgumentException.class, () -> {
            board.moveVehicle("A", Direction.RIGHT);
        });

        assertEquals(new Position(0, 0), board.getVehicle("A").getPosition());
    }

    @Test
    void testIsSolvedReturnsTrueWhenTargetAtEdge() {
        Board board = new Board(6);
        Vehicle target = new Vehicle("RED", 2, Orientation.HORIZONTAL, new Position(4, 2));
        board.addVehicle(target);

        assertTrue(board.isSolved("RED"));
    }

    @Test
    void testIsSolvedReturnsFalseWhenTargetNotAtEdge() {
        Board board = new Board(6);
        Vehicle target = new Vehicle("RED", 2, Orientation.HORIZONTAL, new Position(3, 2));
        board.addVehicle(target);

        assertFalse(board.isSolved("RED"));
    }

    @Test
    void testMoveVehicleMultipleStepsSuccessfully() {
        Board board = new Board(6);
        Vehicle vehicle = new Vehicle("A", 2, Orientation.HORIZONTAL, new Position(0, 0));
        board.addVehicle(vehicle);

        board.moveVehicle("A", Direction.RIGHT, 3);

        assertEquals(new Position(3, 0), board.getVehicle("A").getPosition());
    }

    @Test
    void testMoveVehicleJumpingOverVehicleThrowsExceptionAndRollsBack() {
        Board board = new Board(6);
        Vehicle vehicle1 = new Vehicle("A", 2, Orientation.HORIZONTAL, new Position(0, 0));
        Vehicle vehicle2 = new Vehicle("B", 2, Orientation.VERTICAL, new Position(2, 0));
        board.addVehicle(vehicle1);
        board.addVehicle(vehicle2);

        // Az "A" jármű próbál 3-at lépni jobbra, de a (2,0) pozícióban ott áll a "B"
        assertThrows(IllegalArgumentException.class, () -> {
            board.moveVehicle("A", Direction.RIGHT, 3);
        });

        // Ellenőrizzük a rollbacket: az "A" jármű az eredeti (0,0) pozícióban marad
        assertEquals(new Position(0, 0), board.getVehicle("A").getPosition());
    }
}