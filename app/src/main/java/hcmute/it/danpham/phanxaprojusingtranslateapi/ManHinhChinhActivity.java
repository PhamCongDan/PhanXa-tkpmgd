package hcmute.it.danpham.phanxaprojusingtranslateapi;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import de.hdodenhof.circleimageview.CircleImageView;
import hcmute.it.danpham.Dialog.ChonDoKhoDialog;

public class ManHinhChinhActivity extends AppCompatActivity {
    CircleImageView btnPlay;
    Button btnNhapData;
    ChonDoKhoDialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_man_hinh_chinh);

        //FileHelper.createFile();
        ChonDoKhoDialog dialog;
        addControls();
        addEvents();

    }

    private void addEvents() {
        btnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog=new ChonDoKhoDialog(ManHinhChinhActivity.this);
                dialog.show();
            }
        });
        btnNhapData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                FileHelper.createFile();
//                FileHelper.saveToFile("56565656");
                startActivity(new Intent(ManHinhChinhActivity.this,ManHinhNhapDataActivity.class));
            }
        });
    }

    private void addControls() {
        btnPlay= (CircleImageView) findViewById(R.id.btnPlay);
        btnNhapData= (Button) findViewById(R.id.btnNhapData);
    }
}
