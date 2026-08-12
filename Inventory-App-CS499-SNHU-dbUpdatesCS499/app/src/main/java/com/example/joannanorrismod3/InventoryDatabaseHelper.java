package com.example.joannanorrismod3;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

//manages CRUD for inventory items
public class InventoryDatabaseHelper extends SQLiteOpenHelper {
    //database info
    private static final String DATABASE_NAME = "inventory.db";
    // db version 2
    private static final int DATABASE_VERSION = 2;

    //table names
    public static final String TABLE_ITEMS = "items";
    public static final String TABLE_CATEGORIES = "categories";
    public static final String TABLE_SUPPLIERS = "suppliers";
    public static final String TABLE_TRANSACTIONS = "transactions";

    //inventory items table column names
    public static final String COL_ID = "item_id";
    public static final String COL_NAME = "item_name";
    public static final String COL_SKU = "sku";
    public static final String COL_QUANTITY = "item_quantity";
    public static final String COL_DESC = "description";
    public static final String COL_CAT_ID_FK = "category_id";
    public static final String COL_SUP_ID_FK = "supplier_id";

    //categories table columns
    public static final String COL_CAT_ID = "category_id";
    public static final String COL_CAT_NAME = "category_name";

    //suppliers table columns
    public static final String COL_SUP_ID = "supplier_id";
    public static final String COL_SUP_NAME = "supplier_name";
    public static final String COL_SUP_CONTACT = "supplier_contact";

    //transactions table columns
    public static final String COL_TRANS_ID = "transaction_id";
    public static final String COL_TRANS_DATE = "transaction_date";
    public static final String COL_TRANS_TYPE = "transaction_type";
    public static final String COL_ITEM_ID_FK = "item_id";
    public static final String COL_QUANTITY_CHANGED = "quantity_changed";
    public static final String COL_NOTES = "notes";



    //constructor
    public InventoryDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    //create database tables
    @Override
    public void onCreate(SQLiteDatabase db) {

        //allow foreign keys
        db.execSQL("PRAGMA foreign_keys=ON");

        //create items table
        String createItemsTable =
                "CREATE TABLE " + TABLE_ITEMS + " (" +
                        COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        COL_NAME + " TEXT NOT NULL, " +
                        COL_SKU + " TEXT, " +
                        COL_QUANTITY + " INTEGER NOT NULL," +
                        COL_DESC + " TEXT, " +
                        COL_CAT_ID_FK + " INTEGER, " +
                        COL_SUP_ID_FK + " INTEGER, " +
                        "FOREIGN KEY (" + COL_CAT_ID_FK + ") REFERENCES " +
                        TABLE_CATEGORIES + "(" + COL_CAT_ID + "), " +
                        "FOREIGN KEY (" + COL_SUP_ID_FK + ") REFERENCES " +
                        TABLE_SUPPLIERS + "(" + COL_SUP_ID + ") " +
                        ");";


        //create categories table
        String createCategoriesTable =
                "CREATE TABLE " + TABLE_CATEGORIES + " (" +
                        COL_CAT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        COL_CAT_NAME + " TEXT NOT NULL " +
                        ");";

        //create suppliers table
        String createSuppliersTable =
                "CREATE TABLE " + TABLE_SUPPLIERS + " (" +
                        COL_SUP_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        COL_SUP_CONTACT + " TEXT, " +
                        COL_SUP_NAME + " TEXT NOT NULL " +
                        ");";

        String createTransactionTable =
                "CREATE TABLE " + TABLE_TRANSACTIONS + " (" +
                        COL_TRANS_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        COL_TRANS_DATE + " TEXT NOT NULL, " +
                        COL_TRANS_TYPE + " TEXT NOT NULL, " +
                        COL_QUANTITY_CHANGED + " INTEGER NOT NULL, " +
                        COL_ITEM_ID_FK + " INTEGER NOT NULL, " +
                        COL_NOTES + " TEXT, " +
                        "FOREIGN KEY (" + COL_ITEM_ID_FK + ") REFERENCES " +
                        TABLE_ITEMS + " (" + COL_ID + ")" +
                        ");";

        db.execSQL(createCategoriesTable);
        db.execSQL(createSuppliersTable);
        db.execSQL(createItemsTable);
        db.execSQL(createTransactionTable);


    }

