package ADMIN.fragment.SearchFragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.fragment.app.Fragment;

import com.example.duan1.R;

import java.util.ArrayList;

public class SearchFragment extends Fragment {
    private ListView listViewCombos;
    private ArrayList<Monan> comboList; // Danh sách các món ăn
    private SearchAdapter comboAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflater layout của fragment
        View rootView = inflater.inflate(R.layout.activity_timkiem_adm, container, false);

        // Khởi tạo ListView và các view khác
        listViewCombos = rootView.findViewById(R.id.listViewCombos);

        // Khởi tạo danh sách món ăn (combo)
        comboList = new ArrayList<>();
        comboList.add(new Monan("Combo 1", 2)); // Thêm món ăn mẫu
        comboList.add(new Monan("Combo 2", 5));

        // Thiết lập adapter
        comboAdapter = new SearchAdapter(getContext(), comboList);
        listViewCombos.setAdapter(comboAdapter);

        return rootView; // Trả về root view đã được tạo
    }
}
