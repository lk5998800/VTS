package edu.umn.cs.csci3081w.project.webserver;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class StartCommand extends SimulatorCommand {

  private VisualTransitSimulator simulator;

  /**
   * Start simulation constructor functionality.
   *
   * @param simulator simulation object
   */
  public StartCommand(VisualTransitSimulator simulator) {
    this.simulator = simulator;
  }

  /**
   * This method starts the simulation.
   *
   * @param session current simulation session
   * @param command the content of the start simulations content
   */
  @Override
  public void execute(WebServerSession session, JsonObject command) {
    List<Integer> timeBetweenVehicles = new ArrayList<Integer>();
    int numTimeSteps = command.get("numTimeSteps").getAsInt();
    JsonArray arr = command.getAsJsonArray("timeBetweenVehicles");
    for (int i = 0; i < arr.size(); i++) {
      timeBetweenVehicles.add(arr.get(i).getAsInt());
    }
    for (int i = 0; i < timeBetweenVehicles.size(); i++) {
      System.out.println("Time between vehicles for route  " + i + ": "
          + timeBetweenVehicles.get(i));
    }
    System.out.println("Number of time steps for simulation is: " + numTimeSteps);
    System.out.println("Starting simulation");
    simulator.setVehicleFactories(getCurrentSimulationTime());
    simulator.start(timeBetweenVehicles, numTimeSteps);
  }

  public int getCurrentSimulationTime() {
    return LocalDateTime.now().getHour();
  }

}
