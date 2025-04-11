package dao;

import model.Employees;

import java.util.ArrayList;

public interface InterfaceDAO {
    public int insert(Employees employee);
    public int update(Employees employee);
    public int deleteById(String id);
    public ArrayList<Employees> getAll();
    public Employees getById(int id);
    public Employees selectByID(String id);
}
