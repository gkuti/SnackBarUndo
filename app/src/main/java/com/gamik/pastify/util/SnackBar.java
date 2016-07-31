package com.gamik.pastify.util;

import android.app.Activity;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;

import com.gamik.pastify.R;
import com.gamik.pastify.callback.UndoDeleteCallback;

public class SnackBar {
    Activity activity;
    UndoDeleteCallback undoDeleteCallback;

    public SnackBar(Activity activity, UndoDeleteCallback undoDeleteCallback) {
        this.activity = activity;
        this.undoDeleteCallback = undoDeleteCallback;
    }

    public void show(String message) {
        CoordinatorLayout coordinatorLayout  = (CoordinatorLayout) activity.findViewById(R.id.main);
        Snackbar snackbar = Snackbar.make(coordinatorLayout, message, Snackbar.LENGTH_SHORT)
                .setAction("UNDO", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //activity.finish();
                        undoDeleteCallback.onUndoClick();
                    }
                });
        snackbar.getView().addOnAttachStateChangeListener(new View.OnAttachStateChangeListener() {
            @Override
            public void onViewAttachedToWindow(View view) {
                Log.d("snac","attached");
            }

            @Override
            public void onViewDetachedFromWindow(View view) {
                Log.d("snac","detached");
            }
        });
        snackbar.show();
    }
}

