package ADMIN.fragment.SearchFragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.duan1.R;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class SearchFragment extends Fragment {

    private EditText etSearch;
    private ImageButton btnSearch;
    private ListView listViewCombos;
    private ArrayList<Search> comboList;
    private SearchAdapter comboAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_search_admin, container, false);

        // Khởi tạo các view
        etSearch = rootView.findViewById(R.id.etSearch);
        btnSearch = rootView.findViewById(R.id.btnSearch);
        listViewCombos = rootView.findViewById(R.id.listViewCombos);

        // Tạo danh sách dữ liệu mẫu
        comboList = new ArrayList<>();
        comboList.add(new Search("COMBO VUI VẺ", 10, R.drawable.combovuive));
        comboList.add(new Search("COMBO NO NÊ", 5, R.drawable.combonone));
        comboList.add(new Search("COMBO SOLO", 7, R.drawable.combosolo));
        comboList.add(new Search("BÁNH NHÂN XOÀI", 3, R.drawable.banhnhanxoai));
        comboList.add(new Search("NƯỚC ÉP XOÀI", 12, R.drawable.nuocepxoai));

        // Khởi tạo adapter và gán vào ListView
        comboAdapter = new SearchAdapter(getContext(), comboList);
        listViewCombos.setAdapter(comboAdapter);

        // Xử lý sự kiện nút tìm kiếm
        btnSearch.setOnClickListener(v -> {
            if (comboList == null || comboList.isEmpty()) {
                Toast.makeText(getContext(), "Danh sách trống, không thể tìm kiếm!", Toast.LENGTH_SHORT).show();
                return;
            }

            String query = etSearch.getText().toString().trim();
            if (query.isEmpty()) {
                Toast.makeText(getContext(), "Vui lòng nhập từ khóa tìm kiếm!", Toast.LENGTH_SHORT).show();
                return;
            }

            filterList(query);
        });

        return rootView;
    }

    // Phương thức lọc danh sách
    private void filterList(String query) {
        List<Search> filteredList = comboList.stream()
                .filter(combo -> combo.getName().toLowerCase().contains(query.toLowerCase()))
                .collect(Collectors.toList());

        if (filteredList.isEmpty()) {
            Toast.makeText(getContext(), "Không tìm thấy kết quả nào!", Toast.LENGTH_SHORT).show();
        }

        try {
            comboAdapter = new SearchAdapter(getContext(), filteredList);
            listViewCombos.setAdapter(comboAdapter);
        } catch (Exception e) {
            Toast.makeText(getContext(), "Đã xảy ra lỗi khi cập nhật danh sách!", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }
}
