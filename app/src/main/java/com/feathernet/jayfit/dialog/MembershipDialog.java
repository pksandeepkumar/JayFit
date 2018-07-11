package com.feathernet.jayfit.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import com.feathernet.jayfit.R;
import com.feathernet.jayfit.SubVideoListActivity;
import com.feathernet.jayfit.preferance.SavedPreferance;

/**
 * Created by sandeep on 11/04/18.
 */

public class MembershipDialog extends Dialog  {

    public static  final int REQUEST_CODE = 1234;

    public Dialog d;
//    public Button yes, no;

    public interface OnDialogClose {
        public void OnDialogClose();
    }
    OnDialogClose onDialogClose;
    public void setOnDialogClose(OnDialogClose onDialogClose){
        this.onDialogClose = onDialogClose;
    }

    public MembershipDialog(Context a) {
        super(a);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_membership);

        TextView tv3Months = (TextView) this.findViewById(R.id.tvBtnThreeMonths);
        TextView tv6Months = (TextView) this.findViewById(R.id.tvBtnSixMonths);
        TextView tvBtnOneYear = (TextView) this.findViewById(R.id.tvBtnOneYear);

        tv3Months.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chekout(view);
            }
        });

        tv6Months.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chekout(view);
            }
        });

        tvBtnOneYear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chekout(view);
            }
        });


    }



    public void chekout(View v) {
        Log.e("IN Chekout","XXXXXXXXXXXXXXXXXXXXXXXXXXXX+++++++++++");
        Log.e("IN Chekout","XXXXXXXXXXXXXXXXXXXXXXXXXXXX+++++++++++");
        Log.e("IN Chekout","XXXXXXXXXXXXXXXXXXXXXXXXXXXX+++++++++++");
        Log.e("IN Chekout","XXXXXXXXXXXXXXXXXXXXXXXXXXXX+++++++++++");

        dismiss();
        SavedPreferance.setString(getContext(),SavedPreferance.CHECK_IN,"30");
//        if(onDialogClose != null) {
//            onDialogClose.OnDialogClose();
//        }
//        dismiss();
//
//        Intent intnet = new Intent("com.feathernet.jayfit.CHECKOUT");
//        getContext().sendBroadcast(intnet);

        SubVideoListActivity.chekout();
    }


}
