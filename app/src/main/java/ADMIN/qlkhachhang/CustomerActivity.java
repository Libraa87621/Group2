package ADMIN.qlkhachhang;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.duan1.R;

import java.util.ArrayList;
import java.util.List;

public class CustomerActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private CustomerAdapter adapter;
    private List<Customer> customerItemList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.example.duan1.R.layout.activity_khachhang_adm);

        recyclerView = findViewById(R.id.recyclerViewCustomers);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        customerItemList = new ArrayList<>();
        adapter = new CustomerAdapter(customerItemList);
        recyclerView.setAdapter(adapter);

        // Thêm item mẫu
        addSampleData();
    }

    private void addSampleData() {
        Customer item1 = new Customer("10/5/2024", "Nguyễn Ngọc Duy", "- 2 Miếng gà giòn\n- 1 nước\n- 1 khoai chiên", R.drawable.anhsanpham, R.drawable.man);
        adapter.addItem(item1);

        Customer item2 = new Customer("11/5/2024", "Lê Thị Lan", "- 3 Miếng gà giòn\n- 2 nước", R.drawable.anhsanpham, R.drawable.man);
        adapter.addItem(item2);
    }
}