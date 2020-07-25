package com.android.jobber.Ui.fragments.TimeLineFragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.android.jobber.R;
import com.android.jobber.Ui.fragments.JobberAccountFragment.JobberAccountFragment;
import com.android.jobber.common.HelperStuffs.AppPreferences;
import com.android.jobber.common.HelperStuffs.Constants;
import com.android.jobber.common.HelperStuffs.Message;
import com.android.jobber.common.base.BaseFragment;
import com.android.jobber.common.model.Flats.Flat;
import com.android.jobber.common.model.Flats.FlatsImage;
import com.android.jobber.common.model.Flats.FlatsResponse;
import com.android.jobber.common.model.MyFavorite;
import com.android.jobber.common.model.MyFavoriteResponse;
import com.miguelcatalan.materialsearchview.MaterialSearchView;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class TimeLineFragment extends BaseFragment implements TimeLineContract.View,TimeLineContract.Model.onFinishedListener , FlatAdapter.FlatAdapterInterAction {

    public TimeLineFragment()
    {

    }
    private SwipeRefreshLayout srlList;
    private RecyclerView recyclerView;
    private List<MyFavorite>favoriteList;
    private PresenterTimeLine presenter;
    private List<Flat> flatList;
    private String bundil;
    private CustomeDialogFilterTimeLine customeDialogFilterTimeLine;
    private MaterialSearchView searchView;

    public static TimeLineFragment newInstance() {
        return new TimeLineFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_time_line, container, false);
        initializeViews(view);
        setListeners();
        return view;
    }


    @Override
    protected void initializeViews(View v) {
        customeDialogFilterTimeLine = new CustomeDialogFilterTimeLine(getActivity());
        setHasOptionsMenu(true);

        searchView = ((Activity)getActivity()).findViewById(R.id.search_view);
       // MaterialSearchView materialSearchView = new MaterialSearchView(getActivity());
        searchView.closeSearch();
        recyclerView = v.findViewById(R.id.recycler_view);
        srlList = v.findViewById(R.id.srlList);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        presenter = new PresenterTimeLine(this,this);
        flatList = new ArrayList<>();
        favoriteList = new ArrayList<>();
        presenter.performGetAllFlats(AppPreferences.getString(Constants.AppPreferences.LOGGED_IN_USER_KEY,getActivity(),"0"));

    }

    @Override
    protected void setListeners() {
        srlList.setOnRefreshListener(srlListRefreshListener);
        searchView.setOnSearchViewListener(searchViewListener);
        searchView.setOnQueryTextListener(onQueryTextListener);

    }
    private MaterialSearchView.SearchViewListener searchViewListener = new MaterialSearchView.SearchViewListener() {
        @Override
        public void onSearchViewShown() {

        }

        @Override
        public void onSearchViewClosed() {
           // presenter.performGetAllFlats(AppPreferences.getString(Constants.AppPreferences.LOGGED_IN_USER_KEY,getActivity(),"0"));
            FlatAdapter flatAdapter = new FlatAdapter(flatList, favoriteList, getActivity(), new FlatAdapter.FlatAdapterInterAction() {
                @Override
                public void onRefrishing() {

                }



                @Override
                public void onClickMenu(final Flat flat) {
                    //Resources res = getResources();

                    showDialog(flat);
                }
                @Override
                public void onClickFavorite(Flat car) {
                    presenter.performAddItemFavorite(AppPreferences.getString(Constants.AppPreferences.LOGGED_IN_USER_KEY,getActivity(),"0"),car.getId());
                }
                @Override
                public void onClickUnFavorite(Flat flat) {
                    presenter.performDeleteItemFavorite(AppPreferences.getString(Constants.AppPreferences.LOGGED_IN_USER_KEY,getActivity(),"0"),flat.getId(),"delete");


                }
                @Override
                public void onClickPhoto(FlatsImage flatsImage) {
                    CustomeDialogViewPic customeDialogViewPic = new CustomeDialogViewPic();
                    customeDialogViewPic.showDialog(getActivity(),flatsImage.getImageName());
                }

                @Override
                public void onClickItem(Flat flat) {
                    replaceFragment(R.id.container_body, JobberAccountFragment.newInstance(), String.valueOf(Constants.BottomNavConstants.JOBBER_ACCOUNT),Constants.BundleKeys.USER_ID,flat);

                }
            });
            recyclerView.setAdapter(flatAdapter);

        }
    };
    private MaterialSearchView.OnQueryTextListener onQueryTextListener = new MaterialSearchView.OnQueryTextListener() {
        @Override
        public boolean onQueryTextSubmit(String query) {
            return false;
        }

        @Override
        public boolean onQueryTextChange(String newText) {
            if(newText != null && !newText.isEmpty()){
                //   SearchForPeopleAndFrinds(newText);
                List<Flat> found = new ArrayList<>();
                for (Flat flat:flatList) {
                    if(flat.getUsername().contains(newText))
                        found.add(flat);
                    }

                    FlatAdapter flatAdapter = new FlatAdapter(found, favoriteList, getActivity(), new FlatAdapter.FlatAdapterInterAction() {
                        @Override
                        public void onRefrishing() {

                        }



                        @Override
                        public void onClickMenu(final Flat flat) {
                            //Resources res = getResources();
                            showDialog(flat);
                        }
                        @Override
                        public void onClickFavorite(Flat car) {
                            presenter.performAddItemFavorite(AppPreferences.getString(Constants.AppPreferences.LOGGED_IN_USER_KEY,getActivity(),"0"),car.getId());
                        }
                        @Override
                        public void onClickUnFavorite(Flat flat) {
                            presenter.performDeleteItemFavorite(AppPreferences.getString(Constants.AppPreferences.LOGGED_IN_USER_KEY,getActivity(),"0"),flat.getId(),"delete");


                        }
                        @Override
                        public void onClickPhoto(FlatsImage flatsImage) {
                            CustomeDialogViewPic customeDialogViewPic = new CustomeDialogViewPic();
                            customeDialogViewPic.showDialog(getActivity(),flatsImage.getImageName());
                        }

                        @Override
                        public void onClickItem(Flat flat) {
                            replaceFragment(R.id.container_body, JobberAccountFragment.newInstance(), String.valueOf(Constants.BottomNavConstants.JOBBER_ACCOUNT),Constants.BundleKeys.USER_ID,flat);

                        }
                    });
                    recyclerView.setAdapter(flatAdapter);

            }else{
                FlatAdapter flatAdapter = new FlatAdapter(flatList, favoriteList, getActivity(), new FlatAdapter.FlatAdapterInterAction() {
                    @Override
                    public void onRefrishing() {

                    }



                    @Override
                    public void onClickMenu(final Flat flat) {
                        //Resources res = getResources();
                        showDialog(flat);
                    }
                    @Override
                    public void onClickFavorite(Flat car) {
                        presenter.performAddItemFavorite(AppPreferences.getString(Constants.AppPreferences.LOGGED_IN_USER_KEY,getActivity(),"0"),car.getId());
                    }
                    @Override
                    public void onClickUnFavorite(Flat flat) {
                        presenter.performDeleteItemFavorite(AppPreferences.getString(Constants.AppPreferences.LOGGED_IN_USER_KEY,getActivity(),"0"),flat.getId(),"delete");


                    }
                    @Override
                    public void onClickPhoto(FlatsImage flatsImage) {
                        CustomeDialogViewPic customeDialogViewPic = new CustomeDialogViewPic();
                        customeDialogViewPic.showDialog(getActivity(),flatsImage.getImageName());
                    }

                    @Override
                    public void onClickItem(Flat flat) {
                        replaceFragment(R.id.container_body, JobberAccountFragment.newInstance(), String.valueOf(Constants.BottomNavConstants.JOBBER_ACCOUNT),Constants.BundleKeys.USER_ID,flat);

                    }
                });
                recyclerView.setAdapter(flatAdapter);
            }
            return true;
        }
    };
    private SwipeRefreshLayout.OnRefreshListener srlListRefreshListener = new SwipeRefreshLayout.OnRefreshListener() {
        @Override
        public void onRefresh() {



            presenter.performGetAllFlats(AppPreferences.getString(Constants.AppPreferences.LOGGED_IN_USER_KEY,getActivity(),"0"));


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
    public void loadAllFlats(FlatsResponse flatsResponse, MyFavoriteResponse favoriteResponse) {
        flatList.clear();
        flatList.addAll(flatsResponse.getFlats());
        favoriteList.addAll(favoriteResponse.getMyFavorite());
        FlatAdapter flatAdapter = new FlatAdapter(flatList,favoriteList,getActivity(),this);
        recyclerView.setAdapter(flatAdapter);
        hideProgress();
        if(customeDialogFilterTimeLine !=null){
            customeDialogFilterTimeLine.hideDialog();

        }

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
    public void onRefrishing() {

    }



    @Override
    public void onClickMenu(final Flat flat) {
        //Resources res = getResources();
        showDialog(flat);
    }

    public void showDialog(final Flat flat){
        Resources res = getResources();
        final CharSequence option[]=new CharSequence[]{
                res.getString(R.string.View_Location),
                res.getString(R.string.Call_him),
                AppPreferences.getBoolean(
                        Constants.AppPreferences.IS_ADMIN,
                        getActivity(),
                        false) &&
                        flat.getUserId().equals(AppPreferences.getString(
                                Constants.AppPreferences.LOGGED_IN_USER_KEY,
                                getActivity(),
                                ""))?res.getString(R.string.Delete_Post):res.getString(R.string.Report),
                res.getString(R.string.Book_now)
        };

        AlertDialog.Builder builder =new AlertDialog.Builder(getActivity());
        builder.setTitle(res.getString(R.string.Menu));
        builder.setItems(option, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                switch(which){
                    case 0:
                        Uri uri = Uri.parse("google.navigation:q="+flat.getLat()+","+flat.getLang());
                        Intent intent = new Intent(Intent.ACTION_VIEW,uri);
                        intent.setPackage("com.google.android.apps.maps");
                        startActivity(intent);
                        break;
                    case 1:
                        Intent callIntent = new Intent(Intent.ACTION_DIAL);
                        callIntent.setData(Uri.parse("tel:"+flat.getPhone_no()));
                        startActivity(callIntent);
                        break;
                    case 2:
                        if(option[2].equals("Delete Post")){
                            presenter.performDeleteProduct(flat.getId());
                        }else{
                            Message.message(getActivity(),"third Item");

                        }

                        break;
                    case 3:
                        Date date= new Date();
                        //getTime() returns current time in milliseconds
                        long time = date.getTime();
                        //Passed the milliseconds to constructor of Timestamp class
                        Timestamp ts = new Timestamp(time);
                        presenter.performOrderRequest(AppPreferences.getString(Constants.AppPreferences.LOGGED_IN_USER_KEY,getActivity(), ""),
                                flat.getUserId(),
                                flat.getId(),
                                AppPreferences.getString(Constants.AppPreferences.USER_TOKEN,getActivity(),""),String.valueOf(ts.getTime()));

                        break;
                }

            }
        });
        builder.show();
    }
    @Override
    public void onClickFavorite(Flat car) {
        presenter.performAddItemFavorite(AppPreferences.getString(Constants.AppPreferences.LOGGED_IN_USER_KEY,getActivity(),"0"),car.getId());
    }
    @Override
    public void onClickUnFavorite(Flat flat) {
        presenter.performDeleteItemFavorite(AppPreferences.getString(Constants.AppPreferences.LOGGED_IN_USER_KEY,getActivity(),"0"),flat.getId(),"delete");


    }
    @Override
    public void onClickPhoto(FlatsImage flatsImage) {
        CustomeDialogViewPic customeDialogViewPic = new CustomeDialogViewPic();
        customeDialogViewPic.showDialog(getActivity(),flatsImage.getImageName());
    }

    @Override
    public void onClickItem(Flat flat) {
        replaceFragment(R.id.container_body, JobberAccountFragment.newInstance(), String.valueOf(Constants.BottomNavConstants.JOBBER_ACCOUNT),Constants.BundleKeys.USER_ID,flat);

    }
    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        getActivity().getMenuInflater().inflate(R.menu.time_line_menu,menu);

        MenuItem item = menu.findItem(R.id.action_by_name);
        searchView.setMenuItem(item);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()){

            case R.id.action_multi_option:
                customeDialogFilterTimeLine.showDialog(getActivity(),presenter);
                break;
            case R.id.action_by_name:
                break;

        }
        return super.onOptionsItemSelected(item);

    }

}
