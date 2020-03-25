package com.android.jobber.Ui.fragments.JobberAccountFragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.android.jobber.R;
import com.android.jobber.Ui.fragments.ReviewFragment.ReviewFragment;
import com.android.jobber.Ui.fragments.TimeLineFragment.CustomeDialogViewPic;
import com.android.jobber.common.HelperStuffs.AppPreferences;
import com.android.jobber.common.HelperStuffs.Constants;
import com.android.jobber.common.HelperStuffs.Message;
import com.android.jobber.common.base.BaseFragment;
import com.android.jobber.common.model.Flats.Flat;
import com.android.jobber.common.model.Flats.FlatsImage;
import com.android.jobber.common.model.Flats.FlatsResponse;
import com.android.jobber.common.model.JobberUsers;
import com.android.jobber.common.model.MyFavorite;
import com.android.jobber.common.model.MyFavoriteResponse;
import com.android.jobber.common.network.Urls;
import com.squareup.picasso.Picasso;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class JobberAccountFragment extends BaseFragment implements JobberAccountContract.Model.onFinishedListener,JobberAccountContract.View, FlatAdapter.FlatAdapterInterAction {
    private SwipeRefreshLayout srlList;
    private PresenterJobberAccount presenter;
    private RecyclerView recyclerView;
    private List<Flat> flatList;
    private Flat flat;
    private MyFavorite myFavorite;
    private JobberUsers jobberUsers;
    private ImageView imageCall;
    private List<MyFavorite>favoriteList;
    private String rate;
    private RatingBar ratingBar,smallRateBar;
    private TextView textViewName,textViewPhone,textViewGender,textViewEmail,textViewAddress,btn_reviews;
    private String userAccountId;
    private CustomeDialogRate customeDialogRate;

    private CircleImageView profileImage;
    public JobberAccountFragment() {
        // Required empty public constructor
    }


    public static JobberAccountFragment newInstance() {

        return new JobberAccountFragment();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_jobber_account, container, false);
        initializeViews(view);
        setListeners();
        return view ;
    }

    @Override
    protected void initializeViews(View v) {

        favoriteList = new ArrayList<>();
        textViewGender =v.findViewById(R.id.gender_type);
        textViewPhone = v.findViewById(R.id.phone);
        profileImage = v.findViewById(R.id.profile);
        textViewEmail = v.findViewById(R.id.email);
        textViewName = v.findViewById(R.id.username);
        textViewAddress = v.findViewById(R.id.address);
        imageCall = v.findViewById(R.id.phone_call);
        recyclerView = v.findViewById(R.id.recycler_view);
        srlList = v.findViewById(R.id.srlList);
        ratingBar = v.findViewById(R.id.rating);
        smallRateBar = v.findViewById(R.id.rate_bar);
        btn_reviews = v.findViewById(R.id.btn_reviews);
         customeDialogRate = new CustomeDialogRate(getActivity());

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        presenter = new PresenterJobberAccount(this,this);
        flatList = new ArrayList<>();
        if(getArguments().getParcelable(Constants.BundleKeys.USER_ID).getClass().toString().equals("class com.android.jobber.common.model.MyFavorite")){
            myFavorite= getArguments().getParcelable(Constants.BundleKeys.USER_ID);
            textViewEmail.setText(myFavorite.getEmail());
            textViewAddress.setText(myFavorite.getAddress());
            textViewPhone.setText(myFavorite.getPhoneNo());
            textViewName.setText(myFavorite.getUsername());
            Picasso.with(getActivity())
                    .load(Urls.IMAGE_URL +myFavorite.getUserImage())
                    .placeholder(R.drawable.backgroundprof)
                    .into(profileImage);
            if(myFavorite.getGender().equals("1")){
                textViewGender.setText("male");
            }else if(myFavorite.getGender().equals("2")){
                textViewGender.setText("female");
            }else{
                textViewGender.setText("None");
            }
            presenter.performGetUserFlats(myFavorite.getUser_id());
            userAccountId = myFavorite.getUser_id();
            if (TextUtils.isEmpty(AppPreferences.getString(AppPreferences.getString(Constants.AppPreferences.LOGGED_IN_USER_KEY,getActivity(),"0")+userAccountId,getActivity(),""))){
                ratingBar.setRating(0.0f);
            }else{
                String rating = AppPreferences.getString(AppPreferences.getString(Constants.AppPreferences.LOGGED_IN_USER_KEY,getActivity(),"0")+userAccountId,getActivity(),"");

                if(rating.equals("-20")){
                    ratingBar.setRating(0.5f);
                }else if(rating.equals("-10")){
                    ratingBar.setRating(1.0f);
                }else if(rating.equals("-5")){
                    ratingBar.setRating(1.5f);
                }else if(rating.equals("0")){
                    ratingBar.setRating(2.0f);
                }else if(rating.equals("10")){
                    ratingBar.setRating(2.5f);
                }else if(rating.equals("20")){
                    ratingBar.setRating(3.0f);
                }else if(rating.equals("30")){
                    ratingBar.setRating(3.5f);
                }else if(rating.equals("40")){
                    ratingBar.setRating(4.0f);
                }else if(rating.equals("50")){
                    ratingBar.setRating(4.5f);
                }else if(rating.equals("60")){
                    ratingBar.setRating(5.0f);
                }
            }

            if (myFavorite.getRate()>60){
                smallRateBar.setRating(1.0f);
            }if (myFavorite.getRate()>120){
                smallRateBar.setRating(2.0f);
            } if (myFavorite.getRate()>180){
                smallRateBar.setRating(3.0f);
            } if (myFavorite.getRate()>240){
                smallRateBar.setRating(3.0f);
            } if (myFavorite.getRate()>320){
                smallRateBar.setRating(4.0f);
            } if (myFavorite.getRate()>380){
                smallRateBar.setRating(5.0f);
            }
        }else if (getArguments().getParcelable(Constants.BundleKeys.USER_ID).getClass().toString().equals("class com.android.jobber.common.model.JobberUsers")){



            jobberUsers= getArguments().getParcelable(Constants.BundleKeys.USER_ID);
            textViewEmail.setText(jobberUsers.getEmail());
            textViewAddress.setText(jobberUsers.getAddress());
            textViewPhone.setText(jobberUsers.getPhone_no());
            textViewName.setText(jobberUsers.getUser_name());
            Picasso.with(getActivity())
                    .load(Urls.IMAGE_URL +jobberUsers.getUser_image())
                    .placeholder(R.drawable.backgroundprof)
                    .into(profileImage);
            if(jobberUsers.getGender().equals("1")){
                textViewGender.setText("male");
            }else if(jobberUsers.getGender().equals("2")){
                textViewGender.setText("female");
            }else{
                textViewGender.setText("None");
            }
            presenter.performGetUserFlats(String.valueOf(jobberUsers.getId()));
            userAccountId =String.valueOf(jobberUsers.getId());
            if (TextUtils.isEmpty(AppPreferences.getString(AppPreferences.getString(Constants.AppPreferences.LOGGED_IN_USER_KEY,getActivity(),"0")+userAccountId,getActivity(),""))){
                ratingBar.setRating(0.0f);
            }else{
                String rating = AppPreferences.getString(AppPreferences.getString(Constants.AppPreferences.LOGGED_IN_USER_KEY,getActivity(),"0")+userAccountId,getActivity(),"");

                if(rating.equals("-20")){
                    ratingBar.setRating(0.5f);
                }else if(rating.equals("-10")){
                    ratingBar.setRating(1.0f);
                }else if(rating.equals("-5")){
                    ratingBar.setRating(1.5f);
                }else if(rating.equals("0")){
                    ratingBar.setRating(2.0f);
                }else if(rating.equals("10")){
                    ratingBar.setRating(2.5f);
                }else if(rating.equals("20")){
                    ratingBar.setRating(3.0f);
                }else if(rating.equals("30")){
                    ratingBar.setRating(3.5f);
                }else if(rating.equals("40")){
                    ratingBar.setRating(4.0f);
                }else if(rating.equals("50")){
                    ratingBar.setRating(4.5f);
                }else if(rating.equals("60")){
                    ratingBar.setRating(5.0f);
                }
            }

            if (jobberUsers.getRate()>60){
                smallRateBar.setRating(1.0f);
            }if (jobberUsers.getRate()>120){
                smallRateBar.setRating(2.0f);
            } if (jobberUsers.getRate()>180){
                smallRateBar.setRating(3.0f);
            } if (jobberUsers.getRate()>240){
                smallRateBar.setRating(3.0f);
            } if (jobberUsers.getRate()>320){
                smallRateBar.setRating(4.0f);
            } if (jobberUsers.getRate()>380){
                smallRateBar.setRating(5.0f);
            }


        }else{
            flat = getArguments().getParcelable(Constants.BundleKeys.USER_ID);
            textViewEmail.setText(flat.getEmail());
            textViewAddress.setText(flat.getAddress());
            textViewPhone.setText(flat.getPhone_no());
            textViewName.setText(flat.getUsername());
            Picasso.with(getActivity())
                    .load(Urls.IMAGE_URL +flat.getUserImage())
                    .placeholder(R.drawable.backgroundprof)
                    .into(profileImage);
            if(flat.getGender().equals("1")){
                textViewGender.setText("male");
            }else if(flat.getGender().equals("2")){
                textViewGender.setText("female");
            }else{
                textViewGender.setText("None");
            }
            presenter.performGetUserFlats(flat.getUserId());
            userAccountId = flat.getUserId();
            if (TextUtils.isEmpty(AppPreferences.getString(AppPreferences.getString(Constants.AppPreferences.LOGGED_IN_USER_KEY,getActivity(),"0")+userAccountId,getActivity(),""))){
                ratingBar.setRating(0.0f);
            }else{
                String rating = AppPreferences.getString(AppPreferences.getString(Constants.AppPreferences.LOGGED_IN_USER_KEY,getActivity(),"0")+userAccountId,getActivity(),"");

                if(rating.equals("-20")){
                    ratingBar.setRating(0.5f);
                }else if(rating.equals("-10")){
                    ratingBar.setRating(1.0f);
                }else if(rating.equals("-5")){
                    ratingBar.setRating(1.5f);
                }else if(rating.equals("0")){
                    ratingBar.setRating(2.0f);
                }else if(rating.equals("10")){
                   ratingBar.setRating(2.5f);
                }else if(rating.equals("20")){
                    ratingBar.setRating(3.0f);
                }else if(rating.equals("30")){
                   ratingBar.setRating(3.5f);
                }else if(rating.equals("40")){
                   ratingBar.setRating(4.0f);
                }else if(rating.equals("50")){
                   ratingBar.setRating(4.5f);
                }else if(rating.equals("60")){
                    ratingBar.setRating(5.0f);
                }

                if (flat.getRate()>60){
                    smallRateBar.setRating(1.0f);
                }if (flat.getRate()>120){
                    smallRateBar.setRating(2.0f);
                } if (flat.getRate()>180){
                    smallRateBar.setRating(3.0f);
                } if (flat.getRate()>240){
                    smallRateBar.setRating(3.0f);
                } if (flat.getRate()>320){
                    smallRateBar.setRating(4.0f);
                } if (flat.getRate()>380){
                    smallRateBar.setRating(5.0f);
                }
            }
        }





        recyclerView.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
            @Override
            public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
                int action = e.getAction();
                switch (action) {
                    case MotionEvent.ACTION_MOVE:
                        rv.getParent().requestDisallowInterceptTouchEvent(true);
                        break;
                }
                return false;
            }

            @Override
            public void onTouchEvent(RecyclerView rv, MotionEvent e) {

            }

            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

            }
        });
    }

    @Override
    protected void setListeners() {
        srlList.setOnRefreshListener(srlListRefreshListener);
        imageCall.setOnClickListener(imageCallListener);
        ratingBar.setOnRatingBarChangeListener(ratingBarListener);
        btn_reviews.setOnClickListener(btn_reviewsListener);
    }
    private View.OnClickListener btn_reviewsListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            ReviewFragment reviewFragment = new ReviewFragment();
