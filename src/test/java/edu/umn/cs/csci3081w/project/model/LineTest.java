package edu.umn.cs.csci3081w.project.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.mock;

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

public class LineTest {

  private Line testLine;
  private Stop testStop;

  /**
   * Setup operations before each test runs.
   */
  @BeforeEach
  public void setUp() {

    testStop = new Stop(0, "test stop 1", new Position(-93.243774, 44.972392));

    List<Stop> stopsOut = new ArrayList<Stop>();
    stopsOut.add(testStop);
    List<Double> distancesOut = new ArrayList<Double>();
    distancesOut.add(0.9712663713083954);
    List<Double> probabilitiesOut = new ArrayList<Double>();
    probabilitiesOut.add(.15);
    PassengerGenerator generatorOut = new RandomPassengerGenerator(stopsOut, probabilitiesOut);
    Route testRouteOut = new Route(10, "testRouteOut",
        stopsOut, distancesOut, generatorOut);

    List<Stop> stopsIn = new ArrayList<>();
    stopsIn.add(testStop);
    List<Double> distancesIn = new ArrayList<>();
    distancesIn.add(0.961379387775189);
    List<Double> probabilitiesIn = new ArrayList<>();
    probabilitiesIn.add(.4);
    PassengerGenerator generatorIn = new RandomPassengerGenerator(stopsIn, probabilitiesIn);
    Route testRouteIn = new Route(11, "testRouteIn",
        stopsIn, distancesIn, generatorIn);


    testLine = new Line(0, "testLine", Line.BUS_LINE,
        testRouteOut, testRouteIn,
        new Issue());

  }