    //to handle upgrades
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TRANSACTIONS); //ordered by foreign key
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ITEMS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SUPPLIERS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CATEGORIES);
        onCreate(db);
    }

    //C: add a new inventory item
    public long addItem(String name, String sku, int quantity, String desc, int categoryId, int supplierId) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COL_NAME, name);
        values.put(COL_SKU, sku);
        values.put(COL_QUANTITY, quantity);
        values.put(COL_DESC, desc);
        values.put(COL_CAT_ID_FK, categoryId);
        values.put(COL_SUP_ID_FK, supplierId);

        return db.insert(TABLE_ITEMS, null, values);
    }

    //C: add a new category
    public long addCategory(String categoryName) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COL_CAT_NAME, categoryName);

        return db.insert(TABLE_CATEGORIES, null, values);
    }

    //C: add a new supplier
    public long addSupplier(String supplierName, String contactInfo) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COL_SUP_NAME, supplierName);
        values.put(COL_SUP_CONTACT, contactInfo);

        return db.insert(TABLE_SUPPLIERS, null, values);
    }

    //C: add a new transaction
    public long addTransaction(int itemId, String transactionType, int quantityChanged, String notes) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COL_ITEM_ID_FK, itemId);
        values.put(COL_TRANS_TYPE, transactionType);
        values.put(COL_QUANTITY_CHANGED, quantityChanged);
        values.put(COL_TRANS_DATE, String.valueOf(System.currentTimeMillis()));
        values.put(COL_NOTES, notes);

        return db.insert(TABLE_TRANSACTIONS, null, values);
    }

    //R: get all inventory items
    public Cursor getAllItems() {
        SQLiteDatabase db = this.getReadableDatabase();

        //return items using a SQL query
        String query =
                "SELECT " +
                        "i." + COL_ID + ", " +
                        "i." + COL_NAME + ", " +
                        "i." + COL_SKU + ", " +
                        "i." + COL_QUANTITY + ", " +
                        "i." + COL_DESC + ", " +
                        "i." + COL_CAT_ID_FK + ", " +
                        "i." + COL_SUP_ID_FK + ", " +
                        "c." + COL_CAT_NAME + " AS category_name, " +
                        "s." + COL_SUP_NAME + " AS supplier_name, " +
                        "s." + COL_SUP_CONTACT + " AS supplier_contact " +
                        "FROM " + TABLE_ITEMS + " i " +
                        "LEFT JOIN " + TABLE_CATEGORIES + " c " +
                        "ON i." + COL_CAT_ID_FK + " = c." + COL_CAT_ID + " " +
                        "LEFT JOIN " + TABLE_SUPPLIERS + " s " +
                        "ON i." + COL_SUP_ID_FK + " = s." + COL_SUP_ID + " " +
                        "ORDER BY i." + COL_NAME + " ASC";

        return db.rawQuery(query, null);
    }


    //R: get all categories
    public Cursor getAllCategories() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.query(
                TABLE_CATEGORIES,
                new String[]{COL_CAT_ID + " AS _id", COL_CAT_NAME},
                null,
                null,
                null,
                null,
                COL_CAT_NAME + " ASC"
        );
    }

    //method for "All Categories" as a filter option
    public Cursor getFilterCategories() {
        SQLiteDatabase db = this.getReadableDatabase();

        String query =
                "SELECT " +
                        COL_CAT_ID + " AS _id, " +
                        COL_CAT_NAME +
                        " FROM " + TABLE_CATEGORIES +
                        " UNION ALL " +
                        "SELECT -1 AS _id, " +
                        "'All Categories' AS " + COL_CAT_NAME +
                        " ORDER BY _id ASC";

        return db.rawQuery(query, null);
    }

    //R: get all suppliers
    public Cursor getAllSuppliers() {
        SQLiteDatabase db = this.getReadableDatabase();

        return db.query(
                TABLE_SUPPLIERS,
                new String[]{COL_SUP_ID + " AS _id", COL_SUP_NAME},
                null,
                null,
                null,
                null,
                COL_SUP_NAME + " ASC"
        );
    }

    //R: filter items by category
    public Cursor getItemsByCategory(int categoryId) {

        SQLiteDatabase db = this.getReadableDatabase();

        String query =
                "SELECT " +
                        "i." + COL_ID + ", " +
                        "i." + COL_NAME + ", " +
                        "i." + COL_SKU + ", " +
                        "i." + COL_QUANTITY + ", " +
                        "i." + COL_DESC + ", " +
                        "i." + COL_CAT_ID_FK + ", " +
                        "i." + COL_SUP_ID_FK + ", " +
                        "c." + COL_CAT_NAME + " AS category_name, " +
                        "s." + COL_SUP_NAME + " AS supplier_name, " +
                        "s." + COL_SUP_CONTACT + " AS supplier_contact " +
                        "FROM " + TABLE_ITEMS + " i " +
                        "LEFT JOIN " + TABLE_CATEGORIES + " c " +
                        "ON i." + COL_CAT_ID_FK + " = c." + COL_CAT_ID + " " +
                        "LEFT JOIN " + TABLE_SUPPLIERS + " s " +
                        "ON i." + COL_SUP_ID_FK + " = s." + COL_SUP_ID + " " +
                        "WHERE i." + COL_CAT_ID_FK + " = ? " +
                        "ORDER BY i." + COL_NAME + " ASC";

        return db.rawQuery(
                query,
                new String[]{String.valueOf(categoryId)}
        );
    }

    //U: Update quantity
    public boolean updateItemQuantity(int id, int newQuantity) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COL_QUANTITY, newQuantity);

        int rowsUpdated = db.update(
                TABLE_ITEMS,
                values,
                COL_ID + "=?",
                new String[]{String.valueOf(id)}
        );

        return rowsUpdated > 0;
    }

    //D: Remove an item from inventory
    public boolean deleteItem(int id) {
        SQLiteDatabase db = this.getWritableDatabase();

        int rowsDeleted = db.delete(
                TABLE_ITEMS,
                COL_ID + "=?",
                new String[]{String.valueOf(id)}
        );

        return rowsDeleted > 0;
    }
}

