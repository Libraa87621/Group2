package USER.Home;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.PopupMenu;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.duan1.R;

import USER.Payment.PaymentSuccessActivity;
import USER.login_signin.DangNhapActivity;
import USER.product.CartActivity;
import USER.product.Product;
import USER.product.productActivity;

public class HomeActivity extends AppCompatActivity {

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_home);

        // Tham chiếu đến img1 và img2
        findViewById(R.id.img1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, productActivity.class);
                intent.putExtra("image_resource", R.drawable.monchinh); // Pass img1 resource ID
                startActivity(intent);
            }
        });

        findViewById(R.id.img2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, Product.class );
                intent.putExtra("image_resource", R.drawable.monphu); // Pass img2 resource ID
                startActivity(intent);
            }
        });

        findViewById(R.id.tv1).setOnClickListener(v -> {
            Intent intent = new Intent(HomeActivity.this, productActivity.class);
            startActivity(intent);
        findViewById(R.id.tv1).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // Chuyển sang ProductActivity khi nhấn vào img1
                Intent intent = new Intent(HomeActivity.this, productActivity.class);
                startActivity(intent);
            }
        });

        findViewById(R.id.tv2).setOnClickListener(v -> {
            Intent intent = new Intent(HomeActivity.this, productActivity.class);
            startActivity(intent);
        });

        // Tham chiếu đến ImageButton
        ImageButton menuButton = findViewById(R.id.menu_button);
        menuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Tạo PopupMenu
                PopupMenu popupMenu = new PopupMenu(HomeActivity.this, v);
                popupMenu.getMenuInflater().inflate(R.menu.drawer_menu, popupMenu.getMenu());

                // Xử lý các mục menu
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        if (item.getItemId() == R.id.nav_home) {
                            // Xử lý sự kiện khi chọn 'Trang chủ'
                            return true;
                        } else if (item.getItemId() == R.id.nav_cart) {
                            // Xử lý sự kiện khi chọn "Giỏ hàng"
                            Intent intent = new Intent(HomeActivity.this, CartActivity.class);
                            startActivity(intent);
                            return true;
                        } else if (item.getItemId() == R.id.nav_logout) {
                            // Đăng xuất
                            SharedPreferences preferences = getSharedPreferences("UserPreferences", MODE_PRIVATE);
                            SharedPreferences.Editor editor = preferences.edit();
                            editor.clear(); // Xóa dữ liệu đăng nhập
                            editor.apply();

                            Intent intent = new Intent(HomeActivity.this, DangNhapActivity.class);
                            startActivity(intent);
                            finish(); // Kết thúc hoạt động hiện tại
                            return true;
                        } else {
                            return false;
                        }
                    }
                });

                // Hiển thị menu
                popupMenu.show();
                // Chuyển sang ProductActivity khi nhấn vào img1
                Intent intent = new Intent(HomeActivity.this, Product.class);
                startActivity(intent);
            }
        });
    }
}
