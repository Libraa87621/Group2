package ADMIN.fragment.SettingsFragment;

public class Setting {
    private String name;          // Từ bảng users
    private String date;          // Từ bảng orders
    private String components;    // Từ bảng orders
    private boolean isSelected;   // Trạng thái chọn
    private String orderStatus;   // Trạng thái đơn hàng

    // Constructor
    public Setting(String name, String date, String components) {
        this.name = name;
        this.date = date;
        this.components = components;
        this.isSelected = false; // Mặc định là không chọn
        this.orderStatus = "Chưa xử lý"; // Trạng thái mặc định là chưa xử lý
    }

    // Getter và Setter cho tất cả các thuộc tính
    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getName() {
        return name;
    }

    public String getDate() {
        return date;
    }

    public String getComponents() {
        return components;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setComponents(String components) {
        this.components = components;
    }

    // Setter và logic cập nhật trạng thái đơn hàng khi CheckBox thay đổi
    public void setSelected(boolean selected) {
        this.isSelected = selected;
        // Cập nhật trạng thái đơn hàng dựa trên trạng thái của CheckBox
        if (selected) {
            this.orderStatus = "Đã xử lý";  // Khi chọn, trạng thái đơn hàng là "Đã xử lý"
        } else {
            this.orderStatus = "Chưa xử lý"; // Khi bỏ chọn, trạng thái đơn hàng là "Chưa xử lý"
        }
    }
}
