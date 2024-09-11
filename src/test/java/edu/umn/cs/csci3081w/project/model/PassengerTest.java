package edu.umn.cs.csci3081w.project.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class PassengerTest {

  /**
   * Setup operations before each test runs.
   */

  private Passenger passenger;

  @BeforeEach
  public void setUp() {
    passenger = new Passenger(5, "testPassenger");
  }


  /**
   * Testing state after using constructor.
   */
  @Test
  public void testConstructorNormal() {
    assertEquals(false, passenger.isOnVehicle());
    assertEquals(5, passenger.getDestination());
  }

  /**
   * Testing state after using constructor.
   */
  @Test
  public void testPassUpdate() {
    passenger.pasUpdate();
    assertEquals(passenger.getWaitAtStop(), 1);
    passenger.setOnVehicle();
    passenger.pasUpdate();
    assertEquals(passenger.getWaitAtStop(), 1);
    assertEquals(passenger.getTimeOnVehicle(), 2);
  }

  /**
   * Tests if isOnVehicle return the right value.
   */
  @Test
  public void testIsOnVehicle() {
    assertEquals(false, passenger.isOnVehicle());
    passenger.setOnVehicle();
    assertEquals(true, passenger.isOnVehicle());

  }

  /**
   * Tests if setOnVehicle function works properly.
   */
  @Test
  public void testSetOnVehicle() {
    assertEquals(0, passenger.getTimeOnVehicle());
    passenger.setOnVehicle();
    assertEquals(1, passenger.getTimeOnVehicle());

  }

  /**
   * Tests reporting functionality, as well as the time on vehicle/time at stop updates.
   */
  @Test
  public void testReport() {

    passenger.pasUpdate();
    passenger.pasUpdate();
    passenger.setOnVehicle();
    passenger.pasUpdate();

    try {
      final Charset charset = StandardCharsets.UTF_8;
      ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
      PrintStream testStream = new PrintStream(outputStream, true, charset.name());
      passenger.report(testStream);
      outputStream.flush();
      String data = new String(outputStream.toByteArray(), charset);
      testStream.close();
      outputStream.close();
      String strToCompare =
          "####Passenger Info Start####" + System.lineSeparator()
              + "Name: testPassenger" + System.lineSeparator()
              + "Destination: 5" + System.lineSeparator()
              + "Wait at stop: 2" + System.lineSeparator()
              + "Time on vehicle: 2" + System.lineSeparator()
              + "####Passenger Info End####" + System.lineSeparator();
      assertEquals(data, strToCompare);
    } catch (IOException ioe) {
      fail();
    }
  }


  /**
   * Clean up our variables after each test.
   */
  @AfterEach
  public void cleanUpEach() {
    passenger = null;
  }

}
