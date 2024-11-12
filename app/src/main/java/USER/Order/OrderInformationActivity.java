package USER.Order;

import android.content.Intent;
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

        // Initialize and set total amount in TextView
        TextView tvTotalAmount = findViewById(R.id.tvTotalAmount);
        int totalAmount = getIntent().getIntExtra("totalAmount", 0);
        DecimalFormat formatter = new DecimalFormat("#,###");
        tvTotalAmount.setText(formatter.format(totalAmount) + " VND");

        // Retrieve and display saved addresses
        loadSavedAddresses();

        // Add TextWatcher for phone number validation
        edtNumberPhone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                String phoneNumber = edtNumberPhone.getText().toString();
                if (phoneNumber.length() == 10) {
                    if (phoneNumber.charAt(0) != '0') {
                        edtNumberPhone.setError("Số điện thoại phải bắt đầu bằng 0");
                    }
                } else if (phoneNumber.length() > 10) {
                    edtNumberPhone.setError("Số điện thoại chỉ có thể có 10 ký tự");
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });

        // Handle Next button click
        btnNext.setOnClickListener(v -> onNextButtonClick());
    }

    // Load saved addresses from SharedPreferences and display them as buttons
    private void loadSavedAddresses() {
        SharedPreferences preferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        Set<String> savedAddresses = preferences.getStringSet(KEY_ADDRESSES, new HashSet<>());

        layoutOldAddresses.removeAllViews(); // Clear old buttons
        for (String address : savedAddresses) {
            Button addressButton = new Button(this);
            addressButton.setText(address);
            addressButton.setOnClickListener(v -> edtAdress.setText(address)); // Set address on click
            layoutOldAddresses.addView(addressButton); // Add button to layout
        }
    }

    // Save address to SharedPreferences
    private void saveAddress(String address) {
        SharedPreferences preferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        Set<String> savedAddresses = preferences.getStringSet(KEY_ADDRESSES, new HashSet<>());

        // Add the new address and save
        savedAddresses.add(address);
        preferences.edit().putStringSet(KEY_ADDRESSES, savedAddresses).apply();

        // Refresh the displayed buttons
        loadSavedAddresses();
    }

    // Method to handle Next button click
    // Method to handle Next button click
    public void onNextButtonClick() {
        String fullName = edtFullName.getText().toString().trim();
        String phoneNumber = edtNumberPhone.getText().toString().trim();
        String address = edtAdress.getText().toString().trim();
        String note = edtNote.getText().toString().trim(); // Get note text

        boolean isValid = true;

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

        if (address.isEmpty()) {
            edtAdress.setError("Vui lòng nhập địa chỉ");
            isValid = false;
        } else {
            edtAdress.setError(null);
        }

        // Only proceed if all fields are valid
        if (isValid) {
            saveAddress(address); // Save address for future use

            // Create an Intent to navigate to PaymentActivity
            Intent intent = new Intent(OrderInformationActivity.this, PaymentActivity.class);
            intent.putExtra("totalAmount", getIntent().getIntExtra("totalAmount", 0)); // Pass total amount
            intent.putExtra("fullName", fullName); // Pass full name
            intent.putExtra("phoneNumber", phoneNumber); // Pass phone number
            intent.putExtra("address", address); // Pass address
            intent.putExtra("note", note); // Pass note

            startActivity(intent);
        }
    }
}