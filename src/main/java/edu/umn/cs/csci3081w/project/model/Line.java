package edu.umn.cs.csci3081w.project.model;

import java.io.PrintStream;
import java.util.Iterator;

public class Line {

  public static final String BUS_LINE = "BUS_LINE";
  public static final String TRAIN_LINE = "TRAIN_LINE";
  private int id;
  private String name;
  private String type;
  private Route outboundRoute;
  private Route inboundRoute;
  private Issue issue;

  /**
   * Constructor for a Line.
   *
   * @param id            line identifier
   * @param name          line name
   * @param type          line type
   * @param outboundRoute outbound route
   * @param inboundRoute  inbound route
   * @param issue         issue
   */
  public Line(int id, String name, String type, Route outboundRoute, Route inboundRoute,
              Issue issue) {
    this.id = id;
    this.name = name;
    this.type = type;
    this.outboundRoute = outboundRoute;
    this.inboundRoute = inboundRoute;
    this.issue = issue;
  }

  /**
   * Report statistics for the line.
   *
   * @param out stream for printing
   */
  public void report(PrintStream out) {
    out.println("====Line Info Start====");
    out.println("ID: " + id);
    out.println("Name: " + name);
    out.println("Type: " + type);
    outboundRoute.report(out);
    inboundRoute.report(out);
    out.println("====Line Info End====");
  }

  /**
   * Shallow copies a line.
   * This method shallow copies a line as routes are shared
   * across copied lines
   *
   * @return Copy of line
   */
  public Line shallowCopy() {
    Line shallowCopy = new Line(this.id, this.name, this.type,
        outboundRoute.shallowCopy(), inboundRoute.shallowCopy(), this.issue);
    return shallowCopy;
  }

  /**
   * Updates and generates passengers on both the routes in the line.
   */
  public void update() {
    outboundRoute.update();
    inboundRoute.update();
    if (this.issue != null && !this.issue.isIssueResolved()) {
      this.issue.decrementCounter();
    }
  }

  public int getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public String getType() {
    return type;
  }

  public Route getOutboundRoute() {
    return this.outboundRoute;
  }

  public Route getInboundRoute() {
    return this.inboundRoute;
  }

  /**
   * Check whether an issue exsist on the line.
   *
   * @return whether an issue exist
   */
  public boolean isIssueExist() {
    if (this.issue == null) {
      return false;
    }

    return !issue.isIssueResolved();
  }

  public void createIssue() {
    this.issue.createIssue();
  }

  public Issue getIssue() {
    return issue;
  }
}
