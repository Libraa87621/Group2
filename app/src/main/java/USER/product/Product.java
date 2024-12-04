package USER.product;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.duan1.R;

import USER.choosefood.ChoosefoodActivity2;
import USER.choosefood.choosefoodActivity;

public class Product extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.product);

        ImageView imghome = findViewById(R.id.imghome);
        Button btnOrder = findViewById(R.id.btnOrder);

        // Get image resource ID from intent
        int imageResId = getIntent().getIntExtra("image_resource", -1);
        if (imageResId != -1) {
            imghome.setImageResource(imageResId);
        }

        btnOrder.setOnClickListener(v -> {
            Intent intent = new Intent(Product.this, ChoosefoodActivity2.class);
            startActivity(intent);
        });
    }
}
