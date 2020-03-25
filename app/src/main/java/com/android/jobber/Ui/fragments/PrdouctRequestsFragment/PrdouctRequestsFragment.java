package com.android.jobber.Ui.fragments.PrdouctRequestsFragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.android.jobber.R;
import com.android.jobber.common.HelperStuffs.AppPreferences;
import com.android.jobber.common.HelperStuffs.Constants;
import com.android.jobber.common.HelperStuffs.LastseenTime;
import com.android.jobber.common.HelperStuffs.Message;
import com.android.jobber.common.base.BaseFragment;
import com.android.jobber.common.model.Flats.FlatsImage;
import com.android.jobber.common.model.ProductRequest;
import com.android.jobber.common.model.RequestResponse;

import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;


public class PrdouctRequestsFragment extends BaseFragment implements ProductRequestContract.View,ProductRequestContract.Model.onFinishedListener , ProductRequestAdapter.FlatAdapterInterAction {


    public PrdouctRequestsFragment() {
        // Required empty public constructor
    }
    private SwipeRefreshLayout srlList;
    private RecyclerView recyclerView;
    private List<ProductRequest> productRequests;
    private PresenterPrdouctRequest presenter;

    public static PrdouctRequestsFragment newInstance() {

        return new PrdouctRequestsFragment();
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_prdouct_requests, container, false);
        initializeViews(view);
        setListeners();
        return view ;
    }

    @Override
    protected void initializeViews(View v) {
        recyclerView = v.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        srlList = v.findViewById(R.id.srlList);
        productRequests = new ArrayList<>();
        presenter = new PresenterPrdouctRequest(this,this,getActivity());
        presenter.performGetRequests(AppPreferences.getString(Constants.AppPreferences.LOGGED_IN_USER_KEY,getActivity(),"0"));
    }

    @Override
    protected void setListeners() {
        srlList.setOnRefreshListener(srlListRefreshListener);
    }
    private SwipeRefreshLayout.OnRefreshListener srlListRefreshListener = new SwipeRefreshLayout.OnRefreshListener() {
        @Override
        public void onRefresh() {
            presenter.performGetRequests(AppPreferences.getString(Constants.AppPreferences.LOGGED_IN_USER_KEY,getActivity(),"0"));

        }
    };
    @Override
    public void onFinished(String result) {
        Message.message(getActivity(),result);

    }

    @Override
    public void loadRequestData(RequestResponse requestResponse) {
        productRequests.clear();
        productRequests.addAll(requestResponse.getProductRequest());

        ProductRequestAdapter adapter = new ProductRequestAdapter(productRequests,getActivity(),this);
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
    public void onRefrishing() {

    }

    @Override
    public void onClickFavorite(ProductRequest product) {

    }

    @Override
    public void onClickMenu(final ProductRequest product) {
        final LastseenTime getTime=new LastseenTime();
        long lastseen=Long.parseLong(product.getTimestamp());
        String lastseenDisplayTime=getTime.getTimeAgo(lastseen,getActivity()).toString();
        Log.e(TAG, "onClick: "+lastseenDisplayTime );
        final CharSequence option[]=new CharSequence[]{
                "View Location",
                "Call him",
              AppPreferences.getBoolean(Constants.AppPreferences.IS_ADMIN,getActivity(),false)
                      ? "Delete Request"
                      :lastseenDisplayTime.equals("an hour ago")
                      ?""
                      :"Delete Request"

        };

        AlertDialog.Builder builder =new AlertDialog.Builder(getActivity());
        builder.setTitle("Menu");
        builder.setItems(option, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                switch(which){
                    case 0:
                        Uri uri = Uri.parse("google.navigation:q="+product.getLat()+","+product.getLang());
                        Intent intent = new Intent(Intent.ACTION_VIEW,uri);
                        intent.setPackage("com.google.android.apps.maps");
                        startActivity(intent);
                        break;
                    case 1:
                        Intent callIntent = new Intent(Intent.ACTION_DIAL);
                        callIntent.setData(Uri.parse("tel:"+product.getPhoneNo()));
                        startActivity(callIntent);
                        break;
                    case 2:
                       if(TextUtils.isEmpty(option[2])){
                           Message.message(getActivity(),"is Empty");
                       }else{
                           Message.message(getActivity(),"still in the range");
                       }


                        break;

                }

            }
        });
        builder.show();
    }

    @Override
    public void onClickUnFavorite(ProductRequest product) {

    }

    @Override
    public void onClickPhoto(FlatsImage flatsImage) {

    }

    @Override
    public void onClickItem(ProductRequest product) {

    }

    @Override
    public void onClickRejectRequest(ProductRequest product) {

        presenter.performConfirmation(product.getRequestId(),
                AppPreferences.getString(Constants.AppPreferences.LOGGED_IN_USER_KEY, getActivity(),"0"),
                "2",
                product.getId(),
                AppPreferences.getString(Constants.AppPreferences.USER_TOKEN,getActivity(),""));
    }

    @Override
    public void onClickAcceptRequest(ProductRequest product) {

        presenter.performConfirmation(product.getRequestId(),
                AppPreferences.getString(Constants.AppPreferences.LOGGED_IN_USER_KEY, getActivity(),"0"),
                "1",
                product.getId(),
                AppPreferences.getString(Constants.AppPreferences.USER_TOKEN,getActivity(),""));
    }
}
