package edu.umn.cs.csci3081w.project.webserver;

import com.google.gson.JsonObject;

public abstract class SimulatorCommand {
  public abstract void execute(WebServerSession session, JsonObject command);
}
