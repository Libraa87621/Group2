package USER.product;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.duan1.R;

import java.util.ArrayList;

public class CartActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        // Retrieve the cart list passed from productActivity
        ArrayList<Cart> cartList = (ArrayList<Cart>) getIntent().getSerializableExtra("cart_list");

        if (cartList == null || cartList.isEmpty()) {
            Toast.makeText(this, "Giỏ hàng trống", Toast.LENGTH_SHORT).show();
            return;
        }

        // Map ListView from layout
        ListView listView = findViewById(R.id.cartListView);

        // Set adapter for ListView
        CartAdapter adapter = new CartAdapter(this, cartList);
        listView.setAdapter(adapter);
    }
}
