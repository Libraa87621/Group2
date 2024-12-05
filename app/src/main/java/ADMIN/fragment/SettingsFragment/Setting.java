package ADMIN.fragment.SettingsFragment;

public class Setting {
    private int id;               // Thêm id để xác định đơn hàng
    private String name;          // Từ bảng users
    private String date;          // Từ bảng orders
    private String components;    // Từ bảng orders
    private boolean isSelected;   // Trạng thái chọn
    private String orderStatus;   // Trạng thái đơn hàng
    private boolean selected;

    // Constructor
    public Setting(String name, String date, String components) {
        this.id = id;           // Khởi tạo id
        this.name = name;
        this.date = date;
        this.components = components;
        this.isSelected = false;
    }

    // Getter và Setter cho tất cả các thuộc tính
    public int getId() {
        return id;
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

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public void setOrderStatus(String status) {
        this.orderStatus = status;
    }

    public String getOrderStatus() {
        return orderStatus != null ? orderStatus : "Chưa xác nhận";
    }
}
