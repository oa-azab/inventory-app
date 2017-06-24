package me.azab.oa.inventoryapp;

import android.content.ContentValues;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.IOException;

import me.azab.oa.inventoryapp.data.ProductContract.ProductEntry;

public class AddProductActivity extends AppCompatActivity {

    private EditText nameEditText;
    private EditText priceEditText;
    private EditText quantityEditText;
    private EditText supplierNameEditText;
    private EditText supplierNumberEditText;
    private FloatingActionButton addProductFab;
    private Button addPictureBtn;
    private ImageView picturePreview;
    private String pictureUri = null;

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
        addPictureBtn = (Button) findViewById(R.id.add_picture_btn);
        picturePreview = (ImageView) findViewById(R.id.picture_preview);

        // Handle add picture button
        addPictureBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent getIntent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
                getIntent.setType("image/*");

                Intent pickIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                pickIntent.setType("image/*");

                Intent chooserIntent = Intent.createChooser(getIntent, "Select Image");
                chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, new Intent[] {pickIntent});

                startActivityForResult(chooserIntent, 101);
            }
        });

        // Handle click add new product to db
        addProductFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addProduct();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 101){
            if(resultCode == RESULT_OK){
                Uri contentUri = data.getData();
                pictureUri = String.valueOf(contentUri);
                Toast.makeText(this, ""+contentUri, Toast.LENGTH_SHORT).show();
                try {
                    Bitmap image = MediaStore.Images.Media.getBitmap(getContentResolver(),contentUri);
                    picturePreview.setImageBitmap(image);
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }
    }

    /**
     *  Method Extract data form EditTexts and add new prodcut to database
     *
     */
    private void addProduct(){

        // Extract data from EditTexts
        String name = nameEditText.getText().toString().trim();
        String priceStr = priceEditText.getText().toString().trim();
        String quantityStr = quantityEditText.getText().toString().trim();
        String supplierName = supplierNameEditText.getText().toString().trim();
        String supplierNumber = supplierNumberEditText.getText().toString().trim();

        // Validate data
        if(name.isEmpty() || supplierName.isEmpty() || supplierNumber.isEmpty() ||
                priceStr.isEmpty() || quantityStr.isEmpty()){
            Toast.makeText(this, "Provide correct data.", Toast.LENGTH_SHORT).show();
            return;
        }

        // Cast data to float and int
        float price = Float.valueOf(priceStr);
        int quantity = Integer.valueOf(quantityStr);

        // Add data to corresponding db columns
        ContentValues values = new ContentValues();
        values.put(ProductEntry.COLUMN_PRODUCT_NAME,name);
        if(pictureUri != null){
            values.put(ProductEntry.COLUMN_PRODUCT_PICTURE,pictureUri);
        }
        values.put(ProductEntry.COLUMN_PRODUCT_PRICE,price);
        values.put(ProductEntry.COLUMN_PRODUCT_QUANTITY,quantity);
        values.put(ProductEntry.COLUMN_PRODUCT_SUPPLIER_NAME,supplierName);
        values.put(ProductEntry.COLUMN_PRODUCT_SUPPLIER_NUMBER,supplierNumber);

        // Add values to db
        Uri uriResult = getContentResolver().insert(ProductEntry.CONTENT_URI,values);

        Toast.makeText(this, "result = "+uriResult, Toast.LENGTH_SHORT).show();

        finish();
    }
}
