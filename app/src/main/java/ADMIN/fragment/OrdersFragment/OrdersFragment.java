package ADMIN.fragment.OrdersFragment;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.duan1.R;

import java.util.ArrayList;
import java.util.List;

public class OrdersFragment extends Fragment {

    private RecyclerView recyclerView;
    private OrdersAdapter ordersAdapter;
    private List<Orders> orderList;

    public OrdersFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_datdo_adm, container, false);

        // Lấy RecyclerView từ layout
        recyclerView = view.findViewById(R.id.recyclerView);

        // Sử dụng GridLayoutManager để tạo 2 cột
        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 2); // 2 là số cột
        recyclerView.setLayoutManager(layoutManager);

        // Tạo dữ liệu cứng cho danh sách Orders
        orderList = getDefaultOrders();

        // Tạo và gắn adapter
        ordersAdapter = new OrdersAdapter(orderList);
        recyclerView.setAdapter(ordersAdapter);

        // Thiết lập nút để mở dialog thêm đơn hàng
        Button btnAddOrder = view.findViewById(R.id.btnAddProduct); // Giữ nguyên ID nút
        btnAddOrder.setOnClickListener(v -> openAddOrderDialog());

        return view;
    }

    // Hàm khởi tạo danh sách dữ liệu cứng
    private List<Orders> getDefaultOrders() {
        List<Orders> defaultOrders = new ArrayList<>();
        defaultOrders.add(new Orders("COMBO VUI VẺ", "160000", "2 Miếng gà giòn, ", "combovuive"));
        defaultOrders.add(new Orders("COMBO NO NÊ", "130000", "1 phần cơm gà ", "combonone"));
        defaultOrders.add(new Orders("COMBO SOLO", "120000", "1 hamburger tôm ", "combosolo"));
        defaultOrders.add(new Orders("BÁNH NHÂN XOÀI", "50000", "1 Bánh nhân xoài ", "banhnhanxoai"));
        defaultOrders.add(new Orders("NƯỚC ÉP XOÀI", "30000", "1 Cốc nước ép xoài", "nuocepxoai"));
        return defaultOrders;
    }

    // Hàm mở dialog thêm đơn hàng mới
    private void openAddOrderDialog() {
        View dialogView = LayoutInflater.from(getContext()).inflate(R.layout.dialog_add_product, null);
        EditText orderNameInput = dialogView.findViewById(R.id.productNameInput);
        EditText orderPriceInput = dialogView.findViewById(R.id.productPriceInput);

        new AlertDialog.Builder(getContext())
                .setTitle("Thêm đơn hàng mới")
                .setView(dialogView)
                .setPositiveButton("Thêm", (dialog, which) -> {
                    String orderName = orderNameInput.getText().toString();
                    String orderPrice = orderPriceInput.getText().toString();

                    if (!orderName.isEmpty() && !orderPrice.isEmpty()) {
                        orderList.add(new Orders(orderName, orderPrice));
                        ordersAdapter.notifyDataSetChanged();
                    }
                })
                .setNegativeButton("Hủy", null)
                .create()
                .show();
    }
}
