package hcmute.it.danpham.phanxaprojusingtranslateapi;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Random;

import hcmute.it.danpham.FileStream.FileHelper;
import hcmute.it.danpham.model.KetQua;

public class ConvertSpeechToText extends AppCompatActivity implements RecognitionListener{
    private String LOG_TAG = "ConvertSpeechToText";
    private static final int REQUEST_RECORD_PERMISSION = 100;
    TextView txtVoice,txtDeBai;


    TextToSpeech toSpeech;
    int result;
    String inputString;

    public  static ArrayList<String> dataInput=new ArrayList<>();
    ArrayList<Integer> chiSoDeBai;

    ArrayList<String>dataOutput;
    int mangCauHoi[] =new int[10];

    /// array adapter
    static ArrayList<KetQua>dsKetQua;
    private SpeechRecognizer speech = null;
    private Intent recognizerIntent;

    //progreebar and countdown
    ProgressBar mProgressBar;
    CountDownTimer mCountDownTimer;
    int i=100;  //chi so progress
    int socau=0;
    //get thoi gian max (do kho)
    int T,t;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_convert_speech_to_text);
        //FileHelper.createFile();
        readData();
        addControls();
        addEvents();
        //tesst thoi luc sau xoa
        xuLyCoundown();

        toSpeech = new TextToSpeech(getApplication(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status == TextToSpeech.SUCCESS) {
                    result = toSpeech.setLanguage(Locale.US);
                } else {
                    Toast.makeText(getApplication(), "ko ho tro", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }


    private void addControls() {
        Intent it=getIntent();
        T=it.getIntExtra("thoigian1",0);
        t=it.getIntExtra("thoigian2",0);
        dataOutput=new ArrayList<>();
        chiSoDeBai=new ArrayList<>();
        FileHelper.ReadFile();


        txtVoice= (TextView) findViewById(R.id.txtVoice);
        txtDeBai= (TextView) findViewById(R.id.txtDeBai);
        loadDeBai();






        ////

        speech = SpeechRecognizer.createSpeechRecognizer(this);
        Log.i(LOG_TAG, "isRecognitionAvailable: " + SpeechRecognizer.isRecognitionAvailable(this));
        speech.setRecognitionListener(this);
        recognizerIntent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        recognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_PREFERENCE,
                "en");
        recognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        recognizerIntent.putExtra(RecognizerIntent.EXTRA_MAX_RESULTS, 3);
        //
        mProgressBar=(ProgressBar)findViewById(R.id.progressbar);

        //
        speech.startListening(recognizerIntent);

    }
    private void  loadDeBai(){


        Random r= new Random();
        int getrandom=r.nextInt(dataInput.size()-1);
        while (chiSoDeBai.contains(getrandom)){
            getrandom=r.nextInt(dataInput.size()-1);
        }

        chiSoDeBai.add(getrandom);
        txtDeBai.setText(dataInput.get(getrandom));
    }

    private void addEvents() {


    }

    private void xuLyCoundown(){

        mProgressBar.setProgress(100);
        mCountDownTimer =new CountDownTimer(T,t) {
            @Override
            public void onTick(long l) {
                i--;
                mProgressBar.setProgress(i);
            }

            @Override
            public void onFinish() {
                mProgressBar.setProgress(0);
                speech.stopListening();
                //Toast.makeText(getApplication(),"gio moi het"+chiSoDeBai.get(socau),Toast.LENGTH_SHORT).show();
                socau++;

                if(socau<10){
                    i=100;
                    loadDeBai();
                    mCountDownTimer.start();
                    speech.startListening(recognizerIntent);
                }
                else{
                    //Toast.makeText(getApplication(),"het that roi "+chiSoDeBai.size(),Toast.LENGTH_SHORT).show();
                    socau=0;
                    //showOutput();
                    soSanhKetQua();
                    Intent it=new Intent(ConvertSpeechToText.this,ManHinhKetQuaActivity.class);
                    it.putExtra("socaudung",soCauDung);
                    startActivity(it);
                    finish();
                }


            }
        }.start();

    }
    public void readData()
    {
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(
                    new InputStreamReader(getAssets().open("data.txt")));

            // do reading, usually loop until end of file reading
            String mLine;
            while ((mLine = reader.readLine()) != null) {
                //process line
                dataInput.add(mLine);
                //txtDeBai.setText(mLine);
            }
        } catch (IOException e) {
            //log the exception
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    //log the exception
                }
            }
        }
    }
    private void showOutput(){
        for(int i=0;i<dataOutput.size();i++){
            Toast.makeText(getApplication(),dataOutput.get(i),Toast.LENGTH_SHORT).show();
        }
    }
    int soCauDung=0;
    private void soSanhKetQua(){
        dsKetQua=new ArrayList<>();
        for(int i=0;i<chiSoDeBai.size();i++) {
            if (!dataOutput.get(i).equalsIgnoreCase(dataInput.get(chiSoDeBai.get(i)))) {
                //sai thi` sao
                dsKetQua.add(new KetQua(dataInput.get(chiSoDeBai.get(i)), false));
            }
            else
            {
                dsKetQua.add(new KetQua(dataInput.get(chiSoDeBai.get(i)), true));
                soCauDung++;
            }
        }

    }


    public static String getErrorText(int errorCode) {
        String message;
        switch (errorCode) {
            case SpeechRecognizer.ERROR_AUDIO:
                message = "Audio recording error";
                break;
            case SpeechRecognizer.ERROR_CLIENT:
                message = "Client side error";
                break;
            case SpeechRecognizer.ERROR_INSUFFICIENT_PERMISSIONS:
                message = "Insufficient permissions";
                break;
            case SpeechRecognizer.ERROR_NETWORK:
                message = "Network error";
                break;
            case SpeechRecognizer.ERROR_NETWORK_TIMEOUT:
                message = "Network timeout";
                break;
            case SpeechRecognizer.ERROR_NO_MATCH:
                message = "No match";
                break;
            case SpeechRecognizer.ERROR_RECOGNIZER_BUSY:
                message = "RecognitionService busy";
                break;
            case SpeechRecognizer.ERROR_SERVER:
                message = "error from server";
                break;
            case SpeechRecognizer.ERROR_SPEECH_TIMEOUT:
                message = "No speech input";
                break;
            default:
                message = "Didn't understand, please try again.";
                break;
        }
        return message;
    }

    @Override
    public void onReadyForSpeech(Bundle bundle) {
        Log.i(LOG_TAG, "onReadyForSpeech");
    }

    @Override
    public void onBeginningOfSpeech() {
        Log.i(LOG_TAG, "onBeginningOfSpeech");
//        progressBar.setIndeterminate(false);
//        progressBar.setMax(10);
    }

    @Override
    public void onRmsChanged(float rmsdB) {
        Log.i(LOG_TAG, "onRmsChanged: " + rmsdB);
//        progressBar.setProgress((int) rmsdB);
    }

    @Override
    public void onBufferReceived(byte[] buffer) {
        Log.i(LOG_TAG, "onBufferReceived: " + buffer);
    }

    @Override
    public void onEndOfSpeech() {
        Log.i(LOG_TAG, "onEndOfSpeech");
//        progressBar.setIndeterminate(true);
//        toggleButton.setChecked(false);
    }

    @Override
    public void onError(int errorCode) {
        String errorMessage = getErrorText(errorCode);
        Log.d(LOG_TAG, "FAILED " + errorMessage);
//        returnedText.setText(errorMessage);
//        toggleButton.setChecked(false);
        dataOutput.add(socau,errorMessage);
    }

    @Override
    public void onResults(Bundle results) {
        Log.i(LOG_TAG, "onResults");
        ArrayList<String> matches = results
                .getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);

        dataOutput.add(socau,matches.get(0));
        txtVoice.setText(matches.get(0));
    }

    @Override
    public void onPartialResults(Bundle bundle) {
        Log.i(LOG_TAG, "onPartialResults");
    }

    @Override
    public void onEvent(int i, Bundle bundle) {
        Log.i(LOG_TAG, "onEvent");
    }
}
