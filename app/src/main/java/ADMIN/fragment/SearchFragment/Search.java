package ADMIN.fragment.SearchFragment;

public class Search {
    private String name;
    private int quantity;
    private int imageResourceId;

    public Search(String name, int quantity, int imageResourceId) {
        this.name = name;
        this.quantity = quantity;
        this.imageResourceId = imageResourceId;
    }

    public String getName() {
        return name;
    }

    public int getQuantity() {
        return quantity;
    }

    public int getImageResourceId() {
        return imageResourceId;
    }
}