package USER.login_signin;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.duan1.R;

import Database.DBHelper;


public class UserDAO {
    private final SQLiteDatabase db;

    public UserDAO(Context context) {
        DBHelper dbHelper = new DBHelper(context);
        db = dbHelper.getWritableDatabase();
    }

    public boolean saveUser(String email, String password) {
        ContentValues values = new ContentValues();
        values.put(DBHelper.COLUMN_EMAIL, email);
        values.put(DBHelper.COLUMN_PASSWORD, password);

        long result = db.insert(DBHelper.TABLE_USERS, null, values);
        return result != -1; // Return true if insert is successful
    }

    public boolean isUserExist(String email) {
        Cursor cursor = db.query(DBHelper.TABLE_USERS,
                new String[]{DBHelper.COLUMN_EMAIL},
                DBHelper.COLUMN_EMAIL + "=?",
                new String[]{email},
                null, null, null);

        boolean exists = cursor != null && cursor.moveToFirst();
        if (cursor != null) cursor.close();
        return exists;
    }

    public String getPassword(String email) {
        Cursor cursor = db.query(DBHelper.TABLE_USERS,
                new String[]{DBHelper.COLUMN_PASSWORD},
                DBHelper.COLUMN_EMAIL + "=?",
                new String[]{email},
                null, null, null);

        String password = null; // Initialize the variable to hold the password

        if (cursor != null) {
            if (cursor.moveToFirst()) { // Ensure the cursor is not empty
                int columnIndex = cursor.getColumnIndex(DBHelper.COLUMN_PASSWORD);
                if (columnIndex != -1) {
                    password = cursor.getString(columnIndex); // Fetch the password
                } else {
                    Log.e("UserDAO", "Column not found: " + DBHelper.COLUMN_PASSWORD);
                }
            }
            cursor.close(); // Make sure to close the cursor to avoid memory leaks
        }

        return password; // Return the fetched password or null if not found
    }
}