package USER.login_signin;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.duan1.R;

public class DangKyActivity extends AppCompatActivity {

    private EditText etLastName, etFirstName, etEmail, etBirthdate, etPassword, etConfirmPassword;
    private Button btnRegister, btnResign;
    private ImageButton btnMenu, btnProfile, btnCart;
    private ImageView imgGmail, imgFacebook, imgApple;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dang_ky);

        // Initialize UI components
        btnMenu = findViewById(R.id.btnimg_menu);
        btnResign = findViewById(R.id.btn_resign);
        btnProfile = findViewById(R.id.btnimg_profile);
        btnCart = findViewById(R.id.btnimg_cart);

        etLastName = findViewById(R.id.etLastName);
        etFirstName = findViewById(R.id.etFirstName);
        etEmail = findViewById(R.id.etEmail);
        etBirthdate = findViewById(R.id.etBirthdate);
        etPassword = findViewById(R.id.etPassword);
        etConfirmPassword = findViewById(R.id.etConfirmPassword);

        btnRegister = findViewById(R.id.btnRegister);

        imgGmail = findViewById(R.id.imgGmail);
        imgFacebook = findViewById(R.id.img_facebook);
        imgApple = findViewById(R.id.img_apple);

        // Setup event handlers
        setupEventHandlers();
    }

    private void setupEventHandlers() {
        // Handle menu button click
        btnMenu.setOnClickListener(v -> {
            Toast.makeText(this, "Menu clicked", Toast.LENGTH_SHORT).show();
        });

        // Handle profile button click
        btnProfile.setOnClickListener(v -> {
            Toast.makeText(this, "Profile clicked", Toast.LENGTH_SHORT).show();
        });

        // Handle cart button click
        btnCart.setOnClickListener(v -> {
            Toast.makeText(this, "Cart clicked", Toast.LENGTH_SHORT).show();
        });

        // Handle "Đăng Ký" button click
        btnResign.setOnClickListener(v -> {
            Toast.makeText(this, "Đăng Ký button clicked", Toast.LENGTH_SHORT).show();
        });

        // Handle register button click
        btnRegister.setOnClickListener(v -> {
            registerUser();
        });

        // Social media icons click events
        imgGmail.setOnClickListener(v -> {
            Toast.makeText(this, "Gmail clicked", Toast.LENGTH_SHORT).show();
        });

        imgFacebook.setOnClickListener(v -> {
            Toast.makeText(this, "Facebook clicked", Toast.LENGTH_SHORT).show();
        });

        imgApple.setOnClickListener(v -> {
            Toast.makeText(this, "Apple clicked", Toast.LENGTH_SHORT).show();
        });
    }

    private void registerUser() {
        String lastName = etLastName.getText().toString().trim();
        String firstName = etFirstName.getText().toString().trim();
        String email = etEmail.getText().toString().trim();
        String birthdate = etBirthdate.getText().toString().trim();
        String password = etPassword.getText().toString().trim();
        String confirmPassword = etConfirmPassword.getText().toString().trim();

        // Validate form fields
        if (lastName.isEmpty() || firstName.isEmpty() || email.isEmpty() || birthdate.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            Toast.makeText(this, "Vui lòng điền đầy đủ thông tin", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!password.equals(confirmPassword)) {
            Toast.makeText(this, "Mật khẩu không khớp", Toast.LENGTH_SHORT).show();
            return;
        }

        // Registration success message
        Toast.makeText(this, "Đăng ký thành công!", Toast.LENGTH_SHORT).show();

        // Clear form fields
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
