package ADMIN.fragment.SettingsFragment;

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

public class SettingsFragment extends Fragment {
    private RecyclerView recyclerViewOrders;
    private SettingAdapter settingAdapter;
    private List<Setting> settingList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_donhang_adm, container, false);

        recyclerViewOrders = rootView.findViewById(R.id.recyclerViewOrders);
        recyclerViewOrders.setLayoutManager(new LinearLayoutManager(getContext()));

        settingList = new ArrayList<>();
        settingList.add(new Setting("10/5/2024", "Nguyễn Ngọc Duy", "- 2 Miếng gà giòn\n- 1 nước\n- 1 khoai chiên", true));
        settingList.add(new Setting("11/5/2024", "Lê Văn A", "- 3 Miếng gà giòn\n- 1 nước ngọt", false));

        settingAdapter = new SettingAdapter(settingList);
        recyclerViewOrders.setAdapter(settingAdapter);

        // Icon thêm
        ImageView btnthem = rootView.findViewById(R.id.btnthem);
        btnthem.setOnClickListener(v -> showAddDialog());

        // Icon xóa
        ImageView btxoa = rootView.findViewById(R.id.btxoa);
        btxoa.setOnClickListener(v -> onDeleteClick());

        // Icon sửa
        ImageView btnsua = rootView.findViewById(R.id.btnsua);
        btnsua.setOnClickListener(v -> showEditDialog());

        return rootView;
    }

    private void onDeleteClick() {
        if (settingAdapter.getSelectedItems().isEmpty()) {
            Toast.makeText(getContext(), "Vui lòng chọn ít nhất một item để xóa!", Toast.LENGTH_SHORT).show();
        } else {
            settingAdapter.removeSelectedItems();
        }
    }

    private void showAddDialog() {
        View dialogView = LayoutInflater.from(getContext()).inflate(R.layout.dialog_add_setting, null);
        EditText etDate = dialogView.findViewById(R.id.etDate);
        EditText etCustomerName = dialogView.findViewById(R.id.etCustomerName);
        EditText etItems = dialogView.findViewById(R.id.etItems);

        new androidx.appcompat.app.AlertDialog.Builder(getContext())
                .setTitle("Thêm Đơn Hàng")
                .setView(dialogView)
                .setPositiveButton("Thêm", (dialog, which) -> {
                    String date = etDate.getText().toString();
                    String customerName = etCustomerName.getText().toString();
                    String items = etItems.getText().toString();

                    if (!date.isEmpty() && !customerName.isEmpty() && !items.isEmpty()) {
                        Setting newSetting = new Setting(date, customerName, items, false);
                        settingAdapter.addItem(newSetting);
                    } else {
                        Toast.makeText(getContext(), "Vui lòng điền đầy đủ thông tin!", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("Hủy", null)
                .show();
    }

    private void showEditDialog() {
        List<Setting> selectedItems = settingAdapter.getSelectedItems();
        if (selectedItems.size() != 1) {
            Toast.makeText(getContext(), "Vui lòng chọn một item để sửa!", Toast.LENGTH_SHORT).show();
            return;
        }

        Setting setting = selectedItems.get(0);

        View dialogView = LayoutInflater.from(getContext()).inflate(R.layout.dialog_add_setting, null);
        EditText etDate = dialogView.findViewById(R.id.etDate);
        EditText etCustomerName = dialogView.findViewById(R.id.etCustomerName);
        EditText etItems = dialogView.findViewById(R.id.etItems);

        etDate.setText(setting.getDate());
        etCustomerName.setText(setting.getCustomerName());
        etItems.setText(setting.getItems());

        new androidx.appcompat.app.AlertDialog.Builder(getContext())
                .setTitle("Sửa Đơn Hàng")
                .setView(dialogView)
                .setPositiveButton("Sửa", (dialog, which) -> {
                    String date = etDate.getText().toString();
                    String customerName = etCustomerName.getText().toString();
                    String items = etItems.getText().toString();

                    if (!date.isEmpty() && !customerName.isEmpty() && !items.isEmpty()) {
                        setting.setDate(date);
                        setting.setCustomerName(customerName);
                        setting.setItems(items);
                        settingAdapter.notifyDataSetChanged();
                    } else {
                        Toast.makeText(getContext(), "Vui lòng điền đầy đủ thông tin!", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("Hủy", null)
                .show();
    }
}
