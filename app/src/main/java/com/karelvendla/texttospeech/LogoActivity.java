package com.karelvendla.texttospeech;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class LogoActivity extends AppCompatActivity {

    static int delay = 5000;

    final Handler handler = new Handler();
    final Runnable r = new Runnable() {
        public void run() {

            Intent intent = new Intent(LogoActivity.this,MainActivity.class);
            startActivity(intent);
            LogoActivity.super.onBackPressed();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logo);

        handler.postDelayed(r, delay);
    }
}
