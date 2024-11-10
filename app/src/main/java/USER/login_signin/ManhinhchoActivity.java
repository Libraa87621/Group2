package USER.login_signin;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.duan1.R;

public class ManhinhchoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_manhinhcho);

        // Đặt độ trễ 3 giây trước khi chuyển qua màn hình đăng nhập
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // Chuyển tới màn hình đăng nhập
                Intent intent = new Intent(ManhinhchoActivity.this, DangNhapActivity.class); // LoginActivity là màn hình đăng nhập của bạn
                startActivity(intent);
                finish();  // Đóng màn hình hiện tại
            }
        }, 3000); // 3000 milliseconds = 3 giây
    }
}
