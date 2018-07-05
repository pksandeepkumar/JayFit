package com.feathernet.jayfit.controlls;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.feathernet.jayfit.R;
import com.feathernet.jayfit.preferance.SavedPreferance;

public class HeadderControll extends RelativeLayout {

    Context context;

    public HeadderControll(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context,attrs);
    }

    public HeadderControll(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public HeadderControll(Context context) {
        super(context);
        init(context, null);
    }

    private void init(Context context, AttributeSet attrs) {

        LayoutInflater inflater = (LayoutInflater) getContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View child = inflater.inflate(R.layout.controll_header, this);

        final ImageView imProfilePic = (ImageView) findViewById(R.id.imProfilePic);
        TextView tvName = (TextView) findViewById(R.id.tvName);
        TextView tvEmail = (TextView) findViewById(R.id.tvEmail);
        tvName.setText(SavedPreferance.getString(context,SavedPreferance.NAME));
        tvEmail.setText(SavedPreferance.getString(context,SavedPreferance.EMAIL));

    }



}