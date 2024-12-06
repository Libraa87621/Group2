package ADMIN.fragment.SettingsFragment;

import android.app.AlertDialog;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.duan1.R;

import java.util.ArrayList;
import java.util.List;

import Database.DBHelper;

public class SettingsFragment extends Fragment {
    private RecyclerView recyclerViewOrders;
    private SettingAdapter settingAdapter;
    private List<Setting> settingList;
    private DBHelper dbHelper;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_donhang_adm, container, false);
        ImageView confirmButton = rootView.findViewById(R.id.iconxacnhan);
        ImageView cancelButton = rootView.findViewById(R.id.iconhuy);
        dbHelper = new DBHelper(getContext()); // Initialize DBHelper

        recyclerViewOrders = rootView.findViewById(R.id.recyclerViewOrders);
        recyclerViewOrders.setLayoutManager(new LinearLayoutManager(getContext()));

        settingList = new ArrayList<>();
        settingAdapter = new SettingAdapter(settingList);
        recyclerViewOrders.setAdapter(settingAdapter);
        List<Setting> settingsFromDb = dbHelper.getAllSettings();

        if (settingsFromDb.isEmpty()) {
            Toast.makeText(getContext(), "Không có dữ liệu", Toast.LENGTH_SHORT).show();
        } else {
            settingList.addAll(settingsFromDb);
            settingAdapter.notifyDataSetChanged(); // Đảm bảo RecyclerView nhận được dữ liệu
        }

        confirmButton.setOnClickListener(v -> {
            // Kiểm tra danh sách có đơn hàng nào được chọn không
            boolean hasSelectedOrders = false;
            for (Setting setting : settingList) {
                if (setting.isSelected()) {
                    hasSelectedOrders = true;

                    // Kiểm tra order_id
                    int orderId = setting.getOrderId();
                    if (orderId == 0) {
                        Log.e("UpdateStatus", "Invalid Order ID: 0 for setting " + setting);
                        continue;
                    }

                    boolean isUpdated = dbHelper.updateOrderStatus(orderId, "đã giao");

                    if (isUpdated) {
                        Log.d("UpdateStatus", "Order ID: " + orderId + " updated to 'đã giao'");
                        setting.setStatus("đã giao"); // Cập nhật trạng thái trong danh sách
                    } else {
                        Log.e("UpdateStatus", "Failed to update Order ID: " + orderId);
                        Toast.makeText(getContext(), "Cập nhật thất bại cho đơn hàng ID: " + orderId, Toast.LENGTH_SHORT).show();
                    }
                }
            }
            if (hasSelectedOrders) {
                settingAdapter.notifyDataSetChanged(); // Làm mới RecyclerView
                Toast.makeText(getContext(), "Đơn hàng đã được xác nhận", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getContext(), "Vui lòng chọn ít nhất một đơn hàng!", Toast.LENGTH_SHORT).show();
            }
        });

        cancelButton.setOnClickListener(v -> {
            // Kiểm tra danh sách có đơn hàng nào được chọn không
            boolean hasSelectedOrders = false;
            for (Setting setting : settingList) {
                if (setting.isSelected()) {
                    hasSelectedOrders = true;

                    // Kiểm tra order_id
                    int orderId = setting.getOrderId();
                    if (orderId == 0) {
                        Log.e("UpdateStatus", "Invalid Order ID: 0 for setting " + setting);
                        continue;
                    }

                    // Cập nhật trạng thái đơn hàng thành "đã hủy"
                    boolean isUpdated = dbHelper.updateOrderStatus(orderId, "đã hủy");

                    if (isUpdated) {
                        Log.d("UpdateStatus", "Order ID: " + orderId + " updated to 'đã hủy'");
                        setting.setStatus("đã hủy"); // Cập nhật trạng thái trong danh sách
                    } else {
                        Log.e("UpdateStatus", "Failed to update Order ID: " + orderId);
                        Toast.makeText(getContext(), "Cập nhật thất bại cho đơn hàng ID: " + orderId, Toast.LENGTH_SHORT).show();
                    }
                }
            }
            if (hasSelectedOrders) {
                settingAdapter.notifyDataSetChanged(); // Làm mới RecyclerView
                Toast.makeText(getContext(), "Đơn hàng đã bị hủy", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getContext(), "Vui lòng chọn ít nhất một đơn hàng để hủy!", Toast.LENGTH_SHORT).show();
            }
        });




        return rootView;
    }




}

