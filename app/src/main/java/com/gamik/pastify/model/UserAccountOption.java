package com.gamik.pastify.model;

/**
 * Created by kutigbolahan on 18/07/2016.
 */
public class UserAccountOption {
    private int imageId;
    private String value;

    public UserAccountOption(int imageId, String value) {
        this.imageId = imageId;
        this.value = value;
    }

    public int getImageId() {
        return imageId;
    }

    public String getValue() {
        return value;
    }
}
