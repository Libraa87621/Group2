package ADMIN.fragment.ProfileFragment;

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

import ADMIN.fragment.SettingsFragment.Setting;
import ADMIN.fragment.SettingsFragment.SettingAdapter;
import Database.DBHelper;

public class ProfileFragment extends Fragment {
    private RecyclerView recyclerView;
    private SettingAdapter customerAdapter;
    private CustomerAdapter adapter;
    private List<Customer> customerItemList;
    private DBHelper dbHelper;
    private RecyclerView recyclerViewOrders;
    private List<Setting> customerList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_khachhang_adm, container, false);
        dbHelper = new DBHelper(getContext()); // Khởi tạo DBHelper
        // Initialize RecyclerView and Adapter
        recyclerView = rootView.findViewById(R.id.recyclerViewCustomers);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        customerItemList = new ArrayList<>();
        adapter = new CustomerAdapter(customerItemList);
        recyclerView.setAdapter(adapter);

        customerList = new ArrayList<>();
        addSampleData();
        // Load users from database
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

                    customerList.add(new Setting(name, email, phone));
                }
            } while (cursor.moveToNext());
            cursor.close();
        } else {
            Toast.makeText(getContext(), "No users found", Toast.LENGTH_SHORT).show();
        }
        // Cập nhật adapter với danh sách người dùng
        customerAdapter = new SettingAdapter(customerList);
        recyclerViewOrders.setAdapter(customerAdapter);

        // Các nút thêm, sửa, xóa
        ImageView btnthem = rootView.findViewById(R.id.btnthem);
        btnthem.setOnClickListener(v -> showCustomerDialog(false, null));

        ImageView btnsua = rootView.findViewById(R.id.btnsua);
        btnsua.setOnClickListener(v -> {
            List<Customer> selectedCustomers = adapter.getSelectedCustomers();
            if (selectedCustomers.isEmpty()) {
                showAlert("Bạn cần phải chọn cần sửa");
            } else if (selectedCustomers.size() == 1) {
                showCustomerDialog(true, selectedCustomers.get(0));
            } else {
                showAlert("Chỉ chọn một khách hàng để sửa");
            }
        });

        ImageView btxoa = rootView.findViewById(R.id.btxoa);
        btxoa.setOnClickListener(v -> {
            List<Customer> selectedCustomers = adapter.getSelectedCustomers();
            if (selectedCustomers.isEmpty()) {
                showAlert("Bạn cần phải chọn cần xóa");
            } else {
                adapter.removeSelectedItems();
            }
        });

        return rootView;

    }

    // Method to show the add/edit dialog
    private void showCustomerDialog(boolean isEdit, Customer customer) {
        View dialogView = LayoutInflater.from(getContext()).inflate(R.layout.dialog_customer_info, null);
        EditText etDate = dialogView.findViewById(R.id.etDate);
        EditText etCustomerName = dialogView.findViewById(R.id.etCustomerName);
        EditText etItems = dialogView.findViewById(R.id.etItems);

        if (isEdit && customer != null) {
            etDate.setText(customer.getDate());
            etCustomerName.setText(customer.getCustomerName());
            etItems.setText(customer.getItems());
        }

        new AlertDialog.Builder(getContext())
                .setTitle(isEdit ? "Edit Customer" : "Add Customer")
                .setView(dialogView)
                .setPositiveButton("Save", (dialog, which) -> {
                    String date = etDate.getText().toString();
                    String name = etCustomerName.getText().toString();
                    String items = etItems.getText().toString();

                    if (isEdit && customer != null) {
                        customer.setDate(date);
                        customer.setCustomerName(name);
                        customer.setItems(items);
                        adapter.notifyDataSetChanged();
                    } else {
                        Customer newCustomer = new Customer(date, name, items, R.drawable.anhsanpham, R.drawable.man);
                        adapter.addItem(newCustomer);
                    }
                })
                .setNegativeButton("Cancel", null)
                .show();
    }

    private void addSampleData() {
        Customer item1 = new Customer("10/5/2024", "Nguyễn Ngọc Duy", "- 2 Miếng gà giòn\n- 1 nước\n- 1 khoai chiên", R.drawable.anhsanpham, R.drawable.man);
        adapter.addItem(item1);

        Customer item2 = new Customer("11/5/2024", "Lê Thị Lan", "- 3 Miếng gà giòn\n- 2 nước", R.drawable.anhsanpham, R.drawable.man);
        adapter.addItem(item2);

        Customer item3 = new Customer("12/5/2024", "Trần Văn Bảo", "- 1 Miếng gà giòn\n- 1 nước", R.drawable.anhsanpham, R.drawable.man);
        adapter.addItem(item3);
    }

    // Method to show alert message
    private void showAlert(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }
}
