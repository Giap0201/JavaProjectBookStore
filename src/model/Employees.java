package model;

import java.util.Date;

public class Employees extends Person{
    private String employeeID;
    private double salary;
    private String position ;

    public Employees() {

    }

    public Employees(String firstName, String lastName, String gender, Date dateOfBirth, String phoneNumber, String email, String note, String employeeID, double salary, String position) {
        super(firstName, lastName, gender, dateOfBirth, phoneNumber, email, note);
        this.employeeID = employeeID;
        this.salary = salary;
        this.position = position;
    }

    public String getEmployeeID() {
        return employeeID;
    }

    public void setEmployeeID(String employeeID) {
        this.employeeID = employeeID;
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
