package com.feathernet.jayfit;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.feathernet.jayfit.preferance.SavedPreferance;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;

/**
 * Created by sandeep on 06/04/18.
 */

public class ProfileActivity extends BaseActivity implements View.OnClickListener {

    GoogleSignInClient mGoogleSignInClient;
    final int RC_SIGN_IN = 12;

    ImageView imProfilePic;
    LinearLayout llSignIn;
    LinearLayout llProfileContent;
    TextView tvName;
    TextView tvEmail;
    boolean loginedNow = false;



    public static Context mContext = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        setContentView(R.layout.activity_profile);

        initView();

        loadProfileInfo();

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .requestProfile()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        SignInButton signInButton = (SignInButton)findViewById(R.id.sign_in_button);
        if(signInButton != null) {
            signInButton.setSize(SignInButton.SIZE_STANDARD);
            setGooglePlusButtonText(signInButton,"Login with Gmail");
            findViewById(R.id.sign_in_button).setOnClickListener(this);
        }


        loadProfileInfo();


    }

    private void initView() {
        imProfilePic = (ImageView) this.findViewById(R.id.imProfile);
        llSignIn = (LinearLayout) this.findViewById(R.id.llSignIn);
        llProfileContent = (LinearLayout) this.findViewById(R.id.llProfileContent);
        tvName = (TextView) this.findViewById(R.id.tvName);
        tvEmail = (TextView) this.findViewById(R.id.tvEmail);
    }

    @Override
    public void onBackPressed() {
        if(loginedNow) {
            Intent returnIntent = new Intent();
            setResult(Activity.RESULT_OK,returnIntent);
        }
        finish();

    }



    private void loadProfileInfo() {
        if(SavedPreferance.getGoogleLogined(this)) {
            llProfileContent.setVisibility(View.VISIBLE);
            llSignIn.removeAllViews();
            String imageUrl = SavedPreferance.getString(this, SavedPreferance.PHOTO_URL);
            if(imageUrl != null && imageUrl.length() > 50) {
                Glide.with(mContext).load(imageUrl).apply(RequestOptions.circleCropTransform()).into(imProfilePic);
//                Glide.with(mContext).load(imageUrl)
//                        .into(imProfilePic);
            }
            tvName.setText(SavedPreferance.getString(mContext, SavedPreferance.NAME));
            tvEmail.setText(SavedPreferance.getString(mContext, SavedPreferance.EMAIL));

        } else {
            llProfileContent.setVisibility(View.INVISIBLE);

        }
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

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        loginedNow = true;
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
            loadProfileInfo();
        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
//            Log.w(TAG, "signInResult:failed code=" + e.getStatusCode());
//            updateUI(null);
        }
    }




}
