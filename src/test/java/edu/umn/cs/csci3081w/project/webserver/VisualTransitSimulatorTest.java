package edu.umn.cs.csci3081w.project.webserver;

import static org.junit.jupiter.api.Assertions.assertEquals;

import edu.umn.cs.csci3081w.project.model.Bus;
import edu.umn.cs.csci3081w.project.model.Issue;
import edu.umn.cs.csci3081w.project.model.Line;
import edu.umn.cs.csci3081w.project.model.PassengerGenerator;
import edu.umn.cs.csci3081w.project.model.Position;
import edu.umn.cs.csci3081w.project.model.RandomPassengerGenerator;
import edu.umn.cs.csci3081w.project.model.Route;
import edu.umn.cs.csci3081w.project.model.SmallBus;
import edu.umn.cs.csci3081w.project.model.Stop;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class VisualTransitSimulatorTest {
  private VisualTransitSimulator testSimulator;
  private WebServerSession webServerSession;
  private List<Integer> vehicleStartTimings;
  private int numTimeSteps;
  private Bus testBus;
  private Route testRouteIn;
  private Route testRouteOut;

  /**
   * Setup operations before each test runs.
   */
  @BeforeEach
  public void setUp() {
    webServerSession = new WebServerSession();
    testSimulator = new VisualTransitSimulator("src/main/resources/config.txt", webServerSession);
    vehicleStartTimings = new ArrayList<Integer>();
    vehicleStartTimings.add(5);
    vehicleStartTimings.add(5);
    numTimeSteps = 50;
    List<Stop> stopsIn = new ArrayList<Stop>();
    Stop stop1 = new Stop(0, "test stop 1", new Position(-93.243774, 44.972392));
    Stop stop2 = new Stop(1, "test stop 2", new Position(-93.235071, 44.973580));
    stopsIn.add(stop1);
    stopsIn.add(stop2);
    List<Double> distancesIn = new ArrayList<>();
    distancesIn.add(0.843774422231134);
    List<Double> probabilitiesIn = new ArrayList<Double>();
    probabilitiesIn.add(.025);
    probabilitiesIn.add(0.3);
    PassengerGenerator generatorIn = new RandomPassengerGenerator(stopsIn, probabilitiesIn);

    testRouteIn = new Route(0, "testRouteIn",
        stopsIn, distancesIn, generatorIn);

    List<Stop> stopsOut = new ArrayList<Stop>();
    stopsOut.add(stop2);
    stopsOut.add(stop1);
    List<Double> distancesOut = new ArrayList<>();
    distancesOut.add(0.843774422231134);
    List<Double> probabilitiesOut = new ArrayList<Double>();
    probabilitiesOut.add(0.3);
    probabilitiesOut.add(.025);
    PassengerGenerator generatorOut = new RandomPassengerGenerator(stopsOut, probabilitiesOut);

    testRouteOut = new Route(1, "testRouteOut",
        stopsOut, distancesOut, generatorOut);

    testBus = new SmallBus(1, new Line(10000, "testLine", "BUS", testRouteOut, testRouteIn,
        new Issue()), 3, 1.0);
  }

  /**
   * Test state after using constructor.
   */
  @Test
  public void testConstructor() {
    VisualTransitSimulator testSimulator1 =
        new VisualTransitSimulator("src/main/resources/config.txt", webServerSession);
    assertEquals(webServerSession, testSimulator1.getWebServerSession());
    assertEquals(4, testSimulator1.getStorageFacility().getSmallBusesNum());
    assertEquals(2, testSimulator1.getStorageFacility().getLargeBusesNum());
    assertEquals(1, testSimulator1.getStorageFacility().getElectricTrainsNum());
    assertEquals(5, testSimulator1.getStorageFacility().getDieselTrainsNum());
    VisualTransitSimulator testSimulator2 =
        new VisualTransitSimulator("src/main/resources/testConfig.txt", webServerSession);
    assertEquals(Integer.MAX_VALUE, testSimulator2.getStorageFacility().getSmallBusesNum());
    assertEquals(Integer.MAX_VALUE, testSimulator2.getStorageFacility().getLargeBusesNum());
    assertEquals(Integer.MAX_VALUE, testSimulator2.getStorageFacility().getElectricTrainsNum());
    assertEquals(Integer.MAX_VALUE, testSimulator2.getStorageFacility().getDieselTrainsNum());
  }

  /**
   * Test if start works properly.
   */
  @Test
  public void testStart() {
    testSimulator.start(vehicleStartTimings, numTimeSteps);
    assertEquals(vehicleStartTimings, testSimulator.getVehicleStartTimings());
    assertEquals(numTimeSteps, testSimulator.getNumTimeSteps());
    assertEquals(2, testSimulator.getTimeSinceLastVehicle().size());
    assertEquals(0, testSimulator.getSimulationTimeElapsed());
  }

  /**
   * Test if update works properly.
   */
  @Test
  public void testUpdate() {
    testSimulator.start(vehicleStartTimings, numTimeSteps);
    testSimulator.setVehicleFactories(9);
    assertEquals(0, testSimulator.getSimulationTimeElapsed());
    testSimulator.update();
    assertEquals(1, testSimulator.getSimulationTimeElapsed());
    assertEquals(2, testSimulator.getActiveVehicles().size());
    assertEquals("Coffman", testSimulator.getActiveVehicles().get(0).getNextStop().getName());
  }

  /**
   * Test if update works properly.
   */
  @Test
  public void testUpdateWhenOverTime() {
    testSimulator.start(vehicleStartTimings, numTimeSteps);
    testSimulator.setNumTimeSteps(0);
    testSimulator.setVehicleFactories(9);
    testSimulator.update();
    assertEquals(0, testSimulator.getActiveVehicles().size());
  }

  /**
   * Test if update works properly.
   */
  @Test
  public void testUpdateWhenLogging() {
    testSimulator.start(vehicleStartTimings, numTimeSteps);
    testSimulator.setNumTimeSteps(10);
    testSimulator.setVehicleFactories(9);
    testSimulator.setLogging(true);
    testSimulator.update();
    assertEquals(2, testSimulator.getActiveVehicles().size());
  }

  /**
   * Test if update works properly.
   */
  @Test
  public void testUpdateWhenLineIssued() {
    testSimulator.start(vehicleStartTimings, numTimeSteps);
    testSimulator.setVehicleFactories(9);
    List<Line> lines = new ArrayList<>();
    Line issuedLine1 = new Line(10000, "testLine", "BUS_LINE", testRouteOut, testRouteIn,
        new Issue());
    Line issuedLine2 = new Line(10000, "testLine", "TRAIN_LINE", testRouteOut, testRouteIn,
        new Issue());
    issuedLine1.createIssue();
    issuedLine2.createIssue();
    lines.add(issuedLine1);
    lines.add(issuedLine2);
    testSimulator.setLines(lines);
    testSimulator.update();
    assertEquals(0, testSimulator.getActiveVehicles().size());
  }

  /**
   * Test if update works properly.
   */
  @Test
  public void testUpdateWhenLineIssuedAndNoVehicleGenerated() {
    testSimulator.start(vehicleStartTimings, numTimeSteps);
    testSimulator.setVehicleFactories(9);
    List<Line> lines = new ArrayList<>();
    Line issuedLine1 = new Line(10000, "testLine", "BUS_LINE", testRouteOut, testRouteIn,
        new Issue());
    Line issuedLine2 = new Line(10000, "testLine", "TRAIN_LINE", testRouteOut, testRouteIn,
        new Issue());
    issuedLine1.createIssue();
    issuedLine2.createIssue();
    lines.add(issuedLine1);
    lines.add(issuedLine2);
    testSimulator.setLines(lines);
    testSimulator.update();
    testSimulator.update();
    assertEquals(0, testSimulator.getActiveVehicles().size());
  }

  /**
   * Test if update works properly.
   */
  @Test
  public void testUpdateWhenTripIsComplete() {
    testSimulator.start(vehicleStartTimings, numTimeSteps);
    testSimulator.setVehicleFactories(9);
    List<Line> lines = new ArrayList<>();
    Line line1 = new Line(0, "testLine1", "BUS_LINE", testRouteOut, testRouteIn,
        new Issue());
    Line line2 = new Line(1, "testLine2", "TRAIN_LINE", testRouteOut, testRouteIn,
        new Issue());
    lines.add(line1);
    lines.add(line2);
    testSimulator.setLines(lines);
    testSimulator.update();
    testSimulator.update();
    testSimulator.update();
    testSimulator.update();
    testSimulator.update();
    testSimulator.update();
    testSimulator.update();
    testSimulator.update();
    assertEquals(2, testSimulator.getActiveVehicles().size());
  }

  /**
   * Test if update works properly.
   */
  @Test
  public void testUpdateWhenLineUnrelated() {
    testSimulator.start(vehicleStartTimings, numTimeSteps);
    testSimulator.setVehicleFactories(9);
    List<Line> lines = new ArrayList<>();
    Line line1 = new Line(0, "testLine1", "CAT", testRouteOut, testRouteIn,
        new Issue());
    Line line2 = new Line(1, "testLine2", "DOG", testRouteOut, testRouteIn,
        new Issue());
    lines.add(line1);
    lines.add(line2);
    testSimulator.setLines(lines);
    testSimulator.update();
    assertEquals(0, testSimulator.getActiveVehicles().size());
  }



  /**
   * Test if update works properly.
   */
  @Test
  public void testUpdateWhenNoVehicleGenerated() {
    testSimulator.start(vehicleStartTimings, numTimeSteps);
    testSimulator.setVehicleFactories(9);
    testSimulator.update();
    testSimulator.update();
    assertEquals(2, testSimulator.getActiveVehicles().size());
  }


  /**
   * Test if addObserver works properly.
   */
  @Test
  public void testAddObserver() {
    assertEquals(0, testSimulator.getVehicleConcreteSubject().getObservers().size());
    testSimulator.addObserver(testBus);
    assertEquals(testBus, testSimulator.getVehicleConcreteSubject().getObservers().get(0));
  }

  /**
   * Clean up our variables after each test.
   */
  @AfterEach
  public void cleanUpEach() {
    webServerSession = null;
    testSimulator = null;
  }
}