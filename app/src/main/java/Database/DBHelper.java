package Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.Date; // Add this line
import ADMIN.fragment.FinanceFragment.RevenueManager;

public class DBHelper extends SQLiteOpenHelper {

    public static final String COLUMN_PHONE = "phone";
    private static final String DATABASE_NAME = "app_database";
    private static final int DATABASE_VERSION = 2;

    private static final String TABLE_USERS = "users";
    private static final String TABLE_ORDERS = "orders";

    private static final String COLUMN_USER_ID = "id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_EMAIL = "email";
    private static final String COLUMN_ADDRESS = "address";

    private static final String COLUMN_ORDER_ID = "order_id";
    private static final String COLUMN_PAYMENT_DATE = "payment_date";
    private static final String COLUMN_DATE = "currentDate";
    private static final String COLUMN_ORDER_ADDRESS = "delivery_address";
    private static final String COLUMN_IMAGE_URL = "image_url";
    private static final String COLUMN_COMPONENTS = "components";
    private static final String COLUMN_PRICE = "price";
    private static final String COLUMN_QUANTITY = "quantity";

    private static final String CREATE_TABLE_USERS = "CREATE TABLE " + TABLE_USERS + " (" +
            COLUMN_USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COLUMN_NAME + " TEXT, " +
            COLUMN_EMAIL + " TEXT, " +
            COLUMN_ADDRESS + " TEXT, " +
            COLUMN_PHONE + " TEXT);";

    private static final String CREATE_TABLE_ORDERS = "CREATE TABLE " + TABLE_ORDERS + " (" +
            COLUMN_ORDER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COLUMN_PAYMENT_DATE + " TEXT, " +
            COLUMN_DATE + " TEXT, " +
            COLUMN_ORDER_ADDRESS + " TEXT, " +
            COLUMN_IMAGE_URL + " TEXT, " +
            COLUMN_COMPONENTS + " TEXT, " +
            COLUMN_PRICE + " REAL, " +
            COLUMN_QUANTITY + " INTEGER);";

