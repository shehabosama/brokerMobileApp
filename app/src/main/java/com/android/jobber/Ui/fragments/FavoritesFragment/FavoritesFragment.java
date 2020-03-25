package com.android.jobber.Ui.fragments.FavoritesFragment;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
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
import com.android.jobber.Ui.fragments.TimeLineFragment.CustomeDialogViewPic;
import com.android.jobber.common.HelperStuffs.AppPreferences;
import com.android.jobber.common.HelperStuffs.Constants;
import com.android.jobber.common.HelperStuffs.Message;
import com.android.jobber.common.base.BaseFragment;
import com.android.jobber.common.model.Flats.FlatsImage;
import com.android.jobber.common.model.MyFavorite;
import com.android.jobber.common.model.MyFavoriteResponse;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
/**
 * A simple {@link Fragment} subclass.
 */
public class FavoritesFragment extends BaseFragment implements FavoriteContract.Model.onFinishedListener,FavoriteContract.View, FlatAdapter.MyFavAdapterInterAction {
    private SwipeRefreshLayout srlList;
    private RecyclerView recyclerView;
    private List<MyFavorite> favoriteList;
    private PresenterFavotite presenter;
    public FavoritesFragment() {
        // Required empty public constructor
    }
    public FavoritesFragment newInstance(){
        return new FavoritesFragment();
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_blank2, container, false);
        initializeViews(view);
        setListeners();
        return view ;
    }
    @Override
    protected void initializeViews(View v) {
        recyclerView = v.findViewById(R.id.recycler_view);
        srlList = v.findViewById(R.id.srlList);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        favoriteList = new ArrayList<>();
        presenter = new PresenterFavotite(this,this);
        presenter.performMyFavorites(AppPreferences.getString(Constants.AppPreferences.LOGGED_IN_USER_KEY,getActivity(),"0"));
    }
    @Override
    protected void setListeners() {
        srlList.setOnRefreshListener(srlListRefreshListener);
    }
    private SwipeRefreshLayout.OnRefreshListener srlListRefreshListener = new SwipeRefreshLayout.OnRefreshListener() {
        @Override
        public void onRefresh() {
            presenter.performMyFavorites(AppPreferences.getString(Constants.AppPreferences.LOGGED_IN_USER_KEY,getActivity(),"0"));
        }
    };
    @Override
    public void onFinished(String result) {
        Message.message(getActivity(),result);
    }
    @Override
    public void getMyFavorite(MyFavoriteResponse myFavoriteResponse) {
        favoriteList.clear();
        favoriteList.addAll(myFavoriteResponse.getMyFavorite());
        FlatAdapter flatAdapter = new FlatAdapter(favoriteList,getActivity(),this);
        recyclerView.setAdapter(flatAdapter);
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
    public void onRefrishing() {
    }
    @Override
    public void onClickFavorite(MyFavorite car) {
    }
    @Override
    public void onClickMenu(final MyFavorite flat) {
        //Resources res = getResources();

        final CharSequence option[]=new CharSequence[]{
                "View Location",
                "Call him",
                AppPreferences.getBoolean(
                        Constants.AppPreferences.IS_ADMIN,
                        getActivity(),
                        false) &&
                        flat.getUser_id().equals(AppPreferences.getString(
                                Constants.AppPreferences.LOGGED_IN_USER_KEY,
                                getActivity(),
                                ""))?"Delete Post":"Report",
                "Book now"
        };

        AlertDialog.Builder builder =new AlertDialog.Builder(getActivity());
        builder.setTitle("Menu");
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
                        callIntent.setData(Uri.parse("tel:"+flat.getPhoneNo()));
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
                                flat.getUser_id(),
                                flat.getId(),
                                AppPreferences.getString(Constants.AppPreferences.USER_TOKEN,getActivity(),""),String.valueOf(ts.getTime()));

                        break;
                }

            }
        });
        builder.show();
    }
    @Override
    public void onClickUnFavorite(MyFavorite flat) {
        presenter.performDeleteItemFavorite(AppPreferences.getString(Constants.AppPreferences.LOGGED_IN_USER_KEY,getActivity(),"0"),flat.getId(),"delete");

    }
    @Override
    public void onClickPhoto(FlatsImage flatsImage) {
        CustomeDialogViewPic customeDialogViewPic = new CustomeDialogViewPic();
        customeDialogViewPic.showDialog(getActivity(),flatsImage.getImageName());
    }
    @Override
    public void onClickItem(MyFavorite flat) {
        replaceFragment(R.id.container_body, JobberAccountFragment.newInstance(), String.valueOf(Constants.BottomNavConstants.JOBBER_ACCOUNT),Constants.BundleKeys.USER_ID,flat);

    }
}
