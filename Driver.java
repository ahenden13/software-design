//Alana Henden, Project1, CST338, 10/11/24
//Driver.java
//This class is derived from the base Person class to provide information about the Driver objects
//The most important method in this class is the compareTo, which is referenced when the driver arrayList
//is sorted in the DailyPlan class.

public class Driver extends Person implements Comparable<Driver>{

    private Date licenseExp;
    private boolean hasCDL;
    private String dLicense;

    public Driver(String name, Date hireDate, String dLicense, Date licenseExp, boolean hasCDL) {
        super (name, hireDate);
        this.dLicense = dLicense;
        this.licenseExp = licenseExp;
        this.hasCDL = hasCDL;
    }

    public boolean hasCDL() {
        return hasCDL;
    }

    public Date getLicenseExp() {
        return licenseExp;
    }

    @Override
    public int compareTo(Driver driver) {
        if (this.getHireDate().precedes(driver.getHireDate())) {
            return -1; //if driver precedes other driver
        } else if (driver.getHireDate().precedes(this.getHireDate())) {
            return 1;
        } else {
            return 0;
        }
    }


}
