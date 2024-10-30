//Alana Henden, Project1, CST338, 10/11/24
//Person.java
//This class holds my Person object information (name, hireDate)
// and has a derived class called Driver.


public class Person {

    private String name;
    private Date hireDate;

    public Person(String name, Date hireDate) {
        this.name = name;
        this.hireDate = hireDate;
    }

    public Date getHireDate() {
        return hireDate;
    }

    public String getName() {
        return name;
    }


}
