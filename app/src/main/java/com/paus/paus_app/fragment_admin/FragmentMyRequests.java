package com.paus.paus_app.fragment_admin;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.paus.paus_app.R;
import com.paus.paus_app.common.HelperStuffs.PermissionHandlerFragment;

public class FragmentMyRequests extends PermissionHandlerFragment {

    private Context context;

    public FragmentMyRequests() {
        // Required empty public constructor
    }
    public static FragmentMyRequests newInstance() {
        return new FragmentMyRequests();
    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        context = getActivity();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_my_requests, container, false);
    }


    @Override
    protected void initializeViews(View v) {

    }

    @Override
    protected void setListeners() {

    }
}