    // Tạo bảng products
    private static final String CREATE_TABLE_PRODUCTS = "CREATE TABLE products (" +
            "product_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "name TEXT NOT NULL, " +
            "description TEXT, " +
            "price REAL NOT NULL, " +
            "quantity INTEGER NOT NULL, " +
            "image_url TEXT);";


    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_USERS);
        db.execSQL(CREATE_TABLE_ORDERS);
        db.execSQL(CREATE_TABLE_PRODUCTS);
        String createTableSQL = "CREATE TABLE IF NOT EXISTS transactions (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "amount REAL, " +
                "transaction_date TEXT);";
        db.execSQL(createTableSQL);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion < 2) {
            // SQL để tạo bảng transactions nếu chưa có
            String createTableSQL = "CREATE TABLE IF NOT EXISTS transactions (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "amount REAL, " +
                    "transaction_date TEXT);";
            db.execSQL(createTableSQL);
        }
    }

    public long addUser(String name, String email, String address, String phone) {
        SQLiteDatabase db = this.getWritableDatabase();
        try {
            ContentValues values = new ContentValues();
            values.put(COLUMN_NAME, name);
            values.put(COLUMN_EMAIL, email);
            values.put(COLUMN_ADDRESS, address);
            values.put(COLUMN_PHONE, phone);
            return db.insert(TABLE_USERS, null, values);
        } finally {
            db.close();
        }
    }

    public long addOrder(String paymentDate, String date, String address, String imageUrl,
                         String components, double price, int quantity) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.beginTransaction();
        try {
            ContentValues values = new ContentValues();
            values.put(COLUMN_PAYMENT_DATE, paymentDate);
            values.put(COLUMN_DATE, date);
            values.put(COLUMN_ORDER_ADDRESS, address);
            values.put(COLUMN_IMAGE_URL, imageUrl);
            values.put(COLUMN_COMPONENTS, components);
            values.put(COLUMN_PRICE, price);
            values.put(COLUMN_QUANTITY, quantity);

            long orderId = db.insert(TABLE_ORDERS, null, values);
            db.setTransactionSuccessful();
            return orderId;
        } finally {
            db.endTransaction();
            db.close();
        }
    }


    public Cursor getAllUsers() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT " + COLUMN_NAME + ", " + COLUMN_EMAIL + ", " + COLUMN_PHONE + " FROM " + TABLE_USERS, null);
    }


    // class doanh thu
    public double getTotalRevenue() {
        double totalRevenue = 0;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        try {
            String query = "SELECT SUM(" + COLUMN_PRICE + ") AS TotalRevenue FROM " + TABLE_ORDERS;
            cursor = db.rawQuery(query, null);
            if (cursor.moveToFirst()) {
                totalRevenue = cursor.getDouble(0);
            }
        } finally {
            if (cursor != null) cursor.close();
            db.close();
        }
        return totalRevenue;
    }

    // Tính tổng doanh thu theo ngày
    public double getTotalRevenueByDate(String date) {
        double totalRevenue = 0;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        try {
            String query = "SELECT SUM(" + COLUMN_PRICE + ") AS TotalRevenue " +
                    "FROM " + TABLE_ORDERS + " WHERE DATE(" + COLUMN_PAYMENT_DATE + ") = ?";
            cursor = db.rawQuery(query, new String[]{date});
            if (cursor.moveToFirst()) {
                totalRevenue = cursor.getDouble(0);
            }
        } finally {
            if (cursor != null) cursor.close();
            db.close();
        }
        return totalRevenue;
    }
    // Phương thức lấy doanh thu theo tháng (tháng được định dạng là yyyy-MM)
    public double getTotalRevenueByMonth(String month) {
        double totalRevenue = 0;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        try {
            String query = "SELECT SUM(" + COLUMN_PRICE + ") AS TotalRevenue " +
                    "FROM " + TABLE_ORDERS + " WHERE strftime('%Y-%m', " + COLUMN_PAYMENT_DATE + ") = ?";
            cursor = db.rawQuery(query, new String[]{month});
            if (cursor.moveToFirst()) {
                totalRevenue = cursor.getDouble(0);
            }
        } finally {
            if (cursor != null) cursor.close();
            db.close();
        }
        return totalRevenue;
    }

    // Phương thức lấy doanh thu theo năm (năm được định dạng là yyyy)
    public double getTotalRevenueByYear(String year) {
        double totalRevenue = 0;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        try {
            String query = "SELECT SUM(" + COLUMN_PRICE + ") AS TotalRevenue " +
                    "FROM " + TABLE_ORDERS + " WHERE strftime('%Y', " + COLUMN_PAYMENT_DATE + ") = ?";
            cursor = db.rawQuery(query, new String[]{year});
            if (cursor.moveToFirst()) {
                totalRevenue = cursor.getDouble(0);
            }
        } finally {
            if (cursor != null) cursor.close();
            db.close();
        }
        return totalRevenue;
    }




    // Cập nhật tổng doanh thu mới
    public void updateRevenue(double newTotalRevenue) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("total_revenue", newTotalRevenue);  // Thay thế "total_revenue" bằng tên cột phù hợp trong bảng của bạn

        // Giả sử có một bảng thống kê doanh thu hoặc bảng tổng hợp, bạn có thể thực hiện như sau:
        String whereClause = "id = ?";
        String[] whereArgs = {"1"};  // Giả sử có một dòng duy nhất để lưu tổng doanh thu

        db.update("revenue_summary", contentValues, whereClause, whereArgs);
        db.close();
    }

    // Cập nhật tổng số đơn hàng
    public void updateOrders(int newTotalOrders) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("total_orders", newTotalOrders);  // Thay thế "total_orders" bằng tên cột phù hợp trong bảng của bạn

        // Giả sử có một bảng tổng hợp số đơn hàng
        String whereClause = "id = ?";
        String[] whereArgs = {"1"};  // Giả sử có một dòng duy nhất để lưu tổng số đơn hàng

        db.update("orders_summary", contentValues, whereClause, whereArgs);
        db.close();
    }




    public int getTotalOrders() {
        int totalOrders = 0;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        try {
            String query = "SELECT COUNT(*) FROM " + TABLE_ORDERS;
            cursor = db.rawQuery(query, null);
            if (cursor.moveToFirst()) {
                totalOrders = cursor.getInt(0);
            }
        } finally {
            if (cursor != null) cursor.close();
            db.close();
        }
        return totalOrders;
    }

    public boolean isTableEmpty(String tableName) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        try {
            String query = "SELECT COUNT(*) FROM " + tableName;
            cursor = db.rawQuery(query, null);
            if (cursor.moveToFirst()) {
                return cursor.getInt(0) == 0;
            }
        } finally {
            if (cursor != null) cursor.close();
            db.close();
        }
        return true;
    }

    // Updated onOrderSuccess method to pass necessary parameters
    public void onOrderSuccess(Context context, String paymentDate, String date, String address,
                               String imageUrl, String components, double price, int quantity) {
        // Update revenue and total orders
        RevenueManager revenueManager = new RevenueManager(context);
        revenueManager.updateRevenueAndOrders(price, quantity);

        // Add the order to the database
        DBHelper dbHelper = new DBHelper(context);
        dbHelper.addOrder(paymentDate, date, address, imageUrl, components, price, quantity);
    }
    // Phương thức thêm sản phẩm vào bảng
    public void insertProduct(SQLiteDatabase db, String name, String description, double price, int quantity, String imageUrl) {
        ContentValues values = new ContentValues();
        values.put("name", name);
        values.put("description", description);
        values.put("price", price);
        values.put("quantity", quantity);
        values.put("image_url", imageUrl);

        db.insert("products", null, values);
    }

    private void insertDefaultProducts(SQLiteDatabase db) {
        if (isTableEmpty("products")) {
            insertProduct(db, "COMBO VUI VẺ", "2 Miếng gà giòn, 1 nước, 1 khoai chiên", 160000, 10, "drawable/combovuive.png");
            insertProduct(db, "COMBO NO NÊ", "1 phần cơm gà, 1 nước, 1 súp cà rốt", 130000, 15, "drawable/combonone.png");
            insertProduct(db, "COMBO SOLO", "1 hamburger tôm, 1 nước, 1 khoai chiên", 120000, 20, "drawable/combosolo.png");
            insertProduct(db, "BÁNH NHÂN XOÀI", "1 Bánh nhân xoài thơm vị ngọt", 50000, 50, "drawable/banhnhanxoai.png");
            insertProduct(db, "NƯỚC ÉP XOÀI", "1 Cốc nước ép xoài", 30000, 25, "drawable/nuocepxoai.png");
        }
    }


    // Hàm thêm sản phẩm từ ứng dụng (có thể sử dụng sau)
    public void addProduct(String name, String description, double price, int quantity, String imageUrl) {
        SQLiteDatabase db = this.getWritableDatabase();
        insertProduct(db, name, description, price, quantity, imageUrl);
        db.close();
    }
}