package com.feathernet.jayfit;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;

import com.feathernet.jayfit.preferance.SavedPreferance;

public class SplashScreen extends BaseActivity {

    private static int SPLASH_TIME_OUT = 3000;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashscreen);
        context = this;
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                if(!SavedPreferance.getBoolean(context,SavedPreferance.INTRO_SKIPPED)) {
                    startActivity(WelcomeActivity.class);
                    finish();
                } else if(SavedPreferance.getGoogleLogined(context)) {
                    startActivity(MainActivity.class);
                    finish();
                } else {
                    startActivity(GoogleLoginActivity.class);
                    finish();
                }

            }
        }, SPLASH_TIME_OUT);

    }


}
