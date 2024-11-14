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

import USER.Order.CheckOderActivity;

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
        chickenItems.add(new ChickenItem("Miếng gà (nhỏ)", R.drawable.chicken_piece_1, 91000));
        chickenItems.add(new ChickenItem("Miếng gà (lớn)", R.drawable.chicken_piece_2, 182000));
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
                quantity = 1;
                tvQuantity.setText(String.valueOf(quantity));
                updateComboText();
                updateTotalAmount();
                chickenSpinner.setVisibility(View.GONE);
                tvMessage.setVisibility(View.GONE);
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
                quantity = 1;
                tvQuantity.setText(String.valueOf(quantity));
                updateComboText();
                updateTotalAmount();
                spinnerPotato.setVisibility(View.GONE);
                tvMessage.setVisibility(View.GONE);
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
                if (!isChickenSelected && !isPotatoSelected) {
                    tvMessage.setText("Vui lòng chọn ít nhất một món!");
                    tvMessage.setVisibility(View.VISIBLE);
                }
            }
        });

        Button btnIncrease = findViewById(R.id.btnIncrease);
        btnIncrease.setOnClickListener(v -> {
            if (isChickenSelected || isPotatoSelected) {
                quantity++;
                tvQuantity.setText(String.valueOf(quantity));
                updateTotalAmount();
            } else {
                tvMessage.setText("Vui lòng chọn ít nhất một món!");
                tvMessage.setVisibility(View.VISIBLE);
            }
        });

        Button btnOrder = findViewById(R.id.btnOrder);
        btnOrder.setOnClickListener(v -> {
            if (isChickenSelected || isPotatoSelected) {
                // Hide the message if items are selected
                tvMessage.setVisibility(View.GONE);

                // Create combo string and calculate total price
                StringBuilder comboName = new StringBuilder();
                int totalAmount = 0;
                int comboImageResId = 0;

                // Check if Chicken is selected
                String nameChicken = "";
                String priceChicken = "";
                if (isChickenSelected) {
                    nameChicken = tvChooseChicken.getText().toString();
                    priceChicken = String.valueOf(chickenPrice);
                    totalAmount += chickenPrice;
                    comboImageResId = R.drawable.anhsanpham;
                    comboName.append(nameChicken);
                }

                // Check if Potato is selected
                String namePotato = "";
                String pricePotato = "";
                if (isPotatoSelected) {
                    if (comboName.length() > 0) {
                        comboName.append(" + ");
                    }
                    namePotato = tvChoosePotato.getText().toString();
                    pricePotato = String.valueOf(potatoPrice);
                    totalAmount += potatoPrice;
                    comboImageResId = R.drawable.anhsanpham; // Replace with actual image resource ID for Potato
                    comboName.append(namePotato);
                }

                // Adjust total amount based on quantity
                totalAmount *= Math.max(quantity, 1);

                // Create a Combo object with the updated quantity
                Combo combo = new Combo(comboName.toString(), priceChicken, namePotato, pricePotato, totalAmount, quantity, comboImageResId);

                // Pass Combo object to CheckOderActivity via Intent
                Intent intent = new Intent(choosefoodActivity.this, CheckOderActivity.class);
                intent.putExtra("combo", combo); // Pass the Combo object with updated quantity
                startActivity(intent);
            } else {
                tvMessage.setText("Vui lòng chọn ít nhất một món!");
                tvMessage.setVisibility(View.VISIBLE);
            }
        });
    }

        private void setSpinnerPopupHeight(Spinner spinner, int maxVisibleItems) {
        try {
            Field popupField = Spinner.class.getDeclaredField("mPopup");
            popupField.setAccessible(true);
            Object popup = popupField.get(spinner);

            if (popup instanceof android.widget.ListPopupWindow) {
                android.widget.ListPopupWindow listPopupWindow = (android.widget.ListPopupWindow) popup;
                listPopupWindow.setHeight(maxVisibleItems * 48); // Item height in pixels
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void updateComboText() {
        StringBuilder combo = new StringBuilder();
        if (isChickenSelected) {
            combo.append(tvChooseChicken.getText());
        }
        if (isPotatoSelected) {
            if (combo.length() > 0) {
                combo.append(" + ");
            }
            combo.append(tvChoosePotato.getText());
        }
        tvCombo.setText(combo.toString());
    }

    private void updateTotalAmount() {
        int totalAmount = 0;
        if (isChickenSelected) {
            totalAmount += chickenPrice;
        }
        if (isPotatoSelected) {
            totalAmount += potatoPrice;
        }
        totalAmount *= quantity;

        // Create a custom format with a thousand separator
        NumberFormat currencyFormat = NumberFormat.getInstance();
        currencyFormat.setGroupingUsed(true); // Enable grouping for thousands separator

        // Format the total amount and set it to the TextView
        String formattedAmount = currencyFormat.format(totalAmount);
        tvTotalAmount.setText(formattedAmount);
    }

}
