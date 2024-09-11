package edu.umn.cs.csci3081w.project.model;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;

import com.google.gson.JsonObject;
import edu.umn.cs.csci3081w.project.webserver.WebServerSession;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import javax.websocket.Session;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

class ColorDecoratorTest {
  private ColorDecorator testColorDecorator;
  private Route testRouteOut;
  private Route testRouteIn;

  /**
   * Set up operations before each test runs.
   */
  @BeforeEach
  void setUp() {
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

    testColorDecorator = new ColorDecorator(new SmallBus(1,
        new Line(10000, "testLine", "BUS", testRouteOut, testRouteIn,
        new Issue()), 3, 1.0));
  }

  /**
   * Testing the constructor.
   */
  @Test
  public void testConstructorNormal() {
    ColorDecorator testColorDecoratorForSmallBus = new ColorDecorator(
        new SmallBus(1, new Line(10000, "testLine", "BUS", testRouteOut, testRouteIn,
        new Issue()), 3, 1.0));
    assertEquals(SmallBus.SMALL_BUS_VEHICLE, testColorDecoratorForSmallBus.getType());
    ColorDecorator testColorDecoratorForLargeBus = new ColorDecorator(
        new LargeBus(1, new Line(10000, "testLine", "BUS", testRouteOut, testRouteIn,
        new Issue()), 3, 1.0));
    assertEquals(LargeBus.LARGE_BUS_VEHICLE, testColorDecoratorForLargeBus.getType());
    ColorDecorator testColorDecoratorForElectricTrain = new ColorDecorator(
        new ElectricTrain(1, new Line(10000, "testLine", "Train", testRouteOut, testRouteIn,
        new Issue()), 3, 1.0));
    assertEquals(ElectricTrain.ELECTRIC_TRAIN_VEHICLE,
        testColorDecoratorForElectricTrain.getType());
    ColorDecorator testColorDecoratorForDieselTrain = new ColorDecorator(
        new DieselTrain(1, new Line(10000, "testLine", "Train", testRouteOut, testRouteIn,
        new Issue()), 3, 1.0));
    assertEquals(DieselTrain.DIESEL_TRAIN_VEHICLE, testColorDecoratorForDieselTrain.getType());
    Line issuedLine = new Line(100, "issuedLine", "BUS", testRouteOut, testRouteIn, new Issue());
    issuedLine.createIssue();

    ColorDecorator smallBusAtIssuedLine = new ColorDecorator(new SmallBus(1, issuedLine, 3, 1.0));
    assertEquals(155, smallBusAtIssuedLine.getColor()[3]);
    ColorDecorator largeAtIssuedLine = new ColorDecorator(new LargeBus(1, issuedLine, 3, 1.0));
    assertEquals(155, largeAtIssuedLine.getColor()[3]);
    ColorDecorator electricTrainAtIssuedLine = new ColorDecorator(
        new ElectricTrain(1, issuedLine, 3, 1.0));
    assertEquals(155, electricTrainAtIssuedLine.getColor()[3]);
    ColorDecorator dieselTrainAtIssuedLine = new ColorDecorator(
        new DieselTrain(1, issuedLine, 3, 1.0));
    assertEquals(155, dieselTrainAtIssuedLine.getColor()[3]);
  }

  /**
   * Test if isTripComplete works properly.
   */
  @Test
  public void testIsTripComplete() {
    assertEquals(false, testColorDecorator.isTripComplete());
    testColorDecorator.move();
    testColorDecorator.move();
    testColorDecorator.move();
    testColorDecorator.move();
    assertEquals(true, testColorDecorator.isTripComplete());
  }

  /**
   * Test if loadPassenger works properly.
   */
  @Test
  public void testLoadPassenger() {

    Passenger testPassenger1 = new Passenger(3, "testPassenger1");
    Passenger testPassenger2 = new Passenger(2, "testPassenger2");
    Passenger testPassenger3 = new Passenger(1, "testPassenger3");
    Passenger testPassenger4 = new Passenger(1, "testPassenger4");

    assertEquals(1, testColorDecorator.loadPassenger(testPassenger1));
    assertEquals(1, testColorDecorator.loadPassenger(testPassenger2));
    assertEquals(1, testColorDecorator.loadPassenger(testPassenger3));
    assertEquals(0, testColorDecorator.loadPassenger(testPassenger4));
  }

