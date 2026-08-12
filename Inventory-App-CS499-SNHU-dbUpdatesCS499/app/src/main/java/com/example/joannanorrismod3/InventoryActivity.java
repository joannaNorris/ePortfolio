package com.example.joannanorrismod3;



import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;
import android.widget.Spinner;
import android.widget.AdapterView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

//displays inventory items in a grid/list and allows users to add and delete items
public class InventoryActivity extends AppCompatActivity {
    private InventoryDatabaseHelper dbHelper;
    private InventoryAdapter adapter;


    private Cursor cursor;
    private Button btnAddItem;
    private Spinner spinnerCategoryFilter;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grid);

        //initialize database
        dbHelper = new InventoryDatabaseHelper(this);

        //initialize category filter spinner
        spinnerCategoryFilter = findViewById(R.id.spinnerFilterCategory);
        loadFilterCategories();

        //action for filtering items by category
        spinnerCategoryFilter.setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener() {

                    @Override
                    public void onItemSelected(
                            AdapterView<?> parent,
                            View view,
                            int position,
                            long id) {

                        if (id == -1) {
                            refreshGrid();
                        } else {
                            Cursor filteredCursor =
                                    dbHelper.getItemsByCategory((int) id);

                            adapter.swapCursor(filteredCursor);
                        }
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                        refreshGrid();
                    }
                }
        );

        RecyclerView recyclerView = findViewById(R.id.recyclerGrid);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 1));

        //change add button to update when in edit mode
        btnAddItem = findViewById(R.id.btnAddItem);
        btnAddItem.setText("Add");

        //initialize adapter with db cursor
        cursor = dbHelper.getAllItems();

        //create adapter
        adapter = new InventoryAdapter(this,
                cursor,
                //delete button
                itemId -> {
                    dbHelper.deleteItem(itemId);
                    refreshGrid();
                },
                //edit button
                (id, name, sku, quantity) -> {
                    Intent intent = new Intent(
                            InventoryActivity.this, AddEditInventoryActivity.class);

                    //tells AddEditInventoryActivity which item we're editing
                    intent.putExtra("ITEM_ID", id);
                    intent.putExtra("ITEM_NAME", name);
                    intent.putExtra("ITEM_SKU", sku);
                    intent.putExtra("ITEM_QUANTITY", quantity);

                    startActivity(intent);

                },
                //view button
                (id, name, sku, quantity, description, supplierContact) -> {
                    Intent intent = new Intent(
                            InventoryActivity.this, AddEditInventoryActivity.class);

                    //tells AddEditInventoryActivity which item we're editing
                    intent.putExtra("ITEM_ID", id);
                    intent.putExtra("ITEM_NAME", name);
                    intent.putExtra("ITEM_SKU", sku);
                    intent.putExtra("ITEM_QUANTITY", quantity);
                    intent.putExtra("ITEM_DESC", description);
                    intent.putExtra("CONTACT_INFO", supplierContact);
                    intent.putExtra("VIEW_MODE", true); //sets it to view mode

                    startActivity(intent);
                }
        );
        recyclerView.setAdapter(adapter);
    }

    //to allow recyclerView to get the newest database information
    @Override
    protected void onResume() {
        super.onResume();

        if (adapter != null) {
            refreshGrid();
            loadFilterCategories();

        }
    }

    // opens new page with form
    public void onAddItemClicked(View view) {
        Intent intent = new Intent(
                InventoryActivity.this,
                AddEditInventoryActivity.class
        );

        startActivity(intent);
    }

    //to close cursor
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (cursor != null && !cursor.isClosed()) {
            cursor.close();
        }
        dbHelper.close();

        if (adapter != null) {
            adapter.swapCursor(null);
        }
    }

    //logic for category filter
    private void loadFilterCategories() {

        Cursor categoryCursor = dbHelper.getFilterCategories();

        SimpleCursorAdapter categoryAdapter = new SimpleCursorAdapter(
                this,
                android.R.layout.simple_spinner_item,
                categoryCursor,
                new String[]{InventoryDatabaseHelper.COL_CAT_NAME},
                new int[]{android.R.id.text1},
                0
        );

        categoryAdapter.setDropDownViewResource(
                android.R.layout.simple_spinner_dropdown_item
        );

        spinnerCategoryFilter.setAdapter(categoryAdapter);
    }


    //reload data into recyclerView
    private void refreshGrid() {
        Cursor newCursor = dbHelper.getAllItems();
        adapter.swapCursor(newCursor);
    }


    //method called when logout button is clicked
    public void onLogoutClicked(View view) {
        startActivity(new Intent(this, LoginActivity.class));
        finish();
    }
}
