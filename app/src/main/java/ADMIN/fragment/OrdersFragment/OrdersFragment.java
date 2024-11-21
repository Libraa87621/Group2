import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.duan1.R;

@@ -32,13 +32,18 @@ public class OrdersFragment extends Fragment {
    Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_datdo_adm, container, false);

        // Lấy RecyclerView từ layout
        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Sử dụng GridLayoutManager để tạo 2 cột
        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 2); // 2 là số cột
        recyclerView.setLayoutManager(layoutManager);

        orderList = new ArrayList<>();
        ordersAdapter = new OrdersAdapter(orderList);
        recyclerView.setAdapter(ordersAdapter);

        // Thiết lập nút để mở dialog thêm đơn hàng
        Button btnAddOrder = view.findViewById(R.id.btnAddProduct); // Giữ nguyên ID nút
        btnAddOrder.setOnClickListener(v -> openAddOrderDialog());



        @@ -66,4 +71,4 @@ public class OrdersFragment extends Fragment {
                .create()
                .show();
        }
    }
}