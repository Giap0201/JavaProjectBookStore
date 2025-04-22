package model;

public class OrderDetails {
    private String orderId;
    private float discount; // % giảm giá cho sản phẩm này
    private Books book;
    private int quantity;
    private double total; // Thành tiền cho dòng này


    public OrderDetails() {
    }

    // Constructor này có thể không cần thiết nếu bạn tạo object từ table
    public OrderDetails(String orderId, float discount, Books book, int quantity) {
        this.orderId = orderId;
        this.discount = discount;
        this.book = book;
        this.quantity = quantity;
        calculateTotal();
    }

    // Hàm tính toán thành tiền cho dòng này
    public double calculateTotal() {
        if (book != null && quantity > 0) {
            double price = book.getPrice();
            // Áp dụng giảm giá (nếu có)
            double effectivePrice = price * (1 - (double) discount / 100.0);
            this.total = effectivePrice * quantity;
        } else {
            this.total = 0;
        }
        return this.total;
    }

    // --- Getters and Setters ---

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public float getDiscount() {
        return discount;
    }

    public void setDiscount(float discount) {
        this.discount = discount;
        calculateTotal(); // Tính lại tổng khi giảm giá thay đổi
    }

    public Books getBook() {
        return book;
    }

    public void setBook(Books book) {
        this.book = book;
        calculateTotal(); // Tính lại tổng khi sách thay đổi
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        if (quantity >= 0) { // Đảm bảo số lượng không âm
            this.quantity = quantity;
            calculateTotal(); // Tính lại tổng khi số lượng thay đổi
        } else {
            this.quantity = 0;
            calculateTotal();
        }
    }

    // Getter cho total (không cần setter vì nó được tính toán)
    public double getTotal() {
        // Đảm bảo tính toán lại nếu các thành phần thay đổi mà không qua setter
        calculateTotal();
        return total;
    }

    // Setter này không nên dùng trực tiếp, total nên được tính toán
    // public void setTotal(double total) { this.total = total; }

    // Phương thức tiện ích để lấy dữ liệu cho bảng
    public Object[] toTableRow() {
        if (book == null) return new Object[6]; // Trả về mảng rỗng nếu không có sách
        return new Object[]{
                book.getBookID(),
                book.getBookName(),
                quantity,
                book.getPrice(),
                discount, // % giảm giá item
                getTotal() // Thành tiền item
        };
    }
}
