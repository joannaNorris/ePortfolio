package com.example.joannanorrismod3;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.Toast;


public class AddEditInventoryActivity extends AppCompatActivity {

    private InventoryDatabaseHelper dbHelper;
    private Spinner spinnerCategory;
    private Spinner spinnerSupplier;

    private EditText etItemName;
    private EditText etItemQty;
    private EditText etSku;
    private EditText etDesc;
    private EditText etNewCategory;
    private EditText etNewSupplier;
    private EditText etSupplierContact;

    private int editingItemId = -1;
    private Button btnAddItem;

    private Button btnAddCategory;
    private Button btnAddSupplier;


    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_edit_inventory);

        dbHelper = new InventoryDatabaseHelper(this);

        btnAddItem = findViewById(R.id.btnAddItem);
        btnAddItem.setText("Add");

        //initialize input fields
        etItemName = findViewById(R.id.etItemName);
        etItemQty = findViewById(R.id.etItemQty);
        etSku = findViewById(R.id.etSKU);
        etDesc = findViewById(R.id.etDesc);
        etNewCategory = findViewById(R.id.etNewCategory);
        etNewSupplier = findViewById(R.id.etNewSupplier);
        etSupplierContact = findViewById(R.id.etSupplierContact);

        //initialize buttons
        btnAddCategory = findViewById(R.id.btnAddCategory);
        btnAddSupplier = findViewById(R.id.btnAddSupplier);


        //initialize dropdowns
        spinnerCategory = findViewById(R.id.spinnerCategory);
        spinnerSupplier = findViewById(R.id.spinnerSupplier);

        loadCategories();
        loadSuppliers();

        if (getIntent().hasExtra("ITEM_ID")) { //edit mode
            editingItemId = getIntent().getIntExtra("ITEM_ID", -1);
            String description = getIntent().getStringExtra("ITEM_DESC");

            etItemName.setText(getIntent().getStringExtra("ITEM_NAME"));
            etSku.setText(getIntent().getStringExtra("ITEM_SKU"));
            etItemQty.setText(String.valueOf(
                    getIntent().getIntExtra("ITEM_QUANTITY", 0)
            ));
            etSupplierContact.setText(getIntent().getStringExtra("CONTACT_INFO"));
            etDesc.setText(getIntent().getStringExtra("ITEM_DESC"));

            boolean viewMode = getIntent().getBooleanExtra("VIEW_MODE", false);

            if (viewMode) {
                //View mode: disable editing
                etItemName.setEnabled(false);
                etSku.setEnabled(false);
                etItemQty.setEnabled(false);
                etDesc.setEnabled(false);
                etSupplierContact.setEnabled(false);
                spinnerCategory.setEnabled(false);
                spinnerSupplier.setEnabled(false);

                btnAddItem.setText("Done");

                //disable views
                btnAddCategory.setVisibility(View.GONE);
                btnAddSupplier.setVisibility(View.GONE);
                etNewSupplier.setVisibility(View.GONE);
                etNewCategory.setVisibility(View.GONE);
            }
            else { //Edit mode
                btnAddItem.setText("Update");
            }
        }
    }

    private void loadCategories() {

        //to make the category dropdown work
        Cursor categoryCursor = dbHelper.getAllCategories();

        SimpleCursorAdapter categoryAdapter = new SimpleCursorAdapter(
                this,
                android.R.layout.simple_spinner_item,
                categoryCursor,
                new String[]{InventoryDatabaseHelper.COL_CAT_NAME},
                new int[]{android.R.id.text1},
                0
        );

        categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinnerCategory.setAdapter(categoryAdapter);
    }

    private void loadSuppliers() {
        //to make the supplier dropdown work
        Cursor supplierCursor = dbHelper.getAllSuppliers();

        SimpleCursorAdapter supplierAdapter = new SimpleCursorAdapter(
                this,
                android.R.layout.simple_spinner_item,
                supplierCursor,
                new String[]{InventoryDatabaseHelper.COL_SUP_NAME},
                new int[]{android.R.id.text1},
                0
        );

        supplierAdapter.setDropDownViewResource(
                android.R.layout.simple_spinner_dropdown_item
        );

        spinnerSupplier.setAdapter(supplierAdapter);
    }

    //method called when "Add" button is clicked
    @SuppressLint("SetTextI18n")
    public void onAddItemClicked(View view) {

        String qtyText = etItemQty.getText().toString().trim();

        int quantity;
        try { //ensure quantity is a number
            quantity = Integer.parseInt(qtyText);
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Quantity must be a number", Toast.LENGTH_SHORT).show();
            return;
        }

        if (editingItemId != -1){ //if in edit mode

            boolean success = dbHelper.updateItemQuantity(editingItemId, quantity);


            if (success && btnAddItem.getText().toString().equals("Update")) {
                Toast.makeText(this,"Update Successful", Toast.LENGTH_SHORT).show();
            }
            //return to base
            editingItemId = -1;

            btnAddItem.setText("Add");

            etItemName.setEnabled(true);
            etSku.setEnabled(true);

            clearInputFields();
            finish(); //returns to inventory view

        }
        else { //add new item
            String name = etItemName.getText().toString().trim();
            String sku = etSku.getText().toString().trim();
            String desc = etDesc.getText().toString().trim();

            int categoryId = (int) spinnerCategory.getSelectedItemId();
            int supplierId = (int) spinnerSupplier.getSelectedItemId();

            //validate input
            if (name.isEmpty() || qtyText.isEmpty()) {
                Toast.makeText(this, "Item name and quantity are required", Toast.LENGTH_SHORT).show();
                return;
            }

            //to insert item into database
            long itemId = dbHelper.addItem(
                    name,
                    sku,
                    quantity,
                    desc,
                    categoryId,
                    supplierId
            );

            if (itemId != -1) {

                dbHelper.addTransaction(
                        (int) itemId,
                        "ADD",
                        quantity,
                        "Initial stock"
                );

                Toast.makeText(this, "Item added", Toast.LENGTH_SHORT).show();

            } else {
                Toast.makeText(this, "Failed to add item", Toast.LENGTH_SHORT).show();
            }

            //return to inventory view
            finish();
        }

    }

    //add category button logic
    public void onAddCategoryClicked(View view) {
        String categoryName = etNewCategory.getText().toString().trim();

        //ensure text is not empty
        if (categoryName.isEmpty()) {
            Toast.makeText(this, "Enter a category name", Toast.LENGTH_SHORT).show();
            return;
        }

        long categoryId = dbHelper.addCategory(categoryName);

        if (categoryId != -1) {
            Toast.makeText(this, "Category added", Toast.LENGTH_SHORT).show();

            etNewCategory.setText("");

            loadCategories();

        } else {
            Toast.makeText(this, "Failed to add category", Toast.LENGTH_SHORT).show();
        }
    }

    //add supplier button logic
    public void onAddSupplierClicked(View view) {
        String supplierName = etNewSupplier.getText().toString().trim();
        String contactInfo = etSupplierContact.getText().toString().trim();

        //ensure text is not empty
        if (supplierName.isEmpty()) {
            Toast.makeText(this, "Enter a supplier name", Toast.LENGTH_SHORT).show();
            return;
        }

        long supplierId = dbHelper.addSupplier(supplierName, contactInfo);

        if (supplierId != -1) {
            Toast.makeText(this, "Supplier added", Toast.LENGTH_SHORT).show();

            etNewSupplier.setText("");
            etSupplierContact.setText("");

            loadSuppliers();

        } else {
            Toast.makeText(this, "Failed to add supplier", Toast.LENGTH_SHORT).show();
        }
    }


    //clear input fields
    private void clearInputFields() {
        etItemName.setText("");
        etItemQty.setText("");
        etSku.setText("");
        etDesc.setText("");
    }

}