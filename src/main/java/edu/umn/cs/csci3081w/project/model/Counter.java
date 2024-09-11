package edu.umn.cs.csci3081w.project.model;

public class Counter {

  private int routeIdCounter = 10;
  private int stopIdCounter = 100;
  private int smallBusIdCounter = 1000;
  private int largeBusIdCounter = 2000;
  private int electricTrainIdCounter = 3000;
  private int dieselTrainIdCounter = 4000;
  private int lineIdCounter = 10000;

  public Counter() {

  }

  public int getRouteIdCounterAndIncrement() {
    return routeIdCounter++;
  }

  public int getStopIdCounterAndIncrement() {
    return stopIdCounter++;
  }

  public int getSmallBusIdCounterAndIncrement() {
    return smallBusIdCounter++;
  }

  public int getLargeBusIdCounterAndIncrement() {
    return largeBusIdCounter++;
  }

  public int getElectricTrainIdCounterAndIncrement() {
    return electricTrainIdCounter++;
  }

  public int getDieselTrainIdCounterAndIncrement() {
    return dieselTrainIdCounter++;
  }

  public int getLineIdCounterAndIncrement() {
    return lineIdCounter++;
  }

}
