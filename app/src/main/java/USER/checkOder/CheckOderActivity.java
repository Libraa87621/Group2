package USER.checkOder;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.duan1.R;

import USER.choosefood.Combo;

import java.text.DecimalFormat;

public class CheckOderActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_oder);

        // Get the Combo object passed from ChooseFoodActivity
        Combo combo = getIntent().getParcelableExtra("combo");

        // Find the container LinearLayout where we'll add the inflated item_combo layout
        LinearLayout container = findViewById(R.id.containerLinearLayout);

        // Initialize DecimalFormat for formatting prices
        DecimalFormat formatter = new DecimalFormat("#,###");

        if (combo != null) {
            // Extract Combo details
            String nameChicken = combo.getNameChicken();
            String priceChicken = combo.getPriceChicken();
            String namePotato = combo.getNamePotato();
            String pricePotato = combo.getPricePotato();
            int total = combo.getTotal();
            int quantity = combo.getQuantity();  // Retrieve the quantity for each combo
            int imageResId = combo.getImageResId();

            // Inflate the item_combo.xml layout
            LayoutInflater inflater = LayoutInflater.from(this);
            LinearLayout itemLayout = (LinearLayout) inflater.inflate(R.layout.item_combo, container, false);

            // Find views inside the inflated layout
            ImageView imgCombo = itemLayout.findViewById(R.id.imgCombo);
            TextView tvComboName = itemLayout.findViewById(R.id.tvComboName);
            TextView tvingredientChicken = itemLayout.findViewById(R.id.tvingredientChicken);
            TextView tvingredientPotato = itemLayout.findViewById(R.id.tvingredientPotato);

            // Format the prices
            String formattedPriceChicken = formatter.format(Integer.parseInt(priceChicken));
            String formattedPricePotato = formatter.format(Integer.parseInt(pricePotato));
            String formattedTotal = formatter.format(total);

            // Set data to the views
            tvComboName.setText(nameChicken + " + " + namePotato);  // Combo name
            tvingredientChicken.setText(quantity + " x " + nameChicken + " + " + formattedPriceChicken + " đ");  // Ingredient details for chicken, now showing dynamic quantity
            tvingredientPotato.setText(quantity + " x " + namePotato + " + " + formattedPricePotato + " đ");   // Ingredient details for potato, now showing dynamic quantity
            imgCombo.setImageResource(imageResId);

            // Add inflated itemLayout to the container
            container.addView(itemLayout);

            // Set the total price on the TextView
            TextView totalPriceTextView = findViewById(R.id.totalPrice); // Ensure this ID matches your layout
            totalPriceTextView.setText(formattedTotal + " VND");
        }
    }
}
