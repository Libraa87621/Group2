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

import Database.DBHelper;

public class ProfileFragment extends Fragment {
    private RecyclerView recyclerView;
    private CustomerAdapter adapter;
    private List<Customer> customerItemList;
    private DBHelper dbHelper;

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
        loadCustomersFromDatabase();

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
    private void loadCustomersFromDatabase() {
        Cursor cursor = dbHelper.getAllUsers();
        if (cursor != null && cursor.moveToFirst()) {
            do {
                int nameIndex = cursor.getColumnIndex(DBHelper.COLUMN_NAME);
                int birthDateIndex = cursor.getColumnIndex(DBHelper.COLUMN_BIRTHDATE);
                int addressIndex = cursor.getColumnIndex(DBHelper.COLUMN_ADDRESS);
                int phoneIndex = cursor.getColumnIndex(DBHelper.COLUMN_PHONE);
                int emailIndex = cursor.getColumnIndex(DBHelper.COLUMN_EMAIL);

                if (nameIndex != -1 && birthDateIndex != -1 && addressIndex != -1 && phoneIndex != -1 && emailIndex != -1) {
                    String name = cursor.getString(nameIndex);
                    String birthDate = cursor.getString(birthDateIndex);
                    String address = cursor.getString(addressIndex);
                    String phone = cursor.getString(phoneIndex);
                    String email = cursor.getString(emailIndex);

                    customerItemList.add(new Customer(name, birthDate, address, phone, email));
                }
            } while (cursor.moveToNext());
            cursor.close();
        } else {
            Toast.makeText(getContext(), "Không tìm thấy khách hàng", Toast.LENGTH_SHORT).show();
        }
        adapter.notifyDataSetChanged();
    }

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
