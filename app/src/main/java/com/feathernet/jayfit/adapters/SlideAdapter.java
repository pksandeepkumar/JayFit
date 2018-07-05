package com.feathernet.jayfit.adapters;


import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.feathernet.jayfit.R;
import com.feathernet.jayfit.models.SliderData;

import java.util.ArrayList;

public class SlideAdapter extends PagerAdapter {

    private Context mContext;
    private ArrayList<SliderData> sliderDatas;

    public SlideAdapter(Context context) {
        mContext = context;
        sliderDatas = SliderData.getSamples();
    }

    @Override
    public Object instantiateItem(ViewGroup collection, int position) {

        LayoutInflater inflater = LayoutInflater.from(mContext);
        ViewGroup layout = (ViewGroup) inflater.inflate(R.layout.view_pager_adapter, collection, false);
        ImageView imSlides = (ImageView) layout.findViewById(R.id.imSlides);
//        imSlides.setImageResource(sliderDatas.get(position).resourseImage);

        Glide.with(mContext).load(sliderDatas.get(position).resourseImage)
//                .thumbnail(0.5f)
//                .crossFade()
//                .centerCrop()
//                .fitCenter()
//                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imSlides);
        collection.addView(layout);
        return layout;
    }

    @Override
    public void destroyItem(ViewGroup collection, int position, Object view) {
        collection.removeView((View) view);
    }

    @Override
    public int getCount() {
        return sliderDatas.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return "";
    }

}
