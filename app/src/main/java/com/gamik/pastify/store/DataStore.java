package com.gamik.pastify.store;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashMap;

/**
 * UserData class
 */
public class DataStore {
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    /**
     * Constructor for UserData class
     *
     * @param context the activity context
     */
    public DataStore(Context context) {
        sharedPreferences = context.getSharedPreferences("paste", 0);
        editor = sharedPreferences.edit();
    }

    /**
     * method for storing data to shared preferences
     *
     * @param key   the key
     * @param value the value to map to the key
     */
    public void saveData(String key, String value) {
        editor.putString(key, value);
        editor.commit();
    }

    public String getData(String key) {
        return sharedPreferences.getString(key, "");

    }

    public HashMap<String, String> getAll() {

        return (HashMap<String, String>) sharedPreferences.getAll();
    }
}
