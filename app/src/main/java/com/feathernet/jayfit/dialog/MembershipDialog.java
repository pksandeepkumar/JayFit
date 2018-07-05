package com.feathernet.jayfit.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import com.feathernet.jayfit.R;

/**
 * Created by sandeep on 11/04/18.
 */

public class MembershipDialog extends Dialog  {

    public Dialog d;
//    public Button yes, no;

    public MembershipDialog(Context a) {
        super(a);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_membership);

        TextView tv3Months = (TextView) this.findViewById(R.id.tvBtnThreeMonths);
        tv3Months.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent intent = new Intent(view.getContext(), PaymentActivity.class);
//                view.getContext().startActivity(intent);

            }
        });

//        yes = (Button) findViewById(R.id.btn_yes);
//        no = (Button) findViewById(R.id.btn_no);
//        yes.setOnClickListener(this);
//        no.setOnClickListener(this);

    }


}
