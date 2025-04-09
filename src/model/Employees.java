
package model;

import java.util.Date;

public class Employees extends Person{
    private String empID;
    private double salary;
    private String position ;

    public Employees(String firstName, String lastName, Date dateOfBirth, String phoneNumber, String email, String gender) {
        super(firstName, lastName, dateOfBirth, phoneNumber, email, gender);
        this.empID = "";
        this.salary = 0.0;
        this.position = "";
    }

    public Employees() {

    }

    public String getEmpID() {
        return empID;
    }

    public void setEmpID(String empID) {
        this.empID = empID;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }
}
