package USER.Order;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
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

        // Retrieve Combo object from Intent
        Combo combo = getIntent().getParcelableExtra("combo");

        LinearLayout container = findViewById(R.id.containerLinearLayout);
        DecimalFormat formatter = new DecimalFormat("#,###");

        int total = 0;
        if (combo != null) {
            String nameChicken = combo.getNameChicken();
            String priceChicken = combo.getPriceChicken();
            String namePotato = combo.getNamePotato();
            String pricePotato = combo.getPricePotato();
            total = combo.getTotal();
            int quantity = combo.getQuantity();
            int imageResId = combo.getImageResId();

            LayoutInflater inflater = LayoutInflater.from(this); // lệnh lưu trữ combo lên linerlayout
            LinearLayout itemLayout = (LinearLayout) inflater.inflate(R.layout.item_combo, container, false);

            ImageView imgCombo = itemLayout.findViewById(R.id.imgCombo);
            TextView tvComboName = itemLayout.findViewById(R.id.tvComboName);
            TextView tvingredientChicken = itemLayout.findViewById(R.id.tvingredientChicken);
            TextView tvingredientPotato = itemLayout.findViewById(R.id.tvingredientPotato);



            // Set Combo Name with both items
            tvComboName.setText(nameChicken + " + " + namePotato);

            // nếu name chicken được chọn thì ...
            if (!nameChicken.isEmpty()) {
                tvingredientChicken.setText(quantity + " x " + nameChicken + " + " + formatter.format(Integer.parseInt(priceChicken)) + " ₫");
            } else {
                tvingredientChicken.setVisibility(View.GONE);
            }

            if (!namePotato.isEmpty()) {
                tvingredientPotato.setText(quantity + " x " + namePotato + " + " + formatter.format(Integer.parseInt(pricePotato)) + " ₫");
            } else {
                tvingredientPotato.setVisibility(View.GONE);
            }

            imgCombo.setImageResource(imageResId);
            container.addView(itemLayout);

            TextView totalPriceTextView = findViewById(R.id.totalPrice);
            totalPriceTextView.setText(formatter.format(total) + " VND");
        }

        Button btnConfirmOrder = findViewById(R.id.btnConfirmOrder);
        int finalTotal = total;

        btnConfirmOrder.setOnClickListener(view -> {
            Intent intent = new Intent(CheckOderActivity.this, OrderInformationActivity.class);

            // truyền combo sang OrderInformationActivity
            intent.putExtra("combo", combo);

            // truyền tông tiền sang OrderInformationActivity
            intent.putExtra("totalAmount", finalTotal);

            startActivity(intent);
        });
    }
}
