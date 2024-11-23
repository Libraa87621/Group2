package ADMIN.fragment.SettingsFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.duan1.R;

import java.util.ArrayList;
import java.util.List;

public class SettingAdapter extends RecyclerView.Adapter<SettingAdapter.ViewHolder> {
    private List<Setting> settings;
    private List<Setting> selectedItems = new ArrayList<>();

    public SettingAdapter(List<Setting> settings) {this.settings = settings;}

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView nameTextView, dateTextView;
        LinearLayout componentsLayout;
        CheckBox checkBox;

        public ViewHolder(View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.textViewName);
            dateTextView = itemView.findViewById(R.id.textViewDate);
            componentsLayout = itemView.findViewById(R.id.Components);
            checkBox = itemView.findViewById(R.id.checkbox); // Ánh xạ checkbox
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_quanlydonhang, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Setting setting = settings.get(position);

        // Hiển thị tên và ngày
        holder.nameTextView.setText(setting.getName());
        holder.dateTextView.setText(setting.getDate());
        holder.checkBox.setChecked(setting.isSelected());

        // Hiển thị danh sách components
        holder.componentsLayout.removeAllViews();

        String[] components = setting.getComponents().split(","); // Điều chỉnh theo định dạng dữ liệu thực tế

        for (String component : components) {
            TextView componentTextView = new TextView(holder.itemView.getContext());
            componentTextView.setText(component.trim());
            componentTextView.setTextSize(14); // Kích thước chữ
            componentTextView.setPadding(0, 4, 0, 4); // Khoảng cách giữa các dòng
            holder.componentsLayout.addView(componentTextView); // Thêm vào LinearLayout
        }


        // Xử lý trạng thái checkbox
        holder.checkBox.setOnCheckedChangeListener(null); // Tránh lỗi khi tái sử dụng ViewHolder
        holder.checkBox.setChecked(selectedItems.contains(setting));

        holder.checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                selectedItems.add(setting);
            } else {
                selectedItems.remove(setting);
            }
        });
    }

    @Override
    public int getItemCount() {
        return settings.size();
    }


}



