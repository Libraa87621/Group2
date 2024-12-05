package ADMIN.fragment.ProfileFragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
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
    private CustomerAdapter customerAdapter;
    private List<Customer> customerList;
    private DBHelper dbHelper;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_khachhang_adm, container, false);

        dbHelper = new DBHelper(getContext());

        recyclerView = rootView.findViewById(R.id.recyclerViewCustomers);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        customerList = new ArrayList<>();
        customerAdapter = new CustomerAdapter(customerList);
        recyclerView.setAdapter(customerAdapter);

        // Lấy dữ liệu từ cơ sở dữ liệu
        List<Customer> customerFromDb = dbHelper.getAllCustomer();

        // Kiểm tra nếu không có dữ liệu
        if (customerFromDb == null || customerFromDb.isEmpty()) {
            Toast.makeText(getContext(), "Không có dữ liệu", Toast.LENGTH_SHORT).show();
        } else {
            customerList.addAll(customerFromDb);
            customerAdapter.notifyDataSetChanged();
        }

        // Thiết lập sự kiện cho nút sửa
        ImageView btnEdit = rootView.findViewById(R.id.btnsua);
        btnEdit.setOnClickListener(v -> {
            editSelectedCustomer();
        });

        // Thiết lập sự kiện cho nút thêm
        ImageView btnAdd = rootView.findViewById(R.id.btnthem);
        btnAdd.setOnClickListener(v -> {
            showAddCustomerDialog();
        });
        return rootView;
    }

    private void editSelectedCustomer() {
        Customer selectedCustomer = null;
        for (Customer customer : customerList) {
            if (customer.isSelected()) {
                selectedCustomer = customer;
                break;
            }
        }

        if (selectedCustomer != null) {
            showEditDialog(selectedCustomer);
        } else {
            Toast.makeText(getContext(), "Vui lòng chọn khách hàng để chỉnh sửa", Toast.LENGTH_SHORT).show();
        }
    }

    private void showEditDialog(final Customer customer) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_edit_customer, null);
        builder.setView(dialogView);

        final EditText editName = dialogView.findViewById(R.id.editName);
        final EditText editAddress = dialogView.findViewById(R.id.editAddress);
        final EditText editPhone = dialogView.findViewById(R.id.editPhone);
        final EditText editEmail = dialogView.findViewById(R.id.editEmail);

        // Điền thông tin hiện tại vào các EditText
        editName.setText(customer.getName());
        editAddress.setText(customer.getAddress());
        editPhone.setText(customer.getPhone());
        editEmail.setText(customer.getEmail());

        builder.setTitle("Chỉnh sửa thông tin khách hàng")
                .setPositiveButton("Lưu", (dialog, which) -> {
                    // Cập nhật thông tin khách hàng
                    customer.setName(editName.getText().toString());
                    customer.setAddress(editAddress.getText().toString());
                    customer.setPhone(editPhone.getText().toString());
                    customer.setEmail(editEmail.getText().toString());

                    // Cập nhật vào cơ sở dữ liệu
                    dbHelper.updateCustomer(customer);

                    // Bỏ chọn checkbox của khách hàng
                    customer.setSelected(false);

                    // Cập nhật RecyclerView
                    customerAdapter.notifyDataSetChanged(); // Cập nhật RecyclerView
                })
                .setNegativeButton("Hủy", null);

        builder.create().show();
    }

    private void showAddCustomerDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_add_customer, null);
        builder.setView(dialogView);

        final EditText editName = dialogView.findViewById(R.id.editName);
        final EditText editAddress = dialogView.findViewById(R.id.editAddress);
        final EditText editPhone = dialogView.findViewById(R.id.editPhone);
        final EditText editEmail = dialogView.findViewById(R.id.editEmail);

        builder.setTitle("Thêm khách hàng mới")
                .setPositiveButton("Lưu", (dialog, which) -> {
                    // Lấy thông tin từ EditText
                    String name = editName.getText().toString();
                    String address = editAddress.getText().toString();
                    String phone = editPhone.getText().toString();
                    String email = editEmail.getText().toString();

                    // Tạo đối tượng Customer mới
                    Customer newCustomer = new Customer(name, phone, "", address, email);

                    // Thêm vào cơ sở dữ liệu
                    dbHelper.addCustomer(newCustomer);

                    // Thêm vào danh sách và cập nhật RecyclerView
                    customerList.add(newCustomer);
                    customerAdapter.notifyDataSetChanged();
                })
                .setNegativeButton("Hủy", null);

        builder.create().show();
    }
}