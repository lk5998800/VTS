package edu.umn.cs.csci3081w.project.webserver;

import static org.junit.jupiter.api.Assertions.assertEquals;

import edu.umn.cs.csci3081w.project.model.Counter;
import edu.umn.cs.csci3081w.project.model.Line;
import edu.umn.cs.csci3081w.project.model.Route;
import edu.umn.cs.csci3081w.project.model.StorageFacility;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ConfigManagerTest {

  private ConfigManager testConfigManger;
  private List<Line> lines;
  private List<Route> routes;
  private StorageFacility storageFacility;

  /**
   * Setup deterministic operations before each test runs.
   */
  @BeforeEach
  public void setUp() {
    testConfigManger = new ConfigManager();
  }

  /**
   * Test state after using constructor.
   */
  @Test
  public void testConstructorNormal() {
    assertEquals(null, testConfigManger.getStorageFacility());
  }

  /**
   * Test if readConfig works properly.
   */
  @Test
  public void testReadConfig() {
    testConfigManger.readConfig(new Counter(), "src/main/resources/config.txt");
    lines = testConfigManger.getLines();
    routes = testConfigManger.getRoutes();
    storageFacility = testConfigManger.getStorageFacility();
    assertEquals(2, lines.size());
    assertEquals(4, routes.size());
    assertEquals(4, storageFacility.getSmallBusesNum());
    assertEquals(2, storageFacility.getLargeBusesNum());
    assertEquals(1, storageFacility.getElectricTrainsNum());
    assertEquals(5, storageFacility.getDieselTrainsNum());
  }

  /**
   * Test if readConfig works properly.
   */
  @Test
  public void testReadConfigSpecialCase() {
    testConfigManger.readConfig(new Counter(), "src/main/resources/testConfig.txt");
    lines = testConfigManger.getLines();
    routes = testConfigManger.getRoutes();
    storageFacility = testConfigManger.getStorageFacility();
    assertEquals(2, lines.size());
    assertEquals(3, routes.size());

  }

  /**
   * Clean up our variables after each test.
   */
  @AfterEach
  public void cleanUpEach() {
    testConfigManger = null;
  }
}