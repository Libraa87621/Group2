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
import USER.Order.check1;

public class ChoosefoodActivity2 extends AppCompatActivity {

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

    private Spinner spinnerPotato;
    private TextView tvChoosePotato;
    private ImageView ivChosenPotato;
    private int potatoPrice = 0;
    private boolean isPotatoSelected = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.choosefood);

        tvChooseChicken = findViewById(R.id.tv_choose_chicken);
        chickenSpinner = findViewById(R.id.spinner_chicken);
        ivChosenChicken = findViewById(R.id.iv_chosen_chicken);
        tvCombo = findViewById(R.id.combo);
        tvQuantity = findViewById(R.id.tvQuantity);
        tvTotalAmount = findViewById(R.id.tvTotalAmount);
        tvMessage = findViewById(R.id.tvMessage);

        tvChoosePotato = findViewById(R.id.tv_choose_potato);
        spinnerPotato = findViewById(R.id.spinner_potato);
        ivChosenPotato = findViewById(R.id.iv_chosen_potato);

        setSpinnerPopupHeight(chickenSpinner, 2);
        setSpinnerPopupHeight(spinnerPotato, 2);

        // Set up chicken spinner
        List<ChickenItem> chickenItems = new ArrayList<>();
        chickenItems.add(new ChickenItem("Nước Lọc", R.drawable.img_2, 5000));
        chickenItems.add(new ChickenItem("Nước ngọt", R.drawable.img_3, 20000));
        ChickenAdapter adapter = new ChickenAdapter(this, chickenItems);
        chickenSpinner.setAdapter(adapter);

        // Set up potato spinner
        List<PotatoItem> potatoItems = new ArrayList<>();
        potatoItems.add(new PotatoItem("Bánh Nướng", R.drawable.img_4, 15000));
        potatoItems.add(new PotatoItem("Bánh Rán", R.drawable.img_5, 25000));
        PotatoAdapter potatoAdapter = new PotatoAdapter(this, potatoItems);
        spinnerPotato.setAdapter(potatoAdapter);

        // Chicken selection
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

        // Potato selection
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

        // Quantity control
        Button btnDecrease = findViewById(R.id.btnDecrease);
        btnDecrease.setOnClickListener(v -> {
            if (quantity > 0) {
                quantity--;
                tvQuantity.setText(String.valueOf(quantity));
                updateTotalAmount();
            } else if (!isChickenSelected && !isPotatoSelected) {
                tvMessage.setText("Vui lòng chọn ít nhất một món!");
                tvMessage.setVisibility(View.VISIBLE);
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

        // Order button
        Button btnOrder = findViewById(R.id.btnOrder);
        btnOrder.setOnClickListener(v -> {
            if (isChickenSelected || isPotatoSelected) {
                tvMessage.setVisibility(View.GONE);

                StringBuilder comboName = new StringBuilder();
                int totalAmount = 0;
                int comboImageResId = 0;

                if (isChickenSelected) {
                    comboName.append(tvChooseChicken.getText().toString());
                    totalAmount += chickenPrice;
                }

                if (isPotatoSelected) {
                    if (comboName.length() > 0) {
                        comboName.append(" + ");
                    }
                    comboName.append(tvChoosePotato.getText().toString());
                    totalAmount += potatoPrice;
                }

                totalAmount *= Math.max(quantity, 1);

                Combo combo = new Combo(
                        comboName.toString(),
                        String.valueOf(chickenPrice),
                        tvChoosePotato.getText().toString(),
                        String.valueOf(potatoPrice),
                        totalAmount,
                        quantity,
                        R.drawable.anhsanpham
                );

                Intent intent = new Intent(ChoosefoodActivity2.this, check1.class);
                intent.putExtra("combo", combo);
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
                listPopupWindow.setHeight(maxVisibleItems * 48);
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

        NumberFormat currencyFormat = NumberFormat.getInstance();
        currencyFormat.setGroupingUsed(true);

        String formattedAmount = currencyFormat.format(totalAmount);
        tvTotalAmount.setText(formattedAmount);
    }
}
