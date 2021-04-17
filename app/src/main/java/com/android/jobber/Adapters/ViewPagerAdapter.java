package com.android.jobber.Adapters;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.android.jobber.R;
import com.android.jobber.common.HelperStuffs.AppPreferences;

import static android.text.Layout.JUSTIFICATION_MODE_INTER_WORD;

public class ViewPagerAdapter extends PagerAdapter {
    Context context;
    Activity activity;
    LayoutInflater layoutInflater;
    private boolean swipeLocked;
    private Integer[] images = {R.drawable.ic_account_circle_black_24dp, R.mipmap.ic_launcher_round, R.drawable.ic_airport_shuttle_black_24dp};
    private String[] text = {"."
            ,
            "The Groove offers a cosmopolitan, coastal-living experience to meet everyone’s desires, through a year-round seaside destination that is meant to bring you magical, fun-filled moments and" +
                    " unforgettable memories with family and friends. ",
            "how to use Dm development app \n Lorem ipsum dolor sit amet, vel ponderum convenire cu, pro te aeterno epicurei. Harum democritum id vel, est cu summo gloriatur, mel everti" +
                    " salutatus eu. Qui sale possim causae ex, cum at vero nusquam deleniti, ius minimum interpretaris ad. Vis fugit error cu, paulo delectus mei no. Te cum tale mutat, congue verear has te," +
                    " ei minim debet erant mea.\n" +
            "\n" +
            "Mel homero invidunt ea. Vis te dico suas scaevola, fabulas eloquentiam "
};

    public ViewPagerAdapter(Context context , Activity activity) {
        this.context = context;
        this.activity = activity;
    }

    @Override
    public int getCount() {
        return text.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return view == o;
    }


    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, final int position) {
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.coustom_layout, null);
        LinearLayout linearLayout = view.findViewById(R.id.lin_mode);
        Button btn = ((Activity)context).findViewById(R.id.next_btn);
        RadioGroup radioGroup = view.findViewById(R.id.radio_group_mode);
        RadioButton rDMode = view.findViewById(R.id.radio_dark);
        RadioButton rLMode = view.findViewById(R.id.radio_light);
        final ImageView imageView = (ImageView) view.findViewById(R.id.image);
        TextView textView = view.findViewById(R.id.text_test);

        if(AppPreferences.getString("mode_key",context,"0").equals("D")){
            rDMode.setChecked(true);
        }else if(AppPreferences.getString("mode_key",context,"0").equals("L")){
            rLMode.setChecked(true);
        }

        radioGroup.setOnCheckedChangeListener(radioGroupListener);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            textView.setJustificationMode(JUSTIFICATION_MODE_INTER_WORD);
        }


        if(position == 0 || position == 2)
        {
            if(position == 0){
                linearLayout.setVisibility(View.VISIBLE);
            }

            imageView.setVisibility(View.INVISIBLE);
            textView.setText(text[position]);


        }else
        {
            imageView.setVisibility(View.VISIBLE);
            imageView.setImageResource(images[position]);
            textView.setText(text[position]);
        }


        ViewPager vp = ((ViewPager) container);
        vp.addView(view, 0);

        return view;
    }
    private RadioGroup.OnCheckedChangeListener radioGroupListener = new RadioGroup.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup radioGroup, int i) {
            View radioButton = radioGroup.findViewById(i);
            int index = radioGroup.indexOfChild(radioButton);

            switch (index) {
                case 0:

                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                        AppPreferences.setString("mode_key","D",context);
                        Toast.makeText(context, "Dark mode is open", Toast.LENGTH_SHORT).show();


                    restartActivity(activity);
                    break;
                case 1:

                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                        AppPreferences.setString("mode_key","L",context);
                        Toast.makeText(context, "Light mode is open", Toast.LENGTH_SHORT).show();

                    restartActivity(activity);
                    break;
            }
        }
    };

    public static void restartActivity(Activity activity){
        if (Build.VERSION.SDK_INT >= 11) {
            activity.recreate();
        } else {
            activity.finish();
            activity.startActivity(activity.getIntent());
        }
    }
    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        ViewPager vp = (ViewPager) container;
        View view = (View) object;
        vp.removeView(view);

    }



}