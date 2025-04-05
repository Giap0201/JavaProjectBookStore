package service;

import dao.CategoryDAO;
import model.Category;

import java.util.ArrayList;

public class CategoryService {
   private CategoryDAO dao;
   public CategoryService(){
       this.dao = new CategoryDAO();
   }
   public CategoryService(CategoryDAO dao){
       this.dao = dao;
   }
   public ArrayList<Category> getCategory() {
       return dao.listCategory();
   }
}
