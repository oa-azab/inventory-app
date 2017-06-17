package me.azab.oa.inventoryapp.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import me.azab.oa.inventoryapp.data.ProductContract.ProductEntry;

/**
 * Created by Omar Ahmed on 6/15/2017.
 */

public class ProductDbHelper extends SQLiteOpenHelper {

    public static final String LOG_TAG = ProductDbHelper.class.getSimpleName();

    // Database name and version
    public static final String DATABASE_NAME = "inventory.db";
    public static final int DATABASE_VERSION = 1;

    // Create statement
    public static final String CREATE_PRODUCTS_TABLE = "CREATE TABLE " + ProductEntry.TABLE_NAME + " ("
            + ProductEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + ProductEntry.COLUMN_PRODUCT_NAME + " TEXT NOT NULL, "
            + ProductEntry.COLUMN_PRODUCT_PICTURE + " TEXT, "
            + ProductEntry.COLUMN_PRODUCT_PRICE + " REAL NOT NULL, "
            + ProductEntry.COLUMN_PRODUCT_QUANTITY + " INTEGER NOT NULL DEFAULT 0, "
            + ProductEntry.COLUMN_PRODUCT_SUPPLIER_NAME + " TEXT, "
            + ProductEntry.COLUMN_PRODUCT_SUPPLIER_NUMBER + " TEXT);";

    public ProductDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create table
        db.execSQL(CREATE_PRODUCTS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop old table if exist
        db.execSQL("DROP TABLE IF EXISTS " + ProductEntry.TABLE_NAME);
        // Create new db
        db.execSQL(CREATE_PRODUCTS_TABLE);
    }
}
