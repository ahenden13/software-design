//Alana Henden, Project1, CST338, 10/11/24
//Vehicle.java
//This class holds information about Vehicle objects, and is our base (abstract) class
//for car and truck child classes. Contains basic getters/setters, but mainly the
//important part of this class is declaring the abstract method that Car/Truck will implement

public abstract class Vehicle {

    private String licensePlate;
    private double emissions;
    private Driver driver = null;
    private boolean streetLegal;
    private int mileage;

    public Vehicle(String licensePlate, int mileage, double emissions) {
        this.licensePlate = licensePlate;
        this.mileage = mileage;
        this.emissions = emissions;
    }

    public int getMileage() {
        return mileage;
    }

    public double getEmissions() {
        return emissions;
    }

    public void setDriver(Driver d) {
        this.driver = d;
    }

    public abstract boolean isStreetLegal();

    public String getLicensePlate() {
        return licensePlate;
    }

    public Driver getDriver() {
        return driver;
    }
}
