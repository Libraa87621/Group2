package ADMIN.fragment.SettingsFragment;

public class Setting {
    private int order_id;  // Sử dụng order_id thay vì orderId
    private String name;
    private String date;
    private String components;
    private boolean isSelected;   // Dùng thuộc tính này để lưu trạng thái đã chọn
    private String status;

    // Constructor
    public Setting(int order_id, String name, String date, String components, String status) {
        this.order_id = order_id;  // Gán giá trị order_id từ cơ sở dữ liệu
        this.name = name;
        this.date = date;
        this.components = components;
        this.isSelected = false; // Mặc định là không chọn
        this.status = status;
    }

    // Getter và Setter
    public int getOrderId() {
        return order_id;  // Trả về order_id
    }

    public void setOrderId(int order_id) {
        this.order_id = order_id;  // Cập nhật giá trị order_id
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

    public String getStatus() {
        return status != null ? status : "";  // Trả về status hoặc "" nếu status là null
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
