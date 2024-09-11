package edu.umn.cs.csci3081w.project.webserver;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

import com.google.gson.JsonObject;
import edu.umn.cs.csci3081w.project.model.Bus;
import edu.umn.cs.csci3081w.project.model.Issue;
import edu.umn.cs.csci3081w.project.model.LargeBus;
import edu.umn.cs.csci3081w.project.model.Line;
import edu.umn.cs.csci3081w.project.model.PassengerGenerator;
import edu.umn.cs.csci3081w.project.model.Position;
import edu.umn.cs.csci3081w.project.model.RandomPassengerGenerator;
import edu.umn.cs.csci3081w.project.model.Route;
import edu.umn.cs.csci3081w.project.model.Stop;
import edu.umn.cs.csci3081w.project.model.Vehicle;
import edu.umn.cs.csci3081w.project.model.VehicleObserver;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class RegisterVehicleCommandTest {
  private VisualTransitSimulator simulator;
  private WebServerSession sessionMock;
  private JsonObject commandFromClient;
  private RegisterVehicleCommand testCommand;

  private Bus testBus;
  private Route testRouteIn;
  private Route testRouteOut;

  /**
   * Setup operations before each test runs.
   */
  @BeforeEach
  public void setUp() {
    simulator = new VisualTransitSimulator("src/main/resources/config.txt", sessionMock);
    sessionMock = mock(WebServerSession.class);
    commandFromClient = new JsonObject();
    testCommand = new RegisterVehicleCommand(simulator);

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

    testBus = new LargeBus(1, new Line(10000, "testLine", "BUS", testRouteOut, testRouteIn,
        new Issue()), 3, 1.0);
  }

  /**
   * Test if execute works properly.
   */
  @Test
  public void testExecute() {
    List<Vehicle> vehicles = new ArrayList<Vehicle>();
    vehicles.add(testBus);
    simulator.setActiveVehicles(vehicles);
    commandFromClient.addProperty("id", 1);
    testCommand.execute(sessionMock, commandFromClient);
    List<VehicleObserver> observers = simulator.getVehicleConcreteSubject().getObservers();
    VehicleObserver observer = observers.get(0);
    assertEquals(testBus, observer);
  }

  /**
   * Clean up our variables after each test.
   */
  @AfterEach
  public void cleanUpEach() {
    simulator = null;
    sessionMock = null;
    commandFromClient = null;
    testCommand = null;
    testBus = null;
    testRouteIn = null;
    testRouteOut = null;
  }
}