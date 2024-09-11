package edu.umn.cs.csci3081w.project.model;

import java.io.PrintStream;
import java.util.List;

public class ColorDecorator extends VehicleDecorator {

  /**
   * Color decorator constructor.
   * @param vehicle vehicle
   */
  public ColorDecorator(Vehicle vehicle) {
    super(vehicle);
    if (vehicle instanceof SmallBus) {
      if (vehicle.getLine().isIssueExist()) {
        vehicle.setColor(new int[]{122, 0, 25, 155});
      } else {
        vehicle.setColor(new int[]{122, 0, 25, 255});
      }
    } else if (vehicle instanceof LargeBus) {
      if (vehicle.getLine().isIssueExist()) {
        vehicle.setColor(new int[]{239, 130, 238, 155});
      } else {
        vehicle.setColor(new int[]{239, 130, 238, 255});
      }
    } else if (vehicle instanceof ElectricTrain) {
      if (vehicle.getLine().isIssueExist()) {
        vehicle.setColor(new int[]{60, 179, 113, 155});
      } else {
        vehicle.setColor(new int[]{60, 179, 113, 255});
      }
    } else if (vehicle instanceof DieselTrain) {
      if (vehicle.getLine().isIssueExist()) {
        vehicle.setColor(new int[]{255, 204, 51, 155});
      } else {
        vehicle.setColor(new int[]{255, 204, 51, 255});
      }
    }
  }

  @Override
  public int getId() {
    return vehicle.getId();
  }

  @Override
  public String getType() {
    return vehicle.getType();
  }

  @Override
  public int[] getColor() {
    return vehicle.getColor();
  }

  @Override
  public int getCapacity() {
    return vehicle.getCapacity();
  }

  @Override
  public double getSpeed() {
    return vehicle.getSpeed();
  }

  @Override
  public PassengerLoader getPassengerLoader() {
    return vehicle.getPassengerLoader();
  }

  @Override
  public PassengerUnloader getPassengerUnloader() {
    return vehicle.getPassengerUnloader();
  }

  @Override
  public List<Passenger> getPassengers() {
    return vehicle.getPassengers();
  }

  @Override
  public String getName() {
    return vehicle.getName();
  }

  @Override
  public void setName(String name) {
    vehicle.setName(name);
  }

  @Override
  public Position getPosition() {
    return vehicle.getPosition();
  }

  @Override
  public void setPosition(Position position) {
    vehicle.setPosition(position);
  }

  @Override
  public boolean isTripComplete() {
    return vehicle.isTripComplete();
  }

  @Override
  public int loadPassenger(Passenger newPassenger) {
    return vehicle.loadPassenger(newPassenger);
  }

  @Override
  public void move() {
    vehicle.move();
  }

  @Override
  public void update() {
    vehicle.update();
  }

  @Override
  public Stop getNextStop() {
    return vehicle.getNextStop();
  }

  @Override
  public Line getLine() {
    return vehicle.getLine();
  }

  @Override
  public double getDistanceRemaining() {
    return vehicle.getDistanceRemaining();
  }

  @Override
  public boolean provideInfo() {
    return vehicle.provideInfo();
  }


  @Override
  public void setVehicleSubject(VehicleConcreteSubject vehicleConcreteSubject) {
    vehicle.setVehicleSubject(vehicleConcreteSubject);
  }

  @Override
  public void report(PrintStream out) {
    vehicle.report(out);
  }

  @Override
  public int getCurrentCO2Emission() {
    return vehicle.getCurrentCO2Emission();
  }
}
