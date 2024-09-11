package edu.umn.cs.csci3081w.project.webserver;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class WebServerSessionStateTest {
  private WebServerSessionState testSessionState;

  /**
   * Setup operations before each test runs.
   */
  @BeforeEach
  public void setUp() {
    testSessionState = new WebServerSessionState();
  }

  /**
   * Test state after using constructor.
   */
  @Test
  public void testConstructor() {
    assertTrue(testSessionState.getCommands().isEmpty());
  }


  /**
   * Clean up our variables after each test.
   */
  @AfterEach
  public void cleanUpEach() {
    testSessionState = null;
  }
}