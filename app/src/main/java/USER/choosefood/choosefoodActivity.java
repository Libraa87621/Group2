package USER.choosefood;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.duan1.R;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

public class choosefoodActivity extends AppCompatActivity {

    private TextView tvChooseChicken;
    private TextView tvCombo;  // TextView cho combo
    private TextView tvQuantity; // TextView hiển thị số lượng
    private TextView tvTotalAmount; // TextView hiển thị tổng tiền
    private Spinner chickenSpinner;
    private ImageView ivChosenChicken;
    private TextView tvMessage;  // TextView hiển thị thông báo lỗi

    private int quantity = 1; // Biến lưu số lượng món ăn đã chọn
    private int pricePerItem = 0; // Giá mỗi món ăn (ban đầu là 0)
    private boolean isChickenSelected = false; // Biến kiểm tra xem đã chọn món gà hay chưa

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choosefood);

        tvChooseChicken = findViewById(R.id.tv_choose_chicken);
        chickenSpinner = findViewById(R.id.spinner_chicken);
        ivChosenChicken = findViewById(R.id.iv_chosen_chicken);
        tvCombo = findViewById(R.id.combo);  // TextView combo
        tvQuantity = findViewById(R.id.tvQuantity); // TextView số lượng
        tvTotalAmount = findViewById(R.id.tvTotalAmount); // TextView tổng tiền
        tvMessage = findViewById(R.id.tvMessage);  // TextView thông báo lỗi

        // Tạo danh sách các mục cho Spinner với giá tiền
        List<ChickenItem> chickenItems = new ArrayList<>();
        chickenItems.add(new ChickenItem("1 miếng gà giòn vui vẻ", R.drawable.chicken_piece_1, 91000)); // Giá 91.000 đồng
        chickenItems.add(new ChickenItem("2 miếng gà giòn vui vẻ", R.drawable.chicken_piece_2, 182000)); // Giá 182.000 đồng

        // Tạo một Adapter tùy chỉnh
        ChickenAdapter adapter = new ChickenAdapter(this, chickenItems);
        chickenSpinner.setAdapter(adapter);

        // Lắng nghe sự kiện nhấn vào TextView để mở Spinner
        tvChooseChicken.setOnClickListener(v -> {
            chickenSpinner.performClick();
            chickenSpinner.setVisibility(View.VISIBLE);
        });

        // Lắng nghe sự kiện chọn một mục trong Spinner
        chickenSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                ChickenItem selectedItem = (ChickenItem) parentView.getItemAtPosition(position);
                tvChooseChicken.setText(selectedItem.getName());
                ivChosenChicken.setImageResource(selectedItem.getImageResId());

                // Cập nhật TextView combo để hiển thị tên combo đã chọn
                tvCombo.setText("Combo: " + selectedItem.getName());  // Hiển thị tên combo đã chọn

                // Cập nhật giá món ăn
                pricePerItem = selectedItem.getPrice();

                // Đánh dấu là đã chọn món gà
                isChickenSelected = true;

                // Cập nhật tổng tiền
                updateTotalAmount();

                // Ẩn Spinner sau khi chọn
                chickenSpinner.setVisibility(View.GONE);

                // Ẩn thông báo lỗi khi đã chọn món gà
                tvMessage.setVisibility(View.GONE);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // Không có hành động khi không có lựa chọn
            }
        });

        // Sự kiện cho nút giảm số lượng
        Button btnDecrease = findViewById(R.id.btnDecrease);
        btnDecrease.setOnClickListener(v -> {
            if (isChickenSelected && quantity > 1) {
                quantity--;  // Giảm số lượng
                tvQuantity.setText(String.valueOf(quantity));  // Cập nhật số lượng
                updateTotalAmount(); // Cập nhật tổng tiền
            } else if (!isChickenSelected) {
                // Nếu chưa chọn món gà, hiển thị thông báo dưới nút tăng/giảm
                tvMessage.setText("Vui lòng chọn combo trước!");
                tvMessage.setVisibility(View.VISIBLE);  // Hiển thị thông báo
            }
        });

        // Sự kiện cho nút tăng số lượng
        Button btnIncrease = findViewById(R.id.btnIncrease);
        btnIncrease.setOnClickListener(v -> {
            if (isChickenSelected) {
                quantity++;  // Tăng số lượng
                tvQuantity.setText(String.valueOf(quantity));  // Cập nhật số lượng
                updateTotalAmount(); // Cập nhật tổng tiền
            } else {
                // Nếu chưa chọn món gà, hiển thị thông báo dưới nút tăng/giảm
                tvMessage.setText("Vui lòng chọn combo trước!");
                tvMessage.setVisibility(View.VISIBLE);  // Hiển thị thông báo
            }
        });
    }

    // Hàm cập nhật tổng tiền
    private void updateTotalAmount() {
        // Kiểm tra nếu chưa chọn món gà, không cập nhật tổng tiền
        if (isChickenSelected) {
            int totalAmount = pricePerItem * quantity;  // Tính tổng tiền

            // Format số tiền với dấu phẩy
            String formattedAmount = NumberFormat.getInstance().format(totalAmount);

            // Hiển thị tổng tiền với ký hiệu đ
            tvTotalAmount.setText(formattedAmount + " đ");
        } else {
            tvTotalAmount.setText("0 đ");
        }
    }
}
