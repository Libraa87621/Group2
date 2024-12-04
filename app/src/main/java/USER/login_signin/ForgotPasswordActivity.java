package USER.login_signin;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.example.duan1.R;

public class ForgotPasswordActivity extends AppCompatActivity {

    private EditText editTextEmail;
    private Button buttonSendResetLink;

    private static final String TAG = "ForgotPasswordActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        FirebaseApp.initializeApp(this); // Initialize Firebase
        // Initialize views
        editTextEmail = findViewById(R.id.editTextEmail);
        buttonSendResetLink = findViewById(R.id.buttonSendResetLink);

        // Set up click listener for "Send Reset Link" button
        buttonSendResetLink.setOnClickListener(v -> sendResetLink());
    }

    private void sendResetLink() {
        String email = editTextEmail.getText().toString().trim();

        Log.d(TAG, "Email entered: " + email);

        // Validate email
        if (TextUtils.isEmpty(email)) {
            Log.d(TAG, "Email is empty");
            Toast.makeText(this, "Vui lòng nhập email của bạn", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Log.d(TAG, "Invalid email format");
            Toast.makeText(this, "Vui lòng nhập email hợp lệ", Toast.LENGTH_SHORT).show();
            return;
        }

        // Show loading indication
        buttonSendResetLink.setEnabled(false);
        buttonSendResetLink.setText("Đang gửi...");
        Log.d(TAG, "Sending reset link to email: " + email);

        // Send password reset email
        FirebaseAuth.getInstance().sendPasswordResetEmail(email)
                .addOnCompleteListener(task -> {
                    buttonSendResetLink.setEnabled(true);
                    buttonSendResetLink.setText("Gửi liên kết đặt lại mật khẩu");

                    if (task.isSuccessful()) {
                        Log.d(TAG, "Password reset email sent successfully");
                        Toast.makeText(this, "Liên kết đặt lại mật khẩu đã được gửi đến email của bạn", Toast.LENGTH_LONG).show();
                        finish(); // Close activity and return to login screen
                    } else {
                        Log.e(TAG, "Error sending password reset email", task.getException());
                        // Xử lý lỗi chi tiết hơn
                        String errorMessage = task.getException() != null ? task.getException().getMessage() : "Đã xảy ra lỗi không xác định.";
                        Toast.makeText(this, "Lỗi: " + errorMessage, Toast.LENGTH_LONG).show();
                    }
                })
                .addOnFailureListener(e -> {
                    Log.e(TAG, "Firebase failure: ", e);
                    Toast.makeText(this, "Không thể kết nối Firebase. Vui lòng kiểm tra kết nối mạng.", Toast.LENGTH_SHORT).show();
                });
    }
}
