package hcmute.it.danpham.phanxaprojusingtranslateapi;

import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import java.util.Locale;

import hcmute.it.danpham.FileStream.FileHelper;
import hcmute.it.danpham.XuLyChuoi.ChuanHoaChuoi;

public class ManHinhNhapDataActivity extends AppCompatActivity {
    ImageButton btnThem,btnVolume;
    EditText txtInput;
    ArrayAdapter adapter;
    ListView lvInput;


    //texxt to speech
    private TextToSpeech textToSpeech;
    private int result;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_man_hinh_nhap_data);
        FileHelper.ReadFile();
        addControls();
        addEvents();

    }

    private void addControls() {
        btnThem= (ImageButton) findViewById(R.id.btnThem);
        btnVolume= (ImageButton) findViewById(R.id.btnVolume);
        txtInput= (EditText) findViewById(R.id.txtInput);
        lvInput= (ListView) findViewById(R.id.lvInput);

        textToSpeech=new TextToSpeech(getApplication(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status == TextToSpeech.SUCCESS) {
                    result = textToSpeech.setLanguage(Locale.US);
                } else {
                    Toast.makeText(getApplication(), "ko ho tro", Toast.LENGTH_SHORT).show();
                }
            }
        });
        adapter=new ArrayAdapter(
                getApplication(),
                android.R.layout.simple_list_item_1,
                ConvertSpeechToText.dataInput
        );
        lvInput.setAdapter(adapter);
    }
    private void addEvents(){
        btnThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String ipStr=txtInput.getText().toString();
                ChuanHoaChuoi ch=new ChuanHoaChuoi();

                if (FileHelper.saveToFile(ch.chuanHoa(ipStr)))
                {
                    Toast.makeText(getApplication(),"Saved to file",Toast.LENGTH_SHORT).show();
                    adapter.notifyDataSetChanged();

                }else{
                    Toast.makeText(getApplication(),"Error save file!!!",Toast.LENGTH_SHORT).show();
                }
            }
        });
        btnVolume.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(result==TextToSpeech.LANG_MISSING_DATA || result==TextToSpeech.LANG_NOT_SUPPORTED){
                    Toast.makeText(getApplication(),"ko ho tro",Toast.LENGTH_SHORT).show();
                }
                else{
                    textToSpeech.speak(txtInput.getText().toString(),TextToSpeech.QUEUE_ADD,null);
                }
            }
        });
    }
}
