package com.example.joannanorrismod3;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

//displays inventory items in a RecyclerView
public class InventoryAdapter extends RecyclerView.Adapter<InventoryAdapter.InventoryViewHolder> {
    private Context context;
    private Cursor cursor;
    private OnItemDeleteListener deleteListener;
    private OnUpdateQtyListener editlistener;


    //constructor
    public InventoryAdapter(Context context, Cursor cursor, OnItemDeleteListener listener, OnUpdateQtyListener updateQtyListener) {
        this.context = context;
        this.cursor = cursor;
        this.deleteListener = listener;
        this.editlistener = updateQtyListener;
    }

    public interface OnItemDeleteListener {
        void onItemDelete(int itemId);
    }

    public interface OnUpdateQtyListener {
        void onUpdateItemQuantity(int itemId, String name, String sku, int quantity);
    }

    @NonNull
    @Override
    public InventoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context)
                .inflate(R.layout.item_inventory_row, parent, false);
        return new InventoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull InventoryViewHolder holder, int position) {

        if (!cursor.moveToPosition(position)) {
            return;
        }

        //get data from cursor
        int id = cursor.getInt(cursor.getColumnIndexOrThrow(
                InventoryDatabaseHelper.COL_ID));
        String name = cursor.getString(cursor.getColumnIndexOrThrow(
                InventoryDatabaseHelper.COL_NAME));
        String sku = cursor.getString(cursor.getColumnIndexOrThrow(
                InventoryDatabaseHelper.COL_SKU));
        int quantity = cursor.getInt(cursor.getColumnIndexOrThrow(
                InventoryDatabaseHelper.COL_QUANTITY));

        //bind data to views
        holder.tvName.setText(name);
        holder.tvSku.setText("SKU: " + sku);
        holder.tvQuantity.setText("Qty: " + quantity);

        //delete button logic
        holder.btnDelete.setOnClickListener(v -> {
            if (deleteListener != null) {
                deleteListener.onItemDelete(id);
            }
        });

        //edit button logic
        holder.btnEditQty.setOnClickListener(v -> {
            if (editlistener != null) {
                editlistener.onUpdateItemQuantity(id, name, sku, quantity);
            }
        });
    }

    //get item count
    @Override
    public int getItemCount() {
        return cursor == null ? 0 : cursor.getCount();
    }

    //swap cursor when data changes
    public void swapCursor(Cursor newCursor) {
        if (cursor != null) {
            cursor.close();
        }
        cursor = newCursor;
        notifyDataSetChanged();
    }

    //viewHolder class
    static class InventoryViewHolder extends RecyclerView.ViewHolder {

        TextView tvName;
        TextView tvSku;
        TextView tvQuantity;
        Button btnDelete;
        Button btnEditQty;

        public InventoryViewHolder(@NonNull View itemView) {
            super(itemView);

            tvName = itemView.findViewById(R.id.tvItemName);
            tvSku = itemView.findViewById(R.id.tvItemSku);
            tvQuantity = itemView.findViewById(R.id.tvItemQty);
            btnDelete = itemView.findViewById(R.id.btnDeleteItem);
            btnEditQty = itemView.findViewById(R.id.btnEditQty);
        }
    }
}
