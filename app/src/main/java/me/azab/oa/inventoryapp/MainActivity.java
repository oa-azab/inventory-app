package me.azab.oa.inventoryapp;

import android.app.LoaderManager;
import android.content.ContentUris;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import me.azab.oa.inventoryapp.data.ProductContract.ProductEntry;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    private FloatingActionButton fab;
    private ListView productsListView;
    private ProductCursorAdapter productCursorAdapter;
    private static final int PRODUCTS_LOADER_ID = 1;

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

        //Cursor cursor = getContentResolver().query(ProductEntry.CONTENT_URI, null, null, null, null);

        // Initialize CursorLoader
        getLoaderManager().initLoader(PRODUCTS_LOADER_ID, null, this);

        // adapter initialization
        productCursorAdapter = new ProductCursorAdapter(this, null);
        productsListView.setAdapter(productCursorAdapter);

        // Handle on item click
        productsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this, DetailActivity.class);

                Uri contentUri = ContentUris.withAppendedId(ProductEntry.CONTENT_URI, id);

                intent.setData(contentUri);

                startActivity(intent);
            }
        });


        // Handle click navigate user to AddProductActivity
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddProductActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new CursorLoader(this
                , ProductEntry.CONTENT_URI
                , null , null, null, null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        productCursorAdapter.swapCursor(cursor);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        productCursorAdapter.swapCursor(null);
    }
}
