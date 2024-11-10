package USER.choosefood;

import android.os.Parcel;
import android.os.Parcelable;

public class Combo implements Parcelable {
    private String name;
    private String details;
    private String priceDetails;  // Assuming priceDetails contains a price in string format like "$10"
    private int imageResId;

    // Constructor
    public Combo(String name, String details, String priceDetails, int imageResId) {
        this.name = name;
        this.details = details;
        this.priceDetails = priceDetails;
        this.imageResId = imageResId;
    }

    // Getter methods
    public String getName() {
        return name;
    }

    public String getDetails() {
        return details;
    }

    public String getPriceDetails() {
        return priceDetails;
    }

    public int getImageResId() {
        return imageResId;
    }

    // Method to get the total amount (for example, parsing priceDetails)
    public double getTotalAmount() {
        try {
            // Assuming priceDetails is in format "$10"
            String priceString = priceDetails.replace("$", "");
            return Double.parseDouble(priceString);
        } catch (NumberFormatException e) {
            return 0.0;  // Return 0 if there is an issue with the price string
        }
    }

    // Parcelable implementation
    protected Combo(Parcel in) {
        name = in.readString();
        details = in.readString();
        priceDetails = in.readString();
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(details);
        dest.writeString(priceDetails);
        dest.writeInt(imageResId);
    }
}
