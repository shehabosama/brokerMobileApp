package com.android.jobber.Ui.fragments;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.jobber.Adapters.Service_Adapter;
import com.android.jobber.R;

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
