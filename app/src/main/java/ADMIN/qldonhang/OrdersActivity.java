package ADMIN.qldonhang;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.duan1.R;

import java.util.ArrayList;
import java.util.List;

public class OrdersActivity extends AppCompatActivity {
    private RecyclerView recyclerViewOrders;
    private OrderAdapter orderAdapter;
    private List<Order> orderList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donhang_adm);

        recyclerViewOrders = findViewById(R.id.recyclerViewOrders);
        recyclerViewOrders.setLayoutManager(new LinearLayoutManager(this));

        orderList = new ArrayList<>();
        // Thêm dữ liệu mẫu
        orderList.add(new Order("10/5/2024", "Nguyễn Ngọc Duy", "- 2 Miếng gà giòn\n- 1 nước\n- 1 khoai chiên", true));
        orderList.add(new Order("11/5/2024", "Lê Văn A", "- 3 Miếng gà giòn\n- 1 nước ngọt", false));

        orderAdapter = new OrderAdapter(orderList);
        recyclerViewOrders.setAdapter(orderAdapter);
    }
}
