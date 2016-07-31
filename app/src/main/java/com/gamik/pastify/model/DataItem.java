package com.gamik.pastify.model;

public class DataItem {
    private String placeHolder;
    private String value;
    private int usage;
    private int id;
    private String date;

    public DataItem(String placeHolder, String value, int id, int usage, String date) {
        this.placeHolder = placeHolder;
        this.value = value;
        this.id = id;
        this.usage = usage;
        this.date = date;
    }

    public String getValue() {
        return value;
    }

    public String getPlaceHolder() {
        return placeHolder;
    }

    public int getId() {
        return id;
    }

    public int getUsage() {
        return usage;
    }

    public String getDate() {
        return date;
    }
}
