package ADMIN.fragment.FinanceFragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.duan1.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import Database.DBHelper;

public class FinanceFragment extends Fragment {

    private TextView tvTotalRevenue, tvTotalOrders, tvTopSellingCategory;
    private Button btnFilterToday, btnFilterYear, btnFilterMonth;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_doanhthu_adm, container, false);

        // Ánh xạ các TextView
        tvTotalRevenue = view.findViewById(R.id.tvTotalRevenue);
        tvTotalOrders = view.findViewById(R.id.tvTotalOrders);
        tvTopSellingCategory = view.findViewById(R.id.tvTopSellingCategory);

        // Ánh xạ các Button
        btnFilterToday = view.findViewById(R.id.btnFilterToday);
        btnFilterYear = view.findViewById(R.id.btnFilterYear);
        btnFilterMonth = view.findViewById(R.id.btnFilterMonth);

        // Lấy dữ liệu từ cơ sở dữ liệu
        loadFinanceData();

        // Xử lý sự kiện lọc
        btnFilterToday.setOnClickListener(v -> filterRevenueByDate());
        btnFilterMonth.setOnClickListener(v -> filterRevenueByMonth());
        btnFilterYear.setOnClickListener(v -> filterRevenueByYear());

        return view;
    }

    private void loadFinanceData() {
        DBHelper dbHelper = new DBHelper(getContext());

        // Lấy tổng doanh thu
        double totalRevenue = dbHelper.getTotalRevenue();
        tvTotalRevenue.setText("Tổng doanh thu: " + String.format("%,.0f VNĐ", totalRevenue));

        // Lấy tổng số đơn hàng
        int totalOrders = dbHelper.getTotalOrders();
        tvTotalOrders.setText("Tổng đơn hàng: " + totalOrders);

        // Hiển thị thông tin món bán chạy nhất (có thể thêm logic tùy yêu cầu)
        String topSellingCategory = getTopSellingCategory(dbHelper);
        tvTopSellingCategory.setText("Món bán chạy: " + topSellingCategory);
    }

    private void filterRevenueByDate() {
        // Lấy ngày hiện tại theo định dạng yyyy-MM-dd
        String currentDate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());

        // Lấy doanh thu theo ngày từ DBHelper
        DBHelper dbHelper = new DBHelper(getContext());
        double totalRevenueToday = dbHelper.getTotalRevenueByDate(currentDate);

        // Hiển thị doanh thu
        tvTotalRevenue.setText("Tổng doanh thu hôm nay: " + String.format("%,.0f VNĐ", totalRevenueToday));
    }

    private void filterRevenueByMonth() {
        // Lấy tháng hiện tại theo định dạng yyyy-MM
        String currentMonth = new SimpleDateFormat("yyyy-MM", Locale.getDefault()).format(new Date());

        // Lấy doanh thu theo tháng từ DBHelper
        DBHelper dbHelper = new DBHelper(getContext());
        double totalRevenueMonth = dbHelper.getTotalRevenueByMonth(currentMonth);

        // Hiển thị doanh thu
        tvTotalRevenue.setText("Tổng doanh thu tháng này: " + String.format("%,.0f VNĐ", totalRevenueMonth));
    }

    private void filterRevenueByYear() {
        // Lấy năm hiện tại theo định dạng yyyy
        String currentYear = new SimpleDateFormat("yyyy", Locale.getDefault()).format(new Date());

        // Lấy doanh thu theo năm từ DBHelper
        DBHelper dbHelper = new DBHelper(getContext());
        double totalRevenueYear = dbHelper.getTotalRevenueByYear(currentYear);

        // Hiển thị doanh thu
        tvTotalRevenue.setText("Tổng doanh thu năm nay: " + String.format("%,.0f VNĐ", totalRevenueYear));
    }

    private String getTopSellingCategory(DBHelper dbHelper) {
        // Logic tính toán món bán chạy nhất
        return "Chưa có dữ liệu";
    }
}
