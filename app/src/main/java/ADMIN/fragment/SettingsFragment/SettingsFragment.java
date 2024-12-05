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
            settingAdapter.notifyDataSetChanged();
        }



        confirmButton.setOnClickListener(v -> {
            for (Setting setting : settingList) {
                if (setting.isSelected()) {
                    setting.setOrderStatus("Đã giao");
                    dbHelper.updateOrderStatus(setting, "Đã giao"); // Cập nhật trạng thái trong cơ sở dữ liệu
                }
            }
            settingAdapter.notifyDataSetChanged(); // Cập nhật RecyclerView
        });

        cancelButton.setOnClickListener(v -> {
            for (Setting setting : settingList) {
                if (setting.isSelected()) {
                    setting.setOrderStatus("Đã HỦY");
                    dbHelper.updateOrderStatus(setting, "Đã HỦY"); // Cập nhật trạng thái trong cơ sở dữ liệu
                }
            }
            settingAdapter.notifyDataSetChanged(); // Cập nhật RecyclerView
        });

        return rootView;
    }
}
