package edu.umn.cs.csci3081w.project.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TrainFactoryTest {
  private StorageFacility storageFacility;
  private TrainFactory trainFactoryday;
  private TrainFactory trainFactorynight;
  private TrainFactory trainFactorynight2;

  /**
   * Setup operations before each test runs.
   */
  @BeforeEach
  public void setUp() {
    storageFacility = new StorageFacility(0, 0, 1, 1);
    trainFactoryday = new TrainFactory(storageFacility, new Counter(), 9);
    trainFactorynight = new TrainFactory(storageFacility, new Counter(), 20);
    trainFactorynight2 = new TrainFactory(storageFacility, new Counter(), 7);
  }

  /**
   * Testing the constructor.
   */
  @Test
  public void testConstructor() {
    assertTrue(trainFactoryday.getGenerationStrategy() instanceof TrainStrategyDay);
  }

  /**
   * Testing if generated vehicle is working according to strategy.
   */
  @Test
  public void testGenerateVehicleElectric() {
    List<Stop> stopsIn = new ArrayList<Stop>();
    Stop stop1 = new Stop(0, "test stop 1", new Position(-93.243774, 44.972392));
    Stop stop2 = new Stop(1, "test stop 2", new Position(-93.235071, 44.973580));
    stopsIn.add(stop1);
    stopsIn.add(stop2);
    List<Double> distancesIn = new ArrayList<>();
    distancesIn.add(0.843774422231134);
    List<Double> probabilitiesIn = new ArrayList<Double>();
    probabilitiesIn.add(.025);
    probabilitiesIn.add(0.3);
    PassengerGenerator generatorIn = new RandomPassengerGenerator(stopsIn, probabilitiesIn);

    Route testRouteIn = new Route(0, "testRouteIn",
        stopsIn, distancesIn, generatorIn);

    List<Stop> stopsOut = new ArrayList<Stop>();
    stopsOut.add(stop2);
    stopsOut.add(stop1);
    List<Double> distancesOut = new ArrayList<>();
    distancesOut.add(0.843774422231134);
    List<Double> probabilitiesOut = new ArrayList<Double>();
    probabilitiesOut.add(0.3);
    probabilitiesOut.add(.025);
    PassengerGenerator generatorOut = new RandomPassengerGenerator(stopsOut, probabilitiesOut);

    Route testRouteOut = new Route(1, "testRouteOut",
        stopsOut, distancesOut, generatorOut);

    Line line = new Line(10000, "testLine", "TRAIN", testRouteOut, testRouteIn,
        new Issue());

    Vehicle vehicle1 = trainFactoryday.generateVehicle(line);
    assertEquals(vehicle1.getType(), ElectricTrain.ELECTRIC_TRAIN_VEHICLE);
  }

  /**
   * Testing if generated vehicle is working according to strategy.
   */
  @Test
  public void testGenerateVehicleDieselLateTime() {
    List<Stop> stopsIn = new ArrayList<Stop>();
    Stop stop1 = new Stop(0, "test stop 1", new Position(-93.243774, 44.972392));
    Stop stop2 = new Stop(1, "test stop 2", new Position(-93.235071, 44.973580));
    stopsIn.add(stop1);
    stopsIn.add(stop2);
    List<Double> distancesIn = new ArrayList<>();
    distancesIn.add(0.843774422231134);
    List<Double> probabilitiesIn = new ArrayList<Double>();
    probabilitiesIn.add(.025);
    probabilitiesIn.add(0.3);
    PassengerGenerator generatorIn = new RandomPassengerGenerator(stopsIn, probabilitiesIn);

    Route testRouteIn = new Route(0, "testRouteIn",
        stopsIn, distancesIn, generatorIn);

    List<Stop> stopsOut = new ArrayList<Stop>();
    stopsOut.add(stop2);
    stopsOut.add(stop1);
    List<Double> distancesOut = new ArrayList<>();
    distancesOut.add(0.843774422231134);
    List<Double> probabilitiesOut = new ArrayList<Double>();
    probabilitiesOut.add(0.3);
    probabilitiesOut.add(.025);
    PassengerGenerator generatorOut = new RandomPassengerGenerator(stopsOut, probabilitiesOut);

    Route testRouteOut = new Route(1, "testRouteOut",
        stopsOut, distancesOut, generatorOut);

    Line line = new Line(10000, "testLine", "TRAIN", testRouteOut, testRouteIn,
        new Issue());

    trainFactorynight.generateVehicle(line);
    Vehicle vehicle2 = trainFactorynight.generateVehicle(line);
    assertEquals(vehicle2.getType(), DieselTrain.DIESEL_TRAIN_VEHICLE);
  }

  /**
   * Testing if generated vehicle is working according to strategy.
   */
  @Test
  public void testGenerateVehicleDieselEarlyTime() {
    List<Stop> stopsIn = new ArrayList<Stop>();
    Stop stop1 = new Stop(0, "test stop 1", new Position(-93.243774, 44.972392));
    Stop stop2 = new Stop(1, "test stop 2", new Position(-93.235071, 44.973580));
    stopsIn.add(stop1);
    stopsIn.add(stop2);
    List<Double> distancesIn = new ArrayList<>();
    distancesIn.add(0.843774422231134);
    List<Double> probabilitiesIn = new ArrayList<Double>();
    probabilitiesIn.add(.025);
    probabilitiesIn.add(0.3);
    PassengerGenerator generatorIn = new RandomPassengerGenerator(stopsIn, probabilitiesIn);

    Route testRouteIn = new Route(0, "testRouteIn",
        stopsIn, distancesIn, generatorIn);

    List<Stop> stopsOut = new ArrayList<Stop>();
    stopsOut.add(stop2);
    stopsOut.add(stop1);
    List<Double> distancesOut = new ArrayList<>();
    distancesOut.add(0.843774422231134);
    List<Double> probabilitiesOut = new ArrayList<Double>();
    probabilitiesOut.add(0.3);
    probabilitiesOut.add(.025);
    PassengerGenerator generatorOut = new RandomPassengerGenerator(stopsOut, probabilitiesOut);

    Route testRouteOut = new Route(1, "testRouteOut",
        stopsOut, distancesOut, generatorOut);

    Line line = new Line(10000, "testLine", "TRAIN", testRouteOut, testRouteIn,
        new Issue());

    trainFactorynight2.generateVehicle(line);
    Vehicle vehicle2 = trainFactorynight2.generateVehicle(line);
    assertEquals(vehicle2.getType(), DieselTrain.DIESEL_TRAIN_VEHICLE);
  }

  @Test
  public void testGenerateVehicleNull() {
    List<Stop> stopsIn = new ArrayList<Stop>();
    Stop stop1 = new Stop(0, "test stop 1", new Position(-93.243774, 44.972392));
    Stop stop2 = new Stop(1, "test stop 2", new Position(-93.235071, 44.973580));
    stopsIn.add(stop1);
    stopsIn.add(stop2);
    List<Double> distancesIn = new ArrayList<>();
    distancesIn.add(0.843774422231134);
    List<Double> probabilitiesIn = new ArrayList<Double>();
    probabilitiesIn.add(.025);
    probabilitiesIn.add(0.3);
    PassengerGenerator generatorIn = new RandomPassengerGenerator(stopsIn, probabilitiesIn);

    Route testRouteIn = new Route(0, "testRouteIn",
        stopsIn, distancesIn, generatorIn);

    List<Stop> stopsOut = new ArrayList<Stop>();
    stopsOut.add(stop2);
    stopsOut.add(stop1);
    List<Double> distancesOut = new ArrayList<>();
    distancesOut.add(0.843774422231134);
    List<Double> probabilitiesOut = new ArrayList<Double>();
    probabilitiesOut.add(0.3);
    probabilitiesOut.add(.025);
    PassengerGenerator generatorOut = new RandomPassengerGenerator(stopsOut, probabilitiesOut);

    Route testRouteOut = new Route(1, "testRouteOut",
        stopsOut, distancesOut, generatorOut);

    Line line = new Line(10000, "testLine", "TRAIN", testRouteOut, testRouteIn,
        new Issue());
    trainFactoryday.generateVehicle(line);
    assertEquals(null, trainFactoryday.generateVehicle(line));
  }

  /**
   * Testing if vehicle got returned.
   */
  @Test
  public void testReturnVehicleElectricTrain() {
    List<Stop> stopsIn = new ArrayList<Stop>();
    Stop stop1 = new Stop(0, "test stop 1", new Position(-93.243774, 44.972392));
    Stop stop2 = new Stop(1, "test stop 2", new Position(-93.235071, 44.973580));
    stopsIn.add(stop1);
    stopsIn.add(stop2);
    List<Double> distancesIn = new ArrayList<>();
    distancesIn.add(0.843774422231134);
    List<Double> probabilitiesIn = new ArrayList<Double>();
    probabilitiesIn.add(.025);
    probabilitiesIn.add(0.3);
    PassengerGenerator generatorIn = new RandomPassengerGenerator(stopsIn, probabilitiesIn);

    Route testRouteIn = new Route(0, "testRouteIn",
        stopsIn, distancesIn, generatorIn);

    List<Stop> stopsOut = new ArrayList<Stop>();
    stopsOut.add(stop2);
    stopsOut.add(stop1);
    List<Double> distancesOut = new ArrayList<>();
    distancesOut.add(0.843774422231134);
    List<Double> probabilitiesOut = new ArrayList<Double>();
    probabilitiesOut.add(0.3);
    probabilitiesOut.add(.025);
    PassengerGenerator generatorOut = new RandomPassengerGenerator(stopsOut, probabilitiesOut);

    Route testRouteOut = new Route(1, "testRouteOut",
        stopsOut, distancesOut, generatorOut);

    Train testTrain = new ElectricTrain(1, new Line(10000, "testLine", "BUS",
        testRouteOut, testRouteIn, new Issue()), 3, 1.0);

    assertEquals(1, trainFactoryday.getStorageFacility().getElectricTrainsNum());
    assertEquals(1, trainFactoryday.getStorageFacility().getDieselTrainsNum());
    trainFactoryday.returnVehicle(testTrain);
    assertEquals(2, trainFactoryday.getStorageFacility().getElectricTrainsNum());
    assertEquals(1, trainFactoryday.getStorageFacility().getDieselTrainsNum());

  }

  /**
   * Testing if vehicle got returned.
   */
  @Test
  public void testReturnVehicleDieselTrain() {
    List<Stop> stopsIn = new ArrayList<Stop>();
    Stop stop1 = new Stop(0, "test stop 1", new Position(-93.243774, 44.972392));
    Stop stop2 = new Stop(1, "test stop 2", new Position(-93.235071, 44.973580));
    stopsIn.add(stop1);
    stopsIn.add(stop2);
    List<Double> distancesIn = new ArrayList<>();
    distancesIn.add(0.843774422231134);
    List<Double> probabilitiesIn = new ArrayList<Double>();
    probabilitiesIn.add(.025);
    probabilitiesIn.add(0.3);
    PassengerGenerator generatorIn = new RandomPassengerGenerator(stopsIn, probabilitiesIn);

    Route testRouteIn = new Route(0, "testRouteIn",
        stopsIn, distancesIn, generatorIn);

    List<Stop> stopsOut = new ArrayList<Stop>();
    stopsOut.add(stop2);
    stopsOut.add(stop1);
    List<Double> distancesOut = new ArrayList<>();
    distancesOut.add(0.843774422231134);
    List<Double> probabilitiesOut = new ArrayList<Double>();
    probabilitiesOut.add(0.3);
    probabilitiesOut.add(.025);
    PassengerGenerator generatorOut = new RandomPassengerGenerator(stopsOut, probabilitiesOut);

    Route testRouteOut = new Route(1, "testRouteOut",
        stopsOut, distancesOut, generatorOut);

    Train testTrain = new DieselTrain(1, new Line(10000, "testLine", "BUS",
        testRouteOut, testRouteIn, new Issue()), 3, 1.0);

    assertEquals(1, trainFactoryday.getStorageFacility().getElectricTrainsNum());
    assertEquals(1, trainFactoryday.getStorageFacility().getDieselTrainsNum());
    trainFactoryday.returnVehicle(testTrain);
    assertEquals(1, trainFactoryday.getStorageFacility().getElectricTrainsNum());
    assertEquals(2, trainFactoryday.getStorageFacility().getDieselTrainsNum());

  }

  /**
   * Testing if vehicle got returned.
   */
  @Test
  public void testReturnVehicleNull() {

    assertEquals(1, trainFactoryday.getStorageFacility().getElectricTrainsNum());
    assertEquals(1, trainFactoryday.getStorageFacility().getDieselTrainsNum());
    trainFactoryday.returnVehicle(null);
    assertEquals(1, trainFactoryday.getStorageFacility().getElectricTrainsNum());
    assertEquals(1, trainFactoryday.getStorageFacility().getDieselTrainsNum());

  }

  /**
   * Testing if vehicle got returned.
   */
  @Test
  public void testReturnVehicleBus() {
    List<Stop> stopsIn = new ArrayList<Stop>();
    Stop stop1 = new Stop(0, "test stop 1", new Position(-93.243774, 44.972392));
    Stop stop2 = new Stop(1, "test stop 2", new Position(-93.235071, 44.973580));
    stopsIn.add(stop1);
    stopsIn.add(stop2);
    List<Double> distancesIn = new ArrayList<>();
    distancesIn.add(0.843774422231134);
    List<Double> probabilitiesIn = new ArrayList<Double>();
    probabilitiesIn.add(.025);
    probabilitiesIn.add(0.3);
    PassengerGenerator generatorIn = new RandomPassengerGenerator(stopsIn, probabilitiesIn);

    Route testRouteIn = new Route(0, "testRouteIn",
        stopsIn, distancesIn, generatorIn);

    List<Stop> stopsOut = new ArrayList<Stop>();
    stopsOut.add(stop2);
    stopsOut.add(stop1);
    List<Double> distancesOut = new ArrayList<>();
    distancesOut.add(0.843774422231134);
    List<Double> probabilitiesOut = new ArrayList<Double>();
    probabilitiesOut.add(0.3);
    probabilitiesOut.add(.025);
    PassengerGenerator generatorOut = new RandomPassengerGenerator(stopsOut, probabilitiesOut);

    Route testRouteOut = new Route(1, "testRouteOut",
        stopsOut, distancesOut, generatorOut);

    Bus testBus = new LargeBus(1,
        new Line(10000, "testLine", "BUS", testRouteOut, testRouteIn,
        new Issue()), 3, 1.0);
    assertEquals(1, trainFactoryday.getStorageFacility().getElectricTrainsNum());
    assertEquals(1, trainFactoryday.getStorageFacility().getDieselTrainsNum());
    trainFactoryday.returnVehicle(testBus);
    assertEquals(1, trainFactoryday.getStorageFacility().getElectricTrainsNum());
    assertEquals(1, trainFactoryday.getStorageFacility().getDieselTrainsNum());

  }
}
