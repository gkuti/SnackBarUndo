package com.gamik.pastify.util;

import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.RecyclerView;

/**
 * Created by andela-jugba on 6/14/16.
 */
public class ScrollListener extends RecyclerView.OnScrollListener {
    private FloatingActionButton fab;

    public ScrollListener(FloatingActionButton fab) {
        this.fab = fab;
    }

    public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
        switch (newState) {
            case RecyclerView.SCROLL_STATE_IDLE:
                fab.show();
                break;
            case RecyclerView.SCROLL_STATE_SETTLING:
                fab.show();
                break;
            case RecyclerView.SCROLL_STATE_DRAGGING:
                fab.hide();
                break;

        }

    }

    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
//        if (dy > 2) {
//            fab.hide();
//        } else if (dy < 2) {
//            fab.hide();
//        }
    }
}
