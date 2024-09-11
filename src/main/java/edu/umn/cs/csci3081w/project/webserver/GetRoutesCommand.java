package edu.umn.cs.csci3081w.project.webserver;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import edu.umn.cs.csci3081w.project.model.Line;
import edu.umn.cs.csci3081w.project.model.Route;
import java.util.List;

public class GetRoutesCommand extends SimulatorCommand {

  private VisualTransitSimulator simulator;

  public GetRoutesCommand(VisualTransitSimulator simulator) {
    this.simulator = simulator;
  }

  /**
   * Retrieves routes information from the simulation.
   *
   * @param session current simulation session
   * @param command the get routes command content
   */
  @Override
  public void execute(WebServerSession session, JsonObject command) {
    List<Line> lines = simulator.getLines();
    JsonObject data = new JsonObject();
    data.addProperty("command", "updateRoutes");
    JsonArray routesArray = new JsonArray();
    for (int i = 0; i < lines.size(); i++) {
      routesArray.add(getRouteJsonObject(lines.get(i).getOutboundRoute()));
      routesArray.add(getRouteJsonObject(lines.get(i).getInboundRoute()));
    }
    data.add("routes", routesArray);
    session.sendJson(data);
  }

  private JsonObject getRouteJsonObject(Route route) {
    JsonObject r = new JsonObject();
    r.addProperty("id", route.getId());
    JsonArray stopArray = new JsonArray();
    for (int j = 0; j < (route.getStops().size()); j++) {
      JsonObject stopStruct = new JsonObject();
      stopStruct.addProperty("id", route.getStops().get(j).getId());
      stopStruct.addProperty("numPeople", route.getStops().get(j).getPassengers().size());
      JsonObject jsonObj = new JsonObject();
      jsonObj.addProperty("longitude",
          route.getStops().get(j).getPosition().getLongitude());
      jsonObj.addProperty("latitude",
          route.getStops().get(j).getPosition().getLatitude());
      stopStruct.add("position", jsonObj);
      stopArray.add(stopStruct);
    }
    r.add("stops", stopArray);
    return r;
  }
}

