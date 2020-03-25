package com.android.jobber.Ui.fragments.PublishingFragment;

import android.content.Context;
import android.net.Uri;

import com.android.jobber.Ui.Activities.Login.LoginContract;

import java.util.List;

public interface PublishingContract {
    interface Model {
        interface onFinishedListener {
            void onFinished(String result);
            void onFailuer(Throwable t);

            void getLatLang(double lat, double lang);
        }
        void Login(LoginContract.Model.onFinishedListener onFinishedListener, String email, String password);
    }
    interface View{
        void showProgress();
        void hideProgress();
    }
    interface Presenter{
        void performPublish(List<Uri> uris, Context context,String userId,String flatDescription,String flatLocation,String flatPrice,String typeFlat ,String lat,String lang,String productType);
    }
}
