package edu.umn.cs.csci3081w.project.model;

public class TrainStrategyNight implements GenerationStrategy {
  private int counter;

  public TrainStrategyNight() {
    this.counter = 0;
  }

  @Override
  public String getTypeOfVehicle(StorageFacility storageFacility) {
    String typeOfVehicle = null;
    if (counter == 0) {
      if (storageFacility.getElectricTrainsNum() > 0) {
        typeOfVehicle = ElectricTrain.ELECTRIC_TRAIN_VEHICLE;
      }
    } else {
      if (storageFacility.getDieselTrainsNum() > 0) {
        typeOfVehicle = DieselTrain.DIESEL_TRAIN_VEHICLE;
      }
    }

    if (typeOfVehicle != null) {
      counter++;
      counter = counter % 2;
    }

    return typeOfVehicle;
  }

  public int getCounter() {
    return counter;
  }
}
