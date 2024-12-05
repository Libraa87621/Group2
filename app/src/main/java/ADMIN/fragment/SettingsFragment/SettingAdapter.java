package ADMIN.fragment.SettingsFragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.duan1.R;

import java.util.List;

public class SettingAdapter extends RecyclerView.Adapter<SettingAdapter.ViewHolder> {
    private List<Setting> settings;
    private OnSettingActionListener listener;

    // Constructor accepts settings list and listener for actions
    public SettingAdapter(List<Setting> settings, OnSettingActionListener listener) {
        this.settings = settings;
        this.listener = listener;
    }

    public interface OnSettingActionListener {
        void onConfirmClick(Setting setting); // Confirm action

        void onCancelClick(Setting setting);  // Cancel action

        void onDeleteClick(Setting setting);  // Delete action
    }

    // ViewHolder class to bind views in the item layout
    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView nameTextView, dateTextView;
        LinearLayout componentsLayout;
        CheckBox checkBox;
        ImageView iconxacnhan, iconxoa, btxoa;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.textViewName);
            dateTextView = itemView.findViewById(R.id.textViewDate);
            componentsLayout = itemView.findViewById(R.id.Components);
            checkBox = itemView.findViewById(R.id.checkboxSetting);
            iconxacnhan = itemView.findViewById(R.id.iconxacnhan);
            iconxoa = itemView.findViewById(R.id.iconhuy);
            btxoa = itemView.findViewById(R.id.btxoa);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.d("SettingAdapter", "onCreateViewHolder: Creating new ViewHolder");
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_quanlydonhang, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Setting setting = settings.get(position);
        Log.d("SettingAdapter", "onBindViewHolder: Binding data for position " + position);

        // Set initial values for name, date, and components
        holder.nameTextView.setText(setting.getName());
        holder.dateTextView.setText(setting.getDate());

        // Set the CheckBox state based on the isSelected property of the Setting
        holder.checkBox.setChecked(setting.isSelected());

        // Set the initial order status if it hasn't been updated yet
        if (setting.getOrderStatus() == null) {
            setting.setOrderStatus("Chưa xử lý");
        }

        // Display the components
        holder.componentsLayout.removeAllViews();
        String[] components = setting.getComponents().split("'");
        for (String component : components) {
            TextView componentTextView = new TextView(holder.itemView.getContext());
            componentTextView.setText(component);
            holder.componentsLayout.addView(componentTextView);
        }

        // Log the components being displayed
        Log.d("SettingAdapter", "onBindViewHolder: Components - " + setting.getComponents());

        // Listen for changes to the CheckBox state
        holder.checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            Log.d("SettingAdapter", "onCheckedChanged: CheckBox state changed to " + isChecked);

            // Update the Setting's selection state
            setting.setSelected(isChecked);

            // Update the order status based on the CheckBox state
            if (isChecked) {
                setting.setOrderStatus("Đã xử lý");
            } else {
                setting.setOrderStatus("Chưa xử lý");
            }

            // Log the updated order status
            Log.d("SettingAdapter", "onCheckedChanged: Updated order status to " + setting.getOrderStatus());

            // Call a method to update the order status in the data model or database
            updateOrderStatus(setting);

            // Notify RecyclerView to update the item
            notifyItemChanged(position);
        });

        // Set listeners for confirm, cancel, and delete actions
        holder.iconxacnhan.setOnClickListener(v -> {
            Log.d("SettingAdapter", "onClick: Confirm button clicked for setting " + setting.getName());
            listener.onConfirmClick(setting); // Confirm
        });

        holder.iconxoa.setOnClickListener(v -> {
            Log.d("SettingAdapter", "onClick: Cancel button clicked for setting " + setting.getName());
            listener.onCancelClick(setting);  // Cancel
        });

        holder.btxoa.setOnClickListener(v -> {
            Log.d("SettingAdapter", "onClick: Delete button clicked for setting " + setting.getName());
            listener.onDeleteClick(setting);  // Delete
        });
    }

    private void updateOrderStatus(Setting setting) {
        // Cập nhật trạng thái đơn hàng trong cơ sở dữ liệu hoặc mô hình dữ liệu
        // Ví dụ: nếu bạn đang sử dụng cơ sở dữ liệu Room hoặc SQLite, bạn có thể cập nhật trạng thái ở đây.
        Log.d("SettingAdapter", "updateOrderStatus: Updating order status for setting " + setting.getName());
        // Example:
        // dbHelper.updateSettingStatus(setting);
    }

    @Override
    public int getItemCount() {
        Log.d("SettingAdapter", "getItemCount: Returning item count: " + settings.size());
        return settings.size();
    }
}
