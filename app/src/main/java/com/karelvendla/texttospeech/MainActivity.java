package com.karelvendla.texttospeech;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.speech.RecognitionService;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    ImageButton mikrofon;
    TextView kõneTekst;
    EditText etteloetavTekst;
    String tekst;
    int tulemus;
    TextToSpeech kõneks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mikrofon = findViewById(R.id.mic);
        kõneTekst = findViewById(R.id.spokenTXT);
        etteloetavTekst = findViewById(R.id.readTXT);

        kõneks = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status == TextToSpeech.SUCCESS) {
                    tulemus = kõneks.setLanguage(Locale.getDefault());
                }
                else {
                    Toast.makeText(getApplicationContext(), "Sellise vahendi tööd ei tota sinu seade", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void kusutajaKõneleb() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE,Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT,"Ütle midagi");
        try {
            startActivityForResult(intent,100);
        } catch (ActivityNotFoundException a) {
            Toast.makeText(getApplicationContext(),"Sellise vahendi tööd ei toeta sinu seade",Toast.LENGTH_LONG).show();
        }
    }
    public void onActivityResult(int request_code, int result_code, Intent intent){
        super.onActivityResult(request_code, result_code, intent);
        switch(request_code)
        { case 100: if(result_code == RESULT_OK && intent != null)
        { ArrayList<String> tulem =
                intent.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            kõneTekst.setText(tulem.get(0));
        }
            break;
        }
    }


    public void mikriklõps(View view) {
        kusutajaKõneleb();
    }

    public void tekstkõneks(View view) {
        switch (view.getId())
        {
            case R.id.BTNspeak:
                if (tulemus == TextToSpeech.LANG_MISSING_DATA || tulemus ==
                        TextToSpeech.LANG_NOT_SUPPORTED)
                { Toast.makeText(getApplicationContext(), "Sellise vahendi tööd ei toeta sinu seade",Toast.LENGTH_SHORT).show();
                }
                else {
                    tekst = etteloetavTekst.getText().toString();
                    kõneks.speak(tekst, TextToSpeech.QUEUE_FLUSH, null);
                }
                break;
            case R.id.BTNstop:
                if (kõneks != null)
                {
                    kõneks.stop();
                }
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if ( kõneks!=null)
        {
            kõneks.stop();
            kõneks.shutdown();
        }
    }

    public void puhasta(View view) {
        kõneTekst.setText("");
        etteloetavTekst.setText("");
    }
}
