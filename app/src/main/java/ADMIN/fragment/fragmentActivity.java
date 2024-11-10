package ADMIN.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import com.example.duan1.R;

import ADMIN.fragment.FinanceFragment.FinanceFragment;
import ADMIN.fragment.OrdersFragment.OrdersFragment;
import ADMIN.fragment.ProfileFragment.ProfileFragment;
import ADMIN.fragment.SearchFragment.SearchFragment;
import ADMIN.fragment.SettingsFragment.SettingsFragment;

public class fragmentActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_adm);

        // Tìm các ImageView cho từng icon
        ImageView iconSettings = findViewById(R.id.icon_settings);
        ImageView iconSearch = findViewById(R.id.icon_search);
        ImageView iconOrders = findViewById(R.id.icon_orders);
        ImageView iconFinance = findViewById(R.id.icon_finance);
        ImageView iconProfile = findViewById(R.id.icon_profile);

        // Load SettingsFragment mặc định khi mở ứng dụng
        loadFragment(new SettingsFragment());

        // Thiết lập sự kiện click cho từng icon để chuyển Fragment
        iconSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadFragment(new SettingsFragment());
            }
        });

        iconSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadFragment(new SearchFragment());
            }
        });

        iconOrders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadFragment(new OrdersFragment());
            }
        });

        iconFinance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadFragment(new FinanceFragment());
            }
        });

        iconProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadFragment(new ProfileFragment());
            }
        });
    }

    // Hàm để tải Fragment vào FrameLayout
    private void loadFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}
