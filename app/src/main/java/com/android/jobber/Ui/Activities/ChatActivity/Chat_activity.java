package com.android.jobber.Ui.Activities.ChatActivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.android.jobber.R;
import com.android.jobber.common.SqlHelper.myDbAdapter;
import com.android.jobber.common.base.BaseActivity;
import com.android.jobber.common.model.ChatModel.Messages;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.List;

public class Chat_activity extends BaseActivity implements ChatContract.View,ChatContract.Model.onFinishedListener{

    private RecyclerView recyclerChat;
    private ImageView sentImage;
    private EditText content_edit;
    private int room_id;
    private int user_id;
    private String user_name;
    private _Adapter_Messageing adapter;
    private List<Messages> messages_list;
    private myDbAdapter helper;
    private PreseneterChat presenter;
    private SwipeRefreshLayout swipeRefreshLayout;
    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(messageReceiver,new IntentFilter("UpdateChatActivity"));
    }
    @Override
    protected void onStop() {
        super.onStop();
        unregisterReceiver(messageReceiver);
    }
    private BroadcastReceiver messageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            Messages messages = intent.getParcelableExtra("msg");
            if(messages!=null)
            {
                messages_list.add(messages);
                adapter.notifyItemInserted(messages_list.size()-1);
                recyclerChat.scrollToPosition(messages_list.size()-1);
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_activity);
        initializeViews();
        setListeners();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

    }

    @Override
    protected void initializeViews() {
        helper = new myDbAdapter(this);
        room_id = getIntent().getExtras().getInt("room_id");
        user_id = Integer.parseInt(helper.getEmployeeName("user_id"));
        user_name = helper.getEmployeeName("name");
        recyclerChat = findViewById(R.id.recycler_chat);
        swipeRefreshLayout = findViewById(R.id.swipe);
        swipeRefreshLayout.setEnabled(false);
       // recyclerChat.setLayoutManager(new LinearLayoutManager(this));
       LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
       recyclerChat.setLayoutManager(linearLayoutManager);
        linearLayoutManager.setStackFromEnd(true);
        sentImage = findViewById(R.id.sent_content);
        content_edit = findViewById(R.id.edit_message);
        presenter = new PreseneterChat(this,this);
        presenter.performGetMessages(String.valueOf(room_id));

        FirebaseMessaging.getInstance().subscribeToTopic("room" + room_id).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {

            }
        });
    }

    @Override
    protected void setListeners() {
        sentImage.setOnClickListener(sentImageListener);
    }
    private View.OnClickListener sentImageListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            String msg = content_edit.getText().toString();
            Messages messages = new Messages();
            messages.setType("1");
            messages.setContent(msg);
            messages.setRoom_id(String.valueOf(room_id));
            messages.setUser_id(String.valueOf(user_id));
            messages.setUser_name(user_name);
            messages_list.add(messages);
            adapter.notifyItemInserted(messages_list.size()-1);
            recyclerChat.scrollToPosition(messages_list.size()-1);
            content_edit.setText("");
            presenter.performAddMessage(messages);
        }
    };
    @Override
    public void onFinished(String result) {

    }

    @Override
    public void onFailuer(Throwable t) {

       // Message.message(Chat_activity.this,t.getMessage());
    }

    @Override
    public void loadAllChatMessages(List<Messages> messagesResponse) {
        messages_list = messagesResponse;
        adapter = new _Adapter_Messageing(messages_list,helper,Chat_activity.this);
        recyclerChat.setAdapter(adapter);
    }

    @Override
    public void showProgress() {
        swipeRefreshLayout.setRefreshing(true);
    }

    @Override
    public void hideProgress() {
        swipeRefreshLayout.setRefreshing(false);
    }
}
