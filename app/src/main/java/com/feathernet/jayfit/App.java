package com.feathernet.jayfit;

import android.app.Application;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.feathernet.jayfit.database.DatabaseManager;
import com.feathernet.jayfit.database.DatabasesHelper;

public class App extends Application {

    private static App instance;

    public App() {
        this.instance = this;
        loadInitialTasks();
    }

    public static Context getInstance() {
        return instance;
    }

    private void loadInitialTasks() {
        DatabaseManager.initializeInstance(new DatabasesHelper(this));

    }

    public boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }


}