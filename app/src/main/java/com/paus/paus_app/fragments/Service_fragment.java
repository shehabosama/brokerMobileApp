package com.paus.paus_app.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.paus.paus_app.Adapters.Service_Adapter;
import com.paus.paus_app.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class Service_fragment extends Fragment {


    public Service_fragment() {
        // Required empty public constructor
    }


    private RecyclerView recyclerView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view =  inflater.inflate(R.layout.fragment_service, container, false);

        recyclerView = view.findViewById(R.id.recycler_service);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));


        int[] data_image = {R.drawable.tools,R.drawable.saw,R.drawable.pipe,R.drawable.roller};
        String[] date_text = {"Mechanical","carpenter","Plumber","painter"};

        Service_Adapter service_adapter = new Service_Adapter(data_image,date_text,getActivity());
        recyclerView.setAdapter(service_adapter);
        return view;
    }

}
