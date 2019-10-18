package com.paus.paus_app.fragments;


import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;

import com.paus.paus_app.R;


public class Installment_fragemnt extends Fragment {

    public Installment_fragemnt() {
        // Required empty public constructor
    }



    private HorizontalScrollView scrollView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_installment, container, false);

        scrollView = view.findViewById(R.id.horizontal_scroll);
        scrollView.smoothScrollTo(0,0);
        return view;
    }


}
