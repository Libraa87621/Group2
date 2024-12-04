package USER.product;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.duan1.R;

import java.util.ArrayList;
import java.util.List;

// Assuming this is in your CartAdapter
public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {

    private List<Cart> cartList;

    public CartAdapter(List<Cart> cartList) {
        this.cartList = cartList;
    }

    @Override
    public CartViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cart, parent, false);
        return new CartViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CartViewHolder holder, int position) {
        Cart cart = cartList.get(position);

        // Set product name and image in the item view
        holder.txtProductName.setText(cart.getProductName());
        holder.imgProduct.setImageResource(cart.getProductImage()); // Use the correct getter method here
    }

    @Override
    public int getItemCount() {
        return cartList.size();
    }

    public static class CartViewHolder extends RecyclerView.ViewHolder {

        ImageView imgProduct;
        TextView txtProductName;

        public CartViewHolder(View itemView) {
            super(itemView);
            imgProduct = itemView.findViewById(R.id.imgProduct);
            txtProductName = itemView.findViewById(R.id.txtProductName);
        }
    }
}
