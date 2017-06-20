package me.azab.oa.inventoryapp;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.TextView;


import me.azab.oa.inventoryapp.data.ProductContract.ProductEntry;

/**
 * Created by Omar Ahmed on 6/20/2017.
 */

public class ProductCursorAdapter extends CursorAdapter {

    public ProductCursorAdapter(Context context, Cursor cursor) {
        super(context, cursor, 0);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.item_list, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        // Get UI views
        TextView nameTextView = (TextView) view.findViewById(R.id.name);
        TextView priceTextView = (TextView) view.findViewById(R.id.price);
        TextView quantityTextView = (TextView) view.findViewById(R.id.quantity);
        Button saleButton = (Button) view.findViewById(R.id.sale_btn);

        // Extract properties from cursor
        String name = cursor.getString(cursor.getColumnIndex(ProductEntry.COLUMN_PRODUCT_NAME));
        String price = cursor.getString(cursor.getColumnIndex(ProductEntry.COLUMN_PRODUCT_PRICE));
        String quantity = cursor.getString(cursor.getColumnIndex(ProductEntry.COLUMN_PRODUCT_QUANTITY));

        // Set values to views
        nameTextView.setText(name);
        priceTextView.setText(price);
        quantityTextView.setText(quantity);
    }
}
