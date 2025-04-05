package controller;

import model.Category;
import service.CategoryService;

import java.util.ArrayList;

public class CategoryController {
    private CategoryService service;
    public CategoryController(){
        this.service = new CategoryService();
    }
    public CategoryController(CategoryService service) {
        this.service = service;
    }
    public ArrayList<Category> getCategories(){
        return service.getCategory();
    }
}
