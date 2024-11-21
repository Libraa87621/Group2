package Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import ADMIN.fragment.FinanceFragment.RevenueManager;

public class DBHelper extends SQLiteOpenHelper {

    public static final String COLUMN_PHONE = "phone";
    private static final String DATABASE_NAME = "app_database";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_USERS = "users";
    private static final String TABLE_ORDERS = "orders";

    private static final String COLUMN_USER_ID = "id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_EMAIL = "email";
    private static final String COLUMN_ADDRESS = "address";

    private static final String COLUMN_ORDER_ID = "order_id";
    private static final String COLUMN_PAYMENT_DATE = "payment_date";
    private static final String COLUMN_DATE = "order_date";
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
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ORDERS);
        db.execSQL("DROP TABLE IF EXISTS products");
        onCreate(db);
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
        try {
            ContentValues values = new ContentValues();
            values.put(COLUMN_PAYMENT_DATE, paymentDate);
            values.put(COLUMN_DATE, date);
            values.put(COLUMN_ORDER_ADDRESS, address);
            values.put(COLUMN_IMAGE_URL, imageUrl);
            values.put(COLUMN_COMPONENTS, components);
            values.put(COLUMN_PRICE, price);
            values.put(COLUMN_QUANTITY, quantity);
            return db.insert(TABLE_ORDERS, null, values);
        } finally {
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

    // Thêm các sản phẩm mẫu
    private void insertDefaultProducts(SQLiteDatabase db) {
        insertProduct(db, "COMBO VUI VẺ", "2 Miếng gà giòn, 1 nước, 1 khoai chiên", 160000, 10, "drawable/combovuive.png");
        insertProduct(db, "COMBO NO NÊ", "1 phần cơm gà, 1 nước, 1 súp cà rốt", 130000, 15, "drawable/combonone.png");
        insertProduct(db, "COMBO SOLO", "1 hamburger tôm, 1 nước, 1 khoai chiên", 120000, 20, "drawable/combosolo.png");
        insertProduct(db, "BÁNH NHÂN XOÀI", "1 Bánh nhân xoài thơm vị ngọt", 50000, 50, "drawable/banhnhanxoai.png");
        insertProduct(db, "NƯỚC ÉP XOÀI", "1 Cốc nước ép xoài", 30000, 25, "drawable/nuocepxoai.png");
    }

    // Hàm thêm sản phẩm từ ứng dụng (có thể sử dụng sau)
    public void addProduct(String name, String description, double price, int quantity, String imageUrl) {
        SQLiteDatabase db = this.getWritableDatabase();
        insertProduct(db, name, description, price, quantity, imageUrl);
        db.close();
    }
}