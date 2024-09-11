package edu.umn.cs.csci3081w.project.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;

import com.google.gson.JsonObject;
import edu.umn.cs.csci3081w.project.webserver.WebServerSession;
import java.util.ArrayList;
import java.util.List;
import javax.websocket.Session;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

public class VehicleTest {

  private Vehicle testVehicle;
  private Route testRouteIn;
  private Route testRouteOut;


  /**
   * Setup operations before each test runs.
   */
  @BeforeEach
  public void setUp() {
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

    testVehicle = new VehicleTestImpl(1, new Line(10000, "testLine",
        "VEHICLE_LINE", testRouteOut, testRouteIn,
        new Issue()), 3, 1.0, new PassengerLoader(), new PassengerUnloader());
  }

  /**
   * Tests constructor.
   */
  @Test
  public void testConstructor() {
    assertEquals(1, testVehicle.getId());
    assertEquals("testRouteOut1", testVehicle.getName());
    assertEquals(3, testVehicle.getCapacity());
    assertEquals(1, testVehicle.getSpeed());
    assertEquals(testRouteOut, testVehicle.getLine().getOutboundRoute());
    assertEquals(testRouteIn, testVehicle.getLine().getInboundRoute());
  }

  /**
   * Tests if testIsTripComplete function works properly.
   */
  @Test
  public void testIsTripComplete() {
    assertEquals(false, testVehicle.isTripComplete());
    testVehicle.move();
    testVehicle.move();
    testVehicle.move();
    testVehicle.move();
    assertEquals(true, testVehicle.isTripComplete());

  }


  /**
   * Tests if loadPassenger function works properly.
   */
  @Test
  public void testLoadPassenger() {

    Passenger testPassenger1 = new Passenger(3, "testPassenger1");
    Passenger testPassenger2 = new Passenger(2, "testPassenger2");
    Passenger testPassenger3 = new Passenger(1, "testPassenger3");
    Passenger testPassenger4 = new Passenger(1, "testPassenger4");

    assertEquals(1, testVehicle.loadPassenger(testPassenger1));
    assertEquals(1, testVehicle.loadPassenger(testPassenger2));
    assertEquals(1, testVehicle.loadPassenger(testPassenger3));
    assertEquals(0, testVehicle.loadPassenger(testPassenger4));
  }


  /**
   * Tests if move function works properly.
   */
  @Test
  public void testMove() {

    assertEquals("test stop 2", testVehicle.getNextStop().getName());
    assertEquals(1, testVehicle.getNextStop().getId());
    testVehicle.move();

    assertEquals("test stop 1", testVehicle.getNextStop().getName());
    assertEquals(0, testVehicle.getNextStop().getId());

    testVehicle.move();
    assertEquals("test stop 1", testVehicle.getNextStop().getName());
    assertEquals(0, testVehicle.getNextStop().getId());

    testVehicle.move();
    assertEquals("test stop 2", testVehicle.getNextStop().getName());
    assertEquals(1, testVehicle.getNextStop().getId());

    testVehicle.move();
    assertEquals(null, testVehicle.getNextStop());

  }

  /**
   * Tests if move function works properly.
   */
  @Test
  public void testMoveWhenTripIsComplete() {

    assertEquals("test stop 2", testVehicle.getNextStop().getName());
    assertEquals(1, testVehicle.getNextStop().getId());
    testVehicle.move();

    assertEquals("test stop 1", testVehicle.getNextStop().getName());
    assertEquals(0, testVehicle.getNextStop().getId());

    testVehicle.move();
    assertEquals("test stop 1", testVehicle.getNextStop().getName());
    assertEquals(0, testVehicle.getNextStop().getId());

    testVehicle.move();
    assertEquals("test stop 2", testVehicle.getNextStop().getName());
    assertEquals(1, testVehicle.getNextStop().getId());

    testVehicle.move();
    assertEquals(null, testVehicle.getNextStop());

    testVehicle.move();
    assertEquals(null, testVehicle.getNextStop());

  }

  /**
   * Tests if move function works properly.
   */
  @Test
  public void testMoveWhenDistanceGreaterThanZero() {
    testVehicle.setDistanceRemaining(10.0);
    assertEquals("test stop 2", testVehicle.getNextStop().getName());
    assertEquals(1, testVehicle.getNextStop().getId());
    testVehicle.move();

    assertEquals("test stop 2", testVehicle.getNextStop().getName());
    assertEquals(1, testVehicle.getNextStop().getId());
  }

  /**
   * Tests if update function works properly.
   */
  @Test
  public void testUpdate() {

    assertEquals("test stop 2", testVehicle.getNextStop().getName());
    assertEquals(1, testVehicle.getNextStop().getId());
    testVehicle.update();

    assertEquals("test stop 1", testVehicle.getNextStop().getName());
    assertEquals(0, testVehicle.getNextStop().getId());

    testVehicle.update();
    assertEquals("test stop 1", testVehicle.getNextStop().getName());
    assertEquals(0, testVehicle.getNextStop().getId());

    testVehicle.update();
    assertEquals("test stop 2", testVehicle.getNextStop().getName());
    assertEquals(1, testVehicle.getNextStop().getId());

    testVehicle.update();
    assertEquals(null, testVehicle.getNextStop());

  }

