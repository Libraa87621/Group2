package USER.Home;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.PopupMenu;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.duan1.R;

import USER.login_signin.DangNhapActivity;
import USER.product.CartActivity;
import USER.product.Product_monphu;
import USER.product.product_monchinh;

public class HomeActivity extends AppCompatActivity {

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_home);

        // Tham chiếu đến img1 và img2
        findViewById(R.id.img1).setOnClickListener(v -> {
            Intent intent = new Intent(HomeActivity.this, product_monchinh.class);
            intent.putExtra("image_resource", R.drawable.monchinh); // Pass img1 resource ID
            startActivity(intent);
        });

        findViewById(R.id.img2).setOnClickListener(v -> {
            Intent intent = new Intent(HomeActivity.this, Product_monphu.class);
            intent.putExtra("image_resource", R.drawable.monphu); // Pass img2 resource ID
            startActivity(intent);
        });

        // Tham chiếu đến tv1
        findViewById(R.id.tv1).setOnClickListener(v -> {
            Intent intent = new Intent(HomeActivity.this, product_monchinh.class);
            startActivity(intent);
        });

        // Tham chiếu đến tv2
        findViewById(R.id.tv2).setOnClickListener(v -> {
            Intent intent = new Intent(HomeActivity.this, Product_monphu.class);
            startActivity(intent);
        });

        // Tham chiếu đến ImageButton
        ImageButton menuButton = findViewById(R.id.menu_button);
        menuButton.setOnClickListener(v -> {
            // Tạo PopupMenu
            PopupMenu popupMenu = new PopupMenu(HomeActivity.this, v);
            popupMenu.getMenuInflater().inflate(R.menu.drawer_menu, popupMenu.getMenu());

            // Xử lý các mục menu
            popupMenu.setOnMenuItemClickListener(item -> {
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
            });

            // Hiển thị menu
            popupMenu.show();
        });
    }
}