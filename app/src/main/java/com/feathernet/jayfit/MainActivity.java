package com.feathernet.jayfit;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.feathernet.jayfit.adapters.SlideAdapter;
import com.feathernet.jayfit.adapters.VideoListAdapter;
import com.feathernet.jayfit.controlls.HeadderControll;
import com.feathernet.jayfit.controlls.HeadderControllLogin;
import com.feathernet.jayfit.controlls.VideoListItem;
import com.feathernet.jayfit.database.DatabasesHelper;
import com.feathernet.jayfit.models.Category;
import com.feathernet.jayfit.models.SubCategory;
import com.feathernet.jayfit.models.Videos;
import com.feathernet.jayfit.preferance.SavedPreferance;
import com.feathernet.jayfit.rest.pojos.videosall.GetAllVideosPOJO;
import com.feathernet.jayfit.rest.pojos.videosall.Video;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import me.relex.circleindicator.CircleIndicator;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    public static String TAG = "MainActivity";

    int currentPage = 0;
    Timer timer;
    final long DELAY_MS = 500;//delay in milliseconds before task is to be executed
    final long PERIOD_MS = 3000; // time in milliseconds between successive task executions.
    ViewPager viewPager;

    NavigationView navigationView;
    Context mContext;
    LinearLayout llVideoHolder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContext = this;
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        loadSliders();
//        loadVidHorizontalList();
//        loadVidHorizontalList2();
//        loadVidHorizontalList3();
        isWriteStoragePermissionGranted();


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        loadUser();

        loadAllVideos();
    }

    public void loadUser() {
        View headerView = navigationView.getHeaderView(0);
        LinearLayout llHeadderView = (LinearLayout) headerView.findViewById(R.id.llHeadderView);
        if (llHeadderView == null) return;

        if (SavedPreferance.getBoolean(this, SavedPreferance.GOOGLE_LOGINED)) {
            HeadderControll headderControll = new HeadderControll(this);
            llHeadderView.removeAllViews();
            llHeadderView.addView(headderControll);

        } else {
            HeadderControllLogin headderControllLogin = new HeadderControllLogin(this);
            llHeadderView.removeAllViews();
            llHeadderView.addView(headderControllLogin);


        }


//        Glide.with(this).load(SavedPreferance.getString(this,SavedPreferance.PHOTO_URL))
////                .thumbnail(0.5f)
//                .crossFade()
////                .centerCrop()
////                .fitCenter()
//                .diskCacheStrategy(DiskCacheStrategy.ALL)
//                .into(imProfilePic);

//        Glide.with(this).load(SavedPreferance.getString(this,SavedPreferance.PHOTO_URL))
//                .asBitmap().centerCrop().into(new BitmapImageViewTarget(imProfilePic) {
//            @Override
//            protected void setResource(Bitmap resource) {
//                RoundedBitmapDrawable circularBitmapDrawable =
//                        RoundedBitmapDrawableFactory.create(mContext.getResources(), resource);
//                circularBitmapDrawable.setCircular(true);
//                imProfilePic.setImageDrawable(circularBitmapDrawable);
//            }
//        });


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
            }
            @Override
            public void onFailure(Call<GetAllVideosPOJO> call, Throwable t) {

//                thisMethodEnds();
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

//    public void loadVidHorizontalList() {
//        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.idRecyclerViewHorizontalList);
//        recyclerView.addItemDecoration(new DividerItemDecoration(MainActivity.this, LinearLayoutManager.HORIZONTAL));
//        VideoListAdapter adapter = new VideoListAdapter(getApplicationContext());
//        LinearLayoutManager horizontalLayoutManager = new LinearLayoutManager(MainActivity.this, LinearLayoutManager.HORIZONTAL, false);
//        recyclerView.setLayoutManager(horizontalLayoutManager);
//        recyclerView.setAdapter(adapter);
//
//    }

//    public void loadVidHorizontalList2() {
//        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.idRecyclerViewHorizontalList2);
//        recyclerView.addItemDecoration(new DividerItemDecoration(MainActivity.this, LinearLayoutManager.HORIZONTAL));
//        VideoListAdapter adapter = new VideoListAdapter(getApplicationContext());
//        LinearLayoutManager horizontalLayoutManager = new LinearLayoutManager(MainActivity.this, LinearLayoutManager.HORIZONTAL, false);
//        recyclerView.setLayoutManager(horizontalLayoutManager);
//        recyclerView.setAdapter(adapter);
//
//    }

//    public void loadVidHorizontalList3() {
//        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.idRecyclerViewHorizontalList3);
//        recyclerView.addItemDecoration(new DividerItemDecoration(MainActivity.this, LinearLayoutManager.HORIZONTAL));
//        VideoListAdapter adapter = new VideoListAdapter(getApplicationContext());
//        LinearLayoutManager horizontalLayoutManager = new LinearLayoutManager(MainActivity.this, LinearLayoutManager.HORIZONTAL, false);
//        recyclerView.setLayoutManager(horizontalLayoutManager);
//        recyclerView.setAdapter(adapter);
//
//    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
//        if (id == R.id.menu_search) {
//            return true;
//        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public boolean isReadStoragePermissionGranted() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {
                Log.v(TAG, "Permission is granted1");
                return true;
            } else {

                Log.v(TAG, "Permission is revoked1");
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 3);
                return false;
            }
        } else { //permission is automatically granted on sdk<23 upon installation
            Log.v(TAG, "Permission is granted1");
            return true;
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
