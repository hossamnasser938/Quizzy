package com.example.android.quizzy.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

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
        return password.length() >= Constants.MIN_PASSWORD;
    }

    /**
     * Checks if name is valid(consists of only letters)
     */
    public static boolean isValidName(String name){
        for(int i = 0; i < name.length(); i++){
            if(!((name.charAt(i) >= Constants.A_ASCICODE && name.charAt(i) <= Constants.Z_ASCICODE) || ((name.charAt(i) >= Constants.a_ASCICODE && name.charAt(i) <= Constants.z_ASCICODE))))
                return false;
        }
        return true;
    }

    /**
     * Checks if telephoneNumber is valid(consists of only numbers)
     */
    public static boolean isValidTelephoneNumber(String telephoneNumber){
        for(int i = 0; i < telephoneNumber.length(); i++){
            if(!((telephoneNumber.charAt(i) >= Constants.ZERO_ASCICODE && telephoneNumber.charAt(i) <= Constants.NINE_ASCICODE)))
                return false;
        }
        return true;
    }

    public static boolean checkEmptyInputs(String... inputs){
        for(String input : inputs){
            if(input.isEmpty())
                Log.d(TAG, "empty input");
                return false;
        }
        return true;
    }

}
