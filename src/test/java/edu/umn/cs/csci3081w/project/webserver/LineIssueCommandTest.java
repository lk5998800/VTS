package edu.umn.cs.csci3081w.project.webserver;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

import com.google.gson.JsonObject;
import edu.umn.cs.csci3081w.project.model.Line;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class LineIssueCommandTest {
  private VisualTransitSimulator simulator;
  private WebServerSession sessionMock;
  private JsonObject commandFromClient;
  private LineIssueCommand testCommand;

  /**
   * Setup operations before each test runs.
   */
  @BeforeEach
  public void setUp() {
    simulator = new VisualTransitSimulator("src/main/resources/config.txt", sessionMock);
    sessionMock = mock(WebServerSession.class);
    commandFromClient = new JsonObject();
    testCommand = new LineIssueCommand(simulator);
  }

  /**
   * Test if execute works properly.
   */
  @Test
  public void testExecute() {
    commandFromClient.addProperty("id", 10000);
    testCommand.execute(sessionMock, commandFromClient);
    List<Line> lines = simulator.getLines();
    int id = 0;
    for (int i = 0; i < lines.size(); i++) {
      if (lines.get(i).getId() == 0) {
        id = i;
      }
    }
    assertEquals(10, simulator.getLines().get(id).getIssue().getCounter());
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