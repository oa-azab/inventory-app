package me.azab.oa.inventoryapp;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import me.azab.oa.inventoryapp.data.ProductContract.ProductEntry;

import static me.azab.oa.inventoryapp.R.id.price;
import static me.azab.oa.inventoryapp.R.id.quantity;

/**
 * Created by Omar Ahmed on 6/20/2017.
 */

public class ProductCursorAdapter extends CursorAdapter {

    private Context mContext;

    public ProductCursorAdapter(Context context, Cursor cursor) {
        super(context, cursor, 0);
        mContext = context;
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.item_list, parent, false);
    }

    @Override
    public void bindView(View view, Context context, final Cursor cursor) {
        // Get UI views
        TextView nameTextView = (TextView) view.findViewById(R.id.name);
        TextView priceTextView = (TextView) view.findViewById(price);
        TextView quantityTextView = (TextView) view.findViewById(quantity);
        Button saleButton = (Button) view.findViewById(R.id.sale_btn);

        // Extract properties from cursor
        String name = cursor.getString(cursor.getColumnIndex(ProductEntry.COLUMN_PRODUCT_NAME));
        StringBuilder price = new StringBuilder();
        price.append("Price: ");
        price.append(cursor.getString(cursor.getColumnIndex(ProductEntry.COLUMN_PRODUCT_PRICE)));
        price.append(" LE");
        StringBuilder quantity = new StringBuilder();
        quantity.append("Quantity: ");
        quantity.append(cursor.getString(cursor.getColumnIndex(ProductEntry.COLUMN_PRODUCT_QUANTITY)));

        // Set values to views
        nameTextView.setText(name);
        priceTextView.setText(price);
        quantityTextView.setText(quantity);

        // Get current record id
        final int id = cursor.getInt(cursor.getColumnIndex(ProductEntry._ID));

        // Handel button click
        saleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                boolean result = decreaseQuantity(getCursor(),id);
                if(!result){
                    Toast.makeText(mContext, "Decrease quantity faild", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    /**
     * This method decrease the quantity of a product by one
     *
     * @param uriId the id of record to be quantity decreased
     * @return true for success and false for failure
     */
    private boolean decreaseQuantity(Cursor cursor, int uriId) {
        // Move cursor to the desired row
        cursor.moveToPosition(uriId - 1);

        // Get row data
        String name = cursor.getString(cursor.getColumnIndex(ProductEntry.COLUMN_PRODUCT_NAME));
        float price = cursor.getFloat(cursor.getColumnIndex(ProductEntry.COLUMN_PRODUCT_PRICE));
        int quantity = cursor.getInt(cursor.getColumnIndex(ProductEntry.COLUMN_PRODUCT_QUANTITY));
        String supplierName = cursor.getString(cursor.getColumnIndex(ProductEntry.COLUMN_PRODUCT_SUPPLIER_NAME));
        String supplierNumber = cursor.getString(cursor.getColumnIndex(ProductEntry.COLUMN_PRODUCT_SUPPLIER_NUMBER));

        // Check if quantity greater than 1 decrease otherwise return false
        if (quantity < 1) {
            return false;
        }
        quantity -= 1;

        ContentValues values = new ContentValues();
        values.put(ProductEntry.COLUMN_PRODUCT_NAME, name);
        values.put(ProductEntry.COLUMN_PRODUCT_PRICE, price);
        values.put(ProductEntry.COLUMN_PRODUCT_QUANTITY, quantity);
        values.put(ProductEntry.COLUMN_PRODUCT_SUPPLIER_NAME, supplierName);
        values.put(ProductEntry.COLUMN_PRODUCT_SUPPLIER_NUMBER, supplierNumber);

        int result = mContext.getContentResolver().update(ContentUris.withAppendedId(ProductEntry.CONTENT_URI, uriId), values, null, null);
        if (result > 0) {
            return true;

        } else {
            return false;
        }
    }
}
