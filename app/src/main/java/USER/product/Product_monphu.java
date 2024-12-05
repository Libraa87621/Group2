package USER.product;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.duan1.R;

import USER.choosefood.choosefoodActivity;

public class Product_monphu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.product);

        ImageView imghome = findViewById(R.id.imghome);
        Button btnOrder = findViewById(R.id.btnOrder);
        Button btnAddToCart = findViewById(R.id.btn_addtocart);

        // Get image resource ID and product name from intent
        int imageResId = getIntent().getIntExtra("image_resource", -1);
        String productName = getIntent().getStringExtra("product_name");


    }
}
