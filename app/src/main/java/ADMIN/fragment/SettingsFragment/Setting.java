package ADMIN.fragment.SettingsFragment;

public class Setting {
    private String name; // Từ bảng users
    private String date; // Từ bảng orders
    private String components; // Từ bảng orders
    private boolean isSelected; // Trạng thái chọn

    // Constructor
    public Setting(String name, String date, String components) {
        this.name = name;
        this.date = date;
        this.components = components;
        this.isSelected = false; // Mặc định là không chọn
    }

    // Getter cho tên
    public String getName() {
        return name;
    }

    // Getter cho ngày
    public String getDate() {
        return date;
    }

    // Getter cho thành phần
    public String getComponents() {
        return components;
    }

    // Getter cho trạng thái chọn
    public boolean isSelected() {
        return isSelected;
    }

    // Setter cho trạng thái chọn
    public void setSelected(boolean selected) {
        isSelected = selected;
    }
}
