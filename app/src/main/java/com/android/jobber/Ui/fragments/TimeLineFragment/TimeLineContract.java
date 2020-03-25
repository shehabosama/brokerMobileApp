package com.android.jobber.Ui.fragments.TimeLineFragment;

import com.android.jobber.common.model.Flats.FlatsResponse;
import com.android.jobber.common.model.MyFavoriteResponse;

public interface TimeLineContract {
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
        void performGetAllFlats(String userId);
        void performDeleteProduct(String productId);
        void performFilterProducts(String type_flats,String price_from,String price_to,String owner_ship,String product_country,String user_id);
        void performOrderRequest(String senderId,String receiverId,String orderId,String token,String timesTamp);
        void performAddItemFavorite(String userId,String productId);
        void performDeleteItemFavorite(String userId,String productId,String delete);
        void performGetFavoriteProduct(String user_id, FlatsResponse flatsResponse);

    }
}
