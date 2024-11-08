package USER.choosefood;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.duan1.R;

import java.util.List;

public class ChickenAdapter extends ArrayAdapter<ChickenItem> {
    private Context context;
    private List<ChickenItem> items;

    public ChickenAdapter(Context context, List<ChickenItem> items) {
        super(context, R.layout.spinner_item_with_image, items);
        this.context = context;
        this.items = items;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.spinner_item_with_image, null);
        }

        // Gán dữ liệu vào mỗi mục
        ChickenItem item = items.get(position);
        TextView textView = view.findViewById(R.id.item_text);
        ImageView imageView = view.findViewById(R.id.item_image);

        textView.setText(item.getName());
        imageView.setImageResource(item.getImageResId());

        return view;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        return getView(position, convertView, parent); // Sử dụng cùng một view cho dropdown
    }
}
