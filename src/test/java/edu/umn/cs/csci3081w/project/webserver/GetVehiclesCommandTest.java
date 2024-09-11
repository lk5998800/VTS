package edu.umn.cs.csci3081w.project.webserver;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import edu.umn.cs.csci3081w.project.model.Bus;
import edu.umn.cs.csci3081w.project.model.Issue;
import edu.umn.cs.csci3081w.project.model.Line;
import edu.umn.cs.csci3081w.project.model.PassengerGenerator;
import edu.umn.cs.csci3081w.project.model.Position;
import edu.umn.cs.csci3081w.project.model.RandomPassengerGenerator;
import edu.umn.cs.csci3081w.project.model.Route;
import edu.umn.cs.csci3081w.project.model.SmallBus;
import edu.umn.cs.csci3081w.project.model.Stop;
import edu.umn.cs.csci3081w.project.model.Vehicle;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

public class GetVehiclesCommandTest {
  private VisualTransitSimulator simulator;
  private WebServerSession sessionSpy;
  private JsonObject command;
  private GetVehiclesCommand testCommand;
  private ArgumentCaptor<JsonObject> messageCaptor;
  private Route testRouteOut;
  private Route testRouteIn;
  private Bus testBus;


  /**
   * Setup operations before each test runs.
   */
  @BeforeEach
  public void setUp() {
    sessionSpy = spy(WebServerSession.class);
    doNothing().when(sessionSpy).sendJson(Mockito.isA(JsonObject.class));
    command = new JsonObject();
    simulator = new VisualTransitSimulator("src/main/resources/config.txt", sessionSpy);
    testCommand = new GetVehiclesCommand(simulator);
    messageCaptor = ArgumentCaptor.forClass(JsonObject.class);
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
   * Test if execute works properly.
   */
  @Test
  public void testExecuteWithoutActiveVehicles() {
    testCommand.execute(sessionSpy, command);
    verify(sessionSpy).sendJson(messageCaptor.capture());
    JsonObject commandToClient = messageCaptor.getValue();
    assertEquals("updateVehicles", commandToClient.get("command").getAsString());
  }

  /**
   * Test if execute works properly.
   */
  @Test
  public void testExecuteWithActiveVehicles() {
    List<Vehicle> vehicles = new ArrayList<>();
    vehicles.add(testBus);
    simulator.setActiveVehicles(vehicles);
    testCommand.execute(sessionSpy, command);
    verify(sessionSpy).sendJson(messageCaptor.capture());
    JsonObject commandToClient = messageCaptor.getValue();
    JsonArray testArray = commandToClient.getAsJsonArray("vehicles");
    JsonObject js = testArray.get(0).getAsJsonObject();
    assertEquals("1", js.get("id").getAsString());
  }

  /**
   * Clean up our variables after each test.
   */
  @AfterEach
  public void cleanUpEach() {
    simulator = null;
    sessionSpy = null;
    command = null;
    testCommand = null;
    messageCaptor = null;
  }
}