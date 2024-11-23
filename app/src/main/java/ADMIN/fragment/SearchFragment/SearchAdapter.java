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
    private List<Monan> combos;

    public SearchAdapter(Context context, List<Monan> combos) {
        this.context = context;
        this.combos = combos;
    }

    @Override
    public int getCount() {
        return combos.size(); // Trả về số lượng món ăn trong danh sách
    }

    @Override
    public Object getItem(int position) {
        return combos.get(position); // Trả về món ăn tại vị trí
    }

    @Override
    public long getItemId(int position) {
        return position; // Trả về vị trí của món ăn
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Nếu convertView là null, tạo mới view từ layout item_doan
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_doan, parent, false);
        }

        // Lấy món ăn hiện tại trong danh sách
        Monan currentCombo = combos.get(position);

        // Ánh xạ các phần tử trong layout item_doan.xml
        ImageView imgCombo = convertView.findViewById(R.id.imgCombo);
        TextView tvComboName = convertView.findViewById(R.id.tvComboName);
        TextView tvQuantity = convertView.findViewById(R.id.tvQuantity);

        // Đặt tên và số lượng vào các TextView
        tvComboName.setText(currentCombo.getName());
        tvQuantity.setText("Còn lại: " + currentCombo.getQuantity());

        // Thiết lập hình ảnh cho ImageView từ tên hình ảnh
        int imageResource = context.getResources().getIdentifier(currentCombo.getImage(), "drawable", context.getPackageName());
        imgCombo.setImageResource(imageResource); // Đặt hình ảnh cho ImageView

        return convertView; // Trả về convertView đã được điền dữ liệu
    }
}
