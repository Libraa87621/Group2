package USER.choosefood;

import android.os.Parcel;
import android.os.Parcelable;

public class Combo implements Parcelable {
    private String nameChicken;        // Tên món Gà
    private String namePotato;         // Tên món Khoai tây
    private String priceChicken;       // Giá món Gà
    private String pricePotato;        // Giá món Khoai tây
    private int total;                 // Tổng tiền (change to int for numeric representation)
    private int quantity;              // Số lượng combo
    private int imageResId;            // Hình ảnh của combo

    // Constructor
    public Combo(String nameChicken, String priceChicken, String namePotato, String pricePotato, int total, int quantity, int imageResId) {
        this.nameChicken = nameChicken;
        this.priceChicken = priceChicken;
        this.namePotato = namePotato;
        this.pricePotato = pricePotato;
        this.total = total;  // Store total as an integer
        this.quantity = quantity;
        this.imageResId = imageResId;
    }

    // Parcelable methods (read, write, and describe contents)
    protected Combo(Parcel in) {
        nameChicken = in.readString();
        priceChicken = in.readString();
        namePotato = in.readString();
        pricePotato = in.readString();
        total = in.readInt();  // Read total as an integer
        quantity = in.readInt();
        imageResId = in.readInt();
    }

    public static final Creator<Combo> CREATOR = new Creator<Combo>() {
        @Override
        public Combo createFromParcel(Parcel in) {
            return new Combo(in);
        }

        @Override
        public Combo[] newArray(int size) {
            return new Combo[size];
        }
    };

    // Getters
    public String getNameChicken() {
        return nameChicken;
    }

    public String getPriceChicken() {
        return priceChicken;
    }

    public String getNamePotato() {
        return namePotato;
    }

    public String getPricePotato() {
        return pricePotato;
    }

    public int getTotal() {
        return total;  // Return total as an integer
    }

    public int getQuantity() {
        return quantity;
    }

    public int getImageResId() {
        return imageResId;
    }

    // Setters for quantity
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    // Parcelable methods to write Combo object into parcel
    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(nameChicken);
        parcel.writeString(priceChicken);
        parcel.writeString(namePotato);
        parcel.writeString(pricePotato);
        parcel.writeInt(total);  // Write total as an integer
        parcel.writeInt(quantity);
        parcel.writeInt(imageResId);
    }

    @Override
    public int describeContents() {
        return 0;
    }
}
