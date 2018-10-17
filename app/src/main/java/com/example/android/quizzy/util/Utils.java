package com.example.android.quizzy.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class Utils {

    private final static String TAG = "Utils";

    /**
     * checks if the device is connected to the internet or not
     * @param context
     * @return
     */
    public static boolean isNetworkConnected(Context context){
        ConnectivityManager connectivityManager
                = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    /**
     * checks if email is in valid format
     * Got from "https://codereview.stackexchange.com/questions/33546/simple-code-to-check-format-of-user-inputted-email-address"
     */
    public static boolean isValidEmail(String email){
        return email.matches("[a-zA-Z0-9\\.]+@[a-zA-Z0-9\\-\\_\\.]+\\.[a-zA-Z0-9]{3}");
    }

    /**
     * checks if password is valid(6 characters or more)
     */
    public static boolean isValidPassword(String password){
        return password.length() >= 6;
    }

    /**
     * Checks if name is valid(consists of only letters)
     */
    public static boolean isValidName(String name){
        for(int i = 0; i < name.length(); i++){
            if(!((name.charAt(i) >= 65 && name.charAt(i) <= 90) || ((name.charAt(i) >= 97 && name.charAt(i) <= 122))))
                return false;
        }
        return true;
    }

    /**
     * checks if age is valid(larger than zero and not floating point value)
     * @param age
     */
    public static boolean isValidAge(Double age){
        if(age <= 0.0 || (age - age.intValue()) != 0.0) {
            return false;
        }
        return true;
    }

}
