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
        private static final String PREFS_NAME = "OrderPrefs";
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

            // Initialize Buttons
            btnChangeAddress = findViewById(R.id.btnChangeAddress);
            btnNext = findViewById(R.id.btnNext);

            // Initialize RadioButtons
            rdokHomeDelivery = findViewById(R.id.rdoHomeDelivery);
            rdoPickUpAtStore = findViewById(R.id.rdoPickUpAtStore);

            // Initialize layout for old addresses
            layoutOldAddresses = findViewById(R.id.layoutOldAddresses);

            // Retrieve the Combo object passed from previous activity
            Combo combo = getIntent().getParcelableExtra("combo");
            if (combo == null) {
                combo = new Combo("Default Combo", "", "", "", 0, 0, 0); // Set default values in case combo is null
            }

            // Initialize and set total amount in TextView
            TextView tvTotalAmount = findViewById(R.id.tvTotalAmount);
            int totalAmount = getIntent().getIntExtra("totalAmount", 0);
            DecimalFormat formatter = new DecimalFormat("#,###");
            tvTotalAmount.setText(formatter.format(totalAmount) + " VND");

            // Retrieve and display saved addresses
            loadSavedAddresses();

            // Handle Next button click
            final Combo finalCombo = combo;

            btnNext.setOnClickListener(v -> onNextButtonClick(finalCombo));
        }

        // Method to handle Next button click
        public void onNextButtonClick(Combo combo) {
            String fullName = edtFullName.getText().toString().trim();
            String phoneNumber = edtNumberPhone.getText().toString().trim();
            String address = edtAdress.getText().toString().trim();
            String note = edtNote.getText().toString().trim(); // Get note text

            boolean isValid = true;
            String deliveryTime = "";

            // Validate Full Name
            if (fullName.isEmpty()) {
                edtFullName.setError("Vui lòng nhập họ và tên");
                isValid = false;
            } else {
                edtFullName.setError(null);
            }

            // Validate Phone Number
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
                // Normalize the address to remove accents and check for "quan 12" (case insensitive)
                String normalizedAddress = normalizeString(address);

                // Check if the address belongs to one of the 3 specific areas
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

            // Only proceed if all fields are valid
            if (isValid) {
                // Save address for future use
                saveAddress(address);

                // Create an Intent to navigate to PaymentActivity
                Intent intent = new Intent(OrderInformationActivity.this, PaymentActivity.class);

                // Pass necessary data (including the Combo and delivery time)
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

        // Normalize a string by removing accents
        private String normalizeString(String input) {
            // Use Normalizer to normalize the string
            String normalized = Normalizer.normalize(input, Normalizer.Form.NFD);
            normalized = normalized.replaceAll("[^\\p{ASCII}]", ""); // Remove non-ASCII characters (accents, etc.)
            return normalized.toLowerCase(); // Convert to lowercase for case-insensitive comparison
        }

        // Load saved addresses from SharedPreferences and display them as buttons
        private void loadSavedAddresses() {
            SharedPreferences preferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
            Set<String> savedAddresses = preferences.getStringSet(KEY_ADDRESSES, new HashSet<>());

            layoutOldAddresses.removeAllViews(); // Clear old buttons
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
