package com.example.joannanorrismod3;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

//displays inventory items in a grid/list and allows users to add and delete items
public class InventoryActivity extends AppCompatActivity {
    private InventoryDatabaseHelper dbHelper;
    private InventoryAdapter adapter;

    private EditText etItemName;
    private EditText etItemQty;
    private EditText etSku;
    private Cursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grid);

        //initialize database
        dbHelper = new InventoryDatabaseHelper(this);

        //initialize the components of the UI
        etItemName = findViewById(R.id.etItemName);
        etItemQty = findViewById(R.id.etItemQty);
        etSku = findViewById(R.id.etSKU);

        RecyclerView recyclerView = findViewById(R.id.recyclerGrid);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));

        //initialize adapter with db cursor
        cursor = dbHelper.getAllItems();
        adapter = new InventoryAdapter(this, dbHelper.getAllItems(), itemId -> {
                    dbHelper.deleteItem(itemId);
                    refreshGrid();
        });
        recyclerView.setAdapter(adapter);
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

    //method called when "Add" button is clicked
    public void onAddItemClicked(View view) {

        String name = etItemName.getText().toString().trim();
        String qtyText = etItemQty.getText().toString().trim();
        String sku = etSku.getText().toString().trim();

        //validate input
        if (name.isEmpty() || qtyText.isEmpty()) {
            Toast.makeText(this, "Item name and quantity are required", Toast.LENGTH_SHORT).show();
            return;
        }

        int quantity;
        try {
            quantity = Integer.parseInt(qtyText);
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Quantity must be a number", Toast.LENGTH_SHORT).show();
            return;
        }

        //to insert item into database
        boolean success = dbHelper.addItem(name, sku, quantity);

        if (success) {
            Toast.makeText(this, "Item added", Toast.LENGTH_SHORT).show();
            clearInputFields();
            refreshGrid();
        } else {
            Toast.makeText(this, "Failed to add item", Toast.LENGTH_SHORT).show();
        }
    }

    //reload data into recyclerView
    private void refreshGrid() {
        Cursor newCursor = dbHelper.getAllItems();
        adapter.swapCursor(newCursor);
    }

    //clear input fields
    private void clearInputFields() {
        etItemName.setText("");
        etItemQty.setText("");
        etSku.setText("");
    }
}
