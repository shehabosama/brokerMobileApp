package com.android.jobber.Ui.fragments.PublishingFragment;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.android.jobber.R;
import com.android.jobber.common.model.Listitem;

import java.util.ArrayList;

public class MyCustomeAdapter extends BaseAdapter {

    private Activity context;
    ArrayList<Listitem> listitems;

    public MyCustomeAdapter(Activity context, ArrayList<Listitem> items){
        this.listitems=items;
        this.context = context;
    }

    @Override
    public int getCount() {
        return listitems.size();
    }

    @Override
    public Object getItem(int i) {
        return listitems.get(i).name;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        LayoutInflater layoutInflater=context.getLayoutInflater();
        View view3=layoutInflater.inflate(R.layout.row_lay,null);
        final TextView textView=(TextView)view3.findViewById(R.id.textname);
        textView.setText(listitems.get(i).name);
        return view3;
    }
}