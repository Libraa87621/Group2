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
    private CustomerAdapter adapter;
    private List<Customer> customerItemList;
    private DBHelper dbHelper;
    private SettingAdapter settingAdapter;
    private List<Setting> settingList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_khachhang_adm, container, false);
        dbHelper = new DBHelper(getContext()); // Khởi tạo DBHelper

        // Khởi tạo RecyclerView và Adapter
        recyclerView = rootView.findViewById(R.id.recyclerViewCustomers);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        customerItemList = new ArrayList<>();
        adapter = new CustomerAdapter(customerItemList);
        recyclerView.setAdapter(adapter);

        // Load khách hàng từ cơ sở dữ liệu
        settingList = new ArrayList<>();
        settingAdapter = new SettingAdapter(settingList);
        recyclerView.setAdapter(settingAdapter);
        List<Setting> settingsFromDb = dbHelper.getAllSettings();
        // Cập nhật adapter
        if (settingsFromDb.isEmpty()) {
            Toast.makeText(getContext(), "Không có dữ liệu", Toast.LENGTH_SHORT).show();
        } else {
            settingList.addAll(settingsFromDb);
            settingAdapter.notifyDataSetChanged(); // Cập nhật dữ liệu hiển thị
        }

        // Các nút thêm, sửa, xóa
        ImageView btnthem = rootView.findViewById(R.id.btnthem);
        btnthem.setOnClickListener(v -> showCustomerDialog(false, null));

        ImageView btnsua = rootView.findViewById(R.id.btnsua);
        btnsua.setOnClickListener(v -> {
            List<Customer> selectedCustomers = adapter.getSelectedCustomers();
            if (selectedCustomers.isEmpty()) {
                showAlert("Bạn cần phải chọn khách hàng để sửa");
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
                showAlert("Bạn cần phải chọn khách hàng để xóa");
            } else {
                adapter.removeSelectedItems();
            }
        });

        return rootView;
    }

    // Phương thức để load danh sách khách hàng từ cơ sở dữ liệu

    // Phương thức để hiển thị dialog thêm/sửa thông tin khách hàng
    private void showCustomerDialog(boolean isEdit, Customer customer) {
        View dialogView = LayoutInflater.from(getContext()).inflate(R.layout.dialog_customer_info, null);
        EditText etCustomerName = dialogView.findViewById(R.id.etCustomerName);
        EditText etBirthDate = dialogView.findViewById(R.id.etBirthDate);
        EditText etAddress = dialogView.findViewById(R.id.etAddress);
        EditText etPhoneNumber = dialogView.findViewById(R.id.etPhoneNumber);
        EditText etEmail = dialogView.findViewById(R.id.etEmail);

        if (isEdit && customer != null) {
            etCustomerName.setText(customer.getCustomerName());
            etBirthDate.setText(customer.getBirthDate());
            etAddress.setText(customer.getAddress());
            etPhoneNumber.setText(customer.getPhoneNumber());
            etEmail.setText(customer.getEmail());
        }

        new AlertDialog.Builder(getContext())
                .setTitle(isEdit ? "Chỉnh sửa khách hàng" : "Thêm khách hàng")
                .setView(dialogView)
                .setPositiveButton("Lưu", (dialog, which) -> {
                    String name = etCustomerName.getText().toString();
                    String birthDate = etBirthDate.getText().toString();
                    String address = etAddress.getText().toString();
                    String phone = etPhoneNumber.getText().toString();
                    String email = etEmail.getText().toString();

                    if (isEdit && customer != null) {
                        customer.setCustomerName(name);
                        customer.setBirthDate(birthDate);
                        customer.setAddress(address);
                        customer.setPhoneNumber(phone);
                        customer.setEmail(email);
                        adapter.notifyDataSetChanged();
                    } else {
                        Customer newCustomer = new Customer(name, birthDate, address, phone, email);
                        adapter.addItem(newCustomer);
                    }
                })
                .setNegativeButton("Hủy", null)
                .show();
    }

    // Phương thức để hiển thị thông báo
    private void showAlert(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }
}