  /**
   * Tests constructor.
   */
  @Test
  public void testConstructor() {

    assertEquals(0, testLine.getId());
    assertEquals("testLine", testLine.getName());
    assertEquals(Line.BUS_LINE, testLine.getType());
    assertEquals("testRouteOut", testLine.getOutboundRoute().getName());
    assertEquals("testRouteIn", testLine.getInboundRoute().getName());
    assertEquals(false, testLine.isIssueExist());

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
      testLine.report(testStream);
      outputStream.flush();
      String data = new String(outputStream.toByteArray(), charset);
      testStream.close();
      outputStream.close();
      String strToCompare =
          "====Line Info Start====" + System.lineSeparator()
              + "ID: 0" + System.lineSeparator()
              + "Name: testLine" + System.lineSeparator()
              + "Type: BUS_LINE" + System.lineSeparator()

              // outbound route report
              + "####Route Info Start####" + System.lineSeparator()
              + "ID: 10" + System.lineSeparator()
              + "Name: testRouteOut" + System.lineSeparator()
              + "Num stops: 1" + System.lineSeparator()
              + "****Stops Info Start****" + System.lineSeparator()
              + "++++Next Stop Info Start++++" + System.lineSeparator()
              + "####Stop Info Start####" + System.lineSeparator()
              + "ID: 0" + System.lineSeparator()
              + "Name: test stop 1" + System.lineSeparator()
              + "Position: 44.972392,-93.243774" + System.lineSeparator()
              + "****Passengers Info Start****" + System.lineSeparator()
              + "Num passengers waiting: 0" + System.lineSeparator()
              + "****Passengers Info End****" + System.lineSeparator()
              + "####Stop Info End####" + System.lineSeparator()
              + "++++Next Stop Info End++++" + System.lineSeparator()
              + "****Stops Info End****" + System.lineSeparator()
              + "####Route Info End####" + System.lineSeparator()

              // inbound route report
              + "####Route Info Start####" + System.lineSeparator()
              + "ID: 11" + System.lineSeparator()
              + "Name: testRouteIn" + System.lineSeparator()
              + "Num stops: 1" + System.lineSeparator()
              + "****Stops Info Start****" + System.lineSeparator()
              + "++++Next Stop Info Start++++" + System.lineSeparator()
              + "####Stop Info Start####" + System.lineSeparator()
              + "ID: 0" + System.lineSeparator()
              + "Name: test stop 1" + System.lineSeparator()
              + "Position: 44.972392,-93.243774" + System.lineSeparator()
              + "****Passengers Info Start****" + System.lineSeparator()
              + "Num passengers waiting: 0" + System.lineSeparator()
              + "****Passengers Info End****" + System.lineSeparator()
              + "####Stop Info End####" + System.lineSeparator()
              + "++++Next Stop Info End++++" + System.lineSeparator()
              + "****Stops Info End****" + System.lineSeparator()
              + "####Route Info End####" + System.lineSeparator()


              + "====Line Info End====" + System.lineSeparator();
      assertEquals(data, strToCompare);
    } catch (IOException ioe) {
      fail();
    }
  }


  /**
   * Tests if shallowCopy function works properly.
   */
  @Test
  public void testShallowCopy() {

    Line shallowTestLine = testLine.shallowCopy();

    assertEquals(false, shallowTestLine.getOutboundRoute().isAtEnd());
    assertEquals(false, testLine.getOutboundRoute().isAtEnd());

    shallowTestLine.getOutboundRoute().nextStop();

    assertEquals(true, shallowTestLine.getOutboundRoute().isAtEnd());
    assertEquals(false, testLine.getOutboundRoute().isAtEnd());

  }


  /**
   * Tests if update function works properly.
   */
  @Test
  public void testUpdateWithoutIssue() {

    Passenger passenger = new Passenger(1, "test passenger");

    testStop.addPassengers(passenger);

    assertEquals(false, testLine.isIssueExist());

    assertEquals(0, passenger.getWaitAtStop());

    testLine.update();

    assertEquals(2, passenger.getWaitAtStop());

  }

  /**
   * Tests if update function works properly.
   */
  @Test
  public void testUpdateWithResolvedIssue() {

    testLine.createIssue();
    testLine.update();
    testLine.update();
    testLine.update();
    testLine.update();
    testLine.update();
    testLine.update();
    testLine.update();
    testLine.update();
    testLine.update();
    testLine.update();
    assertEquals(0, testLine.getIssue().getCounter());

    testLine.update();
    assertEquals(0, testLine.getIssue().getCounter());


  }


  /**
   * Tests if update function works properly.
   */
  @Test
  public void testUpdateWithIssue() {

    Passenger passenger = new Passenger(1, "test passenger");

    testLine.createIssue();

    testStop.addPassengers(passenger);

    assertEquals(true, testLine.isIssueExist());

    assertEquals(0, passenger.getWaitAtStop());

    testLine.update();
    testLine.update();
    testLine.update();
    testLine.update();
    testLine.update();
    testLine.update();
    testLine.update();
    testLine.update();
    testLine.update();
    testLine.update();

    assertEquals(20, passenger.getWaitAtStop());
    assertEquals(false, testLine.isIssueExist());

  }


  /**
   * Tests if isIssueExist function works properly.
   */
  @Test
  public void testIsIssueExist() {

    assertEquals(false, testLine.isIssueExist());

    testLine.createIssue();

    assertEquals(true, testLine.isIssueExist());

    testLine.update();
    testLine.update();
    testLine.update();
    testLine.update();
    testLine.update();
    testLine.update();
    testLine.update();
    testLine.update();
    testLine.update();
    testLine.update();

    assertEquals(false, testLine.isIssueExist());


  }

  /**
   * Tests if isIssueExist function works properly.
   */
  @Test
  public void testNoIssueExist() {
    Route routeOutDummy = mock(Route.class);
    Route routeInDummy = mock(Route.class);
    Line testLine1 = new Line(0, "testLine1", Line.BUS_LINE,
        routeOutDummy, routeInDummy,
        null);

    assertEquals(false, testLine1.isIssueExist());


  }

  /**
   * Tests if createIssue function works properly.
   */
  @Test
  public void testCreateIssue() {

    assertEquals(false, testLine.isIssueExist());

    testLine.createIssue();

    assertEquals(true, testLine.isIssueExist());

  }

  /**
   * Clean up our variables after each test.
   */
  @AfterEach
  public void cleanUpEach() {
    testLine = null;
  }

}
