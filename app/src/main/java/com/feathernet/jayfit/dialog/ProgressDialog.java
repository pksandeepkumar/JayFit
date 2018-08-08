package com.feathernet.jayfit.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.Window;

import com.feathernet.jayfit.R;

public class ProgressDialog  extends Dialog {

    public ProgressDialog(Context context) {
        super(context);
        init();
    }

    private void init() {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setBackgroundDrawable(
                new ColorDrawable(android.graphics.Color.TRANSPARENT));
        setContentView(R.layout.dialog_progress);
        setCanceledOnTouchOutside(false);
        setCancelable(false);
    }

    public void hideDialog() {
        if(isShowing()) {
            hide();
        }
    }

}