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

import USER.choosefood.Combo;

public class PaymentActivity extends AppCompatActivity {

    private CheckBox checkboxCash, checkboxMOMO;
    private EditText etCardNumber, etMomoPhone;
    private Button btnBack, btnPlaceOrder, btnPlaceOrderFinal, btnApplyPromo;
    private Spinner spinnerPromo;
    private EditText etPromoCode;
    private TextView tvTotalAmount;
    private double discountedAmount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        // Initialize views
        initializeViews();

        // Display total amount
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
        // Retrieve the total amount from OrderActivity
        int totalAmount = getIntent().getIntExtra("totalAmount", 0);
        DecimalFormat formatter = new DecimalFormat("#,###");
        tvTotalAmount.setText(formatter.format(totalAmount) + " VND");
    }

    private void setupCheckboxListeners() {
        checkboxCash.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                checkboxMOMO.setChecked(false);
                etCardNumber.setVisibility(View.VISIBLE);
                etMomoPhone.setVisibility(View.GONE);
            } else {
                etCardNumber.setVisibility(View.GONE);
            }
        });

        checkboxMOMO.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                checkboxCash.setChecked(false);
                etMomoPhone.setVisibility(View.VISIBLE);
                etCardNumber.setVisibility(View.GONE);
            } else {
                etMomoPhone.setVisibility(View.GONE);
            }
        });
    }

    private void setupPromoSpinner() {
        List<String> promoList = new ArrayList<>();
        promoList.add("Áp Dụng khuyến mãi");
        promoList.add("Mã giảm giá 1");
        promoList.add("Mã giảm giá 2");
        promoList.add("Mã giảm giá 3");

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, promoList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerPromo.setAdapter(adapter);

        spinnerPromo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                String selectedPromo = (String) parentView.getItemAtPosition(position);
                applyPromoCode(selectedPromo);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
            }
        });
    }
    private void applyPromoCode(String promoCode) {
        int totalAmount = getIntent().getIntExtra("totalAmount", 0);
        double discount = 0;

        switch (promoCode) {
            case "Mã giảm giá 1":
                discount = 0.05;
                break;
            case "Mã giảm giá 2":
                discount = 0.10;
                break;
            case "Mã giảm giá 3":
                discount = 0.15;
                break;
            default:
                discount = 0;
                break;
        }

        // Tính tổng tiền sau khi giảm giá
        discountedAmount = totalAmount * (1 - discount);
        DecimalFormat formatter = new DecimalFormat("#,###");
        tvTotalAmount.setText(formatter.format(discountedAmount) + " VND");

        Toast.makeText(PaymentActivity.this, "Mã giảm giá " + promoCode + " đã được áp dụng", Toast.LENGTH_SHORT).show(); }

    private void setupButtonListeners() {
        btnBack.setOnClickListener(v -> onBackPressed());

        btnPlaceOrder.setOnClickListener(v -> {
            if (validateInputs()) {
                Toast.makeText(PaymentActivity.this, "Đặt hàng thành công", Toast.LENGTH_SHORT).show();
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

    // In PaymentActivity, start PaymentSuccessActivity after processing
    private void startPaymentSuccessActivity() {
        Intent intent = new Intent(PaymentActivity.this, PaymentSuccessActivity.class);

        // Lấy dữ liệu từ Intent
        String fullName = getIntent().getStringExtra("fullName");
        String phoneNumber = getIntent().getStringExtra("phoneNumber");
        String address = getIntent().getStringExtra("address");
        String note = getIntent().getStringExtra("note");
        String promoCode = spinnerPromo.getSelectedItem().toString();

        // Chuẩn hóa địa chỉ
        String normalizedAddress = normalizeString(address);
        String deliveryTime = getDeliveryTimeBasedOnAddress(normalizedAddress);

        // Lấy phương thức thanh toán đã chọn
        String paymentMethod = getSelectedPaymentMethod();

        // Dữ liệu combo (nếu có)
        Combo combo = getIntent().getParcelableExtra("combo");

        // Truyền dữ liệu sang PaymentSuccessActivity
        intent.putExtra("combo", combo);
        intent.putExtra("fullName", fullName);
        intent.putExtra("phoneNumber", phoneNumber);
        intent.putExtra("address", address);
        intent.putExtra("note", note);
        intent.putExtra("deliveryTime", deliveryTime);
        intent.putExtra("paymentMethod", paymentMethod);
        intent.putExtra("totalAmount", discountedAmount);  // Truyền tổng tiền sau khi giảm giá
        intent.putExtra("promoCode", promoCode);

        startActivity(intent);
    }



    private String normalizeString(String input) {
        // Implement your string normalization logic here
        return input;
    }

    private String getSelectedPaymentMethod() {
        if (checkboxCash.isChecked()) {
            return "Cash";
        } else if (checkboxMOMO.isChecked()) {
            return "MoMo";
        } else {
            return "No Payment Method Selected";  // If neither is selected
        }
    }

    private String getDeliveryTimeBasedOnAddress(String address) {
        if (address.contains("trung my tay")) {
            return "30 phút";
        } else if (address.contains("quang trung")) {
            return "45 phút";
        } else if (address.contains("tan chanh hiep")) {
            return "40 phút";
        } else {
            return "Thời gian giao hàng không xác định";
        }
    }

    private void applyPromoCodeFromInput() {
        String promoCode = etPromoCode.getText().toString().trim();
        int totalAmount = getIntent().getIntExtra("totalAmount", 0);

        if (promoCode.isEmpty()) {
            Toast.makeText(PaymentActivity.this, "Vui lòng nhập mã khuyến mãi", Toast.LENGTH_SHORT).show();
        } else {
            // Ensure that the entered promo code is valid before applying the discount
            if (promoCode.equalsIgnoreCase("Mã giảm giá 1") || promoCode.equalsIgnoreCase("Mã giảm giá 2") || promoCode.equalsIgnoreCase("Mã giảm giá 3")) {
                applyPromoCode(promoCode);
            } else {
                Toast.makeText(PaymentActivity.this, "Mã khuyến mãi không hợp lệ", Toast.LENGTH_SHORT).show();
            }
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
            if (!cardNumber.matches("\\d{16}")) {
                Toast.makeText(this, "Số thẻ không hợp lệ.", Toast.LENGTH_LONG).show();
                return false;
            }
        }

        return true;
    }
}
