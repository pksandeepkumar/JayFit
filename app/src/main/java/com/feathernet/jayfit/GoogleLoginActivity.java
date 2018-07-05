package com.feathernet.jayfit;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.feathernet.jayfit.preferance.SavedPreferance;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;

public class GoogleLoginActivity extends BaseActivity implements View.OnClickListener {

    private static int SPLASH_TIME_OUT = 3000;

    GoogleSignInClient mGoogleSignInClient;
    final int RC_SIGN_IN = 12;
    Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_google_login);

        mContext = this;

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .requestProfile()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        SignInButton signInButton = (SignInButton)findViewById(R.id.sign_in_button);
        signInButton.setSize(SignInButton.SIZE_STANDARD);
        setGooglePlusButtonText(signInButton,"Login with Gmail");


        findViewById(R.id.sign_in_button).setOnClickListener(this);


    }


    protected void setGooglePlusButtonText(SignInButton signInButton, String buttonText) {
        // Find the TextView that is inside of the SignInButton and set its text
        for (int i = 0; i < signInButton.getChildCount(); i++) {
            View v = signInButton.getChildAt(i);

            if (v instanceof TextView) {
                TextView tv = (TextView) v;
                tv.setText(buttonText);
                return;
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.sign_in_button:
                signIn();
                break;
            // ...
        }
    }

    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }

    public void gotoDashboard(View view) {
        startActivity(MainActivity.class);
        finish();
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);

            String email = account.getEmail();
            String name = account.getDisplayName();
            Uri photo = account.getPhotoUrl();
            String idToken = account.getIdToken();
            account.getIdToken();
            Log.e("GoogleLogin","xcxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx");
            Log.e("GoogleLogin","email:" + email);
            Log.e("GoogleLogin","name:" + name);
            if(photo != null) {
                String photoUrl = photo.toString();
                SavedPreferance.setString(mContext,SavedPreferance.PHOTO_URL,photoUrl);
                Log.e("GoogleLogin","photoUrl:" + photoUrl);
            }
            SavedPreferance.setString(mContext,SavedPreferance.NAME,name);
            SavedPreferance.setString(mContext,SavedPreferance.EMAIL,email);
            SavedPreferance.setString(mContext,SavedPreferance.GMAIL_ACCESS_TOCKEN,idToken);
            Log.e("GoogleLogin","account.getIdToken();:" + account.getIdToken());
            SavedPreferance.setGoogleLogined(mContext, true);
            gotoDashboard(null);
        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
//            Log.w(TAG, "signInResult:failed code=" + e.getStatusCode());
//            updateUI(null);
        }
    }


}
