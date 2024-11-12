package USER.Payment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.example.duan1.R;
import USER.choosefood.Combo;
import java.text.DecimalFormat;

public class PaymentSuccessActivity extends AppCompatActivity {

    private TextView tvPaymentMethodValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_success);

        // Initialize UI components
        TextView tvPromotionValue = findViewById(R.id.tvPromotionValue);
        TextView totalPriceTextView = findViewById(R.id.tvTotalAmountValue);
        LinearLayout container = findViewById(R.id.containerLinearLayout);
        tvPaymentMethodValue = findViewById(R.id.tvPaymentMethodValue);
        TextView tvAddressValue = findViewById(R.id.tvAddressValue);
        TextView tvPhoneValue = findViewById(R.id.tvPhoneValue);
        TextView tvEstimatedTimeValue = findViewById(R.id.tvEstimatedTime);
        TextView tvShippingFeeValue = findViewById(R.id.tvShippingFeeValue);

        // Retrieve data from Intent
        String promoCode = getIntent().getStringExtra("promoCode");
        int totalAmount = getIntent().getIntExtra("totalAmount", 0);  // Retrieve the total amount
        Combo combo = getIntent().getParcelableExtra("combo");
        String address = getIntent().getStringExtra("address");
        String phone = getIntent().getStringExtra("phoneNumber");
        String shippingFee = getIntent().getStringExtra("shippingFee");
        String paymentMethod = getIntent().getStringExtra("paymentMethod");

        // Logging values to ensure data is being passed
        Log.d("PaymentSuccess", "Promo Code: " + promoCode);
        Log.d("PaymentSuccess", "Total Amount: " + totalAmount);
        Log.d("PaymentSuccess", "Address: " + address);
        Log.d("PaymentSuccess", "Phone: " + phone);
        Log.d("PaymentSuccess", "Shipping Fee: " + shippingFee);
        Log.d("PaymentSuccess", "Payment Method: " + paymentMethod);

        // Set the promotion code or default message  tvPromotionValue.setText(promoCode != null ? promoCode : "No promo code applied");
        DecimalFormat formatter = new DecimalFormat("#,###");
        totalPriceTextView.setText(formatter.format(totalAmount) + " VND"); // This will update the total amount TextView
        tvPromotionValue.setText(promoCode != null ? promoCode : "No promo code applied");
        // Display the total price with formatting

        // Display phone number
        tvPhoneValue.setText(phone != null && !phone.isEmpty() ? phone : "N/A");

        // Get delivery time based on the address
        String deliveryTime = getDeliveryTimeBasedOnAddress(address);
        tvEstimatedTimeValue.setText(deliveryTime);

        // Initialize DecimalFormat for formatting prices

        // Populate combo item details if combo is not null
        if (combo != null) {
            String nameChicken = combo.getNameChicken() != null ? combo.getNameChicken() : "";
            String priceChicken = combo.getPriceChicken() != null ? combo.getPriceChicken() : "0";
            String namePotato = combo.getNamePotato() != null ? combo.getNamePotato() : "";
            String pricePotato = combo.getPricePotato() != null ? combo.getPricePotato() : "0";
            int quantity = combo.getQuantity();
            int imageResId = combo.getImageResId();

            // Inflate item_combo layout and populate details
            LayoutInflater inflater = LayoutInflater.from(this);
            LinearLayout itemLayout = (LinearLayout) inflater.inflate(R.layout.item_combo, container, false);

            // Find and populate item layout views
            ImageView imgCombo = itemLayout.findViewById(R.id.imgCombo);
            TextView tvComboName = itemLayout.findViewById(R.id.tvComboName);
            TextView tvingredientChicken = itemLayout.findViewById(R.id.tvingredientChicken);
            TextView tvingredientPotato = itemLayout.findViewById(R.id.tvingredientPotato);

            // Set ingredient details with quantity and price formatting
            if (!nameChicken.isEmpty()) {
                tvingredientChicken.setText(quantity + " x " + nameChicken + " + " + formatter.format(Integer.parseInt(priceChicken)) + " đ");
            } else {
                tvingredientChicken.setVisibility(View.GONE); // Hide if not selected
            }

            if (!namePotato.isEmpty()) {
                tvingredientPotato.setText(quantity + " x " + namePotato + " + " + formatter.format(Integer.parseInt(pricePotato)) + " đ");
            } else {
                tvingredientPotato.setVisibility(View.GONE); // Hide if not selected
            }

            // Set combo name and image
            tvComboName.setText(nameChicken.isEmpty() ? namePotato : (namePotato.isEmpty() ? nameChicken : nameChicken + " + " + namePotato));
            imgCombo.setImageResource(imageResId);

            // Add the item layout to the container dynamically
            container.addView(itemLayout);
        }

        // Set address and shipping fee
        tvAddressValue.setText(address != null && !address.isEmpty() ? address : "N/A");
        tvShippingFeeValue.setText(shippingFee != null && !shippingFee.isEmpty() ? shippingFee + " VND" : "0 VND");

        // Set payment method
        tvPaymentMethodValue.setText(paymentMethod != null && !paymentMethod.isEmpty() ? paymentMethod : "No payment method provided");
    }

    // This method returns the estimated delivery time based on the address
    private String getDeliveryTimeBasedOnAddress(String address) {
        if (address == null || address.isEmpty()) {
            return "Thời gian giao hàng không xác định";  // Default message if address is null or empty
        }

        // Check for specific address keywords and return the corresponding delivery time
        if (address.toLowerCase().contains("trung my tay")) {
            return "30 phút";
        } else if (address.toLowerCase().contains("quang trung")) {
            return "45 phút";
        } else if (address.toLowerCase().contains("tan chanh hiep")) {
            return "40 phút";
        } else {
            return "Thời gian giao hàng không xác định";  // Default message for other addresses
        }
    }
}
