package USER.Home;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import com.example.duan1.R;

import USER.product.productActivity;

public class HomeActivity extends AppCompatActivity {

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_home);

        // Tham chiếu đến img1 và img2
        findViewById(R.id.img1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, productActivity.class);
                intent.putExtra("image_resource", R.drawable.monchinh); // Pass img1 resource ID
                startActivity(intent);
            }
        });

        findViewById(R.id.img2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, productActivity.class);
                intent.putExtra("image_resource", R.drawable.monphu); // Pass img2 resource ID
                startActivity(intent);
            }
        });
        findViewById(R.id.tv1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Chuyển sang ProductActivity khi nhấn vào img1
                Intent intent = new Intent(HomeActivity.this, productActivity.class);
                startActivity(intent);
            }
        });
        findViewById(R.id.tv2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Chuyển sang ProductActivity khi nhấn vào img1
                Intent intent = new Intent(HomeActivity.this, productActivity.class);
                startActivity(intent);
            }
        });
    }
}
