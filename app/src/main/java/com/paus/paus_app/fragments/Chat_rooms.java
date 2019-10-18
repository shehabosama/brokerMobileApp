package com.paus.paus_app.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.paus.paus_app.Chat_activity;
import com.paus.paus_app.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class Chat_rooms extends Fragment {


    public Chat_rooms() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_chat_rooms, container, false);
        CardView cardView = view.findViewById(R.id.first_card);
        CardView cardView1 = view.findViewById(R.id.second_card);
        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(),Chat_activity.class));
            }
        });
        cardView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(),Chat_activity.class));
            }
        });
        return view;
    }

}
