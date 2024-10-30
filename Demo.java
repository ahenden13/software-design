//Alana Henden, Project 1, CST338, 10/11/24
//Demo.java
//This is the demo class to run my project. Simply creates DailyPlan object and prints the report.


public class Demo {
    public static void main(String[] args) {
        DailyPlan w1 = new DailyPlan("resources/workOrder1.txt");
        System.out.println("REPORT");
        System.out.println(w1);
        System.out.println();
        System.out.println("DETAILS");
        w1.getDetails();
        System.out.println("\uD83C\uDF3F Please consider the environment " +
                "before printing this report. \uD83C\uDF3F");
    }
}
