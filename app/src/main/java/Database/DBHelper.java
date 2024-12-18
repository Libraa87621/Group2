package Database;

import static android.app.DownloadManager.COLUMN_ID;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.duan1.R;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.Date; // Add this line
import ADMIN.fragment.FinanceFragment.RevenueManager;
import ADMIN.fragment.ProfileFragment.Customer;
import ADMIN.fragment.SettingsFragment.Setting;
import USER.login_signin.UserDAO;
import USER.product.Product;


public class DBHelper extends SQLiteOpenHelper {


    private static final String DATABASE_NAME = "app_database";
    private static final int DATABASE_VERSION = 2;

    private static final String TABLE_ORDERS = "orders";

    private static final String COLUMN_USER_ID = "id";

    public static final String COLUMN_NAME = "name";

    public static final String TABLE_USERS = "users";
    public static final String COLUMN_EMAIL = "email";
    public static final String COLUMN_PASSWORD = "password";  // Make sure this matches the table structure

    public static final String COLUMN_PHONE = "phone";
    public static final String COLUMN_ADDRESS = "address";

    public static final String COLUMN_BIRTHDATE = "birthdate";
    private static final String COLUMN_ORDER_ID = "order_id";
    private static final String COLUMN_PAYMENT_DATE = "payment_date";

    public static final String COLUMN_DATE = "currentDate";

    private static final String COLUMN_ORDER_ADDRESS = "delivery_address";
    private static final String COLUMN_IMAGE_URL = "image_url";

