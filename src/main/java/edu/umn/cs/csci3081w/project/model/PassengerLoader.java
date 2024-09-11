package edu.umn.cs.csci3081w.project.model;

import java.util.List;

public class PassengerLoader {

  /**
   * Loads a passenger.
   * This function returns the number of passengers added to the vehicle.
   * Currently this is either one or zero: either passenger was added or they
   * weren't. This allows us to change the Passenger Loader without having to change
   * the Vehicle.
   *
   * @param maxPass      max number of passengers in the vehicle
   * @param newPassenger new passenger to be laoded
   * @param passengers   list of passengers
   * @return the number of passengers added
   */
  public int loadPassenger(Passenger newPassenger, int maxPass, List<Passenger> passengers) {
    // Normally would use a boolean, but for extensibility,
    // using count of those added_passenger
    // Currently, only one could be added, so possible values are 0 or 1.
    int addedPassengers = 0;
    if (passengers.size() < maxPass) {
      passengers.add(newPassenger);
      newPassenger.setOnVehicle();
      addedPassengers = 1;
    }
    return addedPassengers;
  }
}
