package ADMIN.fragment.FinanceFragment;

import android.content.Context;

import Database.DBHelper;

public class RevenueManager {

    private DBHelper dbHelper;

    public RevenueManager(Context context) {
        dbHelper = new DBHelper(context);
    }

    // Cập nhật doanh thu và số lượng đơn hàng khi có đơn hàng mới
    public void updateRevenueAndOrders(double price, int quantity) {
        // Tính doanh thu cho đơn hàng mới
        double totalOrderRevenue = price * quantity;

        // Cập nhật tổng doanh thu
        updateTotalRevenue(totalOrderRevenue);

        // Cập nhật tổng số đơn hàng
        updateTotalOrders();
    }

    // Cập nhật tổng doanh thu
    private void updateTotalRevenue(double additionalRevenue) {
        // Lấy tổng doanh thu hiện tại từ cơ sở dữ liệu
        double currentTotalRevenue = dbHelper.getTotalRevenue();

        // Cập nhật lại tổng doanh thu
        double newTotalRevenue = currentTotalRevenue + additionalRevenue;

        // Lưu lại tổng doanh thu mới vào cơ sở dữ liệu (nếu cần thiết)
        dbHelper.updateRevenue(newTotalRevenue);  // Cần tạo phương thức updateRevenue trong DBHelper để thực hiện việc này
    }

    // Cập nhật tổng số đơn hàng
    private void updateTotalOrders() {
        // Lấy tổng số đơn hàng hiện tại từ cơ sở dữ liệu
        int currentTotalOrders = dbHelper.getTotalOrders();

        // Cập nhật lại tổng số đơn hàng
        int newTotalOrders = currentTotalOrders + 1;

        // Lưu lại số đơn hàng mới vào cơ sở dữ liệu (nếu cần thiết)
        dbHelper.updateOrders(newTotalOrders);  // Cần tạo phương thức updateOrders trong DBHelper để thực hiện việc này
    }
}
