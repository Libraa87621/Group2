package ADMIN.fragment.OrdersFragment;

public class Orders {
    private String name;
    private String price;
    private String description;
    private int imageResourceId;

    // Constructor đầy đủ các thuộc tính
    public Orders(String name, String price, String description, int imageResourceId) {
        this.name = name;
        this.price = price;
        this.description = description;
        this.imageResourceId = imageResourceId;
    }

    // Constructor với tên và giá tiền (có thể bổ sung mô tả và ID hình ảnh sau này)
    public Orders(String name, String price) {
        this.name = name;
        this.price = price;
        this.description = ""; // Mặc định mô tả là rỗng
        this.imageResourceId = 0; // Mặc định hình ảnh là 0
    }

    // Getter và Setter cho các thuộc tính
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    // Phương thức trả về ID hình ảnh
    public int getImageId() {
        return imageResourceId;
    }

    // Phương thức trả về ID hình ảnh (tên cũ)
    public int getImageResourceId() {
        return imageResourceId;
    }

    public void setImageResourceId(int imageResourceId) {
        this.imageResourceId = imageResourceId;
    }
}
