package edu.umn.cs.csci3081w.project.model;

import java.util.ArrayList;
import java.util.List;

public class PassengerUnloader {

  /**
   * Unloads passengers.
   *
   * @param currentStop Current stop
   * @param passengers  list of passengers
   * @return number of passengers unloaded
   */
  public int unloadPassengers(List<Passenger> passengers, Stop currentStop) {
    int passengersUnloaded = 0;
    List<Passenger> copyList = new ArrayList<>();
    for (Passenger p : passengers) {
      if (p.getDestination() == currentStop.getId()) {
        passengersUnloaded++;
      } else {
        copyList.add(p);
      }
    }
    passengers.clear();
    passengers.addAll(copyList);
    return passengersUnloaded;
  }
}
