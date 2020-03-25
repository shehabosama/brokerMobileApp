package com.android.jobber.Ui.Activities;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.widget.ProgressBar;

import com.android.jobber.R;
import com.android.jobber.Ui.Activities.MainActivity.MainActivity;
import com.android.jobber.common.SqlHelper.myDbAdapter;
import com.android.jobber.common.base.BaseActivity;

public class SplashScreen extends BaseActivity {

    ProgressBar progressBar;
    myDbAdapter helper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        initializeViews();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

    }

    @Override
    protected void initializeViews() {
        helper = new myDbAdapter(this);
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
                    if(helper.getData().length()>0){
                        startActivity(new Intent(getApplicationContext(), MainActivity.class)
                                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK));
                    }else{
                        startActivity(new Intent(getApplicationContext(),Slider.class)
                                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK));
                    }

                }

            }
        };thread.start();
    }

    @Override
    protected void setListeners() {

    }
}
