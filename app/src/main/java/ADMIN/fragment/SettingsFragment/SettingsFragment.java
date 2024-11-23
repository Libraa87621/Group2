package ADMIN.fragment.SettingsFragment;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
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

        dbHelper = new DBHelper(getContext());
        recyclerViewOrders = rootView.findViewById(R.id.recyclerViewOrders);
        recyclerViewOrders.setLayoutManager(new LinearLayoutManager(getContext()));

        settingList = new ArrayList<>();
        settingAdapter = new SettingAdapter(settingList, new SettingAdapter.OnItemCheckListener() {
            @Override
            public void onItemCheck(Setting setting) {
                // Thêm hoặc sửa
                showEditDialog(setting);
            }

            @Override
            public void onItemUncheck(Setting setting) {
                // Xóa nếu không cần giữ
                deleteSetting(setting);
            }
        });

        recyclerViewOrders.setAdapter(settingAdapter);
        loadSettings();

        return rootView;
    }

    private void loadSettings() {
        settingList.clear();  // Clear current list before adding new data

        Cursor orderCursor = dbHelper.getAllOders();
        if (orderCursor != null) {
            try {
                while (orderCursor.moveToNext()) {
                    int id = orderCursor.getInt(orderCursor.getColumnIndexOrThrow("id"));
                    String date = orderCursor.getString(orderCursor.getColumnIndexOrThrow(DBHelper.COLUMN_DATE));
                    String components = orderCursor.getString(orderCursor.getColumnIndexOrThrow(DBHelper.COLUMN_COMPONENTS));

                    // Add order to setting list
                    settingList.add(new Setting(id, date, components));
                }
            } finally {
                orderCursor.close();
            }
        }

        settingAdapter.notifyDataSetChanged(); // Update RecyclerView
    }

    private void showEditDialog(@Nullable Setting setting) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle(setting == null ? "Thêm mới" : "Chỉnh sửa");

        // Load dialog view
        View dialogView = LayoutInflater.from(getContext()).inflate(R.layout.dialog_add_setting, null);
        EditText dateEditText = dialogView.findViewById(R.id.etDate);
        EditText componentsEditText = dialogView.findViewById(R.id.etItems);

        // If editing, fill in the current values
        if (setting != null) {
            dateEditText.setText(setting.getDate());
            componentsEditText.setText(setting.getComponents());
        }

        builder.setView(dialogView);
        builder.setPositiveButton("Lưu", (dialog, which) -> {
            String date = dateEditText.getText().toString().trim();
            String components = componentsEditText.getText().toString().trim();

            if (date.isEmpty() || components.isEmpty()) {
                Toast.makeText(getContext(), "Vui lòng nhập đầy đủ thông tin!", Toast.LENGTH_SHORT).show();
                return;
            }

            if (setting == null) {
                // Thêm mới
                dbHelper.addOrder(date, components);  // Add a new order
            } else {
                // Chỉnh sửa
                dbHelper.updateOrder(setting.getId(), date, components);  // Update order
            }

            loadSettings(); // Refresh settings list
            Toast.makeText(getContext(), "Dữ liệu đã được lưu!", Toast.LENGTH_SHORT).show();
        });
        builder.setNegativeButton("Hủy", null);
        builder.show();
    }

    private void deleteSetting(Setting setting) {
        // Delete the order from the database
        dbHelper.deleteOrder(setting.getId());

        // Remove it from the list and notify the adapter
        settingList.remove(setting);
        settingAdapter.notifyDataSetChanged();
        Toast.makeText(getContext(), "Đã xóa!", Toast.LENGTH_SHORT).show();
    }
}


