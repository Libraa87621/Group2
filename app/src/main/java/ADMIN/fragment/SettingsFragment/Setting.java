package ADMIN.fragment.SettingsFragment;

public class Setting {
    private int id;
    private String name;
    private String date;
    private String components;
    private boolean isSelected;   // Dùng thuộc tính này để lưu trạng thái đã chọn
    private String orderStatus;

    // Constructor
    public Setting(String name, String date, String components) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.components = components;
        this.isSelected = false; // Mặc định là không chọn
        this.orderStatus = "Chưa xác nhận"; // Trạng thái mặc định
    }

    // Getter và Setter
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

    public void setSelected(boolean isSelected) {
        this.isSelected = isSelected;
    }

    public String getOrderStatus() {
        return orderStatus != null ? orderStatus : "Chưa xác nhận";
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }
}
