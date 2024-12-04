package USER.product;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.duan1.R;

import USER.Home.HomeActivity;
import USER.choosefood.choosefoodActivity;

public class productActivity extends AppCompatActivity {

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

        // Handle direct order button click
        btnOrder.setOnClickListener(v -> {
            Intent intent = new Intent(productActivity.this, choosefoodActivity.class);
            startActivity(intent);
        });

        // Handle add to cart button click
        btnCart.setOnClickListener(v -> {
            // Save the product to SharedPreferences
            SharedPreferences sharedPreferences = getSharedPreferences("CartPrefs", MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("cart_item_name", productName);
            editor.putInt("cart_item_image", imageResId);
            editor.apply();

            Toast.makeText(productActivity.this, "Bạn đã thêm thành công sản phẩm vào giỏ hàng", Toast.LENGTH_SHORT).show();
        });

        // Handle back button click
        btnquayve.setOnClickListener(v -> {
            Intent intent = new Intent(productActivity.this, HomeActivity.class);
            startActivity(intent);
        });
    }
}
