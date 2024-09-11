package edu.umn.cs.csci3081w.project.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class StorageFacilityTest {

  private StorageFacility testStorageFacility;


  /**
   * Setup operations before each test runs.
   */
  @BeforeEach
  public void setUp() {
    testStorageFacility = new StorageFacility(1, 1, 1, 1);
  }

  /**
   * Tests constructor.
   */
  @Test
  public void testConstructor() {
    assertEquals(1, testStorageFacility.getSmallBusesNum());
    assertEquals(1, testStorageFacility.getElectricTrainsNum());
    assertEquals(1, testStorageFacility.getLargeBusesNum());
    assertEquals(1, testStorageFacility.getDieselTrainsNum());

  }

  /**
   * Tests constructor.
   */
  @Test
  public void testConstructor2() {
    StorageFacility storageFacility = new StorageFacility();
    assertEquals(0, storageFacility.getSmallBusesNum());
    assertEquals(0, storageFacility.getElectricTrainsNum());
    assertEquals(0, storageFacility.getLargeBusesNum());
    assertEquals(0, storageFacility.getDieselTrainsNum());

  }


  /**
   * Tests if decrementSmallBusesNum function works properly.
   */
  @Test
  public void testDecrementSmallBusesNum() {
    testStorageFacility.decrementSmallBusesNum();
    assertEquals(0, testStorageFacility.getSmallBusesNum());
  }


  /**
   * Tests if decrementLargeBusesNum function works properly.
   */
  @Test
  public void testDecrementLargeBusesNum() {
    testStorageFacility.decrementLargeBusesNum();
    assertEquals(0, testStorageFacility.getLargeBusesNum());
  }


  /**
   * Tests if decrementElectricTrainsNum function works properly.
   */
  @Test
  public void testDecrementElectricTrainsNum() {
    testStorageFacility.decrementElectricTrainsNum();
    assertEquals(0, testStorageFacility.getElectricTrainsNum());
  }


  /**
   * Tests if decrementDieselTrainsNum function works properly.
   */
  @Test
  public void testDecrementDieselTrainsNum() {
    testStorageFacility.decrementDieselTrainsNum();
    assertEquals(0, testStorageFacility.getDieselTrainsNum());
  }


  /**
   * Tests if decrementSmallBusesNum function works properly.
   */
  @Test
  public void testIncrementSmallBusesNum() {
    testStorageFacility.incrementSmallBusesNum();
    assertEquals(2, testStorageFacility.getSmallBusesNum());
  }


  /**
   * Tests if decrementLargeBusesNum function works properly.
   */
  @Test
  public void testIncrementLargeBusesNum() {
    testStorageFacility.incrementLargeBusesNum();
    assertEquals(2, testStorageFacility.getLargeBusesNum());
  }


  /**
   * Tests if decrementElectricTrainsNum function works properly.
   */
  @Test
  public void testIncrementElectricTrainsNum() {
    testStorageFacility.incrementElectricTrainsNum();
    assertEquals(2, testStorageFacility.getElectricTrainsNum());
  }


  /**
   * Tests if decrementDieselTrainsNum function works properly.
   */
  @Test
  public void testIncrementDieselTrainsNum() {
    testStorageFacility.incrementDieselTrainsNum();
    assertEquals(2, testStorageFacility.getDieselTrainsNum());
  }


  /**
   * Clean up our variables after each test.
   */
  @AfterEach
  public void cleanUpEach() {
    testStorageFacility = null;
  }

}
