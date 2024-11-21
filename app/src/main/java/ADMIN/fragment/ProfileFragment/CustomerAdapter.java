package ADMIN.fragment.ProfileFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.duan1.R;

import java.util.ArrayList;
import java.util.List;

public class CustomerAdapter extends RecyclerView.Adapter<CustomerAdapter.CustomerViewHolder> {
    private List<Customer> customerItems;

    public CustomerAdapter(List<Customer> customerItems) {
        this.customerItems = customerItems;
    }

    @NonNull
    @Override
    public CustomerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_quanlykhachhang, parent, false);
        return new CustomerViewHolder(view);


    }

    @Override
    public void onBindViewHolder(@NonNull CustomerViewHolder holder, int position) {
        Customer item = customerItems.get(position);
        holder.tvCustomerName.setText(item.getCustomerName());
        holder.checkBox.setChecked(item.isSelected());
        holder.checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> item.setSelected(isChecked));
    }

    @Override
    public int getItemCount() {
        return customerItems.size();
    }

    public void addItem(Customer item) {
        customerItems.add(item);
        notifyItemInserted(customerItems.size() - 1);
    }

    public void removeSelectedItems() {
        customerItems.removeAll(getSelectedCustomers());
        notifyDataSetChanged();
    }

    public List<Customer> getSelectedCustomers() {
        List<Customer> selectedCustomers = new ArrayList<>();
        for (Customer customer : customerItems) {
            if (customer.isSelected()) {
                selectedCustomers.add(customer);
            }
        }
        return selectedCustomers;
    }

    public static class CustomerViewHolder extends RecyclerView.ViewHolder {
        TextView tvCustomerName, tvDayOfBirth, tvAddress, tvPhone, tvEmail;
        CheckBox checkBox;

        public CustomerViewHolder(View itemView) {
            super(itemView);
            tvCustomerName = itemView.findViewById(R.id.tvName);
            tvDayOfBirth = itemView.findViewById(R.id.tvdayofbirth);
            tvAddress = itemView.findViewById(R.id.tvAddress);
            tvPhone = itemView.findViewById(R.id.tvPhone);
            tvEmail = itemView.findViewById(R.id.tvEmail);

            // Ánh xạ checkbox
            checkBox = itemView.findViewById(R.id.checkbox);
        }
    }
}
