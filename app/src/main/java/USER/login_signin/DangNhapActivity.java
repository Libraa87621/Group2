package USER.login_signin;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.InputType;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import com.example.duan1.R;

import ADMIN.fragment.HomeFragment.fragmentActivity;
import USER.Home.HomeActivity;

public class DangNhapActivity extends AppCompatActivity {

    private EditText editTextEmail, editTextPassword;
    private Button buttonLogin, buttonCreateAccount;
    private TextView textViewForgotPassword;
    private boolean isPasswordVisible = false; // Track password visibility

    // Tài khoản mặc định của admin
    private final String ADMIN_EMAIL = "admin";
    private final String ADMIN_PASSWORD = "admin123";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dang_nhap2);

        // Initialize views
        ImageButton btnimgProfile = findViewById(R.id.btnimg_profile);
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPassword = findViewById(R.id.editTextPassword);
        buttonLogin = findViewById(R.id.buttonLogin);
        buttonCreateAccount = findViewById(R.id.buttonCreateAccount);
        textViewForgotPassword = findViewById(R.id.textViewForgotPassword); // Initialize forgot password
        Intent intent = getIntent();
        String passedEmail = intent.getStringExtra("email");
        String passedPassword = intent.getStringExtra("password");
        // Lấy thông tin tài khoản từ SharedPreferences (nếu có)
        SharedPreferences preferences = getSharedPreferences("AppData", MODE_PRIVATE);
        String savedEmail = preferences.getString("email", null);
        String savedPassword = preferences.getString("password", null);

        // Nếu có thông tin tài khoản đã lưu, hiển thị lại trong các trường EditText
        if (savedEmail != null) {
            editTextEmail.setText(savedEmail);
        }
        if (savedPassword != null) {
            editTextPassword.setText(savedPassword);
        }
        // If email and password were passed, set them in the EditText fields
        if (passedEmail != null) {
            editTextEmail.setText(passedEmail);
        }
        if (passedPassword != null) {
            editTextPassword.setText(passedPassword);
        }
        // Set up listeners
        buttonLogin.setOnClickListener(v -> login());
        buttonCreateAccount.setOnClickListener(v -> createAccount());
        textViewForgotPassword.setOnClickListener(v -> forgotPassword());

        editTextPassword.setOnTouchListener((v, event) -> {
            if (event.getAction() == MotionEvent.ACTION_UP) {
                int drawableEnd = editTextPassword.getCompoundDrawables()[2] != null ? 2 : -1;
                if (drawableEnd >= 0 && event.getRawX() >= (editTextPassword.getRight() - editTextPassword.getCompoundDrawables()[drawableEnd].getBounds().width())) {
                    togglePasswordVisibility();
                    return true;
                }
            }
            return false;
        });
        btnimgProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Điền thông tin admin vào các EditText
                editTextEmail.setText("admin");
                editTextPassword.setText("admin123");
            }
        });
    }

    private void togglePasswordVisibility() {
        if (isPasswordVisible) {
            // Hide password
            editTextPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
            Drawable eyeIcon = ContextCompat.getDrawable(this, R.drawable.eye_icon); // Replace with "closed eye" icon if desired
            editTextPassword.setCompoundDrawablesWithIntrinsicBounds(null, null, eyeIcon, null);
        } else {
            // Show password
            editTextPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
            Drawable eyeIcon = ContextCompat.getDrawable(this, R.drawable.eye_icon); // Replace with "open eye" icon if desired
            editTextPassword.setCompoundDrawablesWithIntrinsicBounds(null, null, eyeIcon, null);
        }
        isPasswordVisible = !isPasswordVisible;
        editTextPassword.setSelection(editTextPassword.length()); // Keep cursor at the end
    }

    private void login() {
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();

        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Vui lòng điền đầy đủ thông tin", Toast.LENGTH_SHORT).show();
            return;
        }

        // Admin login check
        if (email.equals(ADMIN_EMAIL) && password.equals(ADMIN_PASSWORD)) {
            Toast.makeText(this, "Đăng nhập với quyền admin thành công", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(DangNhapActivity.this, fragmentActivity.class)); // Admin activity
            finish();
        } else {
            // Regular user login check
            UserDAO userDAO = new UserDAO(DangNhapActivity.this);
            String storedPassword = userDAO.getPassword(email);

            if (storedPassword != null && storedPassword.equals(password)) {
                // Successful login
                Toast.makeText(DangNhapActivity.this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();

                // Save the user login state in SharedPreferences for auto-login next time
                SharedPreferences preferences = getSharedPreferences("AppData", MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString("email", email);
                editor.putString("password", password);
                editor.apply();

                // Proceed to the home activity (or whichever activity is next in the flow)
                startActivity(new Intent(DangNhapActivity.this, HomeActivity.class));
                finish();
            } else {
                // Incorrect password or email
                Toast.makeText(DangNhapActivity.this, "Email hoặc mật khẩu không đúng", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void createAccount() {
        // Navigate to the signup (DangKyActivity)
        Intent intent = new Intent(DangNhapActivity.this, DangKyActivity.class);
        startActivity(intent);
    }

    private void forgotPassword() {
        // Navigate to ForgotPasswordActivity
        Intent intent = new Intent(DangNhapActivity.this, ForgotPasswordActivity.class);
        startActivity(intent);
    }
}