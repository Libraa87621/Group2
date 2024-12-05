    package USER.product;

    import android.os.Bundle;
    import android.widget.TextView;
    import android.widget.ImageView;

    import androidx.appcompat.app.AppCompatActivity;

    import com.example.duan1.R;

    import USER.product.Cart;

    public class CartActivity extends AppCompatActivity {

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_cart);

            // Retrieve the Cart object passed from productActivity
            Cart cart = (Cart) getIntent().getSerializableExtra("cart_item");

            if (cart != null) {
                String productName = cart.getProductName();
                int productImage = cart.getProductImage();

                // Display product details in CartActivity
                TextView txtProductName = findViewById(R.id.txtProductName);
                ImageView imgProduct = findViewById(R.id.imgProduct);

                txtProductName.setText(productName);
                imgProduct.setImageResource(productImage);
            }
        }
    }
