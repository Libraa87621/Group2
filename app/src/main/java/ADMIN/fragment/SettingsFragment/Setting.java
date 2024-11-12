package ADMIN.fragment.SettingsFragment;

public class Setting {
    private String date;
    private String customerName;
    private String items;
    private boolean isCompleted;
    private boolean selected;

    public Setting(String date, String customerName, String items, boolean isCompleted) {
        this.date = date;
        this.customerName = customerName;
        this.items = items;
        this.isCompleted = isCompleted;
        this.selected = false;
    }

    public String getDate() { return date; }
    public void setDate(String date) { this.date = date; }  // Thêm setter cho date
    public String getCustomerName() { return customerName; }
    public void setCustomerName(String customerName) { this.customerName = customerName; }
    public String getItems() { return items; }
    public void setItems(String items) { this.items = items; }  // Thêm setter cho items
    public boolean isCompleted() { return isCompleted; }
    public boolean isSelected() { return selected; }
    public void setSelected(boolean selected) { this.selected = selected; }
}