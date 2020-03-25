package com.android.jobber.Ui.fragments;

import android.Manifest;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.speech.tts.TextToSpeech;
import android.speech.tts.Voice;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.core.content.ContextCompat;

import com.android.jobber.R;
import com.android.jobber.common.base.BaseFragment;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

import static android.app.Activity.RESULT_OK;

public class Installment_fragmet_two extends BaseFragment implements TextToSpeech.OnInitListener {
    private static final int RESULT_LOAD_IMG =1121 ;
    private EditText txtSpeechInput;
    private ImageButton btnSpeak;
    private final int REQ_CODE_SPEECH_INPUT = 100;
    SpeechRecognizer mSpeechRecognizer;
    Intent mSpeechRecognizerIntent;
    TextToSpeech textToSpeech;
    public Installment_fragmet_two() {
        // Required empty public constructor
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_installment_fragmet_two, container, false);

        initializeViews(view);
        setListeners();
        return view ;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void initializeViews(View v) {
        txtSpeechInput = v. findViewById(R.id.txtSpeechInput);
        btnSpeak = v. findViewById(R.id.btnSpeak);
        textToSpeech = new TextToSpeech(getActivity(),this, "com.google.android.tts");
        Set<String> a=new HashSet<>();
        a.add("male");//here you can give male if you want to select male voice.
        Voice voice=new Voice("en-us-x-sfg#male_2-local",new Locale("en","US"),400,200,true,a);
        textToSpeech.setVoice(voice);
        textToSpeech.setSpeechRate(0.8f);
        checkPermission();
        mSpeechRecognizer = SpeechRecognizer.createSpeechRecognizer(getActivity());
        mSpeechRecognizerIntent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        mSpeechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE, getClass().getPackage().getName());
        mSpeechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        //  mSpeechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "ar-EG");
        // mSpeechRecognizerIntent.putExtra("android.speech.extra.EXTRA_ADDITIONAL_LANGUAGES", new String[]{"en-US"});
        mSpeechRecognizerIntent.putExtra("android.speech.extra.EXTRA_ADDITIONAL_LANGUAGES",
                Locale.getDefault());
        mSpeechRecognizer.setRecognitionListener(new RecognitionListener() {
            @Override
            public void onReadyForSpeech(Bundle bundle) {

            }

            @Override
            public void onBeginningOfSpeech() {

            }

            @Override
            public void onRmsChanged(float v) {

            }

            @Override
            public void onBufferReceived(byte[] bytes) {

            }

            @Override
            public void onEndOfSpeech() {

            }

            @Override
            public void onError(int i) {

            }

            @Override
            public void onResults(Bundle bundle) {
                //getting all the matches
                ArrayList<String> matches = bundle
                        .getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);

                //displaying the first match
                if (matches != null){
                    if(matches.get(0).equals("who made you")||matches.get(0).equals("Who made you")){
                        txtSpeechInput.setText("shehab osama made me to fucking abdallah abdelhady");
                    }else{
                        txtSpeechInput.setText("I don't understand that, but all of my knowledge and the goal I programmed for  .it is insulted and fucking abdallah abdelhady");
                    }


                    speakOut();

                }

            }

            @Override
            public void onPartialResults(Bundle bundle) {

            }

            @Override
            public void onEvent(int i, Bundle bundle) {

            }
        });
    }

    @Override
    protected void setListeners() {
        btnSpeak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                photoPickerIntent.setType("image/*");
                startActivityForResult(photoPickerIntent, RESULT_LOAD_IMG);
            }
        });
//            btnSpeak.setOnTouchListener(new View.OnTouchListener() {
//                @Override
//                public boolean onTouch(View view, MotionEvent motionEvent) {
//                    switch (motionEvent.getAction()) {
//
//                        case MotionEvent.ACTION_UP:
//                        case MotionEvent.ACTION_CANCEL:
//                            //when the user removed the finger
//                            mSpeechRecognizer.stopListening();
//                            txtSpeechInput.setHint("You will see input here");
//                            break;
//
//                        case MotionEvent.ACTION_DOWN:
//                            //finger is on the button
//                            mSpeechRecognizer.startListening(mSpeechRecognizerIntent);
//                            txtSpeechInput.setText("");
//                            txtSpeechInput.setHint("Listening...");
//                            break;
//                    }
//                    return false;
//                }
//            });
    }

    private void promptSpeechInput() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT,
                getString(R.string.speech_prompt));
        try {
            startActivityForResult(intent, REQ_CODE_SPEECH_INPUT);
        } catch (ActivityNotFoundException a) {
            Toast.makeText(getActivity(),
                    getString(R.string.speech_not_supported),
                    Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case REQ_CODE_SPEECH_INPUT: {
                if (resultCode == RESULT_OK && null != data) {

                    ArrayList<String> result = data
                            .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    txtSpeechInput.setText(result.get(0));
                }
                break;

            }
            case RESULT_LOAD_IMG:

                Uri uri = data.getData();

            //    Message.message(getActivity(),   CompressPhotos.compressImage(getActivity(),uri));
                break;

        }
    }
    private void checkPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (!(ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.RECORD_AUDIO) == PackageManager.PERMISSION_GRANTED)) {
                Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                        Uri.parse("package:" + getActivity().getPackageName()));
                startActivity(intent);
                getActivity().finish();
            }
        }
    }

    @Override
    public void onInit(int status) {
        if(status == TextToSpeech.SUCCESS){
            int result =textToSpeech.setLanguage(Locale.US);
            if(result == TextToSpeech.LANG_MISSING_DATA||result == TextToSpeech.LANG_NOT_SUPPORTED){

            }else{
                speakOut();
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(textToSpeech !=null){
            textToSpeech.stop();
            textToSpeech.shutdown();

        }
    }

    public void speakOut(){
        textToSpeech.speak(txtSpeechInput.getText().toString(),TextToSpeech.QUEUE_FLUSH,null);

    }
}
