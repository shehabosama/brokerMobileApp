package com.android.jobber.Ui.fragments.JobberAccountFragment;

import com.android.jobber.common.model.Flats.FlatsResponse;
import com.android.jobber.common.model.MyFavoriteResponse;

public interface JobberAccountContract {
    interface Model {
        interface onFinishedListener {
            void onFinished(String result);
            void onFailuer(Throwable t);
            void loadAllFlats(FlatsResponse flatsResponse, MyFavoriteResponse favoriteResponse);
            //void loadFavoritesCarList();
        }
    }
    interface View{
        void showProgress();
        void hideProgress();
    }
    interface Presenter{
        void performGetUserFlats(String userId);
        void performDeleteProduct(String userId);
        void performGetFavoriteProduct(String user_id,  FlatsResponse flatsResponse);
        void performAddItemFavorite(String userId,String productId);
        void performDeleteItemFavorite(String userId,String productId,String delete);
        void performOrderRequest(String senderId,String receiverId,String orderId,String token,String timesTamp);
        void performRateUs(String normalUsers,String adminUsers,String rateDegree,String reviewDescription);


    }
}
