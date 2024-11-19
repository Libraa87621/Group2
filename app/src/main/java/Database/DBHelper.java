package Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

    // Tên cơ sở dữ liệu và phiên bản
    private static final String DATABASE_NAME = "app_database"; // Tên database
    private static final int DATABASE_VERSION = 1; // Phiên bản database


    // Tên các bảng
    private static final String TABLE_USERS = "users"; // Bảng người dùng
    private static final String TABLE_ORDERS = "orders"; // Bảng đơn hàng

    // Các cột trong bảng users
    private static final String COLUMN_USER_ID = "id"; // ID người dùng
    public static final String COLUMN_NAME = "name"; // Đổi từ private thành public
    public static final String COLUMN_EMAIL = "email"; // Đổi từ private thành public

    private static final String COLUMN_ADDRESS = "address"; // Địa chỉ
    private static final String COLUMN_PHONE = "phone"; // Số điện thoại (lặp lại - có thể tối ưu)

    // Các cột trong bảng orders
    private static final String COLUMN_ORDER_ID = "order_id"; // ID đơn hàng
    private static final String COLUMN_DATE = "date"; // Ngày đặt hàng
    private static final String COLUMN_ORDER_ADDRESS = "address"; // Địa chỉ giao hàng
    private static final String COLUMN_IMAGE_URL = "image_url"; // URL hình ảnh
    private static final String COLUMN_COMPONENTS = "components"; // Các thành phần của đơn hàng
    private static final String COLUMN_PRICE = "price"; // Giá đơn hàng
    private static final String COLUMN_QUANTITY = "quantity"; // Số lượng

    // Lệnh SQL tạo bảng users
    private static final String CREATE_TABLE_USERS = "CREATE TABLE " + TABLE_USERS + " (" +
            COLUMN_USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COLUMN_NAME + " TEXT, " +
            COLUMN_EMAIL + " TEXT, " +
            COLUMN_ADDRESS + " TEXT, " +
            COLUMN_PHONE + " TEXT);";

    // Lệnh SQL tạo bảng orders
    private static final String CREATE_TABLE_ORDERS = "CREATE TABLE " + TABLE_ORDERS + " (" +
            COLUMN_ORDER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COLUMN_DATE + " TEXT, " +
            COLUMN_ORDER_ADDRESS + " TEXT, " +
            COLUMN_IMAGE_URL + " TEXT, " +
            COLUMN_COMPONENTS + " TEXT, " +
            COLUMN_PRICE + " REAL, " +
            COLUMN_QUANTITY + " INTEGER);";

    // Constructor
    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Tạo bảng khi cơ sở dữ liệu được tạo
        db.execSQL(CREATE_TABLE_USERS); // Tạo bảng users
        db.execSQL(CREATE_TABLE_ORDERS); // Tạo bảng orders
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Nâng cấp cơ sở dữ liệu nếu thay đổi phiên bản
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS); // Xóa bảng users nếu đã tồn tại
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ORDERS); // Xóa bảng orders nếu đã tồn tại
        onCreate(db); // Tạo lại bảng
    }




    // Thêm dữ liệu vào bảng users
    public long addUser(String name, String email, String address, String phone) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, name);
        values.put(COLUMN_EMAIL, email);
        values.put(COLUMN_ADDRESS, address);
        values.put(COLUMN_PHONE, phone);

        return db.insert(TABLE_USERS, null, values); // Trả về ID của bản ghi vừa thêm
    }

    // Thêm dữ liệu vào bảng orders
    public long addOrder(String date, String address, String imageUrl, String components, double price, int quantity) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_DATE, date);
        values.put(COLUMN_ORDER_ADDRESS, address);
        values.put(COLUMN_IMAGE_URL, imageUrl);
        values.put(COLUMN_COMPONENTS, components);
        values.put(COLUMN_PRICE, price);
        values.put(COLUMN_QUANTITY, quantity);

        return db.insert(TABLE_ORDERS, null, values); // Trả về ID của bản ghi vừa thêm
    }




    // Lấy tất cả người dùng từ bảng users
    public Cursor getAllUsers() {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_USERS;
        return db.rawQuery(query, null);
    }

    // Lấy tất cả đơn hàng từ bảng orders
    public Cursor getAllOrders() {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_ORDERS;
        return db.rawQuery(query, null);
    }

}
