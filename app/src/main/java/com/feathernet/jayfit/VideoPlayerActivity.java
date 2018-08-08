package com.feathernet.jayfit;

import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

import com.feathernet.jayfit.preferance.SavedPreferance;
import com.wang.avi.AVLoadingIndicatorView;

public class VideoPlayerActivity extends BaseActivity {

    String VideoURL = "https://gcs-vimeo.akamaized.net/exp=1523394409~acl=%2A%2F914665015.mp4%2A~hmac=940d62e75ed1ef797787f7dd754debab1062685cc9b0a0480617ebb1895b3fdd/vimeo-prod-skyfire-std-us/01/317/10/251587663/914665015.mp4";
    String videoUrl2 = "https://player.vimeo.com/external/280213231.sd.mp4?s=3099248659d3462c412d96b349f11b03775417d2&profile_id=164";
    VideoView videoview;
    Context mContext;
    String videoID = null;
    AVLoadingIndicatorView avlProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_player);

        mContext = this;


        avlProgress = (AVLoadingIndicatorView) findViewById(R.id.avlProgress);
        Bundle bundle = getIntent().getExtras();

        String videoUrl = bundle.getString(AppConst.VIDEO_URL);
        videoID = bundle.getString(AppConst.VIDEO_ID);
        if(videoUrl != null) {
            VideoURL = videoUrl;
//            VideoURL = videoUrl2;

            videoview = (VideoView) findViewById(R.id.VideoView);
            try {
                // Start the MediaController
                MediaController mediacontroller = new MediaController(
                        this);
                mediacontroller.setAnchorView(videoview);
                // Get the URL from String VideoURL
                Uri video = Uri.parse(VideoURL);
                videoview.setMediaController(mediacontroller);
                videoview.setVideoURI(video);

            } catch (Exception e) {
//            Log.e("Error", e.getMessage());
                e.printStackTrace();
            }

            videoview.requestFocus();
            videoview.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                // Close the progress bar and play the video
                public void onPrepared(MediaPlayer mp) {

                    avlProgress.setVisibility(View.INVISIBLE);
                    videoview.start();
                    if(videoID != null) {
                        int currentPostion = SavedPreferance.getInt(mContext, SavedPreferance.ID + videoID);
                        videoview.seekTo(currentPostion);
                        Log.e("XXXXXXXX","SEEK TO.................................." + currentPostion);
                    }


                }
            });




            videoview.setOnErrorListener(new MediaPlayer.OnErrorListener() {
                @Override
                public boolean onError(MediaPlayer mediaPlayer, int i, int i1) {
                    Toast.makeText(mContext,"Something went wrong, Please try later", Toast.LENGTH_LONG).show();
                    return false;
                }
            });
        } else {
            Toast.makeText(this,"Something went wrong, Please try later", Toast.LENGTH_LONG).show();
        }


    }

    public void showProgress() {

    }

    public void hideProgress() {

    }

    @Override
    protected void onPause() {
        super.onPause();

        if(videoview != null && videoID !=null) {
            int currentPositon = videoview.getCurrentPosition();
            SavedPreferance.setInt(mContext, SavedPreferance.ID + videoID, currentPositon);
            Log.e("XXXXXXXX","SAVING.................................." + currentPositon);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();



//        Toast.makeText(this,"OnDestroy", Toast.LENGTH_LONG).show();
    }
}
