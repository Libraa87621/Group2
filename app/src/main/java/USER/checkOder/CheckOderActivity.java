package USER.checkOder;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.duan1.R;

public class CheckOderActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_oder);

        // Get data passed from ChooseFoodActivity
        String comboName = getIntent().getStringExtra("comboName");
        String totalAmount = getIntent().getStringExtra("totalAmount");
        int imageResId = getIntent().getIntExtra("imageResId", -1); // Default to -1 if no image passed

        // Check if data is not null or default values
        if (comboName != null && totalAmount != null && imageResId != -1) {
            // Find LinearLayout to display the data
            LinearLayout container = findViewById(R.id.containerLinearLayout);

            // Create TextViews for Combo Name and Total Amount
            TextView tvComboName = new TextView(this);
            tvComboName.setText(comboName);
            tvComboName.setTextSize(17);
            tvComboName.setTextColor(Color.BLACK);
            tvComboName.setPadding(0, 8, 0, 8);

            TextView tvTotalAmount = new TextView(this);
            tvTotalAmount.setText("Total: " + totalAmount);
            tvTotalAmount.setTextSize(15);
            tvTotalAmount.setTextColor(Color.BLACK);
            tvTotalAmount.setPadding(0, 8, 0, 8);

            // Create an ImageView for Combo image
            ImageView imgCombo = new ImageView(this);
            imgCombo.setImageResource(imageResId);

            // Add Views to the LinearLayout container
            container.addView(tvComboName);
            container.addView(tvTotalAmount);
            container.addView(imgCombo);
        }
    }
}
