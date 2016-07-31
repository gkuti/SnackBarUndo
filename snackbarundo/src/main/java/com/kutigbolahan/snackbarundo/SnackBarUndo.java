package com.kutigbolahan.snackbarundo;

import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;

public class SnackBarUndo {
    ArrayList list;
    CoordinatorLayout coordinatorLayout;
    boolean undo;
    RecyclerView.Adapter adapter;

    public SnackBarUndo(ArrayList list, CoordinatorLayout coordinatorLayout, RecyclerView.Adapter adapter) {
        this.list = list;
        this.coordinatorLayout = coordinatorLayout;
        this.adapter = adapter;
    }

    public void delete(final int deletePosition, final SnackBarUndoCallback snackBarUndoCallback) {
        undo = false;
        final Object removedItem = list.remove(deletePosition);
        Snackbar snackbar = Snackbar.make(coordinatorLayout, "you've deleted ", Snackbar.LENGTH_LONG)
                .setAction("UNDO", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //activity.finish();
                        onUndoClick(deletePosition, removedItem);
                    }
                });
        snackbar.getView().

                addOnAttachStateChangeListener(new View.OnAttachStateChangeListener() {
                    @Override
                    public void onViewAttachedToWindow(View view) {
                        Log.d("snac", "attached");
                    }

                    @Override
                    public void onViewDetachedFromWindow(View view) {
                        if (!undo) {
                            snackBarUndoCallback.deleteData();
                            Log.d("Undo","detached");
                        }
                    }
                });
        snackbar.show();
        adapter.notifyDataSetChanged();
    }

    private void onUndoClick(int deletePosition, Object removedItem) {
        undo = true;
        list.add(deletePosition, removedItem);
        adapter.notifyDataSetChanged();
    }
}
