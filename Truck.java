//Alana Henden, Project1, CST338, 10/11/24
//Truck.java
//This class holds information about Truck objects, and this class is derived from the abstract Vehicle class
//This class implements the isStreetLegal method according to the emission limit set for trucks.


public class Truck extends Vehicle{

    private double loadCap;
    private int towingCap;
    private final double EMISSIONS_LIMIT = 500; // unit: gram/mile

    public Truck(String licensePlate, double loadCap, int towingCap, int mileage, double emissions) {
        super (licensePlate, mileage, emissions);
        this.loadCap = loadCap;
        this.towingCap = towingCap;
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

    public String getTruckLP() {
        return ("Truck with license plate " + getLicensePlate());
    }

}
