package ADMIN.fragment.SettingsFragment;

import android.app.AlertDialog;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.duan1.R;


import java.util.ArrayList;
import java.util.List;

import ADMIN.fragment.ProfileFragment.Customer;
import ADMIN.fragment.ProfileFragment.CustomerAdapter;
import Database.DBHelper;

public class SettingsFragment extends Fragment {
    private RecyclerView recyclerViewOrders;
    private SettingAdapter settingAdapter;
    private List<Setting> settingList;
    private SettingAdapter adapter;
    private DBHelper dbHelper;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_donhang_adm, container, false);

        dbHelper = new DBHelper(getContext()); // Khởi tạo DBHelper

        recyclerViewOrders = rootView.findViewById(R.id.recyclerViewOrders);
        recyclerViewOrders.setLayoutManager(new LinearLayoutManager(getContext()));


        settingList = new ArrayList<>();
        adapter = new SettingAdapter(settingList);
        recyclerViewOrders.setAdapter(adapter);

        // Lấy dữ liệu từ cả hai bảng
        Cursor userCursor = dbHelper.getAllUsers();
        Cursor orderCursor = dbHelper.getAllOders();

        if (userCursor != null && userCursor.moveToFirst() && orderCursor != null && orderCursor.moveToFirst()) {
            do {
                String name = userCursor.getString(userCursor.getColumnIndexOrThrow(DBHelper.COLUMN_NAME));
                String date = orderCursor.getString(orderCursor.getColumnIndexOrThrow(DBHelper.COLUMN_DATE));
                String components = orderCursor.getString(orderCursor.getColumnIndexOrThrow(DBHelper.COLUMN_COMPONENTS));

                // Thêm dữ liệu vào danh sách
                settingList.add(new Setting(name, date, components));

            } while (userCursor.moveToNext() && orderCursor.moveToNext());

            userCursor.close();
            orderCursor.close();
        } else {
            Toast.makeText(getContext(), "Không có dữ liệu", Toast.LENGTH_SHORT).show();
        }

        // Cập nhật adapter với danh sách người dùng
        settingAdapter = new SettingAdapter(settingList);
        recyclerViewOrders.setAdapter(settingAdapter);


        // Các nút thêm, sửa, xóa
        ImageView btnthem = rootView.findViewById(R.id.btnthem);
        btnthem.setOnClickListener(v -> showCustomerDialog(false, null));

        ImageView btnsua = rootView.findViewById(R.id.btnsua);
        btnsua.setOnClickListener(v -> {
            List<Setting> selectedSetting = adapter.getSelectedSetting();
            if (selectedSetting.isEmpty()) {
                showAlert("Bạn cần phải chọn cần sửa");
            } else if (selectedSetting.size() == 1) {
                showCustomerDialog(true, selectedSetting.get(0));
            } else {
                showAlert("Chỉ chọn một khách hàng để sửa");
            }
        });

        ImageView btxoa = rootView.findViewById(R.id.btxoa);
        btxoa.setOnClickListener(v -> {
            List<Setting> selectedCustomers = adapter.getSelectedSetting();
            if (selectedCustomers.isEmpty()) {
                showAlert("Bạn cần phải chọn cần xóa");
            } else {
                adapter.removeSelectedItems();
            }
        });

        return rootView;



    }
    private void showCustomerDialog(boolean isEdit, Setting setting) {
        View dialogView = LayoutInflater.from(getContext()).inflate(R.layout.dialog_customer_info, null);
        EditText etDate = dialogView.findViewById(R.id.etDate);
        EditText etCustomerName = dialogView.findViewById(R.id.etCustomerName);
        EditText etItems = dialogView.findViewById(R.id.etItems);

        // Nếu là chế độ sửa, hiển thị thông tin hiện tại
        if (isEdit && setting != null) {
            etDate.setText(setting.getDate());
            etCustomerName.setText(setting.getCustomerName());
            etItems.setText(setting.getItems());
        }

        new AlertDialog.Builder(getContext())
                .setTitle(isEdit ? "Sửa thông tin đơn hàng" : "Thêm đơn hàng mới")
                .setView(dialogView)
                .setPositiveButton("Lưu", null) // Để xử lý lưu thủ công
                .setNegativeButton("Hủy", null)
                .create()
                .show();

        AlertDialog dialog = new AlertDialog.Builder(getContext())
                .setView(dialogView)
                .setPositiveButton("Lưu", null)
                .setNegativeButton("Hủy", (dlg, which) -> dlg.dismiss())
                .create();

        dialog.setOnShowListener(dialogInterface -> {
            dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(v -> {
                String date = etDate.getText().toString().trim();
                String name = etCustomerName.getText().toString().trim();
                String items = etItems.getText().toString().trim();

                // Kiểm tra dữ liệu đầu vào
                if (name.isEmpty() || date.isEmpty() || items.isEmpty()) {
                    Toast.makeText(getContext(), "Vui lòng điền đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Lưu thông tin hoặc cập nhật danh sách
                if (isEdit && setting != null) {
                    setting.setDate(date);
                    setting.setCustomerName(name);
                    setting.setItems(items);
                    adapter.notifyDataSetChanged();
                } else {
                    Setting newSetting = new Setting(date, name, items);
                    adapter.addItem(newSetting);
                }

                dialog.dismiss();
            });
        });

        dialog.show();
    }

    private void addSampleData() {
        Setting item1 = new Setting("10/5/2024", "Nguyễn Ngọc Duy", "- 2 Miếng gà giòn\n- 1 nước\n- 1 khoai chiên", R.drawable.anhsanpham, R.drawable.man);
        adapter.addItem(item1);
    }
    private void showAlert(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }
}