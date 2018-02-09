package com.pllug.course.tkachuk.basicandroidsocialapp.utils;

import android.content.Context;
import android.net.ConnectivityManager;

public class InternetConnection {
    //Checking internet connection
    public static boolean checkConnection(Context context){
        return ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE))
                .getActiveNetworkInfo()!=null;
    }
}
