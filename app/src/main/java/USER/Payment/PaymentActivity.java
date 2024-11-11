package USER.Payment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.duan1.R;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class PaymentActivity extends AppCompatActivity {

    private RadioGroup radioGroupPaymentMethod;
    private EditText etMomoPhone;
    private Button btnBack, btnPlaceOrder, btnPlaceOrderFinal, btnApplyPromo;
    private Spinner spinnerPromo;
    private EditText etPromoCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        // Initialize views
        radioGroupPaymentMethod = findViewById(R.id.radioGroupPaymentMethod);
        etMomoPhone = findViewById(R.id.etMomoPhone);
        btnBack = findViewById(R.id.btnBack);
        btnPlaceOrder = findViewById(R.id.btnPlaceOrder);
        btnPlaceOrderFinal = findViewById(R.id.btnPlaceOrderFinal);
        btnApplyPromo = findViewById(R.id.btnApplyPromo);
        spinnerPromo = findViewById(R.id.spinnerPromo);
        etPromoCode = findViewById(R.id.etPromoCode);

        // Get the totalAmount from the Intent
        int totalAmount = getIntent().getIntExtra("totalAmount", 0);

        // Format and display the total amount
        TextView tvTotalAmount = findViewById(R.id.tvTotalAmount);
        DecimalFormat formatter = new DecimalFormat("#,###");
        tvTotalAmount.setText(formatter.format(totalAmount) + " VND");

        // Set up RadioGroup listener to show/hide EditText
        radioGroupPaymentMethod.setOnCheckedChangeListener((group, checkedId) -> {
            // Hide the EditText by default
            etMomoPhone.setVisibility(View.GONE);

            // Check which RadioButton is selected
            if (checkedId == R.id.rdoMOMO) {
                // Show the EditText when MoMo is selected
                etMomoPhone.setVisibility(View.VISIBLE);
            }
        });

        // Set up Spinner for promo codes
        List<String> promoList = new ArrayList<>();
        promoList.add("Áp Dụng khuyến mãi"); // Default item
        promoList.add("Mã giảm giá 1");
        promoList.add("Mã giảm giá 2");
        promoList.add("Mã giảm giá 3");

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, promoList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerPromo.setAdapter(adapter);

        // Set up listeners for buttons
        btnBack.setOnClickListener(v -> onBackPressed()); // Go back to previous screen

        btnPlaceOrder.setOnClickListener(v -> {
            // Handle place order action here
            Toast.makeText(PaymentActivity.this, "Đặt hàng thành công", Toast.LENGTH_SHORT).show();
            // You can implement the actual order placement logic here
        });

        btnPlaceOrderFinal.setOnClickListener(v -> {
            // Handle final place order action
            Toast.makeText(PaymentActivity.this, "Đặt hàng hoàn tất", Toast.LENGTH_SHORT).show();

            // Create an Intent to navigate to the PaymentSuccessActivity
            Intent intent = new Intent(PaymentActivity.this, PaymentSuccessActivity.class);

            // Optionally, pass data (e.g., order ID) to the PaymentSuccessActivity if needed
            // intent.putExtra("orderDetails", orderDetails);

            // Start the PaymentSuccessActivity
            startActivity(intent);

            // Optionally, finish the current activity to prevent going back
            finish();
        });

        btnApplyPromo.setOnClickListener(v -> {
            // Apply promo code logic
            String promoCode = etPromoCode.getText().toString();
            if (promoCode.isEmpty()) {
                Toast.makeText(PaymentActivity.this, "Vui lòng nhập mã khuyến mãi", Toast.LENGTH_SHORT).show();
            } else {
                // Apply promo code logic
                Toast.makeText(PaymentActivity.this, "Mã khuyến mãi " + promoCode + " đã được áp dụng", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
