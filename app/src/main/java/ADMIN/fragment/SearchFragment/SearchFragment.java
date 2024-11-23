package ADMIN.fragment.SearchFragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;

import androidx.fragment.app.Fragment;

import com.example.duan1.R;

import java.util.ArrayList;

public class SearchFragment extends Fragment {
    private ListView listViewCombos;
    private ArrayList<Monan> comboList;
    private SearchAdapter comboAdapter;
    private EditText edtSearch; // EditText để nhập từ khóa tìm kiếm
    private ImageButton btnSearch; // Nút tìm kiếm

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflater layout của fragment
        View rootView = inflater.inflate(R.layout.activity_search_admin, container, false);

        // Khởi tạo ListView và các view khác
        listViewCombos = rootView.findViewById(R.id.listViewCombos);
        edtSearch = rootView.findViewById(R.id.edtSearch); // Gán EditText
        btnSearch = rootView.findViewById(R.id.btnSearch); // Gán ImageButton

        // Khởi tạo danh sách món ăn (combo) với tên, số lượng và tên hình ảnh
        comboList = new ArrayList<>();
        comboList.add(new Monan("Combo 1", 2, "combovuive"));
        comboList.add(new Monan("Combo 2", 5, "combonone"));
        comboList.add(new Monan("Combo đặc biệt", 10, "combospecial"));
        comboList.add(new Monan("Combo tiết kiệm", 8, "combosave"));

        // Thiết lập adapter
        comboAdapter = new SearchAdapter(getContext(), comboList);
        listViewCombos.setAdapter(comboAdapter);

        // Xử lý sự kiện khi người dùng nhấn nút tìm kiếm
        btnSearch.setOnClickListener(v -> {
            String query = edtSearch.getText().toString().toLowerCase().trim();
            filterList(query);
        });

        return rootView; // Trả về root view đã được tạo
    }

    // Hàm lọc danh sách combo
    private void filterList(String query) {
        ArrayList<Monan> filteredList = new ArrayList<>();
        for (Monan combo : comboList) {
            if (combo.getName().toLowerCase().contains(query)) { // So sánh tên combo với từ khóa
                filteredList.add(combo);
            }
        }
        // Cập nhật adapter với danh sách đã lọc
        comboAdapter.updateList(filteredList);
    }
}
