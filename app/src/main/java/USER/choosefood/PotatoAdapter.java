package USER.choosefood;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ArrayAdapter;

import com.example.duan1.R;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class PotatoAdapter extends ArrayAdapter<PotatoItem> {

    public PotatoAdapter(Context context, List<PotatoItem> items) {
        super(context, 0, items);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return initView(position, convertView, parent);
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        return initView(position, convertView, parent);
    }

    private View initView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.spinner_item_layout, parent, false);
        }

        ImageView imageView = convertView.findViewById(R.id.imageViewSpinnerItem);
        TextView textViewName = convertView.findViewById(R.id.textViewSpinnerItemName);


        PotatoItem item = getItem(position);
        if (item != null) {
            imageView.setImageResource(item.getImageResId());
            textViewName.setText(item.getName());

            // Use locale-specific formatting for currency
            NumberFormat format = NumberFormat.getInstance(new Locale("vi", "VN"));

        }

        return convertView;
    }
}
