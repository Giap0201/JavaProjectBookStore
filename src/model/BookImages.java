package model;

public class BookImages {
    private String imageId ;
    private String imageName ;
    private String urlImage ;
    private String bookId;

    public BookImages() {}

    public BookImages(String imageId, String imageName, String urlImage, String bookId) {
        this.imageId = imageId;
        this.imageName = imageName;
        this.urlImage = urlImage;
        this.bookId = bookId;
    }

    public String getImageId() {
        return imageId;
    }

    public void setImageId(String imageId) {
        this.imageId = imageId;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public String getUrlImage() {
        return urlImage;
    }

    public void setUrlImage(String urlImage) {
        this.urlImage = urlImage;
    }

    public String getBookId() {
        return bookId;
    }

    public void setBookId(String bookId) {
        this.bookId = bookId;
    }
}
