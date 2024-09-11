package edu.umn.cs.csci3081w.project.model;

import java.io.PrintStream;
import java.util.List;

public abstract class VehicleDecorator extends Vehicle {
  protected Vehicle vehicle;

  public VehicleDecorator(Vehicle vehicle) {
    this.vehicle = vehicle;
  }

  public abstract int getId();

  @Override
  public abstract int getCapacity();

  @Override
  public abstract double getSpeed();

  @Override
  public abstract PassengerLoader getPassengerLoader();

  @Override
  public abstract PassengerUnloader getPassengerUnloader();

  @Override
  public abstract List<Passenger> getPassengers();

  @Override
  public abstract String getName();

  @Override
  public abstract void setName(String name);

  @Override
  public abstract Position getPosition();

  @Override
  public abstract void setPosition(Position position);

  @Override
  public abstract boolean isTripComplete();

  @Override
  public abstract int loadPassenger(Passenger newPassenger);

  @Override
  public abstract void move();

  @Override
  public abstract void update();

  @Override
  public abstract Stop getNextStop();

  @Override
  public abstract Line getLine();

  @Override
  public abstract double getDistanceRemaining();

  @Override
  public abstract boolean provideInfo();

  public abstract String getType();

  public abstract int[] getColor();

  @Override
  public abstract void setVehicleSubject(VehicleConcreteSubject vehicleConcreteSubject);

  @Override
  public void report(PrintStream out) {
    return;
  }

  @Override
  public int getCurrentCO2Emission() {
    return 0;
  }
}
