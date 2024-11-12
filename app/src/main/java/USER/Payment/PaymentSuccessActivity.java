package USER.Payment;

import android.content.Intent;
import android.os.Bundle;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_success);

        // Retrieve Combo object and total amount from intent
        Combo combo = getIntent().getParcelableExtra("combo");
        int totalAmount = getIntent().getIntExtra("totalAmount", 0);

        // Find the container LinearLayout to dynamically add item views
        LinearLayout container = findViewById(R.id.containerLinearLayout);

        // Initialize DecimalFormat for formatting prices
        DecimalFormat formatter = new DecimalFormat("#,###");

        if (combo != null) {
            String nameChicken = combo.getNameChicken();
            String priceChicken = combo.getPriceChicken();
            String namePotato = combo.getNamePotato();
            String pricePotato = combo.getPricePotato();
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

            // Populate ingredient details with quantity and price formatting
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

            // Add the item layout to container dynamically
            container.addView(itemLayout);
        }

        // Display the total price with formatting
        TextView totalPriceTextView = findViewById(R.id.tvTotalAmountValue);
        totalPriceTextView.setText(formatter.format(totalAmount) + " VND");
    }
}
