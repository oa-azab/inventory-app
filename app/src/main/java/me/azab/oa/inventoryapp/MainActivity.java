package me.azab.oa.inventoryapp;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ListView;

import me.azab.oa.inventoryapp.data.ProductContract.ProductEntry;

public class MainActivity extends AppCompatActivity {

    private FloatingActionButton fab;
    private ListView productsListView;
    private ProductCursorAdapter productCursorAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Find UI
        fab = (FloatingActionButton) findViewById(R.id.fab_goto_add_product);
        productsListView = (ListView) findViewById(R.id.products_listview);

        // Find and set empty view on the ListView, so that it only shows when the list has 0 items.
        View emptyView = findViewById(R.id.empty_view);
        productsListView.setEmptyView(emptyView);

        Cursor cursor = getContentResolver().query(ProductEntry.CONTENT_URI, null, null, null, null);

        // adapter initialization
        productCursorAdapter = new ProductCursorAdapter(this, cursor);

        productsListView.setAdapter(productCursorAdapter);

//        while (cursor.moveToNext()){
//            StringBuilder row = new StringBuilder();
//            row.append(cursor.getString(0) + " - ");
//            row.append(cursor.getString(1) + " - ");
//            row.append(cursor.getString(2) + " - ");
//            row.append(cursor.getString(3) + " - ");
//            row.append(cursor.getString(4) + " - ");
//            row.append(cursor.getString(5) + " - ");
//            mTextView.append("\n"+row);
//        }

        // Handle click navigate user to AddProductActivity
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddProductActivity.class);
                startActivity(intent);
            }
        });
    }
}
