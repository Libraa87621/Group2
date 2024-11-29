package ADMIN.fragment.ProfileFragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
            // Thêm dữ liệu vào danh sách và thông báo cho adapter
            customerList.addAll(customerFromDb);
            customerAdapter.notifyDataSetChanged();
        }

        return rootView;
    }
}
