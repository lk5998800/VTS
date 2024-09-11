package edu.umn.cs.csci3081w.project.webserver;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.spy;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class StartCommandTest {
  private VisualTransitSimulator simulator;
  private WebServerSession sessionMock;
  private JsonObject commandFromClient;
  private StartCommand testCommand;

  /**
   * Setup operations before each test runs.
   */
  @BeforeEach
  public void setUp() {
    simulator = new VisualTransitSimulator("src/main/resources/config.txt", sessionMock);
    sessionMock = spy(WebServerSession.class);
    commandFromClient = new JsonObject();
    testCommand = new StartCommand(simulator);
  }

  /**
   * Test if execute works properly.
   */
  @Test
  public void testExecute() {
    commandFromClient.addProperty("numTimeSteps", 2);
    JsonArray jsonArray = new JsonArray();
    jsonArray.add(5);
    jsonArray.add(5);
    commandFromClient.add("timeBetweenVehicles", jsonArray);
    testCommand.execute(sessionMock, commandFromClient);
    List<Integer> timeBetweenVehicles = new ArrayList<Integer>();
    for (int i = 0; i < jsonArray.size(); i++) {
      timeBetweenVehicles.add(jsonArray.get(i).getAsInt());
    }

    assertEquals(timeBetweenVehicles, simulator.getVehicleStartTimings());
    assertEquals(2, simulator.getNumTimeSteps());
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
  }
}