package me.azab.oa.inventoryapp;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import me.azab.oa.inventoryapp.data.ProductContract.ProductEntry;

public class DetailActivity extends AppCompatActivity {

    Uri contentUri;
    TextView productPriceTextView;
    TextView productQuantityTextView;
    Button quantityIncreaseBtn;
    Button quantityDecreaseBtn;
    Button callSupplierBtn;
    Button deleteProductBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        // Get item uri
        contentUri = getIntent().getData();

        // Get item cursor
        Cursor productCursor = getContentResolver().query(contentUri, null, null, null, null);
        productCursor.moveToFirst();

        // Get product details
        String name = productCursor.getString(productCursor.getColumnIndex(ProductEntry.COLUMN_PRODUCT_NAME));
        float price = productCursor.getFloat(productCursor.getColumnIndex(ProductEntry.COLUMN_PRODUCT_PRICE));
        int quantity = productCursor.getInt(productCursor.getColumnIndex(ProductEntry.COLUMN_PRODUCT_QUANTITY));
        String supplierName = productCursor.getString(productCursor.getColumnIndex(ProductEntry.COLUMN_PRODUCT_SUPPLIER_NAME));
        final String supplierNumber = productCursor.getString(productCursor.getColumnIndex(ProductEntry.COLUMN_PRODUCT_SUPPLIER_NUMBER));

        // Set Activity title
        setTitle(name);

        // Get UI
        productPriceTextView = (TextView) findViewById(R.id.txt_product_price);
        productQuantityTextView = (TextView) findViewById(R.id.txt_product_quantity);
        quantityIncreaseBtn = (Button) findViewById(R.id.increase_quantity);
        quantityDecreaseBtn = (Button) findViewById(R.id.decrease_quantity);
        callSupplierBtn = (Button) findViewById(R.id.call_supplier);
        deleteProductBtn = (Button) findViewById(R.id.delete_record);

        // Set UI data
        productPriceTextView.setText("Price: "+price);
        productQuantityTextView.setText(String.valueOf(quantity));

        // Hock button with listeners
        quantityDecreaseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        quantityIncreaseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        callSupplierBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:"+supplierNumber));
                startActivity(intent);
            }
        });

        deleteProductBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}
