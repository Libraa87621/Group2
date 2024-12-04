package USER.login_signin;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.duan1.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;

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
                String email = etEmail.getText().toString();
                String password = etPassword.getText().toString();

                // Save user to database
                UserDAO userDAO = new UserDAO(DangKyActivity.this);
                if (!userDAO.isUserExist(email)) {
                    userDAO.saveUser(email, password);
                    Toast.makeText(DangKyActivity.this, "Đăng ký thành công", Toast.LENGTH_SHORT).show();

                    // Save email and password in SharedPreferences for auto-login
                    SharedPreferences preferences = getSharedPreferences("AppData", MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString("email", email);
                    editor.putString("password", password);
                    editor.apply();

                    // Proceed to login activity
                    Intent intent = new Intent(DangKyActivity.this, DangNhapActivity.class);
                    intent.putExtra("email", email);
                    intent.putExtra("password", password);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(DangKyActivity.this, "Tài khoản đã tồn tại", Toast.LENGTH_SHORT).show();

                    // Show confirmation dialog before transitioning to login screen
                    new AlertDialog.Builder(DangKyActivity.this)
                            .setTitle("Thông báo")
                            .setMessage("Tài khoản đã tồn tại. Bạn có muốn chuyển đến màn hình đăng nhập không?")
                            .setPositiveButton("Có", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    // Transition to login screen after user confirmation
                                    Intent intent = new Intent(DangKyActivity.this, DangNhapActivity.class);
                                    intent.putExtra("email", email);
                                    intent.putExtra("password", password);
                                    startActivity(intent);
                                    finish();
                                }
                            })
                            .setNegativeButton("Không", null) // User clicked "No", do nothing
                            .show();
                }
            }
        });
    }

    private boolean isValidInput() {
        boolean isValid = true;

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
            etDate.setError("Ngày sinh không hợp lệ. Định dạng phải là dd/MM/yyyy (ví dụ: 25/12/2024)");
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

    private boolean isValidEmail(String email) {
        if (TextUtils.isEmpty(email)) {
            etEmail.setError("Email không được bỏ trống");
            return false;
        }

        String emailPattern = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[a-zA-Z]{2,}$";
        if (!email.matches(emailPattern)) {
            etEmail.setError("Email không hợp lệ. Hãy chắc chắn rằng bạn nhập đúng định dạng (ví dụ: example@mail.com)");
            return false;
        }

        return true;
    }

    private boolean isValidDate(String date) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        sdf.setLenient(false);
        try {
            sdf.parse(date);
            return true;
        } catch (ParseException e) {
            return false;
        }
    }
}
