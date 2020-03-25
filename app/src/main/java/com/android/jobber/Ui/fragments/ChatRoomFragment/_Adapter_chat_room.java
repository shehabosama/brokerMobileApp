package com.android.jobber.Ui.fragments.ChatRoomFragment;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.android.jobber.Ui.Activities.ChatActivity.Chat_activity;
import com.android.jobber.R;
import com.android.jobber.common.model.ChatModel.ChatRoom;
import java.util.List;

public class _Adapter_chat_room extends RecyclerView.Adapter
{
    List<ChatRoom> list_chat_rooms;
    Context context;

    public _Adapter_chat_room(Context context,List<ChatRoom> chatRooms)
    {
        this.context = context;
        this.list_chat_rooms = chatRooms;
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.custom_desplay_chat_room,viewGroup,false);
        ViewHolderChatRoom chatRoom =new ViewHolderChatRoom(view);
        return chatRoom;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {

        ViewHolderChatRoom viewholderchatRoom = (ViewHolderChatRoom)viewHolder;
        final ChatRoom chatRoom = list_chat_rooms.get(i);
        viewholderchatRoom.room_desc.setText(chatRoom.room_desc);
        viewholderchatRoom.room_text.setText(chatRoom.room_name);
        viewholderchatRoom.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, Chat_activity.class);
                intent.putExtra("room_id",Integer.parseInt(chatRoom.id));
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return list_chat_rooms.size();
    }

    public class ViewHolderChatRoom extends RecyclerView.ViewHolder
    {

        TextView room_text;
        TextView room_desc;

        public ViewHolderChatRoom(@NonNull View itemView) {
            super(itemView);
            room_text = itemView.findViewById(R.id.room_text);
            room_desc = itemView.findViewById(R.id.room_desc);
        }
    }
}
