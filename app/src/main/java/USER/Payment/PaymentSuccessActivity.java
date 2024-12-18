package USER.Payment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import Database.DBHelper;

import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.example.duan1.R;

import USER.Home.HomeActivity;
import USER.choosefood.Combo;
import USER.login_signin.DangNhapActivity;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import androidx.drawerlayout.widget.DrawerLayout;
import com.google.android.material.navigation.NavigationView;

public class PaymentSuccessActivity extends AppCompatActivity {

    private DBHelper dbHelper = new DBHelper(this);

    private DrawerLayout drawerLayout;
    private NavigationView navigationView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_success);

        // [1] **Khai báo và ánh xạ các thành phần UI**
        TextView tvPromotionValue = findViewById(R.id.tvPromotionValue);
        TextView totalPriceTextView = findViewById(R.id.tvTotalAmountValue);
        LinearLayout container = findViewById(R.id.containerLinearLayout);
        TextView tvPaymentMethodValue = findViewById(R.id.tvPaymentMethodValue);
        TextView tvAddressValue = findViewById(R.id.tvAddressValue);
        TextView tvPhoneValue = findViewById(R.id.tvPhoneValue);
        TextView tvEstimatedTimeValue = findViewById(R.id.tvEstimatedTime);
        TextView tvShippingFeeValue = findViewById(R.id.tvShippingFeeValue);
        Button btnquayve = findViewById(R.id.btnquayve);


        // [2] **Lấy dữ liệu từ Intent**
        String promoCode = getIntent().getStringExtra("promoCode");
        double discountedAmount = getIntent().getDoubleExtra("totalAmount", 0);
        Combo combo = getIntent().getParcelableExtra("combo");
        String address = getIntent().getStringExtra("address");
        String name = getIntent().getStringExtra("fullName");
        String phone = getIntent().getStringExtra("phoneNumber");
        String shippingFee = getIntent().getStringExtra("shippingFee");
        String paymentMethod = getIntent().getStringExtra("paymentMethod");



        // Retrieve email, password, and date from SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("AppData", MODE_PRIVATE);
        String email = sharedPreferences.getString("email", null); // Get email
        String password = sharedPreferences.getString("password", null); // Get password
        String date = sharedPreferences.getString("date", null); // Get date (if available)






        // [3] **Ghi log kiểm tra dữ liệu**
        Log.d("PaymentSuccess", "Promo Code: " + promoCode);
        Log.d("PaymentSuccess", "discountedAmount: " + discountedAmount);
        Log.d("PaymentSuccess", "Address: " + address);
        Log.d("PaymentSuccess", "Phone: " + phone);
        Log.d("PaymentSuccess", "Shipping Fee: " + shippingFee);
        Log.d("PaymentSuccess", "Payment Method: " + paymentMethod);

        // [4] **Lưu thông tin Combo vào CSDL**
        if (combo != null) {
            String components = "";
            if (!combo.getNameChicken().isEmpty()) {
                components += combo.getQuantity() + " x " + combo.getNameChicken() + ", ";
            }
            if (!combo.getNamePotato().isEmpty()) {
                components += combo.getQuantity() + " x " + combo.getNamePotato();
            }
            String currentDate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
            long orderId = dbHelper.addOrder(
                    currentDate,                               // Ngày thanh toán
                    tvEstimatedTimeValue.getText().toString(), // Thời gian ước tính giao hàng
                    address != null ? address : "N/A",        // Địa chỉ giao hàng
                    String.valueOf(combo.getImageResId()),    // ID ảnh
                    components,                               // Chi tiết combo
                    discountedAmount,                         // Tổng giá trị đơn hàng
                    combo.getQuantity()                       // Số lượng
            );

            if (orderId != -1) {
                Log.d("Database", "Order saved successfully with ID: " + orderId);
            } else {
                Log.e("Database", "Failed to save order");
            }
        }

        // [5] **Lưu thông tin người dùng vào CSDL**
        if (phone != null && !phone.isEmpty() && address != null && !address.isEmpty()) {
            long userId = dbHelper.addUser(
                    name,
                    email,
                    phone,
                    date,
                    address

            );

            if (userId != -1) {
                Log.d("Database", "User saved successfully with ID: " + userId);
            } else {
                Log.e("Database", "Failed to save user");
            }
        }

        // [6] **Cập nhật giao diện hiển thị thông tin**
        DecimalFormat formatter = new DecimalFormat("#,###");
        totalPriceTextView.setText(formatter.format(discountedAmount) + " VND");
        tvPromotionValue.setText(promoCode != null ? promoCode : "No promo code applied");
        tvPhoneValue.setText(phone != null && !phone.isEmpty() ? phone : "N/A");
        tvEstimatedTimeValue.setText(getDeliveryTimeBasedOnAddress(address));
        tvAddressValue.setText(address != null && !address.isEmpty() ? address : "N/A");
        tvShippingFeeValue.setText(shippingFee != null && !shippingFee.isEmpty() ? shippingFee + " VND" : "0 VND");
        tvPaymentMethodValue.setText(paymentMethod != null && !paymentMethod.isEmpty() ? paymentMethod : "No payment method provided");

        // [7] **Hiển thị thông tin Combo nếu có**
        if (combo != null) {
            LayoutInflater inflater = LayoutInflater.from(this);
            LinearLayout itemLayout = (LinearLayout) inflater.inflate(R.layout.item_combo, container, false);

            ImageView imgCombo = itemLayout.findViewById(R.id.imgCombo);
            TextView tvComboName = itemLayout.findViewById(R.id.tvComboName);
            TextView tvingredientChicken = itemLayout.findViewById(R.id.tvingredientChicken);
            TextView tvingredientPotato = itemLayout.findViewById(R.id.tvingredientPotato);

            if (!combo.getNameChicken().isEmpty()) {
                tvingredientChicken.setText(combo.getQuantity() + " x " + combo.getNameChicken() + " + " + formatter.format(Integer.parseInt(combo.getPriceChicken())) + " đ");
            } else {
                tvingredientChicken.setVisibility(View.GONE);
            }

            if (!combo.getNamePotato().isEmpty()) {
                tvingredientPotato.setText(combo.getQuantity() + " x " + combo.getNamePotato() + " + " + formatter.format(Integer.parseInt(combo.getPricePotato())) + " đ");
            } else {
                tvingredientPotato.setVisibility(View.GONE);
            }

            tvComboName.setText(!combo.getNameChicken().isEmpty() ? combo.getNameChicken() : combo.getNamePotato());
            imgCombo.setImageResource(combo.getImageResId());

            container.addView(itemLayout);
        }

        ImageButton btnMenu = findViewById(R.id.btnMenu);
        btnMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Tạo PopupMenu
                PopupMenu popupMenu = new PopupMenu(PaymentSuccessActivity.this, btnMenu);

                // Inflate menu từ file XML
                MenuInflater inflater = getMenuInflater();
                inflater.inflate(R.menu.drawer_menu, popupMenu.getMenu());

                // Set listener cho các item menu
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        // Sử dụng if-else thay cho switch-case
                        if (item.getItemId() == R.id.nav_home) {
                            // Xử lý sự kiện khi chọn 'Trang chủ'
                            return true;
                        } else if (item.getItemId() == R.id.nav_cart) {
                            // Xử lý sự kiện khi chọn 'Hồ sơ'
                            return true;
                        } else if (item.getItemId() == R.id.nav_logout) {
                            // Xử lý sự kiện khi chọn 'Đăng xuất'
                            // Xóa thông tin đăng nhập (nếu có, ví dụ sử dụng SharedPreferences)
                            SharedPreferences preferences = getSharedPreferences("UserPreferences", MODE_PRIVATE);
                            SharedPreferences.Editor editor = preferences.edit();
                            editor.clear(); // Xóa dữ liệu đăng nhập
                            editor.apply();

                            // Chuyển hướng đến LoginActivity
                            Intent intent = new Intent(PaymentSuccessActivity.this, DangNhapActivity.class);
                            startActivity(intent);
                            finish(); // Kết thúc hoạt động hiện tại (PaymentSuccessActivity)

                            return true;
                        } else {
                            return false;
                        }
                    }
                });

                // Hiển thị menu
                popupMenu.show();
            }
        });


        // [8] **Xử lý sự kiện nút "Quay về"**
        btnquayve.setOnClickListener(v -> {
            Intent intent = new Intent(PaymentSuccessActivity.this, HomeActivity.class);
            startActivity(intent);
        });
    }

    // [9] **Lấy thời gian giao hàng dựa trên địa chỉ**
    private String getDeliveryTimeBasedOnAddress(String address) {
        if (address == null || address.isEmpty()) {
            return "Thời gian giao hàng không xác định";
        }
        if (address.toLowerCase().contains("trung my tay")) {
            return "30 phút";
        } else if (address.toLowerCase().contains("quang trung")) {
            return "45 phút";
        } else if (address.toLowerCase().contains("tan chanh hiep")) {
            return "40 phút";
        } else {
            return "Thời gian giao hàng không xác định";
        }
    }
}
