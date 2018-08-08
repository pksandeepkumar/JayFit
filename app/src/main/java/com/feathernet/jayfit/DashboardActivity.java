package com.feathernet.jayfit;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.feathernet.jayfit.adapters.SlideAdapter;
import com.feathernet.jayfit.controlls.VideoListItem;
import com.feathernet.jayfit.database.DatabasesHelper;
import com.feathernet.jayfit.dialog.ProgressDialog;
import com.feathernet.jayfit.models.Category;
import com.feathernet.jayfit.models.SubCategory;
import com.feathernet.jayfit.models.Videos;
import com.feathernet.jayfit.preferance.SavedPreferance;
import com.feathernet.jayfit.rest.pojos.videosall.GetAllVideosPOJO;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import me.relex.circleindicator.CircleIndicator;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DashboardActivity extends BaseActivity {

    public static String TAG = "MainActivity";
    public static final int REQ_CODE = 1234;

    int currentPage = 0;
    Timer timer;
    final long DELAY_MS = 500;//delay in milliseconds before task is to be executed
    final long PERIOD_MS = 3000; // time in milliseconds between successive task executions.
    ViewPager viewPager;

    Context mContext;
    LinearLayout llVideoHolder;
    ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        mContext = this;
        loadSliders();
        isWriteStoragePermissionGranted();
        loadAllVideos();
        loadProfilePhoto();
    }

    private void showPogress() {
        if(progressDialog == null) {
            progressDialog = new ProgressDialog(this);
        }
        progressDialog.show();
    }

    private void hideProgress() {
        if(progressDialog == null) return;
        progressDialog.dismiss();
    }

    private void initViews() {

    }

    private void loadProfilePhoto() {
        ImageView imProfilePic = (ImageView) findViewById(R.id.imProfile);
        if(SavedPreferance.getGoogleLogined(this)) {
            String imageUrl = SavedPreferance.getString(this, SavedPreferance.PHOTO_URL);
            if(imageUrl != null && imageUrl.length() > 50) {
                Glide.with(mContext).load(imageUrl).apply(RequestOptions.circleCropTransform()).into(imProfilePic);
            }
        }
    }

    public void gotoProfile(View view) {
        Intent i = new Intent(this, ProfileActivity.class);
        startActivityForResult(i, REQ_CODE);
//        startActivity(ProfileActivity.class);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == REQ_CODE) {
            if(resultCode == Activity.RESULT_OK){
                loadProfilePhoto();
            }
        }
    }

    public void loadSliders() {
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        SlideAdapter adapter = new SlideAdapter(this);
        viewPager.setAdapter(adapter);

        //Bind the title indicator to the adapter
        CircleIndicator titleIndicator = (CircleIndicator) findViewById(R.id.indicator);
        titleIndicator.setViewPager(viewPager);


        final Handler handler = new Handler();
        final Runnable Update = new Runnable() {
            public void run() {
                if (currentPage == 15 - 1) {
                    currentPage = 0;
                }
                viewPager.setCurrentItem(currentPage++, true);
            }
        };

        timer = new Timer(); // This will create a new Thread
        timer.schedule(new TimerTask() { // task to be scheduled

            @Override
            public void run() {
                handler.post(Update);
            }
        }, DELAY_MS, PERIOD_MS);

    }

    public void loadAllVideos() {
        showPogress();
        com.feathernet.jayfit.models.Category category = new com.feathernet.jayfit.models.Category();
        Call<GetAllVideosPOJO> call = category.getAllVideos();
        call.enqueue(new Callback<GetAllVideosPOJO>() {
            @Override
            public void onResponse(Call<GetAllVideosPOJO> call,
                                   Response<GetAllVideosPOJO> response) {
                DatabasesHelper dbHelper = new DatabasesHelper(mContext);
                Category.insertFromPOJO(dbHelper, response.body());
                dbHelper.close();
                populateVideos();
                hideProgress();
            }
            @Override
            public void onFailure(Call<GetAllVideosPOJO> call, Throwable t) {
                hideProgress();
            }

        });
    }

    public void populateVideos() {
        llVideoHolder = (LinearLayout) this.findViewById(R.id.llVideoHolder);
        DatabasesHelper helper = new DatabasesHelper(mContext);
        ArrayList<Category> categories = Category.getAllObjects(helper);
        if(categories != null) {
            for( Category category: categories) {
                ArrayList<SubCategory> subCategories = SubCategory.getAllObjectsUnderCategory(helper, category.categoryID);
                category.subCategories = subCategories;
                if( subCategories != null) {
                    for( SubCategory subCategory : subCategories) {
                        ArrayList<Videos> videosList = Videos.getAllVideosUnderSubCategory(helper, subCategory.subcategoryID);
                        subCategory.videos = videosList;
                    }
                }
            }
        }
        helper.close();

        if(categories != null) {
            for( Category category: categories) {
                VideoListItem videoListItem = new VideoListItem(mContext, category);
                llVideoHolder.addView(videoListItem);
            }
        }

    }


    public boolean isWriteStoragePermissionGranted() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {
                Log.v(TAG, "Permission is granted2");
                return true;
            } else {

                Log.v(TAG, "Permission is revoked2");
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 2);
                return false;
            }
        } else { //permission is automatically granted on sdk<23 upon installation
            Log.v(TAG, "Permission is granted2");
            return true;
        }
    }
}
