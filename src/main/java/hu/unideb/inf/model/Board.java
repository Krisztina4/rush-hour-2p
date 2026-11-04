package hu.unideb.inf.model;

import java.util.ArrayList;
import java.util.List;

public class Board {
    private final int size;
    private final List<Vehicle> vehicles;

    public Board(int size) {
        this.size = size;
        this.vehicles = new ArrayList<>();
    }

    public int getSize() {
        return size;
    }

    public List<Vehicle> getVehicles() {
        return new ArrayList<>(vehicles);
    }

    public void addVehicle(Vehicle vehicle) {
        List<Position> newVehiclePositions = vehicle.getOccupiedPositions();

        for (Position p : newVehiclePositions) {
            if (p.x() < 0 || p.x() >= size || p.y() < 0 || p.y() >= size) {
                throw new IllegalArgumentException("A jármű kilóg a pályáról.");
            }

            for (Vehicle existingVehicle : vehicles) {
                if (existingVehicle.getOccupiedPositions().contains(p)) {
                    throw new IllegalArgumentException("A jármű ütközik egy már pályán lévő járművel.");
                }
            }
        }
        this.vehicles.add(vehicle);
    }

    public Vehicle getVehicle(String id) {
        for (Vehicle v : vehicles) {
            if (v.getId().equals(id)) {
                return v;
            }
        }
        throw new IllegalArgumentException("Nincs ilyen azonosítójú jármű a táblán.");
    }

    public void moveVehicle(String id, Direction direction, int distance) {
        if (distance <= 0) {
            throw new IllegalArgumentException("A lépéstávolságnak legalább 1-nek kell lennie.");
        }

        Vehicle vehicle = getVehicle(id);
        Position originalPosition = vehicle.getPosition();

        for (int step = 1; step <= distance; step++) {
            vehicle.setPosition(originalPosition);
            vehicle.move(direction, step);

            for (Position p : vehicle.getOccupiedPositions()) {
                if (p.x() < 0 || p.x() >= size || p.y() < 0 || p.y() >= size) {
                    vehicle.setPosition(originalPosition);
                    throw new IllegalArgumentException("Érvénytelen lépés: A jármű útközben kilógna a pályáról.");
                }

                for (Vehicle other : vehicles) {
                    if (other != vehicle && other.getOccupiedPositions().contains(p)) {
                        vehicle.setPosition(originalPosition);
                        throw new IllegalArgumentException("Érvénytelen lépés: A jármű útközben ütközne egy másikkal.");
                    }
                }
            }
        }
    }

    public void moveVehicle(String id, Direction direction) {
        moveVehicle(id, direction, 1);
    }

    public boolean isSolved(String targetVehicleId) {
        Vehicle target = getVehicle(targetVehicleId);

        for (Position p : target.getOccupiedPositions()) {
            if (p.x() == size - 1) {
                return true;
            }
        }
        return false;
    }
}