//            getChildFragmentManager().beginTransaction().replace(R.id.container_body,reviewFragment, String.valueOf(Constants.BottomNavConstants.ACTION_REVIEWS)).commit();
            replaceFragment(R.id.container_body,reviewFragment,String.valueOf(Constants.BottomNavConstants.ACTION_REVIEWS),Constants.BundleKeys.USER_ID_FROM_JOBBER,userAccountId);
        }
    };
    private RatingBar.OnRatingBarChangeListener ratingBarListener = new RatingBar.OnRatingBarChangeListener() {
        @Override
        public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {

            if(v == 0.5){
                rate = "-20";
                customeDialogRate.showDialog(getActivity(),presenter,rate,userAccountId);
            }else if(v == 1.0){
                rate = "-10";
                customeDialogRate.showDialog(getActivity(),presenter,rate,userAccountId);
            }else if(v == 1.5){
                rate = "-5";
                customeDialogRate.showDialog(getActivity(),presenter,rate,userAccountId);
            }else if(v == 2.0){
                rate = "0";
                customeDialogRate.showDialog(getActivity(),presenter,rate,userAccountId);
            }else if(v == 2.5){
                rate = "10";
                customeDialogRate.showDialog(getActivity(),presenter,rate,userAccountId);
            }else if(v == 3.0){
                rate = "20";
                customeDialogRate.showDialog(getActivity(),presenter,rate,userAccountId);
            }else if(v == 3.5){
                rate = "30";
                customeDialogRate.showDialog(getActivity(),presenter,rate,userAccountId);
            }else if(v == 4.0){
                rate = "40";
                customeDialogRate.showDialog(getActivity(),presenter,rate,userAccountId);
            }else if(v == 4.5){
                rate = "50";
                customeDialogRate.showDialog(getActivity(),presenter,rate,userAccountId);
            }else if(v == 5.0){
                rate = "60";
                customeDialogRate.showDialog(getActivity(),presenter,rate,userAccountId);
            }
        }
    };
    private View.OnClickListener imageCallListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent callIntent = new Intent(Intent.ACTION_DIAL);
            callIntent.setData(Uri.parse("tel:"+flat.getPhone_no()));
            startActivity(callIntent);
        }
    };
    private SwipeRefreshLayout.OnRefreshListener srlListRefreshListener = new SwipeRefreshLayout.OnRefreshListener() {
        @Override
        public void onRefresh() {
            presenter.performGetUserFlats(flat.getUserId());
        }
    };

    @Override
    public void onFinished(String result) {
        Message.message(getActivity(),result);
        if(customeDialogRate !=null){
            customeDialogRate.hideDialog();

        }
    }

    @Override
    public void onFailuer(Throwable t) {

    }
    @Override
    public void loadAllFlats(FlatsResponse flatsResponse, MyFavoriteResponse favoriteResponse) {
        flatList.clear();
        flatList.addAll(flatsResponse.getFlats());
        favoriteList.addAll(favoriteResponse.getMyFavorite());
        FlatAdapter flatAdapter = new FlatAdapter(flatList, favoriteList, getActivity(), this);
        recyclerView.setAdapter(flatAdapter);
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

    @Override
    public void onRefrishing() {

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
    public void onClickMenu(final Flat flat) {
        //Resources res = getResources();

        final CharSequence option[]=new CharSequence[]{
                "View Location",
                "Call him",
                AppPreferences.getBoolean(
                        Constants.AppPreferences.IS_ADMIN,
                        getActivity(),
                        false) &&
                        flat.getUserId().equals(AppPreferences.getString(
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
    public void onClickPhoto(FlatsImage flatsImage) {
        CustomeDialogViewPic customeDialogViewPic = new CustomeDialogViewPic();
        customeDialogViewPic.showDialog(getActivity(),flatsImage.getImageName());
    }

    @Override
    public void onClickItem(Flat flat) {

    }

}
