package hu.unideb.inf.model;
import java.util.ArrayList;
import java.util.List;

public class Vehicle {
    private final String id;
    private final int length;
    private final Orientation orientation;
    private Position position;

    public Vehicle(String id, int length, Orientation orientation, Position position) {
        this.id = id;
        this.length = length;
        this.orientation = orientation;
        this.position = position;
    }

    public String getId() {
        return id;
    }

    public int getLength() {
        return length;
    }

    public Orientation getOrientation() {
        return orientation;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public void move(Direction direction) {
        if (this.orientation == Orientation.HORIZONTAL) {
            if (direction == Direction.UP || direction == Direction.DOWN) {
                throw new IllegalArgumentException("Vízszintes jármű nem mozoghat fel vagy le.");
            }
            if (direction == Direction.LEFT) {
                this.position = new Position(this.position.x() - 1, this.position.y());
            } else if (direction == Direction.RIGHT) {
                this.position = new Position(this.position.x() + 1, this.position.y());
            }
        } else if (this.orientation == Orientation.VERTICAL) {
            if (direction == Direction.LEFT || direction == Direction.RIGHT) {
                throw new IllegalArgumentException("Függőleges jármű nem mozoghat balra vagy jobbra.");
            }
            if (direction == Direction.UP) {
                this.position = new Position(this.position.x(), this.position.y() - 1);
            } else if (direction == Direction.DOWN) {
                this.position = new Position(this.position.x(), this.position.y() + 1);
            }
        }
    }

    public List<Position> getOccupiedPositions() {
        List<Position> occupied = new ArrayList<>();
        for (int i = 0; i < length; i++) {
            if (orientation == Orientation.HORIZONTAL) {
                occupied.add(new Position(this.position.x() + i, this.position.y()));
            } else {
                occupied.add(new Position(this.position.x(), this.position.y() + i));
            }
        }
        return occupied;
    }
}