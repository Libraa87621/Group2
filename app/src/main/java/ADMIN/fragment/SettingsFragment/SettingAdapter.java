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

    // Thêm listener vào constructor
    public SettingAdapter(List<Setting> settings) {this.settings = settings;}


    //Ánh xạ
    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView nameTextView, dateTextView;
        LinearLayout componentsLayout;
        CheckBox checkBox;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.textViewName);
            dateTextView = itemView.findViewById(R.id.textViewDate);
            componentsLayout = itemView.findViewById(R.id.Components);
            checkBox = itemView.findViewById(R.id.checkbox);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_quanlydonhang, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Setting setting = settings.get(position);
        holder.nameTextView.setText(setting.getName());
        holder.dateTextView.setText(setting.getDate());
        holder.checkBox.setChecked(setting.isSelected());

        // Hiển thị danh sách components
        holder.componentsLayout.removeAllViews();

        String[] components = setting.getComponents().split("'");
        for (String component : components) {
            TextView componentTextView = new TextView(holder.itemView.getContext());
            componentTextView.setText(component);
            holder.componentsLayout.addView(componentTextView);
        }
        holder.checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            setting.setSelected(isChecked);
            if (isChecked) {
                settings.add(setting);
            } else {
                settings.remove(setting);
            }
        });

    }
    @Override
    public int getItemCount() {
        return settings.size();
    }

}




