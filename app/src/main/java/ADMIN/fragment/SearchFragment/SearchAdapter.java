package ADMIN.fragment.SearchFragment;

import android.content.Context;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;


import com.example.duan1.R;

import java.util.List;

import ADMIN.fragment.SearchFragment.Monan;
import USER.choosefood.Combo;

public class SearchAdapter extends BaseAdapter {

    private Context context;
    private List<Monan> combos;

    public SearchAdapter(Context context, List<Monan> combos) {
        super(context, 0, combos);
        this.context = context;
        this.combos = combos;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_combo, parent, false);
        }

        Monan currentCombo = combos.get(position);

        TextView tvComboName = convertView.findViewById(R.id.tvComboName);
        TextView tvQuantity = convertView.findViewById(R.id.tvQuantity);

        tvComboName.setText(currentCombo.getName());
        tvQuantity.setText(String.valueOf(currentCombo.getQuantity()));

        return convertView;
    }

    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return null;
    }
}
