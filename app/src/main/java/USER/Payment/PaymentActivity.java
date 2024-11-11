package USER.Payment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.duan1.R;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class PaymentActivity extends AppCompatActivity {

    private CheckBox checkboxCash, checkboxMOMO;
    private EditText etCardNumber, etMomoPhone;
    private Button btnBack, btnPlaceOrder, btnPlaceOrderFinal, btnApplyPromo;
    private Spinner spinnerPromo;
    private EditText etPromoCode;
    private TextView tvTotalAmount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        // Initialize views
        initializeViews();

        // Set up total amount display
        displayTotalAmount();

        // Set up CheckBox listeners for exclusive selection
        setupCheckboxListeners();

        // Set up Spinner with promo code options
        setupPromoSpinner();

        // Set up listeners for buttons
        setupButtonListeners();
    }

    private void initializeViews() {
        checkboxCash = findViewById(R.id.checkboxCash);
        checkboxMOMO = findViewById(R.id.checkboxMOMO);
        etCardNumber = findViewById(R.id.etCardNumber);
        etMomoPhone = findViewById(R.id.etMomoPhone);
        btnBack = findViewById(R.id.btnBack);
        btnPlaceOrder = findViewById(R.id.btnPlaceOrder);
        btnPlaceOrderFinal = findViewById(R.id.btnPlaceOrderFinal);
        btnApplyPromo = findViewById(R.id.btnApplyPromo);
        spinnerPromo = findViewById(R.id.spinnerPromo);
        etPromoCode = findViewById(R.id.etPromoCode);
        tvTotalAmount = findViewById(R.id.tvTotalAmount);
    }

    private void displayTotalAmount() {
        int totalAmount = getIntent().getIntExtra("totalAmount", 0);
        DecimalFormat formatter = new DecimalFormat("#,###");
        tvTotalAmount.setText(formatter.format(totalAmount) + " VND");
    }

    private void setupCheckboxListeners() {
        checkboxCash.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                checkboxMOMO.setChecked(false);
                etCardNumber.setVisibility(View.VISIBLE); // Show card number input
                etMomoPhone.setVisibility(View.GONE); // Hide MoMo phone input
            } else {
                etCardNumber.setVisibility(View.GONE); // Hide card number input if unchecked
            }
        });

        checkboxMOMO.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                checkboxCash.setChecked(false);
                etMomoPhone.setVisibility(View.VISIBLE); // Show MoMo phone input
                etCardNumber.setVisibility(View.GONE); // Hide card number input
            } else {
                etMomoPhone.setVisibility(View.GONE); // Hide MoMo phone input if unchecked
            }
        });
    }

    private void setupPromoSpinner() {
        List<String> promoList = new ArrayList<>();
        promoList.add("Áp Dụng khuyến mãi"); // Default item
        promoList.add("Mã giảm giá 1");
        promoList.add("Mã giảm giá 2");
        promoList.add("Mã giảm giá 3");

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, promoList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerPromo.setAdapter(adapter);

        // Spinner item selection listener
        spinnerPromo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                String selectedPromo = (String) parentView.getItemAtPosition(position);
                applyPromoCode(selectedPromo);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // Do nothing
            }
        });
    }

    private void applyPromoCode(String promoCode) {
        int totalAmount = getIntent().getIntExtra("totalAmount", 0);  // Get the original total amount
        double discount = 0;

        // Apply the corresponding discount based on the promo code selected in the Spinner
        switch (promoCode) {
            case "Mã giảm giá 1":
                discount = 0.05;  // 5% discount
                break;
            case "Mã giảm giá 2":
                discount = 0.10;  // 10% discount
                break;
            case "Mã giảm giá 3":
                discount = 0.15;  // 15% discount
                break;
            default:
                discount = 0; // No discount if the default item is selected
                break;
        }

        // Calculate the new total amount after applying the discount
        double discountedAmount = totalAmount * (1 - discount);

        // Format the discounted amount and update the UI
        DecimalFormat formatter = new DecimalFormat("#,###");
        tvTotalAmount.setText(formatter.format(discountedAmount) + " VND");

        // Show a confirmation message
        Toast.makeText(PaymentActivity.this, "Mã khuyến mãi " + promoCode + " đã được áp dụng", Toast.LENGTH_SHORT).show();
    }

    private void setupButtonListeners() {
        btnBack.setOnClickListener(v -> onBackPressed());

        btnPlaceOrder.setOnClickListener(v -> {
            if (validateInputs()) {
                Toast.makeText(PaymentActivity.this, "Đặt hàng thành công", Toast.LENGTH_SHORT).show();
                // Implement the actual order placement logic if needed
            }
        });

        btnPlaceOrderFinal.setOnClickListener(v -> {
            if (validateInputs()) {
                Toast.makeText(PaymentActivity.this, "Đặt hàng hoàn tất", Toast.LENGTH_SHORT).show();
                startPaymentSuccessActivity();
            }
        });

        btnApplyPromo.setOnClickListener(v -> applyPromoCodeFromInput());
    }

    private void startPaymentSuccessActivity() {
        Intent intent = new Intent(PaymentActivity.this, PaymentSuccessActivity.class);
        startActivity(intent);
        finish();
    }

    private void applyPromoCodeFromInput() {
        String promoCode = etPromoCode.getText().toString().trim();
        int totalAmount = getIntent().getIntExtra("totalAmount", 0);  // Get the original total amount

        if (promoCode.isEmpty()) {
            Toast.makeText(PaymentActivity.this, "Vui lòng nhập mã khuyến mãi", Toast.LENGTH_SHORT).show();
        } else {
            double discount = 0;

            // Apply the corresponding discount based on the promo code
            switch (promoCode) {
                case "Mã giảm giá 1":
                    discount = 0.05;  // 5% discount
                    break;
                case "Mã giảm giá 2":
                    discount = 0.10;  // 10% discount
                    break;
                case "Mã giảm giá 3":
                    discount = 0.15;  // 15% discount
                    break;
                default:
                    Toast.makeText(PaymentActivity.this, "Mã khuyến mãi không hợp lệ", Toast.LENGTH_SHORT).show();
                    return;
            }

            // Calculate the new total amount after applying the discount
            double discountedAmount = totalAmount * (1 - discount);

            // Format the discounted amount and update the UI
            DecimalFormat formatter = new DecimalFormat("#,###");
            tvTotalAmount.setText(formatter.format(discountedAmount) + " VND");

            // Show a confirmation message
            Toast.makeText(PaymentActivity.this, "Mã khuyến mãi " + promoCode + " đã được áp dụng", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean validateInputs() {
        if (checkboxMOMO.isChecked()) {
            String momoPhone = etMomoPhone.getText().toString();
            if (!momoPhone.matches("^0\\d{9}$")) {
                Toast.makeText(this, "Số điện thoại MoMo phải bắt đầu bằng 0 và đủ 10 số.", Toast.LENGTH_LONG).show();
                return false;
            }
        }

        if (checkboxCash.isChecked()) {
            String cardNumber = etCardNumber.getText().toString();
            if (cardNumber.length() < 10) {
                Toast.makeText(this, "Số thẻ phải có ít nhất 10 chữ số.", Toast.LENGTH_LONG).show();
                return false;
            }
        }

        return true;
    }
}
