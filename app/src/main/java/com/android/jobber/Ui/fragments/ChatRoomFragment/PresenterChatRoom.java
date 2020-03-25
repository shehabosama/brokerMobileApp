package com.android.jobber.Ui.fragments.ChatRoomFragment;

import android.text.TextUtils;

import com.android.jobber.common.model.ChatModel.ChatRoom;
import com.android.jobber.common.model.MainResponse;
import com.android.jobber.common.network.WebService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PresenterChatRoom implements ChatRoomContract.Presenter{
    private ChatRoomContract.Model.onFinishedListener mModel;
    private ChatRoomContract.View mView;

    public PresenterChatRoom(ChatRoomContract.Model.onFinishedListener mModel, ChatRoomContract.View mView) {
        this.mModel = mModel;
        this.mView = mView;
    }

    @Override
    public void performGetChatRoom(String page) {
        mView.showProgress();
        if(TextUtils.isEmpty(page)){
            mModel.onFinished("Something went wrong");
            mView.hideProgress();
        }else{
           WebService.getInstance(true).getApi().getAllChatRooms(Integer.parseInt(page)).enqueue(new Callback<List<ChatRoom>>() {
                @Override
                public void onResponse(Call<List<ChatRoom>> call, Response<List<ChatRoom>> response) {

                    mModel.loadAllRooms(response.body());
                    mView.hideProgress();
                    // adapter.notifyItemRangeInserted(adapter.getItemCount(),chatRooms.size()-1);
                }

                @Override
                public void onFailure(Call<List<ChatRoom>> call, Throwable t) {
                    mModel.onFailuer(t);
                    mView.hideProgress();
                }
            });
        }
    }

    @Override
    public void performDeleteChatRoom(String chatRoomId) {
        mView.showProgress();
        if(TextUtils.isEmpty(chatRoomId)){
            mModel.onFinished("Failed deleted room");
            mView.hideProgress();
        }else{
            WebService.getInstance(true).getApi().deleteChatRoom(Integer.parseInt(chatRoomId)).enqueue(new Callback<MainResponse>() {
                @Override
                public void onResponse(Call<MainResponse> call, Response<MainResponse> response) {
                    if(response.body().status==1)
                    {
                        mModel.onFinished(response.body().message);
                        mView.hideProgress();
                    }else
                    {
                        mModel.onFinished("Something went wrong");
                        mView.hideProgress();
                    }
                }

                @Override
                public void onFailure(Call<MainResponse> call, Throwable t) {
                    mView.hideProgress();
                    mModel.onFailuer(t);
                }
            });
        }
    }

    @Override
    public void performAddChatRoom(ChatRoom chatRoom) {
        mView.showProgress();
        if(chatRoom == null){
            mModel.onFinished("Something Went Wrong...");
            mView.hideProgress();
        }
        WebService.getInstance(true).getApi().add_chat_rooms(chatRoom).enqueue(new Callback<MainResponse>() {
            @Override
            public void onResponse(Call<MainResponse> call, Response<MainResponse> response) {
                if(response.body().status == 1)
                {
                  mModel.onFinished(response.body().message);
                    mView.hideProgress();
                }else{
                    mView.hideProgress();
                }
            }

            @Override
            public void onFailure(Call<MainResponse> call, Throwable t) {

                mView.hideProgress();
            }
        });
    }
}
