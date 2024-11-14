package ADMIN.fragment.SettingsFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.duan1.R;

import java.util.ArrayList;
import java.util.List;

public class SettingAdapter extends RecyclerView.Adapter<SettingAdapter.SettingViewHolder> {
    private List<Setting> settingList;

    public SettingAdapter(List<Setting> settingList) {
        this.settingList = settingList;
    }

    public void addItem(Setting newSetting) {
        settingList.add(newSetting);
        notifyItemInserted(settingList.size() - 1);
    }

    public void removeSelectedItems() {
        for (int i = settingList.size() - 1; i >= 0; i--) {
            if (settingList.get(i).isSelected()) {
                settingList.remove(i);
                notifyItemRemoved(i);
            }
        }
    }

    public void editSelectedItems(String newName) {
        for (Setting setting : settingList) {
            if (setting.isSelected()) {
                setting.setCustomerName(newName);
            }
        }
        notifyDataSetChanged();
    }

    public List<Setting> getSelectedItems() {
        List<Setting> selectedItems = new ArrayList<>();
        for (Setting setting : settingList) {
            if (setting.isSelected()) {
                selectedItems.add(setting);
            }
        }
        return selectedItems;
    }

    @NonNull
    @Override
    public SettingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_quanlydonhang, parent, false);
        return new SettingViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SettingViewHolder holder, int position) {
        Setting setting = settingList.get(position);
        holder.tvDate.setText(setting.getDate());
        holder.tvCustomerName.setText(setting.getCustomerName());
        holder.tvItems.setText(setting.getItems());
        holder.checkCompleted.setChecked(setting.isSelected());

        holder.checkCompleted.setOnCheckedChangeListener((buttonView, isChecked) -> setting.setSelected(isChecked));
    }

    @Override
    public int getItemCount() {
        return settingList.size();
    }

    public static class SettingViewHolder extends RecyclerView.ViewHolder {
        TextView tvDate, tvCustomerName, tvItems;
        CheckBox checkCompleted;

        public SettingViewHolder(@NonNull View itemView) {
            super(itemView);
            tvDate = itemView.findViewById(R.id.tvDate);
            tvCustomerName = itemView.findViewById(R.id.tvCustomerName);
            tvItems = itemView.findViewById(R.id.tvItems);
            checkCompleted = itemView.findViewById(R.id.checkCompleted);
        }
    }
}
