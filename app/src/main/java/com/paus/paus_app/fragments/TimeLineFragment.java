package com.paus.paus_app.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;

import com.paus.paus_app.R;


public class TimeLineFragment extends Fragment {

    public TimeLineFragment()
    {

    }
    ScrollView scrollView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_time_line, container, false);
        scrollView = view.findViewById(R.id.scroll);
        scrollView.smoothScrollTo(0,0);
        return view;
    }


}
