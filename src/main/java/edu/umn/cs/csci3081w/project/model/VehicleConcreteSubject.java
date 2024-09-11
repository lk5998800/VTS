package edu.umn.cs.csci3081w.project.model;

import edu.umn.cs.csci3081w.project.webserver.WebServerSession;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

public class VehicleConcreteSubject implements VehicleSubject {
  private WebServerSession session;
  private List<VehicleObserver> observers;

  /**
   * Create a concrete vehicle subject.
   *
   * @param session parameter to communicate with the visualization module
   */
  public VehicleConcreteSubject(WebServerSession session) {
    this.session = session;
    observers = new ArrayList<VehicleObserver>();
  }

  /**
   * Add observer to the list that will be notified at each iteration.
   *
   * @param observer the observing vehicle
   */
  public void attachObserver(VehicleObserver observer) {
    observers.clear();
    observer.setVehicleSubject(this);
    observers.add(observer);
  }

  /**
   * Remove observer.
   *
   * @param observer the observer to detach
   */
  public void detachObserver(VehicleObserver observer) {
    observers.remove(observer);
  }

  /**
   * Notify all observers.
   */
  public void notifyObservers() {
    ListIterator<VehicleObserver> observersIter = observers.listIterator();
    while (observersIter.hasNext()) {
      boolean tripCompleted = observersIter.next().provideInfo();
      if (tripCompleted) {
        observersIter.remove();
      }
    }
  }

  public List<VehicleObserver> getObservers() {
    return observers;
  }

  public WebServerSession getSession() {
    return session;
  }
}
