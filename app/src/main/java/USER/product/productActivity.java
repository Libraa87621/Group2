package USER.product;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import com.example.duan1.R;

import USER.choosefood.choosefoodActivity;

public class productActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);

        ImageView imghome = findViewById(R.id.imghome);
        Button btnOrder = findViewById(R.id.btnOrder);

        // Get image resource ID from intent
        int imageResId = getIntent().getIntExtra("image_resource", -1);
        if (imageResId != -1) {
            imghome.setImageResource(imageResId);
        }

        btnOrder.setOnClickListener(v -> {
            Intent intent = new Intent(productActivity.this, choosefoodActivity.class);
            startActivity(intent);
        });
    }
}
