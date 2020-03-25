package com.android.jobber.Ui.Activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.viewpager.widget.ViewPager;

import com.android.jobber.Adapters.ViewPagerAdapter;
import com.android.jobber.R;
import com.android.jobber.Ui.Activities.Login.LoginActivity;
import com.android.jobber.Ui.Activities.MainActivity.MainActivity;
import com.android.jobber.common.SqlHelper.myDbAdapter;
import com.android.jobber.common.base.BaseActivity;

public class Slider extends BaseActivity {

    private ViewPager viewPager;
    private String type;
    private String DEFAULT=null;
    private Button button;
    private ImageView imageView;
    private myDbAdapter helper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.slider_activity);
        initializeViews();
        setListeners();
    }

    public void hello (){

    }
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

    }

    @Override
    protected void initializeViews() {
        viewPager = findViewById(R.id.view_pager);
        helper = new myDbAdapter(this);
        button = findViewById(R.id.next_btn);
        button.setVisibility(View.GONE);
        imageView = findViewById(R.id.image_next);
        final ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(this);
        viewPager.setAdapter(viewPagerAdapter);
        String data = helper.getData();

        if (data.length()>0){
            startActivity(new Intent(getApplicationContext(), MainActivity.class)
                    .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK));
        }else {
            SharedPreferences sharedPreferences_ret = getSharedPreferences("Data", Context.MODE_PRIVATE);
            type = sharedPreferences_ret.getString("kind_person", DEFAULT);
            if (type != null) {
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
            }

        }
    }

    @Override
    protected void setListeners() {
        imageView.setOnClickListener(imageViewListener);
        viewPager.addOnPageChangeListener(viewPagerListener);
        button.setOnClickListener(buttonListener);
    }

    private View.OnClickListener buttonListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            type="test";
            SharedPreferences sharedPreferences=getSharedPreferences("Data",MODE_PRIVATE);
            SharedPreferences.Editor editor=sharedPreferences.edit();
            editor.putString("kind_person",type);
            editor.apply();
            startActivity(new Intent(getApplicationContext(),LoginActivity.class)
                    .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK));
        }
    };
    private ViewPager.OnPageChangeListener viewPagerListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            if (position==2) {
                button.setVisibility(View.VISIBLE);
                imageView.setVisibility(View.GONE);
            }else
            {
                button.setVisibility(View.GONE);
                imageView.setVisibility(View.VISIBLE);
            }
        }

        @Override
        public void onPageSelected(int position) {

        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };


    private View.OnClickListener imageViewListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
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
    };
}
