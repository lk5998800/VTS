package edu.umn.cs.csci3081w.project.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class BusStrategyNightTest {

  /**
   * Test constructor normal.
   */
  @Test
  public void testConstructor() {
    BusStrategyNight busStrategyNight = new BusStrategyNight();
    assertEquals(0, busStrategyNight.getCounter());
  }

  /**
   * Testing to get correct vehicle according to the strategy.
   */
  @Test
  public void testGetTypeOfVehicle() {
    StorageFacility storageFacility = new StorageFacility(3, 1, 0, 0);
    BusStrategyNight busStrategyNight = new BusStrategyNight();
    String strToCmpr;
    for (int i = 0; i < 1; i++) {
      strToCmpr = busStrategyNight.getTypeOfVehicle(storageFacility);
      assertEquals(SmallBus.SMALL_BUS_VEHICLE, strToCmpr);
      strToCmpr = busStrategyNight.getTypeOfVehicle(storageFacility);
      assertEquals(SmallBus.SMALL_BUS_VEHICLE, strToCmpr);
      strToCmpr = busStrategyNight.getTypeOfVehicle(storageFacility);
      assertEquals(SmallBus.SMALL_BUS_VEHICLE, strToCmpr);
      strToCmpr = busStrategyNight.getTypeOfVehicle(storageFacility);
      assertEquals(LargeBus.LARGE_BUS_VEHICLE, strToCmpr);
    }
  }

  /**
   * Testing to get correct vehicle according to the strategy.
   */
  @Test
  public void testGetTypeOfVehicleNull() {
    StorageFacility storageFacility = new StorageFacility(3, 0, 0, 0);
    BusStrategyNight busStrategyNight = new BusStrategyNight();
    String strToCmpr;
    for (int i = 0; i < 1; i++) {
      busStrategyNight.getTypeOfVehicle(storageFacility);
      busStrategyNight.getTypeOfVehicle(storageFacility);
      busStrategyNight.getTypeOfVehicle(storageFacility);
      strToCmpr = busStrategyNight.getTypeOfVehicle(storageFacility);
      assertEquals(null, strToCmpr);
    }
  }
}
