package com.gamik.pastify.activity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;

import com.firebase.client.Firebase;
import com.gamik.pastify.R;
import com.gamik.pastify.util.ScrollListener;
import com.gamik.pastify.store.Store;
import com.gamik.pastify.adapter.DataAdapter;
import com.gamik.pastify.callback.DialogCallback;
import com.gamik.pastify.callback.ItemDeleteCallback;
import com.gamik.pastify.dialog.AddDialog;
import com.gamik.pastify.model.DataItem;
import com.gamik.pastify.store.DataStore;
import com.gamik.pastify.util.Constant;
import com.gamik.pastify.util.Decorator;

import java.util.ArrayList;

import it.gmariotti.recyclerview.adapter.AlphaAnimatorAdapter;
import wei.mark.standout.StandOutWindow;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, CompoundButton.OnCheckedChangeListener, ItemDeleteCallback {
    private DataStore dataStore;
    private Store store;
    private ArrayList<DataItem> dataItemArrayList = new ArrayList<>();
    private RecyclerView recyclerView;
    private DataAdapter dataAdapter;
    private DrawerLayout drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dataStore = new DataStore(this);
        store = new Store(this);
        initViews();
    }

    private void firebaseActivity() {
        Firebase firebase = new Firebase(Constant.FIREBASE_URL);
        firebase.child("gamik101").setValue(dataItemArrayList.get(1));
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.drawer, menu);
        return true;
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_profile) {
            Intent intent = new Intent(this,UserAccountActivity.class);
            startActivity(intent);
        }
//        else if (id == R.id.nav_gallery) {
//
//        } else if (id == R.id.nav_slideshow) {
//
//        } else if (id == R.id.nav_manage) {
//
//        } else if (id == R.id.nav_share) {
//
//        } else if (id == R.id.nav_send) {
//
//        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void onCheckedChanged(CompoundButton compoundButton, boolean checked) {
        if (checked) {
            StandOutWindow.show(this, OverLayIconActivity.class, StandOutWindow.DEFAULT_ID);
        } else {
            StandOutWindow.closeAll(this, OverLayIconActivity.class);
        }
    }

    private DialogCallback dialogCallback = new DialogCallback() {
        @Override
        public void onItemAdded() {
            loadData();
        }
    };

    private void loadData() {
        dataItemArrayList.clear();
        Cursor cursor = store.getAllData();
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            String placeHolder = cursor.getString(cursor.getColumnIndex("PlaceHolder"));
            String value = cursor.getString(cursor.getColumnIndex("Value"));
            int id = cursor.getInt(cursor.getColumnIndex("_id"));
            int usage = cursor.getInt(cursor.getColumnIndex("Usage"));
            String date = cursor.getString(cursor.getColumnIndex("Date"));
            DataItem dataItem = new DataItem(placeHolder, value, id, usage,date);
            dataItemArrayList.add(dataItem);
            cursor.moveToNext();
        }
        //dataAdapter = new DataAdapter(dataItemArrayList, this, dialogCallback, this);
        dataAdapter.notifyDataSetChanged();
    }

    private void initViews() {
        Switch onSwitch = (Switch) findViewById(R.id.switch_on);
        onSwitch.setOnCheckedChangeListener(this);
        final FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddDialog dialog = new AddDialog();
                dialog.setCallback(dialogCallback);
                dialog.show(getSupportFragmentManager(), "additem");
                //firebaseActivity();
            }
        });
        dataAdapter = new DataAdapter(dataItemArrayList, this, dialogCallback, this);
        recyclerView = (RecyclerView) findViewById(R.id.data);
        AlphaAnimatorAdapter animatorAdapter = new AlphaAnimatorAdapter(dataAdapter, recyclerView);
        recyclerView.setAdapter(animatorAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new Decorator(this,20.0f,true));
        loadData();
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        recyclerView.addOnScrollListener(new ScrollListener(fab));
        fab.show();
    }

    @Override
    public void onItemDelete() {
        dataAdapter.notifyDataSetChanged();
    }
}
