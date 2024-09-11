package edu.umn.cs.csci3081w.project.model;

import java.util.ArrayList;
import java.util.List;

public abstract class PassengerGenerator {
  private List<Stop> stops;
  private List<Double> probabilities;

  /**
   * Constructor for abstract class.
   *
   * @param stops         list of stops
   * @param probabilities list of probabilities
   */
  public PassengerGenerator(List<Stop> stops, List<Double> probabilities) {
    this.probabilities = new ArrayList<>();
    this.stops = new ArrayList<>();
    for (Stop s : stops) {
      this.stops.add(s);
    }
    for (Double probability : probabilities) {
      this.probabilities.add(probability);
    }
  }

  public abstract int generatePassengers();

  public List<Stop> getStops() {
    return stops;
  }

  public List<Double> getProbabilities() {
    return probabilities;
  }

}