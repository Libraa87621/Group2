package Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "app_database";
    private static final int DATABASE_VERSION = 1;

    // Tạo bảng users với các cột đúng kiểu dữ liệu
    private static final String CREATE_TABLE_USERS = "CREATE TABLE users (" +
            "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "name TEXT, " +
            "email TEXT, " +
            "sdt TEXT, " +
            "address TEXT, " +
            "phone TEXT);";

    // Tạo bảng orders cho đơn hàng
    private static final String CREATE_TABLE_ORDERS = "CREATE TABLE orders (" +
            "order_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "date TEXT, " +
            "address TEXT, " +
            "image_url TEXT, " +
            "components TEXT, " +
            "price REAL, " +
            "quantity INTEGER);";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Gọi khi cơ sở dữ liệu được tạo lần đầu
    @Override
    public void onCreate(SQLiteDatabase db) {
        // Tạo bảng users
        db.execSQL(CREATE_TABLE_USERS);
        // Tạo bảng orders
        db.execSQL(CREATE_TABLE_ORDERS);
    }

    // Gọi khi phiên bản cơ sở dữ liệu thay đổi
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS users");
        db.execSQL("DROP TABLE IF EXISTS orders");
        onCreate(db);
    }
}
