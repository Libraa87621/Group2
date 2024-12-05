package ADMIN.fragment.ProfileFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.duan1.R;

import java.util.List;

public class CustomerAdapter extends RecyclerView.Adapter<CustomerAdapter.CustomerViewHolder> {
    private List<Customer> customers;

    // Constructor
    public CustomerAdapter(List<Customer> customers) {
        this.customers = customers;
    }

    @NonNull
    @Override
    public CustomerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_quanlykhachhang, parent, false);
        return new CustomerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomerViewHolder holder, int position) {
        Customer customer = customers.get(position);
        holder.tvName.setText(customer.getName());
        holder.tvbirthdate.setText(customer.getBirthdate());
        holder.tvAddress.setText(customer.getAddress());
        holder.tvPhone.setText(customer.getPhone());
        holder.tvEmail.setText(customer.getEmail());

        // Thiết lập trạng thái checkbox
        holder.checkBox.setChecked(customer.isSelected());

        // Thiết lập sự kiện cho checkbox
        holder.checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            customer.setSelected(isChecked);
        });

        // Đảm bảo rằng khi checkbox được thay đổi, không gây ra sự kiện không mong muốn
        holder.checkBox.setOnCheckedChangeListener(null);
        holder.checkBox.setChecked(customer.isSelected());
        holder.checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            customer.setSelected(isChecked);
        });
    }

    @Override
    public int getItemCount() {
        return customers.size();
    }

    public static class CustomerViewHolder extends RecyclerView.ViewHolder {
        TextView tvName, tvbirthdate, tvAddress, tvPhone, tvEmail;
        CheckBox checkBox;

        public CustomerViewHolder(View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvName);
            tvbirthdate = itemView.findViewById(R.id.tvbirthdate);
            tvAddress = itemView.findViewById(R.id.tvAddress);
            tvPhone = itemView.findViewById(R.id.tvPhone);
            tvEmail = itemView.findViewById(R.id.tvEmail);
            checkBox = itemView.findViewById(R.id.checkbox);
        }
    }
}
