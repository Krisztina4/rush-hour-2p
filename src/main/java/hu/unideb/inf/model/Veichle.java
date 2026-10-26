package hu.unideb.inf.model;

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
}