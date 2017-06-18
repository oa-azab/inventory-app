package me.azab.oa.inventoryapp;

import android.content.ContentValues;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import me.azab.oa.inventoryapp.data.ProductContract.ProductEntry;

public class AddProductActivity extends AppCompatActivity {

    private EditText nameEditText;
    private EditText priceEditText;
    private EditText quantityEditText;
    private EditText supplierNameEditText;
    private EditText supplierNumberEditText;
    private FloatingActionButton addProductFab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);

        //Find UI
        nameEditText = (EditText) findViewById(R.id.edit_product_name);
        priceEditText = (EditText) findViewById(R.id.edit_product_price);
        quantityEditText = (EditText) findViewById(R.id.edit_product_quantity);
        supplierNameEditText = (EditText) findViewById(R.id.edit_product_supplier_name);
        supplierNumberEditText = (EditText) findViewById(R.id.edit_product_supplier_number);
        addProductFab = (FloatingActionButton) findViewById(R.id.fab_add_product);

        // Handle click add new product to db
        addProductFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addProduct();
                finish();
            }
        });
    }

    /**
     *  Method Extract data form EditTexts and add new prodcut to database
     *
     */
    private void addProduct(){

        // Extract data from EditTexts
        String name = nameEditText.getText().toString().trim();
        float price = Float.parseFloat(priceEditText.getText().toString().trim());
        int quantity = Integer.parseInt(quantityEditText.getText().toString().trim());
        String supplierName = supplierNameEditText.getText().toString().trim();
        String supplierNumber = supplierNumberEditText.getText().toString().trim();

        // Add data to corresponding db columns
        ContentValues values = new ContentValues();
        values.put(ProductEntry.COLUMN_PRODUCT_NAME,name);
        values.put(ProductEntry.COLUMN_PRODUCT_PRICE,price);
        values.put(ProductEntry.COLUMN_PRODUCT_QUANTITY,quantity);
        values.put(ProductEntry.COLUMN_PRODUCT_SUPPLIER_NAME,supplierName);
        values.put(ProductEntry.COLUMN_PRODUCT_SUPPLIER_NUMBER,supplierNumber);

        // Add values to db
        Uri uriResult = getContentResolver().insert(ProductEntry.CONTENT_URI,values);

        Toast.makeText(this, "result = "+uriResult, Toast.LENGTH_SHORT).show();
    }
}
