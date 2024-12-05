package USER.product;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.duan1.R;

import java.util.ArrayList;

import USER.Home.HomeActivity;

public class productActivity extends AppCompatActivity {

    private ArrayList<Cart> cartList = new ArrayList<>(); // Danh sách giỏ hàng

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);

        // Define views
        ImageView imghome = findViewById(R.id.imghome);
        Button btnOrder = findViewById(R.id.btnOrder); // Direct Order Button
        Button btnCart = findViewById(R.id.btnCart); // Add to Cart Button
        ImageButton btnquayve = findViewById(R.id.btnquayve);

        // Get product details from the intent
        String productName = getIntent().getStringExtra("product_name");
        int imageResId = getIntent().getIntExtra("image_resource", -1);

        // Set the image resource if valid
        if (imageResId != -1) {
            imghome.setImageResource(imageResId);
        }

        // Handle add to cart button click
        btnCart.setOnClickListener(v -> {
            // Add product to cart list
            Cart cartItem = new Cart(productName, imageResId);
            cartList.add(cartItem);

            Toast.makeText(productActivity.this, "Bạn đã thêm thành công sản phẩm vào giỏ hàng", Toast.LENGTH_SHORT).show();
        });

        // Handle view cart button click
        btnOrder.setOnClickListener(v -> {
            Intent intent = new Intent(productActivity.this, CartActivity.class);
            intent.putExtra("cart_list", cartList); // Truyền danh sách giỏ hàng
            startActivity(intent);
        });

        // Handle back button click
        btnquayve.setOnClickListener(v -> {
            Intent intent = new Intent(productActivity.this, HomeActivity.class);
            startActivity(intent);
        });
    }
}
