package USER.login_signin;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.InputType;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPassword = findViewById(R.id.editTextPassword);
        buttonLogin = findViewById(R.id.buttonLogin);
        buttonCreateAccount = findViewById(R.id.buttonCreateAccount);
        textViewForgotPassword = findViewById(R.id.textViewForgotPassword);

        // Lấy email and password Từ the Intent
        Intent intent = getIntent();
        String email = intent.getStringExtra("email");
        String password = intent.getStringExtra("password");

        // Set email and password to EditText fields if they are not null
        if (email != null) {
            editTextEmail.setText(email);
        }
        if (password != null) {
            editTextPassword.setText(password);
        }

        // Set up listeners
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });

        buttonCreateAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createAccount();
            }
        });

        textViewForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                forgotPassword();
            }
        });

        // Set up touch listener for eye icon
        editTextPassword.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    int drawableEnd = editTextPassword.getCompoundDrawables()[2] != null ? 2 : -1;
                    if (drawableEnd >= 0 && event.getRawX() >= (editTextPassword.getRight() - editTextPassword.getCompoundDrawables()[drawableEnd].getBounds().width())) {
                        togglePasswordVisibility();
                        return true;
                    }
                }
                return false;
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

        // Get email and password from the registration (passed via Intent)
        Intent intent = getIntent();
        String registeredEmail = intent.getStringExtra("email");
        String registeredPassword = intent.getStringExtra("password");

        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Vui lòng điền đầy đủ thông tin", Toast.LENGTH_SHORT).show();
        } else {
            // Kiểm tra nếu tài khoản là admin
            if (email.equals(ADMIN_EMAIL) && password.equals(ADMIN_PASSWORD)) {
                Toast.makeText(this, "Đăng nhập với quyền admin thành công", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(DangNhapActivity.this, fragmentActivity.class)); // Chuyển hướng đến AdminActivity
                finish();
            }
            // Kiểm tra tài khoản đăng ký thông thường
            else if (email.equals(registeredEmail) && password.equals(registeredPassword)) {
                Toast.makeText(this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(DangNhapActivity.this, HomeActivity.class));
                finish();
            } else {
                Toast.makeText(this, "Email hoặc mật khẩu không chính xác", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void createAccount() {
        startActivity(new Intent(DangNhapActivity.this, DangKyActivity.class));
    }

    private void forgotPassword() {
        Toast.makeText(this, "Chức năng quên mật khẩu đang được phát triển", Toast.LENGTH_SHORT).show();
    }
}
