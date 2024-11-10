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

import USER.Home.HomeActivity;

public class DangNhapActivity extends AppCompatActivity {

    private EditText editTextEmail, editTextPassword;
    private Button buttonLogin, buttonCreateAccount;
    private TextView textViewForgotPassword;

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

        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Vui lòng điền đầy đủ thông tin", Toast.LENGTH_SHORT).show();
        } else {
            // TODO: Authenticate user (connect to your backend or database)
            if (email.equals("test@example.com") && password.equals("password123")) {
                Toast.makeText(this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                // Redirect to another activity on successful login
                startActivity(new Intent(DangNhapActivity.this, HomeActivity.class));
                finish();
            } else {
                Toast.makeText(this, "Email hoặc mật khẩu không chính xác", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void createAccount() {
        // Redirect to CreateAccountActivity
        startActivity(new Intent(DangNhapActivity.this, DangKyActivity.class));
    }

    private void forgotPassword() {
        // TODO: Implement forgot password logic (e.g., open a password reset activity or send a reset email)
        Toast.makeText(this, "Chức năng quên mật khẩu đang được phát triển", Toast.LENGTH_SHORT).show();
    }
}
