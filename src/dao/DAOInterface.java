package dao;

import java.util.ArrayList;
import java.util.List;

public interface DAOInterface<T> {
    public int insert(T t);
    public int update(T t);
    public int delete(T t);
    public List<T> getAll();
    public T selectbyId(T t);
    public ArrayList<T> selectbyCondition(String condition);
}
