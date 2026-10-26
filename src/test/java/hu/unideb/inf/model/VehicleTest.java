package hu.unideb.inf.model;

import java.util.List;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class VehicleTest {

    @Test
    void testHorizontalVehicleMovesRight() {
        Vehicle vehicle = new Vehicle("A", 2, Orientation.HORIZONTAL, new Position(2, 2));
        vehicle.move(Direction.RIGHT);

        Position expectedPosition = new Position(3, 2);
        assertEquals(expectedPosition, vehicle.getPosition());
    }

    @Test
    void testHorizontalVehicleInvalidMoveThrowsException() {
        Vehicle vehicle = new Vehicle("A", 2, Orientation.HORIZONTAL, new Position(2, 2));

        assertThrows(IllegalArgumentException.class, () -> {
            vehicle.move(Direction.UP);
        });
    }

    @Test
    void testGetOccupiedPositionsHorizontal() {
        Vehicle vehicle = new Vehicle("A", 3, Orientation.HORIZONTAL, new Position(1, 1));
        List<Position> expected = List.of(
                new Position(1, 1),
                new Position(2, 1),
                new Position(3, 1)
        );
        assertEquals(expected, vehicle.getOccupiedPositions());
    }

    @Test
    void testGetOccupiedPositionsVertical() {
        Vehicle vehicle = new Vehicle("B", 2, Orientation.VERTICAL, new Position(4, 2));
        List<Position> expected = List.of(
                new Position(4, 2),
                new Position(4, 3)
        );
        assertEquals(expected, vehicle.getOccupiedPositions());
    }

}