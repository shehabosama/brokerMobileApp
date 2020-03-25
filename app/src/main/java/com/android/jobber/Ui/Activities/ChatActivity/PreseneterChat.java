package com.android.jobber.Ui.Activities.ChatActivity;

import com.android.jobber.common.model.MainResponse;
import com.android.jobber.common.model.ChatModel.Messages;
import com.android.jobber.common.network.WebService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PreseneterChat implements ChatContract.Presenter {
    ChatContract.Model.onFinishedListener mModel;
    ChatContract.View mView;

    public PreseneterChat(ChatContract.Model.onFinishedListener mModel, ChatContract.View mView) {
        this.mModel = mModel;
        this.mView = mView;
    }


    @Override
    public void performAddMessage(Messages messages) {


        if(messages !=null){
            WebService.getInstance(true).getApi().add_Message(messages).enqueue(new Callback<MainResponse>() {
                @Override
                public void onResponse(Call<MainResponse> call, Response<MainResponse> response) {
                    if(response.body().status == 0)
                    {

                       mModel.onFinished("Error while trying to send the message ");
                    }else{

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
    public void performGetMessages(String chatRoomId) {
        mView.showProgress();
        WebService.getInstance(true).getApi().getMessages(Integer.parseInt(chatRoomId)).enqueue(new Callback<List<Messages>>() {
            @Override
            public void onResponse(Call<List<Messages>> call, Response<List<Messages>> response) {
                mModel.loadAllChatMessages(response.body());
                mView.hideProgress();
            }

            @Override
            public void onFailure(Call<List<Messages>> call, Throwable t) {
               mModel.onFailuer(t);
                mView.showProgress();
            }
        });
    }
}
