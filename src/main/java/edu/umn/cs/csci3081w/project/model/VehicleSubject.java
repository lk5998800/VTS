package edu.umn.cs.csci3081w.project.model;

public interface VehicleSubject {
  public void attachObserver(VehicleObserver observer);

  public void detachObserver(VehicleObserver observer);

  public void notifyObservers();
}
