package ADMIN.fragment.SearchFragment;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.duan1.R;

import java.util.List;

public class SearchAdapter extends BaseAdapter {

    private Context context;
    private List<Monan> combos;

    public SearchAdapter(Context context, List<Monan> combos) {
        this.context = context;
        this.combos = combos;
    }

    @Override
    public int getCount() {
        return combos.size(); // Sửa lại để trả về số lượng combo trong danh sách
    }

    @Override
    public Object getItem(int position) {
        return combos.get(position); // Trả về item ở vị trí hiện tại
    }

    @Override
    public long getItemId(int position) {
        return position; // Trả về vị trí của item
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Nếu convertView là null, tạo mới view từ layout item_combo
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_combo, parent, false);
        }

        // Lấy món ăn hiện tại trong danh sách
        Monan currentCombo = combos.get(position);

        // Ánh xạ các TextView từ layout item_combo
        TextView tvComboName = convertView.findViewById(R.id.tvComboName);
        TextView tvQuantity = convertView.findViewById(R.id.tvQuantity);

        // Đặt dữ liệu vào các TextView
        tvComboName.setText(currentCombo.getName());
        tvQuantity.setText(String.valueOf(currentCombo.getQuantity()));

        return convertView; // Trả về convertView đã được điền dữ liệu
    }
}
