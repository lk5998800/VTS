package edu.umn.cs.csci3081w.project.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class BusStrategyDayTest {

  /**
   * Test constructor normal.
   */
  @Test
  public void testConstructor() {
    BusStrategyDay busStrategyDay = new BusStrategyDay();
    assertEquals(0, busStrategyDay.getCounter());
  }

  /**
   * Testing to get correct vehicle according to the strategy.
   */
  @Test
  public void testGetTypeOfVehicle() {
    StorageFacility storageFacility = new StorageFacility(1, 2, 0, 0);
    BusStrategyDay busStrategyDay = new BusStrategyDay();
    String strToCmpr;
    for (int i = 0; i < 1; i++) {
      strToCmpr = busStrategyDay.getTypeOfVehicle(storageFacility);
      assertEquals(LargeBus.LARGE_BUS_VEHICLE, strToCmpr);
      strToCmpr = busStrategyDay.getTypeOfVehicle(storageFacility);
      assertEquals(LargeBus.LARGE_BUS_VEHICLE, strToCmpr);
      strToCmpr = busStrategyDay.getTypeOfVehicle(storageFacility);
      assertEquals(SmallBus.SMALL_BUS_VEHICLE, strToCmpr);
    }
  }

  /**
   * Testing to get correct vehicle according to the strategy.
   */
  @Test
  public void testGetTypeOfVehicleNoSmall() {
    StorageFacility storageFacility = new StorageFacility(0, 2, 0, 0);
    BusStrategyDay busStrategyDay = new BusStrategyDay();
    String strToCmpr;
    for (int i = 0; i < 1; i++) {
      strToCmpr = busStrategyDay.getTypeOfVehicle(storageFacility);
      assertEquals(LargeBus.LARGE_BUS_VEHICLE, strToCmpr);
      strToCmpr = busStrategyDay.getTypeOfVehicle(storageFacility);
      assertEquals(LargeBus.LARGE_BUS_VEHICLE, strToCmpr);
      strToCmpr = busStrategyDay.getTypeOfVehicle(storageFacility);
      assertEquals(null, strToCmpr);
    }
  }

  /**
   * Testing to get correct vehicle according to the strategy.
   */
  @Test
  public void testGetTypeOfVehicleNoLarge() {
    StorageFacility storageFacility = new StorageFacility(1, 0, 0, 0);
    BusStrategyDay busStrategyDay = new BusStrategyDay();
    String strToCmpr;
    for (int i = 0; i < 1; i++) {
      strToCmpr = busStrategyDay.getTypeOfVehicle(storageFacility);
      assertEquals(null, strToCmpr);
    }
  }
}