    public static final String COLUMN_COMPONENTS = "components";
    private static final String COLUMN_PRICE = "price";
    private static final String COLUMN_QUANTITY = "quantity";
    private static final String COLUMN_ORDER_STATUS = "status";


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_USERS);
        db.execSQL(CREATE_TABLE_CART);
        db.execSQL(CREATE_TABLE_ORDERS);
        db.execSQL(CREATE_TABLE_PRODUCTS);
        String createTableSQL = "CREATE TABLE IF NOT EXISTS transactions (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "amount REAL, " +
                "transaction_date TEXT);";
        db.execSQL(createTableSQL);
    }

    private static final String CREATE_TABLE_CART = "CREATE TABLE IF NOT EXISTS cart (" +
            "cart_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "cartname TEXT, " +
            "cartprice REAL, " +
            "cartimageUrl TEXT, " +
            "cartquantity INTEGER);";

    private static final String CREATE_TABLE_USERS = "CREATE TABLE " + TABLE_USERS + " (" +
            COLUMN_USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COLUMN_NAME + " TEXT, " +
            COLUMN_EMAIL + " TEXT, " +
            COLUMN_BIRTHDATE + " TEXT, " +
            COLUMN_ADDRESS + " TEXT, " +
            COLUMN_PASSWORD + " TEXT, " +
            COLUMN_PHONE + " TEXT);";


    private static final String CREATE_TABLE_ORDERS = "CREATE TABLE " + TABLE_ORDERS + " (" +
            COLUMN_ORDER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COLUMN_PAYMENT_DATE + " TEXT, " +
            COLUMN_DATE + " TEXT, " +
            COLUMN_ORDER_ADDRESS + " TEXT, " +
            COLUMN_IMAGE_URL + " TEXT, " +
            COLUMN_COMPONENTS + " TEXT, " +
            COLUMN_PRICE + " REAL, " +
            COLUMN_ORDER_STATUS + " TEXT, " +
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

    // Add product to cart


    private int getProductImageResourceId(String imageName) {
        // Trả về mã tài nguyên từ tên hình ảnh
        switch (imageName) {
            case "product1":
                return R.drawable.anhsanpham;
            case "product2":
                return R.drawable.default_image;
            // Thêm các trường hợp khác nếu có
            default:
                return R.drawable.default_image; // Nếu không tìm thấy, trả về hình ảnh mặc định
        }
    }


    // Delete cart item by product name
    public void deleteCartItem(String productName) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("cart", "product_name = ?", new String[]{productName});
        db.close();
    }

    public long addUser(String name, String email, String phone, String birthdate, String address) {
        SQLiteDatabase db = this.getWritableDatabase();
        try {
            // Khởi tạo đối tượng ContentValues để chứa các giá trị cần lưu vào cơ sở dữ liệu
            ContentValues values = new ContentValues();
            values.put(COLUMN_NAME, name);           // Lưu tên người dùng
            values.put(COLUMN_EMAIL, email);         // Lưu email người dùng
            values.put(COLUMN_PHONE, phone);         // Lưu số điện thoại người dùng
            values.put(COLUMN_BIRTHDATE, birthdate); // Lưu ngày sinh người dùng
            values.put(COLUMN_ADDRESS, address);     // Lưu địa chỉ giao hàng
            return db.insert(TABLE_USERS, null, values);
        } finally {
            db.close(); // Đóng cơ sở dữ liệu sau khi thực hiện xong
        }
    }


    public long addOrder(String payment_date, String date, String address, String imageUrl,
                         String components, double price, int quantity) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.beginTransaction();
        try {
            ContentValues values = new ContentValues();
            values.put(COLUMN_PAYMENT_DATE, payment_date);
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

    // Cập nhật dữ liệu
    public long updateOrder(int id, String date, String components) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_DATE, date);
        values.put(COLUMN_COMPONENTS, components);
        return db.update(TABLE_ORDERS, values, "id=?", new String[]{String.valueOf(id)});
    }

    // Xóa dữ liệu
    public void deleteOrder(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_ORDERS, "id=?", new String[]{String.valueOf(id)});
    }

    public Cursor getAllOders() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT " + COLUMN_DATE + ", " + COLUMN_COMPONENTS + " FROM " + TABLE_ORDERS, null);
    }

    public Cursor getAllUsers() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT " + COLUMN_NAME + ", " + COLUMN_EMAIL + ", " + COLUMN_PHONE + ", " + COLUMN_ADDRESS + " FROM " + TABLE_USERS, null);
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


    public List<Setting> getAllSettings() {
        List<Setting> settings = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursorUsers = null;
        Cursor cursorOrders = null;

        try {
            // Truy vấn lấy tên từ bảng Users
            String queryUsers = "SELECT " + COLUMN_NAME + " AS name FROM " + TABLE_USERS;
            cursorUsers = db.rawQuery(queryUsers, null);

            // Truy vấn lấy thông tin đơn hàng từ bảng Orders
            String queryOrders = "SELECT " + COLUMN_ORDER_ID + ", " + COLUMN_PAYMENT_DATE + ", " +
                    COLUMN_COMPONENTS + ", " + COLUMN_ORDER_STATUS +
                    " FROM " + TABLE_ORDERS;
            cursorOrders = db.rawQuery(queryOrders, null);

            // Kiểm tra xem con trỏ Users và Orders có dữ liệu hay không
            if (cursorUsers != null && cursorUsers.moveToFirst() && cursorOrders != null && cursorOrders.moveToFirst()) {
                // Di chuyển qua các bản ghi
                do {
                    // Lấy dữ liệu từ bảng Users
                    String name = cursorUsers.getString(cursorUsers.getColumnIndexOrThrow("name"));
                    Log.d("DatabaseStatus", "Name: " + name); // Log giá trị name
                    name = (name != null && !name.isEmpty()) ? name : "name";  // Kiểm tra null và thay thế nếu cần

                    // Lấy dữ liệu từ bảng Orders
                    String date = cursorOrders.getString(cursorOrders.getColumnIndexOrThrow(COLUMN_PAYMENT_DATE));
                    String components = cursorOrders.getString(cursorOrders.getColumnIndexOrThrow(COLUMN_COMPONENTS));
                    String status = cursorOrders.getString(cursorOrders.getColumnIndexOrThrow(COLUMN_ORDER_STATUS));

                    // Lấy orderId và chuyển đổi từ String sang int
                    int orderId = Integer.parseInt(cursorOrders.getString(cursorOrders.getColumnIndexOrThrow(COLUMN_ORDER_ID)));

                    // Kiểm tra giá trị null và thay thế bằng giá trị mặc định nếu cần
                    date = (date != null) ? date : "";
                    components = (components != null) ? components : "";
                    status = (status != null) ? status : "Chưa xác nhận";

                    // Thêm vào danh sách settings
                    settings.add(new Setting(orderId, name, date, components, status));

                } while (cursorUsers.moveToNext() && cursorOrders.moveToNext());
            } else {
                Log.d("DatabaseStatus", "Không có dữ liệu từ Users hoặc Orders.");
            }
        } catch (Exception e) {
            Log.e("DatabaseError", "Lỗi khi truy vấn cơ sở dữ liệu: " + e.getMessage());
        } finally {
            // Đảm bảo đóng các con trỏ và cơ sở dữ liệu
            if (cursorUsers != null) cursorUsers.close();
            if (cursorOrders != null) cursorOrders.close();
            if (db != null) db.close();
        }

        // Trả về danh sách settings
        Log.d("DatabaseStatus", "Số lượng settings lấy được: " + settings.size());
        return settings;
    }


    public List<Customer> getAllCustomer() {
        List<Customer> customers = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;

        try {
            String query = "SELECT " + COLUMN_NAME + " AS name, " +
                    COLUMN_PHONE + " AS phone, " +
                    COLUMN_BIRTHDATE + " AS birthdate, " +
                    COLUMN_ADDRESS + " AS address, " +
                    COLUMN_EMAIL + " AS email " +
                    "FROM " + TABLE_USERS;

            cursor = db.rawQuery(query, null);

            while (cursor.moveToNext()) {
                String name = cursor.getString(cursor.getColumnIndexOrThrow("name"));
                String phone = cursor.getString(cursor.getColumnIndexOrThrow("phone"));
                String birthdate = cursor.getString(cursor.getColumnIndexOrThrow("birthdate"));
                String address = cursor.getString(cursor.getColumnIndexOrThrow("address"));
                String email = cursor.getString(cursor.getColumnIndexOrThrow("email"));

                customers.add(new Customer(
                        name != null ? name : "",
                        phone != null ? phone : "",
                        birthdate != null ? birthdate : "",
                        address != null ? address : "",
                        email != null ? email : ""
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) cursor.close();
            db.close();
        }

        return customers;
    }

    public void updateCustomer(Customer customer) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(COLUMN_NAME, customer.getName());
        values.put(COLUMN_PHONE, customer.getPhone());
        values.put(COLUMN_BIRTHDATE, customer.getBirthdate());
        values.put(COLUMN_ADDRESS, customer.getAddress());
        values.put(COLUMN_EMAIL, customer.getEmail());

        // Cập nhật hàng trong cơ sở dữ liệu
        db.update(TABLE_USERS, values, COLUMN_PHONE + " = ?", new String[]{customer.getPhone()});
        db.close();
    }

    public void addCustomer(Customer customer) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(COLUMN_NAME, customer.getName());
        values.put(COLUMN_PHONE, customer.getPhone());
        values.put(COLUMN_BIRTHDATE, customer.getBirthdate()); // Nếu không có, có thể để trống hoặc không thêm
        values.put(COLUMN_ADDRESS, customer.getAddress());
        values.put(COLUMN_EMAIL, customer.getEmail());

        // Chèn hàng mới vào cơ sở dữ liệu
        db.insert(TABLE_USERS, null, values);
        db.close();
    }

    public void deleteCustomer(int customerId) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_USERS, COLUMN_ID + " = ?", new String[]{String.valueOf(customerId)});
        db.close();
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


    public boolean insertUser(String firstName, String lastName, String email, String birthdate, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("first_name", firstName);
        contentValues.put("last_name", lastName);
        contentValues.put("email", email);
        contentValues.put("birthday", birthdate); // Cột tương ứng trong bảng
        contentValues.put("password", password);

        long result = db.insert("users", null, contentValues);
        return result != -1;
    }

    public Cursor getOrdersWithDetails() {
        SQLiteDatabase db = this.getReadableDatabase();

        // Thực hiện truy vấn lấy dữ liệu từ ba cột name, date, components
        String query = "SELECT " + COLUMN_NAME + ", " + COLUMN_DATE + ", " + COLUMN_COMPONENTS +
                " FROM " + TABLE_ORDERS;
        return db.rawQuery(query, null);  // Trả về Cursor chứa kết quả truy vấn
    }

    public boolean updateOrderStatus(int orderId, String status) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("status", status);

        // Cập nhật trạng thái cho đơn hàng với order_id
        int rowsAffected = db.update("orders", contentValues, "order_id = ?", new String[]{String.valueOf(orderId)});

        // Nếu có ít nhất một dòng bị ảnh hưởng, trả về true
        return rowsAffected > 0;  // Nếu rowsAffected = 0 thì không có đơn hàng nào được cập nhật
    }

}