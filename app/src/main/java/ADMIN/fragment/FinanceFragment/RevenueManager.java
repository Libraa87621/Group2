package ADMIN.fragment.FinanceFragment;

import android.content.Context;

import Database.DBHelper;

public class RevenueManager {

    private DBHelper dbHelper;

    public RevenueManager(Context context) {
        dbHelper = new DBHelper(context);
    }

    public void updateRevenueAndOrders(double price, int quantity) {
        // Tính doanh thu cho đơn hàng mới
        double totalOrderRevenue = price * quantity;

        // Cập nhật tổng doanh thu
        updateTotalRevenue(totalOrderRevenue);

        // Cập nhật tổng số đơn hàng
        updateTotalOrders();
    }

    private void updateTotalRevenue(double additionalRevenue) {
        // Lấy tổng doanh thu hiện tại từ cơ sở dữ liệu
        double currentTotalRevenue = dbHelper.getTotalRevenue();

        // Cập nhật lại tổng doanh thu
        double newTotalRevenue = currentTotalRevenue + additionalRevenue;

        // Lưu lại tổng doanh thu mới vào cơ sở dữ liệu (nếu cần thiết)
        // Ví dụ: bạn có thể lưu vào bảng thống kê doanh thu hoặc cập nhật trực tiếp vào bảng `orders`
        // Cách thực hiện phụ thuộc vào cấu trúc cơ sở dữ liệu của bạn
    }

    private void updateTotalOrders() {

        int currentTotalOrders = dbHelper.getTotalOrders();

        // Cập nhật lại tổng số đơn hàng
        int newTotalOrders = currentTotalOrders + 1;


    }

}