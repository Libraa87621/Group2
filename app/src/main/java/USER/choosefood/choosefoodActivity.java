package USER.choosefood;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.duan1.R;

import java.lang.reflect.Field;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

import USER.checkOder.CheckOderActivity;

public class choosefoodActivity extends AppCompatActivity {

    private TextView tvChooseChicken;
    private TextView tvCombo;
    private TextView tvQuantity;
    private TextView tvTotalAmount;
    private Spinner chickenSpinner;
    private ImageView ivChosenChicken;
    private TextView tvMessage;
    private int quantity = 0; // Default quantity is 0
    private int chickenPrice = 0;
    private boolean isChickenSelected = false;

    // New variables for potato selection
    private Spinner spinnerPotato;
    private TextView tvChoosePotato;
    private ImageView ivChosenPotato;
    private int potatoPrice = 0;
    private boolean isPotatoSelected = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choosefood);

        tvChooseChicken = findViewById(R.id.tv_choose_chicken);
        chickenSpinner = findViewById(R.id.spinner_chicken);
        ivChosenChicken = findViewById(R.id.iv_chosen_chicken);
        tvCombo = findViewById(R.id.combo);
        tvQuantity = findViewById(R.id.tvQuantity);
        tvTotalAmount = findViewById(R.id.tvTotalAmount);
        tvMessage = findViewById(R.id.tvMessage);

        // Potato views
        tvChoosePotato = findViewById(R.id.tv_choose_potato);
        spinnerPotato = findViewById(R.id.spinner_potato);
        ivChosenPotato = findViewById(R.id.iv_chosen_potato);
        setSpinnerPopupHeight(chickenSpinner, 2);
        setSpinnerPopupHeight(spinnerPotato, 2);

        // Set up chicken spinner
        List<ChickenItem> chickenItems = new ArrayList<>();
        chickenItems.add(new ChickenItem(" miếng gà (nhỏ) vui vẻ", R.drawable.chicken_piece_1, 91000));
        chickenItems.add(new ChickenItem(" miếng gà (lớn) vui vẻ", R.drawable.chicken_piece_2, 182000));
        ChickenAdapter adapter = new ChickenAdapter(this, chickenItems);
        chickenSpinner.setAdapter(adapter);

        // Set up potato spinner
        List<PotatoItem> potatoItems = new ArrayList<>();
        potatoItems.add(new PotatoItem("Khoai tây chiên nhỏ", R.drawable.potato_lagre, 15000));
        potatoItems.add(new PotatoItem("Khoai tây chiên lớn", R.drawable.potato_lagre, 25000));
        PotatoAdapter potatoAdapter = new PotatoAdapter(this, potatoItems);
        spinnerPotato.setAdapter(potatoAdapter);

        // Chicken selection event
        tvChooseChicken.setOnClickListener(v -> {
            chickenSpinner.performClick();
            chickenSpinner.setVisibility(View.VISIBLE);
        });

        chickenSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                ChickenItem selectedItem = (ChickenItem) parentView.getItemAtPosition(position);
                tvChooseChicken.setText(selectedItem.getName());
                ivChosenChicken.setImageResource(selectedItem.getImageResId());
                chickenPrice = selectedItem.getPrice();
                isChickenSelected = true;
                // Set default quantity to 1 when chicken is selected
                quantity = 1;
                tvQuantity.setText(String.valueOf(quantity));
                updateComboText();
                updateTotalAmount();
                chickenSpinner.setVisibility(View.GONE);
                tvMessage.setVisibility(View.GONE); // Hide message when chicken is selected
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
            }
        });

        // Potato selection event
        tvChoosePotato.setOnClickListener(v -> {
            spinnerPotato.performClick();
            spinnerPotato.setVisibility(View.VISIBLE);
        });

        spinnerPotato.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                PotatoItem selectedItem = (PotatoItem) parentView.getItemAtPosition(position);
                tvChoosePotato.setText(selectedItem.getName());
                ivChosenPotato.setImageResource(selectedItem.getImageResId());
                potatoPrice = selectedItem.getPrice();
                isPotatoSelected = true;
                // Set default quantity to 1 when potato is selected
                quantity = 1;
                tvQuantity.setText(String.valueOf(quantity));
                updateComboText();
                updateTotalAmount();
                spinnerPotato.setVisibility(View.GONE);
                tvMessage.setVisibility(View.GONE); // Hide message when potato is selected
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
            }
        });

        // Quantity increase and decrease buttons
        Button btnDecrease = findViewById(R.id.btnDecrease);
        btnDecrease.setOnClickListener(v -> {
            if (quantity > 0) { // Allow quantity to go down to 0
                quantity--;
                tvQuantity.setText(String.valueOf(quantity));
                updateTotalAmount();
            } else {
                // If no item is selected, show message
                if (!isChickenSelected && !isPotatoSelected) {
                    tvMessage.setText("Vui lòng chọn ít nhất một món!");
                    tvMessage.setVisibility(View.VISIBLE);
                }
            }
        });

        Button btnIncrease = findViewById(R.id.btnIncrease);
        btnIncrease.setOnClickListener(v -> {
            if (isChickenSelected || isPotatoSelected) { // Allow quantity increase if at least one item is selected
                quantity++;
                tvQuantity.setText(String.valueOf(quantity));
                updateTotalAmount();
            } else {
                // If no item is selected, show message
                tvMessage.setText("Vui lòng chọn ít nhất một món!");
                tvMessage.setVisibility(View.VISIBLE);
            }
        });

        // Order button
        // Order button
        Button btnOrder = findViewById(R.id.btnOrder);
        btnOrder.setOnClickListener(v -> {
            if (isChickenSelected || isPotatoSelected) { // Check if at least one item is selected
                // Create combo string and price
                String comboName = "";
                int totalAmount = 0;
                int comboImageResId = 0;

                if (isChickenSelected) {
                    comboName += tvChooseChicken.getText().toString();
                    totalAmount += chickenPrice;
                    comboImageResId = R.drawable.anhsanpham; // Replace with your actual image resource ID
                }

                if (isPotatoSelected) {
                    comboName += (isChickenSelected ? " + " : "") + tvChoosePotato.getText().toString();
                    totalAmount += potatoPrice;
                    comboImageResId = R.drawable.potato_lagre; // Replace with your actual image resource ID
                }

                totalAmount *= quantity; // Total for the selected quantity

                // Create a Combo object
                Combo combo = new Combo(comboName, String.valueOf(totalAmount), String.valueOf(totalAmount), comboImageResId);

                // Pass Combo object to CheckOderActivity
                Intent intent = new Intent(choosefoodActivity.this, CheckOderActivity.class);
                intent.putExtra("combo", combo);  // Passing the Combo object directly

                // Start CheckOderActivity
                startActivity(intent);
            } else {
                // If no item is selected, show message
                tvMessage.setText("Vui lòng chọn ít nhất một món!");
                tvMessage.setVisibility(View.VISIBLE);
            }
        });
    }

        // Set the spinner height for a better user experience
    private void setSpinnerPopupHeight(Spinner spinner, int maxVisibleItems) {
        try {
            Field popupField = Spinner.class.getDeclaredField("mPopup");
            popupField.setAccessible(true);
            Object popup = popupField.get(spinner);

            if (popup instanceof android.widget.ListPopupWindow) {
                android.widget.ListPopupWindow listPopupWindow = (android.widget.ListPopupWindow) popup;
                int itemHeight = getResources().getDimensionPixelSize(R.dimen.spinner_item_height); // Define this in dimen file
                listPopupWindow.setHeight(itemHeight * maxVisibleItems);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Update combo text to show selected chicken and potato items
    private void updateComboText() {
        String comboText = "Combo: ";
        if (isChickenSelected) {
            comboText += tvChooseChicken.getText();
        }
        if (isPotatoSelected) {
            comboText += (isChickenSelected ? " + " : "") + tvChoosePotato.getText();
        }
        tvCombo.setText(comboText);
    }

    // Update the total amount displayed
    private void updateTotalAmount() {
        int totalAmount = 0;
        if (isChickenSelected) {
            totalAmount += chickenPrice;
        }
        if (isPotatoSelected) {
            totalAmount += potatoPrice;
        }
        totalAmount *= quantity; // Total for the selected quantity

        String formattedAmount = NumberFormat.getInstance().format(totalAmount) + " đ";
        tvTotalAmount.setText(formattedAmount);
    }
}