  /**
   * test if update works properly.
   */
  @Test
  public void testUpdateWhenPassengerOn() {

    Passenger testPassenger1 = new Passenger(1, "testPassenger4");
    testVehicle.loadPassenger(testPassenger1);
    testVehicle.update();
    assertEquals(testPassenger1.getTimeOnVehicle(), 2);
  }

  /**
   * Test to see if observer got attached.
   */
  @Test
  public void testProvideInfo() {
    WebServerSession webServerSession = spy(WebServerSession.class);
    Session sessionDummy = mock(Session.class);
    doNothing().when(webServerSession).sendJson(Mockito.isA(JsonObject.class));
    VehicleConcreteSubject vehicleConcreteSubject = new VehicleConcreteSubject(webServerSession);
    webServerSession.onOpen(sessionDummy);
    testVehicle.setVehicleSubject(vehicleConcreteSubject);
    testVehicle.update();
    testVehicle.provideInfo();
    ArgumentCaptor<JsonObject> messageCaptor = ArgumentCaptor.forClass(JsonObject.class);
    verify(webServerSession).sendJson(messageCaptor.capture());
    JsonObject message = messageCaptor.getValue();
    String expectedCommand = "observedVehicle";
    assertEquals(expectedCommand, message.get("command").getAsString());
    String expectedText = "1" + System.lineSeparator()
        + "-----------------------------" + System.lineSeparator()
        + "* Type: " + System.lineSeparator()
        + "* Position: (-93.235071,44.973580)" + System.lineSeparator()
        + "* Passengers: 0" + System.lineSeparator()
        + "* CO2: 0" + System.lineSeparator();
    assertEquals(expectedText, message.get("text").getAsString());
  }

  /**
   * test if provideInfo works properly.
   */
  @Test
  public void testProvideInfoWhenTripIsComplete() {
    WebServerSession webServerSession = spy(WebServerSession.class);
    Session sessionDummy = mock(Session.class);
    doNothing().when(webServerSession).sendJson(Mockito.isA(JsonObject.class));
    VehicleConcreteSubject vehicleConcreteSubject = new VehicleConcreteSubject(webServerSession);
    webServerSession.onOpen(sessionDummy);
    testVehicle.setVehicleSubject(vehicleConcreteSubject);
    testVehicle.update();
    testVehicle.update();
    testVehicle.update();
    testVehicle.update();
    testVehicle.provideInfo();
    ArgumentCaptor<JsonObject> messageCaptor = ArgumentCaptor.forClass(JsonObject.class);
    verify(webServerSession).sendJson(messageCaptor.capture());
    JsonObject message = messageCaptor.getValue();
    String expectedCommand = "observedVehicle";
    assertEquals(expectedCommand, message.get("command").getAsString());
    String expectedText = "";
    assertEquals(expectedText, message.get("text").getAsString());
  }

  /**
   * test if provideInfo works properly.
   */
  @Test
  public void testProvideInfoWithHistory() {
    WebServerSession webServerSession = spy(WebServerSession.class);
    Session sessionDummy = mock(Session.class);
    doNothing().when(webServerSession).sendJson(Mockito.isA(JsonObject.class));
    VehicleConcreteSubject vehicleConcreteSubject = new VehicleConcreteSubject(webServerSession);
    webServerSession.onOpen(sessionDummy);
    List<Integer> testList = new ArrayList<>();
    testList.add(0);
    testList.add(0);
    testList.add(0);
    testList.add(0);
    testList.add(0);
    testVehicle.setVehicleSubject(vehicleConcreteSubject);
    testVehicle.update();
    testVehicle.setCarbonEmissionHistory(testList);
    testVehicle.provideInfo();
    ArgumentCaptor<JsonObject> messageCaptor = ArgumentCaptor.forClass(JsonObject.class);
    verify(webServerSession).sendJson(messageCaptor.capture());
    JsonObject message = messageCaptor.getValue();
    String expectedCommand = "observedVehicle";
    assertEquals(expectedCommand, message.get("command").getAsString());
    String expectedText = "1" + System.lineSeparator()
        + "-----------------------------" + System.lineSeparator()
        + "* Type: " + System.lineSeparator()
        + "* Position: (-93.235071,44.973580)" + System.lineSeparator()
        + "* Passengers: 0" + System.lineSeparator()
        + "* CO2: 0, 0, 0, 0, 0" + System.lineSeparator();
    assertEquals(expectedText, message.get("text").getAsString());
  }

  /**
   * test if getSpeed works properly.
   */
  @Test
  public void testSetSpeed() {
    testVehicle.setSpeed(2.0);
    assertEquals(2.0, testVehicle.getSpeed());
  }

  /**
   * test if getType works properly.
   */
  @Test
  public void getType() {
    assertEquals("", testVehicle.getType());
  }


  /**
   * Clean up our variables after each test.
   */
  @AfterEach
  public void cleanUpEach() {
    testVehicle = null;
  }

}
