package com.android.jobber.Ui.fragments.FavoritesFragment;

import com.android.jobber.common.model.MyFavoriteResponse;

public interface FavoriteContract {

    interface Model {
        interface onFinishedListener {
            void onFinished(String result);
            void getMyFavorite(MyFavoriteResponse myFavoriteResponse);
            void onFailuer(Throwable t);

        }
    }

    interface View {
        void showProgress();

        void hideProgress();
    }

    interface Presenter {
        void performMyFavorites(String user_id);
        void performDeleteItemFavorite(String userId, String productId, String delete);
        void performOrderRequest(String senderId, String receiverId, String orderId, String token,String timesTamp);
        void performDeleteProduct(String uerId);
    }

}
