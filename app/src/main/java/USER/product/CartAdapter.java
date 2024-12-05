package USER.product;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.duan1.R;

import java.util.ArrayList;

public class CartAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<Cart> cartList;

    public CartAdapter(Context context, ArrayList<Cart> cartList) {
        this.context = context;
        this.cartList = cartList;
    }

    @Override
    public int getCount() {
        return cartList.size();
    }

    @Override
    public Object getItem(int position) {
        return cartList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_cart, parent, false);
        }

        // Get current cart item
        Cart cart = cartList.get(position);

        // Bind data to views
        ImageView imgProduct = convertView.findViewById(R.id.imgProduct);
        TextView txtProductName = convertView.findViewById(R.id.txtProductName);

        imgProduct.setImageResource(cart.getProductImage());
        txtProductName.setText(cart.getProductName());

        return convertView;
    }
}
