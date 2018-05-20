package hcmute.it.danpham.Adapter;

import android.app.Activity;
import android.speech.tts.TextToSpeech;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;
import hcmute.it.danpham.model.KetQua;
import hcmute.it.danpham.phanxaprojusingtranslateapi.R;

/**
 * Created by Dan Pham on 05/10/2018.
 */

public class KetQuaAdapter extends ArrayAdapter<KetQua> {
    private int result;
    private TextToSpeech textToSpeech;
    //man hinh can hien thi
    Activity context;
    //layout cua tung dong
    int resource;
    List<KetQua> objects;

    public KetQuaAdapter(Activity context, int resource, List<KetQua> objects) {
        super(context, resource, objects);
        this.context=context;
        this.resource=resource;
        this.objects=objects;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {



        LayoutInflater inflater=this.context.getLayoutInflater();
        View row=inflater.inflate(this.resource,null);
        //
        TextView txtDeBai= (TextView) row.findViewById(R.id.txtDeBai);
        ImageView imgDapAn= (ImageView) row.findViewById(R.id.imgDapAn);
        CircleImageView btnSpeak= (CircleImageView) row.findViewById(R.id.btnSpeak);
        ////
        textToSpeech=new TextToSpeech(getContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status == TextToSpeech.SUCCESS) {
                    result = textToSpeech.setLanguage(Locale.US);
                } else {
                    Toast.makeText(getContext(), "ko ho tro", Toast.LENGTH_SHORT).show();
                }
            }
        });
        //
        KetQua kq=this.objects.get(position);
        txtDeBai.setText(kq.getDeBai());
        if(kq.isDapAnDung()){
            imgDapAn.setImageResource(R.drawable.check);
        }
        else{
            imgDapAn.setImageResource(R.drawable.wrong);
        }
        final String inpStr=kq.getDeBai();
        btnSpeak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(result==TextToSpeech.LANG_MISSING_DATA || result==TextToSpeech.LANG_NOT_SUPPORTED){
                    Toast.makeText(getContext(),"ko ho tro",Toast.LENGTH_SHORT).show();
                }
                else{
                    textToSpeech.speak(inpStr,TextToSpeech.QUEUE_ADD,null);
                }
            }
        });

        return row;
    }
}
