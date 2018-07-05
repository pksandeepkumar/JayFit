package com.feathernet.jayfit.adapters;

/**
 * Created by sandeep on 30/03/18.
 */

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.feathernet.jayfit.R;
import com.feathernet.jayfit.SubVideoListActivity;
import com.feathernet.jayfit.models.SliderData;

import java.util.ArrayList;
import java.util.Collections;


public class VideoListAdapter extends RecyclerView.Adapter<VideoListAdapter.ViewHolder> {



    Context mContext;

    private ArrayList<SliderData> mValues;

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public final ImageView imVid;
        public final TextView tvCatTitle;


        public ViewHolder(View view) {
            super(view);
            imVid = (ImageView) view.findViewById(R.id.imVid);
            tvCatTitle = (TextView) view.findViewById(R.id.tvCatTitle);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + tvCatTitle.getText();
        }
    }



    public VideoListAdapter(Context context) {
//        context.getTheme().resolveAttribute(R.attr.selectableItemBackground, mTypedValue, true);
//        mBackground = mTypedValue.resourceId;
        mContext = context;
        mValues = SliderData.getSamples();
        Collections.shuffle(mValues);
    }

    public VideoListAdapter(Context context, ArrayList<SliderData> dataList) {
//        context.getTheme().resolveAttribute(R.attr.selectableItemBackground, mTypedValue, true);
//        mBackground = mTypedValue.resourceId;
        mContext = context;
        mValues = dataList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_video_list_horizontal, parent, false);
        //view.setBackgroundResource(mBackground);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {

        final SliderData product = mValues.get(position);

        holder.tvCatTitle.setText("Sub Category" + (position+1));

        holder.imVid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e("VideoListAdapter","Calling SubVideoListActivity intent");
                Intent intent = new Intent(mContext, SubVideoListActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mContext.startActivity(intent);
            }
        });

        Glide.with(mContext).load(product.resourseImage)
//                .thumbnail(0.5f)
//                .crossFade()
//                .centerCrop()
//                .fitCenter()
//                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.imVid);

    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }
}
