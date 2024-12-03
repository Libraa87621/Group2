package ADMIN.fragment.OrdersFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.duan1.R;

import java.util.List;

public class OrdersAdapter extends RecyclerView.Adapter<OrdersAdapter.OrderViewHolder> {

    private List<Orders> orderList;

    public OrdersAdapter(List<Orders> orderList) {
        this.orderList = orderList;
    }

    @NonNull
    @Override
    public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_order, parent, false);
        return new OrderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderViewHolder holder, int position) {
        Orders order = orderList.get(position);

        // Hiển thị thông tin đơn hàng
        holder.bind(order);

        // Xử lý sự kiện xóa đơn hàng
        holder.btnDeleteOrder.setOnClickListener(v -> {
            orderList.remove(position);
            notifyItemRemoved(position);
            notifyItemRangeChanged(position, orderList.size());  // Cập nhật lại các item sau khi xóa
        });

        // Xử lý sự kiện sửa đơn hàng
        holder.btnEditOrder.setOnClickListener(v -> {
            // Logic sửa đơn hàng (ví dụ, mở một dialog hoặc activity để chỉnh sửa)
            // Bạn có thể sử dụng Intent để mở một activity khác để sửa thông tin
        });
    }

    @Override
    public int getItemCount() {
        return orderList.size();
    }

    public static class OrderViewHolder extends RecyclerView.ViewHolder {
        private TextView orderName, orderDescription, orderPrice;
        private ImageView orderImage, btnDeleteOrder, btnEditOrder;

        public OrderViewHolder(@NonNull View itemView) {
            super(itemView);
            // Ánh xạ các thành phần UI
            orderName = itemView.findViewById(R.id.orderName);
            orderDescription = itemView.findViewById(R.id.orderDescription);
            orderPrice = itemView.findViewById(R.id.orderPrice);
            orderImage = itemView.findViewById(R.id.orderImage);
            btnDeleteOrder = itemView.findViewById(R.id.btnDeleteOrder);
            btnEditOrder = itemView.findViewById(R.id.btnEditOrder);
        }

        // Hàm để ánh xạ và hiển thị thông tin đơn hàng
        public void bind(Orders order) {
            orderName.setText(order.getName());
            orderDescription.setText("Mô tả: " + order.getDescription());
            orderPrice.setText("Giá: " + order.getPrice() + " VND");

            // Hiển thị ảnh của đơn hàng
            if (order.getImageResourceId() != 0) {
                orderImage.setImageResource(order.getImageResourceId());
            } else {
                orderImage.setImageResource(R.drawable.default_image);  // Hiển thị ảnh mặc định nếu không có ảnh
            }
        }
    }
}
