package edu.umn.cs.csci3081w.project.model;

public class StorageFacility {
  private int smallBusesNum;
  private int largeBusesNum;
  private int electricTrainsNum;
  private int dieselTrainsNum;

  /**
   * Creates an empty storage facility.
   */
  public StorageFacility() {
    smallBusesNum = 0;
    largeBusesNum = 0;
    electricTrainsNum = 0;
    dieselTrainsNum = 0;
  }

  /**
   * Creates a storage facility with the given numbers.
   * @param smallBusesNum number of small buses
   * @param largeBusesNum number of large buses
   * @param electricTrainsNum number of electric trains
   * @param dieselTrainsNum number of diesel trains
   */
  public StorageFacility(int smallBusesNum, int largeBusesNum,
                         int electricTrainsNum, int dieselTrainsNum) {
    this.smallBusesNum = smallBusesNum;
    this.largeBusesNum = largeBusesNum;
    this.electricTrainsNum = electricTrainsNum;
    this.dieselTrainsNum = dieselTrainsNum;
  }

  public int getSmallBusesNum() {
    return smallBusesNum;
  }

  public int getElectricTrainsNum() {
    return electricTrainsNum;
  }

  public void setSmallBusesNum(int smallBusesNum) {
    this.smallBusesNum = smallBusesNum;
  }

  public void setElectricTrainsNum(int electricTrainsNum) {
    this.electricTrainsNum = electricTrainsNum;
  }

  public int getLargeBusesNum() {
    return largeBusesNum;
  }

  public void setLargeBusesNum(int largeBusesNum) {
    this.largeBusesNum = largeBusesNum;
  }

  public int getDieselTrainsNum() {
    return dieselTrainsNum;
  }

  public void setDieselTrainsNum(int dieselTrainsNum) {
    this.dieselTrainsNum = dieselTrainsNum;
  }

  public void decrementSmallBusesNum() {
    smallBusesNum--;
  }

  public void decrementLargeBusesNum() {
    largeBusesNum--;
  }

  public void decrementElectricTrainsNum() {
    electricTrainsNum--;
  }

  public void decrementDieselTrainsNum() {
    dieselTrainsNum--;
  }

  public void incrementSmallBusesNum() {
    smallBusesNum++;
  }

  public void incrementLargeBusesNum() {
    largeBusesNum++;
  }

  public void incrementElectricTrainsNum() {
    electricTrainsNum++;
  }

  public void incrementDieselTrainsNum() {
    dieselTrainsNum++;
  }
}
