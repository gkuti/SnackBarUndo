package com.gamik.pastify.activity;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

import com.gamik.pastify.R;
import com.gamik.pastify.store.Store;
import com.gamik.pastify.adapter.HistoryAdapter;
import com.gamik.pastify.model.DataItem;
import com.gamik.pastify.util.Decorator;

import java.util.ArrayList;

import wei.mark.standout.StandOutWindow;
import wei.mark.standout.constants.StandOutFlags;
import wei.mark.standout.ui.Window;

public class OverLayListActivity extends StandOutWindow {

    private Store store;
    private ArrayList<DataItem> dataItemArrayList = new ArrayList<>();

    @Override
    public String getAppName() {
        return "OverLayListActivity";
    }

    @Override
    public int getAppIcon() {
        return R.drawable.ic_paste_special_96;
    }

    @Override
    public void createAndAttachView(int id, FrameLayout frame) {
        // create a new layout from body.xml
        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.activity_overlay_list, frame, true);
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.view);
        store = new Store(this);
        Cursor cursor = store.getAllData();
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            String placeHolder = cursor.getString(cursor.getColumnIndex("PlaceHolder"));
            String value = cursor.getString(cursor.getColumnIndex("Value"));
            int valueId = cursor.getInt(cursor.getColumnIndex("_id"));
            int usage = cursor.getInt(cursor.getColumnIndex("Usage"));
            String date = cursor.getString(cursor.getColumnIndex("Date"));
            DataItem dataItem = new DataItem(placeHolder, value, valueId, usage, date);
            dataItemArrayList.add(dataItem);
            cursor.moveToNext();
        }
        HistoryAdapter historyAdapter = new HistoryAdapter(dataItemArrayList, this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new Decorator(this, 20.0f, true));
        recyclerView.setAdapter(historyAdapter);
    }

    // the window will be centered
    @Override
    public StandOutLayoutParams getParams(int id, Window window) {
        return new StandOutLayoutParams(id, 500, 500,
                StandOutLayoutParams.CENTER, StandOutLayoutParams.CENTER);
    }

    // move the window by dragging the view
    @Override
    public int getFlags(int id) {
        return super.getFlags(id) | StandOutFlags.FLAG_BODY_MOVE_ENABLE
                | StandOutFlags.FLAG_WINDOW_FOCUSABLE_DISABLE;
    }

    @Override
    public String getPersistentNotificationMessage(int id) {
        return "Click to close the OverLayListActivity";
    }

    @Override
    public Intent getPersistentNotificationIntent(int id) {
        return StandOutWindow.getCloseIntent(this, OverLayListActivity.class, id);
    }
}
