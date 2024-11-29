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
    private List<Setting> settingsList;
    private List<Setting> selectedItems = new ArrayList<>();
    private OnItemCheckListener listener; // Thêm listener

    // Interface để lắng nghe sự kiện checkbox
    public interface OnItemCheckListener {
        void onItemCheck(Setting setting);
        void onItemUncheck(Setting setting);
    }

    // Thêm listener vào constructor
    public SettingAdapter(List<Setting> settingsList, OnItemCheckListener listener) {
        this.settingsList = settingsList;
        this.listener = listener;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView textViewName, textViewDate, tvCombo;
        LinearLayout componentsLayout;
        CheckBox checkBox;

        public ViewHolder(View itemView) {
            super(itemView);
            textViewName = itemView.findViewById(R.id.textViewName);
            textViewDate = itemView.findViewById(R.id.textViewDate);
            tvCombo = itemView.findViewById(R.id.tvCombo);
            componentsLayout = itemView.findViewById(R.id.Components);
            checkBox = itemView.findViewById(R.id.checkbox);
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
        Setting setting = settingsList.get(position);
        holder.textViewName.setText(setting.getName());
        holder.textViewDate.setText(setting.getDate());
        holder.tvCombo.setText(setting.getCombo());

        // Hiển thị danh sách components
        holder.componentsLayout.removeAllViews();
        for (String component : setting.getComponents()) {
            TextView componentTextView = new TextView(holder.itemView.getContext());
            componentTextView.setText(component);
            holder.componentsLayout.addView(componentTextView);
        }

        // Xử lý trạng thái checkbox
        holder.checkBox.setOnCheckedChangeListener(null); // Tránh lỗi khi tái sử dụng ViewHolder
        holder.checkBox.setChecked(selectedItems.contains(setting));

        holder.checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                selectedItems.add(setting);
                if (listener != null) {
                    listener.onItemCheck(setting); // Gọi listener khi checkbox được check
                }
            } else {
                selectedItems.remove(setting);
                if (listener != null) {
                    listener.onItemUncheck(setting); // Gọi listener khi checkbox bị bỏ
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return settingsList.size();
    }

    public List<Setting> getSelectedItems() {
        return selectedItems;
    }

    public void addItem(Setting newItem) {
        settingsList.add(newItem);
        notifyItemInserted(settingsList.size() - 1);
    }

    public void removeSelectedItems() {
        settingsList.removeAll(selectedItems);
        selectedItems.clear();
        notifyDataSetChanged();
    }

    public void updateItem(Setting updatedItem, int position) {
        settingsList.set(position, updatedItem);
        notifyItemChanged(position);
    }
}




