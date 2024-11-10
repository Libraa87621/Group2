package ADMIN.fragment.SettingsFragment;

public class Order {
    private String date;
    private String customerName;
    private String items;
    private boolean isCompleted;

    public Order(String date, String customerName, String items, boolean isCompleted) {
        this.date = date;
        this.customerName = customerName;
        this.items = items;
        this.isCompleted = isCompleted;
    }

    public String getDate() { return date; }
    public String getCustomerName() { return customerName; }
    public String getItems() { return items; }
    public boolean isCompleted() { return isCompleted; }
}
