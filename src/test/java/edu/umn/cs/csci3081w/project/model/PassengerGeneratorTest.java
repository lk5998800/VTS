package edu.umn.cs.csci3081w.project.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class PassengerGeneratorTest {

  private PassengerGeneratorTestImpl testPassengerGeneratorTestImpl;


  /**
   * Setup operations before each test runs.
   */
  @BeforeEach
  public void setUp() {

    List<Stop> stops = new ArrayList<Stop>();
    Stop testStop1 = new Stop(0, "test stop 1", new Position(-93.243774, 44.972392));
    Stop testStop2 = new Stop(1, "test stop 2", new Position(-93.25631, 44.963211));

    stops.add(testStop1);
    stops.add(testStop2);

    List<Double> probabilities = new ArrayList<Double>();
    probabilities.add(.15);

    testPassengerGeneratorTestImpl = new PassengerGeneratorTestImpl(stops, probabilities);

  }

  /**
   * Test constructor.
   */
  @Test
  public void testConstructor() {

    assertEquals(0, testPassengerGeneratorTestImpl.getStops()
        .get(0).getId());
    assertEquals("test stop 1", testPassengerGeneratorTestImpl.getStops()
        .get(0).getName());
    assertEquals(-93.243774, testPassengerGeneratorTestImpl.getStops()
        .get(0).getPosition().getLongitude());
    assertEquals(44.972392, testPassengerGeneratorTestImpl.getStops()
        .get(0).getPosition().getLatitude());
    assertEquals(.15, testPassengerGeneratorTestImpl.getProbabilities()
        .get(0));

  }

  /**
   * Clean up our variables after each test.
   */
  @AfterEach
  public void cleanUpEach() {
    testPassengerGeneratorTestImpl = null;
  }


}
