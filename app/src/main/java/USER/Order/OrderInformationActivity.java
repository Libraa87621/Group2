package USER.Order;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.example.duan1.R;
import java.text.DecimalFormat;

public class OrderInformationActivity extends AppCompatActivity {

    private EditText edtFullName, edtNumberPhone, edtAdress, edtNote;
    private Button btnChangeAddress, btnNext;
    private CheckBox chkHomeDelivery, chkPickUpAtStore;

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

        // Initialize CheckBoxes
        chkHomeDelivery = findViewById(R.id.cbHomeDelivery);
        chkPickUpAtStore = findViewById(R.id.cbPickUpAtStore);

        // Initialize and set total amount in TextView
        TextView tvTotalAmount = findViewById(R.id.tvTotalAmount);
        int totalAmount = getIntent().getIntExtra("totalAmount", 0);
        DecimalFormat formatter = new DecimalFormat("#,###");
        tvTotalAmount.setText(formatter.format(totalAmount) + " VND");

        // Add TextWatcher for phone number validation
        edtNumberPhone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int start, int count, int after) {
                // No action needed here
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                // Validate phone number: Must be 10 digits and start with 0
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
                // Additional actions can be performed here if needed
            }
        });

        // Add TextWatcher for other EditText fields if necessary
        edtFullName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int start, int count, int after) {
                // Handle before text change
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                // Handle text changes
            }

            @Override
            public void afterTextChanged(Editable editable) {
                // Handle after text change
            }
        });

        // Handle Change Address button click
        btnChangeAddress.setOnClickListener(v -> {
            // Your action to change the address
        });

        // Handle Next button click
        btnNext.setOnClickListener(v -> onNextButtonClick());
    }

    // Method to handle Next button click
    public void onNextButtonClick() {
        String fullName = edtFullName.getText().toString().trim();
        String phoneNumber = edtNumberPhone.getText().toString().trim();
        String address = edtAdress.getText().toString().trim();
        String note = edtNote.getText().toString().trim();

        // Reset previous error messages
        edtFullName.setError(null);
        edtNumberPhone.setError(null);
        edtAdress.setError(null);

        // Validate input fields
        if (fullName.isEmpty()) {
            edtFullName.setError("Vui lòng nhập họ và tên");
            return;
        }
        if (phoneNumber.isEmpty() || phoneNumber.length() != 10 || phoneNumber.charAt(0) != '0') {
            edtNumberPhone.setError("Số điện thoại không hợp lệ");
            return;
        }
        if (address.isEmpty()) {
            edtAdress.setError("Vui lòng nhập địa chỉ");
            return;
        }

        // You can add further logic for processing the data (e.g., pass data to the next screen)
    }
}
