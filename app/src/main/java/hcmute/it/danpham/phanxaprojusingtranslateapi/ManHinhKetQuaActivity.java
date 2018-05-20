package hcmute.it.danpham.phanxaprojusingtranslateapi;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;
import android.widget.TextView;

import hcmute.it.danpham.Adapter.KetQuaAdapter;

import static hcmute.it.danpham.phanxaprojusingtranslateapi.ConvertSpeechToText.dsKetQua;

public class ManHinhKetQuaActivity extends AppCompatActivity {
    ListView lvKetQua;
    TextView txtkq;

    KetQuaAdapter ketQuaAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_man_hinh_ket_qua);

        addControls();
        addEvents();
    }

    private void addEvents() {
    }

    private void addControls() {
        Intent it=getIntent();
        String kq=it.getIntExtra("socaudung",0)+"/10";
        txtkq= (TextView) findViewById(R.id.txtkq);
        txtkq.setText(kq);
        lvKetQua= (ListView) findViewById(R.id.lvKetQua);
//        dsKetQua=new ArrayList<>();
//        dsKetQua.add(new KetQua("my name is Dan",true));
//        dsKetQua.add(new KetQua("my name is cong",false));
//        dsKetQua.add(new KetQua("hello sd",true));
//        dsKetQua.add(new KetQua("my name is Dan",false));

        ketQuaAdapter=new KetQuaAdapter(
                this,
                R.layout.item_ket_qua,
                dsKetQua);
        lvKetQua.setAdapter(ketQuaAdapter);
    }
}
