package com.paus.paus_app;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.paus.paus_app.Adapters.ViewPagerAdapter;

import java.util.TimerTask;

public class Slider extends AppCompatActivity {

    ViewPager viewPager;
    String type;
    String DEFAULT=null;
  //  TextView next,previous;
//    Button btn1,btn2,btn3;
    Button button;
    ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.slider_activity);
        viewPager = (ViewPager)findViewById(R.id.view_pager);
        button = findViewById(R.id.next_btn);
        button.setVisibility(View.GONE);
        imageView = findViewById(R.id.image_next);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(viewPager.getCurrentItem()==0)
                {
                    viewPager.setCurrentItem(1);
                }
                else if(viewPager.getCurrentItem() == 1)
                {
                    viewPager.setCurrentItem(2);
                    imageView.setVisibility(View.GONE);

                }else if(viewPager.getCurrentItem() == 2)
                {
                    type="test";
                    SharedPreferences sharedPreferences=getSharedPreferences("Data",MODE_PRIVATE);
                    SharedPreferences.Editor editor=sharedPreferences.edit();
                    editor.putString("kind_person",type);
                    editor.commit();

                    imageView.setVisibility(View.GONE);
                }
            }
        });
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {
                if (i==2) {
                    button.setVisibility(View.VISIBLE);
                    imageView.setVisibility(View.GONE);
                }else
                {
                    button.setVisibility(View.GONE);
                    imageView.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onPageSelected(int i) {

            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
       // previous = findViewById(R.id.previous_btn);

//        btn1 = findViewById(R.id.btn1);
//        btn2 = findViewById(R.id.btn2);
//        btn3 = findViewById(R.id.btn3);
        //viewPager.beginFakeDrag();
        final ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(this);
        viewPager.setAdapter(viewPagerAdapter);


        // Timer timer = new Timer();
      // timer.scheduleAtFixedRate(new MyTimeTask(),2000,2000);

        //btn1.setBackgroundResource(R.drawable.button_c2);

        SharedPreferences sharedPreferences_ret = getSharedPreferences("Data", Context.MODE_PRIVATE);
        type = sharedPreferences_ret.getString("kind_person", DEFAULT);
        if (type!=null)
        {
            startActivity(new Intent(getApplicationContext(),LoginActivity.class));
        }
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),LoginActivity.class));

            }
        });


//        previous.setVisibility(View.INVISIBLE);
//
//        previous.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if(viewPager.getCurrentItem()==1)
//                {
//                    previous.setVisibility(View.INVISIBLE);
//                    viewPager.setCurrentItem(0);
////                    btn1.setBackgroundResource(R.drawable.button_c2);
////                    btn2.setBackgroundResource(R.drawable.button_c);
////                    btn3.setBackgroundResource(R.drawable.button_c);
//
//                }
//                else if(viewPager.getCurrentItem() == 2)
//                {
//                  //  next.setText("Next");
//
//                    viewPager.setCurrentItem(1);
////                    btn1.setBackgroundResource(R.drawable.button_c);
////                    btn2.setBackgroundResource(R.drawable.button_c2);
////                    btn3.setBackgroundResource(R.drawable.button_c);
//
//                }else if (viewPager.getCurrentItem() == 0)
//                {
//                   // next.setText("Next");

//                    btn1.setBackgroundResource(R.drawable.button_c2);
//                    btn2.setBackgroundResource(R.drawable.button_c);
//                    btn3.setBackgroundResource(R.drawable.button_c);
              //  }

          //  }
      //  });
//        next.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//
//                if(viewPager.getCurrentItem()==0)
//                {
//                    next.setText("Next");
//
//                    previous.setVisibility(View.VISIBLE);
//                    viewPager.setCurrentItem(1);
////                    btn1.setBackgroundResource(R.drawable.button_c);
////                    btn2.setBackgroundResource(R.drawable.button_c2);
//
//                }
//                else if(viewPager.getCurrentItem() == 1)
//                {
//                    next.setText("Login");
//                    previous.setVisibility(View.VISIBLE);
//                    viewPager.setCurrentItem(2);
////                  +
//
//                }else if(viewPager.getCurrentItem() == 2)
//                {
//
//                    type="test";
//                    SharedPreferences sharedPreferences=getSharedPreferences("Data",MODE_PRIVATE);
//                    SharedPreferences.Editor editor=sharedPreferences.edit();
//                    editor.putString("kind_person",type);
//                    editor.commit();
//
//                    Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
//                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_NEW_TASK);
//                    startActivity(intent);
//
//                }
//
//            }
//        });





    }

    public  class MyTimeTask extends TimerTask
    {

        @Override
        public void run() {
            Slider.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if(viewPager.getCurrentItem() == 0 )
                    {
                        viewPager.setCurrentItem(1);

                    }else if(viewPager.getCurrentItem() == 1)
                    {
                        viewPager.setCurrentItem(2);
                    }else
                    {
                        viewPager.setCurrentItem(0);
                    }
                }
            });

        }
    }
}
