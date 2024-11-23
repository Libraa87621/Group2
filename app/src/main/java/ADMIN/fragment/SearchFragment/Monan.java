package ADMIN.fragment.SearchFragment;

public class Monan {
    private String name;
    private int quantity;
    private String image;  // Thêm thuộc tính hình ảnh

    // Constructor
    public Monan(String name, int quantity, String image) {
        this.name = name;
        this.quantity = quantity;
        this.image = image;
    }

    // Getter cho tên món ăn
    public String getName() {
        return name;
    }

    // Getter cho số lượng
    public int getQuantity() {
        return quantity;
    }

    // Getter cho hình ảnh
    public String getImage() {
        return image;
    }
}
