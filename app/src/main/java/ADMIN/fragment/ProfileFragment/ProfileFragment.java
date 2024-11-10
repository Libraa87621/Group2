package ADMIN.fragment.ProfileFragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.duan1.R;

import java.util.ArrayList;
import java.util.List;

public class ProfileFragment extends Fragment {
    private RecyclerView recyclerView;
    private CustomerAdapter adapter;
    private List<Customer> customerItemList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.activity_khachhang_adm, container, false);

        // Khởi tạo RecyclerView và Adapter
        recyclerView = rootView.findViewById(R.id.recyclerViewCustomers);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        customerItemList = new ArrayList<>();
        adapter = new CustomerAdapter(customerItemList);
        recyclerView.setAdapter(adapter);

        // Thêm dữ liệu mẫu vào RecyclerView
        addSampleData();

        return rootView;
    }

    private void addSampleData() {
        Customer item1 = new Customer("10/5/2024", "Nguyễn Ngọc Duy", "- 2 Miếng gà giòn\n- 1 nước\n- 1 khoai chiên", R.drawable.anhsanpham, R.drawable.man);
        adapter.addItem(item1);

        Customer item2 = new Customer("11/5/2024", "Lê Thị Lan", "- 3 Miếng gà giòn\n- 2 nước", R.drawable.anhsanpham, R.drawable.man);
        adapter.addItem(item2);

        Customer item3 = new Customer("11/5/2024", "Lê Thị Lan", "- 3 Miếng gà giòn\n- 2 nước", R.drawable.anhsanpham, R.drawable.man);
        adapter.addItem(item3);
    }
}
