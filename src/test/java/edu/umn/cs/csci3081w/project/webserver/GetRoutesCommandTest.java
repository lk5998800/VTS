package edu.umn.cs.csci3081w.project.webserver;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;

import com.google.gson.JsonObject;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

public class GetRoutesCommandTest {
  private VisualTransitSimulator simulatorMock;
  private WebServerSession sessionSpy;
  private JsonObject command;
  private GetRoutesCommand testCommand;
  private ArgumentCaptor<JsonObject> messageCaptor;


  /**
   * Setup operations before each test runs.
   */
  @BeforeEach
  public void setUp() {
    simulatorMock = mock(VisualTransitSimulator.class);
    sessionSpy = spy(WebServerSession.class);
    doNothing().when(sessionSpy).sendJson(Mockito.isA(JsonObject.class));
    command = new JsonObject();
    testCommand = new GetRoutesCommand(simulatorMock);
    messageCaptor = ArgumentCaptor.forClass(JsonObject.class);
  }

  /**
   * Test if execute works properly.
   */
  @Test
  public void testExecute() {
    testCommand.execute(sessionSpy, command);
    verify(sessionSpy).sendJson(messageCaptor.capture());
    JsonObject commandToClient = messageCaptor.getValue();
    assertEquals("updateRoutes", commandToClient.get("command").getAsString());
  }


  /**
   * Clean up our variables after each test.
   */
  @AfterEach
  public void cleanUpEach() {
    simulatorMock = null;
    sessionSpy = null;
    command = null;
    testCommand = null;
    messageCaptor = null;
  }

}