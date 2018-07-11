package com.feathernet.jayfit.adapters;

/**
 * Created by sandeep on 30/03/18.
 */

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Environment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.feathernet.jayfit.R;
import com.feathernet.jayfit.VideoPlayerActivity;
import com.feathernet.jayfit.dialog.MembershipDialog;
import com.feathernet.jayfit.models.SliderData;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collections;


public class SubVideoListAdapter extends RecyclerView.Adapter<SubVideoListAdapter.ViewHolder> {

    Context mContext;

    private ArrayList<SliderData> mValues;

    public MembershipDialog.OnDialogClose onDialogClose;

    public void setOnDialogClose(MembershipDialog.OnDialogClose onDialogClose) {
        this.onDialogClose =onDialogClose;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public final ImageView imVid;
        public final TextView tvCatTitle;
        public final TextView btnViewPdf;
        public final TextView tvFree;


        public ViewHolder(View view) {
            super(view);
            imVid = (ImageView) view.findViewById(R.id.imVid);
            btnViewPdf = (TextView) view.findViewById(R.id.btnViewPdf);
            tvCatTitle = (TextView) view.findViewById(R.id.tvCatTitle);
            tvFree = (TextView) view.findViewById(R.id.tvFree);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + tvCatTitle.getText();
        }
    }


    public SubVideoListAdapter(Context context) {
//        context.getTheme().resolveAttribute(R.attr.selectableItemBackground, mTypedValue, true);
//        mBackground = mTypedValue.resourceId;
        mContext = context;
        mValues = SliderData.getVidSamples();
        Collections.shuffle(mValues);
    }

    public SubVideoListAdapter(Context context, ArrayList<SliderData> dataList) {
//        context.getTheme().resolveAttribute(R.attr.selectableItemBackground, mTypedValue, true);
//        mBackground = mTypedValue.resourceId;
        mContext = context;
        mValues = dataList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_sub_video_list, parent, false);
        //view.setBackgroundResource(mBackground);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {

        final SliderData product = mValues.get(position);

        if(!product.free) {
            holder.tvFree.setVisibility(View.INVISIBLE);
        }

        if(!product.hasAttachment) {
            holder.btnViewPdf.setVisibility(View.INVISIBLE);
        }

        holder.tvCatTitle.setText(product.name);
        holder.imVid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(product.free) {
                    Intent intent = new Intent(mContext, VideoPlayerActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    mContext.startActivity(intent);
                } else {
                    showDialog();
                }

            }
        });

        holder.btnViewPdf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openPDF();
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

    public void openPDF() {

//        boolean test = false;
//        if(!test) {
//            SubVideoListActivity.chekout();
//            return;
//        }
        File fileBrochure = new File(Environment.getExternalStorageDirectory() + File.separator + "demo.pdf");
        if (!fileBrochure.exists()) {
            CopyAssetsbrochure();
        }

        /** PDF reader code */
        File file = new File(Environment.getExternalStorageDirectory() + File.separator + "demo.pdf");

        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.fromFile(file), "application/pdf");
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        try {
            mContext.startActivity(intent);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(mContext, "NO Pdf Viewer", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(mContext, "Something went wrong", Toast.LENGTH_SHORT).show();
        }

    }

    private void CopyAssetsbrochure() {
        AssetManager assetManager = mContext.getAssets();
        String[] files = null;
        try {
            files = assetManager.list("");
        } catch (IOException e) {
            Log.e("tag", e.getMessage());
        }
        for (int i = 0; i < files.length; i++) {
            String fStr = files[i];
            if (fStr.equalsIgnoreCase("demo.pdf")) {
                InputStream in = null;
                OutputStream out = null;
                try {
                    in = assetManager.open(files[i]);
                    out = new FileOutputStream(Environment.getExternalStorageDirectory() + File.separator + files[i]);
                    copyFile(in, out);
                    in.close();
                    in = null;
                    out.flush();
                    out.close();
                    out = null;
                    break;
                } catch (Exception e) {
                    Log.e("tag", e.getMessage());
                }
            }
        }
    }

    private void copyFile(InputStream in, OutputStream out) throws IOException {
        byte[] buffer = new byte[1024];
        int read;
        while ((read = in.read(buffer)) != -1) {
            out.write(buffer, 0, read);
        }
    }



    public  void showDialog() {
        MembershipDialog dialog = new MembershipDialog(mContext);
        if(onDialogClose != null) {
            dialog.setOnDialogClose(onDialogClose);
        }

//        dialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, yourHeight);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }
}
