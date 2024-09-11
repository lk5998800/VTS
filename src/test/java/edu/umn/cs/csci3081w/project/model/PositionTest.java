package edu.umn.cs.csci3081w.project.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class PositionTest {

  private Position testPosition;

  /**
   * Test constructor.
   */
  @Test
  public void testConstructor() {

    testPosition = new Position(-93.243774, 44.972392);

    assertEquals(-93.243774, testPosition.getLongitude());
    assertEquals(44.972392, testPosition.getLatitude());

  }

}
