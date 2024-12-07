package ADMIN.fragment.OrdersFragment;

import android.app.AlertDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_datdo_adm, container, false);

        recyclerView = view.findViewById(R.id.recyclerView);
        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 2);
        recyclerView.setLayoutManager(layoutManager);

        // Lấy dữ liệu từ SharedPreferences hoặc dùng dữ liệu mặc định
        orderList = getOrdersFromPreferences();
        if (orderList.isEmpty()) {
            orderList = getDefaultOrders();
        }

        ordersAdapter = new OrdersAdapter(orderList);
        ordersAdapter.setEditOrderListener((order, position) -> openAddOrderDialog(order, position));
        recyclerView.setAdapter(ordersAdapter);

        Button btnAddOrder = view.findViewById(R.id.btnAddProduct);
        btnAddOrder.setOnClickListener(v -> openAddOrderDialog(null, -1));

        return view;
    }

    // Tạo danh sách dữ liệu mặc định
    private List<Orders> getDefaultOrders() {
        List<Orders> defaultOrders = new ArrayList<>();
        defaultOrders.add(new Orders("COMBO VUI VẺ", "160000", "2 Miếng gà giòn, 1 nước, 1 khoai chiên", R.drawable.combovuive));
        defaultOrders.add(new Orders("COMBO NO NÊ", "130000", "1 phần cơm gà, 1 nước, 1 súp cà rốt", R.drawable.combonone));
        defaultOrders.add(new Orders("COMBO SOLO", "120000", "1 hamburger tôm, 1 nước, 1 khoai chiên", R.drawable.combosolo));
        defaultOrders.add(new Orders("BÁNH NHÂN XOÀI", "50000", "1 Bánh nhân xoài thơm vị ngọt", R.drawable.banhnhanxoai));
        defaultOrders.add(new Orders("NƯỚC ÉP XOÀI", "30000", "1 Cốc nước ép xoài", R.drawable.nuocepxoai));
        return defaultOrders;
    }

    // Mở dialog thêm hoặc sửa đơn hàng
    private void openAddOrderDialog(Orders orderToEdit, int position) {
        View dialogView = LayoutInflater.from(getContext()).inflate(R.layout.dialog_add_product, null);
        EditText orderNameInput = dialogView.findViewById(R.id.productNameInput);
        EditText orderPriceInput = dialogView.findViewById(R.id.productPriceInput);
        EditText orderDescriptionInput = dialogView.findViewById(R.id.productDescriptionInput);

        if (orderToEdit != null) {
            orderNameInput.setText(orderToEdit.getName());
            orderPriceInput.setText(orderToEdit.getPrice());
            orderDescriptionInput.setText(orderToEdit.getDescription());
        }

        new AlertDialog.Builder(getContext())
                .setTitle(orderToEdit == null ? "Thêm đơn hàng mới" : "Chỉnh sửa đơn hàng")
                .setView(dialogView)
                .setPositiveButton("Lưu", (dialog, which) -> {
                    String orderName = orderNameInput.getText().toString().trim();
                    String orderPrice = orderPriceInput.getText().toString().trim();
                    String orderDescription = orderDescriptionInput.getText().toString().trim();

                    if (orderName.isEmpty() || orderPrice.isEmpty() || orderDescription.isEmpty()) {
                        Toast.makeText(getContext(), "Vui lòng nhập đầy đủ thông tin!", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    try {
                        double price = Double.parseDouble(orderPrice);
                        if (price <= 0) {
                            Toast.makeText(getContext(), "Giá đơn hàng phải lớn hơn 0!", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        if (orderToEdit == null) {
                            orderList.add(new Orders(orderName, String.format("%.2f", price), orderDescription, R.drawable.default_image));
                            Toast.makeText(getContext(), "Thêm đơn hàng thành công!", Toast.LENGTH_SHORT).show();
                        } else {
                            orderToEdit.setName(orderName);
                            orderToEdit.setPrice(String.format("%.2f", price));
                            orderToEdit.setDescription(orderDescription);
                            Toast.makeText(getContext(), "Cập nhật đơn hàng thành công!", Toast.LENGTH_SHORT).show();
                        }

                        ordersAdapter.notifyDataSetChanged();
                        saveOrdersToPreferences(); // Lưu sau khi thêm hoặc chỉnh sửa
                    } catch (NumberFormatException e) {
                        Toast.makeText(getContext(), "Vui lòng nhập giá hợp lệ!", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("Hủy", null)
                .create()
                .show();
    }

    // Lưu danh sách đơn hàng vào SharedPreferences
    private void saveOrdersToPreferences() {
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("OrdersPrefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        StringBuilder data = new StringBuilder();
        for (Orders order : orderList) {
            data.append(order.getName()).append("|")
                    .append(order.getPrice()).append("|")
                    .append(order.getDescription()).append("|")
                    .append(order.getImageId()).append("\n");
        }

        editor.putString("orders", data.toString());
        editor.apply();
    }

    // Đọc danh sách đơn hàng từ SharedPreferences
    private List<Orders> getOrdersFromPreferences() {
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("OrdersPrefs", Context.MODE_PRIVATE);
        String savedData = sharedPreferences.getString("orders", null);

        List<Orders> orders = new ArrayList<>();
        if (savedData != null) {
            String[] rows = savedData.split("\n");
            for (String row : rows) {
                String[] fields = row.split("\\|");
                if (fields.length == 4) {
                    String name = fields[0];
                    String price = fields[1];
                    String description = fields[2];
                    int imageId = Integer.parseInt(fields[3]);
                    orders.add(new Orders(name, price, description, imageId));
                }
            }
        }
        return orders;
    }
}
