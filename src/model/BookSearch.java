package model;

public class BookSearch {
    private String bookID,categoryName,bookName,author;
    private Integer yearStart,yearEnd;
    private Double priceMin,priceMax;
    public BookSearch() {
    }
    public BookSearch(String bookID, String categoryName, String bookName, String author, Integer yearStart,
            Integer yearEnd, Double priceMin, Double priceMax) {
        this.bookID = bookID;
        this.categoryName = categoryName;
        this.bookName = bookName;
        this.author = author;
        this.yearStart = yearStart;
        this.yearEnd = yearEnd;
        this.priceMin = priceMin;
        this.priceMax = priceMax;
    }
    public String getBookID() {
        return bookID;
    }
    public void setBookID(String bookID) {
        this.bookID = bookID;
    }
    public String getCategoryName() {
        return categoryName;
    }
    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
    public String getBookName() {
        return bookName;
    }
    public void setBookName(String bookName) {
        this.bookName = bookName;
    }
    public String getAuthor() {
        return author;
    }
    public void setAuthor(String author) {
        this.author = author;
    }
    public Integer getYearStart() {
        return yearStart;
    }
    public void setYearStart(Integer yearStart) {
        this.yearStart = yearStart;
    }
    public Integer getYearEnd() {
        return yearEnd;
    }
    public void setYearEnd(Integer yearEnd) {
        this.yearEnd = yearEnd;
    }
    public Double getPriceMin() {
        return priceMin;
    }
    public void setPriceMin(Double priceMin) {
        this.priceMin = priceMin;
    }
    public Double getPriceMax() {
        return priceMax;
    }
    public void setPriceMax(Double priceMax) {
        this.priceMax = priceMax;
    }
    @Override
    public String toString() {
        return "BookSearch [bookID=" + bookID + ", categoryName=" + categoryName + ", bookName=" + bookName
                + ", author=" + author + ", yearStart=" + yearStart + ", yearEnd=" + yearEnd + ", priceMin=" + priceMin
                + ", priceMax=" + priceMax + "]";
    }
    
}
