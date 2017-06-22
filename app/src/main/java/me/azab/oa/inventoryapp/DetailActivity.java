package me.azab.oa.inventoryapp;

import android.content.ContentUris;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import me.azab.oa.inventoryapp.data.ProductContract.ProductEntry;

public class DetailActivity extends AppCompatActivity {

    Uri contentUri;
    TextView productPriceTextView;
    TextView productQuantityTextView;
    TextView productSupplierNameTextView;
    Button quantityIncreaseBtn;
    Button quantityDecreaseBtn;
    Button callSupplierBtn;
    Button deleteProductBtn;
    Cursor productCursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        // Get item uri
        contentUri = getIntent().getData();

        // Get item cursor
        productCursor = getContentResolver().query(contentUri, null, null, null, null);
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
        productSupplierNameTextView = (TextView) findViewById(R.id.txt_product_supplier_name);
        quantityIncreaseBtn = (Button) findViewById(R.id.increase_quantity);
        quantityDecreaseBtn = (Button) findViewById(R.id.decrease_quantity);
        callSupplierBtn = (Button) findViewById(R.id.call_supplier);
        deleteProductBtn = (Button) findViewById(R.id.delete_record);

        // Set UI data
        productPriceTextView.setText(getString(R.string.label_price) + price);
        productQuantityTextView.setText(String.valueOf(quantity));
        productSupplierNameTextView.setText(getString(R.string.label_supplier_name) +supplierName);

        // Hock button with listeners
        quantityDecreaseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProductUtils.changeQuantity(DetailActivity.this
                        , productCursor
                        , ContentUris.parseId(contentUri)
                        , false);
                updateUi();
            }
        });

        quantityIncreaseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProductUtils.changeQuantity(DetailActivity.this
                        , productCursor
                        , ContentUris.parseId(contentUri)
                        , true);
                updateUi();
            }
        });

        callSupplierBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:" + supplierNumber));
                startActivity(intent);
            }
        });

        deleteProductBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDeleteConfirmationDialog();
            }
        });
    }

    /**
     * Update cursor and the ui of the quantity
     */
    private void updateUi(){
        productCursor = getContentResolver().query(contentUri, null, null, null, null);
        productCursor.moveToFirst();
        int quantity = productCursor.getInt(productCursor.getColumnIndex(ProductEntry.COLUMN_PRODUCT_QUANTITY));
        productQuantityTextView.setText(String.valueOf(quantity));
    }

    /**
     * Show alert dialog to confirm deleting the product
     */
    private void showDeleteConfirmationDialog() {
        // Create an AlertDialog.Builder and set the message, and click listeners
        // for the postivie and negative buttons on the dialog.
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.delete_dialog_message);
        builder.setPositiveButton(R.string.delete_dialog_positive, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User clicked the "Delete" button, so delete the product.
                deleteProduct();
            }
        });
        builder.setNegativeButton(R.string.delete_dialog_negative, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User clicked the "Cancel" button, so dismiss the dialog
                // and continue editing the pet.
                if (dialog != null) {
                    dialog.dismiss();
                }
            }
        });

        // Create and show the AlertDialog
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    /**
     * Delete product from database
     */
    private void deleteProduct(){
        int result = getContentResolver().delete(contentUri,null,null);
        if( result > 0){
            Toast.makeText(this, R.string.toast_message_deleted, Toast.LENGTH_SHORT).show();
            finish();
        }
    }
}
