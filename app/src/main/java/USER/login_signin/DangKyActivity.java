package USER.login_signin;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.duan1.R;

public class DangKyActivity extends AppCompatActivity {

    private EditText etFirstName, etLastName, etEmail, etDate, etPassword, etConfirmPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dang_ky);

        // Bind views
        etFirstName = findViewById(R.id.FisrtName);
        etLastName = findViewById(R.id.LastName);
        etEmail = findViewById(R.id.Name);
        etDate = findViewById(R.id.Date);
        etPassword = findViewById(R.id.Password);
        etConfirmPassword = findViewById(R.id.comfirmPassword);

        // Register button click listener
        findViewById(R.id.btnRegister).setOnClickListener(v -> {
            if (isValidInput()) {
                // All fields are valid, proceed with registration
                Toast.makeText(DangKyActivity.this, "Đăng ký thành công", Toast.LENGTH_SHORT).show();
            }
        });
    }

    // Check if all input fields are valid
    private boolean isValidInput() {
        boolean isValid = true;

        // Collect all errors
        if (TextUtils.isEmpty(etFirstName.getText().toString())) {
            etFirstName.setError("Họ không được bỏ trống");
            isValid = false;
        }

        if (TextUtils.isEmpty(etLastName.getText().toString())) {
            etLastName.setError("Tên không được bỏ trống");
            isValid = false;
        }

        if (!isValidEmail(etEmail.getText().toString())) {
            isValid = false;  // The error message is handled in isValidEmail
        }

        if (TextUtils.isEmpty(etDate.getText().toString())) {
            etDate.setError("Ngày sinh không được bỏ trống");
            isValid = false;
        } else if (!isValidDate(etDate.getText().toString())) {
            etDate.setError("Ngày sinh không hợp lệ. Định dạng phải là mm/dd/yy (ví dụ: 12/25/24)");
            isValid = false;
        }

        if (TextUtils.isEmpty(etPassword.getText().toString())) {
            etPassword.setError("Mật khẩu không được bỏ trống");
            isValid = false;
        }

        if (etPassword.getText().toString().length() < 6) {
            etPassword.setError("Mật khẩu phải có ít nhất 6 ký tự");
            isValid = false;
        }

        if (!etPassword.getText().toString().equals(etConfirmPassword.getText().toString())) {
            etConfirmPassword.setError("Mật khẩu và Xác nhận mật khẩu không khớp");
            isValid = false;
        }

        return isValid;
    }

    // Check if the email is valid
    private boolean isValidEmail(String email) {
        if (TextUtils.isEmpty(email)) {
            etEmail.setError("Email không được bỏ trống");
            return false;
        }

        // Validate the email format (example: user@example.com)
        String emailPattern = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[a-zA-Z]{2,}$";
        if (!email.matches(emailPattern)) {
            etEmail.setError("Email không hợp lệ. Hãy chắc chắn rằng bạn nhập đúng định dạng (ví dụ: example@mail.com)");
            return false;
        }

        return true;
    }

    // Check if the date is in valid mm/dd/yy format
    private boolean isValidDate(String date) {
        // Regular expression to check if the date is in the format mm/dd/yy
        String datePattern = "^(0[1-9]|1[0-2])/(0[1-9]|[12][0-9]|3[01])/([0-9]{2})$";
        return date.matches(datePattern);
    }
}
