package com.feathernet.jayfit;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by sandeep on 30/03/18.
 */

public class BaseActivity extends AppCompatActivity {

    public void startActivity(Class cls) {
        Intent intent = new Intent(this, cls);
        startActivity(intent);
    }
}
