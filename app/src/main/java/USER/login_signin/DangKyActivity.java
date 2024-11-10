package USER.login_signin;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.duan1.R;

public class DangKyActivity extends AppCompatActivity {

    private EditText etLastName, etFirstName, etEmail, etBirthdate, etPassword, etConfirmPassword;
    private Button btnRegister;
    private ImageButton btnMenu, btnProfile, btnCart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dang_ky);

        // Khởi tạo các thành phần giao diện
        btnMenu = findViewById(R.id.btnimg_menu);
        btnProfile = findViewById(R.id.btnimg_profile);
        btnCart = findViewById(R.id.btnimg_cart);

        etLastName = findViewById(R.id.etLastName);
        etFirstName = findViewById(R.id.etFirstName);
        etEmail = findViewById(R.id.etEmail);
        etBirthdate = findViewById(R.id.etBirthdate);
        etPassword = findViewById(R.id.etPassword);
        etConfirmPassword = findViewById(R.id.etConfirmPassword);

        btnRegister = findViewById(R.id.btnRegister);

        // Gọi các phương thức để xử lý sự kiện
        setupEventHandlers();
    }

    private void setupEventHandlers() {
        // Xử lý khi nhấn nút "Menu"
        btnMenu.setOnClickListener(v -> {
            // Mã xử lý cho menu
            Toast.makeText(this, "Menu clicked", Toast.LENGTH_SHORT).show();
        });

        // Xử lý khi nhấn nút "Profile"
        btnProfile.setOnClickListener(v -> {
            // Mã xử lý cho profile
            Toast.makeText(this, "Profile clicked", Toast.LENGTH_SHORT).show();
        });

        // Xử lý khi nhấn nút "Cart"
        btnCart.setOnClickListener(v -> {
            // Mã xử lý cho cart
            Toast.makeText(this, "Cart clicked", Toast.LENGTH_SHORT).show();
        });

        // Xử lý khi nhấn nút "Tạo tài khoản"
        btnRegister.setOnClickListener(v -> {
            registerUser();
        });
    }

    private void registerUser() {
        String lastName = etLastName.getText().toString().trim();
        String firstName = etFirstName.getText().toString().trim();
        String email = etEmail.getText().toString().trim();
        String birthdate = etBirthdate.getText().toString().trim();
        String password = etPassword.getText().toString().trim();
        String confirmPassword = etConfirmPassword.getText().toString().trim();

        // Kiểm tra tính hợp lệ
        if (lastName.isEmpty() || firstName.isEmpty() || email.isEmpty() || birthdate.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            Toast.makeText(this, "Vui lòng điền đầy đủ thông tin", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!password.equals(confirmPassword)) {
            Toast.makeText(this, "Mật khẩu không khớp", Toast.LENGTH_SHORT).show();
            return;
        }

        // Thực hiện đăng ký tài khoản (có thể thêm logic để gửi thông tin lên server tại đây)
        Toast.makeText(this, "Đăng ký thành công!", Toast.LENGTH_SHORT).show();

        // Xóa thông tin sau khi đăng ký
        clearForm();
    }

    private void clearForm() {
        etLastName.setText("");
        etFirstName.setText("");
        etEmail.setText("");
        etBirthdate.setText("");
        etPassword.setText("");
        etConfirmPassword.setText("");
    }
}
