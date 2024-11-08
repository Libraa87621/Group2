package USER.choosefood;

public class ChickenItem {
    private String name;
    private int imageResId;
    private int price;  // Thêm trường price để lưu giá của combo

    // Constructor
    public ChickenItem(String name, int imageResId, int price) {
        this.name = name;
        this.imageResId = imageResId;
        this.price = price;
    }

    // Getter và Setter
    public String getName() {
        return name;
    }

    public int getImageResId() {
        return imageResId;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
