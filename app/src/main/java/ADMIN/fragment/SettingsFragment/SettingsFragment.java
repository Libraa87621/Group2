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
    ImageView iconxacnhan, iconxoa, btxoa;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.activity_donhang_adm, container, false);

        dbHelper = new DBHelper(getContext()); // Initialize DBHelper

        // Use rootView.findViewById to find views in the fragment layout
        iconxacnhan = rootView.findViewById(R.id.iconxacnhan);
        iconxoa = rootView.findViewById(R.id.iconhuy);
        btxoa = rootView.findViewById(R.id.btxoa);
        recyclerViewOrders = rootView.findViewById(R.id.recyclerViewOrders);
        recyclerViewOrders.setLayoutManager(new LinearLayoutManager(getContext()));

        settingList = new ArrayList<>();
        settingAdapter = new SettingAdapter(settingList, new SettingAdapter.OnSettingActionListener() {
            @Override
            public void onConfirmClick(Setting setting) {
                // Change status to "Đã đi"
                setting.setComponents("Đã đi");
                dbHelper.updateSettingStatus(setting);  // Ensure this method updates the status in DB
                settingAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelClick(Setting setting) {
                // Change status to "Đã hủy"
                setting.setComponents("Đã hủy");
                dbHelper.updateSettingStatus(setting);  // Ensure this method updates the status in DB
                settingAdapter.notifyDataSetChanged();
            }

            @Override
            public void onDeleteClick(Setting setting) {
                // Delete the setting (order)
                dbHelper.deleteSetting(setting);
                settingList.remove(setting);
                settingAdapter.notifyDataSetChanged();
            }
        });

        recyclerViewOrders.setAdapter(settingAdapter);

        // Fetch settings from the database
        List<Setting> settingsFromDb = dbHelper.getAllSettings();

        // Check if the list is empty and show a toast message
        if (settingsFromDb.isEmpty()) {
            Toast.makeText(getContext(), "Không có dữ liệu", Toast.LENGTH_SHORT).show();
        } else {
            settingList.addAll(settingsFromDb); // Add data from the database to the list
            settingAdapter.notifyDataSetChanged(); // Update the RecyclerView with the data
        }

        return rootView;
    }
}
