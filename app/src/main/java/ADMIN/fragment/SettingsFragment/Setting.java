package ADMIN.fragment.SettingsFragment;

public class Setting {
    private String date;
    private String customerName;
    private String items;
    private boolean isCompleted;
    private boolean selected;
    private String name;
    private String email;
    private String phone;

    public Setting(String name, String email, String phone) {
        this.name = name;
        this.email = email;
        this.phone = phone;
    }
    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }
    public String getDate() { return date; }
    public void setDate(String date) { this.date = date; }
    public String getCustomerName() { return customerName; }
    public void setCustomerName(String customerName) { this.customerName = customerName; }
    public String getItems() { return items; }
    public void setItems(String items) { this.items = items; }
    public boolean isCompleted() { return isCompleted; }
    public void setCompleted(boolean completed) { isCompleted = completed; }
    public boolean isSelected() { return selected; }
    public void setSelected(boolean selected) { this.selected = selected; }
}
