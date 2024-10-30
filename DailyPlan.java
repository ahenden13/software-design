//Alana Henden, Project1, CST338, 10/11/24
//DailyPlan.java
//This class does the majority of the work for my program. It will read in a text file via a code path (string),
// and create Driver, Car and Truck objects according to the file contents and add those objects to 2 lists.
// Then, I will assign vehicles to the corresponding vehicles based on seniority, and checking other conditions along the way.
// Lastly, this class provides a way for me to display the report to the user.


import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;

import java.util.Collections;
import java.util.Scanner;

public class DailyPlan {

    private Date workDate;
    private int numCars;
    private int numTrucks;
    private int assignedCars = 0;
    private int assignedTrucks = 0;
    private boolean allValid = true; //when checking if license is exp, change to true if found exp
    private boolean fulfilled = false; //add section to check if order is fulfilled

    private ArrayList<Driver> drivers;
    private ArrayList<Vehicle> vehicles;
    private StringBuilder dRejects; //could use stringBuilder object, if licenseExp you can append driver to StringBuilder
    private StringBuilder vRejects;

    public DailyPlan(String filePath) {
        drivers = new ArrayList<>(0);
        vehicles = new ArrayList<>(0);
        vRejects = new StringBuilder();
        dRejects = new StringBuilder();
        processFile(readFile(filePath));
    }

    public static Scanner readFile(String filePath) {
        Scanner inputStream = null;
        try {
            inputStream = new Scanner(new FileInputStream(filePath));
        } catch (FileNotFoundException e) {
            System.out.println("File not found or could not be opened.");
            System.exit(0);
        }
        return inputStream;
    }

    public void processFile(Scanner inputStream) {
        String firstLine = inputStream.nextLine();
        workDate = readDate(firstLine);
        String secondLine = inputStream.nextLine();
        String[] vehicleCount = secondLine.split(",");
        numCars = Integer.parseInt(vehicleCount[0]);
        numTrucks = Integer.parseInt(vehicleCount[1]);

        int numDrivers = inputStream.nextInt();
        inputStream.nextLine();
        for (int i = 0; i < numDrivers; i++) {
            readDriver(inputStream.nextLine());
        }

        int numVehicles = inputStream.nextInt();
        inputStream.nextLine();
        for(int j = 0; j < numVehicles; j++) {
            readVehicles(inputStream.nextLine());
        }

        Collections.sort(drivers);

        //vehicle assignments:
        while (!drivers.isEmpty() && !checkFulfilled() && (assignedTrucks + assignedCars < vehicles.size())) {
            for (Vehicle v: vehicles) {
                if (v instanceof Truck) {
                    for (Driver d: drivers) {
                        if (d.hasCDL()) {
                            v.setDriver(d);
                            assignedTrucks++;
                            drivers.remove(d);
                            break;
                        }
                    }
                } else {
                    if (v instanceof Car){
                        for (Driver d: drivers) {
                            v.setDriver(d);
                            assignedCars++;
                            drivers.remove(d);
                            break;
                        }
                    }
                }
            }
        }

        checkFulfilled();

        if (!drivers.isEmpty()) {
            seniorityCheck();
        }
    }

    public void readDriver(String line) {
        String[] driverInfo = line.split(",");
        Date hireDate = readDate(driverInfo[1]);
        Date licenseExp = readDate(driverInfo[3]);
        boolean hasCDL = true;
        if (driverInfo[4].equals("false")) {
            hasCDL = false;
        }
        Driver newDriver = new Driver (driverInfo[0], hireDate, driverInfo[2], licenseExp, hasCDL);

        int m = 10;
        int d = 10;
        int y = 2024;
        Date today = new Date(m, d, y);
        if (newDriver.getLicenseExp().precedes(today)){
            allValid = false;
            dRejects.append("Driver " + newDriver.getName() + " has an expired license \n");
        } else {
            drivers.add(newDriver);
        }
    }

    public void readVehicles(String line) {
        String[] vehicleInfo = line.split(",");
        if (vehicleInfo.length == 4) {
            int numPassengers = Integer.parseInt(vehicleInfo[1]);
            int mileage = Integer.parseInt(vehicleInfo[2]);
            double emission = Double.parseDouble(vehicleInfo[3]);
            Car newCar = new Car(vehicleInfo[0], numPassengers, mileage, emission);
            if (!newCar.isStreetLegal()) {
                vRejects.append("" + newCar.getCarLP() + " exceeds the tailpipe emission standard\n");
            } else {
                vehicles.add(newCar);
            }
        } else {
            double loadCap = Double.parseDouble(vehicleInfo[1]);
            int towingCap = Integer.parseInt(vehicleInfo[2]);
            int mileage = Integer.parseInt(vehicleInfo[3]);
            double emission = Double.parseDouble(vehicleInfo[4]);
            Truck newTruck = new Truck(vehicleInfo[0], loadCap, towingCap, mileage, emission);
            if (!newTruck.isStreetLegal()) {
                vRejects.append("" + newTruck.getTruckLP() + " exceeds the tailpipe emission standard\n");
            } else {
                vehicles.add(newTruck);
            }
        }
    }

