package com.android.jobber.Ui.fragments.AllJobbersFragemnts;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.android.jobber.R;
import com.android.jobber.Ui.fragments.JobberAccountFragment.JobberAccountFragment;
import com.android.jobber.common.HelperStuffs.Constants;
import com.android.jobber.common.base.BaseFragment;
import com.android.jobber.common.model.JobberUsers;
import com.android.jobber.common.model.JobberUsersResponse;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class AllJobberFragment extends BaseFragment implements AllJobberContract.Model.onFinishedListener,AllJobberContract.View , JobberUserAdapter.JobberUserInterAction {


    private PresenterAllJobber presenter;
    private List<JobberUsers> jobberUsers;
    private SwipeRefreshLayout srlList;
    private RecyclerView recyclerView;
    public AllJobberFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_all_jobber, container, false);
        initializeViews(view);
        setListeners();
        return view ;

    }

    @Override
    protected void initializeViews(View v) {
        recyclerView = v.findViewById(R.id.recycler_view);
        srlList = v.findViewById(R.id.srlList);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        presenter = new PresenterAllJobber(this,this);
        jobberUsers = new ArrayList<>();
        presenter.performGetAllJobber();
    }

    @Override
    protected void setListeners() {
        srlList.setOnRefreshListener(srlListRefreshListener);

    }
    SwipeRefreshLayout.OnRefreshListener srlListRefreshListener = new SwipeRefreshLayout.OnRefreshListener() {
        @Override
        public void onRefresh() {
            presenter.performGetAllJobber();
        }
    };
    @Override
    public void onFinished(String result) {

    }

    @Override
    public void loadAllJobberUsers(JobberUsersResponse jobberUsersResponse) {
        jobberUsers.clear();
        jobberUsers.addAll(jobberUsersResponse.getJobberUsers());
        JobberUserAdapter adapter = new JobberUserAdapter(getActivity(),jobberUsers,this);
        recyclerView.setAdapter(adapter);
        hideProgress();

    }

    @Override
    public void onFailuer(Throwable t) {

    }

    @Override
    public void showProgress() {

        srlList.setRefreshing(true);
    }

    @Override
    public void hideProgress() {
        srlList.setRefreshing(false);
    }

    @Override
    public void onClickItem(JobberUsers jobberUsers) {
        replaceFragment(R.id.container_body, JobberAccountFragment.newInstance(), String.valueOf(Constants.BottomNavConstants.JOBBER_ACCOUNT),Constants.BundleKeys.USER_ID,jobberUsers);

    }
}
