package com.feathernet.jayfit.controlls;

import android.content.Context;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.feathernet.jayfit.MainActivity;
import com.feathernet.jayfit.R;
import com.feathernet.jayfit.adapters.VideoListAdapter;
import com.feathernet.jayfit.models.Category;
import com.feathernet.jayfit.preferance.SavedPreferance;

public class VideoListItem extends RelativeLayout {

    Context context;
    Category category;

    public VideoListItem(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.context = context;
        init(context,attrs);
    }

    public VideoListItem(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        init(context, attrs);
    }

    public VideoListItem(Context context, Category category) {
        super(context);
        this.context = context;
        this.category = category;
        init(context, null);
    }

    private void init(Context context, AttributeSet attrs) {

        LayoutInflater inflater = (LayoutInflater) getContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View child = inflater.inflate(R.layout.controll_video_list_item, this);

        if(category ==  null) return;

        TextView tvCatName = (TextView) findViewById(R.id.tvCatName);
        tvCatName.setText(category.name);

        populateList();
    }


    private void populateList() {
        if(category.subCategories == null) return;
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.idRecyclerViewHorizontalList);
        recyclerView.addItemDecoration(new DividerItemDecoration(context,
                LinearLayoutManager.HORIZONTAL));
        VideoListAdapter adapter = new VideoListAdapter(context);
        LinearLayoutManager horizontalLayoutManager = new LinearLayoutManager(context,
                LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(horizontalLayoutManager);
        recyclerView.setAdapter(adapter);

    }



}