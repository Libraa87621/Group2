package USER.checkOder;

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
            String nameChicken = combo.getNameChicken();
            String priceChicken = combo.getPriceChicken();
            String namePotato = combo.getNamePotato();
            String pricePotato = combo.getPricePotato();
            int total = combo.getTotal();
            int quantity = combo.getQuantity();
            int imageResId = combo.getImageResId();

            LayoutInflater inflater = LayoutInflater.from(this);
            LinearLayout itemLayout = (LinearLayout) inflater.inflate(R.layout.item_combo, container, false);

            ImageView imgCombo = itemLayout.findViewById(R.id.imgCombo);
            TextView tvComboName = itemLayout.findViewById(R.id.tvComboName);
            TextView tvingredientChicken = itemLayout.findViewById(R.id.tvingredientChicken);
            TextView tvingredientPotato = itemLayout.findViewById(R.id.tvingredientPotato);

            // Hiển thị từng món nếu đã chọn
            if (!nameChicken.isEmpty()) {
                tvingredientChicken.setText(quantity + " x " + nameChicken + " + " + formatter.format(Integer.parseInt(priceChicken)) + " đ");
            } else {
                tvingredientChicken.setVisibility(View.GONE); // Ẩn TextView nếu không chọn món gà
            }

            if (!namePotato.isEmpty()) {
                tvingredientPotato.setText(quantity + " x " + namePotato + " + " + formatter.format(Integer.parseInt(pricePotato)) + " đ");
            } else {
                tvingredientPotato.setVisibility(View.GONE); // Ẩn TextView nếu không chọn khoai tây
            }

            tvComboName.setText(nameChicken.isEmpty() ? namePotato : (namePotato.isEmpty() ? nameChicken : nameChicken + " + " + namePotato));
            imgCombo.setImageResource(imageResId);
            container.addView(itemLayout);

            TextView totalPriceTextView = findViewById(R.id.totalPrice);
            totalPriceTextView.setText(formatter.format(total) + " VND");
        }
    }
}
