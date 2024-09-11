package edu.umn.cs.csci3081w.project.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class TrainStrategyDayTest {

  /**
   * Test constructor normal.
   */
  @Test
  public void testConstructor() {
    TrainStrategyDay trainStrategyDay = new TrainStrategyDay();
    assertEquals(0, trainStrategyDay.getCounter());
  }

  /**
   * Testing to get correct vehicle according to the strategy.
   */
  @Test
  public void testGetTypeOfVehicle() {
    StorageFacility storageFacility = new StorageFacility(0, 0, 3, 1);
    TrainStrategyDay trainStrategyDay = new TrainStrategyDay();
    String strToCmpr;
    for (int i = 0; i < 1; i++) {
      strToCmpr = trainStrategyDay.getTypeOfVehicle(storageFacility);
      assertEquals(ElectricTrain.ELECTRIC_TRAIN_VEHICLE, strToCmpr);
      strToCmpr = trainStrategyDay.getTypeOfVehicle(storageFacility);
      assertEquals(ElectricTrain.ELECTRIC_TRAIN_VEHICLE, strToCmpr);
      strToCmpr = trainStrategyDay.getTypeOfVehicle(storageFacility);
      assertEquals(ElectricTrain.ELECTRIC_TRAIN_VEHICLE, strToCmpr);
      strToCmpr = trainStrategyDay.getTypeOfVehicle(storageFacility);
      assertEquals(DieselTrain.DIESEL_TRAIN_VEHICLE, strToCmpr);
    }
  }

  /**
   * Testing to get correct vehicle according to the strategy.
   */
  @Test
  public void testGetTypeOfVehicleNull() {
    StorageFacility storageFacility = new StorageFacility(0, 0, 3, 0);
    TrainStrategyDay trainStrategyDay = new TrainStrategyDay();
    String strToCmpr;
    for (int i = 0; i < 1; i++) {
      strToCmpr = trainStrategyDay.getTypeOfVehicle(storageFacility);
      assertEquals(ElectricTrain.ELECTRIC_TRAIN_VEHICLE, strToCmpr);
      strToCmpr = trainStrategyDay.getTypeOfVehicle(storageFacility);
      assertEquals(ElectricTrain.ELECTRIC_TRAIN_VEHICLE, strToCmpr);
      strToCmpr = trainStrategyDay.getTypeOfVehicle(storageFacility);
      assertEquals(ElectricTrain.ELECTRIC_TRAIN_VEHICLE, strToCmpr);
      strToCmpr = trainStrategyDay.getTypeOfVehicle(storageFacility);
      assertEquals(null, strToCmpr);
    }
  }
}
