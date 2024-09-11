package edu.umn.cs.csci3081w.project.webserver;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;

import com.google.gson.JsonObject;
import edu.umn.cs.csci3081w.project.model.PassengerFactory;
import edu.umn.cs.csci3081w.project.model.RandomPassengerGenerator;
import java.io.ByteArrayOutputStream;
import javax.websocket.Session;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

public class WebServerSessionTest {
  private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
  private WebServerSession webServerSessionSpy;
  private Session sessionDummy;
  private JsonObject commandFromClient;
  private ArgumentCaptor<JsonObject> messageCaptor;

  /**
   * Setup deterministic operations before each test runs.
   */
  @BeforeEach
  public void setUp() {
    PassengerFactory.DETERMINISTIC = true;
    PassengerFactory.DETERMINISTIC_NAMES_COUNT = 0;
    PassengerFactory.DETERMINISTIC_DESTINATION_COUNT = 0;
    RandomPassengerGenerator.DETERMINISTIC = true;
    // set up test doubles
    webServerSessionSpy = spy(WebServerSession.class);
    doNothing().when(webServerSessionSpy).sendJson(Mockito.isA(JsonObject.class));
    sessionDummy = mock(Session.class);
    webServerSessionSpy.onOpen(sessionDummy);
    commandFromClient = new JsonObject();
    messageCaptor = ArgumentCaptor.forClass(JsonObject.class);
  }

  /**
   * Test command for initializing the simulation.
   */
  @Test
  public void testSimulationInitialization() {
    commandFromClient.addProperty("command", "initLines");
    webServerSessionSpy.onMessage(commandFromClient.toString());
    verify(webServerSessionSpy).sendJson(messageCaptor.capture());
    JsonObject commandToClient = messageCaptor.getValue();
    assertEquals("2", commandToClient.get("numLines").getAsString());
  }

  /**
   * Test command for retrieving routes information form the simulation.
   */
  @Test
  public void testGetRoutesInfo() {
    commandFromClient.addProperty("command", "getRoutes");
    webServerSessionSpy.onMessage(commandFromClient.toString());
    verify(webServerSessionSpy).sendJson(messageCaptor.capture());
    JsonObject commandToClient = messageCaptor.getValue();
    assertEquals("updateRoutes", commandToClient.get("command").getAsString());
  }


  /**
   * Test command for retrieving vehicles information form the simulation.
   */
  @Test
  public void testGetVehiclesInfo() {
    commandFromClient.addProperty("command", "getVehicles");
    webServerSessionSpy.onMessage(commandFromClient.toString());
    verify(webServerSessionSpy).sendJson(messageCaptor.capture());
    JsonObject commandToClient = messageCaptor.getValue();
    assertEquals("updateVehicles", commandToClient.get("command").getAsString());
  }

  @Test
  public void testOnMessageWithInvalidValue() {
    JsonObject testMessage = new JsonObject();
    testMessage.addProperty("command", "");
    webServerSessionSpy.onMessage(testMessage.toString());
    assertFalse(webServerSessionSpy.getWebServerState().getCommands().containsKey(""));
  }

  @Test
  public void testOnError() {
    Throwable e = mock(Throwable.class);
    webServerSessionSpy.onError(e);
    assertEquals("", outputStreamCaptor.toString()
        .trim());
  }

  @Test
  public void testOnClose() {
    webServerSessionSpy.onClose(sessionDummy);
    assertEquals(null, webServerSessionSpy.getSession());
  }


  /**
   * Clean up our variables after each test.
   */
  @AfterEach
  public void cleanUpEach() {
    webServerSessionSpy = null;
    sessionDummy = null;
    commandFromClient = null;
    messageCaptor = null;
  }

}