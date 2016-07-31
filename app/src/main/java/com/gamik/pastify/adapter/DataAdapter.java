package com.gamik.pastify.adapter;

import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gamik.pastify.model.DataItem;
import com.gamik.pastify.callback.DialogCallback;
import com.gamik.pastify.callback.ItemDeleteCallback;
import com.gamik.pastify.R;
import com.gamik.pastify.util.SnackBar;
import com.gamik.pastify.store.Store;
import com.gamik.pastify.callback.UndoDeleteCallback;
import com.gamik.pastify.dialog.AddDialog;
import com.kutigbolahan.snackbarundo.SnackBarUndo;
import com.kutigbolahan.snackbarundo.SnackBarUndoCallback;

import java.util.ArrayList;

/**
 * HistoryAdapter class
 */
public class DataAdapter extends RecyclerView.Adapter<DataAdapter.ViewHolder> {
    private ArrayList<DataItem> list;
    private Activity activity;
    private DialogCallback createCallback;
    private Store store;
    private SnackBar snackBar;
    private ItemDeleteCallback itemDeleteCallback;
    private ArrayList<DataItem> previousList;
    private DataItem removedItem;
    private int deletePosition;
    private boolean undo = false;
    SnackBarUndo snackBarUndo;

    /**
     * Constructor for HistoryAdapter class
     *
     * @param list arraylist of history
     */
    public DataAdapter(ArrayList<DataItem> list, Activity activity, DialogCallback createCallback, ItemDeleteCallback itemDeleteCallback) {
        this.list = list;
        this.activity = activity;
        this.createCallback = createCallback;
        store = new Store(activity);
        this.itemDeleteCallback = itemDeleteCallback;
        CoordinatorLayout coordinatorLayout = (CoordinatorLayout) activity.findViewById(R.id.main);
        snackBarUndo = new SnackBarUndo(list,coordinatorLayout,this);
    }

    /**
     * Called when RecyclerView needs a new ViewHolder of the given type to represent an item.
     */
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.main_data_item, parent, false);
        return new ViewHolder(itemView);
    }

    /**
     * Called by RecyclerView to display the data at the specified position.
     */
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        DataItem dataItem = list.get(position);
        holder.placeholder.setText(dataItem.getPlaceHolder());
        holder.value.setText(dataItem.getValue());
        holder.id = dataItem.getId();
        holder.usage.setText(String.valueOf(dataItem.getUsage()));
        holder.date.setText(dataItem.getDate());
    }

    /**
     * Returns the total number of items in the data set hold by the adapter.
     */
    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    /**
     * ViewHolder class
     */
    class ViewHolder extends RecyclerView.ViewHolder implements UndoDeleteCallback {
        private TextView placeholder;
        private TextView value;
        private TextView usage;
        private TextView date;
        private int id;

        /**
         * Constructor for ViewHolder class
         */
        public ViewHolder(final View itemView) {
            super(itemView);
            placeholder = (TextView) itemView.findViewById(R.id.textview);
            value = (TextView) itemView.findViewById(R.id.tx_value);
            usage = (TextView) itemView.findViewById(R.id.usage);
            date = (TextView) itemView.findViewById(R.id.tx_date);
            snackBar = new SnackBar(activity, this);
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(final View view) {
                    final PopupMenu popup = new PopupMenu(view.getContext(), view);
                    popup.inflate(R.menu.item_menu);
                    popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem item) {
                            switch (item.getItemId()) {
                                case R.id.action_edit:
                                    AddDialog dialog = new AddDialog();
                                    dialog.setCallback(createCallback);
                                    Bundle args = new Bundle();
                                    args.putString("placeholder", (String) placeholder.getText());
                                    args.putString("value", (String) value.getText());
                                    args.putInt("id", id);
                                    dialog.setArguments(args);
                                    dialog.show(((FragmentActivity) activity).getSupportFragmentManager(), "additem");
                                    break;
                                case R.id.action_delete:
                                    snackBarUndo.delete(getAdapterPosition(), new SnackBarUndoCallback() {
                                        @Override
                                        public void deleteData() {

                                        }
                                    });
                            }
                            return false;
                        }
                    });
                    popup.show();
                    return false;
                }
            });
        }

        @Override
        public void onUndoClick() {
            undo = true;
            list.add(deletePosition, removedItem);
            itemDeleteCallback.onItemDelete();
        }
    }
}