  /**
   * Test if move works properly.
   */
  @Test
  public void testMove() {

    assertEquals("test stop 2", testColorDecorator.getNextStop().getName());
    assertEquals(1, testColorDecorator.getNextStop().getId());
    testColorDecorator.move();

    assertEquals("test stop 1", testColorDecorator.getNextStop().getName());
    assertEquals(0, testColorDecorator.getNextStop().getId());

    testColorDecorator.move();
    assertEquals("test stop 1", testColorDecorator.getNextStop().getName());
    assertEquals(0, testColorDecorator.getNextStop().getId());

    testColorDecorator.move();
    assertEquals("test stop 2", testColorDecorator.getNextStop().getName());
    assertEquals(1, testColorDecorator.getNextStop().getId());

    testColorDecorator.move();
    assertEquals(null, testColorDecorator.getNextStop());
  }

  /**
   * Test if provideInfo works properly.
   */
  @Test
  public void testProvideInfo() {
    WebServerSession webServerSession = spy(WebServerSession.class);
    Session sessionDummy = mock(Session.class);
    doNothing().when(webServerSession).sendJson(Mockito.isA(JsonObject.class));
    VehicleConcreteSubject vehicleConcreteSubject = new VehicleConcreteSubject(webServerSession);
    webServerSession.onOpen(sessionDummy);
    testColorDecorator.setVehicleSubject(vehicleConcreteSubject);
    testColorDecorator.update();
    testColorDecorator.provideInfo();
    ArgumentCaptor<JsonObject> messageCaptor = ArgumentCaptor.forClass(JsonObject.class);
    verify(webServerSession).sendJson(messageCaptor.capture());
    JsonObject message = messageCaptor.getValue();
    String expectedCommand = "observedVehicle";
    assertEquals(expectedCommand, message.get("command").getAsString());
    String expectedText = "1" + System.lineSeparator()
        + "-----------------------------" + System.lineSeparator()
        + "* Type: " + testColorDecorator.getType() + System.lineSeparator()
        + "* Position: (-93.235071,44.973580)" + System.lineSeparator()
        + "* Passengers: 0" + System.lineSeparator()
        + "* CO2: 1" + System.lineSeparator();
    assertEquals(expectedText, message.get("text").getAsString());
  }

  /**
   * Test if getColor works properly.
   */
  @Test
  void testGetColor() {

    final int[] result = testColorDecorator.getColor();
    assertArrayEquals(new int[]{122, 0, 25, 255}, result);
  }

  /**
   * Test if update works properly.
   */
  @Test
  public void testUpdate() {

    assertEquals("test stop 2", testColorDecorator.getNextStop().getName());
    assertEquals(1, testColorDecorator.getNextStop().getId());
    testColorDecorator.update();

    assertEquals("test stop 1", testColorDecorator.getNextStop().getName());
    assertEquals(0, testColorDecorator.getNextStop().getId());

    testColorDecorator.update();
    assertEquals("test stop 1", testColorDecorator.getNextStop().getName());
    assertEquals(0, testColorDecorator.getNextStop().getId());

    testColorDecorator.update();
    assertEquals("test stop 2", testColorDecorator.getNextStop().getName());
    assertEquals(1, testColorDecorator.getNextStop().getId());

    testColorDecorator.update();
    assertEquals(null, testColorDecorator.getNextStop());
  }

  /**
   * Test if report works properly.
   */
  @Test
  void testReport() {
    testColorDecorator.move();
    try {
      final Charset charset = StandardCharsets.UTF_8;
      ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
      PrintStream testStream = new PrintStream(outputStream, true, charset.name());
      testColorDecorator.report(testStream);
      outputStream.flush();
      String data = new String(outputStream.toByteArray(), charset);
      testStream.close();
      outputStream.close();
      String strToCompare =
          "####Small Bus Info Start####" + System.lineSeparator()
              + "ID: 1" + System.lineSeparator()
              + "Name: testRouteOut1" + System.lineSeparator()
              + "Speed: 1.0" + System.lineSeparator()
              + "Capacity: 3" + System.lineSeparator()
              + "Position: 44.97358,-93.235071" + System.lineSeparator()
              + "Distance to next stop: 0.843774422231134" + System.lineSeparator()
              + "****Passengers Info Start****" + System.lineSeparator()
              + "Num of passengers: 0" + System.lineSeparator()
              + "****Passengers Info End****" + System.lineSeparator()
              + "####Small Bus Info End####" + System.lineSeparator();
      assertEquals(strToCompare, data);
    } catch (IOException ioe) {
      fail();
    }
  }

  /**
   * Test if getCo2 works properly.
   */
  @Test
  public void testCurrentCO2Emission() {
    assertEquals(1, testColorDecorator.getCurrentCO2Emission());
    Passenger testPassenger1 = new Passenger(3, "testPassenger1");
    testColorDecorator.loadPassenger(testPassenger1);
    assertEquals(2, testColorDecorator.getCurrentCO2Emission());
  }
}