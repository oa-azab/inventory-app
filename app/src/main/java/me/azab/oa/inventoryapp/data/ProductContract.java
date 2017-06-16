package me.azab.oa.inventoryapp.data;

/**
 * Created by Omar Ahmed on 6/12/2017.
 */

import android.provider.BaseColumns;

/**
 *  Contract class describe contract between app and database
 *
 */
public final class ProductContract {

    // Private constructor to not instantiate objects from this class
    private ProductContract(){}

    /**
     * This class provide all information about the inventory table
     */
    public static final class ProductEntry implements BaseColumns{

        // Table name
        public static final String TABLE_NAME = "products";

        // Column names
        public static final String _ID = BaseColumns._ID;
        public static final String COLUMN_PRODUCT_NAME = "name";
        public static final String COLUMN_PRODUCT_PICTURE = "picture";
        public static final String COLUMN_PRODUCT_PRICE = "price";
        public static final String COLUMN_PRODUCT_QUANTITY = "quantity";
        public static final String COLUMN_PRODUCT_SUPPLIER_NAME = "supplier_name";
        public static final String COLUMN_PRODUCT_SUPPLIER_NUMBER = "supplier_number";
    }
}
