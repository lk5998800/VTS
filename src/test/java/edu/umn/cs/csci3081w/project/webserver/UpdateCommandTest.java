package edu.umn.cs.csci3081w.project.webserver;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;

import com.google.gson.JsonObject;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class UpdateCommandTest {
  private VisualTransitSimulator simulatorMock;
  private WebServerSession sessionMock;
  private JsonObject commandFromClient;
  private UpdateCommand testCommand;

  /**
   * Setup operations before each test runs.
   */
  @BeforeEach
  public void setUp() {
    simulatorMock = mock(VisualTransitSimulator.class);
    sessionMock = spy(WebServerSession.class);
    commandFromClient = new JsonObject();
    testCommand = new UpdateCommand(simulatorMock);
  }

  /**
   * Test if execute works properly.
   */
  @Test
  public void testExecute() {
    testCommand.execute(sessionMock, commandFromClient);
    verify(simulatorMock).update();
  }

  /**
   * Clean up our variables after each test.
   */
  @AfterEach
  public void cleanUpEach() {
    simulatorMock = null;
    sessionMock = null;
    commandFromClient = null;
    testCommand = null;
  }
}