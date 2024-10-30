//Alana Henden, Project1, CST338, 10/11/24
//Car.java
//This class holds information about Car objects, and this class is derived from the abstract Vehicle class
//This class implements the isStreetLegal method according to the emission limit set for cars.


public class Car extends Vehicle {

    private int numPassengers;
    private final double EMISSIONS_LIMIT = 350; //unit: gram/mile

    public Car(String licensePlate, int numPassengers, int mileage, double emissions) {
        super(licensePlate, mileage, emissions);
        this.numPassengers = numPassengers;
    }

    public boolean isStreetLegal() {
        boolean legal = true;
        double em = getEmissions() * 1000;
        int mi = getMileage();
        if ((em/mi) > EMISSIONS_LIMIT) {
            legal = false;
        }
        return legal;
    }

    public String getCarLP() {
        return ("Car with license plate " + getLicensePlate());
    }
}
