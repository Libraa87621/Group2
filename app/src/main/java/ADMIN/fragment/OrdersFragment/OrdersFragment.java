package ADMIN.fragment.OrdersFragment;

import android.app.AlertDialog;
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
        btnAddOrder.setOnClickListener(v -> openAddOrderDialog(null, -1)); // Mở dialog thêm mới

        return view;
    }

    // Hàm khởi tạo danh sách dữ liệu cứng
    private List<Orders> getDefaultOrders() {
        List<Orders> defaultOrders = new ArrayList<>();
        defaultOrders.add(new Orders("COMBO VUI VẺ", "160000", "2 Miếng gà giòn, 1 nước, 1 khoai chiên", R.drawable.combovuive));
        defaultOrders.add(new Orders("COMBO NO NÊ", "130000", "1 phần cơm gà, 1 nước, 1 súp cà rốt", R.drawable.combonone));
        defaultOrders.add(new Orders("COMBO SOLO", "120000", "1 hamburger tôm, 1 nước, 1 khoai chiên", R.drawable.combosolo));
        defaultOrders.add(new Orders("BÁNH NHÂN XOÀI", "50000", "1 Bánh nhân xoài thơm vị ngọt", R.drawable.banhnhanxoai));
        defaultOrders.add(new Orders("NƯỚC ÉP XOÀI", "30000", "1 Cốc nước ép xoài", R.drawable.nuocepxoai));
        return defaultOrders;
    }

    // Hàm mở dialog thêm hoặc sửa đơn hàng
    private void openAddOrderDialog(Orders orderToEdit, int position) {
        // Sử dụng lại layout dialog thêm sản phẩm
        View dialogView = LayoutInflater.from(getContext()).inflate(R.layout.dialog_add_product, null);
        EditText orderNameInput = dialogView.findViewById(R.id.productNameInput);
        EditText orderPriceInput = dialogView.findViewById(R.id.productPriceInput);

        // Nếu đang sửa đơn hàng, điền thông tin vào các input
        if (orderToEdit != null) {
            orderNameInput.setText(orderToEdit.getName());
            orderPriceInput.setText(orderToEdit.getPrice());
        }

        new AlertDialog.Builder(getContext())
                .setTitle(orderToEdit == null ? "Thêm đơn hàng mới" : "Chỉnh sửa đơn hàng")
                .setView(dialogView)
                .setPositiveButton("Lưu", (dialog, which) -> {
                    String orderName = orderNameInput.getText().toString().trim();
                    String orderPrice = orderPriceInput.getText().toString().trim();

                    if (orderName.isEmpty() || orderPrice.isEmpty()) {
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
                            // Nếu là thêm mới đơn hàng
                            orderList.add(new Orders(orderName, String.format("%.2f", price)));
                            Toast.makeText(getContext(), "Thêm đơn hàng thành công!", Toast.LENGTH_SHORT).show();
                        } else {
                            // Nếu là sửa đơn hàng
                            orderToEdit.setName(orderName);
                            orderToEdit.setPrice(String.format("%.2f", price));
                            Toast.makeText(getContext(), "Cập nhật đơn hàng thành công!", Toast.LENGTH_SHORT).show();
                        }

                        // Cập nhật adapter
                        ordersAdapter.notifyDataSetChanged();
                    } catch (NumberFormatException e) {
                        Toast.makeText(getContext(), "Vui lòng nhập giá hợp lệ!", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("Hủy", null)
                .create()
                .show();
        }
    private void openEditOrderDialog(Orders orderToEdit, int position) {
        // Tạo View cho dialog sửa đơn hàng
        View dialogView = LayoutInflater.from(getContext()).inflate(R.layout.dialog_add_product, null);
        EditText orderNameInput = dialogView.findViewById(R.id.productNameInput);
        EditText orderPriceInput = dialogView.findViewById(R.id.productPriceInput);
        EditText orderDescriptionInput = dialogView.findViewById(R.id.orderDescription);

        // Điền thông tin đơn hàng vào các input
        orderNameInput.setText(orderToEdit.getName());
        orderPriceInput.setText(orderToEdit.getPrice());
        orderDescriptionInput.setText(orderToEdit.getDescription());

        // Tạo dialog và xử lý sự kiện lưu
        new AlertDialog.Builder(getContext())
                .setTitle("Chỉnh sửa đơn hàng")
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
                            Toast.makeText(getContext(), "Giá phải lớn hơn 0!", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        // Cập nhật thông tin đơn hàng
                        orderToEdit.setName(orderName);
                        orderToEdit.setPrice(orderPrice);
                        orderToEdit.setDescription(orderDescription);

                        // Cập nhật RecyclerView
                        ordersAdapter.notifyItemChanged(position);
                        Toast.makeText(getContext(), "Cập nhật đơn hàng thành công!", Toast.LENGTH_SHORT).show();
                    } catch (NumberFormatException e) {
                        Toast.makeText(getContext(), "Giá không hợp lệ!", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("Hủy", null)
                .create()
                .show();
    }
}

