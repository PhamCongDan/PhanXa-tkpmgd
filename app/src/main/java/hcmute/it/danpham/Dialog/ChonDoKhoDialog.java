package hcmute.it.danpham.Dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;

import hcmute.it.danpham.phanxaprojusingtranslateapi.ConvertSpeechToText;
import hcmute.it.danpham.phanxaprojusingtranslateapi.R;

/**
 * Created by Dan Pham on 05/15/2018.
 */

public class ChonDoKhoDialog extends Dialog {
    Button btnEasy,btnMedium,btnHard;
    Activity mActivity;
    public ChonDoKhoDialog(Activity activity) {
        super(activity);
        mActivity=activity;
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.layout_chon_do_kho);
        getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT);
//
        btnEasy= (Button) findViewById(R.id.btnEasy);
        btnMedium= (Button) findViewById(R.id.btnMedium);
        btnHard= (Button) findViewById(R.id.btnHard);

        btnEasy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it=new Intent(mActivity, ConvertSpeechToText.class);
                it.putExtra("thoigian1",7700);
                it.putExtra("thoigian2",70);
                mActivity.startActivity(it);
                mActivity.finish();
            }
        });
        btnMedium.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it=new Intent(mActivity, ConvertSpeechToText.class);
                it.putExtra("thoigian1",5500);
                it.putExtra("thoigian2",50);
                mActivity.startActivity(it);
                mActivity.finish();
            }
        });
        btnHard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it=new Intent(mActivity, ConvertSpeechToText.class);
                it.putExtra("thoigian1",3300);
                it.putExtra("thoigian2",30);
                mActivity.startActivity(it);
                mActivity.finish();
            }
        });
    }
}
