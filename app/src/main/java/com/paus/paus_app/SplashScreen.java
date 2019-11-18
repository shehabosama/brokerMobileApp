package com.paus.paus_app;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ProgressBar;

public class SplashScreen extends AppCompatActivity {

    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        progressBar = (ProgressBar)findViewById(R.id.progress_bar_);
        progressBar.getIndeterminateDrawable().setColorFilter(Color.parseColor("#ffffff"),
                android.graphics.PorterDuff.Mode.MULTIPLY);
        Thread thread = new Thread()
        {

            @Override
            public void run() {

                try{
                    sleep(2000);
                }catch (Exception e){

                    e.printStackTrace();

                }

                finally
                {
                    startActivity(new Intent(getApplicationContext(),Slider.class)
                            .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK));
                }

            }
        };thread.start();
    }
}
