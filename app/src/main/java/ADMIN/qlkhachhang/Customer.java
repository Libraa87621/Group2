package ADMIN.qlkhachhang;

public class Customer {
    private String date;
    private String customerName;
    private String items;
    private int imageResource1;
    private int imageResource2;

    public Customer(String date, String customerName, String items, int imageResource1, int imageResource2) {
        this.date = date;
        this.customerName = customerName;
        this.items = items;
        this.imageResource1 = imageResource1;
        this.imageResource2 = imageResource2;
    }

    public String getDate() {
        return date;
    }

    public String getCustomerName() {
        return customerName;
    }

    public String getItems() {
        return items;
    }

    public int getImageResource1() {
        return imageResource1;
    }

    public int getImageResource2() {
        return imageResource2;
    }
}
