package com.feathernet.jayfit;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.widget.MediaController;
import android.widget.VideoView;

import com.feathernet.jayfit.models.SliderData;

public class VideoPlayerActivity extends BaseActivity {

    String VideoURL = "https://gcs-vimeo.akamaized.net/exp=1523394409~acl=%2A%2F914665015.mp4%2A~hmac=940d62e75ed1ef797787f7dd754debab1062685cc9b0a0480617ebb1895b3fdd/vimeo-prod-skyfire-std-us/01/317/10/251587663/914665015.mp4";
    VideoView videoview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_player);
        VideoURL = SliderData.videoURL;

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

                videoview.start();
            }
        });

    }


}
