package ADMIN.fragment.OrdersFragment;

public class Orders {
    private String name;
    private String price;
    private String description;
    private int imageResourceId; // Thay vì imageUrl, dùng int để lưu trữ ID tài nguyên hình ảnh

    // Constructor 1: Khởi tạo đầy đủ các thuộc tính
    public Orders(String name, String price, String description, int imageResourceId) {
        this.name = name;
        this.price = price;
        this.description = description;
        this.imageResourceId = imageResourceId;
    }

    // Constructor 2: Khởi tạo với tên và giá tiền (có thể bổ sung mô tả và ID hình ảnh sau này)
    public Orders(String name, String price) {
        this.name = name;
        this.price = price;
        this.description = ""; // Giá trị mặc định là rỗng nếu không có mô tả
        this.imageResourceId = 0; // Giá trị mặc định là 0 nếu không có hình ảnh
    }

    // Getter cho name
    public String getName() {
        return name;
    }

    // Setter cho name
    public void setName(String name) {
        this.name = name;
    }

    // Getter cho price
    public String getPrice() {
        return price;
    }

    // Setter cho price
    public void setPrice(String price) {
        this.price = price;
    }

    // Getter cho description
    public String getDescription() {
        return description;
    }

    // Setter cho description
    public void setDescription(String description) {
        this.description = description;
    }

    // Getter cho imageResourceId
    public int getImageResourceId() {
        return imageResourceId;
    }

    // Setter cho imageResourceId
    public void setImageResourceId(int imageResourceId) {
        this.imageResourceId = imageResourceId;
    }
}
