package ADMIN.qlkhachhang;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.duan1.R;

import java.util.List;

public class CustomerAdapter extends RecyclerView.Adapter<CustomerAdapter.CustomerViewHolder> {
    private List<Customer> customerItems;

    public CustomerAdapter(List<Customer> customerItems) {
        this.customerItems = customerItems;
    }

    @NonNull
    @Override
    public CustomerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(com.example.duan1.R.layout.item_quanlykhachhang, parent, false);
        return new CustomerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomerViewHolder holder, int position) {
        Customer item = customerItems.get(position);
        holder.tvDate.setText(item.getDate());
        holder.tvCustomerName.setText(item.getCustomerName());
        holder.tvItems.setText(item.getItems());
        holder.imageView1.setImageResource(item.getImageResource1());
        holder.imageView2.setImageResource(item.getImageResource2());
    }

    @Override
    public int getItemCount() {
        return customerItems.size();
    }

    public void addItem(Customer item) {
        customerItems.add(item);
        notifyItemInserted(customerItems.size() - 1);
    }

    public class CustomerViewHolder extends RecyclerView.ViewHolder {
        TextView tvDate, tvCustomerName, tvItems;
        ImageView imageView1, imageView2;

        public CustomerViewHolder(View itemView) {
            super(itemView);
            tvDate = itemView.findViewById(R.id.tvDate);
            tvCustomerName = itemView.findViewById(R.id.tvCustomerName);
            tvItems = itemView.findViewById(R.id.tvItems);
            imageView1 = itemView.findViewById(R.id.imageView2);
            imageView2 = itemView.findViewById(R.id.imageView3);
        }
    }
}
