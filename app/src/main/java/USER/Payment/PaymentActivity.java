package USER.Payment;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;
import android.content.Intent;
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
    private Button btnBack, btnPlaceOrderFinal, btnApplyPromo;
    private Spinner spinnerPromo;
    private EditText etPromoCode;
    private TextView tvTotalAmount;
    private double discountedAmount;
    private ImageView qrCodeImageView;
    private LinearLayout momoPaymentOptions;
    private RadioButton radioQR, radioPhone;

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
        btnPlaceOrderFinal = findViewById(R.id.btnPlaceOrderFinal);
        btnApplyPromo = findViewById(R.id.btnApplyPromo);
        spinnerPromo = findViewById(R.id.spinnerPromo);
        etPromoCode = findViewById(R.id.etPromoCode);
        tvTotalAmount = findViewById(R.id.tvTotalAmount);
        qrCodeImageView = findViewById(R.id.qrCodeImageView);

        momoPaymentOptions = findViewById(R.id.momoPaymentOptions);
        radioQR = findViewById(R.id.radioQR);
        radioPhone = findViewById(R.id.radioPhone);

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
                qrCodeImageView.setVisibility(View.GONE);
                momoPaymentOptions.setVisibility(View.GONE);
            } else {
                etCardNumber.setVisibility(View.GONE);
            }
        });

        checkboxMOMO.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                // Show Momo payment options (QR or Phone)
                momoPaymentOptions.setVisibility(View.VISIBLE);
                etMomoPhone.setVisibility(View.GONE);
                qrCodeImageView.setVisibility(View.GONE);

                // Default to QR option
                radioQR.setChecked(true);
            } else {
                momoPaymentOptions.setVisibility(View.GONE);
                etMomoPhone.setVisibility(View.GONE);
                qrCodeImageView.setVisibility(View.GONE);
            }
        });
        radioQR.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                etMomoPhone.setVisibility(View.GONE);
                qrCodeImageView.setVisibility(View.VISIBLE);
                // Generate QR code when selected
                generateQRCode();
            }
        });
        radioPhone.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                qrCodeImageView.setVisibility(View.GONE);
                etMomoPhone.setVisibility(View.VISIBLE);
            }
        });

        checkboxMOMO.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    // Show phone number input and hide QR code
                    etMomoPhone.setVisibility(View.VISIBLE);
                    qrCodeImageView.setVisibility(View.GONE);
                } else {
                    // Show QR code and hide phone number input
                    etMomoPhone.setVisibility(View.GONE);
                    qrCodeImageView.setVisibility(View.VISIBLE);
                }
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


        btnPlaceOrderFinal.setOnClickListener(v -> {
            if (validateInputs()) {
                Toast.makeText(PaymentActivity.this, "Đặt hàng hoàn tất", Toast.LENGTH_SHORT).show();
                startPaymentSuccessActivity();
            }
        });

        btnApplyPromo.setOnClickListener(v -> applyPromoCodeFromInput());
    }

    private void generateQRCode() {
        // Generate a QR code for MoMo payment
        String paymentData = "Amount: " + discountedAmount + " VND";
        try {
            MultiFormatWriter writer = new MultiFormatWriter();
            BitMatrix matrix = writer.encode(paymentData, BarcodeFormat.QR_CODE, 500, 500);
            BarcodeEncoder encoder = new BarcodeEncoder();
            Bitmap bitmap = encoder.createBitmap(matrix);
            qrCodeImageView.setImageBitmap(bitmap);
        } catch (WriterException e) {
            e.printStackTrace();
            Toast.makeText(this, "Không thể tạo mã QR", Toast.LENGTH_SHORT).show();
        }
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
        String qrContent = "Đơn hàng: " + discountedAmount + " VND";


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
    private boolean isValidPromoCode(String promoCode) {
        return promoCode.equals("Mã giảm giá 1") || promoCode.equals("Mã giảm giá 2") || promoCode.equals("Mã giảm giá 3");
    }
    private void applyPromoCodeFromInput() {
        String promoCode = etPromoCode.getText().toString().trim();
        if (promoCode.isEmpty()) {
            Toast.makeText(PaymentActivity.this, "Vui lòng nhập mã khuyến mãi", Toast.LENGTH_SHORT).show();
        } else {
            if (isValidPromoCode(promoCode)) {
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
