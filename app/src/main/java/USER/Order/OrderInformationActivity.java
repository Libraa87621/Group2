    package USER.Order;

    import android.content.Intent;
    import java.text.Normalizer; // Correct import

    import android.content.SharedPreferences;
    import android.os.Bundle;
    import android.text.Editable;
    import android.text.TextWatcher;
    import android.view.View;
    import android.widget.Button;
    import android.widget.EditText;
    import android.widget.LinearLayout;
    import android.widget.RadioButton;
    import android.widget.TextView;
    import androidx.appcompat.app.AppCompatActivity;
    import com.example.duan1.R;
    import java.text.DecimalFormat;
    import java.util.HashSet;
    import java.util.Set;

    import USER.Payment.PaymentActivity;
    import USER.choosefood.Combo;

    public class OrderInformationActivity extends AppCompatActivity {

        private EditText edtFullName, edtNumberPhone, edtAdress, edtNote;
        private Button btnChangeAddress, btnNext;
        private RadioButton rdokHomeDelivery, rdoPickUpAtStore;
        private LinearLayout layoutOldAddresses;
        private static final String PREFS_NAME = "OrderPrefs"; // lưu thông tin đặt hàng (order) của người dùng
        private static final String KEY_ADDRESSES = "savedAddresses";

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_order_information);


            // Initialize EditText fields
            edtFullName = findViewById(R.id.edtFullName);
            edtNumberPhone = findViewById(R.id.edtNumberPhone);
            edtAdress = findViewById(R.id.edtAdress);
            edtNote = findViewById(R.id.edtnote);
            btnChangeAddress = findViewById(R.id.btnChangeAddress);
            btnNext = findViewById(R.id.btnNext);
            rdokHomeDelivery = findViewById(R.id.rdoHomeDelivery);
            rdoPickUpAtStore = findViewById(R.id.rdoPickUpAtStore);
            layoutOldAddresses = findViewById(R.id.layoutOldAddresses);
            TextView tvTotalAmount = findViewById(R.id.tvTotalAmount);




            Combo combo = getIntent().getParcelableExtra("combo");

            int totalAmount = getIntent().getIntExtra("totalAmount", 0);
            DecimalFormat formatter = new DecimalFormat("#,###");

            tvTotalAmount.setText(formatter.format(totalAmount) + " VND");

            loadSavedAddresses();

            btnNext.setOnClickListener(v -> onNextButtonClick(combo));

        }

        String deliveryTime = "";
        public void onNextButtonClick(Combo combo) {
            String fullName = edtFullName.getText().toString().trim();
            String phoneNumber = edtNumberPhone.getText().toString().trim();
            String address = edtAdress.getText().toString().trim();
            String note = edtNote.getText().toString().trim();
            boolean isValid = true;




            // bắt lỗi
            if (fullName.isEmpty()) {
                edtFullName.setError("Vui lòng nhập họ và tên");
                isValid = false;

            } else {
                edtFullName.setError(null);
            }

            if (phoneNumber.isEmpty()) {
                edtNumberPhone.setError("Vui lòng nhập số điện thoại");
                isValid = false;

            } else if (phoneNumber.length() != 10 || phoneNumber.charAt(0) != '0') {
                edtNumberPhone.setError("Số điện thoại không hợp lệ");
                isValid = false;
            } else {
                edtNumberPhone.setError(null);
            }


            // Validate Address
            if (address.isEmpty()) {
                edtAdress.setError("Vui lòng nhập địa chỉ");
                isValid = false;
            } else {
                String normalizedAddress = normalizeString(address);


                if (normalizedAddress.contains("trung my tay")) {
                    deliveryTime = "30 phút";
                } else if (normalizedAddress.contains("quang trung")) {
                    deliveryTime = "45 phút";
                } else if (normalizedAddress.contains("tan chanh hiep")) {
                    deliveryTime = "40 phút";
                } else {
                    edtAdress.setError("Địa chỉ phải thuộc một trong các khu vực: Trung Mỹ Tây, Quang Trung, Tân Chánh Hiệp của Quận 12");
                    isValid = false;
                }

                edtAdress.setError(null);
            }

            if (isValid) {
                saveAddress(address);

                Intent intent = new Intent(OrderInformationActivity.this, PaymentActivity.class);

                intent.putExtra("combo", combo); // Passing Combo object
                intent.putExtra("totalAmount", getIntent().getIntExtra("totalAmount", 0)); // Pass total amount
                intent.putExtra("fullName", fullName); // Pass full name
                intent.putExtra("phoneNumber", phoneNumber); // Pass phone number
                intent.putExtra("address", address); // Pass address (newly added)
                intent.putExtra("note", note); // Pass note
                intent.putExtra("deliveryTime", deliveryTime); // Pass estimated delivery time

                startActivity(intent);
            }
        }

        //  Chuẩn hóa chuỗi đầu vào: loại bỏ dấu và chuyển về chữ thường
        private String normalizeString(String input) {
            String normalized = Normalizer.normalize(input, Normalizer.Form.NFD);
            normalized = normalized.replaceAll("[^\\p{ASCII}]", "");
            return normalized.toLowerCase();
        }


        private void loadSavedAddresses() {
            SharedPreferences preferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
            Set<String> savedAddresses = preferences.getStringSet(KEY_ADDRESSES, new HashSet<>());

            layoutOldAddresses.removeAllViews();
            for (String address : savedAddresses) {
                Button addressButton = new Button(this);
                addressButton.setText(address);
                addressButton.setOnClickListener(v -> edtAdress.setText(address));
                layoutOldAddresses.addView(addressButton);
            }
        }

        // Save a new address to SharedPreferences for future use
        private void saveAddress(String address) {
            SharedPreferences preferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
            Set<String> savedAddresses = preferences.getStringSet(KEY_ADDRESSES, new HashSet<>());
            savedAddresses.add(address);

            SharedPreferences.Editor editor = preferences.edit();
            editor.putStringSet(KEY_ADDRESSES, savedAddresses);
            editor.apply();
        }
    }
