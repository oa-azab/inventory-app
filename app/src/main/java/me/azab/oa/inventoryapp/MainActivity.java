package me.azab.oa.inventoryapp;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import me.azab.oa.inventoryapp.data.ProductContract.ProductEntry;
import me.azab.oa.inventoryapp.data.ProductDbHelper;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ProductDbHelper dbHelper = new ProductDbHelper(this);

        ContentValues values = new ContentValues();
        values.put(ProductEntry.COLUMN_PRODUCT_NAME,"prod1");
        values.put(ProductEntry.COLUMN_PRODUCT_PICTURE,"sss");
        values.put(ProductEntry.COLUMN_PRODUCT_PRICE,11.55);
        values.put(ProductEntry.COLUMN_PRODUCT_QUANTITY,5);
        values.put(ProductEntry.COLUMN_PRODUCT_SUPPLIER_NAME,"sup");
        values.put(ProductEntry.COLUMN_PRODUCT_SUPPLIER_NUMBER,"0100");

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        long i = db.insert(ProductEntry.TABLE_NAME,null,values);
        Log.d("TAGIT",i+" <<id");

        TextView mTextView = (TextView) findViewById(R.id.textview_test);

        SQLiteDatabase dbR = dbHelper.getReadableDatabase();
        Cursor cursor = dbR.query(ProductEntry.TABLE_NAME,null,null,null,null,null,null);
        while (cursor.moveToNext()){
            String row = cursor.getString(3);
            mTextView.append("\n"+row);
        }
    }
}
