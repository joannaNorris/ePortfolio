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
    private static final int DATABASE_VERSION = 1;
    //table and column names
    public static final String TABLE_ITEMS = "items";
    public static final String COL_ID = "id";
    public static final String COL_NAME = "name";
    public static final String COL_SKU = "sku";
    public static final String COL_QUANTITY = "quantity";

    //constructor
    public InventoryDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    //create database tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable =
                "CREATE TABLE " + TABLE_ITEMS + " (" +
                        COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        COL_NAME + " TEXT NOT NULL, " +
                        COL_SKU + " TEXT, " +
                        COL_QUANTITY + " INTEGER NOT NULL" +
                        ");";
        db.execSQL(createTable);
    }

    //to handle upgrades
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ITEMS);
        onCreate(db);
    }

    //C: add a new inventory item
    public boolean addItem(String name, String sku, int quantity) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COL_NAME, name);
        values.put(COL_SKU, sku);
        values.put(COL_QUANTITY, quantity);

        long result = db.insert(TABLE_ITEMS, null, values);
        return result != -1;
    }

    //R: get all inventory items
    public Cursor getAllItems() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.query(
                TABLE_ITEMS,
                null,
                null,
                null,
                null,
                null,
                COL_NAME + " ASC" //order ascending by name
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

