package edu.umn.cs.csci3081w.project.model;

import java.util.List;

public class PassengerGeneratorTestImpl extends PassengerGenerator {

  public PassengerGeneratorTestImpl(List<Stop> stops, List<Double> probabilities) {
    super(stops, probabilities);
  }

  @Override
  public int generatePassengers() {
    return 0;
  }
}
