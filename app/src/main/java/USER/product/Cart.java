package USER.product;

import java.io.Serializable;

public class Cart implements Serializable {
    private String productName;
    private int productImage;

    public Cart(String productName, int productImage) {
        this.productName = productName;
        this.productImage = productImage;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getProductImage() {
        return productImage;
    }

    public void setProductImage(int productImage) {
        this.productImage = productImage;
    }
}
