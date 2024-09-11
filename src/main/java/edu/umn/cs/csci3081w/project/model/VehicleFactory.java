package edu.umn.cs.csci3081w.project.model;

public interface VehicleFactory {
  public Vehicle generateVehicle(Line line);

  public void returnVehicle(Vehicle vehicle);
}
