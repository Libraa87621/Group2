package ADMIN.fragment.ProfileFragment;

public class Customer {
    private String name;
    private String phone;
    private String birthdate;
    private String address;
    private String email;
    private boolean selected;  // Chỉ cần một trường cho trạng thái chọn

    public Customer(String name, String phone, String birthdate, String address, String email) {
        this.name = name;
        this.phone = phone;
        this.birthdate = birthdate;
        this.address = address;
        this.email = email;
        this.selected = false;  // Mặc định là không chọn
    }

    // Getters và Setters
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(String birthdate) {
        this.birthdate = birthdate;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }
}
