package USER.product;
import java.io.Serializable;

public class Cart implements Serializable {
    private String productName;
    private int productImage; // This holds the image resource ID

    // Constructor
    public Cart(String productName, int productImage) {
        this.productName = productName;
        this.productImage = productImage;
    }

    // Getter for product image resource
    public int getProductImage() {
        return productImage;
    }

    // Getter for product name
    public String getProductName() {
        return productName;
    }

    // Setter for product name
    public void setProductName(String productName) {
        this.productName = productName;
    }

    // Setter for product image
    public void setProductImage(int productImage) {
        this.productImage = productImage;
    }
}
