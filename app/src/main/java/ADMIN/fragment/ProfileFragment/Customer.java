package ADMIN.fragment.ProfileFragment;

public class Customer {
    private String date;
    private String customerName;
    private String items;
    private int imageResource1;
    private int imageResource2;
    private boolean isSelected;

    public Customer(String date, String customerName, String items, int imageResource1, int imageResource2) {
        this.date = date;
        this.customerName = customerName;
        this.items = items;
        this.imageResource1 = imageResource1;
        this.imageResource2 = imageResource2;
        this.isSelected = false;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getItems() {
        return items;
    }

    public void setItems(String items) {
        this.items = items;
    }

    public int getImageResource1() {
        return imageResource1;
    }

    public int getImageResource2() {
        return imageResource2;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }
}
