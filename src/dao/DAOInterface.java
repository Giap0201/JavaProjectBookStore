package dao;

import java.util.ArrayList;

public interface DAOInterface<T> {
    public int insert(T t);
    public int update(T t);
    public int delete(T t);
    public ArrayList<T> getAll();
    public T selectbyId(T t);
    public ArrayList<T> selectbyCondition(String condition);
}
