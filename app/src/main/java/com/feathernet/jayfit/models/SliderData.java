package com.feathernet.jayfit.models;

import com.feathernet.jayfit.R;

import java.util.ArrayList;

/**
 * Created by sandeep on 30/03/18.
 */

public class SliderData {

    public int resourseImage;
    public String name;
    public boolean free = true;
    public boolean hasAttachment = true;


    public static String videoURL = "https://player.vimeo.com/external/251587663.sd.mp4?s=d568956e0d5ce775187a159cf33b23f3e8004053&profile_id=164";

    public SliderData(String name, int resourseImage) {
        this.name = name;
        this.resourseImage = resourseImage;
    }

    public SliderData(String name, int resourseImage, boolean free, boolean hasAttachment) {
        this.name = name;
        this.resourseImage = resourseImage;
        this.free = free;
        this.hasAttachment = hasAttachment;
    }

    public static ArrayList<SliderData> getSamples() {
        ArrayList<SliderData> sample = new ArrayList<SliderData>();
        sample.add(new SliderData("Sub Category 1", R.drawable.thumb1));
        sample.add(new SliderData("Sub Category 2", R.drawable.thumb2));
        sample.add(new SliderData("Sub Category 3", R.drawable.thumb3));
        sample.add(new SliderData("Sub Category 4", R.drawable.thumb4));
        sample.add(new SliderData("Sub Category 5", R.drawable.thumb5));
        sample.add(new SliderData("Sub Category 6", R.drawable.thumb6));
        sample.add(new SliderData("Sub Category 7", R.drawable.thumb7));
        sample.add(new SliderData("Sub Category 8", R.drawable.thumb8));
        sample.add(new SliderData("Sub Category 9", R.drawable.thumb9));
        sample.add(new SliderData("Sub Category 10", R.drawable.thumb10));
        sample.add(new SliderData("Sub Category 11", R.drawable.thumb11));
        sample.add(new SliderData("Sub Category 12", R.drawable.thumb12));
        sample.add(new SliderData("Sub Category 13", R.drawable.thumb13));
        sample.add(new SliderData("Sub Category 14", R.drawable.thumb14));
        sample.add(new SliderData("Sub Category 15", R.drawable.thumb15));
        return sample;
    }


    public static ArrayList<SliderData> getVidSamples() {
        ArrayList<SliderData> sample = new ArrayList<SliderData>();
        sample.add(new SliderData("DELTOID Workout for BIG FULL Shoulders", R.drawable.thumb1, true, true));
        sample.add(new SliderData("DELTOID Workout for BIG FULL Shoulders", R.drawable.thumb2, false, false));
        sample.add(new SliderData("DELTOID Workout for BIG FULL Shoulders", R.drawable.thumb3, true, true));
        sample.add(new SliderData("DELTOID Workout for BIG FULL Shoulders", R.drawable.thumb4, false, false));
        sample.add(new SliderData("DELTOID Workout for BIG FULL Shoulders", R.drawable.thumb5, true, false));
        sample.add(new SliderData("DELTOID Workout for BIG FULL Shoulders", R.drawable.thumb6, true, false));
        sample.add(new SliderData("DELTOID Workout for BIG FULL Shoulders", R.drawable.thumb7, true, true));
        sample.add(new SliderData("DELTOID Workout for BIG FULL Shoulders", R.drawable.thumb8, true, true));
        sample.add(new SliderData("DELTOID Workout for BIG FULL Shoulders", R.drawable.thumb9, false, false));
        sample.add(new SliderData("DELTOID Workout for BIG FULL Shoulders", R.drawable.thumb10, false, false));
        sample.add(new SliderData("DELTOID Workout for BIG FULL Shoulders", R.drawable.thumb11, true, false));
        sample.add(new SliderData("DELTOID Workout for BIG FULL Shoulders", R.drawable.thumb12, false, true));
        sample.add(new SliderData("DELTOID Workout for BIG FULL Shoulders", R.drawable.thumb13, false, true));
        sample.add(new SliderData("DELTOID Workout for BIG FULL Shoulders", R.drawable.thumb14, true, true));
        sample.add(new SliderData("DELTOID Workout for BIG FULL Shoulders", R.drawable.thumb15, false, false));
        return sample;
    }


}
