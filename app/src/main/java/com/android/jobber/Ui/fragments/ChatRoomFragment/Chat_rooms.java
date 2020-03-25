package com.android.jobber.Ui.fragments.ChatRoomFragment;


import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.android.jobber.R;
import com.android.jobber.common.HelperStuffs.AppPreferences;
import com.android.jobber.common.HelperStuffs.Constants;
import com.android.jobber.common.HelperStuffs.Message;
import com.android.jobber.common.base.BaseFragment;
import com.android.jobber.common.model.ChatModel.ChatRoom;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class Chat_rooms extends BaseFragment implements ChatRoomContract.View,ChatRoomContract.Model.onFinishedListener{
    private _Adapter_chat_room adapter;
    private List<ChatRoom> rooms;
    private RecyclerView recyclerView;
    private FloatingActionButton floatingActionButton;
    private int ChatRoomPage =1;
    private List<ChatRoom> chatRooms;
    private PresenterChatRoom presenter;
    private ItemTouchHelper.SimpleCallback swipchatRoomCallBack;
    private SwipeRefreshLayout srlList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chat_rooms, container, false);
        initializeViews(view);
        setListeners();
        return view;
    }
    @Override
    protected void initializeViews(View v) {
        swipeItem();
        chatRooms = new ArrayList<>();
        presenter = new PresenterChatRoom(this,this);
        floatingActionButton = v.findViewById(R.id.fab);
        recyclerView = v.findViewById(R.id.recycler_chat_rooms);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);
        srlList = v.findViewById(R.id.srlList);

        recyclerView.setItemAnimator(new DefaultItemAnimator());

        adapter = new _Adapter_chat_room(getActivity(),chatRooms);

        recyclerView.addOnScrollListener(new EndlessRecyclerViewScrollListener(linearLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {

                ChatRoomPage++;
                presenter.performGetChatRoom(String.valueOf(ChatRoomPage));

            }
        });

                if(AppPreferences.getBoolean(Constants.AppPreferences.IS_ADMIN,getActivity(),false))
                {
                    floatingActionButton.setVisibility(View.VISIBLE);
                    ItemTouchHelper itemTouchHelper = new ItemTouchHelper(swipchatRoomCallBack);
                    itemTouchHelper.attachToRecyclerView(recyclerView);

                }else
                {
                    floatingActionButton.setVisibility(View.INVISIBLE);
                }

                presenter.performGetChatRoom(String.valueOf(ChatRoomPage));
    }


    public void swipeItem(){

        swipchatRoomCallBack = new ItemTouchHelper.SimpleCallback(
                0,ItemTouchHelper.LEFT
        ) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder viewHolder1) {
                return false;
            }
            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
                final int position = viewHolder.getAdapterPosition();
                int chatRoomID = Integer.parseInt(chatRooms.get(position).id);
                presenter.performDeleteChatRoom(String.valueOf(chatRoomID));
                chatRooms.remove(position);
                adapter.notifyItemRemoved(position);
            }
            @Override
            public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
                if(actionState == ItemTouchHelper.ACTION_STATE_SWIPE)
                {
                    View itemview = viewHolder.itemView;
                    Paint p = new Paint();
                    if(dX <=0)
                    {
                        p.setColor(Color.RED);
                        c.drawRect((float) itemview.getRight()+dX,(float)itemview.getTop(),(float)itemview.getRight(),itemview.getBottom(),p);
                    }
                }
                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);

            }
        };
    }
    @Override
    protected void setListeners() {
        floatingActionButton.setOnClickListener(floatingActionButtonListener);
        srlList.setOnRefreshListener(srlListRefreshListener);

    }
    private SwipeRefreshLayout.OnRefreshListener srlListRefreshListener = new SwipeRefreshLayout.OnRefreshListener() {
        @Override
        public void onRefresh() {

            presenter.performGetChatRoom(String.valueOf("1"));
        }
    };
   private View.OnClickListener floatingActionButtonListener = new View.OnClickListener() {
       @Override
       public void onClick(View view) {

           CustomeDialogAddRoom customeDialogAddRoom = new CustomeDialogAddRoom();
           customeDialogAddRoom.showDialog(getActivity(),presenter);

       }
   };



    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
         getActivity().getMenuInflater().inflate(R.menu.chat_menu,menu);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.logout:

                break;
            case R.id.news:

        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onFinished(String result) {
        Message.message(getActivity(),result);
    }

    @Override
    public void onFailuer(Throwable t) {

       // Message.message(getActivity(),t.getLocalizedMessage());
    }

    @Override
    public void loadAllRooms(List<ChatRoom> flatsResponse) {
        chatRooms.clear();
        rooms = flatsResponse;
        chatRooms.addAll(flatsResponse);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();


    }

    @Override
    public void showProgress() {

        srlList.setRefreshing(true);
    }

    @Override
    public void hideProgress() {
        srlList.setRefreshing(false);
    }
}
