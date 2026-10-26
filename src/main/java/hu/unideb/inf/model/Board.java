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
}