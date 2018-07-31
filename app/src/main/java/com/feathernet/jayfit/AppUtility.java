package com.feathernet.jayfit;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.NumberFormat;
import java.util.ArrayList;


/**
 * This class includes basic utility functions
 */
public class AppUtility {

    public static String getDeviceID(Context context) {
//        return  "rrrrr";
        return  "c4da0c5bcca7522";
//        return  "8d5402ba53307cc";

//        return Settings.Secure.getString( context.getContentResolver(),
//                Settings.Secure.ANDROID_ID);
    }

    public static String readFromAssets(String filename, Context context) {
        String content = "";

        BufferedReader br = null;
        try {
            br = new BufferedReader(new InputStreamReader(context.getAssets().open(filename))); //throwing a FileNotFoundException?
            String word;
            while ((word = br.readLine()) != null)
                content = content + word;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                br.close(); //stop reading
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return content;
    }

    public static int parseInt(String number) {
        int num = 0;
        try {
            num = Integer.parseInt(number);
        } catch (Exception e) {
            // TODO: handle exception
        }
        return num;
    }

    public static float parseFloat(String number) {
        float num = 0;
        try {
            num = Float.parseFloat(number);
        } catch (Exception e) {
            // TODO: handle exception
        }
        return num;
    }

    public static long parseLong(String number) {
        long num = 0;
        try {
            num = Long.parseLong(number);
        } catch (Exception e) {
            // TODO: handle exception
        }
        return num;
    }

    public static double parseDouble(String number) {
        double num = 0;
        try {
            num = Double.parseDouble(number);
        } catch (Exception e) {
            // TODO: handle exception
        }
        return num;
    }

    public static boolean parseBoolean(String value) {
        boolean boolValue = false;
        try {
            boolValue = Boolean.parseBoolean(value.toLowerCase());
        } catch (Exception e) {
        }
        return boolValue;
    }

    public static boolean parseBoolean(Integer value) {
        if( value == null) return false;
        if( value != 0) return true;
        return false;
    }


    /**
     * Format as rounded value, example- 2.321 => 2.32
     *
     * @param value
     * @return
     */
    public static String formatValue(float value) {

        NumberFormat formatter = NumberFormat.getNumberInstance();
        formatter.setMinimumFractionDigits(2);
        formatter.setMaximumFractionDigits(2);
        String output = formatter.format(value);
        return output;
    }

    public static int dip2px(Context context, float dp) {
        float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }
    public static boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager)
                App.getInstance().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    public static String[] toStringArray(ArrayList<String> list) {
        String[] stringArr = {};
        if( list == null ) return stringArr;
        stringArr = list.toArray(new String[list.size()]);
        return  stringArr;
    }

    public static boolean ifEqual(String first, String second) {
        if(first == null & second == null) return true;
        if(first == null) return false;
        if(second == null) return false;
        return first.equals(second);
    }

    public static String setPadding(int number) {
        if( number <= 9) return "0" + number;
        else return  "" + number;
    }



}
