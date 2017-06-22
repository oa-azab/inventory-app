package me.azab.oa.inventoryapp;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import me.azab.oa.inventoryapp.data.ProductContract.ProductEntry;

/**
 * Created by Omar Ahmed on 6/22/2017.
 */

public final class ProductUtils {

    /**
     * This method change the quantity of a product increase or decrease by one
     *
     * @param context context to get contentResolver
     * @param cursor the cursor of the product
     * @param uriId product position in product
     * @param increase true to increase false to decrease
     *
     * @return true if success
     */
    public static boolean changeQuantity(Context context, Cursor cursor, long uriId, boolean increase) {
        // Move cursor to the desired row
        cursor.moveToPosition(0);

        // Get row data
        String name = cursor.getString(cursor.getColumnIndex(ProductEntry.COLUMN_PRODUCT_NAME));
        float price = cursor.getFloat(cursor.getColumnIndex(ProductEntry.COLUMN_PRODUCT_PRICE));
        int quantity = cursor.getInt(cursor.getColumnIndex(ProductEntry.COLUMN_PRODUCT_QUANTITY));
        String supplierName = cursor.getString(cursor.getColumnIndex(ProductEntry.COLUMN_PRODUCT_SUPPLIER_NAME));
        String supplierNumber = cursor.getString(cursor.getColumnIndex(ProductEntry.COLUMN_PRODUCT_SUPPLIER_NUMBER));

        if (increase) {
            quantity += 1;
        } else {
            // Check if quantity greater than 1 decrease otherwise return false
            if (quantity < 1) {
                return false;
            }
            quantity -= 1;
        }

        ContentValues values = new ContentValues();
        values.put(ProductEntry.COLUMN_PRODUCT_NAME, name);
        values.put(ProductEntry.COLUMN_PRODUCT_PRICE, price);
        values.put(ProductEntry.COLUMN_PRODUCT_QUANTITY, quantity);
        values.put(ProductEntry.COLUMN_PRODUCT_SUPPLIER_NAME, supplierName);
        values.put(ProductEntry.COLUMN_PRODUCT_SUPPLIER_NUMBER, supplierNumber);

        int result = context.getContentResolver().update(ContentUris.withAppendedId(ProductEntry.CONTENT_URI, uriId), values, null, null);
        if (result > 0) {
            return true;

        } else {
            return false;
        }
    }
}
