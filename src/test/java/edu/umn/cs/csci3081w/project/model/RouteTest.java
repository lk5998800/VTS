package edu.umn.cs.csci3081w.project.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class RouteTest {

  /**
   * Setup operations before each test runs.
   */

  private Route testRouteOut;
  private Route testRouteIn;
  private Route simpleTestRouteIn;

  /**
   * Setup operations before each test runs.
   */
  @BeforeEach
  public void setUp() {
    PassengerFactory.DETERMINISTIC = true;
    PassengerFactory.DETERMINISTIC_NAMES_COUNT = 0;
    PassengerFactory.DETERMINISTIC_DESTINATION_COUNT = 0;
    RandomPassengerGenerator.DETERMINISTIC = true;

    Stop stop1 = new Stop(0, "test stop 1", new Position(-93.243774, 44.972392));
    Stop stop2 = new Stop(1, "test stop 2", new Position(-93.235071, 44.973580));
    Stop stop3 = new Stop(2, "test stop 3", new Position(-93.226632, 44.975392));
    List<Stop> stopsOut = new ArrayList<Stop>();
    stopsOut.add(stop1);
    stopsOut.add(stop2);
    stopsOut.add(stop3);
    List<Double> distancesOut = new ArrayList<Double>();
    distancesOut.add(0.9712663713083954);
    distancesOut.add(0.961379387775189);
    List<Double> probabilitiesOut = new ArrayList<Double>();
    probabilitiesOut.add(.15);
    probabilitiesOut.add(0.3);
    probabilitiesOut.add(.0);
    PassengerGenerator generatorOut = new RandomPassengerGenerator(stopsOut, probabilitiesOut);
    testRouteOut = new Route(10, "testRouteOut",
        stopsOut, distancesOut, generatorOut);

    List<Stop> stopsIn = new ArrayList<>();
    stopsIn.add(stop3);
    stopsIn.add(stop2);
    stopsIn.add(stop1);
    List<Double> distancesIn = new ArrayList<>();
    distancesIn.add(0.961379387775189);
    distancesIn.add(0.9712663713083954);
    List<Double> probabilitiesIn = new ArrayList<>();
    probabilitiesIn.add(.4);
    probabilitiesIn.add(0.3);
    probabilitiesIn.add(.0);
    PassengerGenerator generatorIn = new RandomPassengerGenerator(stopsIn, probabilitiesIn);
    testRouteIn = new Route(11, "testRouteIn",
        stopsIn, distancesIn, generatorIn);


    List<Stop> simpleStopsIn = new ArrayList<Stop>();
    Stop simpleStop1 = new Stop(0, "test stop", new Position(-93.243774, 44.972392));
    simpleStopsIn.add(simpleStop1);
    List<Double> simpleDistancesIn = new ArrayList<>();
    simpleDistancesIn.add(0.961379387775189);
    List<Double> simpleProbabilitiesIn = new ArrayList<Double>();
    simpleProbabilitiesIn.add(.5);
    PassengerGenerator simpleGeneratorIn = new RandomPassengerGenerator(simpleStopsIn,
        simpleProbabilitiesIn);

    simpleTestRouteIn = new Route(0, "simpleTestRouteIn",
        simpleStopsIn, simpleDistancesIn, simpleGeneratorIn);
  }


  /**
   * Testing state after using constructor.
   */
  @Test
  public void testConstructorNormal() {
    assertEquals(10, testRouteOut.getId());
    assertEquals("testRouteOut", testRouteOut.getName());
    assertEquals(3, testRouteOut.getStops().size());

    assertEquals(11, testRouteIn.getId());
    assertEquals("testRouteIn", testRouteIn.getName());
    assertEquals(3, testRouteIn.getStops().size());
  }


  /**
   * Test for the shallowCopy function.
   * check if the function returns the expected shallow copy of the Route class
   */
  @Test
  public void testShallowCopy() {
    Route shallowRouteOut = testRouteOut.shallowCopy();
    assertEquals(testRouteOut.getId(), shallowRouteOut.getId());
    assertEquals(testRouteOut.getName(), shallowRouteOut.getName());
    assertEquals(testRouteOut.getStops(), shallowRouteOut.getStops());
    assertEquals(testRouteOut.getNextStopIndex(), shallowRouteOut.getNextStopIndex());
    assertEquals(testRouteOut.getId(), shallowRouteOut.getId());

    shallowRouteOut.nextStop();

    assertEquals(1, shallowRouteOut.getNextStopIndex());
    assertEquals(0, testRouteOut.getNextStopIndex());

  }

  /**
   * Tests reporting functionality.
   */
  @Test
  public void testReport() {
    try {
      final Charset charset = StandardCharsets.UTF_8;
      ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
      PrintStream testStream = new PrintStream(outputStream, true, charset.name());
      simpleTestRouteIn.report(testStream);
      outputStream.flush();
      String data = new String(outputStream.toByteArray(), charset);
      testStream.close();
      outputStream.close();
      String strToCompare =
          "####Route Info Start####" + System.lineSeparator()
              + "ID: 0" + System.lineSeparator()
              + "Name: simpleTestRouteIn" + System.lineSeparator()
              + "Num stops: 1" + System.lineSeparator()
              + "****Stops Info Start****" + System.lineSeparator()
              + "++++Next Stop Info Start++++" + System.lineSeparator()
              + "####Stop Info Start####" + System.lineSeparator()
              + "ID: 0" + System.lineSeparator()
              + "Name: test stop" + System.lineSeparator()
              + "Position: 44.972392,-93.243774" + System.lineSeparator()
              + "****Passengers Info Start****" + System.lineSeparator()
              + "Num passengers waiting: 0" + System.lineSeparator()
              + "****Passengers Info End****" + System.lineSeparator()
              + "####Stop Info End####" + System.lineSeparator()
              + "++++Next Stop Info End++++" + System.lineSeparator()
              + "****Stops Info End****" + System.lineSeparator()
              + "####Route Info End####" + System.lineSeparator();
      assertEquals(data, strToCompare);
    } catch (IOException ioe) {
      fail();
    }
  }

  /**
   * Tests reporting functionality.
   */
  @Test
  public void testReportNextStop() {
    try {
      simpleTestRouteIn.nextStop();
      final Charset charset = StandardCharsets.UTF_8;
      ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
      PrintStream testStream = new PrintStream(outputStream, true, charset.name());
      simpleTestRouteIn.report(testStream);
      outputStream.flush();
      String data = outputStream.toString(String.valueOf(charset));
      testStream.close();
      outputStream.close();
      String strToCompare =
          "####Route Info Start####" + System.lineSeparator()
              + "ID: 0" + System.lineSeparator()
              + "Name: simpleTestRouteIn" + System.lineSeparator()
              + "Num stops: 1" + System.lineSeparator()
              + "****Stops Info Start****" + System.lineSeparator()
              + "####Stop Info Start####" + System.lineSeparator()
              + "ID: 0" + System.lineSeparator()
              + "Name: test stop" + System.lineSeparator()
              + "Position: 44.972392,-93.243774" + System.lineSeparator()
              + "****Passengers Info Start****" + System.lineSeparator()
              + "Num passengers waiting: 0" + System.lineSeparator()
              + "****Passengers Info End****" + System.lineSeparator()
              + "####Stop Info End####" + System.lineSeparator()
              + "****Stops Info End****" + System.lineSeparator()
              + "####Route Info End####" + System.lineSeparator();
      assertEquals(strToCompare, data);
    } catch (IOException ioe) {
      fail();
    }
  }


  /**
   * Tests if we properly move through stops to end.
   */
  @Test
  public void testIsAtEnd() {

    // simple case
    assertEquals(false, simpleTestRouteIn.isAtEnd());
    simpleTestRouteIn.nextStop();
    assertEquals(true, simpleTestRouteIn.isAtEnd());

    // more complex case
    testRouteIn.nextStop();
    assertEquals(false, testRouteIn.isAtEnd());
    testRouteIn.nextStop();
    assertEquals(false, testRouteIn.isAtEnd());
    testRouteIn.nextStop();
    assertEquals(true, testRouteIn.isAtEnd());

  }


  /**
   * Tests if we can check our previous stop.
   */
  @Test
  public void testPrevStop() {
    // test outbound
    assertEquals("test stop 1", testRouteOut.prevStop().getName());
    testRouteOut.nextStop();
    assertEquals("test stop 1", testRouteOut.prevStop().getName());
    testRouteOut.nextStop();
    assertEquals("test stop 2", testRouteOut.prevStop().getName());

    // test inbound
    assertEquals("test stop 3", testRouteIn.prevStop().getName());
    testRouteIn.nextStop();
    assertEquals("test stop 3", testRouteIn.prevStop().getName());
    testRouteIn.nextStop();
    assertEquals("test stop 2", testRouteIn.prevStop().getName());
  }

  /**
   * Tests if we can check our previous stop.
   */
  @Test
  public void testPrevStopWhenAtTheEnd() {
    // test outbound
    assertEquals("test stop 1", testRouteOut.prevStop().getName());
    testRouteOut.nextStop();
    assertEquals("test stop 1", testRouteOut.prevStop().getName());
    testRouteOut.nextStop();
    assertEquals("test stop 2", testRouteOut.prevStop().getName());
    testRouteOut.nextStop();
    assertEquals("test stop 3", testRouteOut.prevStop().getName());

    // test inbound
    assertEquals("test stop 3", testRouteIn.prevStop().getName());
    testRouteIn.nextStop();
    assertEquals("test stop 3", testRouteIn.prevStop().getName());
    testRouteIn.nextStop();
    assertEquals("test stop 2", testRouteIn.prevStop().getName());
  }

  /**
   * Tests if we can move forward through stops.
   * This also tests getDestinationStop implicitly
   */
  @Test
  public void testNextStop() {
    // test outbound
    assertEquals("test stop 1", testRouteOut.getNextStop().getName());
    testRouteOut.nextStop();
    assertEquals("test stop 2", testRouteOut.getNextStop().getName());

    // test inbound
    assertEquals("test stop 3", testRouteIn.getNextStop().getName());
    testRouteIn.nextStop();
    assertEquals("test stop 2", testRouteIn.getNextStop().getName());
    testRouteIn.nextStop();
    assertEquals("test stop 1", testRouteIn.getNextStop().getName());
  }


  /**
   * Tests if we can calculate the next stops distance.
   */
  @Test
  public void testGetNextStopDistance() {
    assertEquals(0.0, testRouteOut.getNextStopDistance());
    testRouteOut.nextStop();
    assertEquals(0.9712663713083954, testRouteOut.getNextStopDistance());

  }

  /**
   * Tests new passengers are being generated.
   */
  @Test
  public void testGenerateNewPassengers() {
    assertEquals(2.0, testRouteOut.generateNewPassengers());
    assertEquals(1, testRouteOut.getStops().get(0).getPassengers().size());
  }

  /**
   * Tests update.
   */
  @Test
  public void testUpdate() {
    testRouteIn.update();

    // checking if passengers are being generated
    assertEquals(2, testRouteIn.getStops().get(0).getPassengers().size());
    assertEquals(1, testRouteIn.getStops().get(1).getPassengers().size());

    //checking for passenger value updates
    assertEquals(0, testRouteIn.getStops().get(0).getPassengers().get(0).getTimeOnVehicle());
    assertEquals(1, testRouteIn.getStops().get(0).getPassengers().get(0).getWaitAtStop());

    // making sure that the stops are being iterated
    assertEquals(0, testRouteIn.getStops().get(1).getPassengers().get(0).getTimeOnVehicle());
    assertEquals(1, testRouteIn.getStops().get(1).getPassengers().get(0).getWaitAtStop());
  }

  /**
   * Clean up our variables after each test.
   */
  @AfterEach
  public void cleanUpEach() {
    testRouteOut = null;
    testRouteIn = null;
    simpleTestRouteIn = null;
  }

}
