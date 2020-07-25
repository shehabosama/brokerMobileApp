package com.android.jobber.Adapters;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.android.jobber.R;

import static android.text.Layout.JUSTIFICATION_MODE_INTER_WORD;

public class ViewPagerAdapter extends PagerAdapter {
    Context context;
    LayoutInflater layoutInflater;
    private boolean swipeLocked;
    private Integer[] images = {R.drawable.ic_account_circle_black_24dp, R.mipmap.ic_launcher_round, R.drawable.ic_airport_shuttle_black_24dp};
    private String[] text = {"DM developments is a Tourism Development company targeting to become the leading company in the field, covering the entire property chain.\n" +
            "DM developments , fueled by experience, owned by a board of expertise whom contributed building new cities.\n" +
            "DM developments  aims to change urban culture through providing sustainable, well-designed, luxurious and environmentally-friendly homes to please and ease our clients’ lives through creative community-based societies."
            ,
            "The Groove offers a cosmopolitan, coastal-living experience to meet everyone’s desires, through a year-round seaside destination that is meant to bring you magical, fun-filled moments and" +
                    " unforgettable memories with family and friends. ",
            "how to use Dm development app \n Lorem ipsum dolor sit amet, vel ponderum convenire cu, pro te aeterno epicurei. Harum democritum id vel, est cu summo gloriatur, mel everti" +
                    " salutatus eu. Qui sale possim causae ex, cum at vero nusquam deleniti, ius minimum interpretaris ad. Vis fugit error cu, paulo delectus mei no. Te cum tale mutat, congue verear has te," +
                    " ei minim debet erant mea.\n" +
            "\n" +
            "Mel homero invidunt ea. Vis te dico suas scaevola, fabulas eloquentiam "
};

    public ViewPagerAdapter(Context context) {
        this.context = context;
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

        Button btn = ((Activity)context).findViewById(R.id.next_btn);

        final ImageView imageView = (ImageView) view.findViewById(R.id.image);
        TextView textView = view.findViewById(R.id.text_test);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            textView.setJustificationMode(JUSTIFICATION_MODE_INTER_WORD);
        }

        if(position == 0 || position == 2)
        {
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

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        ViewPager vp = (ViewPager) container;
        View view = (View) object;
        vp.removeView(view);

    }



}