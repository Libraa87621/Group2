package ADMIN.fragment.SettingsFragment;

public class Setting {
    private String name;
    private String email;
    private String phone;
    private String date;
    private boolean isSelected;

    public Setting(String name, String email, String phone) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.date = "10/5/2024"; // Giá trị mặc định
        this.isSelected = false; // Giá trị mặc định
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }
}
