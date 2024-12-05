package USER.product;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.duan1.R;

import java.util.ArrayList;

import USER.Home.HomeActivity;
import USER.choosefood.choosefoodActivity;
import Database.DBHelper;  // Đảm bảo bạn import đúng lớp DBHelper

public class product_monchinh extends AppCompatActivity {

    private DBHelper dbHelper;  // Khai báo DBHelper thay vì SQLiteHelper
    private ArrayList<Product> cartList = new ArrayList<>(); // Danh sách giỏ hàng

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);

        // Khởi tạo đối tượng dbHelper
        dbHelper = new DBHelper(this);

        // Ánh xạ các view
        TextView tvProductName = findViewById(R.id.tvProductName);
        TextView tvProductPrice = findViewById(R.id.tvProductPrice);
        ImageView imgProduct = findViewById(R.id.imgProduct);
        Button btnOrder = findViewById(R.id.btnOrder);
        Button btnCart = findViewById(R.id.btnCart);
        ImageButton btnquayve = findViewById(R.id.btnquayve);

        // Lấy thông tin từ layout (có thể dùng nếu cần truyền động)
        String productName = tvProductName.getText().toString();
        String productPriceText = tvProductPrice.getText().toString();
        // Convert price to float by removing non-numeric characters
        float productPrice = Float.parseFloat(productPriceText.replace(",", "").replace("đ", "").trim());

        // Set the product image URL (as a String)
        String productImageUrl = "https://example.com/path_to_image.jpg";  // Example URL or local path

        // Tạo đối tượng Product từ thông tin UI
        Product product = new Product(productName, productPrice, productImageUrl, 1);  // Product constructor updated

        // Thêm sản phẩm vào giỏ hàng khi nhấn nút
        btnCart.setOnClickListener(v -> {
            dbHelper.addProductToCart(product);  // Add product to the database
            Toast.makeText(this, "Sản phẩm đã được thêm vào giỏ hàng", Toast.LENGTH_SHORT).show(); // Show toast message
        });


        // Chuyển tới màn hình đặt hàng
        btnOrder.setOnClickListener(v -> {
            cartList.add(product);
            Intent intent = new Intent(product_monchinh.this, choosefoodActivity.class);
            intent.putParcelableArrayListExtra("cart_list", cartList); // Truyền danh sách giỏ hàng
            startActivity(intent);
        });

        // Quay về trang Home
        btnquayve.setOnClickListener(v -> {
            Intent intent = new Intent(product_monchinh.this, HomeActivity.class);
            startActivity(intent);
        });
    }
}
