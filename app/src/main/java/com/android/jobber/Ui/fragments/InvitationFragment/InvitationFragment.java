package com.android.jobber.Ui.fragments.InvitationFragment;

import android.os.Build;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.jobber.R;


public class InvitationFragment extends Fragment {


    private String mParam1;
    private String mParam2;

    public InvitationFragment() {
        // Required empty public constructor
    }



    public static InvitationFragment newInstance() {
        InvitationFragment fragment = new InvitationFragment();
        return fragment;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES) {
           getActivity().setTheme(R.style.DarkTheme);
        } else {
            getActivity().setTheme(R.style.LightTheme);
        }

        View view =inflater.inflate(R.layout.fragment_invitation, container, false);
        TextView tvChangeTheme =view.findViewById(R.id.tvChangeTheme);
        tvChangeTheme.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES) {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                } else {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                }

                // restartFragment();

            }
        });

        return view;
    }

    private void restartFragment() {
//        FragmentTransaction ft = getFragmentManager().beginTransaction();
//        if (Build.VERSION.SDK_INT >= 26) {
//            ft.setReorderingAllowed(false);
//        }
//        ft.detach(this).attach(this).commit();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            // Refresh your fragment here
            getFragmentManager().beginTransaction().detach(this).attach(this).commit();
            Log.i("IsRefresh", "Yes");
        }
    }
}