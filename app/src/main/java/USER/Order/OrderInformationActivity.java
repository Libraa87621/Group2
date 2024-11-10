package USER.Order;

import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.duan1.R;

import java.text.DecimalFormat;

public class OrderInformationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_information);

        // Tìm TextView để hiển thị tổng tiền
        TextView tvTotalAmount = findViewById(R.id.tvTotalAmount);

        // Nhận giá trị tổng tiền từ Intent
        int totalAmount = getIntent().getIntExtra("totalAmount", 0);

        // Định dạng giá trị tổng tiền và hiển thị lên TextView
        DecimalFormat formatter = new DecimalFormat("#,###");
        tvTotalAmount.setText(formatter.format(totalAmount) + " VND");
    }
}
