package com.android.jobber.Ui.fragments.ReviewFragment;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.android.jobber.R;
import com.android.jobber.common.HelperStuffs.AppPreferences;
import com.android.jobber.common.HelperStuffs.Constants;
import com.android.jobber.common.HelperStuffs.Message;
import com.android.jobber.common.base.BaseFragment;
import com.android.jobber.common.model.ReviewResponse;
import com.android.jobber.common.model.Reviews;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class ReviewFragment extends BaseFragment implements ReviewContract.Model.onFinishedListener,ReviewContract.View, ReviewAdapter.ReviewInterAction {

    private SwipeRefreshLayout srlList;
    private RecyclerView recyclerView;
    private PresenterReviews presenter;
    private List<Reviews> reviews;
    private String id;
    public ReviewFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_review, container, false);

        initializeViews(view);
        setListeners();
        return view ;
    }

    @Override
    protected void initializeViews(View v) {

        recyclerView = v.findViewById(R.id.recycler_view);
        srlList = v.findViewById(R.id.srlList);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        reviews = new ArrayList<>();
        presenter = new PresenterReviews(this,this);
        if(getArguments()!=null){
            System.out.println("test log");
            presenter.performGetReviews(getArguments().getString(Constants.BundleKeys.USER_ID_FROM_JOBBER).toString());

        }else{
            presenter.performGetReviews(AppPreferences.getString(Constants.AppPreferences.LOGGED_IN_USER_KEY,getActivity(),"0"));

        }

    }

    @Override
    protected void setListeners() {
        srlList.setOnRefreshListener(srlListListener);
    }
    private SwipeRefreshLayout.OnRefreshListener srlListListener = new SwipeRefreshLayout.OnRefreshListener() {
        @Override
        public void onRefresh() {
            presenter.performGetReviews(AppPreferences.getString(Constants.AppPreferences.LOGGED_IN_USER_KEY,getActivity(),"0"));
        }
    };
    @Override
    public void onFinished(String result) {
        Message.message(getActivity(),result);
    }

    @Override
    public void onFailuer(Throwable t) {

    }

    @Override
    public void loadReviews(ReviewResponse reviewResponse) {
        reviews.clear();
        reviews.addAll(reviewResponse.getReviews());
        ReviewAdapter adapter = new ReviewAdapter(getActivity(),reviews,this);
        recyclerView.setAdapter(adapter);
        hideProgress();
    }

    @Override
    public void showProgress() {
        srlList.setRefreshing(true);
    }

    @Override
    public void hideProgress() {
        srlList.setRefreshing(false);
    }
}
