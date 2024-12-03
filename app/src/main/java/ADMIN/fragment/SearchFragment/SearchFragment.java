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

        etSearch = rootView.findViewById(R.id.etSearch);
        btnSearch = rootView.findViewById(R.id.btnSearch);
        listViewCombos = rootView.findViewById(R.id.listViewCombos);

        comboList = new ArrayList<>();
        comboList.add(new Search("COMBO VUI VẺ", 10, R.drawable.combovuive));
        comboList.add(new Search("COMBO NO NÊ", 5, R.drawable.combonone));
        comboList.add(new Search("COMBO SOLO", 7, R.drawable.combosolo));
        comboList.add(new Search("BÁNH NHÂN XOÀI", 3, R.drawable.banhnhanxoai));
        comboList.add(new Search("NƯỚC ÉP XOÀI", 12, R.drawable.nuocepxoai));

        comboAdapter = new SearchAdapter(getContext(), comboList);
        listViewCombos.setAdapter(comboAdapter);

        // Tìm kiếm khi bấm nút tìm kiếm
        btnSearch.setOnClickListener(v -> {
            String query = etSearch.getText().toString().trim();
            filterList(query); // Lọc danh sách theo từ khóa
        });

        return rootView;
    }

    // Hàm lọc danh sách tìm kiếm
    private void filterList(String query) {
        List<Search> filteredList = comboList.stream()
                .filter(combo -> combo.getName().toLowerCase().contains(query.toLowerCase()))
                .collect(Collectors.toList());

        // Cập nhật adapter với danh sách đã lọc
        comboAdapter = new SearchAdapter(getContext(), filteredList);
        listViewCombos.setAdapter(comboAdapter);
    }
}
