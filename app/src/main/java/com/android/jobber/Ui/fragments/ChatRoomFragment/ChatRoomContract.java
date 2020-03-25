package com.android.jobber.Ui.fragments.ChatRoomFragment;

import com.android.jobber.common.model.ChatModel.ChatRoom;

import java.util.List;

public interface ChatRoomContract
{

    interface Model {
        interface onFinishedListener {
            void onFinished(String result);
            void onFailuer(Throwable t);
            void loadAllRooms(List<ChatRoom> flatsResponse);
            //void loadFavoritesCarList();
        }
    }
    interface View{
        void showProgress();
        void hideProgress();
    }
    interface Presenter{
        void performGetChatRoom(String page);
        void performDeleteChatRoom(String chatRoomId);
        void performAddChatRoom(ChatRoom chatRoom);

    }
}
