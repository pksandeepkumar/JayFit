package com.feathernet.jayfit.adapters;

/**
 * Created by sandeep on 30/03/18.
 */

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.feathernet.jayfit.AppConst;
import com.feathernet.jayfit.R;
import com.feathernet.jayfit.SubVideoListActivity;
import com.feathernet.jayfit.models.SliderData;
import com.feathernet.jayfit.models.SubCategory;
import com.feathernet.jayfit.models.Videos;

import java.util.ArrayList;
import java.util.Collections;


public class VideoListAdapter extends RecyclerView.Adapter<VideoListAdapter.ViewHolder> {



    Context mContext;
    ArrayList<SubCategory> subCategoryList;


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


    public VideoListAdapter(Context context, ArrayList<SubCategory> subCategoryList) {
        mContext = context;
        this.subCategoryList = subCategoryList;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_video_list_horizontal, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {

        final SubCategory subCategory = subCategoryList.get(position);

        holder.tvCatTitle.setText(subCategory.name);

        holder.imVid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, SubVideoListActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString(AppConst.SUBCATEGORY_ID, subCategory.subcategoryID);
                intent.putExtras(bundle);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mContext.startActivity(intent);
            }
        });

        Glide.with(mContext).load(subCategory.image)
//                .thumbnail(0.5f)
//                .crossFade()
//                .centerCrop()
//                .fitCenter()
//                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.imVid);

    }

    @Override
    public int getItemCount() {
        if(this.subCategoryList == null) return 0;
        return this.subCategoryList.size();
    }
}
