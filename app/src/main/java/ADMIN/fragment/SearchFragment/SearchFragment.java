package ADMIN.fragment.SearchFragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.fragment.app.Fragment;

import com.example.duan1.R;

import java.util.ArrayList;

import USER.choosefood.Combo;

public class SearchFragment extends Fragment {
    private ListView listViewCombos;
    private ArrayList<Monan> comboList;
    private SearchAdapter comboAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timkiem_adm);

        listViewCombos = findViewById(R.id.listViewCombos);

        // Initialize list and add sample data
        comboList = new ArrayList<>();
        comboList.add(new Combo("Combo 1", 2));
        comboList.add(new Combo("Combo 2", 5));

        // Set adapter
        comboAdapter = new SearchAdapter(this, comboList);
        listViewCombos.setAdapter(comboAdapter);
    }
}
}
