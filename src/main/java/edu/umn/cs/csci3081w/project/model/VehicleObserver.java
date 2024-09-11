package edu.umn.cs.csci3081w.project.model;

public interface VehicleObserver {

  public boolean provideInfo();

  public void setVehicleSubject(VehicleConcreteSubject vehicleConcreteSubject);
}
