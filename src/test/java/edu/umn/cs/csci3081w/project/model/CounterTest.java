package edu.umn.cs.csci3081w.project.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CounterTest {

  private Counter testCounter;


  /**
   * Setup operations before each test runs.
   */
  @BeforeEach
  public void setUp() {
    testCounter = new Counter();
  }


  /**
   * Tests if getRouteIdCounterAndIncrement function works properly.
   */
  @Test
  public void testGetRouteIdCounterAndIncrement() {
    assertEquals(10, testCounter.getRouteIdCounterAndIncrement());
    assertEquals(11, testCounter.getRouteIdCounterAndIncrement());
  }

  /**
   * Tests if getStopIdCounterAndIncrement function works properly.
   */
  @Test
  public void testGetStopIdCounterAndIncrement() {
    assertEquals(100, testCounter.getStopIdCounterAndIncrement());
    assertEquals(101, testCounter.getStopIdCounterAndIncrement());
  }

  /**
   * Tests if getSmallBusIdCounterAndIncrement function works properly.
   */
  @Test
  public void testGetSmallBusIdCounterAndIncrement() {
    assertEquals(1000, testCounter.getSmallBusIdCounterAndIncrement());
    assertEquals(1001, testCounter.getSmallBusIdCounterAndIncrement());
  }

  /**
   * Tests if getLargeBusIdCounterAndIncrement function works properly.
   */
  @Test
  public void testGetLargeBusIdCounterAndIncrement() {
    assertEquals(2000, testCounter.getLargeBusIdCounterAndIncrement());
    assertEquals(2001, testCounter.getLargeBusIdCounterAndIncrement());
  }

  /**
   * Tests if getElectricTrainIdCounterAndIncrement function works properly.
   */
  @Test
  public void testGetTrainIdCounterAndIncrement() {
    assertEquals(3000, testCounter.getElectricTrainIdCounterAndIncrement());
    assertEquals(3001, testCounter.getElectricTrainIdCounterAndIncrement());
  }

  /**
   * Tests if getDieselTrainIdCounterAndIncrement function works properly.
   */
  @Test
  public void getDieselTrainIdCounterAndIncrement() {
    assertEquals(4000, testCounter.getDieselTrainIdCounterAndIncrement());
    assertEquals(4001, testCounter.getDieselTrainIdCounterAndIncrement());
  }

  /**
   * Tests if getLineIdCounterAndIncrement function works properly.
   */
  @Test
  public void testGetLineIdCounterAndIncrement() {
    assertEquals(10000, testCounter.getLineIdCounterAndIncrement());
    assertEquals(10001, testCounter.getLineIdCounterAndIncrement());
  }

  /**
   * Clean up our variables after each test.
   */
  @AfterEach
  public void cleanUpEach() {
    testCounter = null;
  }

}
