package edu.umn.cs.csci3081w.project.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;

public class PassengerUnloaderTest {

  private PassengerUnloader testPassengerUnloader;

  /**
   * Tests loadPassenger function.
   */
  @Test
  public void testLoadPassenger() {

    testPassengerUnloader = new PassengerUnloader();

    Stop testStop1 = new Stop(1, "test stop 1", new Position(-93.243774, 44.972392));
    Stop testStop2 = new Stop(2, "test stop 2", new Position(-93.25631, 44.963211));

    Passenger testPassenger1 = new Passenger(1, "testPassenger1");
    Passenger testPassenger2 = new Passenger(2, "testPassenger2");
    Passenger testPassenger3 = new Passenger(2, "testPassenger3");

    List<Passenger> passengerList = new ArrayList<Passenger>();
    passengerList.add(testPassenger1);
    passengerList.add(testPassenger2);
    passengerList.add(testPassenger3);

    assertEquals(1, testPassengerUnloader.unloadPassengers(passengerList, testStop1));
    assertEquals(2, passengerList.size());

    assertEquals(2, testPassengerUnloader.unloadPassengers(passengerList, testStop2));
    assertEquals(0, passengerList.size());

  }

}
