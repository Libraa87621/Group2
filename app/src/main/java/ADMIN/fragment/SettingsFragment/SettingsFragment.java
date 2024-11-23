package ADMIN.fragment.SettingsFragment;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_donhang_adm, container, false);

        dbHelper = new DBHelper(getContext()); // Khởi tạo DBHelper

        recyclerViewOrders = rootView.findViewById(R.id.recyclerViewOrders);
        recyclerViewOrders.setLayoutManager(new LinearLayoutManager(getContext()));

        settingList = new ArrayList<>();
        settingAdapter = new SettingAdapter(settingList);
        recyclerViewOrders.setAdapter(settingAdapter);

        // Lấy dữ liệu từ cả hai bảng
        Cursor userCursor = dbHelper.getAllUsers();
        Cursor orderCursor = dbHelper.getAllOders();

        if (userCursor != null && orderCursor != null) {
            try {
                if (userCursor.moveToFirst() && orderCursor.moveToFirst()) {
                    do {
                        String name = userCursor.getString(userCursor.getColumnIndexOrThrow(DBHelper.COLUMN_NAME));
                        String date = orderCursor.getString(orderCursor.getColumnIndexOrThrow(DBHelper.COLUMN_DATE));
                        String components = orderCursor.getString(orderCursor.getColumnIndexOrThrow(DBHelper.COLUMN_COMPONENTS));

                        settingList.add(new Setting(name, date, components));
                    } while (userCursor.moveToNext() && orderCursor.moveToNext());
                } else {
                    Toast.makeText(getContext(), "Không có dữ liệu", Toast.LENGTH_SHORT).show();
                }
            } finally {
                userCursor.close();
                orderCursor.close();
            }
        } else {
            Toast.makeText(getContext(), "Không thể truy vấn dữ liệu", Toast.LENGTH_SHORT).show();
        }

        // Cập nhật adapter
        settingAdapter.notifyDataSetChanged();



        return rootView;
    }
}
