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



        return rootView;
    }
}
