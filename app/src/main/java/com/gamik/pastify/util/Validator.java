package com.gamik.pastify.util;

import android.text.TextUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by kutigbolahan on 17/07/2016.
 */
public class Validator {
    public static boolean isValidEmail(CharSequence target) {
        if (TextUtils.isEmpty(target)) {
            return false;
        } else {
            return android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
        }
    }
    public static boolean isValidPassword(String password){
        Pattern pattern = Pattern.compile("((?=.*\\d)(?=.*[a-zA-Z])(?!.*\\s).{6,20})");
        Matcher matcher = pattern.matcher(password);
        return matcher.matches();
    }
}
