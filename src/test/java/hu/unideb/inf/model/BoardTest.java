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
}