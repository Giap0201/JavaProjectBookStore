
package model;


import java.util.Date;

public class Employees extends Person{
    private String employeeID;
    private String position ;
    private double salary;


    public Employees() {

    }

    public Employees(String employeeID, String position, double salary, String note) {
        this.employeeID = employeeID;
        this.position = position;
        this.salary = salary;
    }

    public Employees(String firstName, String lastName, Date dateOfBirth, String phoneNumber, String email, String gender, String employeeID, String position, double salary) {
        super(firstName, lastName, dateOfBirth, phoneNumber, email, gender);
        this.employeeID = employeeID;
        this.position = position;
        this.salary = salary;
    }

    public String getEmployeeID() {
        return employeeID;
    }

    public void setEmployeeID(String employeeID) {
        this.employeeID = employeeID;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

}
