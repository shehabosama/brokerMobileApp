package com.android.jobber.Ui.fragments.ProfileFragment;

import android.content.Context;
import android.net.Uri;

public interface ProfileContract {
    interface Model {
        interface onFinishedListener {
            void onFinished(String result);
            void onFailuer(Throwable t);

        }
    }
    interface View{
        void showProgress();
        void hideProgress();
    }
    interface Presenter{

        void performUpdateInformation(String userId,String userName,String userAddress,String gender,String phoneNumber,String email,String image,String password);
        void performUpdateProfilePhoto(Uri uri, String userId, Context context,String phone ,String email, String gender,String name ,String address);

    }
}
