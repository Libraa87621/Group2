package ADMIN.fragment.SettingsFragment;

import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_donhang_adm, container, false);

        dbHelper = new DBHelper(getContext()); // Khởi tạo DBHelper

        recyclerViewOrders = rootView.findViewById(R.id.recyclerViewOrders);
        recyclerViewOrders.setLayoutManager(new LinearLayoutManager(getContext()));

        dbHelper.addUser("John Doe", "john@example.com", "123 Main St", "1234567890");
        dbHelper.addUser("Jane Smith", "jane@example.com", "456 Elm St", "9876543210");

        settingList = new ArrayList<>();

        // Lấy dữ liệu người dùng từ DBHelper
        Cursor cursor = dbHelper.getAllUsers();
        if (cursor != null && cursor.moveToFirst()) {
            do {
                int nameIndex = cursor.getColumnIndex(DBHelper.COLUMN_NAME);
                int emailIndex = cursor.getColumnIndex(DBHelper.COLUMN_EMAIL);
                int phoneIndex = cursor.getColumnIndex(DBHelper.COLUMN_PHONE);

                if (nameIndex != -1 && emailIndex != -1 && phoneIndex != -1) {
                    String name = cursor.getString(nameIndex);
                    String email = cursor.getString(emailIndex);
                    String phone = cursor.getString(phoneIndex);

                    settingList.add(new Setting(name, email, phone));
                }
            } while (cursor.moveToNext());
            cursor.close();
        } else {
            Toast.makeText(getContext(), "No users found", Toast.LENGTH_SHORT).show();
        }

        // Cập nhật adapter với danh sách người dùng
        settingAdapter = new SettingAdapter(settingList);
        recyclerViewOrders.setAdapter(settingAdapter);

        return rootView;
    }
}
