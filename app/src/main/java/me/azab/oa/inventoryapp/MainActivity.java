package me.azab.oa.inventoryapp;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import me.azab.oa.inventoryapp.data.ProductContract.ProductEntry;

public class MainActivity extends AppCompatActivity {

    private FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Find UI
        fab = (FloatingActionButton) findViewById(R.id.fab_goto_add_product);

        TextView mTextView = (TextView) findViewById(R.id.textview_test);

        Cursor cursor = getContentResolver().query(ProductEntry.CONTENT_URI,null,null,null,null);
        while (cursor.moveToNext()){
            StringBuilder row = new StringBuilder();
            row.append(cursor.getString(0) + " - ");
            row.append(cursor.getString(1) + " - ");
            row.append(cursor.getString(2) + " - ");
            row.append(cursor.getString(3) + " - ");
            row.append(cursor.getString(4) + " - ");
            row.append(cursor.getString(5) + " - ");
            mTextView.append("\n"+row);
        }

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
