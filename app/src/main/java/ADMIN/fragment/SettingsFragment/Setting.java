package ADMIN.fragment.SettingsFragment;

public class Setting {
    private int order_id;
    private String name;
    private String date;
    private String components;
    private boolean isSelected;   // Dùng thuộc tính này để lưu trạng thái đã chọn
    private String status;
    private int orderId;

    // Constructor
    public Setting(String name, String date, String components, String status) {
        this.order_id = order_id; // Lấy từ cơ sở dữ liệu
        this.name = name;
        this.date = date;
        this.components = components;
        this.isSelected = false; // Mặc định là không chọn
        this.status = status;
    }

    // Getter và Setter
    public int getorder_id() {
        return order_id;
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

    public void setSelected(boolean isSelected) {
        this.isSelected = isSelected;
    }

    public String getstatus() {
        return status != null ? status : "";
    }

    public void setstatus(String status) {
        this.status = status;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }
}