    public Date readDate(String preDate) {
        String[] dateLine = preDate.split("/");
        return new Date(Integer.parseInt(dateLine[0]), Integer.parseInt(dateLine[1]), Integer.parseInt(dateLine[2]));
    }

    public boolean checkFulfilled() {
        if (numCars == assignedCars && numTrucks == assignedTrucks) {
            fulfilled = true;
        }
        return fulfilled;
    }

    //fix this to use date class and hiredate
    //after assignments have been made
    //identify seniormost unassigned CDL driver and most senior unassigned non CDL driver
    //create driver objects for both of these
    //may want to add a boolean for if driver is assigned or not -- could do this and then dont remove from list, just check if assigned first
    //look at all drivers assigned w/ cdls, are they all more senior than the "most senior unassigned cdl driver" (if not return false)
    //look at all drivers assigned w/out cdls, are they all more senior than the "most senior unassigned cdl driver" (if not return false)
    //look at all drivers assigned w/out cdls, are they all more senior than the "most senior unassigned non cdl driver" (if not return false)
    public boolean seniorityCheck() {
        boolean seniorityCheck = true;
        Driver seniorCDL = null;
        Driver seniorNonCDL = null;
        int count = 0;
        if (drivers.size() == 1) {
            if (drivers.get(0).hasCDL()) {
                seniorCDL = drivers.get(0);
            } else {
                seniorNonCDL = drivers.get(0);
            }
        } else {
            while (count < 2) {
                for (Driver d: drivers) {
                    if (d.hasCDL()) {
                        seniorCDL = d;
                        count++;
                    } else {
                        seniorNonCDL = d;
                        count++;
                    }
                }
            }
        }

        for (Vehicle v: vehicles) {
            if (v instanceof Truck) {
                if (v.getDriver() != null) {
                    if (seniorCDL != null) {
                        if (seniorCDL.getHireDate().precedes(v.getDriver().getHireDate())) {
                            seniorityCheck = false;
                        }
                    }
                }
            } else if (v instanceof Car) {
                if (v.getDriver() != null) {
                    if (seniorNonCDL != null) {
                        if (seniorNonCDL.getHireDate().precedes(v.getDriver().getHireDate())) {
                            seniorityCheck = false;
                        }
                    }
                }
            }
        }

        return seniorityCheck; //return boolean but should always be true if working properly
    }

    public String toString() {
        StringBuilder plan = new StringBuilder("" + workDate + "\n");
        plan.append("Cars requested: " + numCars);
        plan.append(", Trucks requested: " + numTrucks);
        if (fulfilled) {
            plan.append("\nOrder fulfilled");
        } else {
            plan.append("\nOrder not fulfilled:");
            plan.append(checkIssues());
        }
        if (seniorityCheck()) {
            plan.append("\nSeniority check passed");
        } else {
            plan.append("\nSeniority check not passed");
        }
        return plan.toString();
    }

    public String checkIssues() {
        StringBuilder issues = new StringBuilder();
        int carCount = 0;
        int truckCount =0;
        for (Vehicle v: vehicles) {
            if (v instanceof Car) {
                carCount++;
            } else {
                truckCount++;
            }
        }
        if (numCars > carCount) {
            issues.append("\n\t- not enough street legal cars");
        }
        if (numTrucks > truckCount) {
            issues.append("\n\t- not enough street legal trucks");
        }
        /*if (!drivers.isEmpty() && truckCount > 0) {
            issues.append("\n\t- not enough drivers with CDLs");
        }*/
        if (dRejects.length() != 0) {
            issues.append("\n\t- not enough drivers with valid licenses");
        }
        return issues.toString();
    }

    public void getDetails() {
        for (Vehicle v: vehicles) {
            if (v instanceof Car) {
                if (v.getDriver() != null) {
                    System.out.print(((Car) v).getCarLP() + " assigned to " + v.getDriver().getName() + "\n");
                }
            } else if (v instanceof Truck) {
                if (v.getDriver() != null) {
                    System.out.print(((Truck) v).getTruckLP() + " assigned to " + v.getDriver().getName() + "\n");
                }
            }
        }
        System.out.println(vRejects.toString());
        if (allValid) {
            System.out.println("All drivers have valid licenses.");
        } else {
            System.out.print(dRejects.toString());
        }
        System.out.println();
    }

    public ArrayList<Driver> getDrivers() {
        return drivers;
    }

    public ArrayList<Vehicle> getVehicles() {
        return vehicles;
    }

    public String getDRejects() {
        return dRejects.toString();
    }

    public String getVRejects() {
        return vRejects.toString();
    }
}
