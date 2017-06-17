package me.azab.oa.inventoryapp.data;

/**
 * Created by Omar Ahmed on 6/12/2017.
 */

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Contract class describe contract between app and database
 */
public final class ProductContract {

    // Content authority of  {@link ProductProvider}
    public static final String CONTENT_AUTHORITY = "me.azab.oa.inventoryapp";

    // Base content uri of {@link ProductProvider}
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    // Path to Products table
    public static final String PATH_PRODUCTS = "products";

    // Private constructor to not instantiate objects from this class
    private ProductContract() {
    }

    /**
     * This class provide all information about the inventory table
     */
    public static final class ProductEntry implements BaseColumns {

        // Content Uri to access Products table in {@link ProductProvider}
        public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI,PATH_PRODUCTS);
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
