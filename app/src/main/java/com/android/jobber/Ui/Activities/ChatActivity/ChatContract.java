package com.android.jobber.Ui.Activities.ChatActivity;

import com.android.jobber.common.model.ChatModel.Messages;

import java.util.List;

public interface ChatContract {
    interface Model {
        interface onFinishedListener {
            void onFinished(String result);
            void onFailuer(Throwable t);
            void loadAllChatMessages(List<Messages> flatsResponse);
            //void loadFavoritesCarList();
        }
    }
    interface View{
        void showProgress();
        void hideProgress();
    }
    interface Presenter{
        void performAddMessage(Messages messages);
        void performGetMessages(String chatRoomId);

    }
}
