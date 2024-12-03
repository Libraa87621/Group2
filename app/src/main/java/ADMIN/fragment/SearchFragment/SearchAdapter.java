package ADMIN.fragment.SearchFragment;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.duan1.R;

import java.util.List;

public class SearchAdapter extends BaseAdapter {

    private Context context;
    private List<Search> combos;

    public SearchAdapter(Context context, List<Search> combos) {
        this.context = context;
        this.combos = combos;
    }

    @Override
    public int getCount() {
        return combos.size();
    }

    @Override
    public Object getItem(int position) {
        return combos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_doan, parent, false);
        }

        Search currentCombo = combos.get(position);

        ImageView imgCombo = convertView.findViewById(R.id.imgCombo);
        TextView tvComboName = convertView.findViewById(R.id.tvComboName);
        TextView tvQuantity = convertView.findViewById(R.id.tvQuantity);

        imgCombo.setImageResource(currentCombo.getImageResourceId());
        tvComboName.setText(currentCombo.getName());
        tvQuantity.setText("Còn lại: " + currentCombo.getQuantity());

        return convertView;
    }
}
