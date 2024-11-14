package USER.login_signin;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.duan1.R;

import ADMIN.fragment.fragmentActivity;
import USER.Home.HomeActivity;

public class DangNhapActivity extends AppCompatActivity {

    private EditText editTextEmail, editTextPassword;
    private Button buttonLogin, buttonCreateAccount;
    private TextView textViewForgotPassword;

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

        // Get email and password from the Intent
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
