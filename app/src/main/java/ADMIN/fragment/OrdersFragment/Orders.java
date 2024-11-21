package ADMIN.fragment.OrdersFragment;

public class Orders {
    private String name;
    private String price;
    private String description;
    private String imageUrl; // Thêm thuộc tính URL hình ảnh

    // Constructor 1: Khởi tạo đầy đủ các thuộc tính
    public Orders(String name, String price, String description, String imageUrl) {
        this.name = name;
        this.price = price;
        this.description = description;
        this.imageUrl = imageUrl;
    }

    // Constructor 2: Khởi tạo với tên và giá tiền (có thể bổ sung mô tả và URL sau này)
    public Orders(String name, String price) {
        this.name = name;
        this.price = price;
        this.description = ""; // Giá trị mặc định là rỗng nếu không có mô tả
        this.imageUrl = ""; // Giá trị mặc định là rỗng nếu không có URL ảnh
    }

    // Getter cho name
    public String getName() {
        return name;
    }

    // Getter cho price
    public String getPrice() {
        return price;
    }

    // Getter cho description
    public String getDescription() {
        return description;
    }

    // Getter cho imageUrl
    public String getImageUrl() {
        return imageUrl;
    }
}