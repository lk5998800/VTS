package edu.umn.cs.csci3081w.project.webserver;


import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

@ServerEndpoint(
    value = "/simulator",
    subprotocols = {"web_server"}
)
public class WebServerSession {

  private Session session;
  private WebServerSessionState webServerState;

  public WebServerSession() {
    System.out.println("class loaded " + this.getClass());
  }


  /**
   * Function which runs when client connection is made.
   *
   * @param session session object
   */
  @OnOpen
  public void onOpen(Session session) {
    System.out.println("session opened");
    try {
      //save session object
      this.session = session;
      webServerState = new WebServerSessionState();
      VisualTransitSimulator simulator = new VisualTransitSimulator(
          URLDecoder.decode(getClass().getClassLoader()
              .getResource("config.txt").getFile(), "UTF-8"), this);
      webServerState.getCommands().put("getRoutes", new GetRoutesCommand(simulator));
      webServerState.getCommands().put("getVehicles", new GetVehiclesCommand(simulator));
      webServerState.getCommands().put("start", new StartCommand(simulator));
      webServerState.getCommands().put("update", new UpdateCommand(simulator));
      webServerState.getCommands().put("initLines", new InitLinesCommand(simulator));
      webServerState.getCommands().put("registerVehicle", new RegisterVehicleCommand(simulator));
      webServerState.getCommands().put("lineIssue", new LineIssueCommand(simulator));
    } catch (UnsupportedEncodingException uee) {
      uee.printStackTrace();
    }
  }


  /**
   * Function which executes when a simulation command is received from the client.
   *
   * @param message incoming message
   */
  @OnMessage
  public void onMessage(String message) {
    JsonObject commandJson = JsonParser.parseString(message).getAsJsonObject();
    String command = commandJson.get("command").getAsString();
    if (command != null) {
      if (webServerState.getCommands().keySet().contains(command)) {
        SimulatorCommand myC = webServerState.getCommands().get(command);
        myC.execute(this, commandJson);
      }
    }
  }

  /**
   * sends simulation information to the client.
   *
   * @param message incoming data
   */
  public void sendJson(JsonObject message) {
    try {
      session.getBasicRemote().sendText(message.toString());
    } catch (IOException ex) {
      ex.printStackTrace();
    }
  }

  @OnError
  public void onError(Throwable e) {
    e.printStackTrace();
  }

  /**
   * Runs when session is closed by the client.
   *
   * @param session session object
   */
  @OnClose
  public void onClose(Session session) {
    System.out.println("session closed");
    //make session null as the session is closed
    this.session = null;
  }

  public Session getSession() {
    return session;
  }

  public WebServerSessionState getWebServerState() {
    return webServerState;
  }
}
