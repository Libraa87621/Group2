package USER.product;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.duan1.R;
import java.util.ArrayList;

import Database.DBHelper;

public class CartActivity extends AppCompatActivity {

    private RecyclerView recyclerCart;
    private TextView tvTotalPrice;
    private Button btnPlaceOrder;
    private ArrayList<Product> cartProducts;  // List of products in the cart
    private CartAdapter cartAdapter;
    private DBHelper dbHelper;  // DBHelper instance

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        // Initialize DBHelper
        dbHelper = new DBHelper(this);

        // Initialize views
        recyclerCart = findViewById(R.id.recyclerCart);
        tvTotalPrice = findViewById(R.id.tvTotalPrice);
        btnPlaceOrder = findViewById(R.id.btnPlaceOrder);
        ImageButton btnBackToProduct = findViewById(R.id.btnBackToProduct);

        // Get cart items from the database
        cartProducts = dbHelper.getCartItems();
        if (cartProducts == null) {
            cartProducts = new ArrayList<>();
        }

        // Set up the RecyclerView with CartAdapter
        cartAdapter = new CartAdapter(cartProducts);
        recyclerCart.setLayoutManager(new LinearLayoutManager(this));
        recyclerCart.setAdapter(cartAdapter);

        // Update the total price
        updateTotalPrice();

        // Go back to product page
        btnBackToProduct.setOnClickListener(v -> finish());

        // Handle placing the order
        btnPlaceOrder.setOnClickListener(v -> {
            // Add order placement logic here if needed
        });
    }

    private void updateTotalPrice() {
        float total = 0;
        for (Product product : cartProducts) {
            total += product.getPrice() * product.getQuantity();  // Multiply price by quantity
        }
        tvTotalPrice.setText("Tổng: " + formatPrice(total) + "đ");
    }

    private String formatPrice(float price) {
        return String.format("%,.0f", price);  // Format price with commas
    }
}
